
package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="BasicAutonomous", group="NicoleBot")
public class BasicAutonomous extends LinearOpMode {

    /* Declare OpMode members. */
    NicoleBot         robot   = new NicoleBot();   //
    //ColorSensor colorSensor;


    @Override
    public void runOpMode() throws InterruptedException {
        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);



        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();
        float hsvValues[] = {0F, 0F, 0F};

        final float values[] = hsvValues;
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // program
        //robot.turn(90);
        //sleep(1000);
        //robot.turn(-50);
        //sleep(1000);
        //robot.turn(180);
        //sleep(1000);

        //resetAllEncoders();
        robot.resetAllEncoders();

        robot.FrontMotorLeft.setPower(-0.5);
        robot.BackMotorLeft.setPower(-0.5);
        robot.FrontMotorRight.setPower(0.5);
        robot.BackMotorRight.setPower(0.5);
        sleep(1000);
        robot.FrontMotorLeft.setPower(0.0);
        robot.BackMotorLeft.setPower(0.0);
        robot.FrontMotorRight.setPower(0.0);
        robot.BackMotorRight.setPower(0.0);
        //telemetry.addData("distance", distance);
        updateTelemetry(telemetry);


        robot.JewelCS.enableLed(true);


        //Converting RGB colors to values
        Color.RGBToHSV(robot.JewelCS.red() * 8, robot.JewelCS.green() * 8, robot.JewelCS.blue() * 8, hsvValues);



        //Send info to DS
        telemetry.addData("Clear", robot.JewelCS.alpha());
        telemetry.addData("Red", robot.JewelCS.red());
        telemetry.addData("Green", robot.JewelCS.green());
        telemetry.addData("Blue", robot.JewelCS.blue());

//Post scanning color balls


    relativeLayout.post(new Runnable()  {
        realativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
        });

        telemetry.update()
    }

    relativeLayout.post(new Runnable()  {
        relativeLayout.setBackgroundColor(Color.WHITE);
    });
    }
}
