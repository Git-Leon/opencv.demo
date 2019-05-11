package api;

public class MainApplication {
    public static void main(String[] args) {
        try {
            new Smoother().start();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
