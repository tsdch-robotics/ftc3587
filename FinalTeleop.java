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
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name="Final TeleOp", group="FinalBot")
public class FinalTeleop extends OpMode {

    /* Declare OpMode members. */
    FinalBot         robot   = new FinalBot();   // Use robot's hardware
    private ElapsedTime     runtime = new ElapsedTime();

    ColorSensor color_sensor;
    boolean last = false; // used for servo button

    // code runs ONCE when driver hits INIT
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        updateTelemetry(telemetry);
    }

    // code runs REPEATEDLY after the driver hits INIT, but before they hit PLAY
    @Override
    public void init_loop() { }

    // code runs ONCE when the driver hits PLAY
    @Override
    public void start() { }

    // code runs REPEATEDLY when driver hits play
    // put driver controls (drive motors, servos, etc.) HERE
    @Override
    public void loop() {
        double ThrottleLeft = -gamepad1.left_stick_y;
        double ThrottleRight = gamepad1.right_stick_y;
        boolean Pullback = gamepad1.a;
        boolean LBeacon = false;
        boolean RBeacon = false;
        boolean ArmsBack = gamepad1.left_bumper;
        boolean LeftArm = gamepad1.x;
        boolean RightArm = gamepad1.y;
        boolean Slow = gamepad1.b;
        double QuarterRight = -(0.2);
        double QuarterLeft = (0.2);
        boolean ServoOpen = false;


        if(gamepad1.right_bumper && gamepad1.right_bumper != last){
            ServoOpen = !ServoOpen;
            double end_time = runtime.milliseconds() + 150.0;
            while(runtime.milliseconds() < end_time){
                //this is needed to help with the button working
                //trust me
                //-Jonathan
            }
        }


        if(LeftArm == true){
            robot.ArmL.setPosition(1.0);
        }
        else if(RightArm){
            robot.ArmR.setPosition(1.0);
        }
        else if(ArmsBack){
            robot.Servos.setServoPosition(1, 0.5);
            robot.Servos.setServoPosition(2, 0.5);
            robot.Servos.setServoPosition(1, 0.5);
            robot.Servos.setServoPosition(3, 0.5);


        }

        if(Slow){
            robot.FrontMotorLeft.setPower(QuarterLeft);
            robot.BackMotorLeft.setPower(QuarterLeft);

            robot.FrontMotorRight.setPower(QuarterRight);
            robot.BackMotorRight.setPower(QuarterRight);
        }
        else{
            robot.FrontMotorLeft.setPower(ThrottleLeft);
            robot.BackMotorLeft.setPower(ThrottleLeft);

            robot.FrontMotorRight.setPower(ThrottleRight);
            robot.BackMotorRight.setPower(ThrottleRight);
        }

        if(Pullback){
            //robot.FiringMotor.setPower(0.5);
        }
        else{
            //robot.FiringMotor.setPower(0.1);
        }
        if(ServoOpen) {
            //robot.Pickup.setPosition(0.0);
        }else{
            //robot.Pickup.setPosition(0.7);
        }

        if(LBeacon){
            //robot.Beacon.setPosition(0.5);
        }
        else if(RBeacon){
            //robot.Beacon.setPosition(0.0);
        }
        else{
            //robot.Beacon.setPosition(0.0);
        }


        color_sensor = hardwareMap.colorSensor.get("Right");


        // telemetry
        telemetry.addData("left", "%.2f", ThrottleLeft);
        telemetry.addData("right", "%.2f", ThrottleRight);
        telemetry.addData("ColorR",  color_sensor.red());
        telemetry.addData("ColorB",  color_sensor.blue());
        //telemetry.addData("ColorG", color_sensor.green());
        //telemetry.addData("ColorAlp", color_sensor.alpha());
        telemetry.addData("ColorArg", color_sensor.argb());
        telemetry.addData("ArmL Out", robot.ArmL.getPosition());
        telemetry.addData("ArmsBack", ArmsBack);

        updateTelemetry(telemetry);
    }
}
