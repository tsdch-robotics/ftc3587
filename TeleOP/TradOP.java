package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ChampBot;

@TeleOp(name="TradOp", group="ChampBot")
public class TradOP extends OpMode {
    private ChampBot robot = new ChampBot();   // Use robot's hardware
    public ElapsedTime runtime = new ElapsedTime();
    //Gyro gyro;

    @Override
    public void init() {
        /*
         * Use the hardwareMap to get the dc motors and servos by name. Note
         * that the names of the devices must match the names used when you
         * configured your robot and created the configuration file.
         */
        robot.init(hardwareMap);
        //gyro = new Gyro(robot.hwMap, "imu"); // specifically initialize the gyro
        //gyro.start();
        //telemetry.addData("Status", "Ready!");
    }

    @Override
    public void loop() {
        // drivetrain
        // left stick controls direction - forward/back, strafing left/right
        // right stick X controls rotation - CW/CCW
        double r = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y);
        double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = -gamepad1.right_stick_x;
        final double v1 = r * Math.cos(robotAngle) - rightX;
        final double v2 = r * Math.sin(robotAngle) + rightX;
        final double v3 = r * Math.sin(robotAngle) - rightX;
        final double v4 = r * Math.cos(robotAngle) + rightX;

        robot.DriveFrontLeft.setPower(v1);
        robot.DriveFrontRight.setPower(v2);
        robot.DriveBackLeft.setPower(v3);
        robot.DriveBackRight.setPower(v4);

        // Lift
        robot.Lift.setPower(-gamepad2.left_stick_y / 2);

        // Arm

        String WristStatus = "";
        String ClawStatus = "";

        if(gamepad2.right_bumper) {
            robot.Arm.setPosition(1.0); // in
            WristStatus = "moving in";
        }
        else if(gamepad2.right_trigger > 0.1) {
            robot.Arm.setPosition(0.0); // out
            WristStatus = "moving out";
        }

        if (gamepad2.a) {
            robot.Claw.setPosition(0.0);
            ClawStatus = "closed";
        }
        else if (gamepad2.b) {
            robot.Claw.setPosition(1.0);
            ClawStatus = "open";
        }

        //platform
        if (gamepad1.b) { //up
            robot.PlatformServo.setPosition(0.0);
        }
        else if (gamepad1.a) { //down
            robot.PlatformServo.setPosition(1.0);
        }

        if (gamepad1.x) { //lock
            robot.LockServo.setPosition(1.0);
        }
        else if (gamepad1.y) {
            robot.LockServo.setPosition(0.0);
        }

        //slow-mo
        if (gamepad1.right_bumper) {
            robot.DriveFrontLeft.setPower(v1/2);
            robot.DriveFrontRight.setPower(v2/2);
            robot.DriveBackLeft.setPower(v3/2);
            robot.DriveBackRight.setPower(v4/2);
        }

        telemetry.addData("Wrist: ", WristStatus);
        telemetry.addData("Claw: ", ClawStatus);
        telemetry.update();
    }
}