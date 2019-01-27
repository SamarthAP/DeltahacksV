package com.example.android.testapplicationaudio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import cafe.adriel.androidaudioconverter.AndroidAudioConverter;
import cafe.adriel.androidaudioconverter.callback.IConvertCallback;
import cafe.adriel.androidaudioconverter.callback.ILoadCallback;
import cafe.adriel.androidaudioconverter.model.AudioFormat;

public class MainActivity extends AppCompatActivity {

    //Declare variables
    Button btnRecord, btnStopRecord, btnPlay, btnStop;
    String pathSave = "";
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;

    final int REQUEST_PERMISSION_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AndroidAudioConverter.load(this, new ILoadCallback() {
            @Override
            public void onSuccess() {
                // Great!
            }
            @Override
            public void onFailure(Exception error) {
                // FFmpeg is not supported by device
            }
        });

        //Request runtime position
        if (!checkPermissionFromDevice())
            requestPermission();

        //Init view
        btnPlay = (Button)findViewById(R.id.btnPlay);
        btnRecord = (Button)findViewById(R.id.btnStartRecord);
        btnStop = (Button)findViewById(R.id.btnStop);
        btnStopRecord = (Button)findViewById(R.id.btnStopRecord);


        // from Android M, request runtime permission
            btnRecord.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    if (checkPermissionFromDevice()) {
                        pathSave = Environment.getExternalStorageDirectory().getAbsolutePath() +
                                "/" + UUID.randomUUID().toString() + "_audio_record.3aac";
                        setUpMediaRecorder();
                        try {
                            mediaRecorder.prepare();
                            mediaRecorder.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        btnPlay.setEnabled(false);
                        btnStop.setEnabled(false);

                        Toast.makeText(MainActivity.this, "Recording...", Toast.LENGTH_SHORT).show();
                    } else {
                        requestPermission();
                    }
                }
            });

            btnStopRecord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View view){
                    mediaRecorder.stop();
                    btnStop.setEnabled(false);
                    btnPlay.setEnabled(true);
                    btnRecord.setEnabled(true);
                    btnStopRecord.setEnabled(false);
                }
            });

            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btnStop.setEnabled(true);
                    btnStopRecord.setEnabled(true);
                    btnRecord.setEnabled(false);
                    btnPlay.setEnabled(false);

                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(pathSave);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mediaPlayer.start();
                    Toast.makeText(MainActivity.this, "Playing...", Toast.LENGTH_LONG).show();
                }
            });

            btnStop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnStopRecord.setEnabled(false);
                    btnRecord.setEnabled(true);
                    btnPlay.setEnabled(true);
                    btnStop.setEnabled(false);

                    if (mediaPlayer != null){
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        convertFile();
                        setUpMediaRecorder();
                    }
                }
            });
        }

    private void convertFile() {
        Log.e("MEDIAFILE",  pathSave);
        File aacFile = new File(pathSave);
        Log.e("MEDIAFILE", "CONVERTING");
        IConvertCallback callback = new IConvertCallback() {
            @Override
            public void onSuccess(File convertedFile) {
                Log.e("MEDIAFILE", convertedFile.toString());
                // So fast? Love it!
            }
            @Override
            public void onFailure(Exception error) {
                Log.e("MEDIAFILE", error.toString());
                // Oops! Something went wrong
            }
        };
        AndroidAudioConverter.with(this)
                // Your current audio file
                .setFile(aacFile)

                // Your desired audio format
                .setFormat(AudioFormat.WAV)

                // An callback to know when conversion is finished
                .setCallback(callback)

                // Start conversion
                .convert();
    }

    private void setUpMediaRecorder() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSave);
    }

    private void setUpNewFile() {

    }



    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
        }, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_PERMISSION_CODE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    private boolean checkPermissionFromDevice(){
        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int record_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        return (write_external_storage_result == PackageManager.PERMISSION_GRANTED) && (record_audio_result == PackageManager.PERMISSION_GRANTED);
    }
}
