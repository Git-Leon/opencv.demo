package api;

import api.wrapper.*;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.Frame;

import static org.bytedeco.javacpp.opencv_imgproc.CV_THRESH_BINARY;

public class FaceDetector {
    private final CanvasFrameWrapper frame;
    private final FrameGrabAndConverter frameGrabAndConverter;

    public FaceDetector(String title) {
        this.frame = new CanvasFrameWrapper(title);
        this.frameGrabAndConverter = new FrameGrabAndConverter();
    }

    public void detect() {
        frameGrabAndConverter.detect(frame);
    }
}
