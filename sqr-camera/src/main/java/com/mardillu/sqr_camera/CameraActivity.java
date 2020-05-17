package com.mardillu.sqr_camera;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;


public class CameraActivity extends AppCompatActivity {

    public static final String TAG = CameraActivity.class.getSimpleName();

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
            int intColor = validateColor(color);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, CameraFragment.newInstance(intColor), CameraFragment.TAG)
                    .commit();

        }
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
