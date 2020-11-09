package houyaa;

import houyaa.http.Handler;
import houyaa.http.Method;
import houyaa.server.Broker;
import houyaa.server.EmbeddedServer;

public class HouYaa {

    private static Broker broker = new Broker();

    public static void get(String path, Handler handler) {
        broker.addHandler(path, Method.GET, handler);
    }

    public static void start(EmbeddedServer embeddedServer) {
        broker.setEmbeddedServer(embeddedServer);
        broker.start();
    }

    public static void start() {
        broker.start();
    }
}
