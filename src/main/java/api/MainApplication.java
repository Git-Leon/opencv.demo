package api;

import api.facedetector.FaceDetectorFactory;

public class MainApplication {
    public static void main(String[] args) {
        try {
            FaceDetectorFactory.createDefault().detect();
        } catch(Throwable t) {
            LoggerSingleton.GLOBAL.exception(t);
        }
    }
}
