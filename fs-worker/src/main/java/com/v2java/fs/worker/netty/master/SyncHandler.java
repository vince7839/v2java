package com.v2java.fs.worker.netty.master;

import com.v2java.fs.worker.FileManager;
import com.v2java.fs.worker.netty.master.WatermarkFile;
import com.v2java.fs.worker.netty.slave.SyncPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Objects;

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
        File file = fileManager.getFileByWatermark(msg.getWatermark());
        if (Objects.isNull(file)){
            log.warn("no watermark file:{}",msg);
            return;
        }
        ctx.writeAndFlush(new WatermarkFile(msg.getWatermark(),file));
    }
}
