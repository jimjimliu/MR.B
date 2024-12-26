package com.kachiingapp.kachiing.domain.entity.gongyingshang.pushFeedBackRecordEntity;

import java.sql.Timestamp;

/**
 * @ClassName: Formmain_0159_push_record
 * @Auther: liujunhan
 * @Date: 2024/4/15 09:41
 * @Description:
 */
public class Formmain_0159_push_record {
    private String id;
    private String field0008;
    private Timestamp dob;
    private int status;
    private String log;
    private String json_data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getField0008() {
        return field0008;
    }

    public void setField0008(String field0008) {
        this.field0008 = field0008;
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

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getJson_data() {
        return json_data;
    }

    public void setJson_data(String json_data) {
        this.json_data = json_data;
    }
}
