package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp(name="Tank Drive", group="ProgrammingBot")
public class TankDriveTeleop extends OpMode {
    ProgrammingBot robot = new ProgrammingBot();   // Use robot's hardware
    Gyro gyro;

    /*
     * These state variables are placed in the class to prevent them from being reinitialized
     * every time by loop() and wiping out the state we want to maintain.
    */
    // robot state variables
    private boolean SlowMo = false;

    // control state variables
    private boolean DiscreteButtonReleased = true;

    // arm variables
    private double GrabberPos = 0.0;
    private double TurnerPosition = 0.0;

    @Override
    public void init() {
        robot.init(hardwareMap);
        //  gyro = new Gyro(robot.hwMap, "imu"); // specifically initialize the gyro
        //  gyro.start();
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
        boolean IntakeIn = gamepad1.right_trigger > 0.5;
        boolean IntakeOut = gamepad1.right_bumper;
        boolean SpinServoOut = false;

        // apply scaling factor if slow-mo is enabled
        float DriveLeftPower = SlowMo ? (DriveLeftCommand / 3) : DriveLeftCommand;
        float DriveRightPower = SlowMo ? (DriveRightCommand / 3) : DriveRightCommand;

        if (RightStrafe) robot.strafeRight(1);
        else if (LeftStrafe) robot.strafeLeft(1);
        else {
            // if not strafing, write the values to the motors
            robot.DriveBackLeft.setPower(DriveLeftPower);
            robot.DriveFrontLeft.setPower(DriveLeftPower);
            robot.DriveFrontRight.setPower(DriveRightPower);
            robot.DriveBackRight.setPower(DriveRightPower);
        }

//        if (IntakeOut) {
//            robot.IntakeLeft.setPower(1.0);
//            robot.IntakeRight.setPower(1.0);
//        }
//        else if (IntakeIn) {
//            robot.IntakeLeft.setPower(-1.0);
//            robot.IntakeRight.setPower(-1.0);
//        }
//        else {
//            robot.IntakeLeft.setPower(0.0);
//            robot.IntakeRight.setPower(0.0);
//        }
//
//        //arm
//        if (gamepad2.right_trigger > 0.5) {
//            robot.LiftArm.setPower(-0.5);
//        } else if (gamepad2.right_bumper) {
//            robot.LiftArm.setPower(0.5);
//        } else {
//            robot.LiftArm.setPower(0.0);
//        }
//
//        if(gamepad2.a && !SpinServoOut) {
//            if(GrabberPos == 0) GrabberPos = 1;
//            else GrabberPos = 0;
//            SpinServoOut = true;
//        } else if(! gamepad2.a) SpinServoOut = false;
//
//        if (gamepad2.x) {
//            TurnerPosition = 0;
//        } else if(gamepad2.y) {
//            TurnerPosition = 1;
//        }
//
//        robot.GrabServo.setPower(GrabberPos);
//        robot.SpinningServo.setPosition(TurnerPosition);


        //if (gamepad1.b) gyro.resetHeading();

        // control slow-mo
        if (gamepad1.y && DiscreteButtonReleased) { // only listen to the first time the button is pressed
            SlowMo = !SlowMo; // toggle
            DiscreteButtonReleased = false; // ignore future button presses until it's been released
        }
        else if (!gamepad1.y) {
            // button's been released, start listening to button presses again
            DiscreteButtonReleased = true;
        }

        // driver dat
        telemetry.addData("Left Right", String.format("%.2f", DriveLeftPower) + " " + String.format("%.2f", DriveRightPower));
        telemetry.addData("Slow-mo", SlowMo);
//        telemetry.addData("Block in intake", robot.IntakeTouch.isPressed());
        //telemetry.addData("Heading", gyro.globalHeading);
    }
}
