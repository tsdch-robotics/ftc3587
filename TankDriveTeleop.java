package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

// garbage gyro imports


@TeleOp(name="Tank Drive", group="MiniProgrammingBot")
public class TankDriveTeleop extends OpMode {
    MiniProgrammingBot robot = new MiniProgrammingBot();   // Use robot's hardware
    public ElapsedTime runtime = new ElapsedTime();
    Gyro gyro;

    @Override
    public void init() {
        robot.init(hardwareMap);
        gyro = new Gyro(robot.hwMap, "imu"); // specifically initialize the gyro
        gyro.start();
        telemetry.addData("Status", "Ready!");
    }

    @Override
    public void loop() {
        // tank drive: each stick controls one side of the robot
        // dpad for strafing left/right
        float DriveLeftPower = -gamepad1.left_stick_y;
        float DriveRightPower = -gamepad1.right_stick_y;
        boolean LeftStrafe = gamepad1.dpad_left;
        boolean RightStrafe = gamepad1.dpad_right;

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

        if(gamepad1.b) gyro.resetHeading();


        // driver data
        telemetry.addData("Left Right", String.format("%.2f", DriveLeftPower) + " " + String.format("%.2f", DriveRightPower));
        telemetry.addData("Heading", gyro.globalHeading);
        telemetry.update();
    }
}
