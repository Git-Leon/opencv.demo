package api.facedetector;

import api.LoggerSingleton;
import api.wrapper.CascadeClassifierFactory;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class FaceDetectorFactory {
    public static FaceDetector createDefault() {
        opencv_objdetect.CascadeClassifier classifier = CascadeClassifierFactory.FRONTALFACE_ALT.createClassifier();

        FrameGrabber grabber = createDefaultFrameGrabber();
        LoggerSingleton.GLOBAL.info("FrameGrabber created");

        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
        LoggerSingleton.GLOBAL.info("Frame Converter created");


        CanvasFrame frame = new CanvasFrame("", CanvasFrame.getDefaultGamma());
        LoggerSingleton.GLOBAL.info("CanvasFrame created");

        FrameRecorder recorder = createDefaultFrameRecorder();
        LoggerSingleton.GLOBAL.info("FrameRecorder created");

        return new FaceDetectorBuilder()
                .setClassifier(classifier)
                .setGrabber(grabber)
                .setConverter(converter)
                .setFrame(frame)
                .setRecorder(recorder)
                .build();
    }


    private static FrameGrabber createDefaultFrameGrabber() {
        try {
            FrameGrabber frameGrabber = FrameGrabber.createDefault(0);
            return frameGrabber;
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private static FrameRecorder createDefaultFrameRecorder() {
        try {
            FrameRecorder recorder = FrameRecorder.createDefault("output.avi", 1280, 720);
            return recorder;
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
