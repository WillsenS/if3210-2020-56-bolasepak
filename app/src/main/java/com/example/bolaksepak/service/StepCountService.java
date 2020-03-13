package com.example.bolaksepak.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;

import org.joda.time.DateTimeComparator;

import java.util.Date;

public class StepCountService extends Service implements SensorEventListener {
    private static final String DEBUG_TAG = "StepCountService";
    private SensorManager sensorManager = null;
    private Sensor sensor = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        SharedPreferences sh = getSharedPreferences("ServiceStorage", MODE_APPEND);
        int pastValues = sh.getInt("values", 0);
        sendSensorValuesToActivity(pastValues);
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
        int actualValue = 0;
        // grab the values -- off the main thread
        int sensorValues = (int)event.values[0];
        Date now = new Date();
        //grab shared preference data
        SharedPreferences sh = getSharedPreferences("ServiceStorage", MODE_APPEND);
        Date pastDate = new Date(sh.getLong("timestamp", now.getTime()));
        int pastValues = sh.getInt("values", 0);
        boolean accumulative = sh.getBoolean("cumulative", false);
        boolean reductive = sh.getBoolean("reductive", false);
        //compare time
        DateTimeComparator dateTimeComparator = DateTimeComparator.getDateOnlyInstance();
        int retVal = dateTimeComparator.compare(now, pastDate);
        if (retVal == 0){
            //both dates are equal
            if(sensorValues==0){
                //case booted device
                if(reductive){
                    int storageActualValue = sh.getInt("actualValue",0);
                    actualValue = storageActualValue;
                }else{
                    // not reductive
                    actualValue = pastValues;
                }
                writeToSPreference(actualValue, now.getTime(), true,false);
            }else{
                if(accumulative){
                    actualValue = pastValues + sensorValues;
                    writeToSPreference(pastValues, now.getTime(), true, false);
                }else if(reductive){
                    actualValue = sensorValues - pastValues;
                    writeToSPreference(pastValues, now.getTime(), false , true);
                    //for case reductive and reboot
                    sh = getSharedPreferences("ServiceStorage", MODE_PRIVATE);
                    SharedPreferences.Editor shEdit = sh.edit();
                    shEdit.putInt("actualValue", actualValue);
                    shEdit.commit();
                }else {
                    actualValue = sensorValues;
                    writeToSPreference(actualValue, now.getTime(), false, false);
                }
            }
        }else if(retVal > 0){
            //now after pastDate
            if(sensorValues==0){
                actualValue = sensorValues;
                writeToSPreference(actualValue, now.getTime(), false,false);
            }else {
                //sensorValues != 0
                //case not booted > 24 hours
                actualValue = 0;
                writeToSPreference(sensorValues, now.getTime(), false,true);
            }
        }

        sendSensorValuesToActivity(actualValue);
    }

    private void sendSensorValuesToActivity(int values) {
        Intent intent = new Intent("UpdateSensorValues");
        // You can also include some extra data.
        intent.putExtra("Values", values);
        sendBroadcast(intent);
    }

    //boolean cumulative for case sensorRead reset when reboot
    //boolean reductive for case handphone doesn't reboot
    private void writeToSPreference(int values, long timestamp, boolean cumulative, boolean reductive){
        SharedPreferences sh = getSharedPreferences("ServiceStorage", MODE_PRIVATE);
        SharedPreferences.Editor shEdit = sh.edit();
        shEdit.putInt("values", values);
        shEdit.putLong("timestamp", timestamp);
        shEdit.putBoolean("cumulative", cumulative);
        shEdit.putBoolean("reductive", reductive);
        shEdit.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

