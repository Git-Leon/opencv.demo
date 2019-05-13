package api.wrapper;

import api.LoggerSingleton;
import com.github.git_leon.collectionutils.maps.IntrospectiveMap;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;

public class FrameRecorderWrapper extends FrameRecorder {
    private final FrameRecorder recorder;

    public FrameRecorderWrapper(FrameRecorder recorder) {
        this.recorder = recorder;
    }

    public FrameRecorderWrapper(opencv_core.Mat grabbedImage) {
        this(createDefault(grabbedImage));
    }

    @Override
    public void start() {
        try {
            LoggerSingleton.GLOBAL.info("FrameRecorder started");
            recorder.start();
        } catch (FrameRecorder.Exception e) {
            throw new Error(e);
        }
    }

    @Override
    public void stop() {
        try {
            recorder.stop();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @Override
    public void record(Frame frame) {
        try {
            recorder.record(frame);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @Override
    public void release(){
        try {
            recorder.release();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private static FrameRecorder createDefault(opencv_core.Mat grabbedImage) {
        try {
            FrameRecorder recorder = FrameRecorder.createDefault("output.avi", grabbedImage.rows(), grabbedImage.cols());
            LoggerSingleton.GLOBAL.info("FrameRecorder created");
            return recorder;
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
