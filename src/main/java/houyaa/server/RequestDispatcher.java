package houyaa.server;

import houyaa.http.Method;
import houyaa.http.Request;
import houyaa.route.Route;
import houyaa.route.RouterManager;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RequestDispatcher {

    private ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10,
            1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024));

    public void dispatch(Request request) {
        executor.execute(()->{
            doDispatch(request);
        });
    }

    private void doDispatch(Request request) {
        Route route = RouterManager.get(request.getUri(), request.getMethod());
        route.getHandler().handel(request);
    }
}
