package api;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_cudawarping;
import org.bytedeco.javacpp.opencv_imgproc;

import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgproc.arcLength;

public class MatWrapper extends opencv_core.Mat {
    private final opencv_core.Mat image;
    private final Rotator3D rotator3D;

    public MatWrapper(opencv_core.Mat image) {
        this.image = image;
        this.rotator3D = new Rotator3D();
    }

    public void rotate() {
        rotator3D.rotate(image);
    }

    public void warp(opencv_core.Mat rotatedImage) {
        opencv_cudawarping.warpPerspective(image, rotatedImage, rotator3D.getRandomR(), rotatedImage.size());
    }

    public opencv_core.Mat toGrayImage() {
        opencv_core.Mat grayImage = new opencv_core.Mat(image.rows(), image.rows(), CV_8UC1);
        opencv_imgproc.cvtColor(image, grayImage, opencv_imgproc.CV_BGR2GRAY);
        return grayImage;
    }


    public void findContours(opencv_core.Mat grayImage) {
        // To check if an output argument is null we may call either isNull() or equals(null).
        opencv_core.MatVector contours = new opencv_core.MatVector();
        opencv_imgproc.findContours(grayImage, contours, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
        long n = contours.size();
        for (long i = 0; i < n; i++) {
            opencv_core.Mat contour = contours.get(i);
            opencv_core.Mat points = new opencv_core.Mat();
            approxPolyDP(contour, points, arcLength(contour, true) * 0.02, true);
            opencv_imgproc.drawContours(image, new opencv_core.MatVector(points), -1, opencv_core.Scalar.BLUE);
        }
    }

    public void detectFaces(opencv_core.Mat grayImage, opencv_core.RectVector faces, FrontFaceClassifier classifier) {
        classifier.detectMultiScale(grayImage, faces);
        classifier.detectFaces(image, faces, new opencv_core.Point(3));
    }

    public void threshold(opencv_core.Mat image, int i, int i1, int cvThreshBinary) {
        // Let's find some contours! but first some thresholding...
        opencv_imgproc.threshold(image, image, i, i1, cvThreshBinary);
    }
}
