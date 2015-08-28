package com.foo.sensortest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView myOutput;
    private TextView mySensor;
    private String myBuffer = "";
    private Sensor senAcc;
    //private Sensor senPos;
    private SensorManager sm;
    private List<Sensor> sl;
    private char[] axis = new char[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myOutput = (TextView)findViewById(R.id.tvOutput);
        mySensor = (TextView)findViewById(R.id.tvSensor);
        sm = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        //lightSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
        //sm.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        //public void onAccuracyChanged(Sensor sensor, int accuracy){}
        //public void onSensorChanged(SensorEvent event) {}
        sl = sm.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor: sl){
            myBuffer = myBuffer + sensor.getName() + "\n\t" + sensor.toString() + "\n\n";
        }
        //sm.getOrientation()
        myOutput.setText(myBuffer);
        senAcc = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //sm.registerListener(this, senAcc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        if (event.values[0] > 2)
            axis[0] = '<';
        else if(event.values[0] < -2)
            axis[0] = '>';
        else
            axis[0] = ' ';
        if (event.values[1] > 2)
            axis[1] = 'V';
        else if(event.values[1] < -2)
            axis[1] = 'A';
        else
            axis[1] = ' ';
        if (event.values[2] > 2)
            axis[2] = '.';
        else if(event.values[2] < -2)
            axis[2] = 'O';
        else
            axis[2] = ' ';
        mySensor.setText(Calendar.getInstance().getTime()
                + "\n\t" + axis[0] + "\t" + event.values[0]
                + "\n\t" + axis[1] + "\t" + event.values[1]
                + "\n\t" + axis[2] + "\t" + event.values[2]);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this, senAcc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }
}
