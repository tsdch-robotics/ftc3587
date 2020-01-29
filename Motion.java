package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.ChampBot;
import org.firstinspires.ftc.teamcode.Gyro;

public class Motion {
    Motion motion;


    public Gyro gyro;

    public void gyroInit(HardwareMap hwMap) {
        // gyro init
        gyro = new Gyro(hwMap, "imu"); // special initialization for gyro
        gyro.start();
    }

    public void turnToHeading(double target_heading, double speedModifier) {
        boolean goRight;
        double currentHeading;
        double degreesToTurn;
        double wheelPower;
        double prevHeading = 0;
        ElapsedTime timeoutTimer = new ElapsedTime();

        currentHeading = gyro.getHeading();
        degreesToTurn = Math.abs(target_heading - currentHeading);

        goRight = target_heading > currentHeading;

        if (degreesToTurn > 180) {
            goRight = !goRight;
            degreesToTurn = 360 - degreesToTurn;
        }

        timeoutTimer.reset();
        prevHeading = currentHeading;
        while (degreesToTurn > .5 && /*opModeIsActive() &&*/ timeoutTimer.seconds() < 2) {

            if (speedModifier < 0) {
                wheelPower = (Math.pow((degreesToTurn + 25) / -speedModifier, 3) + 15) / 100;
            } else {
                if (speedModifier != 0) {
                    wheelPower = (Math.pow((degreesToTurn) / speedModifier, 4) + 35) / 100;
                } else {
                    wheelPower = (Math.pow((degreesToTurn) / 30, 4) + 15) / 100;
                }
            }

            if (goRight) {
                wheelPower = -wheelPower;
            }

            robot.setDriveMotors(-wheelPower, wheelPower, -wheelPower, wheelPower);

            currentHeading = gyro.getHeading();

            degreesToTurn = Math.abs(target_heading - currentHeading);       // Calculate how far is remaining to turn

            goRight = target_heading > currentHeading;

            if (degreesToTurn > 180) {
                goRight = !goRight;
                degreesToTurn = 360 - degreesToTurn;
            }

            if (Math.abs(currentHeading - prevHeading) > 1) {  // if it has turned at least one degree
                timeoutTimer.reset();
                prevHeading = currentHeading;
            }

        }

        robot.setDriveMotors(0, 0, 0, 0);
    }

    public void resetHeading() {
        gyro.resetHeading();
    }
}
