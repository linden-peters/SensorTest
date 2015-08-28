package com.foo.sensortest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private TextView myOutput;
    private TextView mySensor;
    private Sensor senAcc;
    //private Sensor senPos;
    private String myBuffer = "";
    private SensorManager sm;
    //private Sensor mySensor;
    List<Sensor> sl;

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
        mySensor.setText(Calendar.getInstance().getTime() + "\n\t" + event.values[0] + "\n\t" + event.values[1] + "\n\t" + event.values[2]);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
