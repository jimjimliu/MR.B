package com.kachiingapp.kachiing.domain.entity.gongyingshang;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @ClassName: Formmain_0265
 * @Auther: liujunhan
 * @Date: 2024/5/14 09:05
 * @Description:
 */
public class Formmain_0265 {
    private String id;
    private LocalDateTime modify_date;
    private String field0001;
    private String field0110;
    private String field0112;
    private BigDecimal field0157;
    private BigDecimal field0062;
    private BigDecimal field0091;
    private LocalDateTime field0060;
    private String field0007;

    public String getField0112() {
        return field0112;
    }

    public void setField0112(String field0112) {
        this.field0112 = field0112;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getModify_date() {
        return modify_date;
    }

    public void setModify_date(LocalDateTime modify_date) {
        this.modify_date = modify_date;
    }

    public String getField0001() {
        return field0001;
    }

    public void setField0001(String field0001) {
        this.field0001 = field0001;
    }

    public String getField0110() {
        return field0110;
    }

    public void setField0110(String field0110) {
        this.field0110 = field0110;
    }

    public BigDecimal getField0157() {
        return field0157;
    }

    public void setField0157(BigDecimal field0157) {
        this.field0157 = field0157;
    }

    public BigDecimal getField0062() {
        if (field0062 == null) {
            return new BigDecimal(0);
        }
        return field0062;
    }

    public void setField0062(BigDecimal field0062) {
        this.field0062 = field0062;
    }

    public BigDecimal getField0091() {
        return field0091;
    }

    public void setField0091(BigDecimal field0091) {
        this.field0091 = field0091;
    }

    public LocalDateTime getField0060() {
        return field0060;
    }

    public void setField0060(LocalDateTime field0060) {
        this.field0060 = field0060;
    }

    public String getField0007() {
        return field0007;
    }

    public void setField0007(String field0007) {
        this.field0007 = field0007;
    }
}
