package org.firstinspires.ftc.teamcode;
import android.graphics.Color;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DeviceInterfaceModule;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.ar.pl.SensorController;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This is NOT an opmode.
 * For future changes, consult comments below.
 *
         */
public class NicoleBot {
    // controllers
    public DcMotorController DCMC1;
    public DcMotorController DCMC2;
    public DcMotorController DCMC0;
    public DeviceInterfaceModule DIM1;
    public ServoController SC0;

    // actual hardware
    public DcMotor FrontMotorLeft;
    public DcMotor BackMotorLeft;
    public DcMotor FrontMotorRight;
    public DcMotor BackMotorRight;
    public DcMotor NicoleElevator;
    public Servo RightNicoleClaw;
    public Servo LeftNicoleClaw;
    public GyroSensor GyroCenter;

    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    public NicoleBot() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap; // reference to hardware map

        // initialize controllers
        DCMC0 = hwMap.dcMotorController.get("Motor Controller 0");
        DCMC1 = hwMap.dcMotorController.get("Motor Controller 1");
        DCMC2 = hwMap.dcMotorController.get("Motor Controller 2");
        DIM1 = hwMap.deviceInterfaceModule.get("Device Interface Module 1");
        SC0 = hwMap.servoController.get("Servo Controller 0");

        // initialize motors
        FrontMotorLeft = hwMap.dcMotor.get("FrontMotorLeft");
        BackMotorLeft = hwMap.dcMotor.get("BackMotorLeft");
        FrontMotorRight = hwMap.dcMotor.get("FrontMotorRight");
        BackMotorRight = hwMap.dcMotor.get("BackMotorRight");
        NicoleElevator = hwMap.dcMotor.get("NicoleElevator");

        //initialize servos
        RightNicoleClaw = hwMap.servo.get("RightNicoleClaw");
        LeftNicoleClaw = hwMap.servo.get("LeftNicoleClaw");

        // initialize sensors
        GyroCenter = hwMap.gyroSensor.get("GyroCenter");

        // Set all motors to zero power
        FrontMotorLeft.setPower(0.0);
        BackMotorLeft.setPower(0.0);
        FrontMotorRight.setPower(0.0);
        BackMotorRight.setPower(0.0);
        NicoleElevator.setPower(0.0);

        //Set all servos to open position
        RightNicoleClaw.setPosition(0.0);
        LeftNicoleClaw.setPosition(1.0);
    }

    public void turn(double degreesToTurn) {
        double targetHeading = 0;

        // stop robot
        FrontMotorLeft.setPower(0.0);
        BackMotorLeft.setPower(0.0);
        FrontMotorRight.setPower(0.0);
        BackMotorRight.setPower(0.0);
        NicoleElevator.setPower(0.0);

        // reset heading
        GyroCenter.calibrate();
        while(GyroCenter.isCalibrating()); // wait to finish calibrating

        // determine which direction to turn
        if (degreesToTurn > 0){
            targetHeading = degreesToTurn;
        }
        if (degreesToTurn < 0) {
            targetHeading = 360.0 + degreesToTurn;
        }


        // execute turn
         if (targetHeading > 180) {
             FrontMotorLeft.setPower(1.0);
             BackMotorLeft.setPower(1.0);
             FrontMotorRight.setPower(1.0);
             BackMotorRight.setPower(1.0);
             while (GyroCenter.getHeading() > targetHeading); // wait until robot reaches heading
             FrontMotorLeft.setPower(0.0);
             BackMotorLeft.setPower(0.0);
             FrontMotorRight.setPower(0.0);
             BackMotorRight.setPower(0.0);
         }

         else {
             FrontMotorLeft.setPower(-1.0);
             BackMotorLeft.setPower(-1.0);
             FrontMotorRight.setPower(-1.0);
             BackMotorRight.setPower(-1.0);
             while (GyroCenter.getHeading() < targetHeading); // wait until robot reaches heading
             FrontMotorLeft.setPower(0.0);
             BackMotorLeft.setPower(0.0);
             FrontMotorRight.setPower(0.0);
             BackMotorRight.setPower(0.0);
         }
    }

    public void stopAllMotors() {
        FrontMotorLeft.setPower(0.0);
        BackMotorLeft.setPower(0.0);
        FrontMotorRight.setPower(0.0);
        BackMotorRight.setPower(0.0);
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

