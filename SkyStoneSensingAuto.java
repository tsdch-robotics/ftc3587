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

@Autonomous(name="Skystone Sensing", group="BBot")
public class SkyStoneSensingAuto extends LinearOpMode {
    MiniProgrammingBot robot = new MiniProgrammingBot();   // Use robot's hardware
    Gyro gyro;

    private enum States { // states for the autonomous FSM
        LOWER, MOVE_AWAY_HANGER, TURN1, MOVE_INTO_CRATER, STOP;
    }

    public void runOpMode() {
        States current_state = States.LOWER;
        telemetry.addData("Status", "Initializing...");
        robot.init(hardwareMap);
        gyro = new Gyro(robot.hwMap, "imu"); // special initialization for gyro
        gyro.start();
        telemetry.addData("Status", "Ready!");
        telemetry.update();

        // wait for the start button to be pressed.
        waitForStart();

        // State machine for robot
        telemetry.addData("Status", "Running");
        telemetry.addData("State", "Lowering");
        telemetry.update();
        while (current_state == States.LOWER) {
            // lower the robot off the hanger
            // FAKE NEWS! actually run the robot forward a little bit.
            gyro.resetHeading();
            while(gyro.globalHeading > -90) {
                telemetry.addData("Angle", gyro.globalHeading);
                robot.setDriveMotors(0.5,-0.5,0.5,-0.5);
                telemetry.update();
                if(!opModeIsActive()) return; // check termination in the innermost loop
            }
            robot.stopAllMotors();
            sleep(5000);
        }

        telemetry.addData("State", "Moving away from hanger");
        telemetry.update();
        while (current_state == States.MOVE_AWAY_HANGER) {
            robot.setDriveMotors(0,0,0,0);
            // this will stay stuck here for testing purposes
        }

    }
}