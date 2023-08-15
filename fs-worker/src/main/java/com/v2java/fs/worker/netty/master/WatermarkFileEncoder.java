package com.v2java.fs.worker.netty.master;

import com.v2java.NettyMsgType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * @author liaowenxing 2023/8/15
 **/
@Slf4j
public class WatermarkFileEncoder extends MessageToByteEncoder<WatermarkFile> {

    @Override
    protected void encode(ChannelHandlerContext ctx, WatermarkFile msg, ByteBuf out) {
//        CompositeByteBuf compositeByteBuf = ctx.alloc().compositeBuffer();
//        ByteBuf type = ctx.alloc().buffer().writeByte(NettyMsgType.FILE.getCode());
//        FileRegion region = new DefaultFileRegion(msg.getFile(), 0, msg.getFile().length());
//        compositeByteBuf.addComponent(type);
        out.writeByte(NettyMsgType.FILE.getCode());
        out.writeLong(msg.getWatermark());
        try (
                FileInputStream fis = new FileInputStream(msg.getFile())
        ) {
            byte[] bytes = StreamUtils.copyToByteArray(fis);
            out.writeBytes(bytes);
        } catch (Exception e) {
            log.error("read watermark file exception", e);
        }
    }


}
