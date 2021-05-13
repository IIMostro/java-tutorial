package org.ilmostro.websocket.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketCloseStatus;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author li.bowei
 */
@ChannelHandler.Sharable
@Component
public class WebsocketMessageHandler extends SimpleChannelInboundHandler<WebSocketFrame> {

    private static final Logger logger = LoggerFactory.getLogger(WebsocketMessageHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            // 业务层处理数据
            logger.info("监听到信息:{}", msg);
            TimeUnit.SECONDS.sleep(5);
            // 响应客户端
            ctx.channel().writeAndFlush(new TextWebSocketFrame("我收到了你的消息：" + ((TextWebSocketFrame) msg).text() + System.currentTimeMillis()));
        } else {
            // 不接受文本以外的数据帧类型
            ctx.channel().writeAndFlush(WebSocketCloseStatus.INVALID_MESSAGE_TYPE).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("连接断开:{}", ctx.channel().remoteAddress());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("建立连接:{}", ctx.channel().remoteAddress());
    }
}
