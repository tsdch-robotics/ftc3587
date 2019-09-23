package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TradOp", group="MiniProgrammingBot")
public class TradOP extends OpMode {
    private MiniProgrammingBot robot = new MiniProgrammingBot();   // Use robot's hardware
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
        telemetry.addData("Status", "Ready!");
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

        //if(gamepad1.b) gyro.resetHeading();

        //telemetry.addData("Heading", gyro.globalHeading);
    }
}