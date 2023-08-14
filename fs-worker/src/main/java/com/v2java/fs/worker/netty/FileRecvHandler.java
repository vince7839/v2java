package com.v2java.fs.worker.netty;

import com.v2java.fs.worker.FileManager;
import com.v2java.util.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class FileRecvHandler extends SimpleChannelInboundHandler<FilePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FilePacket msg) throws Exception {
        FileManager fileManager = SpringUtil.getBean(FileManager.class);
        File file = fileManager.getFileByWatermark(msg.getWatermark());
        OutputStream out = new FileOutputStream(file);
        out.write(msg.getBytes());
        out.close();
    }
}
