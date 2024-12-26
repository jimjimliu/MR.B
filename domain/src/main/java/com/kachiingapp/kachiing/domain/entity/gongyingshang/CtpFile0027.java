package com.kachiingapp.kachiing.domain.entity.gongyingshang;

import java.time.LocalDateTime;

/**
 * @ClassName: CtpFile0027
 * @Auther: liujunhan
 * @Date: 2024/3/29 10:59
 * @Description:
 */
public class CtpFile0027 {
    private String ID0027;
    private LocalDateTime CREATE_DATE;
    private String FILENAME;
    private String fileurl;

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public String getID0027() {
        return ID0027;
    }

    public void setID0027(String ID0027) {
        this.ID0027 = ID0027;
    }

    public LocalDateTime getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(LocalDateTime CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }

    public String getFILENAME() {
        return FILENAME;
    }

    public void setFILENAME(String FILENAME) {
        this.FILENAME = FILENAME;
    }
}
