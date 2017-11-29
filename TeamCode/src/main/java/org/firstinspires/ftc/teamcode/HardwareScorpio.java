package org.firstinspires.ftc.teamcode;

import android.hardware.Sensor;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;

/**
 * Created by cvn on 11/19/17.
 * Example hardware abstraction for the Scorpio example robot. It is assumed that the following
 * device names have been correctly configured on the robot. Scorpio corrently uses the Modern
 * Robotics Modules. If you are using the REV controller and use parts of this example code,
 * configure accordingly!
 *
 * Motor controller:    Left  drive motor:          "Left_drive"
 * Right drive motor:                               "Right_drive"
 * Device Interface     MR Color Sensor (I2C):      "Front_color"
 *                      NIR IR Seeker v3 (I2C):     "Front_IR"
 * Servo controller:    Base tail servo:            "Tail_servo"
 *
 * Public methods
 * 1) void init( HardwareMap map);               Initializes the hardware map and hardware
 * 2) double scalePower( double inputPower );    Rescales the joystick input between -1 and 1
 *
 * This hardware abstraction class liberally borrows from the PushBot example hardware
 * example class. Any similarities are entirely intentional.
 */

public class HardwareScorpio

{

    // Public OpMode members
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public Servo tailBase = null;
    public ColorSensor colorSensor = null;
    public IrSeekerSensor irSeeker = null;
    // public androidAccelerometer

    public static final double MID_SERVO = 0.5;


    // Local OpMode members
    private HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    // Constructor
    public HardwareScorpio() {

    }

    // Initialize standard Hardware interfaces
    public void init(HardwareMap ahwMap) {

        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Main Drive Motors
        leftDrive  = hwMap.get(DcMotor.class, "Left_drive");
        rightDrive = hwMap.get(DcMotor.class, "Right_drive");
        leftDrive.setDirection(DcMotor.Direction.FORWARD); // Scorpio uses AndyMark Nevrest 40s.
        rightDrive.setDirection(DcMotor.Direction.FORWARD);// So there.

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize all installed servos.
        tailBase = hwMap.get(Servo.class, "Tail_servo");

        // Define and initialize sensors
        colorSensor = hwMap.get(ColorSensor.class, "Front_color");
        irSeeker = hwMap.get(IrSeekerSensor.class, "Front_IR");

        // Android sensors
        // androidAccelerometer =
        //         sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);



    }

    // Drive scaling method
    // We use this method to scale the joysticks for better control and responsiveness.
    // See ScorpioTeleOp_DrivesOnly for other algorithms for joystick control.
    public double scalePower(double dVal) {
        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }


}

