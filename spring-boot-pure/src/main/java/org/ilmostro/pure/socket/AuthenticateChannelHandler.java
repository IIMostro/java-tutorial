package org.ilmostro.pure.socket;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@ChannelHandler.Sharable
public class AuthenticateChannelHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticateChannelHandler.class);

    private AuthenticateChannelHandler() {

    }

    private static class LazyHolder{
        private static final AuthenticateChannelHandler handler = new AuthenticateChannelHandler();
    }

    public static AuthenticateChannelHandler getInstance() {
        return LazyHolder.handler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String token = request.headers().get("token");
            if(StringUtils.isBlank(token)){
                logger.warn("token is empty");
                ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED))
                        .addListener(ChannelFutureListener.CLOSE);
                return;
            }
            if(token.equals("123456")){
                logger.info("authenticate success!");
                ctx.pipeline().remove(this);
            }else {
                logger.warn("token is illegality");
                ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED))
                        .addListener(ChannelFutureListener.CLOSE);
            }
        }
        super.channelRead(ctx, msg);
    }
}
