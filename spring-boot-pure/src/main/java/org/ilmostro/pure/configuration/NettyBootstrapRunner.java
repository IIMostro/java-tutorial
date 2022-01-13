package org.ilmostro.pure.configuration;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.ilmostro.pure.socket.AuthenticateChannelHandler;
import org.ilmostro.pure.socket.ChannelHandlerHeaderCache;
import org.ilmostro.pure.socket.CustomChannelInboundHandlerAdapter;
import org.ilmostro.pure.socket.UrlResolverChannelChandler;
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
public class NettyBootstrapRunner implements ApplicationRunner, ApplicationListener<ContextClosedEvent>, ApplicationContextAware, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(NettyBootstrapRunner.class);
    private static NettyConfigurationProperties properties;
    private static CustomChannelInboundHandlerAdapter channelHandler;

    private ApplicationContext applicationContext;
    private Channel serverChannel;

    @Override
    public void afterPropertiesSet() {
        properties = applicationContext.getBean(NettyConfigurationProperties.class);
        channelHandler = applicationContext.getBean(CustomChannelInboundHandlerAdapter.class);
    }

    private static class CustomChannelInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) {
            ChannelPipeline pipeline = ch.pipeline();
            // 服务端，对请求解码。属于ChannelIntboundHandler，按照顺序执行
            pipeline.addLast(new HttpServerCodec());
            pipeline.addLast(new ChunkedWriteHandler());
            //即通过它可以把 HttpMessage 和 HttpContent 聚合成一个 FullHttpRequest,并定义可以接受的数据大小，在文件上传时，可以支持params+multipart
            pipeline.addLast(new HttpObjectAggregator(65536));
            //webSocket 数据压缩扩展
            pipeline.addLast(new WebSocketServerCompressionHandler());
            pipeline.addLast(UrlResolverChannelChandler.getInstance());
            pipeline.addLast(AuthenticateChannelHandler.getInstance());
            pipeline.addLast(channelHandler);
        }
    }

    @Bean
    public Executor nettyExecutor() {
        return new ThreadPoolExecutor(10, 20,
                60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000)
        );
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1, nettyExecutor());
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
        ChannelHandlerHeaderCache.cleanAll();
        logger.info("websocket 服务停止");
    }
}
