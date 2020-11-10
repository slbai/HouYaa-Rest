package houyaa.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.nio.charset.StandardCharsets;

public class Request {

    private Method method;
    private String uri;
    private ChannelHandlerContext ctx;
    private FullHttpRequest fullHttpRequest;

    public FullHttpRequest getFullHttpRequest() {
        return fullHttpRequest;
    }

    public void setFullHttpRequest(FullHttpRequest fullHttpRequest) {
        this.fullHttpRequest = fullHttpRequest;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

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

    private void text(String text, String contentType) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        buf.writeBytes(bytes);
        DefaultFullHttpResponse res = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.valueOf(200), buf);
        res.headers().add(HttpHeaderNames.CONTENT_TYPE, String.format("%s; charset=utf-8", contentType));
        res.headers().add(HttpHeaderNames.CONTENT_LENGTH, bytes.length);
        getCtx().writeAndFlush(res);
    }

    public void json(String text) {
        text(text, "application/json");
    }

    public void text(String text) {
        text(text, "text/plain");
    }
}
