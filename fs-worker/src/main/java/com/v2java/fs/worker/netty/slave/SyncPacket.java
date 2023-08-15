package com.v2java.fs.worker.netty.slave;

import java.io.File;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author liaowenxing 2023/8/15
 **/
@Data
@AllArgsConstructor
public class SyncPacket {

    private Long watermark;

    private Long start;

}
