package api;


import org.bytedeco.javacpp.indexer.DoubleIndexer;
import org.bytedeco.javacpp.opencv_calib3d;
import org.bytedeco.javacpp.opencv_core;

import static org.bytedeco.javacpp.opencv_core.CV_64FC1;

public class Rotator3D {
    private opencv_core.Mat transormationMatrix;
    private opencv_core.Mat axis;

    public Rotator3D() {
        LoggerSingleton.GLOBAL.info("Creating 3D rotation");
        this.transormationMatrix = new opencv_core.Mat(3, 3, CV_64FC1);
        this.axis = new opencv_core.Mat(3, 1, CV_64FC1);
    }

    public opencv_core.Mat rotate(opencv_core.Mat mat) {
        LoggerSingleton.GLOBAL.info("Creating indexers");
        double f = (mat.cols() + mat.rows()) / 2.0;
        DoubleIndexer ridx = transormationMatrix.createIndexer();
        DoubleIndexer axisIdx = axis.createIndexer();
        axisIdx.put(0, 0.0);
        ridx.put(0, 2, ridx.get(0, 2) * f);
        ridx.put(1, 2, ridx.get(1, 2) * f);
        ridx.put(2, 0, ridx.get(2, 0) / f);
        ridx.put(2, 1, ridx.get(2, 1) / f);
        opencv_calib3d.Rodrigues(axis, transormationMatrix);
        LoggerSingleton.GLOBAL.info("Camera calibrated");
        return mat;
    }

    public opencv_core.Mat getTransormationMatrix() {
        return transormationMatrix;
    }
}

