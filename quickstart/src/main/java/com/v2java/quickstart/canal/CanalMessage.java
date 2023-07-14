package com.v2java.quickstart.canal;

import java.util.List;
import lombok.Data;

/**
 * @author liaowenxing 2023/7/11
 **/
@Data
public class CanalMessage {
    private List<Row> data;
    private String database;
    private long es;
    private long id;
    private boolean isDdl;
    private Row mysqlType;
    private List<Row> old;
    private List<String> pkNames;
    private String sql;
    private Row sqlType;
    private String table;
    private long ts;
    private String type;
}
