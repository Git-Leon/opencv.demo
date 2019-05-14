package api;

import com.github.git_leon.logging.SimpleLogger;

import java.io.PrintWriter;
import java.io.StringWriter;

public enum LoggerSingleton {
    GLOBAL, LEGACY;
    private SimpleLogger logger;

    LoggerSingleton() {
        this.logger = new SimpleLogger(toString() + System.currentTimeMillis());
    }

    public void info(String message, Object... args) {
        logger.info(message, args);
    }

    public void error(String message, Object... args) {
        logger.error(message, args);
    }

    public void exception(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        logger.error(sw.toString());
    }

    @Override
    public String toString() {
        return getClass().getName() + "." + name();
    }
}
