package com.github.gitleon.opencvdemo.facedetector;

import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class FaceDetectorBuilder {
    private opencv_objdetect.CascadeClassifier classifier;
    private FrameGrabber grabber;
    private CanvasFrame frame;
    private FrameRecorder recorder;

    public FaceDetectorBuilder setClassifier(opencv_objdetect.CascadeClassifier classifier) {
        this.classifier = classifier;
        return this;
    }

    public FaceDetectorBuilder setGrabber(FrameGrabber grabber) {
        this.grabber = grabber;
        return this;
    }

    public FaceDetectorBuilder setFrame(CanvasFrame frame) {
        this.frame = frame;
        return this;
    }

    public FaceDetectorBuilder setRecorder(FrameRecorder recorder) {
        this.recorder = recorder;
        return this;
    }

    public FaceDetector build() {
        return new FaceDetector(classifier, grabber, recorder, frame);
    }
}