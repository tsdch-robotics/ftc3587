/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.RobotLog;

@Autonomous(name="Basic", group="BBot")
public class BasicAuto extends LinearOpMode {
    ProgrammingBot robot = new ProgrammingBot();   // Use robot's hardware
    Gyro gyro;

    private enum States { // states for the autonomous FSM
        MOVE_2_STONES, TURN_2_SCAN, SCAN_STONES, GET_NEW_STONE, MOVE_BACK, MOVE_INTO_STONES, INTAKE_STONE, STOP;
    }

    public void runOpMode() {
        States current_state = States.MOVE_2_STONES;
        telemetry.addData("Status", "Initializing...");
        robot.init(hardwareMap);

        //gyro init
        gyro = new Gyro(robot.hwMap, "imu"); // special initialization for gyro
        gyro.start();

        //touch sensor init
        DigitalChannel digitalTouch;
        digitalTouch = hardwareMap.get(DigitalChannel.class, "sensor_digital");
        digitalTouch.setMode(DigitalChannel.Mode.INPUT);

        //color sensor
        ColorSensor RCS;
        // hsvValues is an array that will hold the hue, saturation, and value information.
        float hsvValues[] = {0F,0F,0F};

        // values is a reference to the hsvValues array.
        final float values[] = hsvValues;

        // get a reference to the RelativeLayout so we can change the background
        // color of the Robot Controller app to match the hue detected by the RGB sensor.
        int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);
        // bPrevState and bCurrState represent the previous and current state of the button.
        boolean bPrevState = false;
        boolean bCurrState = false;
        // bLedOn represents the state of the LED.
        boolean bLedOn = true;
        // get a reference to our ColorSensor object.
        RCS = hardwareMap.get(ColorSensor.class, "RCS");
        // Set the LED in the beginning
        RCS.enableLed(bLedOn);

        telemetry.addData("Status", "Ready!");
        telemetry.update();

        // wait for the start button to be pressed.
        waitForStart();

        // State machine for robot
        telemetry.addData("Status", "Running");
        telemetry.addData("State", "Moving to stones");
        telemetry.update();
        telemetry.addData("Motor position: ", robot.DriveFrontLeft.getCurrentPosition());

        robot.resetAllEncoders();
        gyro.resetHeading();

        while (current_state == States.MOVE_2_STONES) {
            // move from wall to stones
            // FAKE NEWS! actually run the robot forward a little bit.
            robot.setDriveMotors(0.5, 0.5, 0.5, 0.5);
            telemetry.update();
            if (robot.DriveFrontLeft.getCurrentPosition() > 1500) {
                current_state = States.TURN_2_SCAN;
                robot.setDriveMotors(0.0, 0.0, 0.0, 0.0);
            }
            if(!opModeIsActive()) return; // check termination in the innermost loop
        }

            robot.stopAllMotors();
            sleep(1000);

        telemetry.addData("State", "Moving away from wall");
        telemetry.update();


        while (current_state == States.TURN_2_SCAN) {
            robot.setDriveMotors(-0.5,0.5,-0.5,0.5);
            if (gyro.globalHeading < -90) {
                current_state = States.STOP;
                robot.setDriveMotors(0.0, 0.0, 0.0, 0.0);
                telemetry.addData("Gyro position: ", gyro.globalHeading);
                telemetry.update();
            }
            if(!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.stopAllMotors();
        sleep(5000);

        while (current_state == States.SCAN_STONES) {
            if (RCS.alpha() > 150) {    //skystone
                robot.stopAllMotors();
                current_state = States.MOVE_BACK;
            } else {
                robot.stopAllMotors();
                current_state = States.GET_NEW_STONE;
            }
            if(!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.stopAllMotors();
        sleep(5000);

        while (current_state == States.GET_NEW_STONE) {
            robot.setDriveMotors(0.5,0.5,0.5,0.5);
            if (robot.DriveFrontLeft.getCurrentPosition() >1300) {
                robot.stopAllMotors();
                current_state = States.SCAN_STONES;
            }
            if(!opModeIsActive()) return; // check termination in the innermost loop
        }

        while (current_state == States.MOVE_BACK) {
            robot.setDriveMotors(-0.5,-0.5,-0.5,-0.5);
            if (robot.DriveFrontLeft.getCurrentPosition() < 1300) {
                current_state = States.MOVE_INTO_STONES;
                robot.stopAllMotors();
            }
            if(!opModeIsActive()) return; // check termination in the innermost loop
        }

        while (current_state == States.MOVE_INTO_STONES) {
            robot.setDriveMotors(0.5,-0.5,-0.5,0.5);
            if (robot.DriveFrontLeft.getCurrentPosition() > 1300) {
                current_state = States.STOP;
                robot.stopAllMotors();
            }
            if(!opModeIsActive()) return; // check termination in the innermost loop
        }

        while (current_state == States.INTAKE_STONE) {
            if (digitalTouch.getState() == false) {
                // button is pressed.
                telemetry.addData("Button", "PRESSED");
                robot.IntakeLeft.setPower(0.0);
                robot.IntakeRight.setPower(0.0);
            } else {
                // button is not pressed.
                telemetry.addData("Button", "NOT PRESSED");
                robot.IntakeLeft.setPower(1.0);
                robot.IntakeRight.setPower(1.0);
            }
            if(!opModeIsActive()) return; // check termination in the innermost loop
        }
        //telemetry.addData("Angle", gyro.globalHeading);

        while (current_state == States.STOP) {
            robot.stopAllMotors();
            if(!opModeIsActive()) return; // check termination in the innermost loop
        }
    }
}