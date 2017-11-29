package org.firstinspires.ftc.teamcode;

// import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by cvn on 11/28/17.
 * Example hardware abstraction class for the 2017 FTC Rover. It is assumed that the following
 * device names have been correctly configured on the robot. The Rover currently uses the REV
 * controller. If you are using the Modern Robotics controller and use parts of this example code,
 * configure accordingly!
 *
 * Several methods for interacting with the harware are included in this class.
 *
 * Motor controllers:   Left front drive motor:     "Left_front_drive"
 *                      Right front drive motor:    "Right_front_drive"
 *                      Left rear drive motor:      "Left_rear_drive"
 *                      Right rear drive motor:     "Right_rear_drive"
 *
 *                      Cube grapple drive:         "Grapple_drive"
 *                      Ball displace motor:        "Ball_drive"
 *                      Extension motor:            "Extension_drive"
 *
 * Servo controllers:   Cube grapple right servo:   "CGrappleR_servo"
 *                      Cube grapple left servo:    "CGrappleL_servo"
 *                      Extension grapple servo:    "EGrapple_servo"
 *
 * Sensors              Color sensor:               "ColorSensor"
 *
 * Public methods
 * 1) void init( HardwareMap map);               Initializes the hardware map and hardware
 * 2) double scalePower( double inputPower );    Rescales the joystick input between -1 and 1
 * 3) void holonomic( double Speed, double Turn, Control the mecanum wheels
 *         double Strafe, double MAX_SPEED)
 * 4) void cube_grapple_open();                  Opens cube grapple system
 * 5) void cube_grapple_close();                 Closes cube grapple system
 * 6) void extension_grapple_open();             Opens extension grapple
 * 7) void extension_grapple_close();            Closes extension grapple
 * 8) boolean color_detect( integer hue );       Returns true if hue is detected
 *
 * This hardware abstraction class liberally borrows from the PushBot example hardware
 * example class. Any similarities are entirely intentional. For some reason K9 does not
 * work for me.
 *
 */

public class HardwareFTC_Rover

{

    // Public OpMode members
    // You will use these object names to access the hardware on your rover
    public DcMotor leftFrontDrive = null;
    public DcMotor rightFrontDrive = null;
    public DcMotor leftRearDrive = null;
    public DcMotor rightRearDrive = null;
    public DcMotor grappleDrive = null;
    // public DcMotor ballDrive = null;
    // public DcMotor extensionDrive = null;
    public Servo rightCubeGrappleServo = null;
    public Servo leftCubeGrappleServo = null;
    // public Servo extensionGrappleServo = null;
    // public ColorSensor colorSensor = null;

    // Set constants for the position of the servos.
    public static final double SERVO_MID = 0.5;
    public static final double SERVO_OPEN = 0.9;
    public static final double SERVO_CLOSED = 0.1;
    public static final double MAX_SPEED = 1.0;
    public static final double GRAPPLE_LIFT_SPEED = 0.2;
    public static final double SERVO_OFFSET = 0.0;




    // Local OpMode members
    private HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    // Constructor
    public HardwareFTC_Rover() {

    }

    // Initialize standard Hardware interfaces
    public void init(HardwareMap ahwMap) {

        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and initialize each of the four main drive motors
        leftFrontDrive  = hwMap.get(DcMotor.class, "Left_front_drive");
        rightFrontDrive = hwMap.get(DcMotor.class, "Right_front_drive");
        leftRearDrive  = hwMap.get(DcMotor.class, "Left_rear_drive");
        rightRearDrive = hwMap.get(DcMotor.class, "Right_rear_drive");

        // Set the start direction of each of the four main drive motors.
        // NevRest 40s are installed.
        leftFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        leftRearDrive.setDirection(DcMotor.Direction.FORWARD);
        rightRearDrive.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFrontDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRearDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRearDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize each of the manipulator motors
        grappleDrive = hwMap.get(DcMotor.class, "Grapple_drive");
        // ballDrive = hwMap.get(DcMotor.class, "Ball_drive");
        // extensionDrive = hwMap.get(DcMotor.class, "Extension_drive");

        // Set the start direction of each of the four main drive motors.
        // NevRest 40s are installed.
        grappleDrive.setDirection(DcMotor.Direction.FORWARD);
        // ballDrive.setDirection(DcMotor.Direction.FORWARD);
        // extensionDrive.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        grappleDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // ballDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // extensionDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize all installed servos.
        leftCubeGrappleServo = hwMap.get(Servo.class, "CGrappleL_servo");
        rightCubeGrappleServo = hwMap.get(Servo.class, "CGrappleR_servo");


        // Define and initialize sensors
        // colorSensor = hwMap.get(ColorSensor.class, "Front_color");

    }

    // Drive scaling method
    // We use this method to scale the joysticks for better control and responsiveness.
    // See ScorpioTeleOp_DrivesOnly for other algorithms for joystick control and for
    // Wolfram code to visualize the scaling algorighms.
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

    // Mecanum wheel control method
    // After https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example
    // For further information on controlling mecanum wheels consult the following collection of
    // documents: https://www.chiefdelphi.com/media/papers/2390
    public void holonomic(double Speed, double Turn, double Strafe, double MAX_SPEED){

//      Left Front = +Speed + Turn - Strafe      Right Front = +Speed - Turn + Strafe
//      Left Rear  = +Speed + Turn + Strafe      Right Rear  = +Speed - Turn - Strafe

        double Magnitude = abs(Speed) + abs(Turn) + abs(Strafe);
        Magnitude = (Magnitude > 1) ? Magnitude : 1; //Set scaling to keep -1,+1 range

        leftFrontDrive.setPower(scale((scaleInput(Speed) + scaleInput(Turn) - scaleInput(Strafe)),
                -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        if (leftRearDrive != null) {
            leftRearDrive.setPower(scale((scaleInput(Speed) + scaleInput(Turn) + scaleInput(Strafe)),
                    -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        }
        rightFrontDrive.setPower(scale((scaleInput(Speed) - scaleInput(Turn) + scaleInput(Strafe)),
                -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        if (rightRearDrive != null) {
            rightRearDrive.setPower(scale((scaleInput(Speed) - scaleInput(Turn) - scaleInput(Strafe)),
                    -Magnitude, +Magnitude, -MAX_SPEED, +MAX_SPEED));
        }
    }



}

