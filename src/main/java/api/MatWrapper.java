package api;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_cudawarping;
import org.bytedeco.javacpp.opencv_imgproc;

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

    public void convertColorScale(opencv_core.Mat grayImage, int colorScale) {
        opencv_imgproc.cvtColor(image, grayImage, colorScale);
    }
}
