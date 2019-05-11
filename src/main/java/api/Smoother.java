package api;

import org.bytedeco.javacpp.indexer.DoubleIndexer;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacv.*;

import static org.bytedeco.javacpp.opencv_calib3d.Rodrigues;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

public class Smoother {
    public void start() throws Exception {
        FrameGrabber grabber = FrameGrabberFactory.createDefault(0);
        CanvasFrame frame = new CanvasFrame("Some Title", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        FrontFaceClassifier frontFaceClassifier = new FrontFaceClassifier();
        RectVector faces = new RectVector();
        MatImage matImage = new MatImage(frame, grabber);
        frontFaceClassifier.detectMultiScale(matImage, faces);
        int height = matImage.getHeight();
        int width = matImage.getWidth();
        grabber.start();

        // The OpenCVFrameRecorder class simply uses the VideoWriter of opencv_videoio,
        // but FFmpegFrameRecorder also exists as a more versatile alternative.

        // CanvasFrame is a JFrame containing a Canvas component, which is hardware accelerated.
        // It can also switch into full-screen mode when called with a screenNumber.
        // We should also specify the relative monitor/camera response for proper gamma correction.

        // Let's create some random 3D rotation...
        Mat randomR = new Mat(3, 3, CV_64FC1);
        Mat randomAxis = new Mat(3, 1, CV_64FC1);

        // We can easily and efficiently access the elements of matrices and images
        // through an Indexer object with the set of get() and put() methods.
        DoubleIndexer Ridx = randomR.createIndexer(),
                axisIdx = randomAxis.createIndexer();
        axisIdx.put(0, (Math.random() - 0.5) / 4,
                (Math.random() - 0.5) / 4,
                (Math.random() - 0.5) / 4);
        Rodrigues(randomAxis, randomR);
        double f = (width + height) / 2.0;
        Ridx.put(0, 2, Ridx.get(0, 2) * f);
        Ridx.put(1, 2, Ridx.get(1, 2) * f);
        Ridx.put(2, 0, Ridx.get(2, 0) / f);
        Ridx.put(2, 1, Ridx.get(2, 1) / f);
        System.out.println(Ridx);


        FaceFinder faceFinder = new FaceFinder(matImage);
        FrameRecorder recorder = FrameRecorder.createDefault("output.avi", width, height);
        recorder.start();
        while (frame.isVisible() && (matImage = matImage.convert()) != null) {
            faceFinder.detect();
            Frame rotatedFrame = matImage.grabAndConvert();
            recorder.record(rotatedFrame);
        }
        frame.dispose();
        recorder.stop();
        grabber.stop();
    }
}