package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TradOp", group="BBot")
public class TradOP extends OpMode {
    private BBot robot = new BBot();   // Use robot's hardware
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

    private String LiftStatus = "";
    private String AVS = "";
    private String AHS = "";

    @Override
    public void loop() {
        // drivetrain
        // left stick controls direction - forward/back, strafing left/right
        // right stick X controls rotation - CW/CCW
        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x;
        final double v1 = r * Math.cos(robotAngle) - rightX;
        final double v2 = r * Math.sin(robotAngle) + rightX;
        final double v3 = r * Math.sin(robotAngle) - rightX;
        final double v4 = r * Math.cos(robotAngle) + rightX;

        robot.DriveFrontLeft.setPower(v1);
        robot.DriveFrontRight.setPower(v2);
        robot.DriveBackLeft.setPower(v3);
        robot.DriveBackRight.setPower(v4);


        // elevator
        boolean Lift_up = gamepad1.right_bumper;
        boolean Lift_down = (gamepad1.right_trigger > 0.1);

        if(Lift_up) { // if right bumper is held arm elevator is set to 1
            robot.Lift.setPower(1);
            LiftStatus = "moving up";
        }
        else if(Lift_down) { // if right trigger is pressed elevator is set to -1
            robot.Lift.setPower(-1);
            LiftStatus = "moving down";  
        }
        else { // if neither are pressed elevator is set to 0
            robot.Lift.setPower(0);
            LiftStatus = "idle";
        }

        // arm
        boolean Arm_Up = gamepad2.right_bumper;
        boolean Arm_Down = (gamepad2.right_trigger > 0.1);

        if (Arm_Up) {
            robot.ArmUpDown.setPower(1.0);
            AVS = "up";
        } else if(Arm_Down) {
            robot.ArmUpDown.setPower(-1.0);
            AVS = "down";
        } else {
            robot.ArmUpDown.setPower(0.0);
            AVS = "idle";
        }

        boolean Arm_Out = gamepad2.dpad_up;
        boolean Arm_In = gamepad2.dpad_down;

        if (Arm_Out) {
            robot.ArmInOut.setPower(1.0);
            AHS = "out";
        } else if(Arm_In) {
            robot.ArmInOut.setPower(-1.0);
            AHS = "in";
        } else {
            robot.ArmInOut.setPower(0.0);
            AHS = "idle";
        }

        // driver data
        telemetry.addData("Lift", LiftStatus);
        telemetry.addData("Arm", AVS);
        telemetry.addData("Arm", AHS);
        //telemetry.addData("Gyro Directions", gyro.globalHeading);
    }
}