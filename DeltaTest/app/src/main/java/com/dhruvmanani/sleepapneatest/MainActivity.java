package com.dhruvmanani.sleepapneatest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private  TextView xText, yText, zText;
    private  Sensor accelerometer;
    private SensorManager sensorManager;

    private float threshold = 0;

    private float deltaX = 0, deltaY = 0, deltaZ = 0;

    private float lastX = 0, lastY = 0, lastZ = 0;

    final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private final String[] requiredPermissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!checkWritePermissions()) requestStoragePermissions();

        getSupportActionBar().setTitle("Goodnight");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.app_bar);

        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            threshold = accelerometer.getMaximumRange() / 2;
        }
        else {
        Toast.makeText(getBaseContext(), "Can't Find Accelerometer", Toast.LENGTH_LONG).show();
        }

        xText = (TextView)findViewById(R.id.xText);
        yText = (TextView)findViewById(R.id.yText);
        zText = (TextView)findViewById(R.id.zText);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // clean current values
        displayCleanValues();
        // display the current x,y,z accelerometer values
        displayCurrentValues();

        // get the change of the x,y,z values of the accelerometer
        deltaX = Math.abs(lastX - event.values[0]);
        deltaY = Math.abs(lastY - event.values[1]);
        deltaZ = Math.abs(lastZ - event.values[2]);

        // if the change is below 2, it is just plain noise
        if (deltaX < 2)
            deltaX = 0;
        if (deltaY < 2)
            deltaY = 0;
        if (deltaZ < 2)
            deltaX = 0;

        String entry = xText.getText().toString() + "," + yText.getText().toString() + "," + zText.getText().toString() + ",";
        try {

            File sdCard = Environment.getExternalStorageDirectory();
            File dir = new File(sdCard.getAbsolutePath() + "/sean");
            Boolean dirsMade = dir.mkdir();
            //System.out.println(dirsMade);
            Log.v("Accel", dirsMade.toString());

            File file = new File(dir, "output.csv");
            FileOutputStream f = new FileOutputStream(file, true);

            try {
                f.write(entry.getBytes());
                f.flush();
                f.close();
                Toast.makeText(getBaseContext(), "Data saved", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void displayCleanValues() {
        xText.setText("0.0");
        yText.setText("0.0");
        zText.setText("0.0");
    }

    // display the current x,y,z accelerometer values
    public void displayCurrentValues() {
        xText.setText(Float.toString(deltaX));
        yText.setText(Float.toString(deltaY));
        zText.setText(Float.toString(deltaZ));
    }

    public void requestStoragePermissions() {
        ActivityCompat.requestPermissions(
                this,
                requiredPermissions,
                REQUEST_CODE_ASK_PERMISSIONS);
    }

//    private void checkPermissions() {
//        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
//            return;
//        }
//        Toast.makeText(getBaseContext(), "Permission is already granted", Toast.LENGTH_LONG).show();
//    }

    private boolean checkWritePermissions() {
        for (String permission : requiredPermissions) {
            if (!(ActivityCompat.checkSelfPermission(this, permission) ==
                    PackageManager.PERMISSION_GRANTED)) {

                return false;
            }
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Toast.makeText(getBaseContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "WRITE_EXTERNAL_STORAGE Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
