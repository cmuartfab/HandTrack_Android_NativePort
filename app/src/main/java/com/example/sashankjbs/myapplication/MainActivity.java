package com.example.sashankjbs.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.graphics.ImageFormat;
import android.hardware.Camera.PreviewCallback;
import android.widget.ImageView;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SurfaceHolder.Callback,Camera.PreviewCallback{

    private Camera mCamera = null;
    private Camera mCamera2 = null;
    private CameraView mCameraView = null;
    private CameraView mCameraView2 = null;
    private SurfaceHolder mHolder;
    private ImageView imageView;
    FrameLayout cameraFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cameraFrameLayout = (FrameLayout)findViewById(R.id.camera_view);
        imageView = (ImageView) findViewById(R.id.imageView1);

        try{
         //   CameraManager manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        //    String[] cameraIds = manager.getCameraIdList();
            mCamera = Camera.open(1);//you can use open(int) to use different cameras
            mCamera.setPreviewCallback(this);
//            mCamera2 = Camera.open(1);
        } catch (Exception e){
            Log.d("ERROR", "Failed to get camera: " + e.getMessage());
        }

        if(mCamera != null) {
            mCameraView = new CameraView(this,mCamera);//create a SurfaceView to show camera data
            mHolder = mCameraView.getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
//            mCameraView2 = new CameraView(this,mCamera2);//create a SurfaceView to show camera data

//            FrameLayout out_view = (FrameLayout)findViewById(R.id.out_view);
            cameraFrameLayout.addView(mCameraView);//add the SurfaceView to the layout

//            out_view.addView(mCameraView2);

        }

        //btn to close the application
        ImageButton imgClose = (ImageButton)findViewById(R.id.imgClose);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });



    }




    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try{
            Log.d("Camera", "Set Preview");
            //when the surface is created, we can set the camera to draw images in this surfaceholder
            mCamera.setPreviewDisplay(holder);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if(mHolder.getSurface() == null)//check if the surface is ready to receive camera data
            return;

        try{
            mCamera.stopPreview();
        } catch (Exception e){
            //this will happen when you are trying the camera if it's not running
        }

        //now, recreate the camera preview
        try{
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceChanged " + e.getMessage());
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
 //       mCameraView.getHolder().removeCallback(mCameraView);
 //       mCamera.stopPreview();
 //       mCamera.release();

    }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        Log.d("Camera", "Got a camera frame");

        Canvas c = null;

        if(mHolder == null){
            return;
        }

        try {
            synchronized (mHolder) {
                //               c = mHolder.lockCanvas(null);
                Camera.Parameters parameters = camera.getParameters();
                int width = parameters.getPreviewSize().width;
                int height = parameters.getPreviewSize().height;
                ByteArrayOutputStream outstr = new ByteArrayOutputStream();
                Rect rect = new Rect(0, 0, width, height);
                YuvImage yuvimage = new YuvImage(data, ImageFormat.NV21,width,height,null);
                yuvimage.compressToJpeg(rect, 100, outstr);
                final Bitmap bmp = BitmapFactory.decodeByteArray(outstr.toByteArray(), 0, outstr.size());

                Matrix mtx = new Matrix();
                mtx.postRotate(270);
                // Rotating Bitmap



                VisionEng veng = new VisionEng();

                veng.helloWorld(bmp);

                final Bitmap bm = Bitmap.createBitmap(bmp, 0, 0, width, height, mtx, true);

                if (bmp != null)
                {
                    int k = 0;
                }

                if (bmp == null){
                    int k = 1;
                }

               imageView.setImageBitmap(bm);

            }
        } finally {

        }
    }






}
