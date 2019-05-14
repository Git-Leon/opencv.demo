package com.github.gitleon.opencvdemo.facedetector;

import com.github.gitleon.opencvdemo.facedetector.classifier.FrontFaceClassifier;
import com.github.gitleon.opencvdemo.utils.FrameGrabberWrapper;
import com.github.gitleon.opencvdemo.utils.FrameRecorderWrapper;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.*;

public class FaceDetector {
    private final FrontFaceClassifier classifier;
    private final FrameGrabberWrapper grabber;
    private final FrameRecorderWrapper recorder;
    private final OpenCVFrameConverter.ToMat converter;
    private final CanvasFrame frame;

    FaceDetector(opencv_objdetect.CascadeClassifier classifier, FrameGrabber grabber, FrameRecorder recorder, CanvasFrame frame) {
        this.classifier = new FrontFaceClassifier(classifier);
        this.grabber = new FrameGrabberWrapper(grabber);
        this.recorder = new FrameRecorderWrapper(recorder);
        this.converter = new OpenCVFrameConverter.ToMat();
        this.frame = frame;
    }

    public void detect() {
        recorder.start();
        grabber.start();
        opencv_core.Mat image = this.grabAndConvert();
        while (frame.isVisible()) {
            Frame convertedFrame = converter.convert(classifier.classify(image));

            frame.showImage(convertedFrame);
            recorder.record(convertedFrame);

            this.grabAndConvert();
        }
        frame.dispose();
        recorder.stop();
        grabber.stop();
    }

    private opencv_core.Mat grabAndConvert() {
        return converter.convert(grabber.grab());
    }
}
