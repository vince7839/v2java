package com.v2java.quickstart.canal;

import java.util.Date;
import lombok.Data;

/**
 * @author liaowenxing 2023/7/11
 **/
@Data
public class Row {
    private String id;
    private String key;
    private String val;
    private String description;
    private String create_time;
    private String update_time;
}
