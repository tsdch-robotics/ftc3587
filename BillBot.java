package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * This is NOT an opmode. This file defines all the hardware on the robot
 * and some common helper functions (stop motors, reset encoders, etc.)
 */
public class BillBot {
    // class variables for all hardware
    public DcMotor Motor1;

    public Servo Servo6;

    public Servo Servo1;

    public CRServo CRServo1;

    public Servo Servo2;

    public Servo Servo3;

    public Servo Servo5;
    }

    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    // constant encoder counts
    public final double REVCoreHexEncoder = 288.0;
    public final double REVHD401Encoder = 1120.0;

    public BillBot() { }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap; // reference to hardware map

        // initialize motors
        Motor1 = hwMap.dcMotor.get("Stalin");

        // initialize servos
        Servo1 = hwMap.servo.get("bro");

        Servo2 = hwMap.servo.get("Agaard");

        Servo3 = hwMap.servo.get("YOLO");
        
        Servo5 = hwMap.servo.get("Jarrett");
        Servo6 = hwMap.servo.get("RJ");

        CRServo1 = hwMap.crservo.get("Jesus");


        // initialize sensors


        stopAllMotors();
        initServos();
    }

    public void setDriveMotors(double FrontL, double FrontR, double BackL, double BackR) {

    }

    public void setArmUpDownMotors(double ArmUpDown) {

    }

    public void stopAllMotors() {

    }

    public void initServos() {

    }
    public void resetAllEncoders() {

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