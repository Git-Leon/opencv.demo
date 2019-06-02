package com.github.gitleon.opencvdemo.facedetector;

import com.github.gitleon.opencvdemo.utils.FrameGrabberWrapper;
import com.github.gitleon.opencvdemo.utils.FrameRecorderWrapper;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.*;

/**
 * @author leon
 */
public class FaceDetector implements FaceDetectorInterface {
    private final FaceClassifier classifier;
    private final FrameGrabberWrapper grabber;
    private final FrameRecorderWrapper recorder;
    private final OpenCVFrameConverter.ToMat converter;
    private final CanvasFrame canvas;

    FaceDetector(opencv_objdetect.CascadeClassifier classifier, FrameGrabber grabber, FrameRecorder recorder, CanvasFrame canvas) {
        this.classifier = new FaceClassifier(classifier);
        this.grabber = new FrameGrabberWrapper(grabber);
        this.recorder = new FrameRecorderWrapper(recorder);
        this.converter = new OpenCVFrameConverter.ToMat();
        this.canvas = canvas;
    }

    @Override
    public void start() {
        grabber.start();
        recorder.start();
    }

    @Override
    public void detect() {
        if(canvas.isVisible()) {
            Frame frame = getFrameFromCamera();
            canvas.showImage(frame);
            recorder.record(frame);
        }
    }

    @Override
    public void stop() {
        canvas.dispose();
        recorder.stop();
        grabber.stop();
    }

    private Frame getFrameFromCamera() {
        return converter.convert(classifier.classify(converter.convert(grabber.grab())));
    }
}
