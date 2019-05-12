package api;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;

public class LegacyMainApplication {

    public static void main(String[] args) throws Exception {
        FrameGrabberWrapper grabber = new FrameGrabberWrapper();
        CanvasFrame frame = new CanvasFrame("Some Title", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        FrameConverterWrapper converter = new FrameConverterWrapper();

        grabber.start();
        FaceDetector faceDetector = new FaceDetector(grabber, converter, frame);
        faceDetector.detect();
    }
}
