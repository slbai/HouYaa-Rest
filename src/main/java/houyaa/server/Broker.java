package houyaa.server;

import houyaa.http.Handler;
import houyaa.http.Method;
import houyaa.route.Route;
import houyaa.route.RouterManager;
import houyaa.server.netty.NettyServer;

public class Broker {

    private EmbeddedServer embeddedServer;

    public void start(){
        if (embeddedServer == null) {
            embeddedServer = new NettyServer();
        }
        embeddedServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            embeddedServer.stop();
        }));
    }

    public void addHandler(String path, Method method, Handler handler) {
        Route route = new Route(path, method, handler);
        RouterManager.add(route);
    }

    public void setEmbeddedServer(EmbeddedServer embeddedServer) {
        this.embeddedServer = embeddedServer;
    }


}
