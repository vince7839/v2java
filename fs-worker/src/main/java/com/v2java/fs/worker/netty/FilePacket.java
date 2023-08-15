package com.v2java.fs.worker.netty;

import java.io.File;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FilePacket {

    private Long watermark;

    private File file;
}
