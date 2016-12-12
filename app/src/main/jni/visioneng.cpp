#include <jni.h>
#include <android/log.h>


#define LOG_TAG "org.opencv.android.Utils"
//#include "common.h"
//#include "opencv2/opencv.hpp"
//#include "opencv2/imgproc.hpp"
#include "opencv2/core/core.hpp"
#include "opencv2/imgproc/imgproc.hpp"
#include <opencv2/flann/config.h>
#include <opencv2/legacy/legacy.hpp>		// EM
#include <opencv2/contrib/contrib.hpp>		// colormap
#include <opencv2/nonfree/nonfree.hpp>		// SIFT
#include "AndIn.h"
#include <time.h>

#ifdef __ANDROID__
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOGE(...)  __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, "my module name", __VA_ARGS__)
#endif
#include <android/bitmap.h>

using namespace cv;

extern "C" {

JNIEXPORT void JNICALL Java_com_example_sashankjbs_myapplication_VisionEng_helloWorld(JNIEnv *env,
                                                                                      jclass,
                                                                                      jobject bitmap) {
    AndroidBitmapInfo bitmapInfo;
    void *pixels = 0;

    Mat frame;
    time_t start = clock();
    CV_Assert(AndroidBitmap_getInfo(env, bitmap, &bitmapInfo) >= 0);
    CV_Assert(bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888);

    int h = bitmapInfo.height;
    int w = bitmapInfo.width;


    CV_Assert(AndroidBitmap_lockPixels(env, bitmap, &pixels) >= 0);
    CV_Assert(pixels);

    Mat mSrc(h, w, CV_8UC4, pixels);
    Mat Src;
    cvtColor(mSrc, Src, CV_RGBA2BGR);


    imageIn(Src).copyTo(Src);
    cvtColor(Src, mSrc, CV_BGR2RGBA);


    AndroidBitmap_unlockPixels(env, bitmap);
    pixels = 0;
    LOGD("time=%d",clock() - start);
}
}