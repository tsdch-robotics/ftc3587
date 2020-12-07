package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;


@Autonomous(name="test2", group="ChampBot")
//@Disabled


public class test1 extends LinearOpMode {

    ChampBot robot = new ChampBot() {
    };
    static final int MOTOR_TICK_COUNT = 1120;
    int inches = 0;
    double LeftPower = 0;
    double RightPower = 0;

    @Override
    public void runOpMode() {
        DriveRobot(18,.5,.5);
    }

    private void DriveRobot(int inches, double LeftPower, double RightPower) {
        robot.init(hardwareMap);

        waitForStart();

        robot.DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = 3.14 * 3.94;
        double rotationsNeeded = inches / circumference;
        int encoderDrivingTarget = (int)(rotationsNeeded*1120);
        robot.DriveFrontLeft.setTargetPosition(encoderDrivingTarget);
        robot.DriveBackLeft.setTargetPosition(encoderDrivingTarget);
        robot.DriveFrontRight.setTargetPosition(encoderDrivingTarget);
        robot.DriveBackRight.setTargetPosition(encoderDrivingTarget);


        robot.DriveFrontLeft.setPower(LeftPower);
        robot.DriveFrontRight.setPower(RightPower);
        robot.DriveBackLeft.setPower(LeftPower);
        robot.DriveBackRight.setPower(RightPower);

        robot.DriveFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.DriveBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (robot.DriveFrontLeft.isBusy()) {
            telemetry.addData("Path", "Driving 18 inches");
            telemetry.update();
        }


        robot.DriveFrontLeft.setPower(0);
        robot.DriveFrontRight.setPower(0);
        robot.DriveBackLeft.setPower(0);
        robot.DriveBackRight.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(500);
    }
}