package com.v2java.fs.router;

import com.v2java.dao.mapper.GroupWatermarkMapper;
import com.v2java.dao.mapper.WatermarkFileMapper;
import com.v2java.dao.po.WatermarkFilePO;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liaowenxing 2023/7/31
 **/
@Service
public class WatermarkService {

    @Autowired
    GroupWatermarkMapper watermarkMapper;
    @Autowired
    WatermarkFileMapper fileMapper;

    @Transactional(rollbackFor = Exception.class)
    public Long requestNewWatermark(String groupId){
        Long wartermark = watermarkMapper.selectMaxWatermarkForUpdate(groupId);
        if (Objects.isNull(wartermark)){

        }
        long newWatermark = wartermark+1;
        int row = watermarkMapper.updateWatermark(groupId,newWatermark,wartermark);
        if (row != 1){
            throw new RuntimeException();
        }
        WatermarkFilePO filePO = new WatermarkFilePO();
        fileMapper.insert(filePO);
        return newWatermark;
    }
}
