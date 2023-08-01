package com.v2java.dao.po;

import lombok.Data;

/**
 * @author liaowenxing 2023/7/31
 **/
@Data
public class WatermarkFilePO {

    private Long id;

    private String groupId;

    private Long watermark;

    private String filename;

    private String status;
}
