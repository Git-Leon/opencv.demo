package com.github.gitleon.opencvdemo.utils;

import gitleon.utils.exceptionalfunctionalinterface.ExceptionalConsumer;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalRunnable;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameRecorder;

/**
 * @author leon
 * used to handle `FrameRecorder` methods which throw exceptions
 */
public class FrameRecorderWrapper {
    private final FrameRecorder recorder;
    private boolean started;

    public FrameRecorderWrapper(FrameRecorder recorder) {
        this.recorder = recorder;
    }

    public void start() {
        if (!started) {
            ExceptionalRunnable.tryInvoke(recorder::start);
            LoggerSingleton.GLOBAL.info("FrameRecorder started");
            this.started = true;
        }
    }

    public void stop() {
        ExceptionalRunnable.tryInvoke(recorder::stop);
        LoggerSingleton.GLOBAL.info("FrameRecorder stopped");
        started = false;
    }

    public void record(Frame frame) {
        ExceptionalConsumer.tryInvoke(recorder::record, frame);
    }
}
