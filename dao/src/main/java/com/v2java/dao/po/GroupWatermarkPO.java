package com.v2java.dao.po;

import lombok.Data;

/**
 * @author liaowenxing 2023/7/31
 **/
@Data
public class GroupWatermarkPO {
    private Long id;

    private String groupId;

    private Long maxWatermark;
}
