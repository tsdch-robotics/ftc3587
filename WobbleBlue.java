
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

// below is the Annotation that registers this OpMode with the FtcRobotController app.
// @Autonomous classifies the OpMode as autonomous, name is the OpMode title and the
// optional group places the OpMode into the Exercises group.
// uncomment the @Disable annotation to remove the OpMode from the OpMode list.

@Autonomous(name="WobbleBlue", group="ChampBot")
//@Disabled
public class WobbleBlue extends LinearOpMode {
    DcMotor FrontLeftMotor;
    DcMotor FrontRightMotor;
    DcMotor BackLeftMotor;
    DcMotor BackRightMotor;
    int milliseconds = 0;
    double LeftPower = 0;
    double RightPower = 0;



    // called when init button is  pressed.

    @Override
    public void runOpMode() throws InterruptedException {
        FrontLeftMotor = hardwareMap.dcMotor.get("DriveFrontLeft");
        FrontRightMotor = hardwareMap.dcMotor.get("DriveFrontRight");
        BackLeftMotor = hardwareMap.dcMotor.get("DriveBackLeft");
        BackRightMotor = hardwareMap.dcMotor.get("DriveBackRight");
        FrontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        FrontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        BackLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        DriveRobot(4000,.5,.5);
        //DriveRobot(1200,-.5,.5);
    }

<<<<<<< HEAD
    public void runOpMode() {
        robot.init(hardwareMap);
        Gyro gyro;
        gyro = new Gyro(robot.hwMap, "imu"); // special initialization for gyro
        gyro.start();

        WobbleBlue.States current_state = WobbleBlue.States.DRIVE_OUT;

        // send telemetry message to signify robot waiting
        telemetry.addData("Status: ", "Snoozing");
=======
    private void DriveRobot(int milliseconds, double LeftPower, double RightPower) {
        telemetry.addData("Mode", "waiting");
>>>>>>> deaee019e2c1e2689366cf0d0eade2a65936ccbb
        telemetry.update();

        // wait for start button.

        waitForStart();

<<<<<<< HEAD
        gyro.resetHeading();
        robot.resetAllEncoders();
        telemetry.addData("State: ", current_state);
        telemetry.update();
        sleep(500);

        if (gamepad1.a) {
            while (current_state == WobbleBlue.States.DRIVE_OUT) {
                robot.setDriveMotors(0.4, 0.4, 0.4, 0.4); //drive forwards
                if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(5.0)) {
                    robot.stopAllMotors();
                    current_state = States.TURN_RIGHT;

                }
                if (!opModeIsActive()) return; // check termination in the innermost loop
            }
                robot.resetAllEncoders();
                telemetry.addData("Status", current_state);
                telemetry.update();
                sleep(100);

            while (current_state == WobbleBlue.States.TURN_RIGHT) {
                robot.setDriveMotors(0.4,  -0.4, 0.4, -0.4); //drive forwards
                if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(19.21)) {
                    robot.stopAllMotors();
                    current_state = States.DRIVE_OUT;

                }
                if (!opModeIsActive()) return; // check termination in the innermost loop
            }
                robot.resetAllEncoders();
                telemetry.addData("Status", current_state);
                telemetry.update();
                sleep(100);

            while (current_state == WobbleBlue.States.DRIVE_OUT) {
                robot.setDriveMotors(0.4, 0.4, 0.4, 0.4); //drive forwards
                if (robot.DriveFrontLeft.getCurrentPosition() > robot.inchesToEncoderCounts(52.0)) {
                    robot.stopAllMotors();
                    current_state = States.DRIVE_BACK;

                }
                if (!opModeIsActive()) return; // check termination in the innermost loop
            }
                robot.resetAllEncoders();
                telemetry.addData("Status", current_state);
                telemetry.update();
                sleep(100);
        }
=======
        telemetry.addData("Mode", "running");
        telemetry.update();

        // set both motors to x power.

        FrontLeftMotor.setPower(LeftPower);
        FrontRightMotor.setPower(RightPower);
        BackLeftMotor.setPower(LeftPower);
        BackRightMotor.setPower(RightPower);


        sleep(milliseconds);        // wait for x seconds.

        // set motor power to zero to stop motors.

        FrontLeftMotor.setPower(0);
        FrontRightMotor.setPower(0);
        BackLeftMotor.setPower(0);
        BackRightMotor.setPower(0);
>>>>>>> deaee019e2c1e2689366cf0d0eade2a65936ccbb
    }
}