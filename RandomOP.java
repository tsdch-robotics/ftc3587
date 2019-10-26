package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="RandomOP", group="MiniProgrammingBot")
public class RandomOP extends OpMode {
    private MiniProgrammingBot robot = new MiniProgrammingBot();   // Use robot's hardware
    public ElapsedTime runtime = new ElapsedTime();
    private double GrabberPosition = 0.0;
    private double TurnerPosition = 0.0;

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

        robot.DriveBackLeft.setPower(gamepad1.left_stick_y);
        robot.DriveBackRight.setPower(gamepad1.right_stick_y);

        // Arm components
        if (gamepad1.right_trigger > 0.5) {
            robot.ArmUpDown.setPower(-0.5);
        } else if (gamepad1.right_bumper) {
            robot.ArmUpDown.setPower(0.5);
        } else {
            robot.ArmUpDown.setPower(0.0);
        }

        if (gamepad1.a) {
            GrabberPosition = 0.0;
        } else if (gamepad1.b) {
            GrabberPosition = 1.0;
        }

        if (gamepad1.x) {
            TurnerPosition = 0.0;
        } else if(gamepad1.y) {
            TurnerPosition = 1.0;
        }

        robot.Grabber.setPosition(GrabberPosition);
        robot.Turner.setPosition(TurnerPosition);

        //if(gamepad1.b) gyro.resetHeading();

        //telemetry.addData("Heading", gyro.globalHeading);

        telemetry.addData("Grabber", GrabberPosition);
        telemetry.addData("Turner", TurnerPosition);
    }
}