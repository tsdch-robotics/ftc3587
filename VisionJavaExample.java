package org.firstinspires.ftc.teamcode.vision;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@TeleOp
@Disabled
public class VisionJavaExample extends LinearOpMode{
    MasterVision vision;
    SampleRandomizedPositions goldPosition;

    @Override
    public void runOpMode() throws InterruptedException {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;// recommended camera direction
        parameters.vuforiaLicenseKey = "AYuwB/n/////AAABmc2iWLR8g0iipnUkJKVfgAYw+QI3BcT5KMR/SavKNiO/7h1HrtK20ekoQerKKc0YoamY11r9MOZzcgz6ku69rBwqrrl08VUqzKn+d49/pW3Gi6SseQMgb5piXwASgO9XHeqCFgmD+NkR52ta3MGEI8X6FGAt3uATqM20EPbIugPpnNjsdCgCav51jMCUI5kvgG4AjO4MIN/kPE4PlJ3ZUI7/lTSDZ8nImPoRuJQ9VWJrjOJzY6/ylE9V5j5r5nkixzVwLJ1GzA0vYsvFc+62J11ZuhiAoc1zxzpe8VK4ibSxwCP1lFRSg+6T8jiX4OXYnzovD4ghLc+0KXtF+hl9niNSkiBY7oaRYGwQW1MlgzJ9";

        vision = new MasterVision(parameters, hardwareMap, true, MasterVision.TFLiteAlgorithm.INFER_NONE);
        vision.init();// enables the camera overlay. this will take a couple of seconds
        vision.enable();// enables the tracking algorithms. this might also take a little time

        waitForStart();

        vision.disable();// disables tracking algorithms. this will free up your phone's processing power for other jobs.

        goldPosition = vision.getTfLite().getLastKnownSampleOrder();

        while(opModeIsActive()){
            telemetry.addData("goldPosition was", goldPosition);// giving feedback

            switch (goldPosition){ // using for things in the autonomous program
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
        }

        vision.shutdown();
    }
}