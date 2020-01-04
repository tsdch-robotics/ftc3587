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

@Autonomous(name="BasicAuto", group="ChampBot")
public class BasicAuto extends LinearOpMode {
    ChampBot robot = new ChampBot();   // Use robot's hardware

    private enum States { // states for the autonomous FSM
        MOVE_2_SS, SCAN_4_SS, LOCATE_SS, STOP;
    }

    public void runOpMode() {
        robot.init(hardwareMap);

        States current_state = States.MOVE_2_SS;

        // send telemetry message to signify robot waiting
        telemetry.addData("Status", "Snoozing");
        telemetry.update();


        // wait for the start button to be pressed.
        waitForStart();

        robot.resetAllEncoders();
        telemetry.addData("Encodervalue", robot.DriveFrontLeft.getCurrentPosition());
        telemetry.update();

        while (current_state == States.MOVE_2_SS) {
            robot.setDriveMotors(0.2,0.2,0.2,0.2);
            telemetry.addData("Encodervalue", robot.DriveFrontLeft.getCurrentPosition());
            telemetry.addData("Inchestoencoder", robot.inchesToEncoderCounts(26.0));
            telemetry.update();
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(26.0)) {
                robot.setDriveMotors(0,0,0,0);
                current_state = States.STOP;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        // State machine for robot

        telemetry.addData("Status", "Finished");
        telemetry.addData("State", "Stop");
        telemetry.update();
        while (current_state == States.STOP) {
            // stop all motors
            telemetry.addData("Encodervalue", robot.DriveFrontLeft.getCurrentPosition());
            telemetry.addData("Inchestoencoder", robot.inchesToEncoderCounts(27.0));
            telemetry.update();
            robot.stopAllMotors();
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
    }
}