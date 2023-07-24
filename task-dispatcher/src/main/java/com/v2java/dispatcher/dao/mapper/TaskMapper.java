package com.v2java.dispatcher.dao.mapper;

import com.v2java.dispatcher.dao.TaskPO;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author liaowenxing 2023/7/24
 **/
@Repository
public interface TaskMapper {

    void insertBatch(@Param("list") List<TaskPO> list);

    void updateStatus(Long id,String status,String message);
}
