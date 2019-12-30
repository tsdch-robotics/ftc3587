package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.State;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name="Skystone sensing", group="BBot")
public class BasicAuto extends LinearOpMode {
    // Robot hardware
    ProgrammingBot robot = new ProgrammingBot();
    Gyro gyro;

    // Robot telemetry
    Telemetry.Item telStatus;
    Telemetry.Item telState;
    Telemetry.Item telValue;

    // Indicate whether we need to transition states
    public enum ExitCodes { RUN, NEXT }

    // Define names for each state and a variable to keep of which state we're in
    public enum States {
        MOVE_2_STONES, FIND_STONE, DONE;
    }
    States currentState = States.MOVE_2_STONES;

    // Run another tick of the autonomous FSM; return to the caller a code indicating whether a state transition is needed
    public ExitCodes runState() {
        ExitCodes exitCode = ExitCodes.RUN; // by default assume we should continue running the same state
        switch(currentState)
        {
            case MOVE_2_STONES:
            {
                exitCode = run_MOVE_2_STONES();
                break;
            }
            case FIND_STONE:
            {
                exitCode = run_FIND_STONE();
                break;
            }
            default: // any unknown/unhandled state should safely redirect to "done"
            {
                run_DONE();
            }
        }
        return exitCode; // pass the exit code back out to the caller
    }

    // Transition to the next state of the autonomous FSM
    public void transitionState() {
        switch(currentState)
        {
            case MOVE_2_STONES:
            {
                robot.stopAllMotors();
                sleep(1000);
                // Set up telemetry for the next stage
                telValue.setCaption("Left color sensor");
                currentState = States.FIND_STONE;
                break;
            }
            case FIND_STONE:
            {
                currentState = States.DONE;
                robot.stopAllMotors();
                break;
            }
        }
    }

    public ExitCodes run_MOVE_2_STONES() {
        robot.setDriveMotors(0.5, 0.5, 0.5, 0.5);
        // If we've moved a sufficient distance...
        if (robot.DriveFrontLeft.getCurrentPosition() >= robot.inchesToEncoderCounts(24)) {
            robot.stopAllMotors();
            return ExitCodes.NEXT; // advance to the next state
        }
        return ExitCodes.RUN; // continue running this state
    }

    // Since Java doesn't have static variables, use a private global
    private int blocksScanned = 0;
    public ExitCodes run_FIND_STONE() {
        telValue.setValue(robot.LCS.alpha());
        telemetry.update();

        if(robot.LCS.alpha() < 90) { // found stone!
            telValue.setCaption("Skystone");
            telValue.setValue("Found!");
            return ExitCodes.NEXT;
        }
        else { // try to find another stone
            blocksScanned++;
            robot.strafeLeft(0.66);
            sleep(400);
            // correct strafing drift
            robot.resetAllEncoders();
            robot.setDriveMotors(0.3, 0, 0.3, 0);
            while(robot.DriveFrontLeft.getCurrentPosition() < 4); // strafing correction
            robot.stopAllMotors();
            sleep(1250);
        }

        if(blocksScanned < 4) return ExitCodes.RUN;
        else return ExitCodes.NEXT; // didn't find a block and ran out of blocks to check
    }


    public ExitCodes run_DONE() { // Stop motors, do nothing, don't transition
        robot.stopAllMotors();
        telemetry.update();
        return ExitCodes.RUN;
    }

