package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous

public class Auto extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {

        //Define motors

        DcMotor LeftMotor = hardwareMap.get(DcMotor.class, "LeftMotor");
        DcMotor RightMotor = hardwareMap.get(DcMotor.class, "RightMotor");

        //Set spin direction opposite for left motor

        LeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //Set target rotation (ticks)

        LeftMotor.setTargetPosition(1500);
        RightMotor.setTargetPosition(1500);

        LeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //Set stopping behaviour to brake immediately

        LeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Instructions take place only after start

        waitForStart();

        // Set power

        LeftMotor.setPower(0.75);
        RightMotor.setPower(0.75);

    }
}
