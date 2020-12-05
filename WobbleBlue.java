package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.ChampBot;
import org.firstinspires.ftc.teamcode.Gyro;
import org.firstinspires.ftc.teamcode.ParkingBlue;

@Autonomous(name="ParkingLeft", group="ChampBot")
public class WobbleBlue extends LinearOpMode {
    ChampBot robot = new ChampBot();   // Use robot's hardware

    private enum States { // states for the autonomous FSM
        DRIVE_OUT1, TURN_RIGHT1, TURN_RIGHT2, DRIVE_OUT2, DRIVE_OUT3, DRIVE_OUT4, DRIVE_OUT5, TURN_LEFT1, TURN_LEFT2, DRIVE_BACK2, DRIVE_BACK1, STOP;
    }

    public void runOpMode() {
        robot.init(hardwareMap);
        Gyro gyro;

        States current_state = States.DRIVE_OUT1;

        // send telemetry message to signify robot waiting
        telemetry.addData("Status: ", "Snoozing");
        telemetry.update();


        // wait for the start button to be pressed.
        waitForStart();

        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.DRIVE_OUT1) {
            robot.setDriveMotors(0.4, 0.4, 0.4, 0.4); //drive forwards
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(5.0)) {
                robot.stopAllMotors();
                current_state = States.TURN_RIGHT1;

            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.TURN_RIGHT1) {
            robot.setDriveMotors(0.4,  -0.4, 0.4, -0.4); //turn right
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(19.21)) {
                robot.stopAllMotors();
                current_state = States.DRIVE_OUT2;

            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.DRIVE_OUT2) {
            robot.setDriveMotors(0.4, 0.4, 0.4, 0.4); //drive forwards
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(52.0)) {
                robot.stopAllMotors();
                current_state = States.DRIVE_BACK1;

            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.DRIVE_BACK1) {
            robot.setDriveMotors(-0.4, -0.4, -0.4, -0.4); //drive forwards
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(45.0)) {
                robot.stopAllMotors();
                current_state = States.TURN_RIGHT2;

            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.TURN_RIGHT2) {
            robot.setDriveMotors(0.4, -0.4, 0.4, -0.4); //turn right
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(19.21)) {
                robot.stopAllMotors();
                current_state = States.DRIVE_OUT3;

            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.DRIVE_OUT3) {
            robot.setDriveMotors(0.4, 0.4, 0.4, 0.4); //drive forwards
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(33.0)) {
                robot.stopAllMotors();
                current_state = States.TURN_LEFT1;

            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.TURN_LEFT1) {
            robot.setDriveMotors(-0.4, 0.4, -0.4, 0.4);//turn left
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(19.21)) {
                robot.stopAllMotors();
                current_state = States.DRIVE_OUT4;

            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.DRIVE_OUT4) {
            robot.setDriveMotors(0.4, 0.4, 0.4, 0.4); //drive forwards
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(45.0)) {
                robot.stopAllMotors();
                current_state = States.TURN_LEFT2;

            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.TURN_LEFT2) {
            robot.setDriveMotors(-0.4, 0.4, -0.4, 0.4); //turn left
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(19.21)) {
                robot.stopAllMotors();
                current_state = States.DRIVE_OUT5;

            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.DRIVE_OUT5) {
            robot.setDriveMotors(0.4, 0.4, 0.4, 0.4); //drive forwards
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(33.0)) {
                robot.stopAllMotors();
                current_state = States.DRIVE_BACK2;

            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.DRIVE_BACK2) {
            robot.setDriveMotors(-0.4, -0.4, -0.4, -0.4); //drive forwards
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(10.0)) {
                robot.stopAllMotors();
                current_state = States.STOP;

            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.resetAllEncoders();
        telemetry.addData("Status", current_state);
        telemetry.update();
        sleep(100);

        while (current_state == States.STOP) {
            // stop all motors
            robot.stopAllMotors();
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
    }
}