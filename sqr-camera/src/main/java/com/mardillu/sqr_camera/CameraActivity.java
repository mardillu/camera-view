package com.mardillu.sqr_camera;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;

import java.io.Serializable;


public class CameraActivity extends AppCompatActivity {

    public static final String TAG = CameraActivity.class.getSimpleName();

    static SquareCameraCallback cameraCallback;
    static int requestCode;
    static int docType; //1=profile; 2=id document
    int intColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.squarecamera__CameraFullScreenTheme);
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.squarecamera__activity_camera);

        if (savedInstanceState == null) {
            String color = getIntent().getStringExtra("navigation_color");
            intColor = validateColor(color);
            if (checkHasPermission()){
                Fragment cameraFragment = CameraFragment.newInstance(intColor);
                Bundle bundle = new Bundle();
                bundle.putInt("doc_type", docType);
                cameraFragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, cameraFragment, CameraFragment.TAG)
                        .commit();
            }else {
                requestPermission();
            }
        }
    }

    boolean checkHasPermission() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void allowAccess(View view){
        requestPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Fragment cameraFragment = CameraFragment.newInstance(intColor);
                    Bundle bundle = new Bundle();
                    bundle.putInt("doc_type", docType);
                    cameraFragment.setArguments(bundle);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, cameraFragment, CameraFragment.TAG)
                            .commit();
                } else {

                }
            }
        }
    }

    public static void init(int requesCode, SquareCameraCallback callback, int dType){
        cameraCallback = callback;
        requestCode = requesCode;
        docType = dType;
    }

    private int validateColor(String color){
        try {
            return Color.parseColor(color);
        }catch (IllegalArgumentException iae){
            Log.e(TAG, "validateColor: Invalid Hex color string. Hex color string should be in the form #fff or #ffffff");
            iae.printStackTrace();
        }

        return Color.parseColor("black");
    }

    public void returnPhotoUri(Uri uri) {
        Intent data = new Intent();
        if (uri == null){
            data.putExtra("data_available", false);
        }else {
            data.putExtra("data_available", true);
        }
        data.setData(uri);

        if (cameraCallback != null){
            cameraCallback.onPictureTaken(requestCode, uri);
        }

        if (getParent() == null) {
            setResult(RESULT_OK, data);
        } else {
            getParent().setResult(RESULT_OK, data);
        }

        finish();
    }

    public void cancelCamera(){
        Intent data = new Intent();
        data.putExtra("data_available", false);

        if (cameraCallback != null){
            cameraCallback.onCancel(requestCode);
        }
        if (getParent() == null) {
            setResult(RESULT_CANCELED, data);
        } else {
            getParent().setResult(RESULT_CANCELED, data);
        }

        finish();
    }

    public void onRetry(View view) {
        getSupportFragmentManager().popBackStack();
    }
}
