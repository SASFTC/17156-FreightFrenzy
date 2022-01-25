package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Mecanum Wheel", group="Daniel")
public class MecanumWheel extends OpMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();

    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;

    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;

    private boolean nateMode = false;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        rightFrontDrive = hardwareMap.get(DcMotor.class, "motor1");
        leftFrontDrive  = hardwareMap.get(DcMotor.class, "motor2");

        rightBackDrive = hardwareMap.get(DcMotor.class, "motor3");
        leftBackDrive  = hardwareMap.get(DcMotor.class, "motor4");




        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBackDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        // leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");
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
        runtime.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        float t = System.currentTimeMillis();
        if (gamepad1.back) {
            nateMode = !nateMode;
        }

        double fwd = -gamepad1.left_stick_y; // Remember, this is reversed!
        double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
        double steer = gamepad1.right_stick_x;

        double fineSteerLeft = gamepad1.left_trigger;
        double fineSteerRight = gamepad1.right_trigger;


        telemetry.addData("left_stick_y", gamepad1.left_stick_y);
        telemetry.addData("right_stick_x", gamepad1.right_stick_x);

        telemetry.addData("Nate Control Active", nateMode);
        telemetry.addData("Loop Delay", System.currentTimeMillis() - t);


        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]


        double denominator = Math.max(Math.abs(fwd) + Math.abs(x) + Math.abs(steer), 1);
        double frontLeftPower = (fwd * 0.9 + x + steer * 0.2 - fineSteerLeft * 0.6 + fineSteerRight * 0.6) / denominator;
        double backLeftPower = (fwd * 0.9 - x + steer * 0.2 - fineSteerLeft * 0.6 + fineSteerRight * 0.6) / denominator;

        double frontRightPower = (fwd * 0.9 - x - steer * 0.2 + fineSteerLeft * 0.6 - fineSteerRight * 0.6) / denominator;
        double backRightPower = (fwd * 0.9 + x - steer * 0.2 + fineSteerLeft * 0.6 - fineSteerRight * 0.6) / denominator;


        leftFrontDrive.setPower(frontLeftPower);
        leftBackDrive.setPower(backLeftPower);
        rightFrontDrive.setPower(frontRightPower);
        rightBackDrive.setPower(backRightPower);

        // Show the elapsed game time and wheel power.
        telemetry.addData("FineSteerLeft", fineSteerLeft);
        telemetry.addData("FineSteerRight", fineSteerRight);
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }

}

