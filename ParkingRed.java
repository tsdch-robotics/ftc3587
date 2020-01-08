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

@Autonomous(name="ParkingRed", group="ChampBot")
public class ParkingRed extends LinearOpMode {
    ChampBot robot = new ChampBot();   // Use robot's hardware

    private enum States { // states for the autonomous FSM
        DRIVE_OUT, TURN_2_BRIDGE, TURN_2_BRIDGE_FIX, DRIVE_2_BRIDGE , STOP;
    }

    public void runOpMode() {
        robot.init(hardwareMap);
        Gyro gyro;

        States current_state = States.DRIVE_OUT;

        // send telemetry message to signify robot waiting
        telemetry.addData("Status: ", "Snoozing");
        telemetry.update();

        // gyro init
        gyro = new Gyro(robot.hwMap, "imu"); // special initialization for gyro
        gyro.start();


        // wait for the start button to be pressed.
        waitForStart();

        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();

        while (current_state == States.DRIVE_OUT) {
            robot.setDriveMotors(0.4,0.4,0.4,0.4); //drive forwards
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(20.0)) {
                robot.stopAllMotors();
                current_state = States.TURN_2_BRIDGE;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();

        while (current_state == States.TURN_2_BRIDGE) {
            robot.setDriveMotors(0.4,-0.4,0.4,-0.4); //turn RIGHT in place
            if (gyro.globalHeading <= -90) {
                robot.stopAllMotors();
                current_state = States.TURN_2_BRIDGE_FIX;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        sleep(100);
        while (current_state == States.TURN_2_BRIDGE_FIX) {
            robot.setDriveMotors(-0.1,0.1,-0.1,0.1); //turn LEFT in place to fix
            if (gyro.globalHeading >= -90) {
                robot.stopAllMotors();
                current_state = States.DRIVE_2_BRIDGE;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();

        while (current_state == States.DRIVE_2_BRIDGE) {
            robot.setDriveMotors(0.4,0.4,0.4,0.4);
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(24.0)) {
                robot.stopAllMotors();
                current_state = States.STOP;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();

        while (current_state == States.STOP) {
            // stop all motors
            robot.stopAllMotors();
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
    }
}