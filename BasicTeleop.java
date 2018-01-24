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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;


@TeleOp(name="Basic TeleOp", group="NioleBot")
public class BasicTeleop extends OpMode {

    NicoleBot robot = new NicoleBot();   // Use robot's hardware

    public ElapsedTime runtime = new ElapsedTime();
    public BasicTeleop() { }

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
        // left stick controls direction - forward/back, left/right
        // right stick X controls rotation - CW/CCW
        float gamepad1LeftY = gamepad1.left_stick_y;
        float gamepad1LeftX = -gamepad1.left_stick_x;
        float gamepad1RightX = -gamepad1.right_stick_x;

        float gamepad2RightY = -gamepad2.right_stick_y;
        float gamepad2LeftY = -gamepad2.left_stick_y;

        // holonomic formulas for omnibot control
        float FrontLeft = -gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        float FrontRight = gamepad1LeftY - gamepad1LeftX - gamepad1RightX;
        float BackRight = gamepad1LeftY + gamepad1LeftX - gamepad1RightX;
        float BackLeft = -gamepad1LeftY + gamepad1LeftX - gamepad1RightX;

        // write the values to the motors
        robot.FrontMotorRight.setPower(FrontRight);
        robot.FrontMotorLeft.setPower(FrontLeft);
        robot.BackMotorLeft.setPower(BackLeft);
        robot.BackMotorRight.setPower(BackRight);

        robot.RBotEscalator.setPower(gamepad2RightY);
        robot.LBotEscalator.setPower(-gamepad2RightY);

        robot.RTopEscalator.setPower(gamepad2LeftY);
        robot.LTopEscalator.setPower(-gamepad2LeftY);


        if (gamepad2.y) {
            robot.REscalatorDown.setPosition(0.6);
            robot.LEscalatorDown.setPosition(0.4); //Top Escalator up
        }
        else if (gamepad1.a) {
            robot.REscalatorDown.setPosition(0.4);
            robot.LEscalatorDown.setPosition(0.6);   //Top Escalator down
        }
        else {
            robot.REscalatorDown.setPosition(0.5);
            robot.LEscalatorDown.setPosition(0.5);
        }

//        if (gamepad2.dpad_up) robot.NicoleArm.setPower(1.0); //Arm out
//        else robot.NicoleArm.setPower(0.0);
//
//        if (gamepad2.dpad_down) robot.NicoleArm.setPower(-1.0); //Arm in
//        else robot.NicoleArm.setPower(0.0);

//        if (gamepad2.y) { //Arm up
//            robot.ArmClawUD.setPosition(1.0);
//        } else if (gamepad2.b) { //Arm down
//            robot.ArmClawUD.setPosition(0.0);
//        }
//        if (gamepad2.x) { //Arm open
//            robot.ArmClawFront.setPosition(0.0);
//            robot.ArmClawBack.setPosition(1.0);
//        } else if (gamepad2.a) { //Arm close
//            robot.ArmClawUD.setPosition(0.85);
//            robot.ArmClawUD.setPosition(0.15);
        // }

		/*
		 * Telemetry for debugging
		 */
            telemetry.addData("Text", "*** Robot Data***");
            telemetry.addData("Joy XL YL XR", String.format("%.2f", gamepad1LeftX) + " " +
                    String.format("%.2f", gamepad1LeftY) + " " + String.format("%.2f", gamepad1RightX));
            telemetry.addData("f left pwr", "front left  pwr: " + String.format("%.2f", FrontLeft));
            telemetry.addData("f right pwr", "front right pwr: " + String.format("%.2f", FrontRight));
            telemetry.addData("b right pwr", "back right pwr: " + String.format("%.2f", BackRight));
            telemetry.addData("b left pwr", "back left pwr: " + String.format("%.2f", BackLeft));

    }

    @Override
    public void stop() { }

    /*
     * This method scales the joystick input so for low joystick values, the
     * scaled value is less than linear.  This is to make it easier to drive
     * the robot more precisely at slower speeds.
     */
    double scaleInput(double dVal)  {
        double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
}

}
