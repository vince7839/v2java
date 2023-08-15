package com.v2java.fs.worker.netty.slave;

import com.v2java.fs.worker.FileManager;
import com.v2java.util.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Slf4j
public class FileRecvHandler extends SimpleChannelInboundHandler<FilePacket> {

    private FileManager fileManager;

    public FileRecvHandler(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FilePacket msg) throws Exception {
        File file = fileManager.getFileByWatermark(msg.getWatermark());
        try (
                OutputStream out = new FileOutputStream(file)
        ) {
            out.write(msg.getBytes());
            //TODO update bitSet
        } catch (Exception e) {
            log.error("write watermark file exception", e);
        }
    }
}
