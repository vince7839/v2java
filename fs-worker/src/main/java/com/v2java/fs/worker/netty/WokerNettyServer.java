package com.v2java.fs.worker.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import java.net.InetSocketAddress;
import javax.annotation.PostConstruct;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WokerNettyServer {

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    Channel channel;

    @Getter
    int syncPort;

    @PostConstruct
    public void init(){
        syncPort = start(0);
        log.info("master sync listen on port:{}",syncPort);
    }

    @SneakyThrows
    public int start(int port) {
        // Configure the server.
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
                        ChannelPipeline p = ch.pipeline();
//                        p.addLast(new LoggingHandler(LogLevel.INFO));
//                        p.addLast(new ChannelExceptionHandler());
                        p.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,Long.BYTES,0,Long.BYTES));
                          p.addLast(new PacketDecoder());
//                        p.addLast(new AuthHandler());
                          p.addLast(new JsonPacketHandler());
                    }
                });

        // Start the server.
        ChannelFuture f = b.bind(port).sync();
        channel = f.channel();
        InetSocketAddress address = (InetSocketAddress) f.channel().localAddress();
        return address.getPort();
    }
}
