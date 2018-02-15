package org.usfirst.frc.team686.robot.auto.modes;

import org.usfirst.frc.team686.robot.lib.util.Pose;
import org.usfirst.frc.team686.robot.lib.util.Util;
import org.usfirst.frc.team686.robot.lib.util.Vector2d;

import java.util.Optional;

import org.usfirst.frc.team686.robot.Constants;

/**
 * Interface that holds all the field measurements 
 */

//https://www.chiefdelphi.com/forums/attachment.php?attachmentid=22877&d=1516118855

public class FieldDimensions 
{
	// dimensions of field components
	public static double kFieldLengthX = 648;
	public static double kAllianceStationLengthY = 264;
	public static double kScalePlatformLengthX = 82;
	public static double kPowerCubeZoneLengthX = 42;

	public static double kSwitchLengthX = 56;
	public static double kSwitchLengthY = 153.5;
	
	public static double kScaleLengthX = 48;
	public static double kScaleLengthY = 180;
	
	
	// field distances with Center Start as origin
	public static double kAutoLineFromCenterStartDistX = 120;
	
	public static double kSwitchCenterFromCenterStartDistX = 140;
	public static double kLeftSwitchFromCenterStartDistX = kSwitchCenterFromCenterStartDistX + (kSwitchLengthX/2); // to center of switch
	public static double kLeftSwitchFromCenterStartDistY = kSwitchLengthY/2; // to y end of switch
	public static double kRightSwitchFromCenterStartDistX = kLeftSwitchFromCenterStartDistX;
	public static double kRightSwitchFromCenterStartDistY = -kLeftSwitchFromCenterStartDistY;
	
	
	public static double kScaleFromCenterStartDistX = kFieldLengthX/2;
	public static double kLeftScaleFromCenterStartDistX = kScaleFromCenterStartDistX; // to center of scale
	public static double kLeftScaleFromCenterStartDistY = kScaleLengthY/2; // to y end of scale
	public static double kRightScaleFromCenterStartDistX = kLeftScaleFromCenterStartDistX;
	public static double kRightScaleFromCenterStartDistY = -kLeftScaleFromCenterStartDistY;
	
	
	public static double kScaleLeftEndXFromCenterStartDistX = 299.65;
	public static double kSwitchRightEndXFromCenterStartDistX = 196;
	public static double kPlatFormLeftEndXFromSwitchCenterDistX = (kFieldLengthX/2) - kSwitchCenterFromCenterStartDistX - (kScalePlatformLengthX/2);
	public static double kScaleFromSwitchOffsetY = (kScaleLengthY/2) - (kSwitchLengthY/2);
	
	public static double kPowerCubeZoneFromCenterStartDistX = 98;
	
	// get turn angles
	public static double kTurnPositionOffsetY = 36;
	
	public static double kSwitchTurnPositionOffsetX = (kSwitchLengthX/2);// + kPowerCubeZoneLengthX + Constants.kCenterToRearBumper;
	public static double kSwitchTurnAngle =  Math.toRadians(-10);//Math.atan(kTurnPositionOffsetY/kSwitchTurnPositionOffsetX);
	public static double kLeftSwitchTurnAngle = (Math.PI + kSwitchTurnAngle);
	public static double kRightSwitchTurnAngle = -kLeftSwitchTurnAngle;


	public static double kScaleTurnAngle = Math.toRadians(10);
	public static double kLeftScaleTurnAngle = (Math.PI + kScaleTurnAngle);
	public static double kRightScaleTurnAngle = -kLeftScaleTurnAngle;
	
	
	public static double kBackupDistY = 20;
	
	public static double kCollisionAvoidanceOffsetY = 1;
	
	// get poses
	public static Pose getCenterStartPose() { return new Pose(Constants.kCenterToRearBumper, 0, 0); }
	public static Pose getLeftStartPose() { return new Pose(Constants.kCenterToRearBumper, (kAllianceStationLengthY/2) - Constants.kCenterToSideBumper, 0); }
	public static Pose getRightStartPose() { return new Pose(Constants.kCenterToRearBumper, -((kAllianceStationLengthY/2) - Constants.kCenterToSideBumper), 0); }
	public static Pose getExchangeStartPose() { return new Pose(0, 0, 0); }	// temporary-- TODO: replace!
	public static Pose getOtherStartPose() { return new Pose(0, 0, 0); }	// temporary-- TODO: replace!
	
	
	public static Pose getLeftSwitchPose() { return new Pose(kLeftSwitchFromCenterStartDistX, kLeftSwitchFromCenterStartDistY, kLeftSwitchTurnAngle); }
	public static Pose getRightSwitchPose() { return new Pose(kRightSwitchFromCenterStartDistX, kRightSwitchFromCenterStartDistY, kRightSwitchTurnAngle); }
	
	public static double getSwitchTurnPositionX() { return kSwitchCenterFromCenterStartDistX - kSwitchTurnPositionOffsetX; }
	public static double getSwitchTurnOffsetY() { return kTurnPositionOffsetY; }
	

	public static Pose getLeftScalePose() { return new Pose(kLeftScaleFromCenterStartDistX, kLeftScaleFromCenterStartDistY, kLeftScaleTurnAngle); }
	public static Pose getRightScalePose() { return new Pose(kRightScaleFromCenterStartDistX, kRightScaleFromCenterStartDistY, kRightScaleTurnAngle); }
	
	public static double getScaleTurnPositionX() { return kSwitchCenterFromCenterStartDistX - kSwitchTurnPositionOffsetX; }
	public static double getScaleTurnOffsetY() { return kTurnPositionOffsetY + kScaleFromSwitchOffsetY; }
	
	public static double getScaleTurnFromSwitchPositionX() { return ((kPlatFormLeftEndXFromSwitchCenterDistX/2) + Constants.kCenterToSideBumper) + kSwitchCenterFromCenterStartDistX; }
	
	public static double getPowerCubeZoneFromCenterStartDistX() { return kPowerCubeZoneFromCenterStartDistX; }
	
	public static Vector2d getBackupPosition() { return new Vector2d(0, kBackupDistY); }
	
	public static double getCollisionAvoidanceOffsetY() { return kCollisionAvoidanceOffsetY; }
	
}
