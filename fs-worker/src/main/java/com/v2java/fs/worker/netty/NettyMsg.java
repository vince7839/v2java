package com.v2java.fs.worker.netty;

import lombok.Data;

/**
 * @author liaowenxing 2023/8/14
 **/
@Data
public class NettyMsg {

    private int msgType;

    private byte[] bytes;
}
