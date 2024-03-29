package org.ilmostro.pure.socket;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelMatchers;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.ilmostro.pure.annotation.NettyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author li.bowei
 */
@ChannelHandler.Sharable
@NettyHandler(path = "/test")
public class WebsocketMessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(WebsocketMessageHandler.class);
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws InterruptedException {
        // 业务层处理数据
        logger.info("监听到信息:{}", msg.toString());
        Map<String, String> headers = ChannelHandlerHeaderCache.getChannelHeaders.apply(ctx);
        logger.info("headers:{}", headers);
        // 响应客户端
        channels.writeAndFlush(new TextWebSocketFrame("我收到了你的消息：" + msg.text() + System.currentTimeMillis()),
                ChannelMatchers.isNot(ctx.channel()));
//        TimeUnit.SECONDS.sleep(300);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("连接断开:{}", ctx.channel().remoteAddress());
        channels.remove(ctx.channel());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("建立连接:{}", ctx.channel().remoteAddress());
        channels.add(ctx.channel());
    }
}
