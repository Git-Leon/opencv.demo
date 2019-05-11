package api;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FrontFaceClassifier {
    private opencv_objdetect.CascadeClassifier classifier;

    public FrontFaceClassifier() {
        String classifierName = getClassifierName();
        // Preload the opencv_objdetect module to work around a known bug.
        Loader.load(opencv_objdetect.class);

        // We can "cast" Pointer objects by instantiating a new object of the desired class.
        this.classifier = new opencv_objdetect.CascadeClassifier(classifierName);
    }

    private String getClassifierName() {
        try {
            String address = "https://raw.github.com/opencv/opencv/master/data/haarcascades/haarcascade_frontalface_alt.xml";
            URL url = new URL(address);
            File file = Loader.cacheResource(url);
            return file.getAbsolutePath();
        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public void detectMultiScale(opencv_core.Mat grayImage, opencv_core.RectVector faces) {
        this.classifier.detectMultiScale(grayImage, faces);
    }


    public void detectMultiScale(MatImage image, opencv_core.RectVector faces) {
        this.classifier.detectMultiScale(image.grayScale().getMatObject(), faces);
    }

    public opencv_objdetect.CascadeClassifier getClassifier() {
        return this.classifier;
    }
}
