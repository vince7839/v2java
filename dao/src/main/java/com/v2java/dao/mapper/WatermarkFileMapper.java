package com.v2java.dao.mapper;

import com.v2java.dao.po.WatermarkFilePO;
import org.springframework.stereotype.Repository;

/**
 * @author liaowenxing 2023/7/31
 **/
@Repository
public interface WatermarkFileMapper {

    void insert(WatermarkFilePO po);
}
