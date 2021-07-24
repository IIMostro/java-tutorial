package org.ilmostro.websocket.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.ilmostro.websocket.annotation.NettyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author li.bowei
 */
@ChannelHandler.Sharable
@NettyHandler(path = "/test")
public class WebsocketMessageHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(WebsocketMessageHandler.class);
    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) {

        if (!(msg instanceof TextWebSocketFrame)) {
            ctx.channel().writeAndFlush(WebSocketCloseStatus.INVALID_MESSAGE_TYPE).addListener(ChannelFutureListener.CLOSE);
            return;
        }
        // 业务层处理数据
        logger.info("监听到信息:{}", msg.toString());
        // 响应客户端
        channels.writeAndFlush(new TextWebSocketFrame("我收到了你的消息：" + ((TextWebSocketFrame) msg).text() + System.currentTimeMillis()));
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("连接断开:{}", ctx.channel().remoteAddress());
        channels.remove(ctx.channel());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("建立连接:{}", ctx.channel().remoteAddress());
        channels.add(ctx.channel());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        logger.info("添加handler:{}", ctx.channel().remoteAddress());
        channels.add(ctx.channel());
    }
}
