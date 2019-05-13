package api;

import java.io.PrintWriter;
import java.io.StringWriter;

public class MainApplication {
    public static void main(String[] args) throws Exception {
        try {
            new FaceDetector("Title").detect();
        } catch(Throwable t) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            LoggerSingleton.GLOBAL.info(sw.toString());
        }
    }
}
