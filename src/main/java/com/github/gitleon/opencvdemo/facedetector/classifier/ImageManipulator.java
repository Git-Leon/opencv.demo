package com.github.gitleon.opencvdemo.facedetector.classifier;


import org.bytedeco.javacpp.indexer.DoubleIndexer;
import org.bytedeco.javacpp.opencv_calib3d;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

import static org.bytedeco.javacpp.opencv_core.CV_64FC1;
import static org.bytedeco.javacpp.opencv_core.CV_8UC1;

class ImageManipulator {
    private opencv_core.Mat transformationMatrix;
    private opencv_core.Mat axis;

    ImageManipulator() {
        this.transformationMatrix = new opencv_core.Mat(3, 3, CV_64FC1);
        this.axis = new opencv_core.Mat(3, 1, CV_64FC1);
    }

    opencv_core.Mat rotate(opencv_core.Mat image) {
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
        return image;
    }

    opencv_core.Mat gray(opencv_core.Mat image) {
        opencv_core.Mat grayImage = new opencv_core.Mat(image.rows(), image.rows(), CV_8UC1);
        opencv_imgproc.cvtColor(image, grayImage, opencv_imgproc.CV_BGR2GRAY);
        return grayImage;
    }
}

