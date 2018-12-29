package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

public class Gyro {
    BNO055IMU imu; // IMU = gyro + accelerometer
    public double lastAngle;
    public double globalAngle;

    /**
     * Initialize the REV controller's builtin IMU.
     * @param hwMap: the robot's hardware map (usually robot.hwMap).
     * @param imuName: the name of the IMU in the robot configuration (usually "imu").
     */
    public Gyro(HardwareMap hwMap, String imuName) {
        // get the REV controller's builtin imu
        imu = hwMap.get(BNO055IMU.class, imuName);

        // set up parameters
        BNO055IMU.Parameters params = new BNO055IMU.Parameters();
        params.mode = BNO055IMU.SensorMode.IMU;
        params.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        params.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        params.loggingEnabled = false;
        imu.initialize(params);

        while (imu.isGyroCalibrated()) { // wait for gyro to be calibrated
            try { java.lang.Thread.sleep(50); } catch (Exception ex) {} // funky-looking sleep
        }
    }

    public double getAngle() {
        // read the Z axis angle, accounting for the transition from +180 <-> -180
        double rawImuAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        double delta = rawImuAngle - lastAngle;

        // An illustrative example: assume the robot is facing +179 degrees (last angle) and makes a +2 degree turn.
        // The raw IMU value will roll over from +180 to -180, so the final raw angle will be -179.
        // So delta = -179 - (+179) = -358.
        // Since delta is less than -180, add 360 to it: -358 + 360 = +2 (the amount we turned!)
        // This works the same way in the other direction.

        if(delta > 180) delta -= 360;
        else if(delta < -180) delta += 360;

        globalAngle += delta; // change the global state
        lastAngle = rawImuAngle; // save the current raw Z state
        return globalAngle;
    }

    public void resetAngle() {
        lastAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
        globalAngle = 0;
    }
}
