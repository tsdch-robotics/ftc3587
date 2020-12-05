package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name= "Test1", group = "ChampBot")
public class test1 extends LinearOpMode{

    ChampBot robot = new ChampBot();


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);
        waitForStart();

        robot.DriveFrontLeft.setPower(1);
        robot.DriveBackLeft.setPower(1);
        robot.DriveFrontRight.setPower(1);
        robot.DriveBackRight.setPower(1);
        sleep(500);

        robot.DriveFrontLeft.setPower(0);
        robot.DriveBackLeft.setPower(0);
        robot.DriveFrontRight.setPower(0);
        robot.DriveBackRight.setPower(0);

        telemetry.addData("Path", "Complete");
        telemetry.update();

    }
}
