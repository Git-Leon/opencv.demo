package com.github.gitleon.opencvdemo.facedetector;

import com.github.gitleon.opencvdemo.utils.LoggerSingleton;
import com.github.gitleon.opencvdemo.utils.CascadeClassifierFactory;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class FaceDetectorFactory {
    public static FaceDetector createDefault() {
        opencv_objdetect.CascadeClassifier classifier = CascadeClassifierFactory.FRONTALFACE_ALT.createClassifier();

        FrameGrabber grabber = createDefaultFrameGrabber();
        LoggerSingleton.GLOBAL.info("FrameGrabber created");

        FrameRecorder recorder = createDefaultFrameRecorder();
        LoggerSingleton.GLOBAL.info("FrameRecorder created");

        CanvasFrame frame = new CanvasFrame("", 2.2);
        LoggerSingleton.GLOBAL.info("CanvasFrame created");

        return new FaceDetectorBuilder()
                .setClassifier(classifier)
                .setGrabber(grabber)
                .setFrame(frame)
                .setRecorder(recorder)
                .build();
    }


    private static FrameGrabber createDefaultFrameGrabber() {
        try {
            FrameGrabber frameGrabber = FrameGrabber.createDefault(0);
            return frameGrabber;
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private static FrameRecorder createDefaultFrameRecorder() {
        try {
            FrameRecorder recorder = FrameRecorder.createDefault("output.avi", 1280, 720);
            return recorder;
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
