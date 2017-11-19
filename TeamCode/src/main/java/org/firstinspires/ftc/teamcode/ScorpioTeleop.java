package org.firstinspires.ftc.teamcode;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;


/**
 * Created by cvn on 11/19/17.
 * <p>
 * This file provides tha basic driving framework for Scorpio in teleop mode and
 * illustrates the attached sensors.
 */

@TeleOp(name = "Scorpio TeleOp", group = "Scorpio")
public class ScorpioTeleop extends OpMode {

    /// Declare OpMode members.
    HardwareScorpio robot = new HardwareScorpio(); // use the class created to define your hardware
    double tailOffset = 0.0;
    final double TAIL_SPEED = 0.02;                 // sets rate to move the tail base.

    // Color sensor HSV data.
    float hsvValues[] = {0F, 0F, 0F};
    final float values[] = hsvValues;

    // Get a reference to the RelativeLayout so we can change the background
    // color of the Robot Controller app to match the hue detected by the RGB sensor.
    int relativeLayoutId = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());
    final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

    // Color sensor variables
    // bPrevState and bCurrState represent the previous and current state of the button controlling the color sensor LED.
    boolean bPrevState = false;
    boolean bCurrState = false;

    // bLedOn represents the state of the LED.
    boolean bLedOn = true;
    boolean bLedOff = false;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);
        robot.colorSensor.enableLed(bLedOn);
        // Set the Driver Station panel to a default neutral color
        /*relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }

        });*/

        telemetry.addData("Say", "Ready ... ");    //
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        bLedOn = true;
        robot.colorSensor.enableLed(bLedOn);
        telemetry.addData("Say", "Starting ... ");
        // runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {

        double left;
        double right;

        // Run main drive wheels
        left = -gamepad1.left_stick_y;
        right = -gamepad1.right_stick_y;

        robot.leftDrive.setPower(left);
        robot.rightDrive.setPower(right);

        // Use gamepad left & right Bumpers to turn tail
        if (gamepad1.right_bumper)
            tailOffset += TAIL_SPEED;
        else if (gamepad1.left_bumper)
            tailOffset -= TAIL_SPEED;

        tailOffset = Range.clip(tailOffset, -0.5, 0.5);
        robot.tailBase.setPosition(robot.MID_SERVO + tailOffset);

        // check the status of the x button on either gamepad.
        bCurrState = gamepad1.x;

        // check for button state transitions.
        if (bCurrState && (bCurrState != bPrevState)) {

            // button is transitioning to a pressed state. So Toggle LED
            bLedOn = !bLedOn;
            robot.colorSensor.enableLed(bLedOn);
        }

        // update previous state variable.
        bPrevState = bCurrState;

        // convert the RGB values to HSV values.
        Color.RGBToHSV(robot.colorSensor.red() * 8, robot.colorSensor.green() * 8, robot.colorSensor.blue() * 8, hsvValues);

        // Change the background color of the DriverStation app to match the color detected by the RGB sensor.
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.HSVToColor(0xff, values));
            }
        });

        // Send drive telemetry to the driver station;
        telemetry.addData("tail", "Offset = %.2f", tailOffset);
        telemetry.addData("left", "%.2f", left);
        telemetry.addData("right", "%.2f", right);

        // Send color information to the driver station.
        telemetry.addData("LED", bLedOn ? "On" : "Off");
        telemetry.addData("Clear", robot.colorSensor.alpha());
        telemetry.addData("Red  ", robot.colorSensor.red());
        telemetry.addData("Green", robot.colorSensor.green());
        telemetry.addData("Blue ", robot.colorSensor.blue());
        telemetry.addData("Hue", hsvValues[0]);

        // Send IR data to the driver station if an infrared signal is detected.
        if (robot.irSeeker.signalDetected()) {
            // Display angle and strength
            telemetry.addData("Angle", robot.irSeeker.getAngle());
            telemetry.addData("Strength", robot.irSeeker.getStrength());
        } else {
            // Display loss of signal
            telemetry.addData("Seeker", "No signal");
        }

    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        // Set the panel back to the default color
        relativeLayout.post(new Runnable() {
            public void run() {
                relativeLayout.setBackgroundColor(Color.WHITE);
            }
        });
        robot.colorSensor.enableLed(bLedOff);
        telemetry.addData("Say", "Stopped ... ");
    }


}
