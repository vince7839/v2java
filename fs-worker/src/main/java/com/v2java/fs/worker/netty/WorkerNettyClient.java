package com.v2java.fs.worker.netty;

import com.v2java.fs.worker.netty.slave.SyncPacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Component
@Slf4j
public class WorkerNettyClient {

    private volatile Channel channel;

    private String currentMaster;

    @SneakyThrows
    public void connect(String host, int port) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 添加其他处理器
//                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
//                        pipeline.addLast(new ChannelExceptionHandler());
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0,
                                Long.BYTES));
                        pipeline.addLast(new LengthFieldPrepender(Long.BYTES));
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new SyncPacketEncoder());
                        pipeline.addLast(new PacketDecoder());
                        //pipeline.addLast(new StringDecoder());
//                        pipeline.addLast(new FileRecvHandler());
//                        pipeline.addLast(new JsonPacketHandler());
                    }
                });

        // 发起连接
        ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
        if (channelFuture.isSuccess()) {
            channel = channelFuture.channel();
        }else{
            log.error("连接失败！");
        }
    }

    private String authToken() {
        return "123";
    }

    public void send(@NotNull Object msg) {
        if (Objects.isNull(channel) || !channel.isActive()) {
            currentMaster = null;
            log.info("fail to connect master");
            //return;
        }
        channel.writeAndFlush(msg);
    }

    public void checkConnect(String ip, Integer port) {
        if (StringUtils.isEmpty(ip) || Objects.isNull(port)) {
            log.error("wrong ip or port:{},{}", ip, port);
            return;
        }
        String key = ip + ":" + port;
        log.info("current master:{},check master:{}", currentMaster, key);
        if (StringUtils.isNotEmpty(currentMaster) && currentMaster.equals(key)) {
            return;
        }
        connect(ip, port);
        currentMaster = key;
    }
}
