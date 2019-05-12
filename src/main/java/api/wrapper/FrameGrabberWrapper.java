package api.wrapper;

import api.LoggerSingleton;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

public class FrameGrabberWrapper extends FrameGrabber {
    private final FrameGrabber grabber;

    public FrameGrabberWrapper(FrameGrabber grabber) {
        this.grabber = grabber;
    }

    public FrameGrabberWrapper() {
        this(createDefault());
    }


    @Override
    public void start() {
        try {
            grabber.start();
            LoggerSingleton.GLOBAL.info("FrameGrabber started");
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @Override
    public void stop() {
        try {
            grabber.stop();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @Override
    public void trigger() {
        try {
            grabber.trigger();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @Override
    public Frame grab() {
        try {
            return grabber.grab();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @Override
    public void release() {
        try {
            grabber.release();
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    @Override
    public double getGamma() {
        return grabber.getGamma();
    }

    private static FrameGrabber createDefault() {
        try {
            LoggerSingleton.GLOBAL.info("FrameGrabber being created...");
            FrameGrabber frameGrabber = FrameGrabber.createDefault(0);;
            LoggerSingleton.GLOBAL.info("FrameGrabber created");
            return frameGrabber;
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
