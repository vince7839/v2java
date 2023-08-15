package com.v2java.fs.worker.netty.master;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liaowenxing 2023/8/7
 **/
@Slf4j
public class AuthHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("new connect!");
    }

    private boolean isPass(String json) {
        log.info("连接验证:{}", json);
        return true;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        if (isPass(msg)) {
            ctx.pipeline().remove(this);
        } else {
            ctx.writeAndFlush("AUTH_REQUIRED");
        }
    }
}
