package com.github.gitleon.opencvdemo.facedetector;

import com.github.gitleon.opencvdemo.utils.FrameGrabberWrapper;
import com.github.gitleon.opencvdemo.utils.FrameRecorderWrapper;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.*;

/**
 * @author leon
 */
public class FaceDetector {
    private final FaceClassifier classifier;
    private final FrameGrabberWrapper grabber;
    private final FrameRecorderWrapper recorder;
    private final OpenCVFrameConverter.ToMat converter;
    private final CanvasFrame frame;

    FaceDetector(opencv_objdetect.CascadeClassifier classifier, FrameGrabber grabber, FrameRecorder recorder, CanvasFrame frame) {
        this.classifier = new FaceClassifier(classifier);
        this.grabber = new FrameGrabberWrapper(grabber);
        this.recorder = new FrameRecorderWrapper(recorder);
        this.converter = new OpenCVFrameConverter.ToMat();
        this.frame = frame;
    }

    public void detect() {
        grabber.start();
        recorder.start();
        while (frame.isVisible()) {
            Frame convertedFrame = converter.convert(classifier.classify(grabAndConvert()));

            frame.showImage(convertedFrame);
            recorder.record(convertedFrame);
        }
        frame.dispose();
        recorder.stop();
        grabber.stop();
    }

    private opencv_core.Mat grabAndConvert() {
        return converter.convert(grabber.grab());
    }
}
