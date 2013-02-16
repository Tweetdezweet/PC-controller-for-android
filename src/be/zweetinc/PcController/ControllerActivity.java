package be.zweetinc.PcController;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ControllerActivity extends Activity implements SensorEventListener{
    private SensorManager sensorManager;
    private Sensor sensor;

    private float mLastX, mLastY, mLastZ;

    private boolean mInitialized;
    private final float NOISE = (float) 0.25;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ControllerActivity", "onCreate just ran");
        setContentView(R.layout.main);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

        ListIterator<Sensor> sensorListIterator = sensorList.listIterator();
        Log.d("SensorInfo", "Sensor list has " + sensorList.size() + " entries.");

        while(sensorListIterator.hasNext()){
            Sensor tempSensor = sensorListIterator.next();
            Log.d("SensorInfo" , "Name: " + tempSensor.getName() + ", type: " + tempSensor.getType() );
        }

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        //makeConnection();
    }



    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
        Log.d("ControllerActivity", "onPause just ran");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("ControllerActivity", "onDestroy just ran");
    }

    @Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.d("ControllerActivity", "onResume just ran");
    }

    @Override
    public void onConfigurationChanged(Configuration config){
        super.onConfigurationChanged(config);
        Log.d("ControllerActivity", "onConfigurationChanged just ran");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        if (!mInitialized) {
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            Log.d("SensorInfo", "X :" + "0.0");
            Log.d("SensorInfo", "Y :" + "0.0");
            Log.d("SensorInfo", "Z :" + "0.0");
            mInitialized = true;
        } else {
            float deltaX = Math.abs(mLastX - x);
            float deltaY = Math.abs(mLastY - y);
            float deltaZ = Math.abs(mLastZ - z);
            if (deltaX < NOISE) deltaX = (float)0.0;
            if (deltaY < NOISE) deltaY = (float)0.0;
            if (deltaZ < NOISE) deltaZ = (float)0.0;
            mLastX = x;
            mLastY = y;
            mLastZ = z;
            Log.d("SensorInfo", "X :" + deltaX);
            Log.d("SensorInfo", "Y :" + deltaY);
            Log.d("SensorInfo", "Z :" + deltaZ);

//            iv.setVisibility(View.VISIBLE);
//            if (deltaX > deltaY) {
//                iv.setImageResource(R.drawable.horizontal);
//            } else if (deltaY > deltaX) {
//                iv.setImageResource(R.drawable.vertical);
//            } else {
//                iv.setVisibility(View.INVISIBLE);
//            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
