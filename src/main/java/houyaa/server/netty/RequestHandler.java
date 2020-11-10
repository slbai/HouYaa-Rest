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

    public RequestHandler(RequestDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest fullHttpRequest) throws Exception {
        Request request = new Request();
        HttpMethod method = fullHttpRequest.method();
        if (HttpMethod.GET.equals(method)) {
            request.setMethod(Method.GET);
        } else if (HttpMethod.POST.equals(method)) {
            request.setMethod(Method.POST);
        } else {
            ctx.writeAndFlush("method not support");
        }
        request.setUri(fullHttpRequest.uri());
        request.setCtx(ctx);
        request.setFullHttpRequest(fullHttpRequest);
        dispatcher.dispatch(request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
