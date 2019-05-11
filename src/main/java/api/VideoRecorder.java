package api;

import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class VideoRecorder {
    private final FrameRecorderWrapper recorder;
    private final FrameGrabberWrapper grabber;
    private final OpenCVFrameConverter.ToMat converter;
    private Boolean isStarted = false;

    public VideoRecorder(FrameGrabber grabber, FrameRecorder recorder, OpenCVFrameConverter.ToMat converter) {
        this.recorder = new FrameRecorderWrapper(recorder);
        this.grabber = new FrameGrabberWrapper(grabber);
        this.converter = new FrameConverterWrapper(converter);
    }

    public VideoRecorder(FrameGrabber grabber, FrameRecorder recorder) {
        this(grabber, recorder, new OpenCVFrameConverter.ToMat());
    }

    /**
     * grabber must start before recorder
     */
    public void start() {
        grabber.start();
        recorder.start();
        this.isStarted = true;
    }


    private static FrameRecorder createRecorder(String outputFileName, int width, int height) {
        try {
            return FrameRecorder.createDefault(outputFileName, width, height);
        } catch (FrameRecorder.Exception e) {
            throw new Error(e);
        }
    }
}
