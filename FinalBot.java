             /**These are the imported libraries, use these exact commands */
package org.firstinspires.ftc.teamcode;
import android.graphics.Color;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.ar.pl.SensorController;

/**
 * This is NOT an opmode.
 * For future changes, consult comments below.
 *
 */
public class FinalBot {
    // define all hardware on robot,
    public DcMotorController Front;
    public DcMotorController Back;
    //public DcMotorController Fire;
    public ServoController Servos;
    public DeviceInterfaceModule Detect;
    //public ServoController BaconPress;

    public DcMotor FrontMotorLeft;
    public DcMotor FrontMotorRight;
    public DcMotor BackMotorLeft;
    public DcMotor BackMotorRight;
    public ColorSensor Right;
    public Servo ArmL;
    public Servo ArmR;
    //public ColorSensor Left;
    //public DcMotor FiringMotor;
    //public OpticalDistanceSensor GodsEye;
    //public Servo Pickup;
    //public Servo Beacon;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    public FinalBot(){ }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap; // reference to hardware map

        // initialize controllers - motor and servo
        Front = hwMap.dcMotorController.get("Motor 2");
        Back = hwMap.dcMotorController.get("Motor 1");
        //Fire = hwMap.dcMotorController.get("Motor 3");
        Detect = hwMap.deviceInterfaceModule.get("Device");
        Servos = hwMap.servoController.get("Servos");
        // initialize motors
        FrontMotorLeft = hwMap.dcMotor.get("FrontMotorLeft");
        FrontMotorRight = hwMap.dcMotor.get("FrontMotorRight");
        BackMotorLeft = hwMap.dcMotor.get("BackMotorLeft");
        BackMotorRight = hwMap.dcMotor.get("BackMotorRight");
        //FiringMotor = hwMap.dcMotor.get("FiringMotor");
        Right = hwMap.colorSensor.get("Right");
        //Left = hwMap.colorSensor.get("Left");
        //FiringMotor.setDirection(DcMotor.Direction.REVERSE);
        //GodsEye = hwMap.opticalDistanceSensor.get("ODS 1");
        //Pickup = hwMap.servo.get("Pickup");
        ArmL= hwMap.servo.get("ArmL");
        ArmR = hwMap.servo.get("ArmR");
        //Beacon.setDirection(Servo.Direction.REVERSE);

         // Set all motors to zero power
        FrontMotorLeft.setPower(0.0);
        FrontMotorRight.setPower(0.0);
        BackMotorLeft.setPower(0.0);
        BackMotorRight.setPower(0.0);
        //FiringMotor.setPower(0.0);

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

