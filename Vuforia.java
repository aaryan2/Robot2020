package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.vuforia.HINT;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

@Autonomous (name= "Vuforia")
public class Vuforia extends LinearOpMode {

    VuforiaLocalizer vuforiaLocalizer;
    VuforiaLocalizer.Parameters parameters;
    VuforiaTrackables visionTargets;
    VuforiaTrackable target;
    VuforiaTrackableDefaultListener listener;

    OpenGLMatrix lastKnownLocation;
    OpenGLMatrix phoneLocation;

    public static final String VUFORIA_KEY = "AYEGecf/////AAABmbdNhF+PXEZltugJsrPfHm0p4qB1sS/C7Z7tOz1BBqKl2pOjV+hijGgTfQ7Wpa3wNniWntL1Cve/lPoOYaljaYu2M+9nUMRM8buompzosVyLYobZhzQbfv4hayc+wLwsC1+cpBOdmOYN5CgpaG5JMaXy4tn/CstePP8OqniTekaOUckfFU6f6yt6ejQHOopLjRt+5N0mP0fX/lAGPOwoEftX9aTuB/+rM/+77rwXIn8Ygf2PnGF7HEdW7XXKghAB7Bka8NbTldm42tyliehNsbMDy6wmMC8HW6732pQmuNMvJogN8OP8TiBo1KjKFjaNCPEUDViUtKIAVmMcppo/dzLp8JrTyFXF4wO6ZKFGZ69X";

    private float RobotX = 0;
    private float RobotY = 0;
    private float RobotAngle = 0;

    public void runOpMode() throws InterruptedException{

        setupVuforia();

        lastKnownLocation = createMatrix(0,0,0,0,0,0);

        waitForStart();

        visionTargets.activate();

        while (opModeIsActive()){

            OpenGLMatrix latestLocation = listener.getUpdatedRobotLocation();

            if(latestLocation != null)
                lastKnownLocation = latestLocation;
            telemetry.addData("Tracking:" + target.getName(), listener.isVisible());
            telemetry.addData("Last Known Location:", formatMatrix(lastKnownLocation));

            float[] coordinates = latestLocation.getTranslation().getData();

            RobotX = coordinates [0];
            RobotY = coordinates [1];
            RobotAngle = Orientation.getOrientation(lastKnownLocation, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES).thirdAngle;

            telemetry.update();
            idle();
        }
    }

    public void setupVuforia (){

        parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.useExtendedTracking = false;
        vuforiaLocalizer = ClassFactory.createVuforiaLocalizer(parameters);

        visionTargets = vuforiaLocalizer.loadTrackablesFromAsset("UltimateGoal");


        target = visionTargets.get(0);
        target.setName("BlueTower");
        target.setLocation(createMatrix(0,500,0,90,0,90));

        phoneLocation = createMatrix(0,225,0,90,0,0);
        listener = (VuforiaTrackableDefaultListener) target.getListener();
        listener.setPhoneInformation(phoneLocation, parameters.cameraDirection);

    }

    public OpenGLMatrix createMatrix(float x, float y, float z, float u, float v, float w){

        return OpenGLMatrix.translation(x,y,z).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, u, v, w));

    }

    public String formatMatrix (OpenGLMatrix matrix){

        return matrix.formatAsTransform();
    }
}
