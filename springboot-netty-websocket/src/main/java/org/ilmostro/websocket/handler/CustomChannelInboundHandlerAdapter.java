package org.ilmostro.websocket.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.ilmostro.websocket.annotation.NettyHandlerSupport;
import org.ilmostro.websocket.configuration.NettyConfigurationProperties;

import java.util.List;

/**
 * @author li.bowei
 */
@ChannelHandler.Sharable
public class CustomChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {

    private final NettyHandlerSupport support;
    private final NettyConfigurationProperties properties;

    public CustomChannelInboundHandlerAdapter(NettyHandlerSupport support, NettyConfigurationProperties properties) {
        this.support = support;
        this.properties = properties;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            String uri = fullHttpRequest.uri();
            //这个地方可以通过uri的策略模式来动态添加handler
            List<String> paths = support.getCustomPipelinePath();
            // 访问的路径不是 websocket的端点地址，响应404
            if(!paths.contains(uri)){
                ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND))
                        .addListener(ChannelFutureListener.CLOSE);
                return;
            }

            for (String path : paths) {
                ctx.pipeline().addLast(new WebSocketServerProtocolHandler(uri, null, true, properties.getMaxFrameSize()));
                ChannelHandler handler = support.getHandler(path);
                ctx.pipeline().addLast(handler);
            }
        }
        super.channelRead(ctx, msg);
    }
}
