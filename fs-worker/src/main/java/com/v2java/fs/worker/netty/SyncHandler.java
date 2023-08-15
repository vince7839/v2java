package com.v2java.fs.worker.netty;

import com.v2java.fs.worker.FileManager;
import com.v2java.fs.worker.netty.slave.SyncPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liaowenxing 2023/8/15
 **/
@Slf4j
public class SyncHandler extends SimpleChannelInboundHandler<SyncPacket> {

    FileManager fileManager;

    public SyncHandler(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SyncPacket msg) {
        log.info("master process sync:{}",msg);

    }
}
