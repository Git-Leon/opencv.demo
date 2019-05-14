package api.facedetector;

import api.FrontFaceClassifier;
import api.wrapper.*;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.*;

public class FaceDetector {
    private final FrameGrabberWrapper grabber;
    private final FrameConverterWrapper converter;
    private final FrontFaceClassifier classifier;
    private final CanvasFrameWrapper frame;
    private final FrameRecorderWrapper recorder;

    FaceDetector(opencv_objdetect.CascadeClassifier classifier, FrameGrabber grabber, OpenCVFrameConverter.ToMat converter, FrameRecorder recorder, CanvasFrame frame) {
        this.classifier = new FrontFaceClassifier(classifier);
        this.grabber = new FrameGrabberWrapper(grabber);
        this.converter = new FrameConverterWrapper(converter);
        this.frame = new CanvasFrameWrapper(frame);
        this.recorder = new FrameRecorderWrapper(recorder);
    }

    public void detect() {
        recorder.start();
        grabber.start();
        opencv_core.Mat rotatedImage;
        opencv_core.Mat image = this.grabAndConvert();
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
