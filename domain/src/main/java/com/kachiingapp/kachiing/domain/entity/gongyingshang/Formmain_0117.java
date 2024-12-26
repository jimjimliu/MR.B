package com.kachiingapp.kachiing.domain.entity.gongyingshang;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * @ClassName: Formmain_0117
 * @Auther: liujunhan
 * @Date: 2023/12/1 09:56
 * @Description:
 *      合同开票
 */

public class Formmain_0117 {
    private String ID;
    private String field0001;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private String field0012;
    private String field0011;
    private String field0016;
    private String field0020;
    private String field0019;
    private String field0018;
    private String field0017;
    private String field0026;
    private String field0025;
    private float field0022;
    private float field0023;
    private float field0024;
    private String field0021;
    private String field0029;
    private String field0030;
    private String field0031;
    private String field0033;
    private String field0032;

    private LocalDateTime field0035;
    private float field0034;
    private String field0060;
    private String field0050;
    private String field0051;
    private String field0013;
    private String field0049;

    public String getField0049() {
        return field0049;
    }

    public void setField0049(String field0049) {
        this.field0049 = field0049;
    }

    public String getField0013() {
        return field0013;
    }

    public void setField0013(String field0013) {
        this.field0013 = field0013;
    }

    public String getField0051() {
        return field0051;
    }

    public void setField0051(String field0051) {
        this.field0051 = field0051;
    }

    public String getField0050() {
        return field0050;
    }

    public void setField0050(String field0050) {
        this.field0050 = field0050;
    }

    public String getField0060() {
        return field0060;
    }

    public void setField0060(String field0060) {
        this.field0060 = field0060;
    }

    public String getField0033() {
        return field0033;
    }

    public void setField0033(String field0033) {
        this.field0033 = field0033;
    }

    public String getField0032() {
        return field0032;
    }

    public void setField0032(String field0032) {
        this.field0032 = field0032;
    }

    public String getField0035() {
        if (field0035 == null) {
            return "0000-00-00";
        }
        return field0035.getYear()+"-"+field0035.getMonthValue()+"-"+field0035.getDayOfMonth();
    }

    public void setField0035(LocalDateTime field0035) {
        this.field0035 = field0035;
    }

    public float getField0034() {
        return field0034;
    }

    public void setField0034(float field0034) {
        this.field0034 = field0034;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getField0001() {
        return field0001;
    }

    public void setField0001(String field0001) {
        this.field0001 = field0001;
    }

    public String getField0012() {
        return field0012;
    }

    public void setField0012(String field0012) {
        this.field0012 = field0012;
    }

    public String getField0011() {
        return field0011;
    }

    public void setField0011(String field0011) {
        this.field0011 = field0011;
    }

    public String getField0016() {
        return field0016;
    }

    public void setField0016(String field0016) {
        this.field0016 = field0016;
    }

    public String getField0020() {
        return field0020;
    }

    public void setField0020(String field0020) {
        this.field0020 = field0020;
    }

    public String getField0019() {
        return field0019;
    }

    public void setField0019(String field0019) {
        this.field0019 = field0019;
    }

    public String getField0018() {
        return field0018;
    }

    public void setField0018(String field0018) {
        this.field0018 = field0018;
    }

    public String getField0017() {
        return field0017;
    }

    public void setField0017(String field0017) {
        this.field0017 = field0017;
    }

    public String getField0026() {
        return field0026;
    }

    public void setField0026(String field0026) {
        this.field0026 = field0026;
    }

    public String getField0025() {
        return field0025;
    }

    public void setField0025(String field0025) {
        this.field0025 = field0025;
    }

    public float getField0022() {
        return field0022;
    }

    public void setField0022(float field0022) {
        this.field0022 = field0022;
    }

    public float getField0023() {
        return field0023;
    }

    public void setField0023(float field0023) {
        this.field0023 = field0023;
    }

    public float getField0024() {
        return field0024;
    }

    public void setField0024(float field0024) {
        this.field0024 = field0024;
    }

    public String getField0021() {
        return field0021;
    }

    public void setField0021(String field0021) {
        this.field0021 = field0021;
    }

    public String getField0029() {
        return field0029;
    }

    public void setField0029(String field0029) {
        this.field0029 = field0029;
    }

    public String getField0030() {
        return field0030;
    }

    public void setField0030(String field0030) {
        this.field0030 = field0030;
    }

    public String getField0031() {
        return field0031;
    }

    public void setField0031(String field0031) {
        this.field0031 = field0031;
    }
}
