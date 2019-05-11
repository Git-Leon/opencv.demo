package api;

import org.bytedeco.javacv.FrameGrabber;

import java.io.File;

// The available FrameGrabber classes include OpenCVFrameGrabber (opencv_videoio),
// DC1394FrameGrabber, FlyCaptureFrameGrabber, OpenKinectFrameGrabber, OpenKinect2FrameGrabber,
// RealSenseFrameGrabber, PS3EyeFrameGrabber, VideoInputFrameGrabber, and FFmpegFrameGrabber.
public class FrameGrabberFactory {
    public static FrameGrabber create(Class<? extends FrameGrabber> c, Class p, Object o) throws FrameGrabber.Exception {
        return FrameGrabber.create(c, p, o);
    }

    public static FrameGrabber createDefault(File deviceFile) throws FrameGrabber.Exception {
        return FrameGrabber.createDefault(deviceFile);
    }

    public static FrameGrabber createDefault(String devicePath) throws FrameGrabber.Exception {
        return FrameGrabber.createDefault(devicePath);
    }

    public static FrameGrabber createDefault(int deviceNumber) throws FrameGrabber.Exception {
        return FrameGrabber.createDefault(deviceNumber);
    }

    public static FrameGrabber create(String className, File deviceFile) throws FrameGrabber.Exception {
        return FrameGrabber.create(className, deviceFile);
    }

    public static FrameGrabber create(String className, String devicePath) throws FrameGrabber.Exception {
        return FrameGrabber.create(className, devicePath);
    }

    public static FrameGrabber create(String className, int deviceNumber) throws FrameGrabber.Exception {
        return FrameGrabber.create(className, deviceNumber);
    }
}
