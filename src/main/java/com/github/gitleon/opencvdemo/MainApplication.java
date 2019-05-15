package com.github.gitleon.opencvdemo;

import com.github.gitleon.opencvdemo.facedetector.FaceDetectorFactory;
import com.github.gitleon.opencvdemo.utils.LoggerSingleton;

/**
 * @author leon
 */
public class MainApplication {
    public static void main(String[] args) {
        try {
            new FaceDetectorFactory(LoggerSingleton.GLOBAL).createDefault().detect();
        } catch (Throwable throwable) {
            LoggerSingleton.GLOBAL.throwable(throwable);
        }
    }
}
