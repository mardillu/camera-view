package com.mardillu.sqr_camera;

import android.net.Uri;

public interface SquareCameraCallback {
    void onPictureTaken(int requestCode, Uri pictureUri);
    void onCancel(int requestCode);
    void onError(int requestCode, Exception e);
}
