package api.wrapper;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;

public class CanvasFrameWrapper {
    private final CanvasFrame frame;

    public CanvasFrameWrapper(CanvasFrame frame) {
        this.frame = frame;
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
