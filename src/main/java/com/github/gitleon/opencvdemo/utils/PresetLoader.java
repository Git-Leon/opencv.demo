package com.github.gitleon.opencvdemo.utils;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.tools.InfoMapper;

public class PresetLoader {
    public static void load(Class<? extends InfoMapper> preset) {
        Loader.load(preset);
        LoggerSingleton.GLOBAL.info("[ %s ] has preloaded", preset.getName());
    }
}
