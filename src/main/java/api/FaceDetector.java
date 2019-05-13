package api;

import api.wrapper.*;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;

import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY;

public class FaceDetector {
    private final FrameGrabberWrapper grabber;
    private final CanvasFrameWrapper frame;
    private final MatWrapper grabbedImage;
    private final FrameRecorderWrapper recorder;
    private final FrameConverterWrapper converter;
    private final FrontFaceClassifier classifier;

    public FaceDetector(String title) {
        this.grabber = new FrameGrabberWrapper();
        this.classifier = new FrontFaceClassifier();
        this.converter = new FrameConverterWrapper();
        grabber.start();
        this.frame = new CanvasFrameWrapper(title, grabber.getGamma());
        this.grabbedImage = new MatWrapper(converter.convert(grabber.grab()));
        this.recorder = new FrameRecorderWrapper(grabbedImage.getImage());
    }

    public void detect() {
        recorder.start();
        grabbedImage.rotate();
        opencv_core.Mat rotatedImage = grabbedImage.getImage().clone();

        grabbedImage.convert(converter, grabber);
        while (frame.isVisible()) {
            opencv_core.Mat grayImage = grabbedImage.toGrayImage();
            grabbedImage.detectFaces(grayImage, classifier);
            grabbedImage.threshold(grayImage, 64, 255, CV_THRESH_BINARY);
            grabbedImage.findContours(grayImage);
            grabbedImage.warp(rotatedImage);

            Frame rotatedFrame = converter.convert(rotatedImage);
            frame.showImage(rotatedFrame);
            recorder.record(rotatedFrame);
            grabbedImage.convert(converter, grabber);
        }
        frame.dispose();
        recorder.stop();
        grabber.stop();
    }
}
