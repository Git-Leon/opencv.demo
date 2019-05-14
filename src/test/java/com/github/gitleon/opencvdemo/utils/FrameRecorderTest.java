package com.github.gitleon.opencvdemo.utils;

import gitleon.utils.exceptionalfunctionalinterface.ExceptionalSupplier;
import org.bytedeco.javacv.FrameRecorder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FrameRecorderTest {
    private FrameRecorderWrapper recorder;

    @Before
    public void setup() {
        FrameRecorder frameRecorder = ExceptionalSupplier.tryInvoke(() -> FrameRecorder.createDefault("output.avi", 1280, 720));
        this.recorder = new FrameRecorderWrapper(frameRecorder);
    }

    @Test
    public void testStartedFlagIsTriggeredCorrectly() {
        Assert.assertFalse(recorder.isStarted());
        recorder.start();
        Assert.assertTrue(recorder.isStarted());
        recorder.stop();
        Assert.assertFalse(recorder.isStarted());
    }
}
