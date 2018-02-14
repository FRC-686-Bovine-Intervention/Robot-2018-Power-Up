package org.usfirst.frc.team686.robot;

import org.usfirst.frc.team686.robot.lib.util.ConstantsBase;
import org.usfirst.frc.team686.robot.lib.joystick.ArcadeDriveJoystick;
import org.usfirst.frc.team686.robot.lib.joystick.JoystickControlsBase;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SPI;

/**
 * Attribution: adapted from FRC Team 254
 */


/**
 * A list of constants used by the rest of the robot code. This include physics
 * constants as well as constants determined through calibrations.
 */
public class Constants extends ConstantsBase
{
    private static ConstantsBase mInstance = new Constants();	// make sure we call constructor to set all robot-specific constants
    public static ConstantsBase getInstance() { return mInstance; }
	
    public enum RobotSelectionEnum { COMPETITION_BOT, PRACTICE_BOT; }
    public static RobotSelectionEnum kRobotSelection;
	
    public static double kLoopDt = 0.01;
    public static double kDriveWatchdogTimerThreshold = 0.500;    
    public static int kTalonTimeoutMs = 5;	// ms
    public static int kTalonPidIdx = 0;		// 0 for non-cascaded PIDs, 1 for cascaded PIDs
    	
    public static double kNominalBatteryVoltage = 12.0;
    
    
    // Bumpers
    public static double kCenterToFrontBumper;	// position of front bumper with respect to robot center of rotation
    public static double kCenterToRearBumper;	// position of rear bumper with respect to robot center of rotation
    public static double kCenterToSideBumper;	// position of side bumper with respect to robot center of rotation
	public static double kCenterToCornerBumper;

    // Wheels
    public static double kDriveWheelCircumInches;
    public static double kDriveWheelDiameterInches;
    public static double kTrackLengthInches;
    public static double kTrackWidthInches;
    public static double kTrackEffectiveDiameter;
    public static double kTrackScrubFactor;

    // Wheel Encoder
    public static double kQuadEncoderGain ;	// number of drive shaft rotations per encoder shaft rotation
    
    public static int    kQuadEncoderCodesPerRev;
    public static int    kQuadEncoderPulsesPerRev;
    public static double kQuadEncoderStatusFramePeriod = 0.100;	// 100 ms
    
    // CONTROL LOOP GAINS
    
    // PID gains for drive velocity loop (sent to Talon)
    // Units: error is 4*256 counts/rev. Max output is +/- 1023 units.
    public static double kDriveVelocityKp;
    public static double kDriveVelocityKi;
    public static double kDriveVelocityKd;
    public static double kDriveVelocityKf;
    public static int    kDriveVelocityIZone;
    public static double kDriveVelocityRampRate;
    public static int    kDriveVelocityAllowableError;

    // PID gains for drive base lock loop
    // Units: error is 4*256 counts/rev. Max output is +/- 1023 units.
    public static double kDriveBaseLockKp;
    public static double kDriveBaseLockKi;
    public static double kDriveBaseLockKd;
    public static double kDriveBaseLockKf;
    public static int    kDriveBaseLockIZone;
    public static double kDriveBaseLockRampRate;
    public static int    kDriveBaseLockAllowableError;

    // PID gains for constant heading velocity control
    // Units: Error is degrees. Output is inches/second difference to
    // left/right.
    public static double kDriveHeadingVelocityKp;
    public static double kDriveHeadingVelocityKi;
    public static double kDriveHeadingVelocityKd;
    
    // Point Turn constants
    public static double kPointTurnMaxVel; // inches/sec  		
    public static double kPointTurnMaxAccel; // inches/sec^2	
    public static double kPointTurnMinSpeed; // inches/sec 
    public static double kPointTurnCompletionTolerance; 
    
    // Path following constants
    public static double kPathFollowingMaxVel; // inches/sec  		
    public static double kPathFollowingMaxAccel; // inches/sec^2	
    public static double kPathFollowingLookahead ; // inches
    public static double kPathFollowingCompletionTolerance; 
    public static double kCollisionThreshold = 0.5;
    
