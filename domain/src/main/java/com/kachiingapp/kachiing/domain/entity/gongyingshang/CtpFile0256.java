package com.kachiingapp.kachiing.domain.entity.gongyingshang;

import java.time.LocalDateTime;

/**
 * @ClassName: CtpFile0256
 * @Auther: liujunhan
 * @Date: 2024/4/2 10:24
 * @Description:
 */
public class CtpFile0256 {
    private String ID;
    private LocalDateTime CREATE_DATE;
    private String FILENAME;
    private String FILE_URL;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public String getFILE_URL() {
        return FILE_URL;
    }

    public void setFILE_URL(String FILE_URL) {
        this.FILE_URL = FILE_URL;
    }
}
