package api.wrapper;

import api.LoggerSingleton;
import api.Rotator3D;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;

public class MatWrapper {
    private opencv_core.Mat image;

    public MatWrapper(opencv_core.Mat image) {
        this.image = image;
        LoggerSingleton.GLOBAL.info("MatWrapper created");
    }

    public void threshold(opencv_core.Mat image, int i, int i1, int cvThreshBinary) {
        // Let's find some contours! but first some thresholding...
        opencv_imgproc.threshold(image, image, i, i1, cvThreshBinary);
    }

    public opencv_core.Mat getImage() {
        return image;
    }
}