    // Vision constants
    public static double kCameraPoseX ;	// camera location with respect to robot center of rotation, +X axis is in direction of travel
    public static double kCameraPoseY;	// camera location with respect to robot center of rotation, +Y axis is positive to the left
    public static double kCameraPoseTheta;	// camera angle with respect to robot heading
    
    public static double kVisionMaxVel; // inches/sec  		
    public static double kVisionMaxAccel; // inches/sec^2		
    public static double kTargetWidthInches;
    public static double kPegTargetDistanceThresholdFromBumperInches;		// inches to stop from target, measured from front bumper
    public static double kPegTargetDistanceThresholdFromCameraInches;
    public static double kVisionCompletionTolerance; 
    public static double kVisionMaxDistanceInches;		// ignore targets greater than this distance
    public static double kVisionLookaheadDist;	// inches
    public static double kCameraFOVDegrees;			// Camera Field of View (degrees)
    public static double kCameraHalfFOVRadians;			// Half of Camera Field of View (radians)
    public static double kTangentCameraHalfFOV;
    public static double kCameraLatencySeconds;			// Camera image capturing latency
    public static double kTargetLocationFilterConstant;		// 30 time constants in 1 second
    

    // POWER UP FIELD
    public static double kScaleHeightHigh = 72.0;
    public static double kScaleHeightMed = 	60.0;
    public static double kScaleHeightLow = 	48.0;
    public static double kSwitchHeight = 	10.0;
    public static double kExchangeHeight = 	 2.0;
    public static double kGroundHeight = 	 0.0;

    public static double kCubeClearance = 6.0;

    
    // ELEVATOR    
    public static double kElevatorMinHeightLimit =  0.0;	// stop at min height
    public static double kElevatorMaxHeightLimit = 65.0;	// stop at max height
    
    public static double kElevatorZeroingVelocity = 1;
    public static double kElevatorVelocity = 10;	//30;
    
	public static double kElevatorQuadEncoderGain = 1.0;			// a 3:1 and 4:1 gear stages plus a 24:12 tooth reduction 
	public static double kElevatorQuadEncoderUnitsPerRev = 4096;
	public static double kElevatorGearCircum = 4.538;
	public static double kElevatorEncoderUnitsPerInch = kElevatorQuadEncoderUnitsPerRev / kElevatorGearCircum * kElevatorQuadEncoderGain; 
    
	public static double kElevatorKf = 0.0;
	public static double kElevatorKp = 10;
	public static double KElevatorKd = 0.0;
	public static double KElevatorKi = 0.0;
	
    public static double kMaxElevatorVoltage = 12.0;
    
    
    // ARM BAR
    public static double kArmBarLength = 14.0;
    
    public static double kArmBarUpAngleDeg = 80;	// TODO: fix
    public static double kArmBarDownAngleDeg = -20;	// TODO: quick calculations show -34 is the right number
    public static double kArmBarZeroingVelocity = 30.0;	// in degrees per second
    public static double kArmBarVelocity = 150.0;	// in degrees per second
    
	public static double kArmBarQuadEncoderGain = 81.0 * 2.0;			// two 9:1 gear stages plus a 24:12 tooth reduction 
	public static double kArmBarQuadEncoderUnitsPerRev = 4096;
	public static double kArmBarEncoderUnitsPerDeg = kArmBarQuadEncoderUnitsPerRev / 360.0 * kArmBarQuadEncoderGain; 
	
	public static double kArmBarKf = 0.0;
	public static double kArmBarKp = 0.4;
	public static double kArmBarKd = 0.0;
	public static double kArmBarKi = 0.0;

	public static double kMaxArmBarVoltage = 6.0;	// may be less than 12V battery voltage when testing	
	
	
    // CUBE HEIGHT
    public static double kCubeGrabHeight = 8.5;		// inches above ground where cube is grabbed
       
    
    
    // Do not change anything after this line!
    
    // Motor Controllers
    // (Note that if multiple Talons are dedicated to a mechanism, any sensors are attached to the master)
	public static int kRightMotorMasterTalonId;
	public static int kRightMotorSlave1TalonId;
	public static int kRightMotorSlave2TalonId;
	public static int kElevatorTalonId;
    public static int kLeftMotorMasterTalonId;
	public static int kLeftMotorSlave1TalonId;
	public static int kLeftMotorSlave2TalonId;
	public static int kArmBarTalonId;

