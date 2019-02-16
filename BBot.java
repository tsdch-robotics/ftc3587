package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
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
    public DcMotor Lift;
    public DcMotor ArmInOut;
    public DcMotor ArmUpDown;
    public Servo StupidStick;

    // Intake
    public CRServo Brotation;
    public Servo PhatServo;

    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    // constant encoder counts
    public final double REVCoreHexEncoder = 288.0;
    public final double REVHD401Encoder = 1120.0;

    public BBot() { }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap; // reference to hardware map

        // initialize motors
        DriveFrontLeft = hwMap.dcMotor.get("DriveFrontLeft");
        DriveBackLeft = hwMap.dcMotor.get("DriveBackLeft");
        DriveFrontRight = hwMap.dcMotor.get("DriveFrontRight");
        DriveBackRight = hwMap.dcMotor.get("DriveBackRight");
        DriveFrontRight.setDirection(DcMotor.Direction.REVERSE);
        DriveBackRight.setDirection(DcMotor.Direction.REVERSE);

        Lift = hwMap.dcMotor.get("Lift");
        Lift.setDirection(DcMotor.Direction.REVERSE);

        ArmInOut = hwMap.dcMotor.get("ArmInOut");
        ArmUpDown = hwMap.dcMotor.get("ArmUpDown");

        // initialize servos
        StupidStick = hwMap.servo.get("StupidStick");
        Brotation = hwMap.crservo.get("Brotation");
        PhatServo = hwMap.servo.get("PhatServo");

        // initialize sensors
        //GyroCenter = hwMap.gyroSensor.get("GyroCenter");
        //JewelCS = hwMap.colorSensor.get("JewelCS");

        stopAllMotors();
        initServos();
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
        Lift.setPower(0.0);
        ArmInOut.setPower(0.0);
        ArmUpDown.setPower(0.0);
    }

    public void initServos() {
        StupidStick.setPosition(0.75);
        Brotation.setPower(0.0);
        PhatServo.setPosition(0.0);
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
        //ArmElevator.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
       // ArmElevator.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal) {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) index = -index;

        // index cannot exceed size of array minus 1.
        if (index > 16) index = 16;

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
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
        long remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}