    public void runOpMode() {
        // force print the initialize message before the long gyro init
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        // gyro init
        robot.init(hardwareMap);
        gyro = new Gyro(robot.hwMap, "imu"); // special initialization for gyro
        gyro.start();

        // Enable the LED to get more consistent readings
        robot.LCS.enableLed(true);

        // Select red or blue starting position
        boolean redSelected = false; // start off with blue selected
        boolean DiscreteButtonReleased = true; // start off with button released
        while(true) {
            telemetry.addData("Alliance", redSelected ? "Red" : "Blue");
            telemetry.addLine("Press DPad Up/Down to change selection, Y to confirm");
            if((gamepad1.dpad_down || gamepad1.dpad_up) && DiscreteButtonReleased) {
                redSelected = !redSelected;
                DiscreteButtonReleased = false; // ignore future presses until button is released
            }
            else if(gamepad1.y) { // selection confirmed, break out of input loop and wait for auto to start
                DiscreteButtonReleased = true;
                telemetry.clearAll();
                break;
            }

            if(!gamepad1.dpad_up && !gamepad1.dpad_down) // start listening to button presses if button has been released
                DiscreteButtonReleased = true;

            telemetry.update();
        }

        telemetry.addData("Alliance set to", redSelected ? "red" : "blue");
        telemetry.addData("Status", "Ready!");
        telemetry.addData("Stuck at stop thresh", msStuckDetectStop);
        telemetry.update();

        // Wait for the start button to be pressed on the driver station
        waitForStart();

        // <<<<<< AUTONOMOUS STARTS HERE >>>>>>
        // Perform telemetry init
        telemetry.clearAll();
        telStatus = telemetry.addData("Status", "Running");
        telState = telemetry.addData("State", "INIT");
        telValue = telemetry.addData("Value", "none");
        telemetry.setAutoClear(false); // From here on don't autoclear the screen
        telemetry.update(); // Update the screen to indicate we are running


        // Reset everything in case the robot got bumped around during init
        robot.resetAllEncoders();
        gyro.resetHeading();

        // Run the autonomous finite state machine (FSM)
        while(opModeIsActive()) {
            telState.setValue(currentState);
            telemetry.update();
            // check if the opMode is still active after running but before transitioning
            if(runState() == ExitCodes.NEXT && opModeIsActive()){
                transitionState();
            }
        }
        // opMode is no longer active, terminate the application
        gyro.exit = true;
        return;

        // <<<<<<<<<<<<<<<<<<<< OLD CODE BELOW HERE >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
//
//        int blocksScanned = 0;
//        while (current_state == States.FIND_STONE && blocksScanned < 4) {
//            telemetry.addData("Color sensor", robot.RCS.alpha());
//            telemetry.update();
//            if (robot.RCS.alpha() < 90) { // found a stone!
//                telemetry.addData("State: ", "Found stone!");
//                telemetry.update();
//                current_state = States.MOVE_BACK;
//            }
//            else { // advance to the next stone
//                blocksScanned++;
//                robot.resetAllEncoders();
//                while (robot.DriveFrontLeft.getCurrentPosition() < robot.inchesToEncoderCounts(10)) {
//                    robot.setDriveMotors(0.4, 0.4, 0.4, 0.4);
//                    if (!opModeIsActive()) return;
//                }
//                robot.stopAllMotors();
//                sleep(1000);
//            }
//            if (!opModeIsActive()) return;
//        }
//
//        telemetry.addData("State", "Moving back");
//        telemetry.update();
//        robot.resetAllEncoders();
//        sleep(3000);
//
//        while (current_state == States.MOVE_BACK) {
//            robot.setDriveMotors(-0.4, -0.4, -0.4, -0.4);
//            if (robot.DriveFrontLeft.getCurrentPosition() < robot.inchesToEncoderCounts(-12)) {
//                robot.stopAllMotors();
//                current_state = States.STRAFE_AWAY;
//            }
//            if (!opModeIsActive()) return;
//        }
//
//        telemetry.addData("State", "Strafing away");
//        telemetry.update();
//        robot.resetAllEncoders();
//        sleep(3000);
//
//        while (current_state == States.STRAFE_AWAY) {
//            robot.setDriveMotors(-1, 1, 1, -1);
//            if(robot.DriveFrontLeft.getCurrentPosition() < robot.inchesToEncoderCounts(-48)) {
//                robot.stopAllMotors();
//                current_state = States.STOP;
//            }
//            if (!opModeIsActive()) return;
//        }
//
//
//        // block detection loop
//
//        gyro.resetHeading();
//
//        motorSpeed = 0.5;
//        degreeSet = 30;
//        sleep(100);
//
//        while (current_state == States.TURN_2_INTAKE) {
//            robot.setDriveMotors(motorSpeed, -motorSpeed, motorSpeed, -motorSpeed);
//            telemetry.addData("Gyro position", gyro.globalHeading);
//            telemetry.update();
//            if (gyro.globalHeading > degreeSet && gyro.globalHeading > -45) {
//                motorSpeed = motorSpeed / 1.2;
//                degreeSet = degreeSet + 35;
//            }
//            else if (gyro.globalHeading < -45) {
//                current_state = States.FIND_STONE;
//                robot.stopAllMotors();
//            }
//            if (!opModeIsActive()) return; // check termination in the innermost loop
//        }
//
//        while (current_state == States.INTAKE_STONES) {
//            if (robot.IntakeTouch.isPressed()) { // button is pressed.
//                telemetry.addData("Button", "PRESSED");
//                robot.IntakeLeft.setPower(0.0);
//                robot.IntakeRight.setPower(0.0);
//                robot.setDriveMotors(0,0,0,0);
//                current_state = States.STOP;
//            }
//            else { // button is not pressed.
//                telemetry.addData("Button", "NOT PRESSED");
//                robot.IntakeLeft.setPower(1.0);
//                robot.IntakeRight.setPower(1.0);
//                robot.setDriveMotors(0.5,0.5,0.5,0.5);
//            }
//            if (!opModeIsActive()) return; // check termination in the innermost loop
//        }
//        //telemetry.addData("Angle", gyro.globalHeading);
//        telemetry.addData("Status", "Done");
//        telemetry.update();
//        while (current_state == States.STOP) {
//            robot.stopAllMotors();
//            if (!opModeIsActive()) return; // check termination in the innermost loop
//        }
    }
}