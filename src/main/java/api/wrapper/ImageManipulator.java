package api.wrapper;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

import static org.bytedeco.javacpp.opencv_core.CV_8UC1;

public class ImageManipulator {
    public static opencv_core.Mat gray(opencv_core.Mat image) {
        opencv_core.Mat grayImage = new opencv_core.Mat(image.rows(), image.rows(), CV_8UC1);
        opencv_imgproc.cvtColor(image, grayImage, opencv_imgproc.CV_BGR2GRAY);
        return grayImage;
    }
}
