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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="BasicAutoBlue", group="ChampBot")
public class BasicAutoBlue extends LinearOpMode {
    ChampBot robot = new ChampBot();   // Use robot's hardware

    private enum States { // states for the autonomous FSM
        MOVE_2_SS, SCAN_4_SS, GET_NEW_SS, PreGRAB_SS, GRAB_SS, GRAB_SS_2, GRAB_SS_3, MOVE_AWAY_FROM_STONES, TURN_2_BRIDGE, DRIVE_2_BRIDGE, STOP;
    }

    public void runOpMode() {
        robot.init(hardwareMap);

        Gyro gyro;

        // gyro init
        gyro = new Gyro(robot.hwMap, "imu"); // special initialization for gyro
        gyro.start();

        States current_state = States.MOVE_2_SS;


        // send telemetry message to signify robot waiting
        telemetry.addData("Status", "Snoozing");
        telemetry.update();

        int ScannedBlocks = 0;

        // wait for the start button to be pressed.
        waitForStart();

        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.MOVE_2_SS) {
            robot.setDriveMotors(0.2,0.2,0.2,0.2);
            //robot.PlatformServo.setPosition(1.0); //down
            robot.Claw.setPosition(1.0); // open
            robot.Wrist1.setPosition(0.0); // down
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(27.0)) {
                robot.stopAllMotors();
                current_state = States.SCAN_4_SS;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state != States.PreGRAB_SS) {
            sleep(2000);
            while (current_state == States.SCAN_4_SS) {
                sleep(100);
                if (robot.RCS.alpha() <= 90 || ScannedBlocks > 5) { // found stone!
                    current_state = States.PreGRAB_SS;
                } else if (robot.RCS.alpha() > 90) { // not stone!
                    current_state = States.GET_NEW_SS;
                }
                if (!opModeIsActive()) return; // check termination in the innermost loop
            }

            robot.resetAllEncoders();
            telemetry.addData("Status", current_state);
            telemetry.update();
            sleep(2000);

            while (current_state == States.GET_NEW_SS) {
                robot.setDriveMotors(0.4, -0.4, -0.4, 0.4); // left strafe
                if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(12.0)) {
                    robot.stopAllMotors();
                    ScannedBlocks += 1;
                    current_state = States.SCAN_4_SS;
                }
                if (!opModeIsActive()) return; // check termination in the innermost loop
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();

        while (current_state == States.PreGRAB_SS) {
            robot.setDriveMotors(-0.4,0.4,0.4,-0.4);
            if (robot.DriveFrontLeft.getCurrentPosition() < -robot.inchesToEncoderCounts(6.0)) {
                robot.stopAllMotors();
                current_state = States.MOVE_AWAY_FROM_STONES;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();
        gyro.resetHeading();
        telemetry.addData("Status", current_state);
        telemetry.update();
        /*sleep(100);

        while (current_state == States.GRAB_SS) {
            robot.setDriveMotors(-0.4, -0.4, -0.4, -0.4);
            if (robot.DriveFrontLeft.getCurrentPosition() < -robot.inchesToEncoderCounts(3.0)) {
                robot.stopAllMotors();
                current_state = States.MOVE_AWAY_FROM_STONES;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.GRAB_SS_2) {
            robot.setDriveMotors(-0.4, 0.4, -0.4, 0.4); //turn LEFT in place
            if (gyro.globalHeading >= 180) {
                robot.stopAllMotors();
                current_state = States.MOVE_AWAY_FROM_STONES;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.GRAB_SS_3) {
            robot.setDriveMotors(-0.4, -0.4, -0.4, -0.4);
            if (robot.DriveFrontLeft.getCurrentPosition() < -robot.inchesToEncoderCounts(3.0)) {
                robot.stopAllMotors();
                current_state = States.MOVE_AWAY_FROM_STONES;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }*/
        sleep(200);

        robot.Claw.setPosition(0.3); //down

        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.MOVE_AWAY_FROM_STONES) {
            robot.setDriveMotors(-0.4, -0.4, -0.4, -0.4);
            if (robot.DriveFrontLeft.getCurrentPosition() <= -robot.inchesToEncoderCounts(6.0)) {
                robot.stopAllMotors();
                current_state = States.TURN_2_BRIDGE;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();
        gyro.resetHeading();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.TURN_2_BRIDGE) {
            robot.setDriveMotors(-0.4, 0.4, -0.4, 0.4); //swivel LEFT
            if (gyro.globalHeading >= 90) {
                robot.stopAllMotors();
                current_state = States.DRIVE_2_BRIDGE;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();
        gyro.resetHeading();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.DRIVE_2_BRIDGE) {
            robot.setDriveMotors(0.4, 0.4, 0.4, 0.4);
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(24.0)) {
                robot.stopAllMotors();
                current_state = States.STOP;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        while (current_state == States.STOP) {
            // stop all motors
            robot.stopAllMotors();
            telemetry.addData("Status", current_state);
            telemetry.update();
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
    }
}