package com.github.gitleon.opencvdemo.utils;

import com.github.git_leon.logging.SimpleLogger;
import com.github.git_leon.logging.SimpleLoggerInterface;

import java.util.logging.Logger;

/**
 * @author leon
 */
public enum LoggerSingleton implements SimpleLoggerInterface  {
    GLOBAL, LEGACY;
    private SimpleLogger logger;

    LoggerSingleton() {
        this.logger = new SimpleLogger(toString() + System.currentTimeMillis());
    }

    public void error(String message, Object... args) {
        logger.error(message, args);
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
    public Logger getLogger() {
        return logger.getLogger();
    }

    @Override
    public String toString() {
        return getClass().getName() + "." + name();
    }
}
