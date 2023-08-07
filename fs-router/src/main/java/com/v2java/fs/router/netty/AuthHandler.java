package com.v2java.fs.router.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author liaowenxing 2023/8/7
 **/
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof String){
            if (isPass((String) msg)){
                ctx.pipeline().remove(this);
                return;
            }
        }
        ctx.writeAndFlush("AUTH_REQUIRED");
    }

    private boolean isPass(String json){
        return true;
    }
}
