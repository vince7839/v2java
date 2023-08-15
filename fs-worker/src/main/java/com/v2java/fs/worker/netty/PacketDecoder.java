package com.v2java.fs.worker.netty;

import com.v2java.NettyMsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liaowenxing 2023/8/15
 **/
@Slf4j
public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out)
            throws Exception {
        byte type = byteBuf.readByte();

        log.info("decode type:{}", type);
        Object msg = null;
        if (NettyMsgType.STRING.getCode() == type) {
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            msg = new String(bytes);
        } else if (NettyMsgType.FILE.getCode() == type) {
            Long watermark = byteBuf.readLong();
            byte[] bytes = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(bytes);
            msg = new FilePacket(watermark,bytes);
        }
        if (Objects.nonNull(msg)) {
            out.add(msg);
        }
    }
}
