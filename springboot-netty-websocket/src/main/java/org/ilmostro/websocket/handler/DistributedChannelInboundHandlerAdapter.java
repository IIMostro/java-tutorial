package org.ilmostro.websocket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class DistributedChannelInboundHandlerAdapter extends ChannelInboundHandlerAdapter implements DistributedMessageHandler{

    private static final Logger logger = LoggerFactory.getLogger(DistributedChannelInboundHandlerAdapter.class);

    @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
    protected final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

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
}
