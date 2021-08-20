package org.ilmostro.websocket.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;


@ChannelHandler.Sharable
public class AuthenticateHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest fullHttpRequest = (FullHttpRequest) msg;
            String token = fullHttpRequest.headers().get("TOKEN");
            //TODO 拿到token校验, 并且根据校验的情况来处理这个pipeline
//            if(true){
//                ctx.pipeline().remove(this);
//            }else {
//                ctx.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED))
//                        .addListener(ChannelFutureListener.CLOSE);
//                return;
//            }
        }
        super.channelRead(ctx, msg);
    }
}
