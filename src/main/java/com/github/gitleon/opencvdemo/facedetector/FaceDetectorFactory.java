package com.github.gitleon.opencvdemo.facedetector;

import com.github.git_leon.logging.SimpleLoggerInterface;
import com.github.gitleon.opencvdemo.utils.CascadeClassifierFactory;
import com.github.gitleon.opencvdemo.utils.LoggerSingleton;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalBiFunction;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalFunction;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalSupplier;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;

/**
 * @author leon
 */
public class FaceDetectorFactory {
    private SimpleLoggerInterface logger;

    public FaceDetectorFactory(SimpleLoggerInterface logger) {
        this.logger = logger;
    }

    public FaceDetector createDefault() {
        opencv_objdetect.CascadeClassifier classifier = CascadeClassifierFactory.FRONTALFACE_ALT.createClassifier();

        CanvasFrame frame = ExceptionalBiFunction.tryInvoke(
                CanvasFrame::new, "", 2.2);
        logger.info("CanvasFrame created");

        FrameGrabber grabber = ExceptionalFunction.tryInvoke(
                FrameGrabber::createDefault, 0);
        logger.info("FrameGrabber created");

        FrameRecorder recorder = ExceptionalSupplier.tryInvoke(
                () -> FrameRecorder.createDefault("output.avi", 1280, 720));
        LoggerSingleton.GLOBAL.info("FrameRecorder created");

        return new FaceDetectorBuilder()
                .setClassifier(classifier)
                .setGrabber(grabber)
                .setFrame(frame)
                .setRecorder(recorder)
                .build();
    }
}
