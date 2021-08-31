package org.ilmostro.websocket.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

@ChannelHandler.Sharable
public class ResourceReleaseHandler extends ChannelInboundHandlerAdapter {

    private ResourceReleaseHandler(){

    }

    private static class LazyHolder{
        private static final ResourceReleaseHandler instance = new ResourceReleaseHandler();
    }

    public static ResourceReleaseHandler getInstance() {
        return LazyHolder.instance;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ChannelHandlerHeaderUtils.clean(ctx.channel().id().asLongText());
    }
}
