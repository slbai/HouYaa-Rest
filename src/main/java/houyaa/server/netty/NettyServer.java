package houyaa.server.netty;

import houyaa.server.EmbeddedServer;
import houyaa.server.RequestDispatcher;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

public class NettyServer implements EmbeddedServer {

    private Integer ioThreads;
    private Integer workerThreads;
    private String host;
    private Integer port;

    private Channel serverChannel;
    private NioEventLoopGroup parentGroup;
    private NioEventLoopGroup childGroup;

    public NettyServer() {
        this.ioThreads = Integer.valueOf(System.getProperty("netty.ioThreads", "1"));
        this.workerThreads = Integer.valueOf(System.getProperty("netty.workerThreads", "8"));;
        this.host = System.getProperty("netty.bind.host");
        this.port = Integer.valueOf(System.getProperty("netty.bind.port", "8888"));;
    }

    public void start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        parentGroup = new NioEventLoopGroup(ioThreads);
        childGroup = new NioEventLoopGroup(workerThreads);
        bootstrap.group(parentGroup, childGroup);
        bootstrap
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipe = socketChannel.pipeline();
                        pipe.addLast(new ReadTimeoutHandler(10));
                        pipe.addLast(new HttpServerCodec());
                        pipe.addLast(new HttpObjectAggregator(1 << 30));
                        pipe.addLast(new ChunkedWriteHandler());
                        pipe.addLast(new RequestHandler(new RequestDispatcher()));
                    }
                });

        if (host == null) {
            serverChannel = bootstrap.bind(port).channel();
        } else {
            serverChannel = bootstrap.bind(host, port).channel();
        }
        System.out.println(String.format("netty server start with binding host %s and port %d", host, port));
    }


    public void stop() {
        serverChannel.close();
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }
}
