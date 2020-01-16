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
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.VuforiaStuff;

@Autonomous(name="Basic", group="BBot")
public class BasicAuto extends LinearOpMode {
    MiniProgrammingBot robot = new MiniProgrammingBot();   // Use robot's hardware
    Gyro gyro;

    private enum States { // states for the autonomous FSM
        STOP;
    }

    public void runOpMode() {
        States current_state = States.STOP;

        robot.init(hardwareMap);
        gyro = new Gyro(robot.hwMap, "imu"); // special initialization for gyro
        gyro.start();

        VuforiaStuff.skystonePos pos;

        telemetry.addData("Status", current_state);
        telemetry.update();

        // wait for the start button to be pressed.
        waitForStart();

        pos = vuforiaStuff.vuforiascan(false, true);
        //liftSystem.hLift.setPower(-.3);
        switch (pos) {
            case RIGHT:
                break;
            case CENTER:
                break;
            case LEFT:
                break;
        }
        // State machine for robot

        telemetry.addData("State", current_state);
        telemetry.update();

        while (current_state == States.STOP) {
            robot.stopAllMotors();
            // this will stay stuck here for testing purposes
        }

    }
}