package api;

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
            return FrameGrabber.createDefault(0);
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
