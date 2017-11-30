package org.firstinspires.ftc.teamcode;

import android.graphics.Color;
import android.hardware.SensorEvent;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;


/**
 * Created by cvn on 11/28/17.
 *
 * This opmode provides a basic driving framework for FTC Rover in TeleOp mode using holonomic
 * drives. This opmode extends the Linear Opmode class.
 *
 */

@TeleOp(name = "FTC Rover TeleOp", group = "Example Rover")
public class FTC_RoverTeleOp extends LinearOpMode {

    // Instantiate the hardware abstraction class HardwareScorpio
    HardwareFTC_Rover robot = new HardwareFTC_Rover();

    // Grapple control
    // TRUE represents the OPEN state.
    boolean gOpen = true;


    // Unimplemented Color sensor HSV data. Note we are using hexadecimal values here.
    // float hsvValues[] = {0F, 0F, 0F};
    // final float values[] = hsvValues;

    // Unimplemented Color sensor variables
    // bPrevState and bCurrState represent the previous and current state of the button
    // controlling the color sensor LED.
    // boolean bPrevState = false;
    // boolean bCurrState = false;

    // Unimplemented bLedOn represents the state of the LED.
    // boolean bLedOn = true;


    // On initialization of the OpMode do this:
    @Override
    public void runOpMode() {

        // Initialize the hardware variables.
        // The init() method of the hardware class does all the work.
        robot.init(hardwareMap);

        // Set initial conditions. LED on. Motors zero.
        // robot.colorSensor.enableLed(bLedOn);
        robot.leftFrontDrive.setPower(0);
        robot.rightFrontDrive.setPower(0);
        robot.leftRearDrive.setPower(0);
        robot.rightRearDrive.setPower(0);
        robot.grappleDrive.setPower(0);
        robot.leftCubeGrappleServo.setPosition(robot.SERVO_OPEN);
        robot.rightCubeGrappleServo.setPosition(-robot.SERVO_OPEN);

        // Telemetry update.
        telemetry.addData("State: ", "Ready ... waiting for start.");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // The following code is a simple means of operating the holonomic (mecanum wheel)
            // drive on the rover. The hardware class contains an alternative method. The approach
            // here would be inappropriate for autonomous. The left joystick translates the robot.
            // The right joystick rotates the robot.

            // Drive algorithm after
            // https://ftcforum.usfirst.org/forum/ftc-technology/android-studio/6361-mecanum-wheels-drive-code-example
            // For further information on controlling mecanum wheels consult the following collection of
            // documents: https://www.chiefdelphi.com/media/papers/2390


            // Get joystick values, limit range, and scale for response
            double leftX = robot.scalePower(Range.clip(gamepad1.left_stick_x, -1.0, 1.0));
            double leftY = robot.scalePower(Range.clip(gamepad1.left_stick_y, -1.0, 1.0));
            double rightX = robot.scalePower(Range.clip(gamepad1.right_stick_x, -1.0, 1.0));
            //double rightY = robot.scalePower(gamepad1.right_stick_y)

            // Calculate r and angle for translation
            double r = Math.hypot(leftX, leftY);
            double robotAngle = Math.atan2(leftY, leftX) - Math.PI / 4;

            // Calculate power level to deliver to each drive
            final double v1 = r * Math.cos(robotAngle) + rightX; // Left front drive
            final double v2 = r * Math.sin(robotAngle) - rightX; // Right front drive
            final double v3 = r * Math.sin(robotAngle) + rightX; // Left rear drive
            final double v4 = r * Math.cos(robotAngle) - rightX; // Right rear drive

            // Set drive power levels
            robot.leftFrontDrive.setPower(v1);
            robot.rightFrontDrive.setPower(v2);
            robot.leftRearDrive.setPower(v3);
            robot.rightRearDrive.setPower(v4);

            // Alternatively, you might use this method (defined in the HardwareFTC_Rover class
            // robot.holonomic(leftY, rightX, leftX, MAX_SPEED)


            // Manipulate the grapple assembly using the dpad. dpad provides boolean values. You must
            // take care not to run the grabble lift drive past end positions else damage will ensue!
            // The responsibility is yours. Use encoders!

            // dpad_up runs the grabble arm up in short increments
            // dpad_down runs the grabble arm down in short increments
            double v5 = 0;

            if (gamepad1.dpad_up)
                v5 = robot.GRAPPLE_LIFT_SPEED;
            else if (gamepad1.dpad_down)
                v5 = -robot.GRAPPLE_LIFT_SPEED;
            else if (gamepad1.dpad_left)
                gOpen = true;
            else if (gamepad1.dpad_right)
                gOpen = false;

            // Move the grapple lift drive
            robot.grappleDrive.setPower(v5);

            // Move both servos to new position.  Assume servos are mirror image of each other.
            if (gOpen) {
                robot.leftCubeGrappleServo.setPosition(robot.SERVO_OPEN);
                robot.rightCubeGrappleServo.setPosition(1-robot.SERVO_OPEN);
            }
             else {
                robot.leftCubeGrappleServo.setPosition(robot.SERVO_CLOSED);
                robot.rightCubeGrappleServo.setPosition(-robot.SERVO_CLOSED);
            }



            // bCurrState = gamepad1.x;
            // if (bCurrState && (bCurrState != bPrevState)) {

                // button is transitioning to a pressed state. So Toggle LED
                // bLedOn = !bLedOn;
                // robot.colorSensor.enableLed(bLedOn);
            // }

            // update previous state variable.
            // bPrevState = bCurrState;

            // convert the RGB values to HSV values.
            // Color.RGBToHSV(robot.colorSensor.red() * 8, robot.colorSensor.green() * 8, robot.colorSensor.blue() * 8, hsvValues);

            // Send drive and color telemetry to the driver station;
            telemetry.addData("LFront drive: ", "%.2f", v1);
            telemetry.addData("RFront drive: ", "%.2f", v2);
            telemetry.addData("LRear drive: ", "%.2f", v3);
            telemetry.addData("RRear drive: ", "%.2f", v4);

            telemetry.update();


            // Pace this loop.
            sleep(50);

            // Stop the grapple lift drive. Better, use encoders!
            robot.grappleDrive.setPower(0);
        }
    }


}
