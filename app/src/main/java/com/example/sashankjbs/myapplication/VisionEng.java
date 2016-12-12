package com.example.sashankjbs.myapplication;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


import android.graphics.Bitmap;
/**
 * Created by sashankjbs on 31/10/16.
 */

public class VisionEng {

    static {
        System.loadLibrary("visioneng");
    }
    public native void helloWorld(Bitmap bitmap);

}
