package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotorController;

/*
 * WoodyBot: defines hardware on the robot.
 * This is NOT an opmode. DO NOT put code that controls motors or servos here.
 *
 * multiple authors, February 2018
 */

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class PocketBot {
    /* define all hardware on robot with global variables */
    public DcMotorController DCMC0;

    // motors
    public DcMotor MotorLeft;
    public DcMotor MotorRight;

    // servos

    // sensors

    // internal variables (DO NOT MODIFY)
    HardwareMap hwMap = null;
    protected ElapsedTime period = new ElapsedTime();
    final String vKey = "AbFBF+7/////AAAAGa/gIXOlBktXs2sS6yoBjiIuh1aNum98eoDbvnjL19QRg5cvOdwMqBPOqfpp1EBPRmxEsInZKXFnwsNZendSBGEmdkf2nnSY3kTzHUXvr9VzNknBFPKHG1VVfKNLqscbUkiFVj8ubGv0xgl09aTZFg1qc+QkBkpNEZBHoIzCzK+M4qhRkdhq0glhmX8H/AdEeJG22S3PPylBXG03ZZZAtCozcN5PBIrvkZg+hg66vbfPzoAzQZCifsfzmT3LHAiiNvX69DBmfllexEBSxuqDID2y8toMFWx/4D4N+55hO7QcQc2qhLBMSwqKRfvGNDlc6ELcIuROkUPA5hXX/GJyrARwqc4vI3xvHMNBTFWp/Zkz";

    public PocketBot() { }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        hwMap = ahwMap; // reference to hardware map

        DCMC0 = hwMap.dcMotorController.get("Motor Controller 0");

        // initialize drive motors
        // +1 moves robot forward
        // -1 moves robot backward
        MotorLeft = hwMap.dcMotor.get("FrontMotorLeft");
        MotorLeft.setDirection(DcMotor.Direction.REVERSE);
        MotorRight = hwMap.dcMotor.get("FrontMotorRight");
        // initialize other motors

        // initialize servos

        // initialize sensors

        //set motor power to 0
    }


    /*
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     * @throws InterruptedException
     */

    public void waitForTick(long periodMs) throws InterruptedException {
        long remaining = periodMs - (long) period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0) Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}