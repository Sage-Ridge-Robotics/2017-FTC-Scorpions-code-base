/**
 * Created by Caleb Briggs on 11/28/17.
 *
 * This opmode provides a basic driving framework for FTC Rover in TeleOp mode using holonomic
 * drives. This opmode extends the Linear Opmode class.
 *
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import java.util.Random;


@Autonomous(name = "AutoMode2")
public class Auto2 extends LinearOpMode {

    // Instantiate the hardware abstraction class HardwareScorpio
    HardwareFTC_Rover robot = new HardwareFTC_Rover();

    // Grapple control
    // TRUE represents the OPEN state.



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
    public void crawl(double degrees,double val) {
        double start = System.currentTimeMillis();
        double end = start + degrees*50; // 60 seconds * 1000 ms/sec
        while (System.currentTimeMillis() < end)
        {
            setVals(false,0,val,0,false,0);
            sleep(100);
        }

    }
    public void drive2(double time) {
        Random rand = new Random();
        double start = System.currentTimeMillis();
        double end = start + time*50 ; // 60 seconds * 1000 ms/sec
        while (System.currentTimeMillis() < end)
        {
            setVals(false,rand.nextInt(2) ,rand.nextInt(2) ,rand.nextInt(2),false,0);
            sleep(100);
        }
    }
    public void drive(double time,double val) {

        double start = System.currentTimeMillis();
        double end = start + time*50 ; // 60 seconds * 1000 ms/sec
        while (System.currentTimeMillis() < end)
        {
            setVals(false,0,0,val,false,0);
            sleep(100);
        }
    }
    public void turn(double time,double val) {

        double start = System.currentTimeMillis();
        double end = start + time*5.5 ; // 60 seconds * 1000 ms/sec

        while (System.currentTimeMillis() < end)
        {

            setVals(false,val,0,0,false,0);
            sleep(100);
        }
    }
    public void dance(double time, boolean open) {

        double start = System.currentTimeMillis();
        double end = start + time*50 ; // 60 seconds * 1000 ms/sec

        while (System.currentTimeMillis() < end)
        {

            setVals(open,0,0,0,false,0);
            sleep(100);
        }
    }
    public void dance2(double time, boolean up) {

        double start = System.currentTimeMillis();
        double end = start + time*50 ; // 60 seconds * 1000 ms/sec

        while (System.currentTimeMillis() < end)
        {

            setVals(false,0,0,0,up,0);
            sleep(100);
        }
    }
    public void reset() {

        robot.leftFrontDrive.setPower(0);
        robot.rightFrontDrive.setPower(0);
        robot.leftRearDrive.setPower(0);
        robot.rightRearDrive.setPower(0);
        robot.grappleDrive.setPower(0);

    }
    public void setVals(boolean Open, double strafe, double rotate, double drive, boolean gamePadUp, double v5) {
//Open part needs to be chaanged, forces it to be closed nearly always
        final double v1 = Range.clip(drive + strafe + rotate,-1.0,1.0); // Left front drive
        final double v2 = Range.clip(drive - strafe - rotate,-1.0,1.0); // Right front drive
        final double v3 = Range.clip(drive + strafe - rotate,-1,1); // Left rear drive
        final double v4 = Range.clip(drive - strafe + rotate,-1,1); // Right rear drive
        robot.leftFrontDrive.setPower(v1);
        robot.rightFrontDrive.setPower(v2);
        robot.leftRearDrive.setPower(v3);
        robot.rightRearDrive.setPower(v4);
        if (Open) {
            robot.leftCubeGrappleServo.setPosition(robot.SERVO_OPEN);
            robot.rightCubeGrappleServo.setPosition(1-robot.SERVO_OPEN);
        }
        else {
            robot.leftCubeGrappleServo.setPosition(robot.SERVO_CLOSED);
            robot.rightCubeGrappleServo.setPosition(1-robot.SERVO_CLOSED);
        }





        robot.grappleDrive.setPower(v5);
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




    }
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
        robot.leftCubeGrappleServo.setPosition(robot.SERVO_CLOSED);
        robot.rightCubeGrappleServo.setPosition(1-robot.SERVO_CLOSED);

        // Telemetry update.
        telemetry.addData("State: ", "Ready ... waiting for start.");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        int a = 0;
        double[][] choices = {{0,15,-1},{1,118,-1},{0,25,-1},{2,7,1},{0,10,-1},{3,25,1},{0,25,1},{3,35,-1},{0,55,-1},{0,3,1}};
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

            //setVals(true,1,0,1,true,1);

            if (a < choices.length) {
                double[] choi = choices[a];
                if (choi[0] == 0) {
                    drive(choi[1], choi[2]);
                } else if (choi[0] == 1) {
                    turn(choi[1], choi[2]);
                } else if (choi[0] == 2) {
                    crawl(choi[1], choi[2]);
                } else if (choi[0] == 3) {
                    if (choi[2] == 1) {
                        dance(choi[1], true);
                    }
                    else {

                        dance(choi[1], false);
                    }
                }
                else if (choi[0] == 4) {
                    if (choi[2] == 1) {
                        dance2(choi[1], true);
                    }
                    else {

                        dance2(choi[1], false);
                    }
                }
                else if (choi[0] == 5) {
                    drive2(choi[1]);
                }
                a++;
            }



            reset();
            //double rightY = robot.scalePower(gamepad1.right_stick_y)

            // Calculate r and angle for translation
            //double r = Math.hypot(leftX, leftY);
            // double robotAngle = Math.atan2(leftY, leftX) - Math.PI / 4;

            // Calculate power level to deliver to each drive


            // Set drive power levels


            // Alternatively, you might use this method (defined in the HardwareFTC_Rover class
            // robot.holonomic(leftY, rightX, leftX, MAX_SPEED)


            // Manipulate the grapple assembly using the dpad. dpad provides boolean values. You must
            // take care not to run the grabble lift drive past end positions else damage will ensue!
            // The responsibility is yours. Use encoders!

            // dpad_up runs the grabble arm up in short increments
            // dpad_down runs the grabble arm down in short increments


            // Move both servos to new position.  Assume servos are mirror image of each other.



            // Pace this loop.
            telemetry.addData("RRear drive: ", "%.2f", (float)a);
            telemetry.update();
            sleep(350);


            // Stop the grapple lift drive. Better, use encoders!
            //robot.grappleDrive.setPower(0);
        }
    }


}