    // left motors are inverted
    public static boolean	kLeftMotorInverted;
    public static boolean	kRightMotorInverted;
    public static boolean	kLeftMotorSensorPhase;
    public static boolean	kRightMotorSensorPhase;

	public static int kTalonCurrentLimit;
	
	public static int kHallEffectSensorId;
	

    // Joystick Controls
    public static int kXboxButtonA  = 1;
    public static int kXboxButtonB  = 2;
    public static int kXboxButtonX  = 3;
    public static int kXboxButtonY  = 4;
    public static int kXboxButtonLB = 5;
    public static int kXboxButtonRB = 6;
    
    public static int kXboxLStickXAxis  = 0;
    public static int kXboxLStickYAxis  = 1;
    public static int kXboxLTriggerAxis = 2;
    public static int kXboxRTriggerAxis = 3;
    public static int kXboxRStickXAxis  = 4;
    public static int kXboxRStickYAxis  = 5;
    
    public static int kElevatorScaleButton = kXboxButtonY;
    public static int kElevatorSwitchButton = kXboxButtonB;
    public static int kArmBarButton = kXboxButtonX;

    
        
    //Robot stops when joystick axis < 0.1 and >-0.1
    public static double kDriveDeadzone = 0.2;

    // Relay Ports
    public static int kLedRelayPort = 0;
    
    // Gyro
    public enum GyroSelectionEnum { BNO055, NAVX; }
    public static GyroSelectionEnum GyroSelection = GyroSelectionEnum.NAVX;

	// The I2C port the BNO055 is connected to
    public static final I2C.Port BNO055_PORT = I2C.Port.kOnboard;
    
    // BNO055 accelerometer calibration constants
    // ( -7, -34,  33, -24) - taken 10/14/2016
    // (-13, -53,  18, -24) - taken 10/14/2016
    // (  0, -59,  25, -24) - taken 10/14/2016
    // using average of the above
    public static short kAccelOffsetX =  -7;
    public static short kAccelOffsetY = -53;
    public static short kAccelOffsetZ =   25;
    public static short kAccelRadius  = -24;
    
    // The SPI port the NavX is connected to
    // (see https://www.pdocs.kauailabs.com/navx-mxp/guidance/selecting-an-interface/)
    public static final SPI.Port NAVX_PORT = SPI.Port.kMXP;						// the SPI port has low latency (<0.1 ms)

