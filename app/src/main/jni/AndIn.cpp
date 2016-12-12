//
//  AndIn.cpp
//  HandTracker
//
//  Created by Sashank Jujjavarapu on 08/11/16.
//  Copyright © 2016 Pratik Prakash. All rights reserved.
//

#include "AndIn.h"

using namespace std;
using namespace cv;

Mat  imageIn(Mat &Inp)
{
    bool TRAIN_MODEL = 0;           //1 if you are training the models, 0 if you are running the program to predict
    bool TEST_MODEL  = 1;           //0 if you are training the models, 1 if you are running the program to predict
    
    int target_width = 320;			// for resizing the input (small is faster)
    
    // maximum number of image masks that you will use
    // must have the masks prepared in advance
    // only used at training time
    int num_models_to_train = 16;
    
    
    // number of models used to compute a single pixel response
    // must be less than the number of training models
    // only used at test time
    int num_models_to_average = 10;
    
    // runs detector on every 'step_size' pixels
    // only used at test time
    // bigger means faster but you lose resolution
    // you need post-processing to get contours
    int step_size = 3;
    
    // Assumes a certain file structure e.g., /root/img/basename/00000000.jpg
    string root = "/Users/sashankjbs/Desktop/ArtFab/handtrack-master/";       //replace with path to your Xcode project
    string basename = "";
    string img_prefix		= root + "img"		+ basename + "/";			// color images
    string msk_prefix		= root + "mask"     + basename + "/";			// binary masks
    string model_prefix		= root + "models"	+ basename + "/";			// output path for learned models
    string globfeat_prefix  = root + "globfeat" + basename + "/";			// output path for color histograms
    
    
    // types of features to use (you will over-fit if you do not have enough data)
    // r: RGB (5x5 patch)
    // v: HSV
    // l: LAB
    // b: BRIEF descriptor
    // o: ORB descriptor
    // s: SIFT descriptor
    // u: SURF descriptor
    // h: HOG descriptor
    string feature_set = "rvl";
    
    
    
    if(TRAIN_MODEL)
    {
        cout << "Training...\n";
        HandDetector hd;
        hd.loadMaskFilenames(msk_prefix);
        hd.trainModels(basename, img_prefix, msk_prefix,model_prefix,globfeat_prefix,feature_set,num_models_to_train,target_width);
        cout << "Done Training...\n";
    }
    
    Mat im;

    Mat pp_res;


    im = Inp;
 //   if(!im.data) break;

    if(TEST_MODEL)
    {
        cout << "Testing...\n";
        
        HandDetector hd;
        hd.testInitialize(model_prefix,globfeat_prefix,feature_set,num_models_to_average,target_width);
        
        


        hd.test(im,num_models_to_average,step_size);
        pp_res = hd.postprocess(hd._response_img);

        hd.colormap(pp_res,pp_res,0);
        resize(pp_res,pp_res,Inp.size(),0,0,INTER_LINEAR);


        addWeighted(Inp,0.7,pp_res,0.3,0,pp_res);


        
    }

    return pp_res;
    
}
