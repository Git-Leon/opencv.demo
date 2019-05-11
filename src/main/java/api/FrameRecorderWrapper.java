package api;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;

public class FrameRecorderWrapper extends FrameRecorder {
    private final FrameRecorder recorder;

    public FrameRecorderWrapper(FrameRecorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void start() {
        try {
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
}
