package api;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;

import static org.bytedeco.javacpp.opencv_core.CV_64FC1;
import static org.bytedeco.javacpp.opencv_imgproc.CV_AA;
import static org.bytedeco.javacpp.opencv_imgproc.CV_BGR2GRAY;

public class MatImage {
    private final opencv_core.Mat image;
    private final FrameGrabber frameGrabber;
    private final OpenCVFrameConverter.ToMat converter;
    private final CanvasFrame frame;

    /**
     * @param frameGrabber FrameGrabber, FrameRecorder, and CanvasFrame use Frame objects to communicate image data.
     */
    public MatImage(CanvasFrame frame, FrameGrabber frameGrabber) {
        this(frame, frameGrabber, new OpenCVFrameConverter.ToMat());
    }

    /**
     * @param frameGrabber uses Frame objects to communicate image data.
     * @param converter    we need a FrameConverter to interface with other APIs (Android, Java 2D, JavaFX, Tesseract, OpenCV, etc).
     */
    public MatImage(CanvasFrame frame, FrameGrabber frameGrabber, OpenCVFrameConverter.ToMat converter) {
        this.frame = frame;
        this.frameGrabber = frameGrabber;
        this.converter = converter;
        this.image = createGrabbedImage();
    }

    /**
     * @param frameGrabber uses Frame objects to communicate image data.
     * @param converter    we need a FrameConverter to interface with other APIs (Android, Java 2D, JavaFX, Tesseract, OpenCV, etc).
     */
    public MatImage(CanvasFrame frame, FrameGrabber frameGrabber, OpenCVFrameConverter.ToMat converter, opencv_core.Mat image) {
        this.frame = frame;
        this.frameGrabber = frameGrabber;
        this.converter = converter;
        this.image = image;
    }


    /**
     * // FAQ about IplImage and Mat objects from OpenCV:
     * // - For custom raw processing of data, createBuffer() returns an NIO direct
     * //   buffer wrapped around the memory pointed by imageData, and under Android we can
     * //   also use that Buffer with Bitmap.copyPixelsFromBuffer() and copyPixelsToBuffer().
     * // - To get a BufferedImage from an IplImage, or vice versa, we can chain calls to
     * //   Java2DFrameConverter and OpenCVFrameConverter, one after the other.
     * // - Java2DFrameConverter also has static copy() methods that we can use to transfer
     * //   data more directly between BufferedImage and IplImage or Mat via Frame objects.
     *
     * @return
     */
    private opencv_core.Mat createGrabbedImage() {
        try {
            Frame frame = frameGrabber.grab();
            return converter.convert(frame);
        } catch (FrameGrabber.Exception e) {
            throw new Error(e);
        }
    }

    public opencv_core.Mat getMatObject() {
        return image;
    }

    public int getHeight() {
        return getMatObject().rows();
    }

    public int getWidth() {
        return getMatObject().cols();
    }

    public MatImage grayScale() {
        opencv_core.Mat grayImage = new opencv_core.Mat(getHeight(), getWidth(), opencv_core.CV_8UC1);
        return new MatImage(frame, frameGrabber, converter, grayImage);
    }

    public MatImage convert() {
        try {
            return new MatImage(frame, frameGrabber, converter, converter.convert(frameGrabber.grab()));
        } catch (FrameGrabber.Exception e) {
            throw new Error(e);
        }
    }

    public Frame grabAndConvert() {
        MatImage convertedImage = this.convert();
        Frame rotatedFrame = convertedImage.converter.convert(convertedImage.getMatObject());
        frame.showImage(rotatedFrame);
        return rotatedFrame;
    }

    @Override
    /**
     * // Objects allocated with `new`, clone(), or a create*() factory method are automatically released
     * // by the garbage collector, but may still be explicitly released by calling deallocate().
     * // You shall NOT call cvReleaseImage(), cvReleaseMemStorage(), etc. on objects allocated this way.
     */
    public MatImage clone() {
        return new MatImage(frame, frameGrabber, converter, image.clone());
    }


    /**
     * // The function converts an input image from one color space to another.
     * // color space conversion code (see #ColorConversionCodes).
     *
     * @param targetScale
     * @param colorConversionCode
     */
    public void convertToColorSpace(opencv_core.Mat targetScale, int colorConversionCode) {
        opencv_imgproc.cvtColor(this.image, targetScale, colorConversionCode);
        // opencv_imgproc.cvtColor(this.image, this.grayScale().getMatObject(), CV_BGR2GRAY);
    }


    /**
     * // The function converts an input image from one color space to another.
     * // color space conversion code (see #ColorConversionCodes).
     *
     * @param targetScale
     * @param colorConversionCode
     */
    public void convertToColorSpace(MatImage targetScale, int colorConversionCode) {
        this.convertToColorSpace(targetScale.getMatObject(), colorConversionCode);
    }

    public void convertToGrayScaleColorSpace() {
        this.convertToColorSpace(this.grayScale().getMatObject(), CV_BGR2GRAY);
    }

    public void warpPerspective() {
        warpPerspective(new opencv_core.Mat(3, 3, CV_64FC1));
    }

    public void warpPerspective(opencv_core.Mat ranomdR) {
        opencv_core.Mat rotatedImage = image.clone();
        opencv_imgproc.warpPerspective(image, rotatedImage, ranomdR, rotatedImage.size());
    }

    public void rectangle(long i, opencv_core.RectVector faces) {
        // We can allocate native arrays using constructors taking an integer as argument.
        this.rectangle(i, new opencv_core.Point(3), faces);
    }

    public void rectangle(long i, opencv_core.Point hatPoints, opencv_core.RectVector faces) {
        opencv_core.Rect r = faces.get(i);
        int x = r.x(), y = r.y(), w = r.width(), h = r.height();
        opencv_imgproc.rectangle(
                image,
                new opencv_core.Point(x, y),
                new opencv_core.Point(x + w, y + h),
                opencv_core.Scalar.RED, 1, CV_AA, 0);

        // To access or pass as argument the elements of a native array, call position() before.
        hatPoints.position(0).x(x - w / 10).y(y - h / 10);
        hatPoints.position(1).x(x + w * 11 / 10).y(y - h / 10);
        hatPoints.position(2).x(x + w / 2).y(y - h / 2);
        opencv_imgproc.fillConvexPoly(image, hatPoints.position(0), 3, opencv_core.Scalar.GREEN, CV_AA, 0);
    }

}
