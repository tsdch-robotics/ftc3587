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


@Autonomous(name="GrabBlockRed", group="ChampBot")
public class GrabBlockRed extends LinearOpMode {
    ChampBot robot = new ChampBot();   // Use robot's hardware

    private enum States { // states for the autonomous FSM
        NAV_2_BLOCKS, AWAY_FROM_BLOCKS, TURN_AWAY_FROM_BLOCKS, MID_2_BRIDGE, PARK_UNDER_BRIDGE, STOP;
    }

    public void runOpMode() {
        robot.init(hardwareMap);

        // gyro init
        Gyro gyro;
        gyro = new Gyro(robot.hwMap, "imu"); // special initialization for gyro
        gyro.start();

        States current_state = States.NAV_2_BLOCKS;

        // wait for the start button to be pressed
        waitForStart();


        // State machine for robot
/*
        robot.Wrist.setPosition(0.0); // out
        robot.Claw.setPosition(1.0); // open
        gyro.resetHeading();
        robot.resetAllEncoders();
        telemetry.addData("State: ", current_state);
        telemetry.update();
        sleep(1000);

        while (current_state == States.NAV_2_BLOCKS) {
            robot.setDriveMotors(0.35, 0.35, 0.35, 0.35); // left strafe (since robot backwards)
            if (robot.DriveFrontLeft.getCurrentPosition() >= robot.inchesToEncoderCounts(28.0)) {
                robot.stopAllMotors();
                robot.PlatformServo.setPosition(1.0); // closed on block
                current_state = States.AWAY_FROM_BLOCKS;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.Claw.setPosition(0.0); // close
        gyro.resetHeading();
        robot.resetAllEncoders();
        telemetry.addData("State: ", current_state);
        telemetry.update();
        sleep(500);

        while (current_state == States.AWAY_FROM_BLOCKS) {
            robot.setDriveMotors(-0.35, -0.35, -0.35, -0.35); // moves away from blocks
            if (robot.DriveFrontLeft.getCurrentPosition() <= -robot.inchesToEncoderCounts(5.0)) {
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
            robot.setDriveMotors(0.25, -0.25, 0.25, -0.25); // Turns right
            if (gyro.getHeading() <= -85.0 ) {
                robot.stopAllMotors();
                current_state = States.MID_2_BRIDGE;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        sleep(100);

        gyro.resetHeading();
        robot.resetAllEncoders();
        telemetry.addData("State: ", current_state);
        telemetry.update();
        sleep(500);

        while (current_state == States.MID_2_BRIDGE) {
            robot.setDriveMotors(0.35, 0.35, 0.35, 0.35);
            if (robot.DriveFrontLeft.getCurrentPosition() >= robot.inchesToEncoderCounts(48.0)) {
                robot.stopAllMotors();
                current_state = States.PARK_UNDER_BRIDGE;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }


        robot.Claw.setPosition(1.0); // open
        gyro.resetHeading();
        robot.resetAllEncoders();
        telemetry.addData("State: ", current_state);
        telemetry.update();
        sleep(500);

        while (current_state == States.PARK_UNDER_BRIDGE) {
            robot.setDriveMotors(-0.35, -0.35, -0.35, -0.35); // right strafe (since robot backwards)
            if (robot.DriveFrontLeft.getCurrentPosition() <= -robot.inchesToEncoderCounts(14.0)) {
                robot.stopAllMotors();
                current_state = States.STOP;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.Wrist.setPosition(1.0); //in
        robot.Claw.setPosition(0.0); // close
        robot.PlatformServo.setPosition(0.0); //up
        robot.LockServo.setPosition(0.0); //un-locked

        while (current_state == States.STOP) {
            // stop all motors
            robot.stopAllMotors();
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

 */
    }
}