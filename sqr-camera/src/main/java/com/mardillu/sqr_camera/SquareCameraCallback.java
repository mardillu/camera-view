package com.mardillu.sqr_camera;

import android.net.Uri;

public interface SquareCameraCallback {
    void onPictureTaken(Uri pictureUri);
    void onCancel();
    void onError(Exception e);
}
