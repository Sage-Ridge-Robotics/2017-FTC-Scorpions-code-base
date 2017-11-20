package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;


/**
 * Created by cvn on 11/19/17.
 *
 * This opmode provides a basic driving framework for Scorpio in TeleOp mode and
 * illustrates the attached sensors. This opmode extends the Linear Opmode class and
 * uses a modified POV model of control. The right joystick controls speed and direction
 * using the y-axis for speed and x-axis for direction.
 *
 */

@TeleOp(name = "Scorpio TeleOp", group = "Scorpio")
public class ScorpioTeleop extends LinearOpMode {

    // Instantiate the hardware abstraction class HardwareScorpio
    HardwareScorpio robot = new HardwareScorpio();

    // Declare settings for tail
    double tailOffset = 0.0;
    final double TAIL_SPEED = 0.02;

    // Color sensor HSV data. Note we are using hexadecimal values here.
    float hsvValues[] = {0F, 0F, 0F};
    final float values[] = hsvValues;

    // Color sensor variables
    // bPrevState and bCurrState represent the previous and current state of the button
    // controlling the color sensor LED.
    boolean bPrevState = false;
    boolean bCurrState = false;

    // bLedOn represents the state of the LED.
    boolean bLedOn = true;


    // On initialization of the OpMode
    @Override
    public void runOpMode() {
        double leftPower;
        double rightPower;
        double drive;
        double turn;

        // Initialize the hardware variables.
        // The init() method of the hardware class does all the work.
        robot.init(hardwareMap);

        // Set initial conditions. LED on. Motors zero.
        robot.colorSensor.enableLed(bLedOn);
        robot.leftDrive.setPower(0);
        robot.rightDrive.setPower(0);
        robot.tailBase.setPosition(robot.MID_SERVO);

        // Telemetry update.
        telemetry.addData("State: ", "Ready ... waiting for start.");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // Run wheels in POV mode (note: The joystick goes negative when pushed forwards, so negate it)
            // In this mode the Left stick moves the robot fwd and back, the Right stick turns left and right.
            // This way it's also easy to just drive straight, or just turn.
            // Turning is further tamped down.
            drive = -gamepad1.right_stick_y;
            turn  =  Math.pow(gamepad1.right_stick_x,3.0);

            // Combine drive and turn for blended motion.
            leftPower = robot.scalePower(Range.clip(drive + turn, -1.0, 1.0));
            rightPower = robot.scalePower(Range.clip(drive - turn, -1.0, 1.0));

            // Output the safe vales to the motor drives.
            robot.leftDrive.setPower(leftPower);
            robot.rightDrive.setPower(rightPower);

            // Use gamepad left & right Bumpers to move the tail.
            if (gamepad1.dpad_left)
                tailOffset += TAIL_SPEED;
            else if (gamepad1.dpad_right)
                tailOffset -= TAIL_SPEED;

            // Move both servos to new position.  Assume servos are mirror image of each other.
            tailOffset = Range.clip(tailOffset, -0.5, 0.5);
            robot.tailBase.setPosition(robot.MID_SERVO + tailOffset);
            robot.tailBase.setPosition(robot.MID_SERVO - tailOffset);


            bCurrState = gamepad1.x;
            if (bCurrState && (bCurrState != bPrevState)) {

                // button is transitioning to a pressed state. So Toggle LED
                bLedOn = !bLedOn;
                robot.colorSensor.enableLed(bLedOn);
            }

            // update previous state variable.
            bPrevState = bCurrState;

            // convert the RGB values to HSV values.
            Color.RGBToHSV(robot.colorSensor.red() * 8, robot.colorSensor.green() * 8, robot.colorSensor.blue() * 8, hsvValues);

            // Send drive and color telemetry to the driver station;
            telemetry.addData("left", "%.2f", leftPower);
            telemetry.addData("right", "%.2f", rightPower);
            telemetry.addData("Color hue", hsvValues[0]);

            // Send IR data to the driver station if an infrared signal is detected.
        if (robot.irSeeker.signalDetected()) {

            // Display angle and strength
            telemetry.addData("Angle", robot.irSeeker.getAngle());
            telemetry.addData("Strength", robot.irSeeker.getStrength());
        } else {

            // Display loss of signal
            telemetry.addData("Seeker", "No signal");
        }
            telemetry.update();


            // Pace this loop.
            sleep(50);
        }
    }


}
