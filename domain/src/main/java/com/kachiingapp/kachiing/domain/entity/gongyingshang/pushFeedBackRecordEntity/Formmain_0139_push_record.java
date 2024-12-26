package com.kachiingapp.kachiing.domain.entity.gongyingshang.pushFeedBackRecordEntity;

import java.sql.Timestamp;

/**
 * @ClassName: Formmain_0139_push_record
 * @Auther: liujunhan
 * @Date: 2024/1/24 10:31
 * @Description:
 */
public class Formmain_0139_push_record {
    private String id;
    private Timestamp dob;
    private int status;
    private String field0001;
    private String field0039;
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

    public String getField0001() {
        return field0001;
    }

    public void setField0001(String field0001) {
        this.field0001 = field0001;
    }

    public String getField0039() {
        return field0039;
    }

    public void setField0039(String field0039) {
        this.field0039 = field0039;
    }
}
