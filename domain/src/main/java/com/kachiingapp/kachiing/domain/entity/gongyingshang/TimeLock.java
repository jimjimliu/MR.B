package com.kachiingapp.kachiing.domain.entity.gongyingshang;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * @ClassName: TimeLock
 * @Auther: liujunhan
 * @Date: 2023/12/11 08:53
 * @Description:
 */
public class TimeLock {
    private Timestamp time_lock_supplier;
    private Timestamp time_lock_customer;
    private Timestamp time_lock_heTongKaiPiao;
    private Timestamp time_lock_xiangMuLiXiang;
    private Timestamp time_lock_collectContract;
    private Timestamp time_lock_payContract;
    private Timestamp time_lock_collectionReal;
    private Timestamp time_lock_collectionPlan;
    private Timestamp time_lock_TRANSDETAILVIEW_DLY;
    private Timestamp time_lock_productPlan;
    private Timestamp time_lock_productReal;
    private Timestamp time_lock_estimateAccountsData;
    private Timestamp time_lock_collectContract2;
    private Timestamp time_lock_payContract2;

    public Timestamp getTime_lock_payContract2() {
        return time_lock_payContract2;
    }

    public void setTime_lock_payContract2(Timestamp time_lock_payContract2) {
        this.time_lock_payContract2 = time_lock_payContract2;
    }

    public Timestamp getTime_lock_collectContract2() {
        return time_lock_collectContract2;
    }

    public void setTime_lock_collectContract2(Timestamp time_lock_collectContract2) {
        this.time_lock_collectContract2 = time_lock_collectContract2;
    }

    public Timestamp getTime_lock_estimateAccountsData() {
        return time_lock_estimateAccountsData;
    }

    public void setTime_lock_estimateAccountsData(Timestamp time_lock_estimateAccountsData) {
        this.time_lock_estimateAccountsData = time_lock_estimateAccountsData;
    }

    public Timestamp getTime_lock_productReal() {
        return time_lock_productReal;
    }

    public void setTime_lock_productReal(Timestamp time_lock_productReal) {
        this.time_lock_productReal = time_lock_productReal;
    }

    public Timestamp getTime_lock_productPlan() {
        return time_lock_productPlan;
    }

    public void setTime_lock_productPlan(Timestamp time_lock_productPlan) {
        this.time_lock_productPlan = time_lock_productPlan;
    }

    public Timestamp getTime_lock_TRANSDETAILVIEW_DLY() {
        return time_lock_TRANSDETAILVIEW_DLY;
    }

    public void setTime_lock_TRANSDETAILVIEW_DLY(Timestamp time_lock_TRANSDETAILVIEW_DLY) {
        this.time_lock_TRANSDETAILVIEW_DLY = time_lock_TRANSDETAILVIEW_DLY;
    }

    public Timestamp getTime_lock_collectionPlan() {
        return time_lock_collectionPlan;
    }

    public void setTime_lock_collectionPlan(Timestamp time_lock_collectionPlan) {
        this.time_lock_collectionPlan = time_lock_collectionPlan;
    }

    public Timestamp getTime_lock_collectionReal() {
        return time_lock_collectionReal;
    }

    public void setTime_lock_collectionReal(Timestamp time_lock_collectionReal) {
        this.time_lock_collectionReal = time_lock_collectionReal;
    }

    public Timestamp getTime_lock_collectContract() {
        return time_lock_collectContract;
    }

    public void setTime_lock_collectContract(Timestamp time_lock_collectContract) {
        this.time_lock_collectContract = time_lock_collectContract;
    }

    public Timestamp getTime_lock_payContract() {
        return time_lock_payContract;
    }

    public void setTime_lock_payContract(Timestamp time_lock_payContract) {
        this.time_lock_payContract = time_lock_payContract;
    }

    public Timestamp getTime_lock_supplier() {
        return time_lock_supplier;
    }

    public void setTime_lock_supplier(Timestamp time_lock_supplier) {
        this.time_lock_supplier = time_lock_supplier;
    }

    public Timestamp getTime_lock_customer() {
        return time_lock_customer;
    }

    public void setTime_lock_customer(Timestamp time_lock_customer) {
        this.time_lock_customer = time_lock_customer;
    }

    public Timestamp getTime_lock_heTongKaiPiao() {
        return time_lock_heTongKaiPiao;
    }

    public void setTime_lock_heTongKaiPiao(Timestamp time_lock_heTongKaiPiao) {
        this.time_lock_heTongKaiPiao = time_lock_heTongKaiPiao;
    }

    public Timestamp getTime_lock_xiangMuLiXiang() {
        return time_lock_xiangMuLiXiang;
    }

    public void setTime_lock_xiangMuLiXiang(Timestamp time_lock_xiangMuLiXiang) {
        this.time_lock_xiangMuLiXiang = time_lock_xiangMuLiXiang;
    }
}
