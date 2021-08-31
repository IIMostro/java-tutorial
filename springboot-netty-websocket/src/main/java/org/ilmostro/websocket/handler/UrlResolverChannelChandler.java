package org.ilmostro.websocket.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@ChannelHandler.Sharable
public class UrlResolverChannelChandler extends ChannelInboundHandlerAdapter {

    private UrlResolverChannelChandler() {

    }

    private static class LazyHolder{
        private static final UrlResolverChannelChandler instance = new UrlResolverChannelChandler();
    }

    public static UrlResolverChannelChandler getInstance() {
        return LazyHolder.instance;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof FullHttpRequest){
            FullHttpRequest request = (FullHttpRequest) msg;
            MultiValueMap<String, String> params = Optional.of(request)
                    .map(HttpRequest::uri)
                    .map(UriComponentsBuilder::fromUriString)
                    .map(UriComponentsBuilder::build)
                    .map(UriComponents::getQueryParams)
                    .orElseGet(LinkedMultiValueMap::new);
            params.forEach((v1, v2) -> request.headers().add(v1, v2));
            String uri = StringUtils.substringBefore(request.uri(), "?");
            request.setUri(uri);
            ChannelHandlerHeaderUtils.setChannelHeader(ctx.channel().id().asLongText(), params.toSingleValueMap());
            ctx.pipeline().remove(this);
        }
        super.channelRead(ctx, msg);
    }
}
