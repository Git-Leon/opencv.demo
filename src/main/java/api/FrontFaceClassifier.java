package api;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static org.bytedeco.javacpp.opencv_imgproc.CV_AA;
import static org.bytedeco.javacpp.opencv_imgproc.fillConvexPoly;
import static org.bytedeco.javacpp.opencv_imgproc.rectangle;

public class FrontFaceClassifier {
    private opencv_objdetect.CascadeClassifier classifier;

    public FrontFaceClassifier() {
        String classifierName = getClassifierName();
        proloadOpenCvObjectModel();

        // We can "cast" Pointer objects by instantiating a new object of the desired class.
        this.classifier = new opencv_objdetect.CascadeClassifier(classifierName);
        LoggerSingleton.GLOBAL.info("Classifier created with name [ %s ]", classifierName);
    }

    private void proloadOpenCvObjectModel() {
        // Preload the opencv_objdetect module to work around a known bug.
        LoggerSingleton.GLOBAL.info("opencv_objectdetect.class is preloading");
        Loader.load(opencv_objdetect.class);
        LoggerSingleton.GLOBAL.info("opencv_objectdetect.class has been preloaded");
    }

    private String getClassifierName() {
        try {
            LoggerSingleton.GLOBAL.info("Training classifier is preloading");
            String address = "https://raw.github.com/opencv/opencv/master/data/haarcascades/haarcascade_frontalface_alt.xml";
            URL urlOfTrainedClassifier = new URL(address);
            File frontFaceTrainedClassifier = Loader.cacheResource(urlOfTrainedClassifier);
            LoggerSingleton.GLOBAL.info("Training classifier has been preloaded");
            return frontFaceTrainedClassifier.getAbsolutePath();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public void detectMultiScale(opencv_core.Mat grayImage, opencv_core.RectVector rectVector) {
        this.classifier.detectMultiScale(grayImage, rectVector);
    }

    public void detectFaces(
            opencv_core.Mat grabbedImage,
            opencv_core.RectVector faces,
            opencv_core.Point hatPoints) {
        long total = faces.size();
        for (long i = 0; i < total; i++) {
            opencv_core.Rect r = faces.get(i);
            int x = r.x(), y = r.y(), w = r.width(), h = r.height();
            rectangle(grabbedImage, new opencv_core.Point(x, y), new opencv_core.Point(x + w, y + h), opencv_core.Scalar.RED, 1, CV_AA, 0);

            // To access or pass as argument the elements of a native array, call position() before.
            hatPoints.position(0).x(x - w / 10).y(y - h / 10);
            hatPoints.position(1).x(x + w * 11 / 10).y(y - h / 10);
            hatPoints.position(2).x(x + w / 2).y(y - h / 2);
            fillConvexPoly(grabbedImage, hatPoints.position(0), 3, opencv_core.Scalar.GREEN, CV_AA, 0);
        }

    }

    public opencv_objdetect.CascadeClassifier getClassifier() {
        return this.classifier;
    }
}
