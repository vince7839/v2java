package com.v2java.dao.mapper;

import org.springframework.stereotype.Repository;

/**
 * @author liaowenxing 2023/7/31
 **/
@Repository
public interface GroupWatermarkMapper {

    Long selectMaxWatermarkForUpdate(String groupId);

    int updateWatermark(String groupId,Long newWatermark,Long oldWartermark);


}
