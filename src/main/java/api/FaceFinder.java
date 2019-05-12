package api;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgproc.arcLength;

public class FaceFinder {
    private FrontFaceClassifier classifier;
    private MatImagee image;
    private opencv_core.Point hatPoints;


    public FaceFinder(MatImagee image) {
        this(new FrontFaceClassifier(), image, new opencv_core.Point(3));
    }

    public FaceFinder(FrontFaceClassifier classifier, MatImagee image, opencv_core.Point hatPoints) {
        this.classifier = classifier;
        this.image = image;
        this.hatPoints = hatPoints;
    }

    public void detect() {
        opencv_core.RectVector faces = new opencv_core.RectVector();
        opencv_core.Mat grayImage = image.grayScale().getMatObject();
        opencv_core.Mat grabbedImage = image.getMatObject();
        this.classifier.detectMultiScale(grayImage, faces);
        long total = faces.size();
        for (long i = 0; i < total; i++) {
            image.rectangle(i, faces);
            opencv_imgproc.fillConvexPoly(grabbedImage, hatPoints.position(0), 3, opencv_core.Scalar.GREEN, CV_AA, 0);
        }

        // Let's find some contours! but first some thresholding...
        opencv_imgproc.threshold(grayImage, grayImage, 64, 255, CV_THRESH_BINARY);

        // To check if an output argument is null we may call either isNull() or equals(null).
        opencv_core.MatVector contours = new opencv_core.MatVector();
        opencv_imgproc.findContours(grayImage, contours, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
        long n = contours.size();
        for (long i = 0; i < n; i++) {
            opencv_core.Mat contour = contours.get(i);
            opencv_core.Mat points = new opencv_core.Mat();
            opencv_imgproc.approxPolyDP(contour, points, arcLength(contour, true) * 0.02, true);
            opencv_imgproc.drawContours(grabbedImage, new opencv_core.MatVector(points), -1, opencv_core.Scalar.BLUE);
        }

        image.warpPerspective();
        image.convert();
    }


}
