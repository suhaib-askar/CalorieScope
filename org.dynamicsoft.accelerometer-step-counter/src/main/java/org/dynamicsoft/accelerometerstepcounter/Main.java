package org.dynamicsoft.accelerometerstepcounter;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by karan on 9/3/18.
 */

public class Main extends AppCompatActivity implements SensorEventListener, StepListener {
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    public int numSteps;
    public float SensorSentivityTemp = 30; //30 for now


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);
    }

    public void startCounting(){
        numSteps = 0;
        sensorManager.registerListener(Main.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
    }

    public void stopCounting() {
        sensorManager.unregisterListener(Main.this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2], SensorSentivityTemp);
        }
    }

    @Override
    public void step(long timeNs) {numSteps++;     //Steps value here
    }
}