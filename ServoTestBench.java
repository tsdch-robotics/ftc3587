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
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name="Servo Test Bench", group="BBot")
public class ServoTestBench extends OpMode {
    private Servo servos[];
    private CRServo CRservos[];
    private DcMotor motors[];

    private int selectedServo = 0;
    private double servoPosition[];
    private int selectedCRServo = 0;
    private int selectedMotor = 0;

    @Override
    public void init() {
		// allocate arrays
        servos = new Servo[3];
        servoPosition = new double[] { 0.0, 0.0, 0.0 };
        CRservos = new CRServo[3];
        motors = new DcMotor[4];

        // instead of using the robot base class, use hardwareMap directly (since this uses a special configuration)
        try {
            int i;
            for(i = 0; i < 3; i++) servos[i] = hardwareMap.servo.get(String.format("Servo%d", i));
            for(i = 0; i < 3; i++) CRservos[i] = hardwareMap.crservo.get(String.format("CRServo%d", i+3));
            for(i = 0; i < 4; i++) motors[i] = hardwareMap.dcMotor.get(String.format("Motor%d", i));
        }
        catch(Exception ex) {
            telemetry.addLine("Incorrect config selected! Must use STB configuration.");
        }
    }

    @Override
    public void loop() {
        // discrete controls
        boolean ServoNext = gamepad1.dpad_up;
        boolean ServoPrev = gamepad1.dpad_down;
        boolean CRNext = gamepad1.left_bumper;
        boolean CRPrev = (gamepad1.left_trigger > 0.5);
        boolean MotorNext = gamepad1.right_bumper;
        boolean MotorPrev = (gamepad1.right_trigger > 0.5);

        // continuous controls
        boolean ServoPosIncrease = gamepad1.b;
        boolean ServoPosDecrease = gamepad1.a;
        double CRServoPower = -gamepad1.left_stick_y;
        double MotorPower = -gamepad1.right_stick_y;

        // servo and motor selection logic
        if(ServoNext) selectedServo = (selectedServo >= 2) ? 0 : selectedServo + 1;
        if(ServoPrev) selectedServo = (selectedServo <= 0) ? 2 : selectedServo - 1;
        if(CRNext) selectedCRServo = (selectedCRServo >= 2) ? 0 : selectedCRServo + 1;
        if(CRPrev) selectedCRServo = (selectedCRServo <= 0) ? 2 : selectedCRServo - 1;
        if(MotorNext) selectedMotor = (selectedMotor >= 3) ? 0 : selectedMotor + 1;
        if(MotorPrev) selectedMotor = (selectedMotor <= 0) ? 3 : selectedMotor - 1;

        // now we know which servo is selected...
        if(ServoPosIncrease) {
            double curPos = servoPosition[selectedServo];
            servoPosition[selectedServo] = (curPos >= 1.0) ? 1.0 : curPos + 0.01;
        }
        else if(ServoPosDecrease) {
            double curPos = servoPosition[selectedServo];
            servoPosition[selectedServo] = (curPos <= 0.0) ? 0.0 : curPos - 0.01;
        }

        // write out servo positions
        for(int i = 0; i < 3; i++) servos[i].setPosition(servoPosition[i]);

        // write out CR servo speeds
        for(int i = 0; i < 3; i++) {
            if(i == selectedCRServo) CRservos[i].setPower(CRServoPower); // set active servo to gamepad value
            else CRservos[i].setPower(0); // set inactive servos to zero
        }

        // if a new motor was selected, reset its encoder
        if(MotorNext || MotorPrev) {
            motors[selectedMotor].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motors[selectedMotor].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        // write out motor speeds
        for(int i = 0; i < 4; i++) {
            if(i == selectedMotor) motors[i].setPower(MotorPower); // active motor to gamepad value
            else motors[i].setPower(0); // inactive motor to zero
        }

        telemetry.addData("Selected servo, CR servo, motor", "%d, %d, %d", selectedServo, selectedCRServo + 3, selectedMotor );
        telemetry.addData("Servo positions", "%.2f %.2f %.2f", servoPosition[0], servoPosition[1], servoPosition[2]);
        telemetry.addData("CR servo speed", "%.2f", CRServoPower);
        telemetry.addData("Motor speed", "%.2f", MotorPower);
        telemetry.addData("Motor position", "%d", motors[selectedMotor].getCurrentPosition());
        telemetry.update();

        // if any discrete-control buttons are pressed, sleep for 0.3s to give user time to release button
        if(ServoNext || ServoPrev || CRNext || CRPrev || MotorNext || MotorPrev) {
            try { Thread.sleep(300); }
            catch(Exception ex) {}
        }
    }
}
