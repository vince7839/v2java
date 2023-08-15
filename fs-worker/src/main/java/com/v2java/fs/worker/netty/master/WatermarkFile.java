package com.v2java.fs.worker.netty.master;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class WatermarkFile {

    private Long watermark;

    private File file;

}
