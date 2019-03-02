/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.vision.MasterVision;
import org.firstinspires.ftc.teamcode.vision.SampleRandomizedPositions;
import org.firstinspires.ftc.teamcode.vision.TFLite;

@Autonomous(name="Trad Auto", group="BBot")
public class TradAuto extends LinearOpMode {
    BBot robot = new BBot();   // Use robot's hardware
    MasterVision vision;
    SampleRandomizedPositions goldPosition;

    private enum States { // states for the autonomous FSM
        Sampling, LOWERING, CLEAR_LANDER, RETRACT_LIFT, PICKDIRECTION, TURN_LEFT, NAV_LEFT, TURN_2_DEPOT_L, NAV_2_DEPOT_L, CENTER, TURN_RIGHT, NAV_RIGHT, TURN_2_DEPOT_R, NAV_2_DEPOT_R, NAV_2_PIT, DROP_IDOL_TURN, NAV_2_CRATER, ARM_IN_CRATER, STOP, NAV_2_Depot_L_FINAL, NAV_2_CRATER_L;
    }

    public void runOpMode() {
        robot.init(hardwareMap);
        // send telemetry message to signify robot waiting
        telemetry.addData("Status", "Snoozing");
        telemetry.update();

        boolean usingPhoneCam = false; // change to true to do the smart thing and use the phone's cam (not the webcam)

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        if(!usingPhoneCam) {
            parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        }
        else {
            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;// recommended camera direction
        }
        parameters.vuforiaLicenseKey = "AYuwB/n/////AAABmc2iWLR8g0iipnUkJKVfgAYw+QI3BcT5KMR/SavKNiO/7h1HrtK20ekoQerKKc0YoamY11r9MOZzcgz6ku69rBwqrrl08VUqzKn+d49/pW3Gi6SseQMgb5piXwASgO9XHeqCFgmD+NkR52ta3MGEI8X6FGAt3uATqM20EPbIugPpnNjsdCgCav51jMCUI5kvgG4AjO4MIN/kPE4PlJ3ZUI7/lTSDZ8nImPoRuJQ9VWJrjOJzY6/ylE9V5j5r5nkixzVwLJ1GzA0vYsvFc+62J11ZuhiAoc1zxzpe8VK4ibSxwCP1lFRSg+6T8jiX4OXYnzovD4ghLc+0KXtF+hl9niNSkiBY7oaRYGwQW1MlgzJ9";

        vision = new MasterVision(parameters, hardwareMap, true, MasterVision.TFLiteAlgorithm.INFER_RIGHT);
        vision.init();// enables the camera overlay. this will take a couple of seconds
        vision.enable();// enables the tracking algorithms. this might also take a little time

        // wait for the start button to be pressed.
        waitForStart();

        // State machine for robot
        States current_state = States.Sampling;
        telemetry.addData("Status", "Running");
        telemetry.addData("State", "Lowering");
        telemetry.update();
        vision.disable();// disables tracking algorithms. this will free up your phone's processing power for other jobs.

        while (current_state == States.Sampling) {
            goldPosition = vision.getTfLite().getLastKnownSampleOrder();
            telemetry.addData("goldPosition was", goldPosition);// giving feedback

            switch (goldPosition) { // using for things in the autonomous program
                case LEFT:
                    telemetry.addLine("going to the left");
                    break;
                case CENTER:
                    telemetry.addLine("going straight");
                    break;
                case RIGHT:
                    telemetry.addLine("going to the right");
                    break;
                case UNKNOWN:
                    telemetry.addLine("staying put");
                    break;
            }

            telemetry.update();

            vision.shutdown();
            sleep(500);
            current_state = States.LOWERING;
        }


        robot.Lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        robot.resetAllEncoders();

        while (current_state == States.LOWERING) {
            // lower the robot off the hanger
            robot.Lift.setPower(1.0);
            if (robot.Lift.getCurrentPosition() > robot.REVHD401Encoder * 14.5) {
                current_state = States.CLEAR_LANDER;
                robot.Lift.setPower(0.0);
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.Lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (current_state == States.CLEAR_LANDER) {
            robot.setDriveMotors(-0.5, -0.5, -0.5, -0.5);
            if (robot.DriveFrontRight.getCurrentPosition() < -robot.REVHD401Encoder * 0.15) {
                current_state = States.RETRACT_LIFT;
                robot.setDriveMotors(0.0, 0.0, 0.0, 0.0);
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.resetAllEncoders();
        //telemetry.update();
        //telemetry.addData("Lift:" ,robot.Lift.getCurrentPosition());

        while (current_state == States.RETRACT_LIFT) {
            robot.Lift.setPower(-1.0);
            if (robot.Lift.getCurrentPosition() < -robot.REVHD401Encoder * 10) {
                current_state = States.PICKDIRECTION;
                robot.Lift.setPower(0.0);
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();

        while (current_state == States.PICKDIRECTION) {
            switch (goldPosition){ // using for things in the autonomous program
                case LEFT:
                    telemetry.addLine("going to the left");
                    current_state = States.TURN_LEFT;
                    break;
                case CENTER:
                    telemetry.addLine("going straight");
                    current_state = States.CENTER;
                    break;
                case RIGHT:
                    telemetry.addLine("going to the right");
                    current_state = States.TURN_RIGHT;
                    break;
                case UNKNOWN:
                    telemetry.addLine("help! going straight");
                    current_state = States.CENTER;
                    break;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();

        while (current_state == States.TURN_LEFT) {
            telemetry.addLine("TurnLeft");
            robot.setDriveMotors(-0.25, 0.25, -0.25, 0.25);
            if (robot.DriveFrontRight.getCurrentPosition() > robot.REVHD401Encoder * 0.20) {
                robot.setDriveMotors(0, 0, 0, 0);
                current_state = States.NAV_LEFT;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();

        while (current_state == States.NAV_LEFT) {
            telemetry.addLine("NavLeft");
            robot.setDriveMotors(-0.5, -0.5, -0.5, -0.5);
            if (robot.DriveFrontRight.getCurrentPosition() < -robot.REVHD401Encoder * .85) {
                robot.setDriveMotors(0, 0, 0, 0);
                current_state = States.TURN_2_DEPOT_L;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();

        while (current_state == States.TURN_2_DEPOT_L) {
            telemetry.addLine("turn2depotl");
            robot.setDriveMotors(0.25, -0.25, 0.25, -0.25);
            if (robot.DriveFrontRight.getCurrentPosition() < -robot.REVHD401Encoder * 0.18) {
                robot.setDriveMotors(0, 0, 0, 0);
                current_state = States.NAV_2_DEPOT_L;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();

        while (current_state == States.NAV_2_DEPOT_L) {
            telemetry.addLine("Nav2depotL");
            robot.setDriveMotors(-0.25, -0.25, -0.25, -0.25);
            if (robot.DriveFrontRight.getCurrentPosition() < -robot.REVHD401Encoder * 1) {
                robot.setDriveMotors(0, 0, 0, 0);
                current_state = States.DROP_IDOL_TURN;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();

        while (current_state == States.CENTER) {
            telemetry.addLine("Center");
            robot.setDriveMotors(-0.25, -0.25, -0.25, -0.25);
            if (robot.DriveFrontRight.getCurrentPosition() < -robot.REVHD401Encoder * 2.25) {
                robot.StupidStick.setPosition(0);
                robot.setDriveMotors(0, 0, 0, 0);
                current_state = States.STOP;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();

        while (current_state == States.TURN_RIGHT) {
            telemetry.addLine("Turnright");
            telemetry.update();
            robot.setDriveMotors(0.25, -0.25, 0.25, -0.25);
            if (robot.DriveFrontRight.getCurrentPosition() < -robot.REVHD401Encoder * 0.15) {
                robot.setDriveMotors(0, 0, 0, 0);
                current_state = States.NAV_RIGHT;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();

        while (current_state == States.NAV_RIGHT) {
            telemetry.addLine("NavRight");
            telemetry.update();
            robot.setDriveMotors(-0.5, -0.5, -0.5, -0.5);
            if (robot.DriveFrontRight.getCurrentPosition() < -robot.REVHD401Encoder * 1.75) {
                robot.setDriveMotors(0, 0, 0, 0);
                current_state = States.TURN_2_DEPOT_R;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        robot.resetAllEncoders();

        while (current_state == States.TURN_2_DEPOT_R) {
            //telemetry.addLine("Turn2depotr");
            //telemetry.addData("encoder",robot.REVHD401Encoder);
            //telemetry.addData("FrontRight", robot.DriveFrontRight.getCurrentPosition());
            telemetry.update();
            robot.setDriveMotors(-0.5, 0.5, -0.5, 0.5); //turn left
            if (robot.DriveFrontRight.getCurrentPosition() > robot.REVHD401Encoder * 0.35) {
                robot.setDriveMotors(0, 0, 0, 0);
                current_state = States.NAV_2_DEPOT_R;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();

        while (current_state == States.NAV_2_DEPOT_R) {
            telemetry.addLine("nav2depot");
            telemetry.update();
            robot.setDriveMotors(-0.25, -0.25, -0.25, -0.25);
            if (robot.DriveFrontRight.getCurrentPosition() < -robot.REVHD401Encoder * 1) {
                robot.StupidStick.setPosition(0);
                robot.setDriveMotors(0, 0, 0, 0);
                current_state = States.STOP;
            }
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }

        robot.resetAllEncoders();

         //drop idol
        sleep(500);

        while (current_state == States.DROP_IDOL_TURN) {
            switch (goldPosition) { // using for things in the autonomous program
                case LEFT:
                    telemetry.addLine("came from the left");
                    robot.setDriveMotors(0.5, -0.5, 0.5, -0.5); //turns right
                    if (robot.DriveFrontRight.getCurrentPosition() < -robot.REVHD401Encoder * .65) {
                        robot.setDriveMotors(0, 0, 0, 0);
                        current_state = States.NAV_2_Depot_L_FINAL;
                    }
                    break;
                case CENTER:
                    telemetry.addLine("coming from center");
                    robot.setDriveMotors(0.5, -0.5, 0.5, -0.5); //turns right
                    if (robot.DriveFrontRight.getCurrentPosition() < -robot.REVHD401Encoder * 0.5) {
                        robot.setDriveMotors(0, 0, 0, 0);
                        current_state = States.NAV_2_CRATER;
                    }
                    break;
                case RIGHT:
                   // telemetry.addLine("coming from right");
                   // telemetry.update();
                   // robot.setDriveMotors(0.5, -0.5, 0.5, -0.5); //turns right
                  //  if (robot.DriveFrontRight.getCurrentPosition() < -robot.REVHD401Encoder * 0.20) {
                   //     robot.setDriveMotors(0, 0, 0, 0);
                   //     current_state = States.NAV_2_CRATER;
                   // }
                    break;
                case UNKNOWN:
                    telemetry.addLine("coming from center");
                    robot.setDriveMotors(0.5, -0.5, 0.5, -0.5); //turns right
                    if (robot.DriveFrontRight.getCurrentPosition() < -robot.REVHD401Encoder * 0.5) {
                        robot.setDriveMotors(0, 0, 0, 0);
                        current_state = States.NAV_2_CRATER;
                    }
                    break;
            }


            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
        while(current_state == States.NAV_2_Depot_L_FINAL)
        {
telemetry.addLine("moving to depot");
robot.setDriveMotors(-.5,-.5,-.5,-.5);
if(robot.DriveFrontRight.getCurrentPosition()< - robot.REVHD401Encoder *1.25)
{
    robot.StupidStick.setPosition(0);
    robot.setDriveMotors(0,0,0,0);
    current_state = States.NAV_2_CRATER_L;
}
        }


        robot.resetAllEncoders();
        while(current_state == States.NAV_2_DEPOT_L)
        {
            telemetry.addLine("YEET YEET");
            robot.setDriveMotors(.5,.5,.5,.5);
            if(robot.DriveFrontRight.getCurrentPosition()< - robot.REVHD401Encoder *1.25) {
                robot.setDriveMotors(0,0,0,0);
                current_state = States.STOP;
            }

        }
        robot.resetAllEncoders();

        while (current_state == States.NAV_2_CRATER) {
            current_state = States.STOP;
          //  robot.setDriveMotors(.5, .5, .5, .5);
           // if (robot.DriveFrontRight.getCurrentPosition() > robot.REVHD401Encoder * 5) {
            //   current_state = States.ARM_IN_CRATER;
            //    robot.setDriveMotors(0, 0, 0, 0);
           // }
           // if (!opModeIsActive()) return; // check termination in the innermost loop
          //
         }

        robot.resetAllEncoders();

        while (current_state == States.ARM_IN_CRATER) {
            robot.setArmUpDownMotors(1.0);
            if (robot.ArmUpDownR.getCurrentPosition() > robot.REVHD401Encoder * 2) {
                robot.setArmUpDownMotors(0);
                current_state = States.STOP;
            }
        }


        telemetry.addData("Status", "Finished");
        telemetry.addData("State", "Stop");
        telemetry.update();
        while (current_state == States.STOP) {
            // stop all motors
            robot.stopAllMotors();
            if (!opModeIsActive()) return; // check termination in the innermost loop
        }
    }
}
