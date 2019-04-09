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

import android.renderscript.ScriptGroup;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Tank Drive", group="BBot")
public class TankDriveTeleop extends OpMode {
    BBot robot = new BBot();
    private String AVS = "";// Use robot's hardware
    private String AHS = "";
    private String Intake = "";
    private String PhatServo = "";
    private String LiftStatus = "";
    public ElapsedTime runtime = new ElapsedTime();
    boolean slowcheck = false;


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
        boolean Reverse = gamepad1.y;
        boolean slow = gamepad1.x;
        boolean liftup = gamepad1.right_bumper;
        float liftdown = gamepad1.right_trigger;


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
        } else if (Reverse) {
            robot.DriveBackLeft.setPower(-DriveLeftPower);
            robot.DriveFrontLeft.setPower(-DriveLeftPower);
            robot.DriveFrontRight.setPower(-DriveRightPower);
            robot.DriveBackRight.setPower(-DriveRightPower);
        }
        if (slowcheck == true && slow == true)
            slowcheck = false;

        else if (slow == true && slowcheck == false) {
            slowcheck = true;
        }
        if (slowcheck == true) {
            robot.DriveBackLeft.setPower(DriveLeftPower / 4);
            robot.DriveFrontLeft.setPower(DriveLeftPower / 4);
            robot.DriveFrontRight.setPower(DriveRightPower / 4);
            robot.DriveBackRight.setPower(DriveRightPower / 4);
        } else {
            // write the values to the motors
            robot.DriveBackLeft.setPower(DriveLeftPower);
            robot.DriveFrontLeft.setPower(DriveLeftPower);
            robot.DriveFrontRight.setPower(DriveRightPower);
            robot.DriveBackRight.setPower(DriveRightPower);
        }
        if (liftup) {
            robot.Lift.setPower(1);

        } else if (liftdown > .1) {
            robot.Lift.setPower(-1);
        } else {
            robot.Lift.setPower(0);
        }


        // intake mechanism variables

        // elevator
        boolean Lift_up = gamepad1.right_bumper;
        boolean Lift_down = (gamepad1.right_trigger > 0.1);

       if (Lift_up) { // if right bumper is held arm elevator is set to 1
            robot.Lift.setPower(1);
            LiftStatus = "moving up";
        } else if (Lift_down) { // if right trigger is pressed elevator is set to -1
            robot.Lift.setPower(-1);
            LiftStatus = "moving down";
        } else { // if neither are pressed elevator is set to 0
            robot.Lift.setPower(0);
            LiftStatus = "idle";
        }

        // arm rotation
        boolean Arm_Up = (gamepad2.left_stick_y > 0);
        boolean Arm_Down = (gamepad2.left_stick_y < 0);

        if (Arm_Down) {
            robot.ArmUpDownR.setPower(-gamepad2.left_stick_y / 4);
            robot.ArmUpDownL.setPower(-gamepad2.left_stick_y / 4);
        } else if (Arm_Up) {
            robot.ArmUpDownR.setPower(-gamepad2.left_stick_y);
            robot.ArmUpDownL.setPower(-gamepad2.left_stick_y);
        }
        else {
            robot.ArmUpDownR.setPower(0);
            robot.ArmUpDownL.setPower(0);
        }


  //      if (Arm_Up) {
//            robot.ArmUpDownL.setPower(.5);
//            robot.ArmUpDownR.setPower(.5);
//        }
//        else if (Arm_Down){
//            robot.ArmUpDownL.setPower(-.5);
//            robot.ArmUpDownR.setPower(-.5);
//        }
//        else {
//            robot.ArmUpDownL.setPower(0);
//            robot.ArmUpDownR.setPower(0);
//        }
//
        // arm extension
        boolean Arm_Out = (gamepad2.right_trigger > 0.1);
        boolean Arm_In = gamepad2.right_bumper;

        if (Arm_Out) {
            robot.ArmExtend.setPower(.7);
        }
        else if (Arm_In) {
            robot.ArmExtend.setPower(-.7);
        }
        else {
            robot.ArmExtend.setPower(0);
        }
     /*
        if (Arm_Out) {
            robot.Raise.setPower(1);
        } else if (Arm_In) {
            robot.Raise.setPower(0);
        } else
            robot.Raise.setPower(.5);
        }
*/
        boolean Spit = gamepad2.a;
        boolean Zucc = gamepad2.b;



        if (Spit) {
            robot.Brotation.setPower(1.0);
            Intake = "spit";
        }
        else if(Zucc) {
            robot.Brotation.setPower(-1.0);
            Intake = "zucc";
        }
        else {
            robot.Brotation.setPower(0.0);
            Intake = "idle";
        }

        boolean Store = gamepad2.dpad_down;
        boolean Active = gamepad2.dpad_up;

        if (Store) {
            robot.PhatServo.setPosition(1.0);
            PhatServo = "store";
        } else if(Active) {
            robot.PhatServo.setPosition(0.0);
            PhatServo = "active";
        }

        // driver data
        telemetry.addData("Lift", LiftStatus);
       // telemetry.addData("Arm", AVS);
       // telemetry.addData("Arm", AHS);

        telemetry.addData("Brotation", Intake);
        telemetry.addData("PhatServo", PhatServo);
        //telemetry.addData("Gyro Directions", gyro.globalHeading);
    }
}