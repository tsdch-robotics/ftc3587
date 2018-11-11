/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Tank Drive", group="BBot")
public class TankDriveTeleop extends OpMode {
    BBot robot = new BBot();   // Use robot's hardware
    public ElapsedTime runtime = new ElapsedTime();

    double HookPosition = 0.0;


    @Override
    public void init() {
        /*
         * Use the hardwareMap to get the dc motors and servos by name. Note
         * that the names of the devices must match the names used when you
         * configured your robot and created the configuration file.
         */
        robot.init(hardwareMap);
    }

    @Override
    public void loop() {
        // tank drive: each stick controls one side of the robot
        // dpad for strafing left/right
        float DriveLeftPower = -gamepad1.left_stick_y;
        float DriveRightPower = -gamepad1.right_stick_y;
        boolean LeftStrafe = gamepad1.dpad_left;
        boolean RightStrafe = gamepad1.dpad_right;
        boolean IntakeCR = gamepad2.left_bumper;

        if (RightStrafe) {
            // to right strafe, right motors towards each other, left motors away from each other
            robot.DriveFrontLeft.setPower(1);
            robot.DriveFrontRight.setPower(-1);
            robot.DriveBackLeft.setPower(-1);
            robot.DriveBackRight.setPower(1);
        } else if (LeftStrafe) {
            // opposite of right strafe
            robot.DriveFrontLeft.setPower(-1);
            robot.DriveFrontRight.setPower(1);
            robot.DriveBackLeft.setPower(1);
            robot.DriveBackRight.setPower(-1);
        } else {
            // write the values to the motors
            robot.DriveBackLeft.setPower(DriveLeftPower);
            robot.DriveFrontLeft.setPower(DriveLeftPower);
            robot.DriveFrontRight.setPower(DriveRightPower);
            robot.DriveBackRight.setPower(DriveRightPower);
        }

        // intake mechanism variables

        // elevator
        boolean Elevator_up = gamepad2.right_bumper;
        boolean Elevator_down = (gamepad2.right_trigger > 0.1);
        String elevatorStatus = "";

        if(Elevator_up) { // if right bumper is held arm elevator is set to 1
            robot.ArmElevator.setPower(1);
            elevatorStatus = "up";
        }
        else if(Elevator_down) { // if right trigger is pressed elevator is set to -1
            robot.ArmElevator.setPower(-1);
            elevatorStatus = "down";
        }
        else { // if neither are pressed elevator is set to 0
            robot.ArmElevator.setPower(0);
            elevatorStatus="off";
        }

        // hook
        boolean HookUp = gamepad2.dpad_up;
        boolean HookDown = gamepad2.dpad_down;
        String hookStatus = "";

        if (HookUp) {
            HookPosition = (HookPosition < 1) ? HookPosition + 0.05 : HookPosition;
            hookStatus = "Moving up";
        }
        else if (HookDown) {
            HookPosition = (HookPosition > 0) ? HookPosition - 0.05 : HookPosition;
            hookStatus = "Moving down";
        }

        else {
            hookStatus = "idle";
        }
        robot.ArmHook.setPosition(HookPosition);

     //intake

       boolean intakePower = gamepad2.left_bumper;

        if (intakePower) {
            robot.IntakeCR.setPower(-1);
        }

        else {
            robot.IntakeCR.setPower(0);
        }
        /*
         * Telemetry for debugging
         */

        telemetry.addData("Left Right", String.format("%.2f", DriveLeftPower) + " " + String.format("%.2f", DriveRightPower));
    }


    @Override
    public void stop() { }

}
