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
            this.started = true;
        }
    }

    public void stop() {
        ExceptionalRunnable.tryInvoke(recorder::stop);
        started = false;
    }

    public void record(Frame frame) {
        ExceptionalConsumer.tryInvoke(recorder::record, frame);
    }

    public boolean isStarted() {
        return started;
    }
}
