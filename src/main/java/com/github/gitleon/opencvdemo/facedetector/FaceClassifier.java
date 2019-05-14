package com.github.gitleon.opencvdemo.facedetector;

import org.bytedeco.javacpp.indexer.DoubleIndexer;
import org.bytedeco.javacpp.opencv_calib3d;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_objdetect;

import static org.bytedeco.javacpp.opencv_core.CV_64FC1;
import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 * @author leon
 */
class FaceClassifier {
    private opencv_objdetect.CascadeClassifier classifier;
    private ImageManipulator imageManipulator = new ImageManipulator();

    FaceClassifier(opencv_objdetect.CascadeClassifier classifier) {
        this.classifier = classifier;
    }

    opencv_core.Mat classify(opencv_core.Mat image) {
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


    private static class ImageManipulator {
        private opencv_core.Mat transformationMatrix;
        private opencv_core.Mat axis;

        private ImageManipulator() {
            this.transformationMatrix = new opencv_core.Mat(3, 3, CV_64FC1);
            this.axis = new opencv_core.Mat(3, 1, CV_64FC1);
        }

        private void rotate(opencv_core.Mat image) {
            double f = (image.cols() + image.rows()) / 2.0;
            DoubleIndexer ridx = transformationMatrix.createIndexer();
            DoubleIndexer axisIdx = axis.createIndexer();
            axisIdx.put(0, 0.0);
            ridx.put(0, 2, ridx.get(0, 2) * f);
            ridx.put(1, 2, ridx.get(1, 2) * f);
            ridx.put(2, 0, ridx.get(2, 0) / f);
            ridx.put(2, 1, ridx.get(2, 1) / f);
            opencv_calib3d.Rodrigues(axis, transformationMatrix);

            opencv_imgproc.warpPerspective(image, image, transformationMatrix, image.size());
        }

        private opencv_core.Mat gray(opencv_core.Mat image) {
            opencv_core.Mat grayImage = new opencv_core.Mat(image.rows(), image.rows(), CV_8UC1);
            opencv_imgproc.cvtColor(image, grayImage, opencv_imgproc.CV_BGR2GRAY);
            return grayImage;
        }
    }
}
