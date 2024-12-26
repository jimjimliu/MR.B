package com.kachiingapp.kachiing.data.dao.data;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.kachiingapp.kachiing.domain.entity.gongyingshang.TimeLock;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: TimeLockMapper
 * @Auther: liujunhan
 * @Date: 2023/12/11 08:51
 * @Description:
 */

@Mapper
@Repository
@DS("master")
public interface TimeLockMapper {
    TimeLock getLock();

    void updateSupplierLock(String lock);

    void updateCustomerLock(String lock);

    void updateInvoiceLock(String lock);

    void updateProjectLock(String lock);

    void updateCollectContractLock(String lock);

    void updatePayContractLock(String lock);

    void updateCollectionRealLock(String lock);

    void updateCollectionPlanLock(String lock);

    void updateTRANSDETAILVIEW_DLY(String lock);

    void updateProductPlanLock(String lock);

    void updateProductRealLock(String lock);

    void updateEstimateAccountsLock(String lock);

    void updateCollectContractLock2(String lock);
    void updatePayContractLock2(String lock);
}
