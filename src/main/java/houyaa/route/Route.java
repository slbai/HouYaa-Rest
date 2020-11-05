package houyaa.route;

import houyaa.http.Handler;
import houyaa.http.Method;

import java.util.Objects;

public class Route {

    private String path;
    private Method method;
    private Handler handler;

    public Route(String path, Method method, Handler handler) {
        this.path = path;
        this.method = method;
        this.handler = handler;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Objects.equals(path, route.path) &&
                method == route.method &&
                Objects.equals(handler, route.handler);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, method, handler);
    }
}
