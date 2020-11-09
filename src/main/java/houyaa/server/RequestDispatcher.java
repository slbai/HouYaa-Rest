package houyaa.server;

import houyaa.http.Method;
import houyaa.http.Request;

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
        if (request.getMethod().equals(Method.GET)) {

        } else if (request.getMethod().equals(Method.POST)) {

        } else {

        }
    }
}
