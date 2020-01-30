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

import org.firstinspires.ftc.teamcode.Vision.VuforiaStuff;


@Autonomous(name="BasicAutoRed", group="ChampBot")
public class VisionAutoRed extends LinearOpMode {
    ChampBot robot = new ChampBot();   // Use robot's hardware

    private enum States { // states for the autonomous FSM
        STRAFE_2_RIGHT, STRAFE_2_CENTER, STRAFE_2_LEFT, NAV_2_BLOCKS, AWAY_FROM_BLOCKS, TURN_AWAY_FROM_BLOCKS, TURN_AWAY_FROM_BLOCKS_FIX, FAR_2_BRIDGE, MID_2_BRIDGE, CLOSE_2_BRIDGE, STOP;
    }

    public void runOpMode() {
        robot.init(hardwareMap);

        // gyro init
        Gyro gyro;
        gyro = new Gyro(robot.hwMap, "imu"); // special initialization for gyro
        gyro.start();

        // vision init
        robot.visionInit();
        VuforiaStuff.skystonePos pos;

        States current_state = States.STOP;

        // wait for the start button to be pressed
        waitForStart();

        pos = robot.vuforiaStuff.vuforiascan(false, true);

        switch (pos) {
            case RIGHT:
                current_state = States.STRAFE_2_RIGHT; // to right block
                break;
            case CENTER:
                current_state = States.NAV_2_BLOCKS;
                break;
            case LEFT:
                current_state = States.STRAFE_2_LEFT; // to left block
                break;
        }

        // State machine for robot

        gyro.resetHeading();
        robot.resetAllEncoders();
        telemetry.addData("State: ", current_state);
        telemetry.update();
        sleep(500);

        while (current_state == States.STRAFE_2_RIGHT) {
            robot.setDriveMotors(-0.3, 0.3, 0.3, -0.3); // right strafe (since robot backwards)
            if (robot.DriveFrontLeft.getCurrentPosition() <= -robot.inchesToEncoderCounts(16.0)) {
                robot.stopAllMotors();
                current_state = States.NAV_2_BLOCKS;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        /*while (current_state == States.STRAFE_2_CENTER) {
            robot.setDriveMotors(0.3, -0.3, -0.3, 0.3); // left strafe (since robot backwards)
            if (robot.DriveFrontLeft.getCurrentPosition() >= robot.inchesToEncoderCounts(3.0)) {
                robot.stopAllMotors();
                current_state = States.NAV_2_BLOCKS;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }*/

        while (current_state == States.STRAFE_2_LEFT) {
            robot.setDriveMotors(0.3, -0.3, -0.3, 0.3); // right strafe (since robot backwards)
            if (robot.DriveFrontLeft.getCurrentPosition() >= robot.inchesToEncoderCounts(16.0)) {
                robot.stopAllMotors();
                current_state = States.NAV_2_BLOCKS;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        gyro.resetHeading();
        robot.resetAllEncoders();
        telemetry.addData("State: ", current_state);
        telemetry.update();
        sleep(500);

        while (current_state == States.NAV_2_BLOCKS) {
            robot.setDriveMotors(-0.3, -0.3, -0.3, -0.3); // left strafe (since robot backwards)
            if (robot.DriveFrontLeft.getCurrentPosition() <= -robot.inchesToEncoderCounts(27.0)) {
                robot.stopAllMotors();
                robot.PlatformServo.setPosition(1.0); // closed on block
                current_state = States.AWAY_FROM_BLOCKS;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        gyro.resetHeading();
        robot.resetAllEncoders();
        telemetry.addData("State: ", current_state);
        telemetry.update();
        sleep(500);

        while (current_state == States.AWAY_FROM_BLOCKS) {
            robot.setDriveMotors(0.3, 0.3, 0.3, 0.3); // moves away from blocks
            if (robot.DriveFrontLeft.getCurrentPosition() >= robot.inchesToEncoderCounts(6.0)) {
                robot.stopAllMotors();
                current_state = States.TURN_AWAY_FROM_BLOCKS;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        gyro.resetHeading();
        robot.resetAllEncoders();
        telemetry.addData("State: ", current_state);
        telemetry.update();
        sleep(500);

        while (current_state == States.TURN_AWAY_FROM_BLOCKS) {
            robot.setDriveMotors(0.2, -0.2, 0.2, -0.2); // Turns right
            if (gyro.getHeading() <= -90.0 ) {
                robot.stopAllMotors();
                current_state = States.TURN_AWAY_FROM_BLOCKS_FIX;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        /*while (current_state == States.TURN_AWAY_FROM_BLOCKS_FIX) {
            robot.setDriveMotors(-0.05, 0.05, -0.05, 0.05); // Turns left correct
            if (gyro.getHeading() >= -89.7 ) {
                robot.stopAllMotors();
                current_state = States.STOP;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }*/

        sleep(100);

        switch (pos) {
            case RIGHT:
                current_state = States.CLOSE_2_BRIDGE;
                break;
            case CENTER:
                current_state = States.MID_2_BRIDGE; // 80
                break;
            case LEFT:
                current_state = States.FAR_2_BRIDGE;
                break;
        }

        robot.Wrist.setPosition(0.0); // down
        gyro.resetHeading();
        robot.resetAllEncoders();
        telemetry.addData("State: ", current_state);
        telemetry.update();
        sleep(500);

        while (current_state == States.CLOSE_2_BRIDGE) {
            robot.setDriveMotors(-0.3, -0.3, -0.3, -0.3); // right strafe (since robot backwards)
            if (robot.DriveFrontLeft.getCurrentPosition() <= -robot.inchesToEncoderCounts(72.0)) {
                robot.stopAllMotors();
                current_state = States.STOP;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        while (current_state == States.MID_2_BRIDGE) {
            robot.setDriveMotors(-0.3, -0.3, -0.3, -0.3); // right strafe (since robot backwards)
            if (robot.DriveFrontLeft.getCurrentPosition() <= -robot.inchesToEncoderCounts(80.0)) {
                robot.stopAllMotors();
                current_state = States.STOP;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        while (current_state == States.FAR_2_BRIDGE) {
            robot.setDriveMotors(-0.3, -0.3, -0.3, -0.3); // right strafe (since robot backwards)
            if (robot.DriveFrontLeft.getCurrentPosition() <= -robot.inchesToEncoderCounts(88.0)) {
                robot.stopAllMotors();
                current_state = States.STOP;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        gyro.resetHeading();
        robot.resetAllEncoders();
        telemetry.addData("State: ", current_state);
        telemetry.update();
        sleep(500);

        while (current_state == States.STOP) {
            // stop all motors
            robot.stopAllMotors();
            telemetry.addData("Case", pos);
            telemetry.update();
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
    }
}