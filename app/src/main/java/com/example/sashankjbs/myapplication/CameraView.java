package com.example.sashankjbs.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import java.io.IOException;
import java.nio.Buffer;
import android.view.WindowManager;
import android.widget.ImageView;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback,Camera.PreviewCallback{

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private byte [] rgbbuffer = new byte[256 * 256];
    private int [] rgbints = new int[256 * 256];
    private Context mContext;

    protected final Paint rectanglePaint = new Paint();


    public CameraView(Context context, Camera camera){
        super(context);

        mCamera = camera;
        mContext = context;
        mCamera.setDisplayOrientation(90);
        //get the holder and set this class as the callback, so we can get camera data here
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(new Rect((int) Math.random() * 100,
                (int) Math.random() * 100, 200, 200), rectanglePaint);
        Log.w(this.getClass().getName(), "On Draw Called");
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try{
            Log.d("Camera", "Set Preview");
            //when the surface is created, we can set the camera to draw images in this surfaceholder
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.setPreviewCallback(this);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d("ERROR", "Camera error on surfaceCreated " + e.getMessage());
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

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

//    Camera.PreviewCallback previewCallback = new Camera.PreviewCallback()
//    {
//        public void onPreviewFrame(byte[] data, Camera cam) {



//                Camera.Parameters parameters = cam.getParameters();
//                int width = parameters.getPreviewSize().width;
//                int height = parameters.getPreviewSize().height;
//                ByteArrayOutputStream outstr = new ByteArrayOutputStream();
//                Rect rect = new Rect(0, 0, width, height);
//                YuvImage yuvimage=new YuvImage(data, ImageFormat.NV21,width,height,null);
//                yuvimage.compressToJpeg(rect, 100, outstr);
//                Bitmap bmp = BitmapFactory.decodeByteArray(outstr.toByteArray(), 0, outstr.size());
                // custom image data processing

//                VisionEng v_eng = new VisionEng();
//                v_eng.helloWorld(bmp,true);





//        }

//    };





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

                if (bmp != null)
                {
                    int k = 0;
                }

                if (bmp == null){
                    int k = 1;
                }

                ((MainActivity)mContext).runOnUiThread(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                               ImageView image = (ImageView) findViewById(R.id.imageView1);
                                                               image.setImageBitmap(bmp);

                                                           }
                                                       });

                // display the imageView
 //               setContentView(image) ;

                // Do your drawing here
                // So this data value you're getting back is formatted in YUV format and you can't do much
                // with it until you convert it to rgb
//                int bwCounter=0;
//                int yuvsCounter=0;
//                for (int y=0;y<160;y++) {
//                    System.arraycopy(data, yuvsCounter, rgbbuffer, bwCounter, 240);
//                    yuvsCounter=yuvsCounter+240;
//                    bwCounter=bwCounter+256;
//                }

//                for(int i = 0; i < rgbints.length; i++){
//                    rgbints[i] = (int)rgbbuffer[i];
//                }

                //decodeYUV(rgbbuffer, data, 100, 100);
//                c.drawBitmap(rgbints, 0, 256, 0, 0, 256, 256, false, new Paint());

//                Log.d("SOMETHING", "Got Bitmap");

            }
        } finally {
            // do this in a finally so that if an exception is thrown
            // during the above, we don't leave the Surface in an
            // inconsistent state
 //           if (c != null) {
  //              mHolder.unlockCanvasAndPost(c);
            }
        }






    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mCamera.setPreviewCallback(null);
        mCamera.stopPreview();
        mCamera.release();

    }
}