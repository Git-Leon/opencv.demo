package com.github.gitleon.opencvdemo.utils;

import com.github.git_leon.logging.SimpleLogger;
import com.github.git_leon.logging.SimpleLoggerInterface;

import java.util.logging.Level;

/**
 * @author leon
 */
public enum LoggerSingleton implements SimpleLoggerInterface  {
    GLOBAL;
    private SimpleLoggerInterface logger;

    LoggerSingleton() {
        this.logger = new SimpleLogger(toString() + System.currentTimeMillis());
    }

    public void setLogger(SimpleLoggerInterface logger) {
        this.logger = logger;
    }

    @Override
    public void log(Level level, String s, Object... objects) {
        logger.log(level, s, objects);
    }

    @Override
    public void enabled() {
        logger.enabled();
    }

    @Override
    public void disble() {
        logger.disble();
    }

    @Override
    public boolean isEnabled() {
        return logger.isEnabled();
    }


    @Override
    public String toString() {
        return getClass().getName() + "." + name();
    }
}
