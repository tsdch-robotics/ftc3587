package org.firstinspires.ftc.teamcode;

/**
 * Created by 2827 on 9/20/2017.
 **/
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
@TeleOp(name="Tele",group = "2827")
public class Teleop extends OpMode {
    Bot2827 bot = new Bot2827();
    double Rposition = 0.5;
    double Lposition = 0.6;
    //double R2position = 0.5;
    //  double L2position = 0.6;
    double MotorSpeed = 1;
    double Aposition1 = 0.1;
    double Aposition2 = 0.1;
    boolean closeServos = false;
    boolean openServos = false;
    double servoPosition = 0;
    boolean armUp = false;
    boolean armDown = false;
    double  armPower = 0;
    boolean y = false;

    @Override
    public void init() {
        bot.init(hardwareMap);
        telemetry.addData("Good day", "Bespeckled Cephalapods");
        updateTelemetry(telemetry);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void loop() {
        // drivetrain control
        float gamepad1LeftY = gamepad1.right_stick_y;
        float gamepad1RightY = gamepad1.left_stick_y;

        float FrontLeft = gamepad1LeftY;
        float FrontRight = gamepad1RightY;
        float BackRight = gamepad1RightY;
        float BackLeft = -gamepad1LeftY;
        armUp = gamepad1.a;
        armDown = gamepad1.b;

        closeServos = gamepad1.left_bumper;
        openServos = gamepad1.right_bumper;

        if(openServos){
            servoPosition = servoPosition - .1;
        }
        if(closeServos){
            servoPosition = servoPosition + .1;
        }

        if(armUp == true){
            armPower = 1.0;
            y = true;
        }
        else if(armDown){
            armPower = -1.0;
        }
        else{
            armPower = 0;
        }

        bot.lift.setPower(armPower);

        bot.servoLeft.setPosition(servoPosition);
        bot.servoRight.setPosition(-servoPosition);

        FrontRight = Range.clip(FrontRight, -1, 1);
        FrontLeft = Range.clip(FrontLeft, -1, 1);
        BackLeft = Range.clip(BackLeft, -1, 1);
        BackRight = Range.clip(BackRight, -1, 1);


        MotorSpeed = Range.clip(MotorSpeed, 0.25, 1);

        bot.motorFrontRight.setPower(FrontRight);
        bot.motorFrontLeft.setPower(-FrontLeft);
        bot.motorBackLeft.setPower(BackLeft);
        bot.motorBackRight.setPower(BackRight);


        telemetry.addData("f left pwr", "front left  pwr: " + String.format("%.2f", FrontLeft));
        telemetry.addData("f right pwr", "front right pwr: " + String.format("%.2f", FrontRight));
        telemetry.addData("b right pwr", "back right pwr: " + String.format("%.2f", BackRight));
        telemetry.addData("b left pwr", "back left pwr: " + String.format("%.2f", BackLeft));
        telemetry.addData("BackLeft", bot.motorBackLeft.getCurrentPosition());
        telemetry.addData("BackRight", bot.motorBackRight.getCurrentPosition());
        telemetry.addData("FrontLeft", bot.motorFrontLeft.getCurrentPosition());
        telemetry.addData("FrontRight", bot.motorFrontRight.getCurrentPosition());
        //telemetry.addData("servos open", "servos open: " + String.format("%.2f", openServos));
        //telemetry.addData("servos close", "servos close: " + String.format("%.2f", closeServos));
        telemetry.addData("ServoLeft", bot.servoLeft.getPosition());
        telemetry.addData("ServoRight", bot.servoRight.getPosition());
        //telemetry.addData("arm up", "arm up: " + String.format("%.2f", armUp));
        //telemetry.addData("arm down", "arm down: " + String.format("%.2f", armDown));
        telemetry.addData("arm power", "arm power: " + String.format("%.2f", armPower));
        telemetry.addData("x:",gamepad1.x);
        telemetry.addData("y:",y);

        updateTelemetry(telemetry);
    }

    @Override
    public void stop() {
    }

    double scaleInput(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};
        double dScale = 0.0;
        int index = (int) (dVal * 16.0);

        if (index < 0) index = -index;
        if (index > 16) index = 16;


        if (dVal < 0) {
            dScale = -scaleArray[index];

        } else {
            dScale = scaleArray[index];
        }

        return dScale;
    }
}
