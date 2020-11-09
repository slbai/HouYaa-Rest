package houyaa.http;

import java.util.List;
import java.util.Map;

public class Request {

    private Method method;
    private String uri;
    private Map<String, List<String>> headers;
    private Map<String, Object> params;
    private Map<String, Object> jsonBody;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public Map<String, Object> getJsonBody() {
        return jsonBody;
    }

    public void setJsonBody(Map<String, Object> jsonBody) {
        this.jsonBody = jsonBody;
    }
}
