package api;

import api.wrapper.*;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;

import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY;

public class FaceDetector {
    private final FrameGrabberWrapper grabber;
    private final FrameConverterWrapper converter;
    private final CanvasFrameWrapper frame;
    private MatWrapper grabbedImage;
    private opencv_core.Mat rotatedImage;
    private FrameRecorderWrapper recorder;
    private FrontFaceClassifier classifier;

    public FaceDetector(String title) {
        this.classifier = new FrontFaceClassifier();
        this.grabber = new FrameGrabberWrapper();
        grabber.start();
        this.converter = new FrameConverterWrapper();
        this.grabbedImage = new MatWrapper(converter.convert(grabber.grab()));
        this.rotatedImage = grabbedImage.getImage().clone();
        this.recorder = new FrameRecorderWrapper(grabbedImage.getImage());
        recorder.start();
        this.frame = new CanvasFrameWrapper(title, grabber);
        grabbedImage.rotate();
    }

    public void detect() {
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
