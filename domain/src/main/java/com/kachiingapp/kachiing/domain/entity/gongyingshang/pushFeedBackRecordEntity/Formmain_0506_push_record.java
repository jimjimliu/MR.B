package com.kachiingapp.kachiing.domain.entity.gongyingshang.pushFeedBackRecordEntity;

import java.sql.Timestamp;

/**
 * @ClassName: Formmain_0506_push_record
 * @Auther: liujunhan
 * @Date: 2024/1/24 10:36
 * @Description:
 */
public class Formmain_0506_push_record {
    private String id;
    private String field0023;
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

    public String getField0023() {
        return field0023;
    }

    public void setField0023(String field0023) {
        this.field0023 = field0023;
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