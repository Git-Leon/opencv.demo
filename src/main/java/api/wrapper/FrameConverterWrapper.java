package api.wrapper;


import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class FrameConverterWrapper extends OpenCVFrameConverter.ToMat {
    private final ToMat converter;

    public FrameConverterWrapper(OpenCVFrameConverter.ToMat converter) {
        this.converter = converter;
    }

    public FrameConverterWrapper() {
        this(new OpenCVFrameConverter.ToMat());
    }

    @Override
    public opencv_core.IplImage convertToIplImage(Frame frame) {
        return converter.convertToIplImage(frame);
    }

    @Override
    public opencv_core.Mat convertToMat(Frame frame) {
        return converter.convertToMat(frame);
    }

    @Override
    public Frame convert(opencv_core.Mat mat) {
        return converter.convert(mat);
    }

    @Override
    public Frame convert(opencv_core.IplImage img) {
        return converter.convert(img);
    }

    @Override
    public org.opencv.core.Mat convertToOrgOpenCvCoreMat(Frame frame) {
        return converter.convertToOrgOpenCvCoreMat(frame);
    }

    @Override
    public Frame convert(final org.opencv.core.Mat mat) {
        return converter.convert(mat);
    }

}
