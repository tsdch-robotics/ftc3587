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
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Servo Test Bench", group="BBot")
public class ServoTestBench extends OpMode {
    public Servo Servo1;
    public Servo Servo2;
    public Servo Servo3;
    public CRServo CRServo1;
    public CRServo CRServo2;
    public CRServo CRServo3;

    double servoPosition[];

    boolean buttonReleased; // debounce toggle buttons
    int selectedServo = 0;
    int selectedCRServo = 0;

    @Override
    public void init() {
		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */
        // todo: add scan for incorrect configuration (must be STB)
        try {
            Servo1 = hardwareMap.servo.get("Servo1");
            Servo2 = hardwareMap.servo.get("Servo2");
            Servo3 = hardwareMap.servo.get("Servo3");
            CRServo1 = hardwareMap.crservo.get("CRServo1");
            CRServo2 = hardwareMap.crservo.get("CRServo2");
            CRServo3 = hardwareMap.crservo.get("CRServo3");
        }
        catch(Exception ex) {
            telemetry.addData("Incorrect configuration! Must use STB configuration.", null);
        }

        servoPosition = new double[] { 0.0, 0.0, 0.0 };
    }

    @Override
    public void loop() {
        boolean ServoNext = gamepad1.dpad_right;
        boolean ServoPrev = gamepad1.dpad_left;
        boolean CRNext = gamepad1.left_bumper;
        boolean CRPrev = (gamepad1.left_trigger > 0.5);

        boolean ServoPosIncrease = gamepad1.b;
        boolean ServoPosDecrease = gamepad1.a;

        double CRServoPower = -gamepad1.right_stick_y;

        // servo selection logic
        if(ServoNext) selectedServo = (selectedServo >= 2) ? 0 : selectedServo + 1;
        if(ServoPrev) selectedServo = (selectedServo <= 0) ? 2 : selectedServo - 1;
        if(CRNext) selectedCRServo = (selectedCRServo >= 2) ? 0 : selectedCRServo + 1;
        if(CRPrev) selectedCRServo = (selectedCRServo <= 0) ? 2 : selectedCRServo - 1;

        // now we know which servo and which CR servo is selected
        if(ServoPosIncrease) {
            double curPos = servoPosition[selectedServo];
            servoPosition[selectedServo] = (curPos >= 1.0) ? 1.0 : curPos + 0.01;
        }
        else if(ServoPosDecrease) {
            double curPos = servoPosition[selectedServo];
            servoPosition[selectedServo] = (curPos <= 0.0) ? 0.0 : curPos - 0.01;
        }

        Servo1.setPosition(servoPosition[0]);
        Servo2.setPosition(servoPosition[1]);
        Servo3.setPosition(servoPosition[2]);

        switch(selectedCRServo) {
            case 0:
                CRServo1.setPower(CRServoPower);
                CRServo2.setPower(0);
                CRServo3.setPower(0);
                break;
            case 1:
                CRServo1.setPower(0);
                CRServo2.setPower(CRServoPower);
                CRServo3.setPower(0);
                break;
            case 2:
                CRServo1.setPower(0);
                CRServo2.setPower(0);
                CRServo3.setPower(CRServoPower);
                break;
            default:
                break;
        }

		/*
		 * Telemetry for debugging
		 */
        telemetry.addData("Selected servo, CR servo", "%d, %d", selectedServo, selectedCRServo + 3);
        telemetry.addData("Servo positions", "%.2f %.2f %.2f", servoPosition[0], servoPosition[1], servoPosition[2]);
        telemetry.addData("CR servo speed", "%.2f", CRServoPower);

        if(ServoNext || ServoPrev || CRNext || CRPrev) {
            try { Thread.sleep(300); }
            catch(Exception ex) {}
        }

    }

    @Override
    public void stop() { }

}
