package com.github.gitleon.opencvdemo.utils;

import org.bytedeco.javacpp.opencv_objdetect;

public class CascadeClassifierWrapper extends opencv_objdetect.CascadeClassifier {
    String fileName;

    public CascadeClassifierWrapper(String fileName) {
        super(fileName);
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
