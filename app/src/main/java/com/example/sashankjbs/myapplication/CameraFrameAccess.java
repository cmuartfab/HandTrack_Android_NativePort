package com.example.sashankjbs.myapplication;

/**
 * Created by sashankjbs on 07/11/16.
 */


import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.graphics.YuvImage;
import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;
import android.graphics.Rect;
import android.graphics.ImageFormat;
import android.graphics.BitmapFactory;


public class CameraFrameAccess {
    public void onPreviewFrame(byte[] data, Camera cam) {

        Camera.Parameters parameters = cam.getParameters();
        int width = parameters.getPreviewSize().width;
        int height = parameters.getPreviewSize().height;
        ByteArrayOutputStream outstr = new ByteArrayOutputStream();
        Rect rect = new Rect(0, 0, width, height);
        YuvImage yuvimage=new YuvImage(data,ImageFormat.NV21,width,height,null);
        yuvimage.compressToJpeg(rect, 100, outstr);
        Bitmap bmp = BitmapFactory.decodeByteArray(outstr.toByteArray(), 0, outstr.size());
        // custom image data processing



    }
}
