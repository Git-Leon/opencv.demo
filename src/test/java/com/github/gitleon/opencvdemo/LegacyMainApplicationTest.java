package com.github.gitleon.opencvdemo;


import com.github.gitleon.opencvdemo.utils.LoggerSingleton;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.indexer.DoubleIndexer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacv.*;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.bytedeco.javacpp.opencv_calib3d.Rodrigues;
import static org.bytedeco.javacpp.opencv_core.CV_64FC1;
import static org.bytedeco.javacpp.opencv_core.CV_8UC1;
import static org.bytedeco.javacpp.opencv_imgproc.*;
public class LegacyMainApplicationTest {

    @Test
    @Deprecated
    public void test() throws Exception {
        String classifierName = null;
        LoggerSingleton.LEGACY.info("Training classifier is preloading");
        String address = "https://raw.github.com/opencv/opencv/master/data/haarcascades/haarcascade_frontalface_alt.xml";
        URL urlOfTrainedClassifier = new URL(address);
        File frontFaceTrainedClassifier = Loader.cacheResource(urlOfTrainedClassifier);
        LoggerSingleton.LEGACY.info("Training classifier has been preloaded");
        classifierName = frontFaceTrainedClassifier.getAbsolutePath();


        // Preload the opencv_objdetect module to work around a known bug.
        LoggerSingleton.LEGACY.info("opencv_objectdetect.class is preloading");
        Loader.load(opencv_objdetect.class);
        LoggerSingleton.LEGACY.info("opencv_objectdetect.class has been preloaded");

        // We can "cast" Pointer objects by instantiating a new object of the desired class.
        opencv_objdetect.CascadeClassifier classifier = new opencv_objdetect.CascadeClassifier(classifierName);
        LoggerSingleton.LEGACY.info("Classifier created with name [ %s ]", classifierName);

        // The available FrameGrabber classes include OpenCVFrameGrabber (opencv_videoio),
        // DC1394FrameGrabber, FlyCaptureFrameGrabber, OpenKinectFrameGrabber, OpenKinect2FrameGrabber,
        // RealSenseFrameGrabber, PS3EyeFrameGrabber, VideoInputFrameGrabber, and FFmpegFrameGrabber.
        FrameGrabber grabber = FrameGrabber.createDefault(0);
        LoggerSingleton.LEGACY.info("FrameGrabber created");
        grabber.start();
        LoggerSingleton.LEGACY.info("FrameGrabber started");

        // CanvasFrame, FrameGrabber, and FrameRecorder use Frame objects to communicate image data.
        // We need a FrameConverter to interface with other APIs (Android, Java 2D, JavaFX, Tesseract, OpenCV, etc).
        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
        LoggerSingleton.LEGACY.info("FrameConverter created");

        // FAQ about IplImage and Mat objects from OpenCV:
        // - For custom raw processing of data, createBuffer() returns an NIO direct
        //   buffer wrapped around the memory pointed by imageData, and under Android we can
        //   also use that Buffer with Bitmap.copyPixelsFromBuffer() and copyPixelsToBuffer().
        // - To get a BufferedImage from an IplImage, or vice versa, we can chain calls to
        //   Java2DFrameConverter and OpenCVFrameConverter, one after the other.
        // - Java2DFrameConverter also has static copy() methods that we can use to transfer
        //   data more directly between BufferedImage and IplImage or Mat via Frame objects.
        opencv_core.Mat grabbedImage = converter.convert(grabber.grab());
        int height = grabbedImage.rows();
        int width = grabbedImage.cols();

        // Objects allocated with `new`, clone(), or a create*() factory method are automatically released
        // by the garbage collector, but may still be explicitly released by calling deallocate().
        // You shall NOT call cvReleaseImage(), cvReleaseMemStorage(), etc. on objects allocated this way.
        opencv_core.Mat grayImage = new opencv_core.Mat(height, width, CV_8UC1);
        opencv_core.Mat rotatedImage = grabbedImage.clone();

        // The OpenCVFrameRecorder class simply uses the VideoWriter of opencv_videoio,
        // but FFmpegFrameRecorder also exists as a more versatile alternative.
        FrameRecorder recorder = FrameRecorder.createDefault("output.avi", grabbedImage.rows(), grabbedImage.cols());

        LoggerSingleton.LEGACY.info("FrameRecorder created");
        recorder.start();
        LoggerSingleton.LEGACY.info("FrameRecorder started");

        // CanvasFrame is a JFrame containing a Canvas component, which is hardware accelerated.
        // It can also switch into full-screen mode when called with a screenNumber.
        // We should also specify the relative monitor/camera response for proper gamma correction.
        CanvasFrame frame = new CanvasFrame("Some Title", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        LoggerSingleton.LEGACY.info("CanvasFrame created");

        // Let's create some random 3D rotation...
        LoggerSingleton.LEGACY.info("Creating 3D rotation");
        opencv_core.Mat randomR = new opencv_core.Mat(3, 3, CV_64FC1),
                randomAxis = new opencv_core.Mat(3, 1, CV_64FC1);
        // We can easily and efficiently access the elements of matrices and images
        // through an Indexer object with the set of get() and put() methods.
        LoggerSingleton.LEGACY.info("Creating indexers");
        DoubleIndexer Ridx = randomR.createIndexer();
        DoubleIndexer axisIdx = randomAxis.createIndexer();
        axisIdx.put(0, (Math.random() - 0.5) / 4,
                (Math.random() - 0.5) / 4,
                (Math.random() - 0.5) / 4);
        LoggerSingleton.LEGACY.info("Calibrating camera");
        Rodrigues(randomAxis, randomR);
        double f = (width + height) / 2.0;
        Ridx.put(0, 2, Ridx.get(0, 2) * f);
        Ridx.put(1, 2, Ridx.get(1, 2) * f);
        Ridx.put(2, 0, Ridx.get(2, 0) / f);
        Ridx.put(2, 1, Ridx.get(2, 1) / f);
        LoggerSingleton.LEGACY.info("ridx = " + Ridx.toString());

        // We can allocate native arrays using constructors taking an integer as argument.
        opencv_core.Point hatPoints = new opencv_core.Point(3);

        while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab())) != null) {
            // Let's try to detect some faces! but we need a grayscale image...
            cvtColor(grabbedImage, grayImage, CV_BGR2GRAY);
            opencv_core.RectVector faces = new opencv_core.RectVector();
            classifier.detectMultiScale(grayImage, faces);
            long total = faces.size();
            for (long i = 0; i < total; i++) {
                opencv_core.Rect r = faces.get(i);
                int x = r.x(), y = r.y(), w = r.width(), h = r.height();
                rectangle(grabbedImage, new opencv_core.Point(x, y), new opencv_core.Point(x + w, y + h), opencv_core.Scalar.RED, 1, CV_AA, 0);

                // To access or pass as argument the elements of a native array, call position() before.
                hatPoints.position(0).x(x - w / 10).y(y - h / 10);
                hatPoints.position(1).x(x + w * 11 / 10).y(y - h / 10);
                hatPoints.position(2).x(x + w / 2).y(y - h / 2);
                fillConvexPoly(grabbedImage, hatPoints.position(0), 3, opencv_core.Scalar.GREEN, CV_AA, 0);
            }

            // Let's find some contours! but first some thresholding...
            threshold(grayImage, grayImage, 64, 255, CV_THRESH_BINARY);

            // To check if an output argument is null we may call either isNull() or equals(null).
            opencv_core.MatVector contours = new opencv_core.MatVector();
            findContours(grayImage, contours, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
            long n = contours.size();
            for (long i = 0; i < n; i++) {
                opencv_core.Mat contour = contours.get(i);
                opencv_core.Mat points = new opencv_core.Mat();
                approxPolyDP(contour, points, arcLength(contour, true) * 0.02, true);
                drawContours(grabbedImage, new opencv_core.MatVector(points), -1, opencv_core.Scalar.BLUE);
            }

            warpPerspective(grabbedImage, rotatedImage, randomR, rotatedImage.size());

            Frame rotatedFrame = converter.convert(rotatedImage);
            frame.showImage(rotatedFrame);
            recorder.record(rotatedFrame);
        }
        frame.dispose();
        recorder.stop();
        grabber.stop();
    }


    @Test
    public void original() throws Exception {
        String classifierName = null;
        String address = "https://raw.github.com/opencv/opencv/master/data/haarcascades/haarcascade_frontalface_alt.xml";
        URL urlOfTrainedClassifier = new URL(address);
        File frontFaceTrainedClassifier = Loader.cacheResource(urlOfTrainedClassifier);
        classifierName = frontFaceTrainedClassifier.getAbsolutePath();
        Loader.load(opencv_objdetect.class);
        opencv_objdetect.CascadeClassifier classifier = new opencv_objdetect.CascadeClassifier(classifierName);
        FrameGrabber grabber = FrameGrabber.createDefault(0);
        grabber.start();
        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
        opencv_core.Mat grabbedImage = converter.convert(grabber.grab());
        int height = grabbedImage.rows();
        int width = grabbedImage.cols();
        opencv_core.Mat grayImage = new opencv_core.Mat(height, width, CV_8UC1);
        opencv_core.Mat rotatedImage = grabbedImage.clone();
        FrameRecorder recorder = FrameRecorder.createDefault("output.avi", grabbedImage.rows(), grabbedImage.cols());
        recorder.start();
        CanvasFrame frame = new CanvasFrame("Some Title", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        opencv_core.Mat randomR = new opencv_core.Mat(3, 3, CV_64FC1),
                randomAxis = new opencv_core.Mat(3, 1, CV_64FC1);
        DoubleIndexer Ridx = randomR.createIndexer();
        DoubleIndexer axisIdx = randomAxis.createIndexer();
        axisIdx.put(0, (Math.random() - 0.5) / 4,
                (Math.random() - 0.5) / 4,
                (Math.random() - 0.5) / 4);
        Rodrigues(randomAxis, randomR);
        double f = (width + height) / 2.0;
        Ridx.put(0, 2, Ridx.get(0, 2) * f);
        Ridx.put(1, 2, Ridx.get(1, 2) * f);
        Ridx.put(2, 0, Ridx.get(2, 0) / f);
        Ridx.put(2, 1, Ridx.get(2, 1) / f);
        opencv_core.Point hatPoints = new opencv_core.Point(3);
        while (frame.isVisible() && (grabbedImage = converter.convert(grabber.grab())) != null) {
            cvtColor(grabbedImage, grayImage, CV_BGR2GRAY);
            opencv_core.RectVector faces = new opencv_core.RectVector();
            classifier.detectMultiScale(grayImage, faces);
            long total = faces.size();
            for (long i = 0; i < total; i++) {
                opencv_core.Rect r = faces.get(i);
                int x = r.x(), y = r.y(), w = r.width(), h = r.height();
                rectangle(grabbedImage, new opencv_core.Point(x, y), new opencv_core.Point(x + w, y + h), opencv_core.Scalar.RED, 1, CV_AA, 0);
                hatPoints.position(0).x(x - w / 10).y(y - h / 10);
                hatPoints.position(1).x(x + w * 11 / 10).y(y - h / 10);
                hatPoints.position(2).x(x + w / 2).y(y - h / 2);
                fillConvexPoly(grabbedImage, hatPoints.position(0), 3, opencv_core.Scalar.GREEN, CV_AA, 0);
            }
            threshold(grayImage, grayImage, 64, 255, CV_THRESH_BINARY);
            opencv_core.MatVector contours = new opencv_core.MatVector();
            findContours(grayImage, contours, CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE);
            long n = contours.size();
            for (long i = 0; i < n; i++) {
                opencv_core.Mat contour = contours.get(i);
                opencv_core.Mat points = new opencv_core.Mat();
                approxPolyDP(contour, points, arcLength(contour, true) * 0.02, true);
                drawContours(grabbedImage, new opencv_core.MatVector(points), -1, opencv_core.Scalar.BLUE);
            }

            warpPerspective(grabbedImage, rotatedImage, randomR, rotatedImage.size());

            Frame rotatedFrame = converter.convert(rotatedImage);
            frame.showImage(rotatedFrame);
            recorder.record(rotatedFrame);
        }
        frame.dispose();
        recorder.stop();
        grabber.stop();
    }
}
