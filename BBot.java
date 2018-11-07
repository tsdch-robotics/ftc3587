package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * This is NOT an opmode. This file defines all the hardware on the robot
 * and some common helper functions (stop motors, reset encoders, etc.)
*/
public class BBot {
    // class variables for all hardware
    public DcMotor DriveFrontLeft;
    public DcMotor DriveFrontRight;
    public DcMotor DriveBackLeft;
    public DcMotor DriveBackRight;

    // game element manipulation
    public DcMotor ArmElevator;
    public Servo ArmHook;


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
        ArmElevator = hwMap.dcMotor.get("ArmElevator");

        // initialize servos
        ArmHook = hwMap.servo.get("ArmHook");


        // initialize sensors
        //GyroCenter = hwMap.gyroSensor.get("GyroCenter");
        //JewelCS = hwMap.colorSensor.get("JewelCS");

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

    public void setDriveMotors(double FrontL, double FrontR, double BackL, double BackR) {
        DriveFrontLeft.setPower(FrontL);
        DriveFrontRight.setPower(FrontR);
        DriveBackLeft.setPower(BackL);
        DriveBackRight.setPower(BackR);
    }

    public void stopAllMotors() {
        DriveFrontLeft.setPower(0.0);
        DriveFrontRight.setPower(0.0);
        DriveBackLeft.setPower(0.0);
        DriveBackRight.setPower(0.0);
        ArmElevator.setPower(0.0);
    }

    public void resetAllEncoders() {
        // reset drive encoders
        DriveFrontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveBackLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveBackRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        DriveFrontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DriveFrontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DriveBackLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        DriveBackRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // extra encoders
        ArmElevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        ArmElevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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

