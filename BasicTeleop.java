package org.firstinspires.ftc.teamcode;

/*
 * BasicTeleop: driver control software used in matches.
 * Almost all code that you need to write will be in the loop() method.
 *
 * multiple authors, February 2018
 */

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="OutReach op", group="WoodyBot")
public class BasicTeleop extends OpMode {
    private WoodyBot robot = new WoodyBot();   // Use robot's hardware
    private ElapsedTime runtime = new ElapsedTime();

    // code runs ONCE when driver hits INIT
    @Override
    public void init() {
        // Initialize the hardware variables. The init() method does all the work here
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting
        telemetry.addData("Say", "I like potatoes");    //
        updateTelemetry(telemetry);

        // disable the encoders since the robot will be under driver control
        robot.FrontMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.FrontMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.BackMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.BackMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    // code runs REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() { }

    // code runs ONCE when the driver hits PLAY
    @Override
    public void start() {

    }

    // code runs REPEATEDLY when driver hits play
    // put driver controls (drive motors, servos, etc.) HERE
    @Override
    public void loop() {
        double ThrottleRight = gamepad1.right_stick_y;
        double ThrottleLeft = gamepad1.left_stick_y;
        boolean elevatorup = gamepad1.right_bumper;
        boolean elevatordown = gamepad1.left_bumper;
        robot.FrontMotorLeft.setPower(ThrottleLeft);
        robot.FrontMotorRight.setPower(ThrottleRight);
        robot.BackMotorLeft.setPower(ThrottleLeft);
        robot.BackMotorRight.setPower(ThrottleRight);
        if(elevatorup==true)
        {
            robot.Elevator1.setPower(1);
        }
        if(elevatordown==true)
        {
            robot.Elevator1.setPower(-1);
        }
        updateTelemetry(telemetry);
    }
}