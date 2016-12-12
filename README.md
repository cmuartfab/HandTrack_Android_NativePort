# HandTrackAndroidNativePort


This repository contains the Android port of C++ written for foreground/background segmentation of the human hand in videos from an egocentric perspective, using pixel-level classification. The native C++ can be found at https://github.com/cmuartfab/handtrack.


# Dependencies

#IDE
This project is based in Android Studio 2.2.2
Android Studio can be downloaded from https://developer.android.com/studio/index.html
Installation instructions can be found at https://developer.android.com/studio/install.html

#Android SDK
SDK can be downloaded using Android Studio. The application has been compiled for API version 24

#Android NDK
Based your system, NDK can be downloaded from https://developer.android.com/ndk/downloads/index.html


#OpenCV 
Native C++ code which is ported to Android uses OpenCV library(Version 2.4.13/2.4.12/2.4.9). OpenCV be downloaded from http://opencv.org/downloads.html 
Kindly download OpenCV 2.4.9 since the ARM binaries used to build the application are of version OpenCV 2.4.9


#Project Installation

To install the project complete the following steps.
1. Clone this repository: git clone https://github.com/sashankjbs/HandTrackAndroidNativePort.git
2. Open the Project in Android Studio
3. In the gradle folder, make the following changes to local.properties
ndk.dir = (NDK path in your system)
sdk.dir = (SDK path in your system)

4. In the src folder, make the following changes to build.gradle
opencvSrcDir = (OpenCV 2.4.9 path in your system)
ndk{
cppFlags.addAll(["-I/(OpenCV 2.4.9 path in your system)/include/",
"-I/(OpenCV 2.4.9 path in your system)/modules/core/include/"])
}

5. Copy the files in "models/" to "/sdcard/handtracking/models" on the phone

6. Copy the files in "globfeat/" to "/sdcard/handtracking/globfeat" on the phone


#Running the Project from PC

1. The Android device which is going to use this application should have an Android version of API level 17 or above. Latest devices can be readily used
2. Plug the device to the PC through USB Cable.
3. Open the Project in Android Studio
4. Click the RUN button(green with an arrow)
5. Pop up which says "Select Deployment Target" will appear, select the connected device
6. Application should open up in the mobile.