    public static byte NAVX_UPDATE_RATE = (byte) (1.0 / Constants.kLoopDt);		// the SPI port supports update rates from 4-200 Hz
   
    
    
    
    
    
    
    
    public Constants()
    {
        kRobotSelection = RobotSelectionEnum.COMPETITION_BOT;	// select which robot we are building code for (TODO: make this automatic?)
    	
    	// place robot-specific constants here
    	
    	switch (kRobotSelection)
    	{
    		case COMPETITION_BOT:
    			GyroSelection = GyroSelectionEnum.NAVX;

    			kCenterToFrontBumper = 25.0;	// position of front bumper with respect to robot center of rotation
       		    kCenterToRearBumper = 16.0;	// position of rear bumper with respect to robot center of rotation
       		    kCenterToSideBumper = 17.5;	// position of side bumper with respect to robot center of rotation
       		    

    			kDriveWheelCircumInches = 18.800 * (244.0/241.72);	// empirically corrected over a 20' test run
    		    kTrackLengthInches = 11.500;	// 23.000 counting the omniwheels
    		    kTrackWidthInches = 21.500;
    		    kTrackScrubFactor = 0.5;

    		    // Wheel Encoder
    		    kQuadEncoderGain = 1.0;			// number of drive shaft rotations per encoder shaft rotation
    											// single speed, double reduction encoder is directly coupled to the drive shaft 
    		    kQuadEncoderCodesPerRev = 64;
     		    
    		    // CONTROL LOOP GAINS
    		    double kNominalEncoderPulsePer100ms = 85;		// RPM at a nominal throttle (measured using NI web interface)
    		    double kNominalPercentOutput 		 = 0.4447;	// percent output of motor at above throttle (using NI web interface)
    		    
    		    kDriveVelocityKp = 20.0;
    		    kDriveVelocityKi = 0.01;
    		    kDriveVelocityKd = 500.0;
    		    kDriveVelocityKf = kNominalPercentOutput * 1023.0 / kNominalEncoderPulsePer100ms;
    		    kDriveVelocityIZone = 0;
    		    kDriveVelocityRampRate = 0.0;
    		    kDriveVelocityAllowableError = 0;

    		    // PID gains for drive base lock loop
    		    // Units: error is 4*256 counts/rev. Max output is +/- 1023 units.
    		    kDriveBaseLockKp = 0.5;
    		    kDriveBaseLockKi = 0;
    		    kDriveBaseLockKd = 0;
    		    kDriveBaseLockKf = 0;
    		    kDriveBaseLockIZone = 0;
    		    kDriveBaseLockRampRate = 0;
    		    kDriveBaseLockAllowableError = 10;

    		    // PID gains for constant heading velocity control
    		    // Units: Error is degrees. Output is inches/second difference to
    		    // left/right.
    		    kDriveHeadingVelocityKp = 4.0;
    		    kDriveHeadingVelocityKi = 0.0;
    		    kDriveHeadingVelocityKd = 50.0;
    		    
    		    
    		    // Motor Controllers
    		    // (Note that if multiple Talons are dedicated to a mechanism, any sensors are attached to the master)
    		    kLeftMotorMasterTalonId 	= 1;
    			kLeftMotorSlave1TalonId 	= 2;
    			kElevatorTalonId 			= 3;
    			kRightMotorMasterTalonId 	= 4;
    			kRightMotorSlave1TalonId 	= 5;
    			kArmBarTalonId 				= 6;

    		    // left motors are inverted
    		    kLeftMotorInverted  = false;
    		    kRightMotorInverted = true;
    		    kLeftMotorSensorPhase = false;
    		    kRightMotorSensorPhase = false;
    		    
    			kTalonCurrentLimit = 25;
    			
    			kHallEffectSensorId = 0;

    			break;
    			
    			
    			
    			
    			
    			
    			
    			
    		case PRACTICE_BOT:
    			GyroSelection = GyroSelectionEnum.BNO055;
    			
       		    kCenterToFrontBumper = 19.0;	// position of front bumper with respect to robot center of rotation
    		    kCenterToRearBumper = 19.5;	// position of rear bumper with respect to robot center of rotation
    		    kCenterToSideBumper = 17.5;	// position of side bumper with respect to robot center of rotation

     		    kDriveWheelCircumInches = 13.229;//13.250;
    		    kTrackLengthInches = 25.000;
    		    kTrackWidthInches = 23.000;
    		    kTrackScrubFactor = 0.5;

    		    // Wheel Encoder
    		    kQuadEncoderGain = ( 30.0 / 54.0 ) * ( 12.0 / 36.0 );	// number of drive shaft rotations per encoder shaft rotation
    																						// 54:30 drive shaft --> 3rd stage, 36:12 3rd stage --> encoder shaft     
    		    kQuadEncoderCodesPerRev = 64;
    		    
    		    // CONTROL LOOP GAINS
    		//  kFullThrottleRPM = 4500 * kQuadEncoderGain;	// high gear: measured max RPM using NI web interface
    		    double kFullThrottleRPM = 520;	// low gear: measured max RPM using NI web interface
    		    double kFullThrottleEncoderPulsePer100ms = kFullThrottleRPM / 60.0 * kQuadEncoderStatusFramePeriod * kQuadEncoderPulsesPerRev; 

    		    kDriveVelocityKp = 1.0;
    		    kDriveVelocityKi = 0.001;
    		    kDriveVelocityKd = 6.0;
    		    kDriveVelocityKf = 1023.0 / kFullThrottleEncoderPulsePer100ms;
    		    kDriveVelocityIZone = 0;
    		    kDriveVelocityRampRate = 0.0;
    		    kDriveVelocityAllowableError = 0;

    		    // PID gains for drive base lock loop
    		    // Units: error is 4*256 counts/rev. Max output is +/- 1023 units.
    		    kDriveBaseLockKp = 0.5;
    		    kDriveBaseLockKi = 0;
    		    kDriveBaseLockKd = 0;
    		    kDriveBaseLockKf = 0;
    		    kDriveBaseLockIZone = 0;
    		    kDriveBaseLockRampRate = 0;
    		    kDriveBaseLockAllowableError = 10;

    		    // PID gains for constant heading velocity control
    		    // Units: Error is degrees. Output is inches/second difference to
    		    // left/right.
    		    kDriveHeadingVelocityKp = 4.0;
    		    kDriveHeadingVelocityKi = 0.0;
    		    kDriveHeadingVelocityKd = 50.0;
    		    
    		    
    		    // Motor Controllers
    		    // (Note that if multiple Talons are dedicated to a mechanism, any sensors are attached to the master)
    		    kLeftMotorMasterTalonId 	= 1;
    			kLeftMotorSlave1TalonId 	= 2;
    			kLeftMotorSlave2TalonId 	= 3;
    			kElevatorTalonId 			= 99;

    			kRightMotorMasterTalonId 	= 5;
    			kRightMotorSlave1TalonId 	= 6;
    			kRightMotorSlave2TalonId 	= 7;
    			kArmBarTalonId 				= 99;

    		    // left motors are inverted
    		    kLeftMotorInverted  = true;
    		    kRightMotorInverted = false;
    		    kLeftMotorSensorPhase = true;
    		    kRightMotorSensorPhase = true;
    			
    			kTalonCurrentLimit = 25;
    		    
    		    break;
    	}

    	// calculated constants
    	kCenterToCornerBumper = Math.sqrt(kCenterToRearBumper*kCenterToRearBumper + kCenterToSideBumper*kCenterToSideBumper);
    	
	    kDriveWheelDiameterInches = kDriveWheelCircumInches / Math.PI;
	    kTrackEffectiveDiameter = (kTrackWidthInches * kTrackWidthInches + kTrackLengthInches * kTrackLengthInches) / kTrackWidthInches;

	    kQuadEncoderPulsesPerRev = (int)(4*kQuadEncoderCodesPerRev / kQuadEncoderGain);    
	    
	    // Point Turn constants
	    kPointTurnMaxVel    = 80.0; // inches/sec  		
	    kPointTurnMaxAccel  = 200.0; // inches/sec^2	
	    kPointTurnMinSpeed  = 20.0; // inches/sec 
	    kPointTurnCompletionTolerance = 1.0 * (Math.PI/180.0); 
	    
	    // Path following constants
	    kPathFollowingMaxVel    = 80.0; // inches/sec  		
	    kPathFollowingMaxAccel  = 48.0; // inches/sec^2	
	    kPathFollowingLookahead = 24.0; // inches
	    kPathFollowingCompletionTolerance = 1.0; 
	    
	    // Vision constants
	    kCameraPoseX     = +7.25;	// camera location with respect to robot center of rotation, +X axis is in direction of travel
	    kCameraPoseY     =     0;	// camera location with respect to robot center of rotation, +Y axis is positive to the left
	    kCameraPoseTheta =     0;	// camera angle with respect to robot heading
	    
	    kVisionMaxVel    = 60.0; // inches/sec  		
	    kVisionMaxAccel  = 48.0; // inches/sec^2		
	    kTargetWidthInches = 10.25;
	    kPegTargetDistanceThresholdFromBumperInches = 18;		// inches to stop from target, measured from front bumper
	    kPegTargetDistanceThresholdFromCameraInches = kCenterToFrontBumper - kCameraPoseX + kPegTargetDistanceThresholdFromBumperInches;
	    kVisionCompletionTolerance = 1.0; 
	    kVisionMaxDistanceInches = 240;		// ignore targets greater than this distance
	    kVisionLookaheadDist = 24.0;	// inches
	    kCameraFOVDegrees = 42.5;			// Camera Field of View (degrees)
	    kCameraHalfFOVRadians = kCameraFOVDegrees/2.0 * Math.PI/180.0;			// Half of Camera Field of View (radians)
	    kTangentCameraHalfFOV = Math.tan(kCameraHalfFOVRadians);
	    kCameraLatencySeconds = 0.240;			// Camera image capturing latency
	    kTargetLocationFilterConstant = (30.0 * kLoopDt);		// 30 time constants in 1 second
	    
    }
}
