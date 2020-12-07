package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;


@Autonomous(name="WobbleBlue", group="ChampBot")
//@Disabled


public class WobbleBlue extends LinearOpMode {

    ChampBot robot = new ChampBot() {
    };
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();
//forward for 5 inches
        robot.DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = 3.14 * 3.94;
        double rotationsNeeded = 5 / circumference;
        int encoderDrivingTarget = (int) (rotationsNeeded * 1140);
        robot.DriveFrontLeft.setTargetPosition(encoderDrivingTarget);
        robot.DriveBackLeft.setTargetPosition(encoderDrivingTarget);
        robot.DriveFrontRight.setTargetPosition(encoderDrivingTarget);
        robot.DriveBackRight.setTargetPosition(encoderDrivingTarget);

        robot.DriveFrontLeft.setPower(0.5);
        robot.DriveFrontRight.setPower(0.5);
        robot.DriveBackLeft.setPower(0.5);
        robot.DriveBackRight.setPower(0.5);

        robot.DriveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.DriveBackLeft.isBusy()) {
            telemetry.addData("Path", "forward for 5 inches");
            telemetry.update();
        }

        robot.DriveFrontLeft.setPower(0);
        robot.DriveFrontRight.setPower(0);
        robot.DriveBackLeft.setPower(0);
        robot.DriveBackRight.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);


// turn right
        robot.DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rotationsNeeded = 19.21 / circumference;
        encoderDrivingTarget = (int) (rotationsNeeded * 1140);
        robot.DriveFrontLeft.setTargetPosition(encoderDrivingTarget);
        robot.DriveBackLeft.setTargetPosition(encoderDrivingTarget);
        robot.DriveFrontRight.setTargetPosition(encoderDrivingTarget);
        robot.DriveBackRight.setTargetPosition(encoderDrivingTarget);

        robot.DriveFrontLeft.setPower(0.5);
        robot.DriveFrontRight.setPower(-.5);
        robot.DriveBackLeft.setPower(0.5);
        robot.DriveBackRight.setPower(-.5);

        robot.DriveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.DriveBackLeft.isBusy()) {
            telemetry.addData("Path", "turn right");
            telemetry.update();
        }

        robot.DriveFrontLeft.setPower(0);
        robot.DriveFrontRight.setPower(0);
        robot.DriveBackLeft.setPower(0);
        robot.DriveBackRight.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);

//forward for 52 inches
        robot.DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rotationsNeeded = 52 / circumference;
        encoderDrivingTarget = (int) (rotationsNeeded * 1140);
        robot.DriveFrontLeft.setTargetPosition(encoderDrivingTarget);
        robot.DriveBackLeft.setTargetPosition(encoderDrivingTarget);
        robot.DriveFrontRight.setTargetPosition(encoderDrivingTarget);
        robot.DriveBackRight.setTargetPosition(encoderDrivingTarget);

        robot.DriveFrontLeft.setPower(0.5);
        robot.DriveFrontRight.setPower(0.5);
        robot.DriveBackLeft.setPower(0.5);
        robot.DriveBackRight.setPower(0.5);

        robot.DriveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.DriveBackLeft.isBusy()) {
            telemetry.addData("Path", "forward for 52 inches");
            telemetry.update();
        }

        robot.DriveFrontLeft.setPower(0);
        robot.DriveFrontRight.setPower(0);
        robot.DriveBackLeft.setPower(0);
        robot.DriveBackRight.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);

//back for 45 inches
        robot.DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rotationsNeeded = 45 / circumference;
        encoderDrivingTarget = (int) (rotationsNeeded * 1140);
        robot.DriveFrontLeft.setTargetPosition(encoderDrivingTarget);
        robot.DriveBackLeft.setTargetPosition(encoderDrivingTarget);
        robot.DriveFrontRight.setTargetPosition(encoderDrivingTarget);
        robot.DriveBackRight.setTargetPosition(encoderDrivingTarget);

        robot.DriveFrontLeft.setPower(-0.5);
        robot.DriveFrontRight.setPower(-0.5);
        robot.DriveBackLeft.setPower(-0.5);
        robot.DriveBackRight.setPower(-0.5);

        robot.DriveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.DriveBackLeft.isBusy()) {
            telemetry.addData("Path", "back for 45 inches");
            telemetry.update();
        }

        robot.DriveFrontLeft.setPower(0);
        robot.DriveFrontRight.setPower(0);
        robot.DriveBackLeft.setPower(0);
        robot.DriveBackRight.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
    }
}