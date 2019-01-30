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

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name="Encoder Test Bench", group="BBot")
public class EncoderTestBench extends OpMode {
    public DcMotor Motor1;
    public DcMotor Motor2;
    public Servo ServoSlap;
    public CRServo ServoSuck;
    //public DcMotor Motor3;
    //public DcMotor Motor4;
    int Motor1Encoder;
    @Override
    public void init() {
        /*
         * Use the hardwareMap to get the dc motors and servos by name. Note
         * that the names of the devices must match the names used when you
         * configured your robot and created the configuration file.
         */


        try {
            Motor1 = hardwareMap.dcMotor.get("Motor1");
            Motor2 = hardwareMap.dcMotor.get("Motor2");
            ServoSlap = hardwareMap.servo.get("ServoSlap");
            ServoSuck = hardwareMap.crservo.get("ServoSuck");
            //Motor3 = hardwareMap.dcMotor.get("Motor3");
            //Motor4 = hardwareMap.dcMotor.get("Motor4");
        } catch (Exception ex) {
            telemetry.addData("Incorrect configuration! Must use STB configuration.", null);
        }
        Motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }

    @Override
    public void loop() {
        boolean motor = gamepad1.a;
        boolean reset = gamepad1.b;
        boolean resetencoder = gamepad1.dpad_right;
        boolean slapup = gamepad1.dpad_up;
        boolean slapdown = gamepad1.dpad_down;
        boolean  suckon = gamepad1.x;
        boolean suckoff = gamepad1.y;
        float Rotatearmforward = gamepad1.right_trigger;
        boolean Rotatearmbackward = gamepad1.right_bumper;
        Motor1Encoder = Motor1.getCurrentPosition();
        if(motor==true) {
            Motor1.setTargetPosition(10650);
            //Motor2.setTargetPosition(720);
            Motor1.setPower(.5);
            //Motor2.setPower(.5);
            }
            if(Rotatearmbackward)
            {
                Motor2.setPower(-1);
            }
            else if(Rotatearmforward>0.1)
            {
                Motor2.setPower(1);
            }
            else
            {
                Motor2.setPower(0);
            }
        if(slapup)
        {
            ServoSlap.setPosition(1);
        }
        else if(slapdown)
        {
            ServoSlap.setPosition(0);
        }
        if(suckon)
        {
            ServoSuck.setPower(-1);
        }
        else if(suckoff)
        {
            ServoSuck.setPower(1);
        }
        else
        {
            ServoSuck.setPower(0);
        }








    if(reset== true)
    {
        Motor1.setTargetPosition(3050);
        Motor1.setPower(.5);
    }
    if(resetencoder)
    {
        Motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Motor1.setTargetPosition(0);
        Motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
    telemetry.addData("Encoder=" , Motor1Encoder);


    }
        @Override
        public void stop () {
        }

    }
