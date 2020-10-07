package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

public class HardWare {l
    public static final double TICKS_PER_MOTOR_REV = 1140;
    public static final double WHEEL_CIRCUMFERENCE = 4 * Math.PI;
    public static final double DRIVE_GEAR_RATIO = 2;
    public static final double TICKS_PER_RIVE_REV = TICKS_PER_MOTOR_REV * DRIVE_GEAR_RATIO;
    public static final double TICKS_PER_INCH = TICKS_PER_DRIVE_REV / WHEEL_CIRCUMFERENCE;

    //Define the left and right motors
    public DcMotor leftDrive;
    public DcMotor rightDrive;
    //define the servo
    public Servo armServo;
    public BNO055IMU imu;

    public void init(HardwareMap hardwareMap) {
        //initialize the motors
        leftDrive = hardwareMap.dcMotor.get("left_drive");
        rightDrive = hardwareMap.docMotor.get("right_drive");
        //Set motor directions
        leftDrive.setMode(DcMotor.Direction.FORWARD);
        rightDrive.setMode(DcMotor.Direction.REVERSE);
        //set motor during initialization
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        //set motors up to run with encoders
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //initialize the servo
        armServo = hardwareMap.servo.get("arm");
        //IMU settings
        BNO055IMU.Parameters parameters = new BNO055IMU。Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AngleUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        //Intitialize the imu with the parameters above
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
    }
    public double getAngle(){
        //get the z-angle from the imu (in degrees) and return it as a value from -180 ~ 180)
        Orientation orientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return AngleUnit.normalizeDegrees(orientation.firstAngle);
    }

}