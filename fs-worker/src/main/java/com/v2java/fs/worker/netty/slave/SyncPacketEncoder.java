package com.v2java.fs.worker.netty.slave;

import com.v2java.NettyMsgType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author liaowenxing 2023/8/15
 **/
@Slf4j
public class SyncPacketEncoder extends MessageToByteEncoder<SyncPacket> {

    @Override
    protected void encode(ChannelHandlerContext ctx, SyncPacket msg, ByteBuf out) throws Exception {
        log.info("sync msg:{}",msg.getWatermark());
        out.writeByte(NettyMsgType.SYNC.getCode());
        out.writeLong(msg.getWatermark());
    }
}
