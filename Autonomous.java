package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.hardware.HardWare;

@Autonomous(name = "Kick-off Sample Autonomous")
public class Autonomous extends LinearOpMode {
	@Override
	public void runOpMode() throws InterruptedException{
		robot.init(this.hardwareMap);
		waitForStart();
		//moveforward 6 inches
		moveInches(6);
		waveServoHand();
	}
	public void waveServoHand(){
		//an example of how to wave servo back and forth 4 times.
		for (int i = 0; i < 4; i++) {
			//make servo move to first position
			robot.armServo.setPosition(.25);
			//wait for servo to reach position
			sleep(1000);
			//make servo move to second position
			robot.armServo.setPosition(.75);
			//wait for servo to reach position
			sleep(1000)
		}
	}
	public void moveInches(double inches){
		//calculate the target position
		int ticks = (int) (inches * robot.TICKS_PER_INCH);

		//set the target position for each motor and start moving
		robot.leftDrive.setTargetPosition(robot.leftDrive.getCurrentPosition() + ticks);
		robot.rightDrive.setTargetPosition(robot.leftDrive.getCurrentPosition() + ticks);
		robot.leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		robot.rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
		robot.leftDrive.setPower(.5);
		robot.rightDrive.setPower(.5);
		//display the motors current position while they are moving
		while (opModeIsActive() && (robot.leftDrive.isBusy() && robot.rightDrive.isBusy())){
			telemetry.addData("Current Left Position", robot.leftDrive.getCurrentPosition());
			telemetry.update();
			telemetry.addData("Current Right Position", robot.rightDrive.getCurrentPosition());
			telemetry.update();
		}
		//stop the motors and revert the motors to run using encoders
		robot.leftDrive.setPower(0);
		robot.leftDrive.setMode(DcModtor.RunMode.RUN_USING_ENCODER);
		robot.rightDrive.setPower(0);
		robot.rightDrive.setMode(DcModtor.RunMode.RUN_USING_ENCODER);
	}
	//an example to turn the robot by 90 degrees
	public void turnByAngle(double turnAngle){
		double startAngle = robot.getAngle();
		double targetAngle = AngleUnit.normalizeDegrees(startAngle + turnAngle);
		//determine which direction the robot needs to turn
		int turnDirection = (turnAngle >= 0) ? 1 : -1;
		//set the wheels to turn the correct direction
		robot.leftDrive.setPower(-0.25 * turnDirection);
		robot.rightDrive.setPower(0.25 * turnDirection);
		double currentAngle = robot.getAngle();
		if (turnDirection == 1) {
			//If turning counter-clockwise: update currentAngle until it is at or less than the targetAngle
			while (AngleUnit.normalizedDegrees(currentAngle - targetAngle) < 0 && opModeIsActive()) {
				currentAngle = robot.getAngle();
			}
		}else{
			// if turning clockwise:update currentAngle until it is at or less than targetAngle
			while (AngleUnit.normalizedDegrees(currentAngle-targetAngle)>0 && opModeIsActive()){
				currentAngle = robot.getAngle();
			}
		}
		//stop the motors once we hit the targetAngle
		robot.leftDrive.setPower(0);
		robot.rightDrive.setPower(0);
	}
}
