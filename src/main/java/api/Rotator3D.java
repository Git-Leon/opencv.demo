package api;


import org.bytedeco.javacpp.indexer.DoubleIndexer;
import org.bytedeco.javacpp.opencv_calib3d;
import org.bytedeco.javacpp.opencv_core;

import static org.bytedeco.javacpp.opencv_core.CV_64FC1;

public class Rotator3D {
    private opencv_core.Mat randomR;
    private final opencv_core.Mat randomAxis;

    public Rotator3D() {
        // Let's create some random 3D rotation...
        LoggerSingleton.GLOBAL.info("Creating 3D rotation");
        this.randomR = new opencv_core.Mat(3, 3, CV_64FC1);
        this.randomAxis = new opencv_core.Mat(3, 1, CV_64FC1);
    }

    public void rotate(opencv_core.Mat mat) {
        // We can easily and efficiently access the elements of matrices and images
        // through an Indexer object with the set of get() and put() methods.
        LoggerSingleton.GLOBAL.info("Creating indexers")
        DoubleIndexer ridx = randomR.createIndexer();
        DoubleIndexer axisIdx = randomAxis.createIndexer();
        axisIdx.put(0, 0.0);
        LoggerSingleton.GLOBAL.info("Calibrating camera");
        opencv_calib3d.Rodrigues(randomAxis, randomR);
        double f = (mat.cols() + mat.rows()) / 2.0;
        ridx.put(0, 2, ridx.get(0, 2) * f);
        ridx.put(1, 2, ridx.get(1, 2) * f);
        ridx.put(2, 0, ridx.get(2, 0) / f);
        ridx.put(2, 1, ridx.get(2, 1) / f);
        LoggerSingleton.GLOBAL.info("ridx = " + ridx);
    }

    public opencv_core.Mat getRandomR() {
        return randomR;
    }

    public opencv_core.Mat getRandomAxis() {
        return randomAxis;
    }
}

