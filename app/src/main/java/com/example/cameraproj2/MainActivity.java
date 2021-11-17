package com.example.cameraproj2;

import android.content.Context;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = "Cam2Logs";
    String[] myCameras = null;
    private CameraManager mCameraManager    = null;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            //Получение списка камер
            myCameras = new String[mCameraManager.getCameraIdList().length];
            // выводим информацию по камере
            for (String cameraID : mCameraManager.getCameraIdList()) {
                Log.i(LOG_TAG, "cameraID: " + cameraID);
                int id = Integer.parseInt(cameraID);
                // Получениe характеристик камеры
                CameraCharacteristics cc = mCameraManager.getCameraCharacteristics(cameraID);
                // Получения списка выходного формата, который поддерживает камера
                StreamConfigurationMap configurationMap =
                        cc.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
                //  Определение какая камера куда смотрит
                int Faceing = cc.get(CameraCharacteristics.LENS_FACING);

                if (Faceing == CameraCharacteristics.LENS_FACING_FRONT) {
                    Log.i(LOG_TAG, "Camera with ID: " + cameraID + "  is FRONT CAMERA  ");
                }

                if (Faceing == CameraCharacteristics.LENS_FACING_BACK) {
                    Log.i(LOG_TAG, "Camera with: ID " + cameraID + " is BACK CAMERA  ");
                }

                // Получения списка разрешений которые поддерживаются для формата jpeg
                Size[] sizesJPEG = configurationMap.getOutputSizes(ImageFormat.JPEG);

                if (sizesJPEG != null) {
                    for (Size item : sizesJPEG) {
                        Log.i(LOG_TAG, "w:" + item.getWidth() + " h:" + item.getHeight());
                    }
                } else {
                    Log.i(LOG_TAG, "camera don`t support JPEG");
                }
            }
        }catch (CameraAccessException e){
            Log.e(LOG_TAG,e.getMessage());
            e.printStackTrace();
        }

    }
}