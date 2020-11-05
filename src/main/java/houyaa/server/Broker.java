package houyaa.server;

import houyaa.http.Handler;
import houyaa.http.Method;
import houyaa.route.Route;
import houyaa.route.RouterManager;

public class Broker {

    private EmbeddedServer embeddedServer;

    private RouterManager routerManager;

    public void start(){
        if (embeddedServer == null) {
            embeddedServer = new NettyServer();
        }
        embeddedServer.start();
    }

    public void addHandler(String path, Method method, Handler handler) {
        Route route = new Route(path, method, handler);
        routerManager.add(route);
    }


    public EmbeddedServer getEmbeddedServer() {
        return embeddedServer;
    }

    public void setEmbeddedServer(EmbeddedServer embeddedServer) {
        this.embeddedServer = embeddedServer;
    }


}
