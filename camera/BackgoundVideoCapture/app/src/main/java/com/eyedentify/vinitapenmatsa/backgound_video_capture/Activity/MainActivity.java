package com.eyedentify.vinitapenmatsa.backgound_video_capture.Activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eyedentify.vinitapenmatsa.backgound_video_capture.R;
import com.eyedentify.vinitapenmatsa.backgound_video_capture.service.BackgroundCameraService;

public class MainActivity extends AppCompatActivity {

    private Boolean isServiceBound = false;
    private BackgroundCameraService backgroundCameraService;

    private ServiceConnection myBackgroundCameraService = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            BackgroundCameraService.LocalBinder localBinder = (BackgroundCameraService.LocalBinder)iBinder;
            backgroundCameraService = localBinder.getService();
            backgroundCameraService.captureImage();
            isServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent backgroundVideoIntent = new Intent(this,BackgroundCameraService.class);

        Button captureImageButton = (Button) findViewById(R.id.imageCapture);
        captureImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(backgroundVideoIntent,myBackgroundCameraService, Context.BIND_AUTO_CREATE);

            }
        });

        Button stopCapturingImage = (Button) findViewById(R.id.stopCapture);
        stopCapturingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(myBackgroundCameraService);
            }
        });





    }



}
