package com.github.gitleon.opencvdemo;

import com.github.gitleon.opencvdemo.facedetector.FaceDetector;
import com.github.gitleon.opencvdemo.facedetector.FaceDetectorFactory;
import com.github.gitleon.opencvdemo.utils.LoggerSingleton;

/**
 * @author leon
 */
public class MainApplication {
    public static void main(String[] args) {
        try {
            FaceDetector facedetector = new FaceDetectorFactory(LoggerSingleton.GLOBAL).createDefault();
            facedetector.start();
            while(true) {
                facedetector.detect();
            }
        } catch (Throwable throwable) {
            LoggerSingleton.GLOBAL.throwable(throwable);
        }
    }
}
