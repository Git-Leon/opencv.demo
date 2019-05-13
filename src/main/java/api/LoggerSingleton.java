package api;

import com.github.git_leon.logging.SimpleLogger;

public enum LoggerSingleton {
    GLOBAL, LEGACY;
    private SimpleLogger logger;

    LoggerSingleton() {
        this.logger = new SimpleLogger(toString() + System.currentTimeMillis());
    }

    public void info(String message, Object... args) {
        logger.info(message, args);
    }

    @Override
    public String toString() {
        return getClass().getName() + "." + name();
    }
}
