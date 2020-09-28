package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Vision.VuforiaStuff;

/* (RR: commented out, error Class 'Motion' must either be declared abstract or implement abstract method 'runOpMode()' in 'linearOpMode')
@Autonomous(name="Motion", group="ChampBot")
public class Motion extends LinearOpMode {
    ChampBot robot = new ChampBot();   // Use robot's hardware

    public void DriveForward(double motorPower, double distanceInches) {
        while (opModeIsActive()) {
            robot.setDriveMotors(motorPower, motorPower, motorPower, motorPower); // moves away from blocks
            if (robot.DriveFrontLeft.getCurrentPosition() >= robot.inchesToEncoderCounts(distanceInches)) {
                robot.stopAllMotors();
                return;
            }
        }
    }

    public void Turn(double motorPower, double angle, boolean right) {

    }
}
*/
