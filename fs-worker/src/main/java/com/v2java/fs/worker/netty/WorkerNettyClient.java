package com.v2java.fs.worker.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@Component
@Slf4j
public class WorkerNettyClient {

    private Channel channel;

    @SneakyThrows
    public void connect(String host,int port){
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 添加其他处理器
                        pipeline.addLast();
                    }
                });

        // 设置连接属性
        AttributeKey<String> attrKey = AttributeKey.valueOf("authToken");
        bootstrap.attr(attrKey, authToken());

        // 发起连接
        ChannelFuture channelFuture = bootstrap.connect(host, port).sync();

        // 等待连接关闭
        channel = channelFuture.channel();
    }

    private String authToken(){
        return "123";
    }

    public void send(@NotNull Object msg){
        if (Objects.isNull(channel)||!channel.isActive()){
            log.info("fail to connect master");
            return;
        }
        channel.writeAndFlush(msg);
    }
}
