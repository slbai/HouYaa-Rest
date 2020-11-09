package houyaa.server.netty;

import houyaa.server.EmbeddedServer;
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

    public NettyServer(Integer ioThreads, Integer workerThreads, String host, Integer port) {
        this.ioThreads = ioThreads;
        this.workerThreads = workerThreads;
        this.host = host;
        this.port = port;
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
                .option(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipe = socketChannel.pipeline();
                        pipe.addLast(new ReadTimeoutHandler(10));
                        pipe.addLast(new HttpServerCodec());
                        pipe.addLast(new HttpObjectAggregator(1 << 30));
                        pipe.addLast(new ChunkedWriteHandler());
                        pipe.addLast(new RequestHandler());
                    }
                });

        serverChannel = bootstrap.bind(host, port).channel();
    }


    public void stop() {
        serverChannel.close();
        parentGroup.shutdownGracefully();
        childGroup.shutdownGracefully();
    }
}
