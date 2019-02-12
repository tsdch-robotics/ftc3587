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
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="Ralph's Auto", group="BBot")
public class TradAuto extends LinearOpMode {
    BBot robot = new BBot();   // Use robot's hardware

    private enum States { // states for the autonomous FSM
        LOWERING, CLEAR_LANDER, RETRACT_LIFT, STOP;
    }

    public void runOpMode() {
        robot.init(hardwareMap);

        // send telemetry message to signify robot waiting
        telemetry.addData("Status", "Snoozing");
        telemetry.update();

        // wait for the start button to be pressed.
        waitForStart();

        // State machine for robot
        States current_state = States.LOWERING;
        telemetry.addData("Status", "Running");
        telemetry.addData("State", "Lowering");
        telemetry.update();

        while (current_state == States.LOWERING) {
            // lower the robot off the hanger
            robot.Lift.setPower(1.0);
            if(robot.Lift.getCurrentPosition() > robot.REVHD401Encoder * 10) {
                current_state = States.CLEAR_LANDER;
                robot.Lift.setPower(0.0);
            }
            if(!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.Lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //telemetry.addData("State", "Clearing Lift, prepping for colored balls");
        //telemetry.update();

        while (current_state == States.CLEAR_LANDER) {
            robot.setDriveMotors(-0.5,-0.5,-0.5,-0.5);
            if(robot.DriveFrontRight.getCurrentPosition() < -robot.REVHD401Encoder * 1) {
                current_state = States.RETRACT_LIFT;
                robot.setDriveMotors(0.0,0.0,0.0,0.0);
            }
            if(!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.DriveFrontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //telemetry.addData("State", "Clearing Lift, prepping for colored balls");
        //telemetry.update();
        //telemetry.addData("Lift:" ,robot.Lift.getCurrentPosition());
        
        while (current_state == States.RETRACT_LIFT) {
            robot.Lift.setPower(-1.0);
            if(robot.Lift.getCurrentPosition() < -robot.REVHD401Encoder * 8) {
                current_state = States.STOP;
                robot.Lift.setPower(0.0);
            }
            if(!opModeIsActive()) return; // check termination in the innermost loop
        }
        telemetry.addData("Status", "Finished");
        telemetry.addData("State", "Stop");
        telemetry.update();
        while (current_state == States.STOP) {
            // stop all motors
            robot.stopAllMotors();
            if(!opModeIsActive()) return; // check termination in the innermost loop
        }
    }
}