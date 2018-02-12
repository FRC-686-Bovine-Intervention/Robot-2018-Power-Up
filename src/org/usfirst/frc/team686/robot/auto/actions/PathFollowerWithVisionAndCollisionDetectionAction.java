package org.usfirst.frc.team686.robot.auto.actions;

import org.usfirst.frc.team686.robot.Constants;
import org.usfirst.frc.team686.robot.lib.sensors.BNO055;
import org.usfirst.frc.team686.robot.lib.sensors.GyroBase;
import org.usfirst.frc.team686.robot.lib.sensors.NavX;
import org.usfirst.frc.team686.robot.lib.util.DataLogger;
import org.usfirst.frc.team686.robot.lib.util.Path;
import org.usfirst.frc.team686.robot.lib.util.PathFollowerWithVisionDriveController;
import org.usfirst.frc.team686.robot.lib.util.PathFollowerWithVisionDriveController.PathVisionState;

import edu.wpi.first.wpilibj.Timer;

/**
 * Action for following a path defined by a Path object.
 * 
 * Serially configures a PathFollower object to follow each path 
 */
public class PathFollowerWithVisionAndCollisionDetectionAction implements Action 
{
	PathFollowerWithVisionDriveController driveCtrl;
	public static NavX gyro;
	double lastWorldLinearAccelerationX;
	double lastWorldLinearAccelerationY;
	double startTime; //seconds

    public PathFollowerWithVisionAndCollisionDetectionAction(Path _path) 
    {
    	driveCtrl = new PathFollowerWithVisionDriveController(_path, PathVisionState.PATH_FOLLOWING);
    	gyro = NavX.getInstance();
    	lastWorldLinearAccelerationX = gyro.getWorldLinearAccelerationX();
    	lastWorldLinearAccelerationY = gyro.getWorldLinearAccelerationY();
    	startTime = Timer.getFPGATimestamp();
    }

    public PathFollowerWithVisionDriveController getDriveController() { return driveCtrl; }

    @Override
    public void start() 
    {
		System.out.println("Starting PathFollowerWithVisionAction");
		driveCtrl.start();
    }


    @Override
    public void update() 
    {
    	driveCtrl.update();
	}	
	
	
    @Override
    public boolean isFinished() 
    {
    	
    	if( (Timer.getFPGATimestamp() - startTime) > 1 ) { return true; }
    	
        boolean collisionDetected = false;
        
        double currWorldLinearAccelerationX = gyro.getWorldLinearAccelerationX();
        double currentJerkX = currWorldLinearAccelerationX - lastWorldLinearAccelerationX;
        lastWorldLinearAccelerationX = currWorldLinearAccelerationX;
        double currWorldLinearAccelerationY = gyro.getWorldLinearAccelerationY();
        double currentJerkY = currWorldLinearAccelerationY - lastWorldLinearAccelerationY;
        lastWorldLinearAccelerationY = currWorldLinearAccelerationY;
        
        if ( ( Math.abs(currentJerkX) > Constants.kCollisionThreshold ) ||
             ( Math.abs(currentJerkY) > Constants.kCollisionThreshold) ) {
            collisionDetected = true;
        }
        
    	
    	return driveCtrl.isFinished() || collisionDetected ;
    }

    @Override
    public void done() 
    {
		System.out.println("Finished PathFollowerWithVisionAction");
		// cleanup code, if any
		driveCtrl.done();
    }

 
    
    
    public DataLogger getLogger() { return driveCtrl.getLogger(); }
}