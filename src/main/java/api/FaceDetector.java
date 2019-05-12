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
        opencv_core.Mat grayImage = new opencv_core.Mat(grabbedImage.rows(), grabbedImage.rows(), CV_8UC1);
        FrameRecorderWrapper recorder = new FrameRecorderWrapper(grabbedImage);
        grabbedImage.rotate();

        opencv_core.Point hatPoints = new opencv_core.Point(3);
        FrontFaceClassifier classifier = new FrontFaceClassifier();
        recorder.start();
        while (frame.isVisible() && (grabbedImage = new MatWrapper(converter.convert(grabber.grab()))) != null) {
            // Let's try to detect some faces! but we need a grayscale image...
            grabbedImage.convertColorScale(grayImage, CV_BGR2GRAY);
            opencv_core.RectVector faces = new opencv_core.RectVector();
            classifier.detectMultiScale(grayImage, faces);
            classifier.detectFaces(grabbedImage, faces, hatPoints);
            // Let's find some contours! but first some thresholding...
            opencv_imgproc.threshold(grayImage, grayImage, 64, 255, CV_THRESH_BINARY);

            // To check if an output argument is null we may call either isNull() or equals(null).
            opencv_core.MatVector contours = new opencv_core.MatVector();
            opencv_imgproc.findContours(grayImage, contours, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
            long n = contours.size();
            for (long i = 0; i < n; i++) {
                opencv_core.Mat contour = contours.get(i);
                opencv_core.Mat points = new opencv_core.Mat();
                approxPolyDP(contour, points, arcLength(contour, true) * 0.02, true);
                drawContours(grabbedImage, new opencv_core.MatVector(points), -1, opencv_core.Scalar.BLUE);
            }

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
