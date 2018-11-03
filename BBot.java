package org.firstinspires.ftc.teamcode;

import android.provider.Telephony;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This is NOT an opmode.
 * For future changes, consult comments below.
 *
         */
public class BBot {
    //Public Motors
    public DcMotor DriveFrontLeft;
    public DcMotor DriveFrontRight;
    public DcMotor DriveBackLeft;
    public DcMotor DriveBackRight;
    public Servo Servo1;
    public DcMotor BasketArm;




    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public BBot() { }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap; // reference to hardware map


        // initialize motors
        DriveFrontLeft = hwMap.dcMotor.get("DriveFrontLeft");
        DriveBackLeft = hwMap.dcMotor.get("DriveBackLeft");
        DriveFrontRight = hwMap.dcMotor.get("DriveFrontRight");
        DriveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        DriveBackRight = hwMap.dcMotor.get("DriveBackRight");
        DriveBackRight.setDirection(DcMotor.Direction.REVERSE);

        // initialize servos
        Servo1 = hwMap.servo.get("Servo1");

        //HangArm = hwMap.dcMotor.get("HangArm");
        //ParticleArm = hwMap.dcMotor.get("ParticleArm");

        //initialize servos
        //ABC = hwMap.servo.get("ABC");
        //REscalatorDown = hwMap.servo.get("REscalatorDown");


        //JewelDown = hwMap.servo.get("JewelDown");

        // initialize sensors
        //GyroCenter = hwMap.gyroSensor.get("GyroCenter");
        //JewelCS = hwMap.colorSensor.get("JewelCS");



//        ABC.setPosition(0.5);
        //REscalatorDown.setPosition(0.5);

        //Set all servos to open position

        stopAllMotors();
    }

//    public void turn(double degreesToTurn) {
//        double targetHeading = 0;
//
//        // stop robot
//        DriveFrontLeft.setPower(0.0);
//        DriveFrontRight.setPower(0.0);
//        DriveBackLeft.setPower(0.0);
//        DriveBackRight.setPower(0.0);
//
        // reset heading
        //GyroCenter.calibrate();
        //while(GyroCenter.isCalibrating()); // wait to finish calibrating

        // determine which direction to turn
//        if (degreesToTurn > 0){
//            targetHeading = degreesToTurn;
//        }
//        if (degreesToTurn < 0) {
//            targetHeading = 360.0 + degreesToTurn;
//        }


        // execute turn
//         if (targetHeading > 180) {
//             DriveFrontLeft.setPower(1.0);
//             DriveFrontRight.setPower(1.0);
//             DriveBackLeft.setPower(1.0);
//             DriveBackRight.setPower(1.0);
//             while (GyroCenter.getHeading() > targetHeading); // wait until robot reaches heading
//             DriveFrontLeft.setPower(0.0);
//             DriveFrontRight.setPower(0.0);
//             DriveBackLeft.setPower(0.0);
//             DriveBackRight.setPower(0.0);
//         }
//
//         else {
//             DriveFrontLeft.setPower(-1.0);
//             DriveFrontRight.setPower(-1.0);
//             DriveBackLeft.setPower(-1.0);
//             DriveBackRight.setPower(-1.0);
//             while (GyroCenter.getHeading() < targetHeading); // wait until robot reaches heading
//             DriveFrontLeft.setPower(0.0);
//             DriveFrontRight.setPower(0.0);
//             DriveBackLeft.setPower(0.0);
//             DriveBackRight.setPower(0.0);
//         }
//    }

    public void stopAllMotors() {
        DriveFrontLeft.setPower(0.0);
        DriveFrontRight.setPower(0.0);
        DriveBackLeft.setPower(0.0);
        DriveBackRight.setPower(0.0);

        //HangArm.setPower(0.0);
        //ParticleArm.setPower(0.0);
    }

    public void resetAllEncoders() {
        DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DriveFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DriveBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DriveBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        /*HangArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        HangArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ParticleArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ParticleArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        */
    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     * @throws InterruptedException
     */
    public void waitForTick(long periodMs) throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}

