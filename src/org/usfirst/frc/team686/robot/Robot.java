package org.usfirst.frc.team686.robot;

import org.usfirst.frc.team686.robot.auto.AutoModeBase;
import org.usfirst.frc.team686.robot.auto.AutoModeExecuter;
import org.usfirst.frc.team686.robot.auto.actions.SeriesAction;
import org.usfirst.frc.team686.robot.auto.modes.PointTurnMode;
import org.usfirst.frc.team686.robot.auto.modes.RunSeriesActionMode;
import org.usfirst.frc.team686.robot.command_status.DriveState;
import org.usfirst.frc.team686.robot.command_status.RobotState;
import org.usfirst.frc.team686.robot.lib.joystick.ArcadeDriveJoystick;
import org.usfirst.frc.team686.robot.lib.joystick.ButtonBoard;
import org.usfirst.frc.team686.robot.lib.joystick.JoystickControlsBase;
import org.usfirst.frc.team686.robot.lib.sensors.SwitchableCameraServer;
import org.usfirst.frc.team686.robot.subsystems.Drive;
import org.usfirst.frc.team686.robot.util.DataLogController;
import org.usfirst.frc.team686.robot.lib.util.DataLogger;
import org.usfirst.frc.team686.robot.command_status.DriveCommand;

import java.util.Optional;
import java.util.List;
import java.util.TimeZone;

