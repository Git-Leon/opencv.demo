package api.wrapper;

import api.FrontFaceClassifier;
import api.Rotator3D;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;

public class FrameGrabAndConverter {
    private final FrameGrabberWrapper grabber;
    private final FrameConverterWrapper converter;
    private final FrontFaceClassifier classifier;

    public FrameGrabAndConverter() {
        this(new FrameGrabberWrapper(), new FrameConverterWrapper(), new FrontFaceClassifier());
    }

    public FrameGrabAndConverter(FrameGrabberWrapper grabber, FrameConverterWrapper converter, FrontFaceClassifier classifier) {
        this.grabber = grabber;
        this.converter = converter;
        this.classifier = classifier;
    }

    public void detect(CanvasFrameWrapper frame) {
        grabber.start();
        opencv_core.Mat rotatedImage;
        opencv_core.Mat image = this.grabAndConvert();
        FrameRecorderWrapper recorder = new FrameRecorderWrapper(image);
        recorder.start();
        while (frame.isVisible()) {
            opencv_core.Mat grayImage = ImageManipulator.gray(image);
            classifier.detectFaces(image, grayImage);
            classifier.findContours(image, grayImage);
            rotatedImage  = classifier.warp(image);

            Frame rotatedFrame = converter.convert(rotatedImage);
            frame.showImage(rotatedFrame);
            recorder.record(rotatedFrame);
            this.grabAndConvert();
        }
        frame.dispose();
        recorder.stop();
        grabber.stop();
    }

    private opencv_core.Mat grabAndConvert() {
        Frame frame = grabber.grab();
        opencv_core.Mat mat = converter.convert(frame);
        return mat;
    }
}
