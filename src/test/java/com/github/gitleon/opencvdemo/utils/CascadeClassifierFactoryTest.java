package com.github.gitleon.opencvdemo.utils;

import org.bytedeco.javacpp.opencv_objdetect;
import org.junit.Assert;
import org.junit.Test;

public class CascadeClassifierFactoryTest {
    @Test
    public void test() {
        CascadeClassifierFactory[] classifiers = CascadeClassifierFactory.values();
        for (int i = 0; i < classifiers.length; i++) {
            CascadeClassifierFactory classifierFactory = classifiers[i];
            opencv_objdetect.CascadeClassifier classifier = classifierFactory.createClassifier();
            Assert.assertNotNull(classifier);
        }
    }
}
