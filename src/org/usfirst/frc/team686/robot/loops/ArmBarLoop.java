package org.usfirst.frc.team686.robot.loops;

import org.usfirst.frc.team686.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public class ArmBarLoop implements Loop
{
	private static ArmBarLoop instance = new ArmBarLoop();
	public static ArmBarLoop getInstance() { return instance; }
	
	public double Kf = Constants.kArmBarKf;
	public double Kp = Constants.kArmBarKp;
	public double Kd = Constants.kArmBarKd;
	public double Ki = Constants.kArmBarKi;
	
	public enum ArmBarState { UNINITIALIZED, CALIBRATING, RUNNING, ESTOPPED; }
	public ArmBarState state = ArmBarState.UNINITIALIZED;
	public ArmBarState nextState = ArmBarState.UNINITIALIZED;
	
	public boolean enable = false;
	
	public double position;
	public double goal;
	public double filteredGoal;

	public double error = 0.0;
	public double dError = 0.0;
	public double iError = 0.0;
	public double lastError = 0.0;
	
	public double voltage = 0.0;
	
	public TalonSRX armBarTalon;
	public DigitalInput limitSwitch;
	
	public ArmBarLoop()
	{
		System.out.println("ArmBarLoop constructor");
		
		// Configure Talon
		armBarTalon = new TalonSRX(Constants.kArmBarTalonId);
		armBarTalon.setStatusFramePeriod(StatusFrameEnhanced.Status_2_Feedback0, 10, Constants.kTalonTimeoutMs);
		armBarTalon.set(ControlMode.PercentOutput, 0.0);
		armBarTalon.setNeutralMode(NeutralMode.Brake);
				
		// Configure Encoder
		armBarTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kTalonPidIdx, Constants.kTalonTimeoutMs);	// configure for closed-loop PID
		armBarTalon.setSensorPhase(true);
		armBarTalon.setInverted(true);

		// Configure Limit Switch
		limitSwitch = new DigitalInput(1);
		
		enable = false;
	}
	

	public void enable() { enable = true; }
	public void disable() { enable = false; }
	
	public void setGoal(double goal_){ goal = goal_; }
	public double getGoal () { return goal; } 
	
	public double getFilteredGoal() { return filteredGoal; }

	public ArmBarState getState() { return state; }
	
	public void stop() { goal = getPosition(); }

	public double getPosition() { return position; }
	
	public void setPosition(double _angleDeg) 
	{
		int encoderEdges = (int)(_angleDeg * Constants.kArmBarEncoderPulsePerDeg);
		armBarTalon.setSelectedSensorPosition(encoderEdges, Constants.kTalonPidIdx, Constants.kTalonTimeoutMs);
	}
	
	public int getEncoder() 
	{
		int encoderEdges = armBarTalon.getSensorCollection().getQuadraturePosition();
		return encoderEdges;
	}
	
	public double getPositionFromEncoder() 
	{
		// returns encoder angle in degrees
		double angleDeg = getEncoder() / Constants.kArmBarEncoderPulsePerDeg;
		return angleDeg;
	}
	
	public boolean getLimitSwitch() { return !limitSwitch.get(); }	// returns true when limit switch is triggered 
	
	
	
	@Override
	public void onStart() {
		state = ArmBarState.UNINITIALIZED;
		nextState = ArmBarState.UNINITIALIZED;
	}

	@Override
	public void onLoop() 
	{
		position = getPositionFromEncoder();
		boolean limitSwitch = getLimitSwitch();
		
		double voltage = calcVoltage(position, limitSwitch, enable);			// output in [-12, +12] volts	
		double percentOutput = voltage / Constants.kMaxBatteryVoltage;			// normalize output to [-1,. +1]
		armBarTalon.set(ControlMode.PercentOutput, percentOutput);				// send to motor control
		
		System.out.println(toString());
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
	}	
	
	
	
	public double calcVoltage(double position, boolean limitSwitchTriggered, boolean enabled)
	{
		// transition states
		state = nextState;
		// start over if ever disabled
		if (!enabled)
		{
			state = ArmBarState.UNINITIALIZED;
		}
		
		switch (state)
		{
		case UNINITIALIZED:
			// initial state.  stay here until enabled
			if (enabled)
			{
				nextState = ArmBarState.CALIBRATING;	// when enabled, state ZEROING
				filteredGoal = position;				// hold position
			}
			break;
			
		case CALIBRATING:
			// slowly move up towards limit switch
			filteredGoal += (Constants.kArmBarZeroingVelocity * Constants.kLoopDt);
			
			if (limitSwitchTriggered)
			{
				// CALIBRATING is done when limit switch is hit
				setPosition(Constants.kArmBarUpAngleDeg);	// write new position to Talon
				position = Constants.kArmBarUpAngleDeg;		// override position before limit switch was hit
				setGoal(position);							// initial goal is to stay in the same position
				filteredGoal = position;					// initial goal is to stay in the same position
				nextState = ArmBarState.RUNNING;			// start running state
			}
			break;
			
		case RUNNING:
			// velocity control -- move filtered goal a little more towards the ultimate goal
			
			if (goal > filteredGoal)
			{
				// moving up
				filteredGoal += Constants.kArmBarVelocity * Constants.kLoopDt;
				filteredGoal = Math.min(filteredGoal, goal);
			}
			else
			{
				// moving down
				filteredGoal -= Constants.kArmBarVelocity * Constants.kLoopDt;
				filteredGoal = Math.max(filteredGoal, goal);
			}

			break;
			
		default:
			nextState = ArmBarState.UNINITIALIZED;
		}
		
		error = filteredGoal - position;
		dError = (error - lastError) / Constants.kLoopDt;
		iError += (error * Constants.kLoopDt);

		lastError = error;
		
		voltage = Kp * error + Kd * dError + Ki * iError;
		voltage = Math.min(Constants.kMaxArmBarVoltage, Math.max(-Constants.kMaxArmBarVoltage, voltage));
		
		if (limitSwitchTriggered)
			voltage = Math.min(voltage, 0.0);	// do not let elevator continue up when at limit switch
		
		return voltage;
	}	
	
    public String toString() 
    {
    	return String.format("%s, Enc: %d, Pos: %.1f, LimSwitch: %d, Goal: %.1f, FiltGoal = %.1f, e = %.1f, de = %.1f, ie = %.1f, voltage = %.1f", state.toString(), getEncoder(), getPosition(), getLimitSwitch() ? 1 : 0, goal, filteredGoal, error, dError, iError, voltage);
    }
}