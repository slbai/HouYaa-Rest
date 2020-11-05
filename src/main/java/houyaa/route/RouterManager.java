package houyaa.route;

import java.util.HashMap;
import java.util.Map;

public class RouterManager {

    private static Map<String, Route> routeMap = new HashMap<>();

    public void add(Route route) {
        routeMap.put(route.getPath(), route);
    }
}
