package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TradOp", group="BBot")
public class TradOP extends OpMode {
    private BBot robot = new BBot();   // Use robot's hardware
    public ElapsedTime runtime = new ElapsedTime();

    double hookPosition = 0.4;

    @Override
    public void init() {
        /*
         * Use the hardwareMap to get the dc motors and servos by name. Note
         * that the names of the devices must match the names used when you
         * configured your robot and created the configuration file.
         */
        robot.init(hardwareMap);
    }

    private String elevatorStatus = "";

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

        // intake mechanism
        double intakePower = -gamepad2.right_stick_y;
        robot.IntakeCR.setPower(intakePower);

        // elevator
        boolean Elevator_up = gamepad2.right_bumper;
        boolean Elevator_down = (gamepad2.right_trigger > 0.1);


        if(Elevator_up) { // if right bumper is held arm elevator is set to 1
            robot.Lift.setPower(1);
            elevatorStatus = "moving up";
        }
        else if(Elevator_down) { // if right trigger is pressed elevator is set to -1
            robot.Lift.setPower(-1);
            elevatorStatus = "moving down";
        }
        else { // if neither are pressed elevator is set to 0
            robot.Lift.setPower(0);
            elevatorStatus = "idle";
        }

        // hook
        boolean HookUp = gamepad2.dpad_up;
        boolean HookDown = gamepad2.dpad_down;
        boolean HookOverride = gamepad2.x;
        boolean HookCradle = gamepad2.b;
        String hookStatus = "";

        if (HookUp) {
            hookPosition = (hookPosition < 1) ? hookPosition + 0.05 : 1;
            hookStatus = "moving up";
        }
        else if (HookDown) {
            hookPosition = (hookPosition > 0.4) ? hookPosition - 0.05 : 0.4;
            hookStatus = "moving down";
        }
        else if(HookOverride) {
            hookPosition = 0.25;
            hookStatus = "override";
        }
        else if(HookCradle)
        {
            hookPosition = .6;
            hookStatus = "cradle";
        }
        else {
            hookStatus = "idle";
        }
        robot.ArmHook.setPosition(hookPosition);

        // driver data
        telemetry.addData("Elevator", elevatorStatus);
        telemetry.addData("Hook", "%s, %f", hookStatus, hookPosition);
        telemetry.addData("Intake", intakePower);
    }
}