package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

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

        // Arm
        robot.Arm.setPower(-gamepad2.left_stick_y);

        //Wrist
        boolean Up = gamepad2.right_bumper;
        boolean Down = (gamepad2.right_trigger > 0.1);

        String WristStatus = "";

        if(Up) { // if right bumper is held arm elevator is set to 1
            robot.Wrist1.setPosition(1.0);
            robot.Wrist2.setPosition(1.0);
            WristStatus = "moving up";
        }
        else if(Down) { // if right trigger is pressed elevator is set to -1
            robot.Wrist1.setPosition(0.0);
            robot.Wrist2.setPosition(0.0);
            WristStatus = "moving down";
        }

        //Claw
        String ClawStatus = "";

        if (gamepad2.a) {
            robot.Claw.setPosition(0.3);
            ClawStatus = "closed";
        }
        else if (gamepad2.b) {
            robot.Claw.setPosition(1.0);
            ClawStatus = "open";
        }

        telemetry.addData("Wrist: ", WristStatus);
        telemetry.addData("Claw: ", ClawStatus);
        telemetry.update();
    }
}