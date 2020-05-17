package com.mardillu.sqrcamera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.mardillu.sqr_camera.CameraActivity;
import com.mardillu.sqr_camera.SquareCameraCallback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements SquareCameraCallback {

    private static final String TAG = "MainActivity";

    private final int REQUEST_CAMERA= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start CameraActivity
                CameraActivity.init(MainActivity.this);
                Intent startCustomCameraIntent = new Intent(MainActivity.this, CameraActivity.class);
                startCustomCameraIntent.putExtra("navigation_color", "#ffffff");
                startActivityForResult(startCustomCameraIntent, REQUEST_CAMERA);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_CAMERA) {
            Uri photoUri = data.getData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPictureTaken(Uri pictureUri) {
        Log.d(TAG, "onActivityResult: " + pictureUri);
    }

    @Override
    public void onCancel() {
        Log.d(TAG, "onActivityResult: Cancel");
    }

    @Override
    public void onError(Exception e){
        Log.d(TAG, "onActivityResult: Error");
    }
}
