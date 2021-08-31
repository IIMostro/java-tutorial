package org.ilmostro.websocket.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import org.apache.commons.lang3.StringUtils;
import org.ilmostro.websocket.annotation.NettyHandlerSupport;
import org.ilmostro.websocket.configuration.NettyConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

/**
 * @author li.bowei
 */
@ChannelHandler.Sharable
@Component
public class CustomChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(CustomChannelInboundHandlerAdapter.class);

    private final NettyHandlerSupport support;
    private final NettyConfigurationProperties properties;

    public CustomChannelInboundHandlerAdapter(NettyHandlerSupport support,
                                              NettyConfigurationProperties properties) {
        this.support = support;
        this.properties = properties;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            String uri = Optional.of(fullHttpRequest)
                    .map(HttpRequest::uri)
                    .map(UriComponentsBuilder::fromUriString)
                    .map(UriComponentsBuilder::build)
                    .map(UriComponents::getPath)
                    .orElse("");
            if(StringUtils.isBlank(uri)){
                logger.warn("websocket path is empty! socket will be close!");
                ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND))
                        .addListener(ChannelFutureListener.CLOSE);
                return;
            }
            //这个地方可以通过uri的策略模式来动态添加handler
            List<String> paths = support.getCustomPipelinePath();
            // 访问的路径不是 websocket的端点地址，响应404
            if(!paths.contains(uri)){
                logger.warn("socket path:{} is illegality", uri);
                ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND))
                        .addListener(ChannelFutureListener.CLOSE);
                return;
            }
            ctx.pipeline().addLast(new WebSocketServerProtocolHandler(uri, null, true, properties.getMaxFrameSize()));
            ChannelHandler handler = support.getHandler(uri);
            ctx.pipeline().addLast(handler);
            ctx.pipeline().addLast(ResourceReleaseHandler.getInstance());
            ctx.pipeline().remove(this);
        }

        super.channelRead(ctx, msg);
    }
}
