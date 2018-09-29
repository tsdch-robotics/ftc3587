package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

public class Bot2827 {

    public DcMotorController DCMC1;
    public DcMotorController DCMC2;
    public DcMotorController DCMC3;
    public ServoController SMC1;

    public DcMotor motorFrontRight;
    public DcMotor motorFrontLeft;
    public DcMotor motorBackLeft;
    public DcMotor motorBackRight;
    public DcMotor lift;
    public Servo servoRight;
    public Servo servoLeft;


   // public DcMotor Lift;
    //public Servo Servo_Left;
   // public Servo Servo_Right
    //public DcMotorController control;

    HardwareMap hwMap = null;

    public void init(HardwareMap ahwMap){
        hwMap = ahwMap;
        //back_left = Hwmap.dcMotor.get("RearLeftDrive");
        //back_right = Hwmap.dcMotor.get("RearRightDrive");
        //front_left = Hwmap.dcMotor.get("FrontLeftDrive");
        //front_right = Hwmap.dcMotor.get("FrontRightDrive");
        //control = Hwmap.dcMotorController.get("control");

        DCMC1 = hwMap.dcMotorController.get("DCMC1");
        DCMC2 = hwMap.dcMotorController.get("DCMC2");
        DCMC3 = hwMap.dcMotorController.get("DCMC3");
        SMC1 = hwMap.servoController.get("SMC1");



        motorFrontRight = hwMap.dcMotor.get("motor front right");
        motorFrontLeft = hwMap.dcMotor.get("motor front left");
        motorBackLeft = hwMap.dcMotor.get("motor back left");
        motorBackRight = hwMap.dcMotor.get("motor back right");
        servoRight = hwMap.servo.get("servo right");
        servoLeft = hwMap.servo.get("servo left");
        lift = hwMap.dcMotor.get("lift");

        //back_right.setPower(0.0);
        //back_left.setPower(0.0);
        //front_right.setPower(0.0);
        //front_left.setPower(0.0);

    }
    //This function sets all motor encoders to the same position. You need to set a fixed position.
    //Position should be an int, and is the number of rotations times the motor ticks per rotation.
    //Brad should know how to find this, then store it somewhere.
    //use: bot.setposition(integer);
    public int curpos() {
        return motorBackLeft.getCurrentPosition();
    }

    public void setposition(int pos){
        motorBackLeft.setTargetPosition(pos);
        motorBackRight.setTargetPosition(pos);
        motorFrontLeft.setTargetPosition(pos);
        motorFrontRight.setTargetPosition(pos);
    }

    //When changing encoder position this is necessary to change the value correctly.
    //Run this everytime the position changes
    //Stops and resets all encoders and then set the mode to RUN_TO_POSITION
    //use: bot.reset();
    public void reset(){
        motorBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


}