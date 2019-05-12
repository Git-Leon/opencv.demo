package api;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;

import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class FaceDetector {
    private final FrameGrabberWrapper grabber;
    private final FrameConverterWrapper converter;
    private final CanvasFrame frame;

    public FaceDetector(String title) {
        this.grabber = new FrameGrabberWrapper();
        this.converter = new FrameConverterWrapper();
        this.frame = new CanvasFrame(title, CanvasFrame.getDefaultGamma() / grabber.getGamma());
    }

    public FaceDetector(CanvasFrame frame) {
        this(new FrameGrabberWrapper(), new FrameConverterWrapper(), frame);
    }

    public FaceDetector(FrameGrabberWrapper grabber, FrameConverterWrapper converter, CanvasFrame frame) {
        this.grabber = grabber;
        this.converter = converter;
        this.frame = frame;
    }


    public void detect() {
        MatWrapper grabbedImage = new MatWrapper(converter.convert(grabber.grab()));
        opencv_core.Mat rotatedImage = grabbedImage.clone();
        FrameRecorderWrapper recorder = new FrameRecorderWrapper(grabbedImage);
        grabbedImage.rotate();
        FrontFaceClassifier classifier = new FrontFaceClassifier();
        recorder.start();
        while (frame.isVisible() && (grabbedImage = new MatWrapper(converter.convert(grabber.grab()))) != null) {
            // Let's try to detect some faces! but we need a grayscale image...
            opencv_core.Mat grayImage = grabbedImage.toGrayImage();
            opencv_core.RectVector faces = new opencv_core.RectVector();
            grabbedImage.detectFaces(grayImage, faces, classifier);
            grabbedImage.threshold(grayImage, 64, 255, CV_THRESH_BINARY);
            grabbedImage.findContours(grayImage);
            grabbedImage.warp(rotatedImage);

            Frame rotatedFrame = converter.convert(rotatedImage);
            frame.showImage(rotatedFrame);
            recorder.record(rotatedFrame);
        }
        frame.dispose();
        recorder.stop();
        grabber.stop();
    }
}
