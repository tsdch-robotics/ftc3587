package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;


@Autonomous(name="test2", group="ChampBot")
//@Disabled


public class test2 extends LinearOpMode {

    ChampBot robot = new ChampBot() {
    };
    static final int MOTOR_TICK_COUNT = 1120;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        waitForStart();

        robot.DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double circumference = 3.14 * 3.94;
        double rotationsNeeded = 18 / circumference;
        int encoderDrivingTarget = (int)(rotationsNeeded*1120);
        robot.DriveFrontLeft.setTargetPosition(encoderDrivingTarget);
        robot.DriveBackLeft.setTargetPosition(encoderDrivingTarget);
        robot.DriveFrontRight.setTargetPosition(encoderDrivingTarget);
        robot.DriveBackRight.setTargetPosition(encoderDrivingTarget);


        robot.DriveFrontLeft.setPower(.5);
        robot.DriveFrontRight.setPower(.5);
        robot.DriveBackLeft.setPower(.5);
        robot.DriveBackRight.setPower(.5);

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
    }
}

        /*FrontleftMotor = hardwareMap.dcMotor.get("DriveFrontLeft");
        FrontrightMotor = hardwareMap.dcMotor.get("DriveFrontRight");
        BackleftMotor = hardwareMap.dcMotor.get("DriveBackLeft");


        // reset encoder counts kept by motors.
        FrontleftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FrontrightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // set motors to run forward for 5000 encoder counts.
        FrontleftMotor.setTargetPosition(100);
        FrontrightMotor.setTargetPosition(100);

        // set motors to run to target encoder position and stop with brakes on.
        FrontleftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontrightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telemetry.addData("Mode", "waiting");
        telemetry.update();

        // wait for start button.

        waitForStart();

        telemetry.addData("Mode", "running");
        telemetry.update();

        // set both motors to 25% power. Movement will start. Sign of power is
        // ignored as sign of target encoder position controls direction when
        // running to position.

        FrontleftMotor.setPower(0.25);
        FrontrightMotor.setPower(0.25);

        // wait while opmode is active and left motor is busy running to position.

        while (opModeIsActive() && FrontleftMotor.isBusy())   //leftMotor.getCurrentPosition() < leftMotor.getTargetPosition())
        {
            telemetry.addData("encoder-fwd-left", FrontleftMotor.getCurrentPosition() + "  busy=" + FrontleftMotor.isBusy());
            telemetry.addData("encoder-fwd-right", FrontrightMotor.getCurrentPosition() + "  busy=" + FrontrightMotor.isBusy());
            telemetry.update();
            idle();
        }

        // set motor power to zero to turn off motors. The motors stop on their own but
        // power is still applied so we turn off the power.

        FrontleftMotor.setPower(0.0);
        FrontrightMotor.setPower(0.0);

        // wait 5 sec to you can observe the final encoder position.

        resetStartTime();

    }

         */