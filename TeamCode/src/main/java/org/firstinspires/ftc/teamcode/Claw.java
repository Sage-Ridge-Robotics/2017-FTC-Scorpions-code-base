/* Copyright (c) 2017 FIRST. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification,
* are permitted (subject to the limitations in the disclaimer below) provided that
* the following conditions are met:
*
* Redistributions of source code must retain the above copyright notice, this list
* of conditions and the following disclaimer.
*
* Redistributions in binary form must reproduce the above copyright notice, this
* list of conditions and the following disclaimer in the documentation and/or
* other materials provided with the distribution.
*
* Neither the name of FIRST nor the names of its contributors may be used to endorse or
* promote products derived from this software without specific prior written permission.
*
* NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
* LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
* ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
* FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
* DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
* CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
* OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
* OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * This OpMode scans a single servo back and forwards until Stop is pressed.
 * The code is structured as a LinearOpMode
 * INCREMENT sets how much to increase/decrease the servo position each cycle
 * CYCLE_MS sets the update period.
 *
 * This code assumes a Servo configured with the name "left claw" as is found on a pushbot.
 *
 * NOTE: When any servo position is set, ALL attached servos are activated, so ensure that any other
 * connected servos are able to move freely before running this test.
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@TeleOp(name = "Claw", group = "Controls")
public class Claw extends LinearOpMode {

    int motion =  0;    //where 0 is startionary, 1 for grabbing, -1 for returning back to start
    private boolean down = false;
    private boolean stop = false;
    private static final double    inc = 0.01; //increment
    private static final int    CYCLE_MS    =   50;     // length of each cycle
    private static final double MAX_POS     =  1.0;     // Maximum  position
    private static final double MIN_POS     =  0.0;     // Minimum position
    private static final double clawMAX_POS     =  1.0;     // Maximum  position
    private static final double clawMIN_POS     =  0.0;     // Minimum position
    private static final double clawInc     =  0.01;     // Minimum position

    // Define class
    Servo   servo;
    Servo servo2;
    DcMotor drive;

    double positionClaw = (clawMAX_POS + clawMIN_POS) / 2;
    double  position = (MAX_POS + MIN_POS) / 2; // Start at halfway position
    public int abs(int a){

        if (a < 0) {
            a = -a;
        }
        return a;
    }
    @Override
    public void runOpMode() {


        // Change the text in quotes to match any servo name on your robot.
        servo = hardwareMap.get(Servo.class, "arm");
        servo2 = hardwareMap.get(Servo.class,"arm2");
        drive = hardwareMap.get(DcMotor.class,"height");
        // Wait for the start button
        telemetry.addData(">", "Press Start to scan Servo." );
        telemetry.update();
        waitForStart();


        // Scan servo till stop pressed.
        while(opModeIsActive()){
            // slew the servo, according to the rampUp (direction) variable.
            if (gamepad1.x) {
                if (positionClaw < clawMAX_POS ) {
                    positionClaw += clawInc;
                }
                else {
                    positionClaw = clawMAX_POS;
                }

            }
            if (gamepad1.y) {
                if (positionClaw > clawMIN_POS ) {
                    positionClaw -= clawInc;
                }
                else {
                    positionClaw = clawMIN_POS;
                }

            }
            if (gamepad1.b) {
               
                position = 0.5;
                stop = !stop;

            }
            if (gamepad1.a && !stop){
                if(abs(motion) == 1) {
                    motion = 0;

                }

                else if (motion == 0) {
                    if (down){
                        motion = -1;

                    }
                    else {

                        motion = 1;

                    }
                    down = !down;
                }


            }
            if (motion == 1) {
                // Keep stepping up until we hit the max value.
                position += inc;
                if (position >= MAX_POS ) {
                    position = MAX_POS;
                }
            }
            else if (motion == -1) {
                // Keep stepping down until we hit the min value.
                position -= inc ;
                if (position <= MIN_POS ) {
                    position = MIN_POS;

                }
            }


            // Display the current value
            telemetry.addData("Servo Position", "%5.2f", position);
            telemetry.addData("High", "%5.2f", positionClaw);
            telemetry.addData(">", "Press Stop to end test." );
            telemetry.update();

            // Set the servo to the new position and pause;
            servo.setPosition(position);
            servo2.setPosition(position);
            drive.setPower(positionClaw);
            sleep(CYCLE_MS);
            idle();
        }

        // Signal done;
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}




