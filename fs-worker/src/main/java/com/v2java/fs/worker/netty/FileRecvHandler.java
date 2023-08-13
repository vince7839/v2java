package com.v2java.fs.worker.netty;

import com.v2java.fs.worker.FileManager;
import com.v2java.util.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Objects;

public class FileRecvHandler extends SimpleChannelInboundHandler<FilePacket> {

    public static Long currentWatermark = null;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FilePacket msg) throws Exception {
        if (Objects.isNull(currentWatermark)){
            return;
        }
        FileManager fileManager = SpringUtil.getBean(FileManager.class);
        File file = fileManager.getFileByWatermark(currentWatermark);
        OutputStream out = new FileOutputStream(file);
        out.write(msg.getBytes());
        out.close();
    }
}
