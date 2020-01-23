package org.firstinspires.ftc.teamcode.TeleOP;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.ChampBot;

@TeleOp(name="TradOp", group="ChampBot")
public class TradOP extends OpMode {
    private ChampBot robot = new ChampBot();   // Use robot's hardware
    public ElapsedTime runtime = new ElapsedTime();

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

        // Arm
        robot.Lift.setPower(-gamepad2.left_stick_y);

        //Wrist
        //boolean Up = gamepad2.right_bumper;
        //boolean Down = (gamepad2.right_trigger > 0.1);

        String ArmStatus = "";
        String ClawStatus = "";

        if(gamepad2.right_bumper) { // up
            robot.Arm.setPosition(0.0);
            ArmStatus = "moving up";
        }
        else if(gamepad2.right_trigger > 0.1) { // down
            robot.Arm.setPosition(1.0);
            ArmStatus = "moving down";
        }

        if (gamepad2.a) {
            robot.Claw.setPosition(0.0); // closed
            ClawStatus = "closed";
        }
        else if (gamepad2.b) {
            robot.Claw.setPosition(1.0); // open
            ClawStatus = "open";
        }


        telemetry.addData("Wrist: ", ArmStatus);
        telemetry.addData("Claw: ", ClawStatus);
        telemetry.update();
    }
}