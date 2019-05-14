package com.github.gitleon.opencvdemo.facedetector.classifier;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_objdetect;

import static org.bytedeco.javacpp.opencv_imgproc.*;

public class FrontFaceClassifier {
    private opencv_objdetect.CascadeClassifier classifier;

    public FrontFaceClassifier(opencv_objdetect.CascadeClassifier classifier) {
        this.classifier = classifier;
    }

    public opencv_core.Mat classify(opencv_core.Mat image) {
        ImageManipulator imageManipulator = new ImageManipulator();
        opencv_core.Mat grayImage = imageManipulator.gray(image);

        classifyFaces(image, grayImage);
        classifyContours(image, grayImage);

        imageManipulator.rotate(image);
        return image;
    }

    private void classifyFaces(opencv_core.Mat image, opencv_core.Mat grayImage) {
        opencv_core.Point hatPoints = new opencv_core.Point(3);
        opencv_core.RectVector faces = new opencv_core.RectVector();
        classifier.detectMultiScale(grayImage, faces);
        long total = faces.size();
        for (long i = 0; i < total; i++) {
            opencv_core.Rect r = faces.get(i);
            int x = r.x(), y = r.y(), w = r.width(), h = r.height();
            rectangle(image, new opencv_core.Point(x, y), new opencv_core.Point(x + w, y + h), opencv_core.Scalar.RED, 1, CV_AA, 0);
            hatPoints.position(0).x(x - w / 10).y(y - h / 10);
            hatPoints.position(1).x(x + w * 11 / 10).y(y - h / 10);
            hatPoints.position(2).x(x + w / 2).y(y - h / 2);
            fillConvexPoly(image, hatPoints.position(0), 3, opencv_core.Scalar.GREEN, CV_AA, 0);
        }
    }


    private void classifyContours(opencv_core.Mat image, opencv_core.Mat grayImage) {
        opencv_core.MatVector contours = new opencv_core.MatVector();
        opencv_imgproc.threshold(grayImage, grayImage, 64, 255, CV_THRESH_BINARY);
        opencv_imgproc.findContours(grayImage, contours, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
        long n = contours.size();
        for (long i = 0; i < n; i++) {
            opencv_core.Mat contour = contours.get(i);
            opencv_core.Mat points = new opencv_core.Mat();
            approxPolyDP(contour, points, arcLength(contour, true) * 0.02, true);
            opencv_imgproc.drawContours(image, new opencv_core.MatVector(points), -1, opencv_core.Scalar.BLUE);
        }
    }
}
