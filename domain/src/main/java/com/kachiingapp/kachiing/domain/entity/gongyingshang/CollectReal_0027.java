package com.kachiingapp.kachiing.domain.entity.gongyingshang;

import java.math.BigDecimal;

/**
 * @ClassName: CollectReal_0027
 * @Auther: liujunhan
 * @Date: 2023/12/27 19:27
 * @Description:
 */
public class CollectReal_0027 {
    private String id;
    private String skdw;
    private String fkdw;
    private String htbh;
    private String htzy;
    private String ywkm;
    private String zxbm;
    private BigDecimal jljsje;
    private BigDecimal je;
    private String skrq;
    private String hetongID;
    private String szfb;
    private String rzbm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkdw() {
        return skdw;
    }

    public void setSkdw(String skdw) {
        this.skdw = skdw;
    }

    public String getFkdw() {
        return fkdw;
    }

    public void setFkdw(String fkdw) {
        this.fkdw = fkdw;
    }

    public String getHtbh() {
        return htbh;
    }

    public void setHtbh(String htbh) {
        this.htbh = htbh;
    }

    public String getHtzy() {
        return htzy;
    }

    public void setHtzy(String htzy) {
        this.htzy = htzy;
    }

    public String getYwkm() {
        return ywkm;
    }

    public void setYwkm(String ywkm) {
        this.ywkm = ywkm;
    }

    public String getZxbm() {
        return zxbm;
    }

    public void setZxbm(String zxbm) {
        this.zxbm = zxbm;
    }

    public BigDecimal getJljsje() {
        if (jljsje == null) {
            return new BigDecimal(0);
        }
        return jljsje;
    }

    public void setJljsje(BigDecimal jljsje) {
        this.jljsje = jljsje;
    }

    public BigDecimal getJe() {
        return je;
    }

    public void setJe(BigDecimal je) {
        this.je = je;
    }

    public String getSkrq() {
        return skrq;
    }

    public void setSkrq(String skrq) {
        this.skrq = skrq;
    }

    public String getHetongID() {
        return hetongID;
    }

    public void setHetongID(String hetongID) {
        this.hetongID = hetongID;
    }

    public String getSzfb() {
        return szfb;
    }

    public void setSzfb(String szfb) {
        this.szfb = szfb;
    }

    public String getRzbm() {
        return rzbm;
    }

    public void setRzbm(String rzbm) {
        this.rzbm = rzbm;
    }
}
