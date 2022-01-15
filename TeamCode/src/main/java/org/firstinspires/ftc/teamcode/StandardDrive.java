package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.*;

@Disabled
@TeleOp(name = "StandardDrive", group = "Daniel")
public class StandardDrive extends com.qualcomm.robotcore.eventloop.opmode.OpMode {

    DcMotor back_left;
    DcMotor back_right;
    boolean nateMode = false;

    public void init() {
        back_left = hardwareMap.dcMotor.get("motor1");
        back_right = hardwareMap.dcMotor.get("motor2");

        back_left.setDirection(DcMotor.Direction.REVERSE);
        back_right.setDirection(DcMotor.Direction.REVERSE);
    }

    public void loop() {

        float t = System.currentTimeMillis();

        if (gamepad1.back) {
            nateMode = !nateMode;
        }

        if (nateMode) {
            back_left.setPower(gamepad1.left_stick_y);
            back_right.setPower(gamepad1.right_stick_y);
        } else {
            float fwd = gamepad1.left_stick_y;
            float steer = gamepad1.right_stick_x;

            float fineSteerLeft = gamepad1.left_trigger;
            float fineSteerRight = gamepad1.right_trigger;

            back_left.setPower(
                    fwd - 0.9 * steer - fineSteerLeft + fineSteerRight
            );

            back_right.setPower(
                    fwd + 0.9 * steer + fineSteerLeft - fineSteerRight
            );
        }



        telemetry.addData("left_stick_y", gamepad1.left_stick_y);
        telemetry.addData("right_stick_x", gamepad1.right_stick_x);

        telemetry.addData("Nate Control Active", nateMode);
        telemetry.addData("Loop Delay", System.currentTimeMillis() - t);
    }


}
