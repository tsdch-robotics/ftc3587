package org.firstinspires.ftc.teamcode;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Tank Drive", group="ProgrammingBot")
public class TankDriveTeleop extends OpMode {
    ProgrammingBot robot = new ProgrammingBot();   // Use robot's hardware
    public ElapsedTime runtime = new ElapsedTime();
    Gyro gyro;

    // robot state variables
    private boolean SlowMo = false;

    // control state variables
    private boolean DiscreteButtonReleased = true;

    @Override
    public void init() {
        robot.init(hardwareMap);
        //gyro = new Gyro(robot.hwMap, "imu"); // specifically initialize the gyro
        //gyro.start();
        telemetry.addData("Status", "Ready!");
    }

    @Override
    public void loop() {
        // tank drive: each stick controls one side of the robot
        // dpad for strafing left/right
        float DriveLeftCommand = -gamepad1.left_stick_y;
        float DriveRightCommand = -gamepad1.right_stick_y;
        boolean LeftStrafe = gamepad1.dpad_left;
        boolean RightStrafe = gamepad1.dpad_right;
        boolean IntakeForward = gamepad1.right_trigger > 0.5;
        boolean IntakeReverse = gamepad1.right_bumper;

        // state variables for toggle controls


        // apply scaling factor if slow-mo is enabled
        float DriveLeftPower = SlowMo ? (DriveLeftCommand / 3) : DriveLeftCommand;
        float DriveRightPower = SlowMo ? (DriveRightCommand / 3) : DriveRightCommand;

        if (RightStrafe) {
            // to right strafe, right motors towards each other, left motors away from each other
            robot.DriveFrontLeft.setPower(1);
            robot.DriveFrontRight.setPower(-1);
            robot.DriveBackLeft.setPower(-1);
            robot.DriveBackRight.setPower(1);
        } else if (LeftStrafe) {
            // opposite of right strafe
            robot.DriveFrontLeft.setPower(-1);
            robot.DriveFrontRight.setPower(1);
            robot.DriveBackLeft.setPower(1);
            robot.DriveBackRight.setPower(-1);
        } else {
            // write the values to the motors
            robot.DriveBackLeft.setPower(DriveLeftPower);
            robot.DriveFrontLeft.setPower(DriveLeftPower);
            robot.DriveFrontRight.setPower(DriveRightPower);
            robot.DriveBackRight.setPower(DriveRightPower);
        }

        if (IntakeForward) {
            robot.IntakeLeft.setPower(1.0);
            robot.IntakeRight.setPower(1.0);
        }
        else if (IntakeReverse) {
            robot.IntakeLeft.setPower(-1.0);
            robot.IntakeRight.setPower(-1.0);
        }
        else {
            robot.IntakeLeft.setPower(0.0);
            robot.IntakeRight.setPower(0.0);
        }

        if (gamepad1.b) gyro.resetHeading();

        // control slow-mo
        if (gamepad1.y && DiscreteButtonReleased) { // only listen to the first time the button is pressed
            SlowMo = !SlowMo; // toggle
            DiscreteButtonReleased = false; // ignore future button presses until it's been released
        }
        else if (!gamepad1.y) {
            // button's been released, start listening to button presses again
            DiscreteButtonReleased = true;
        }

        // driver data
        telemetry.addData("Left Right", String.format("%.2f", DriveLeftPower) + " " + String.format("%.2f", DriveRightPower));
        telemetry.addData("DBR", DiscreteButtonReleased);
        telemetry.addData("gamepad", gamepad1.y);
        telemetry.addData("Slow-mo", SlowMo);
        telemetry.addData("Runtime", runtime);
        //telemetry.addData("Heading", gyro.globalHeading);
    }
}
