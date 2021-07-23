package org.ilmostro.websocket.configuration;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.ilmostro.websocket.handler.WebsocketMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.lang.NonNull;

import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
@Configuration
@EnableConfigurationProperties(NettyConfigurationProperties.class)
public class NettyBootstrapRunner implements ApplicationRunner, ApplicationListener<ContextClosedEvent>,
        ApplicationContextAware,
        InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(NettyBootstrapRunner.class);
    private static NettyConfigurationProperties properties;
    private static WebsocketMessageHandler handler;

    private ApplicationContext applicationContext;
    private Channel serverChannel;

    @Override
    public void afterPropertiesSet() {
        properties = applicationContext.getBean(NettyConfigurationProperties.class);
        handler = applicationContext.getBean(WebsocketMessageHandler.class);
    }

    private static class CustomChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new HttpServerCodec());
            pipeline.addLast(new ChunkedWriteHandler());
            pipeline.addLast(new HttpObjectAggregator(65536));
            pipeline.addLast(new CustomChannelInboundHandlerAdapter());
            pipeline.addLast(new WebSocketServerCompressionHandler());
            pipeline.addLast(handler);
        }
    }

    private static class CustomChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof FullHttpRequest) {
                FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
                String uri = fullHttpRequest.uri();
//                if (!uri.equals(properties.getPath())) {
//                    // 访问的路径不是 websocket的端点地址，响应404
//                    ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND))
//                            .addListener(ChannelFutureListener.CLOSE);
//                    return;
//                }
//                ctx.pipeline().addLast()
                ctx.pipeline().addLast(new WebSocketServerProtocolHandler(uri, null, true, properties.getMaxFrameSize()));
            }
            super.channelRead(ctx, msg);
        }
    }

    @Bean
    public Executor nettyExecutor() {
        return new ThreadPoolExecutor(
                10,
                20,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                new BasicThreadFactory.Builder()
                        .daemon(false)
                        .namingPattern("netty-loop-executor-%d")
                        .build()
        );
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(2, nettyExecutor());
        EventLoopGroup workerGroup = new NioEventLoopGroup(8, nettyExecutor());
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup);
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.localAddress(new InetSocketAddress(properties.getIp(), properties.getPort()));
            serverBootstrap.childHandler(new CustomChannelInitializer());
            Channel channel = serverBootstrap.bind().sync().channel();
            this.serverChannel = channel;
            logger.info("websocket 服务启动，ip={},port={}", properties.getIp(), properties.getPort());
            channel.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(@NonNull ContextClosedEvent contextClosedEvent) {
        if (this.serverChannel != null) {
            this.serverChannel.close();
        }
        logger.info("websocket 服务停止");
    }
}
