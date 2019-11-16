package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Basic", group="BBot")
public class BasicAuto extends LinearOpMode {
    ProgrammingBot robot = new ProgrammingBot();   // Use robot's hardware
    Gyro gyro;

    private enum States { // states for the autonomous FSM
        MOVE_2_STONES, TURN_2_SCAN, SCAN_STONES, GET_NEW_STONE, MOVE_BACK, MOVE_INTO_STONES, INTAKE_STONE, STOP;
    }

    public void runOpMode() {
        States current_state = States.MOVE_2_STONES;

        // force print the initialize message before the long gyro init
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        // gyro init
        robot.init(hardwareMap);
        gyro = new Gyro(robot.hwMap, "imu 1", this); // special initialization for gyro
        gyro.start();

        // Set the LED in the beginning
        robot.RCS.enableLed(true);

        // Select red or blue starting position
        boolean redSelected = false; // start off with blue selected
        boolean DiscreteButtonReleased = true;
        while(true) {
            telemetry.addData("Alliance", redSelected ? "Red" : "Blue");
            telemetry.addLine("Press DPad Up/Down to change selection, A to confirm");
            if((gamepad1.dpad_down || gamepad1.dpad_up) && DiscreteButtonReleased) {
                redSelected = !redSelected;
                DiscreteButtonReleased = false; // ignore future presses until button is released
            }
            else if(gamepad1.a) { // selection confirmed, break out of input loop and wait for auto to start
                DiscreteButtonReleased = true;
                telemetry.clearAll();
                break;
            }

            if(!gamepad1.dpad_up && !gamepad1.dpad_down) // start listening to button presses if button has been released
                DiscreteButtonReleased = true;

            telemetry.update();
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        telemetry.addData("Alliance set to", redSelected ? "red" : "blue");
        telemetry.addData("Status", "Ready!");
        telemetry.update();

        // Wait for the start button to be pressed on the driver station
        waitForStart();

        // State machine for robot
        telemetry.addData("Status", "Running");
        telemetry.addData("State", "Moving to stones");
        telemetry.update();

        robot.resetAllEncoders();
        gyro.resetHeading();

        while (current_state == States.MOVE_2_STONES) {
            // move from wall to stones
            robot.setDriveMotors(0.5, 0.5, 0.5, 0.5);
            telemetry.update();
            if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(34)) {
                current_state = States.TURN_2_SCAN;
                robot.setDriveMotors(0.0, 0.0, 0.0, 0.0);
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.stopAllMotors();
        sleep(1000);

        telemetry.addData("State", "Turning");
        telemetry.update();

        gyro.resetHeading();
        telemetry.addData("Gyro position: ", gyro.globalHeading);

        // Set motor speed as a proportion of angle to go
        // This should prevent the turn from overshooting
        double motorSpeed = 0.5;
        int degreeSet = 30;
        sleep(100);

        while (current_state == States.TURN_2_SCAN) {
            robot.setDriveMotors(-motorSpeed, motorSpeed, -motorSpeed, motorSpeed);
            telemetry.addData("Gyro position: ", gyro.globalHeading);
            telemetry.update();
            if (gyro.globalHeading > degreeSet && gyro.globalHeading < 90) {
                motorSpeed = motorSpeed / 1.2;
                degreeSet = degreeSet + 35;
            }
            else if (gyro.globalHeading > 90) {
                current_state = States.STOP;
                robot.stopAllMotors();
                telemetry.addData("Gyro position: ", gyro.globalHeading);
                telemetry.update();
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        telemetry.addData("State: ", "Scanning");
        robot.stopAllMotors();
        sleep(5000);

        while (current_state == States.SCAN_STONES) {
            if (robot.RCS.alpha() > 150) {    //skystone
                robot.stopAllMotors();
                current_state = States.MOVE_BACK;
            } else {
                robot.stopAllMotors();
                current_state = States.GET_NEW_STONE;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.stopAllMotors();
        sleep(5000);

        while (current_state == States.GET_NEW_STONE) {
            robot.setDriveMotors(0.5, 0.5, 0.5, 0.5);
            if (robot.DriveFrontLeft.getCurrentPosition() > 1300) {
                robot.stopAllMotors();
                current_state = States.SCAN_STONES;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        while (current_state == States.MOVE_BACK) {
            robot.setDriveMotors(-0.5, -0.5, -0.5, -0.5);
            if (robot.DriveFrontLeft.getCurrentPosition() < 1300) {
                current_state = States.MOVE_INTO_STONES;
                robot.stopAllMotors();
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        while (current_state == States.MOVE_INTO_STONES) {
            robot.setDriveMotors(0.5, -0.5, -0.5, 0.5);
            if (robot.DriveFrontLeft.getCurrentPosition() > 1300) {
                current_state = States.STOP;
                robot.stopAllMotors();
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        while (current_state == States.INTAKE_STONE) {
            if (robot.IntakeTouch.isPressed()) { // button is pressed.
                telemetry.addData("Button", "PRESSED");
                robot.IntakeLeft.setPower(0.0);
                robot.IntakeRight.setPower(0.0);
            }
            else { // button is not pressed.
                telemetry.addData("Button", "NOT PRESSED");
                robot.IntakeLeft.setPower(1.0);
                robot.IntakeRight.setPower(1.0);
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        //telemetry.addData("Angle", gyro.globalHeading);

        while (current_state == States.STOP) {
            robot.stopAllMotors();
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
    }
}