import org.usfirst.frc.team686.robot.Robot.OperationalMode;
import org.usfirst.frc.team686.robot.lib.util.CrashTracker;
import org.usfirst.frc.team686.robot.lib.util.Pose;
import org.usfirst.frc.team686.robot.loops.DriveLoop;
import org.usfirst.frc.team686.robot.loops.LoopController;
import org.usfirst.frc.team686.robot.loops.RobotStateLoop;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
	
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	JoystickControlsBase controls = ArcadeDriveJoystick.getInstance();
	ButtonBoard buttonBoard = ButtonBoard.getInstance();

	RobotState robotState = RobotState.getInstance();
	Drive drive = Drive.getInstance();
	
	AutoModeExecuter autoModeExecuter = null;
	
	LoopController loopController;
	
	SmartDashboardInteractions smartDashboardInteractions;
	DataLogController robotLogger;

	CameraServer cameraServer;

	
	enum OperationalMode 
    {
    	DISABLED(0), AUTONOMOUS(1), TELEOP(2), TEST(3);
    	
    	private int val;
    	
    	private OperationalMode (int val) {this.val = val;}
    	public int getVal() {return val;}
    } 
    
    OperationalMode operationalMode = OperationalMode.DISABLED;
    
    public Robot() {
    	CrashTracker.logRobotConstruction();
    }
    
    
	@Override
	public void robotInit() {
		try
    	{
    		CrashTracker.logRobotInit();
    		
    		loopController = new LoopController();
    		loopController.register(drive.getVelocityPIDLoop());
    		loopController.register(DriveLoop.getInstance());
    		loopController.register(RobotStateLoop.getInstance());
    		
    		smartDashboardInteractions = new SmartDashboardInteractions();
    		smartDashboardInteractions.initWithDefaults();
    		
    		// set datalogger and time info
    		TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
    		
    		robotLogger = DataLogController.getRobotLogController();
    		robotLogger.register(Drive.getInstance().getLogger());
    		robotLogger.register(drive.getCommand().getLogger());
    		robotLogger.register(DriveState.getInstance().getLogger());
    		robotLogger.register(RobotState.getInstance().getLogger());
    		
    		setInitialPose(new Pose());

    		//cameraServer.getInstance().startAutomaticCapture();
    	}
    	catch(Throwable t)
    	{
    		CrashTracker.logThrowableCrash(t);
    		throw t;
    	}
	}
	
	public void setInitialPose (Pose _initialPose){
    	robotState.reset(Timer.getFPGATimestamp(), DriveState.getInstance().getLeftDistanceInches(), DriveState.getInstance().getRightDistanceInches(), _initialPose);
    	System.out.println("InitialPose: " + _initialPose);
    }
    
    public void zeroAllSensors()
    {
    	drive.zeroSensors();
		// mSuperstructure.zeroSensors();
    }
    
    public void stopAll()
    {
    	drive.stop();
		// mSuperstructure.stop();
    }



	/****************************************************************
	 * DISABLED MODE
	 ****************************************************************/

	@Override
	public void disabledInit()
	{
		operationalMode = OperationalMode.DISABLED;
		boolean logToFile = true;
		boolean logToSmartDashboard = true;
		robotLogger.setOutputMode(logToFile, logToSmartDashboard);

		try
		{
			CrashTracker.logDisabledInit();
			if (autoModeExecuter != null)
			{
				autoModeExecuter.stop();
			}
			autoModeExecuter = null;

			stopAll(); // stop all actuators
			loopController.start();

		}
		catch (Throwable t)
		{
			CrashTracker.logThrowableCrash(t);
			throw t;
		}
	}

	@Override
	public void disabledPeriodic()
	{
		try
		{
			stopAll(); // stop all actuators

			System.gc(); // runs garbage collector
		}
		catch (Throwable t)
		{
			CrashTracker.logThrowableCrash(t);
			throw t;
		}
	}



	/****************************************************************
	 * AUTONOMOUS MODE
	 ****************************************************************/

	@Override
	public void autonomousInit() {
    	operationalMode = OperationalMode.AUTONOMOUS;
    	boolean logToFile = true;
    	boolean logToSmartDashboard = true;
    	robotLogger.setOutputMode(logToFile, logToSmartDashboard);

    	try
    	{
    		CrashTracker.logAutoInit();
    		if(autoModeExecuter != null){
    			autoModeExecuter.stop();
    		}
    		autoModeExecuter = null;
    		
			SeriesAction autoSequence;
			autoSequence = SmartDashboardInteractions.autoSequenceBuilder();
			
			autoModeExecuter = new AutoModeExecuter();
			autoModeExecuter.setAutoMode( new RunSeriesActionMode( autoSequence ) );

			setInitialPose( autoModeExecuter.getAutoMode().getInitialPose() );

			autoModeExecuter.start();
    	}
    	catch(Throwable t)
    	{
    		CrashTracker.logThrowableCrash(t);
    		throw t;
    	}
		
	}

	@Override
	public void autonomousPeriodic() {
    	try
    	{
    		
    	}
    	catch (Throwable t)
    	{
    		CrashTracker.logThrowableCrash(t);
    		throw t;
    	}
	}
	
	
	/****************************************************************
	 * TELEOP MODE
	 ****************************************************************/

	@Override
	public void teleopInit(){
		operationalMode = OperationalMode.TELEOP;
		boolean logToFile = true;
		boolean logToSmartDashboard = true;
		robotLogger.setOutputMode(logToFile, logToSmartDashboard);

		try 
		{
			CrashTracker.logTeleopInit();

			// Select joystick control method
			controls = smartDashboardInteractions.getJoystickControlsMode();

			// Configure looper
			loopController.start();

			//gearShifter.setLowGear();
			drive.setOpenLoop(DriveCommand.COAST());

		} 
		catch (Throwable t) 
		{
			CrashTracker.logThrowableCrash(t);
			throw t;
		}
	}
	@Override
	public void teleopPeriodic() {
		try{
			if ((autoModeExecuter == null) || (!autoModeExecuter.getAutoMode().isActive()))
				drive.setOpenLoop(controls.getDriveCommand());
			
			// override operator controls if button board direction is set
			Optional<Double> buttonBoardDirection = buttonBoard.getDirection();
			if (buttonBoardDirection.isPresent())
			{
				if (autoModeExecuter != null)
					autoModeExecuter.stop();	// kill any old commands
				autoModeExecuter = new AutoModeExecuter();
				AutoModeBase autoMode = new PointTurnMode( buttonBoardDirection.get().doubleValue() );
				autoModeExecuter.setAutoMode( autoMode );
				autoModeExecuter.start();
			}
		}
		catch (Throwable t){
			CrashTracker.logThrowableCrash(t);
			throw t;
		}
	}



	/****************************************************************
	 * TEST MODE
	 ****************************************************************/

	@Override
	public void testInit() 
	{
		loopController.start();
	}

	@Override
	public void testPeriodic()
	{
		drive.testDriveSpeedControl();
	}
	
	
	// called after disabledPeriodic, autoPeriodic, and teleopPeriodic 
	@Override
	public void robotPeriodic()
	{
		robotLogger.log();
		//switchableCameraServer.outputToSmartDashboard();
	}


	
	
	private final DataLogger logger = new DataLogger()
    {
        @Override
        public void log()
        {
			put("OperationalMode", operationalMode.getVal());
        }
    };
    
    public DataLogger getLogger() { return logger; }
}

