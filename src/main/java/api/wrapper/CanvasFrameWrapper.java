package api.wrapper;

import api.LoggerSingleton;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

public class CanvasFrameWrapper {
    private final CanvasFrame frame;

    public CanvasFrameWrapper(String title) {
        this.frame = new CanvasFrame(title, CanvasFrame.getDefaultGamma());
        LoggerSingleton.GLOBAL.info("CanvasFrame created");
    }

    public boolean isVisible() {
        return frame.isVisible();
    }

    public void showImage(Frame rotatedFrame) {
        frame.showImage(rotatedFrame);
    }

    public void dispose() {
        frame.dispose();
    }
}
