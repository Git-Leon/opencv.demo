package com.github.gitleon.opencvdemo.facedetector;

import com.github.gitleon.opencvdemo.utils.LoggerSingleton;
import org.junit.Test;

public class FaceDetectorTest {
    @Test
    public void test() {
        //given
        FaceDetector facedetector = new FaceDetectorFactory(LoggerSingleton.GLOBAL).createDefault();
        facedetector.start();

        for (int i = 0; i < 150; i++) {
            // when
            facedetector.detect();
        }

        // then
        facedetector.stop();
    }
}
