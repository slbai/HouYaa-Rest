package houyaa.route;

import houyaa.http.Method;

import java.util.HashMap;
import java.util.Map;

public class RouterManager {

    private static Map<String, Route> routeMap = new HashMap<>();

    public static void add(Route route) {
        routeMap.put(route.getPath(), route);
    }

    public static Route get(String uri) {
        return routeMap.get(uri);
    }

    public static Route get(String uri, Method method) {
        Route route = routeMap.get(uri);
        if (route == null) {
            return null;
        }
        if (method.equals(route.getMethod())) {
            return route;
        } else {
            return null;
        }
    }
}
