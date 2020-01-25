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


@Autonomous(name="BasicAutoBlue", group="ChampBot")
public class VisionAutoBlue extends LinearOpMode {
    ChampBot robot = new ChampBot();   // Use robot's hardware

    private enum States { // states for the autonomous FSM
        STRAFE_2_RIGHT, STRAFE_2_LEFT, NAV_2_BLOCKS, AWAY_FROM_BLOCKS, TURN_AWAY_FROM_BLOCKS, FAR_2_BRIDGE, MID_2_BRIDGE, CLOSE_2_BRIDGE, STOP;
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
                break;
            case CENTER:
                break;
            case LEFT:
                break;
        }

        // State machine for robot

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