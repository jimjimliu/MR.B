package com.kachiingapp.kachiing.domain.entity.gongyingshang.pushFeedBackRecordEntity;

import java.sql.Timestamp;

/**
 * @ClassName: Formmain_0255_push_record
 * @Auther: liujunhan
 * @Date: 2024/3/25 09:20
 * @Description:
 */
public class Formmain_0255_push_record {
    private String id;
    private String field0001;
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

    public String getField0001() {
        return field0001;
    }

    public void setField0001(String field0001) {
        this.field0001 = field0001;
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
