package com.github.gitleon.opencvdemo.utils;

import gitleon.utils.exceptionalfunctionalinterface.ExceptionalFunction;
import org.bytedeco.javacv.FrameGrabber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FrameGrabberTest {
    private FrameGrabberWrapper grabber;

    @Before
    public void setup() {
        this.grabber = new FrameGrabberWrapper(ExceptionalFunction.tryInvoke(FrameGrabber::createDefault, 0));
    }

    @Test
    public void testStartedFlagIsTriggeredCorrectly() {
        Assert.assertFalse(grabber.isStarted());
        grabber.start();
        Assert.assertTrue(grabber.isStarted());
        grabber.stop();
        Assert.assertFalse(grabber.isStarted());
    }
}
