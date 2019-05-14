package com.github.gitleon.opencvdemo.utils;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;

public class FrameRecorderWrapper {
    private final FrameRecorder recorder;

    public FrameRecorderWrapper(FrameRecorder recorder) {
        this.recorder = recorder;
    }

    public void start() {
        try {
            recorder.start();
            LoggerSingleton.GLOBAL.info("FrameRecorder started");
        } catch (FrameRecorder.Exception e) {
            throw new Error(e);
        }
    }


    public void stop() {
        try {
            recorder.stop();
        } catch (Exception e) {
            throw new Error(e);
        }
    }


    public void record(Frame frame) {
        try {
            recorder.record(frame);
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
