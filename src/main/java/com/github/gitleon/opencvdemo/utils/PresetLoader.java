package com.github.gitleon.opencvdemo.utils;

import com.github.git_leon.logging.SimpleLoggerInterface;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.tools.InfoMapper;

/**
 * @author leon
 */
public class PresetLoader {
    private SimpleLoggerInterface logger;

    public PresetLoader(SimpleLoggerInterface logger) {
        this.logger = logger;
    }

    public void load(Class<? extends InfoMapper> preset) {
        Loader.load(preset);
        logger.info("[ %s ] has preloaded", preset.getName());
    }
}
