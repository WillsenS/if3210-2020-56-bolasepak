package com.example.bolaksepak;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.widget.Toast;

public class StepCountService extends Service implements SensorEventListener {
    private static final String DEBUG_TAG = "StepCountService";
    private SensorManager sensorManager = null;
    private Sensor sensor = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // do nothing
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // grab the values -- off the main thread
        int sensorValues = (int)event.values[0];
        sendSensorValuesToActivity(sensorValues);
    }

    private void sendSensorValuesToActivity(int values) {
        Intent intent = new Intent("UpdateSensorValues");
        // You can also include some extra data.
        intent.putExtra("Values", values);
        sendBroadcast(intent);
    }
}

