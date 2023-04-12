package com.example.app12abril;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.net.Uri;

import com.example.app12abril.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private LeeSensor listener = new LeeSensor();
    private SensorManager manager;
    private StringBuilder string=new StringBuilder();
    private String lucho = "000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView( binding.getRoot());

    }

    @Override
    protected void onResume() {
        super.onResume();
        manager=(SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensores=manager.getSensorList(Sensor.TYPE_PROXIMITY);
        if(sensores.size()>0){

            Sensor acelerometro=sensores.get(0);
            manager.registerListener(listener,acelerometro,SensorManager.SENSOR_DELAY_GAME);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.unregisterListener(listener);
    }

    private class LeeSensor implements SensorEventListener{


        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            //Lo que quiero que haga cuando se produzca el evento.

            if (sensorEvent.values[0] == 0) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + lucho));
                if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                startActivity(intent);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }
}