package org.firstinspires.ftc.teamcode;

/**
 * Created by cvn on 11/19/17.
 * <p>
 * Example RegisterOpModes
 * Christopher von Nagy
 */

import com.qualcomm.robotcore.eventloop.opmode.OpModeManager;
import com.qualcomm.robotcore.eventloop.opmode.OpModeRegistrar;

import org.firstinspires.ftc.robotcontroller.external.samples.*;

public class RegisterOpModes {
    @OpModeRegistrar
    public static void registerMyOpModes(OpModeManager manager) {

        //manager.register("Scorpio TeleOp",            ScorpioTeleop.class);


        // Un-comment any line to enable that sample.
        // Or add your own lines to register your Team opmodes.

        // Basic Templates
        // manager.register("Iterative Opmode",       BasicOpMode_Iterative.class);
        // manager.register("Linear Opmode",          BasicOpMode_Linear.class);

        // Driving Samples
        // manager.register("Teleop POV",             PushbotTeleopPOV_Linear.class);
        // manager.register("Teleop Tank",            PushbotTeleopTank_Iterative.class);
        // manager.register("Auto Drive Gyro",        PushbotAutoDriveByGyro_Linear.class);
        // manager.register("Auto Drive Encoder",     PushbotAutoDriveByEncoder_Linear.class);
        // manager.register("Auto Drive Time",        PushbotAutoDriveByTime_Linear.class);
        // manager.register("Auto Drive Line",        PushbotAutoDriveToLine_Linear.class);
        // manager.register("K9 Telop",               K9botTeleopTank_Linear.class);

        // Sensor Samples
        // manager.register("BNO055 IMU",             SensorBNO055IMU.class);
        // manager.register("BNO055 IMU Cal",         SensorBNO055IMUCalibration.class);
        // manager.register("AdaFruit Color",         SensorAdafruitRGB.class);
        // manager.register("Digital Touch",          SensorDigitalTouch.class);
        // manager.register("DIM DIO",                SensorDIO.class);
        // manager.register("HT Color",               SensorHTColor.class);
        // manager.register("HT Gyro",                SensorHTGyro.class);
        // manager.register("LEGO Light",             SensorLEGOLight.class);
        // manager.register("LEGO Touch",             SensorLEGOTouch.class);
        // manager.register("MR Color",               SensorMRColor.class);
        // manager.register("MR Compass",             SensorMRCompass.class);
        // manager.register("MR Gyro",                SensorMRGyro.class);
        // manager.register("MR IR Seeker",           SensorMRIrSeeker.class);
        // manager.register("MR ODS",                 SensorMROpticalDistance.class);
        // manager.register("MR Range Sensor",        SensorMRRangeSensor.class);
        // manager.register("REV Color Distance",     SensorREVColorDistance.class);

        //  Concept Samples
        // manager.register("Null Op",                ConceptNullOp.class);
        // manager.register("Compass Calibration",    ConceptCompassCalibration.class);
        // manager.register("DIM as Indicator",       ConceptDIMAsIndicator.class);
        // manager.register("I2C Address Change",     ConceptI2cAddressChange.class);
        // manager.register("Ramp Motor Speed",       ConceptRampMotorSpeed.class);
        // manager.register("Scan Servo",             ConceptScanServo.class);
        // manager.register("Telemetry",              ConceptTelemetry.class);
        // manager.register("Vuforia Navigation",     ConceptVuforiaNavigation.class);
    }
}
