package api.wrapper;


import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;

public class FrameConverterWrapper {
    private final OpenCVFrameConverter.ToMat converter;

    public FrameConverterWrapper(OpenCVFrameConverter.ToMat converter) {
        this.converter = converter;
    }

    public Frame convert(opencv_core.Mat mat) {
        return converter.convert(mat);
    }

    public opencv_core.Mat convert(Frame frame) {
        return converter.convertToMat(frame);
    }
}
