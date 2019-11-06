package com.example.myapplication;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private TextView reading;

    private Sensor proximitySensor;
    private SensorEventListener proximitySensorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        reading = findViewById(R.id.ReadingValue);

        if(proximitySensor == null) {
            finish();
        }

        proximitySensorListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                reading.setText(Float.toString(sensorEvent.values[0]));

                if(sensorEvent.values[0] < proximitySensor.getMaximumRange()) {
                    getWindow().getDecorView().setBackgroundColor(Color.GREEN);

                } else {
                    getWindow().getDecorView().setBackgroundColor(Color.GRAY);

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {
                //
            }
        };
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(proximitySensorListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(proximitySensorListener, proximitySensor, 2 * 1000 * 1000);
    }


}