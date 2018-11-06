package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="TradOp", group="BBot")
public class TradOP extends OpMode {

    BBot robot = new BBot();   // Use robot's hardware

    public ElapsedTime runtime = new ElapsedTime();
    public TradOP() { }

    @Override
    public void init() {
        /*
         * Use the hardwareMap to get the dc motors and servos by name. Note
         * that the names of the devices must match the names used when you
         * configured your robot and created the configuration file.
         */
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        // drivetrain
        // left stick controls direction - forward/back, left/right
        // right stick X controls rotation - CW/CCW
        double r = Math.hypot(gamepad1.left_stick_x, gamepad1.left_stick_y);
        double robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
        double rightX = gamepad1.right_stick_x;
        final double v1 = r * Math.cos(robotAngle) + rightX;
        final double v2 = r * Math.sin(robotAngle) - rightX;
        final double v3 = r * Math.sin(robotAngle) + rightX;
        final double v4 = r * Math.cos(robotAngle) - rightX;

        robot.DriveFrontLeft.setPower(v1);
        robot.DriveFrontRight.setPower(v2);
        robot.DriveBackLeft.setPower(v3);
        robot.DriveBackRight.setPower(v4);

        // intake mechanism variables
        boolean Elevator_up = gamepad1.right_bumper;
        float Elevator_down = gamepad1.right_trigger;
        //telemetry variable
        String elevator_status;

        //if right bumper is held arm elevator is set to 1
        if(Elevator_up == true)
        {
            robot.ArmElevator.setPower(1);
            elevator_status = "up";
        }
        //if right trigger is pressed elevator is set to -1
        else if(Elevator_down >= .1)
        {
            robot.ArmElevator.setPower(-1);
            elevator_status = "down";
        }
        //if neither are pressed elevator is set to 0
        else
        {
            robot.ArmElevator.setPower(0);
            elevator_status="off";
        }
        // driver data
        telemetry.addData("FrontMotorLeft = " , v1);
        telemetry.addData("FrontMotorRight =", v2);
        telemetry.addData("BackMotorLeft =", v3);
        telemetry.addData("BackMotorRight =", v4);
        telemetry.addData("elevator", elevator_status);


    }
//test
    @Override
    public void stop() { }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */

}



