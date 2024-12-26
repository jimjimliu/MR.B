package com.kachiingapp.kachiing.domain.entity.gongyingshang.pushFeedBackRecordEntity;

import java.sql.Timestamp;

/**
 * @ClassName: Formmain_0117_push_record
 * @Auther: liujunhan
 * @Date: 2024/1/24 10:33
 * @Description:
 */
public class Formmain_0117_push_record {
    private String id;
    private String field0049;
    private Timestamp dob;
    private int status;
    private String log;
    private String json_data;

    public String getJson_data() {
        return json_data;
    }

    public void setJson_data(String json_data) {
        this.json_data = json_data;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getField0049() {
        return field0049;
    }

    public void setField0049(String field0049) {
        this.field0049 = field0049;
    }

    public Timestamp getDob() {
        return dob;
    }

    public void setDob(Timestamp dob) {
        this.dob = dob;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
