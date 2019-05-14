package api.wrapper;

import api.LoggerSingleton;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.tools.InfoMapper;

public class OpenCVPresetLoader {
    public static void load(Class<? extends InfoMapper> preset) {
        Loader.load(preset);
        LoggerSingleton.GLOBAL.info("[ %s ] has preloaded", preset.getName());
    }
}
