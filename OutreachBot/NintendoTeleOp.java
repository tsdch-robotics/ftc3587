package org.firstinspires.ftc.teamcode.OutreachBot;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="NintendoTeleOp", group="MarioBot")
public class NintendoTeleOp extends OpMode {
    private MarioBot robot = new MarioBot(); //use robot's hardware
    public ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        robot.init(hardwareMap);
    }

    @Override
    public void loop(){
        float LeftSideMotors = -gamepad1.left_stick_y + gamepad1.left_stick_x;
        float RightSideMotors = -gamepad1.left_stick_y - gamepad1.left_stick_x;

        robot.LeftFrontMotor.setPower(LeftSideMotors);
        robot.RightFrontMotor.setPower(RightSideMotors);

        telemetry.addData("LeftMotorStatus: ", LeftSideMotors);
        telemetry.addData("RightMotorsStatus: ", RightSideMotors);
    }
}