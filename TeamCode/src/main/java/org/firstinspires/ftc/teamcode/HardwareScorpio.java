package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by cvn on 11/19/17.
 * Example hardware abstraction for the Scorpio example robot. It is assumed that the following
 * device names have been correctly configured on the robot. Scorpio corrently uses the Modern
 * Robotics Modules. If you are using the REV controller and use parts of this example code,
 * configure accordingly!
 * <p>
 * Motor controller:    Left  drive motor:          "Left_drive"
 * Right drive motor:          "Right_drive"
 * Device Interface     MR Color Sensor (I2C):      "Front_color"
 * NIR IR Seeker v3 (I2C):     "Front_IR"
 * Servo controller:    Base tail servo:            "Tail_servo"
 * <p>
 * This hardware abstraction class liberally borrows from the PushBot example hardware
 * example class. Any similarities are entirely intentional.
 */

public class HardwareScorpio {

    // Public OpMode members
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public Servo tailBase = null;
    public IrSeekerSensor irSeeker = null;
    public ColorSensor colorSensor = null;

    public static final double MID_SERVO = 0.5;


    //Local OpMode members
    HardwareMap hardwareMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public HardwareScorpio() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwMap) {

        // Save reference to Hardware map
        hardwareMap = hwMap;

        // Define and Initialize Main Drive Motors
        leftDrive = hardwareMap.get(DcMotor.class, "Left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "Right_drive");
        leftDrive.setDirection(DcMotor.Direction.REVERSE); // Scorpio uses AndyMark Nevrest 40s.
        rightDrive.setDirection(DcMotor.Direction.FORWARD);// So there.

        // Set all motors to zero power
        leftDrive.setPower(0);
        rightDrive.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize all installed servos.
        tailBase = hardwareMap.get(Servo.class, "Tail_servo");
        tailBase.setPosition(MID_SERVO);

        // Define and initialize sensors
        irSeeker = hardwareMap.get(IrSeekerSensor.class, "Front_IR");
        colorSensor = hardwareMap.get(ColorSensor.class, "Front_color");

    }
}

