package houyaa.server.netty;

import houyaa.http.Method;
import houyaa.http.Request;
import houyaa.server.RequestDispatcher;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;

@ChannelHandler.Sharable
public class RequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private RequestDispatcher dispatcher;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        Request request = new Request();
        HttpMethod method = msg.method();
        if (HttpMethod.GET.equals(method)) {
            request.setMethod(Method.GET);
        } else if (HttpMethod.POST.equals(method)) {
            request.setMethod(Method.POST);
        } else {
            ctx.writeAndFlush("method not support");
        }
        request.setUri(msg.uri());
        dispatcher.dispatch(request);
    }
}
