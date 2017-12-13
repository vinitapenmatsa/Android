package com.eyedentify.vinitapenmatsa.backgound_video_capture.service;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.androidhiddencamera.CameraConfig;
import com.androidhiddencamera.CameraError;
import com.androidhiddencamera.HiddenCameraService;
import com.androidhiddencamera.HiddenCameraUtils;
import com.androidhiddencamera.config.CameraFacing;
import com.androidhiddencamera.config.CameraImageFormat;
import com.androidhiddencamera.config.CameraResolution;
import com.androidhiddencamera.config.CameraRotation;

import java.io.File;

/**
 * Created by vinitapenmatsa on 12/13/17.
 */

public class BackgroundCameraService extends HiddenCameraService {

    private final IBinder mBinder = new LocalBinder();
    CameraConfig mCameraConfig = new CameraConfig()
            .getBuilder(getApplicationContext())
            .setCameraFacing(CameraFacing.FRONT_FACING_CAMERA)
            .setCameraResolution(CameraResolution.MEDIUM_RESOLUTION)
            .setImageFormat(CameraImageFormat.FORMAT_JPEG)
            .setImageRotation(CameraRotation.ROTATION_270)
            .build();


    public class LocalBinder extends Binder{
        public BackgroundCameraService getService(){
            return BackgroundCameraService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent){
        return mBinder;
    }

    @Override
    public void onCreate(){
        super.onCreate();
      if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
          if (HiddenCameraUtils.canOverDrawOtherApps(this)) {
              startCamera(mCameraConfig);
          } else {
              HiddenCameraUtils.openDrawOverPermissionSetting(this);
          }
      }

    }

    public void captureImage(){

    }




    @Override
    public void onImageCapture(File file){

        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
        MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),
                bitmap,"BackgroundImage","Example Background image");

    }



    @Override
    public void onCameraError(@CameraError.CameraErrorCodes int errorCode){

        Log.i("Camera Servce" , "ErrorCode" + errorCode);

    }




}
