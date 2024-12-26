package com.kachiingapp.kachiing.domain.entity.gongyingshang;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @ClassName: TransDetailView
 * @Auther: liujunhan
 * @Date: 2024/1/15 10:08
 * @Description:
 */
public class TransDetailView {
    private String ID;
    private String COMPANYNAME;
    private String companyID;
    private String ACCOUNTBANKNAME;
    private Timestamp BIZTIME;
    private String DESCRIPTION;
    private String OPPUNIT;
    private String OPPBANKNUMBER;
    private String OPPBANK;
    private BigDecimal AMOUNT;
    private String CLAIMSTATUS;
    private String OAID;
    private String COMPANYNUMBER;
    private String BANKID;
    private String ACCOUNTID;
    private String FNUMBER;

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getFNUMBER() {
        return FNUMBER;
    }

    public void setFNUMBER(String FNUMBER) {
        this.FNUMBER = FNUMBER;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCOMPANYNAME() {
        return COMPANYNAME;
    }

    public void setCOMPANYNAME(String COMPANYNAME) {
        this.COMPANYNAME = COMPANYNAME;
    }

    public String getACCOUNTBANKNAME() {
        return ACCOUNTBANKNAME;
    }

    public void setACCOUNTBANKNAME(String ACCOUNTBANKNAME) {
        this.ACCOUNTBANKNAME = ACCOUNTBANKNAME;
    }

    public Timestamp getBIZTIME() {
        return BIZTIME;
    }

    public void setBIZTIME(Timestamp BIZTIME) {
        this.BIZTIME = BIZTIME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getOPPUNIT() {
        return OPPUNIT;
    }

    public void setOPPUNIT(String OPPUNIT) {
        this.OPPUNIT = OPPUNIT;
    }

    public String getOPPBANKNUMBER() {
        return OPPBANKNUMBER;
    }

    public void setOPPBANKNUMBER(String OPPBANKNUMBER) {
        this.OPPBANKNUMBER = OPPBANKNUMBER;
    }

    public String getOPPBANK() {
        return OPPBANK;
    }

    public void setOPPBANK(String OPPBANK) {
        this.OPPBANK = OPPBANK;
    }

    public BigDecimal getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(BigDecimal AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getCLAIMSTATUS() {
        return CLAIMSTATUS;
    }

    public void setCLAIMSTATUS(String CLAIMSTATUS) {
        this.CLAIMSTATUS = CLAIMSTATUS;
    }

    public String getOAID() {
        return OAID;
    }

    public void setOAID(String OAID) {
        this.OAID = OAID;
    }

    public String getCOMPANYNUMBER() {
        return COMPANYNUMBER;
    }

    public void setCOMPANYNUMBER(String COMPANYNUMBER) {
        this.COMPANYNUMBER = COMPANYNUMBER;
    }

    public String getBANKID() {
        return BANKID;
    }

    public void setBANKID(String BANKID) {
        this.BANKID = BANKID;
    }

    public String getACCOUNTID() {
        return ACCOUNTID;
    }

    public void setACCOUNTID(String ACCOUNTID) {
        this.ACCOUNTID = ACCOUNTID;
    }
}
