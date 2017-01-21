
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Redemergency", group="FinalBot")
public class Redemergency extends LinearOpMode {

    /* Declare OpMode members. */
    FinalBot robot = new FinalBot();   //


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
        // code runs REPEATEDLY after the driver hits INIT, but before they hit PLAY

        sleep(500);
        waitForStart();
        robot.FrontMotorLeft.setPower(0.2);
        robot.FrontMotorRight.setPower(-0.2);
        robot.BackMotorLeft.setPower(-0.2);
        robot.BackMotorRight.setPower(0.2);
        sleep(2500);
        robot.FrontMotorLeft.setPower(0.0);
        robot.FrontMotorRight.setPower(0.0);
        robot.BackMotorLeft.setPower(0.0);
        robot.BackMotorRight.setPower(0.0);
        sleep(1000);
        /**robot.FrontMotorLeft.setPower(1.0);
        robot.FrontMotorRight.setPower(1.0);
        robot.BackMotorLeft.setPower(1.0);
        robot.BackMotorRight.setPower(1.0);
        sleep(3000);
        robot.FrontMotorLeft.setPower(0.0);
        robot.FrontMotorRight.setPower(0.0);
        robot.BackMotorLeft.setPower(0.0);
        robot.BackMotorRight.setPower(0.0);
        sleep(1000);
        robot.FrontMotorLeft.setPower(-0.5);
        robot.FrontMotorRight.setPower(0.5);
        robot.BackMotorLeft.setPower(-0.5);
        robot.BackMotorRight.setPower(0.5);
        sleep(4000);
        robot.FrontMotorLeft.setPower(0.0);
        robot.FrontMotorRight.setPower(0.0);
        robot.BackMotorLeft.setPower(0.0);
        robot.BackMotorRight.setPower(0.0);
        sleep(100000); **/
    }
}