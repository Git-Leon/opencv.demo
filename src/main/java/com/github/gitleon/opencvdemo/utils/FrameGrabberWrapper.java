package com.github.gitleon.opencvdemo.utils;

import gitleon.utils.exceptionalfunctionalinterface.ExceptionalRunnable;
import gitleon.utils.exceptionalfunctionalinterface.ExceptionalSupplier;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

/**
 * @author leon
 * used to handle `FrameGrabber` methods which throw exceptions
 */
public class FrameGrabberWrapper {
    private final FrameGrabber grabber;
    private Boolean started = false;

    public FrameGrabberWrapper(FrameGrabber grabber) {
        this.grabber = grabber;
    }

    public void start() {
        if (!started) {
            ExceptionalRunnable.tryInvoke(grabber::start);
            started = true;
        }
    }

    public void stop() {
        ExceptionalRunnable.tryInvoke(grabber::stop);
        started = false;
    }

    public Frame grab() {
        return ExceptionalSupplier.tryInvoke(grabber::grab);
    }

    public boolean isStarted() {
        return started;
    }
}
