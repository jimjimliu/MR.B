package com.kachiingapp.kachiing.data.dao.data;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.kachiingapp.kachiing.domain.entity.gongyingshang.*;
import com.kachiingapp.kachiing.domain.entity.gongyingshang.pushFeedBackRecordEntity.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: PushFeedBackRecordMapper
 * @Auther: liujunhan
 * @Date: 2024/1/24 10:41
 * @Description:
 */
@Mapper
@Repository
@DS("master")
public interface PushFeedBackRecordMapper {
    // 记录供应商推送记录
    void recordFormmain_0277(Formmain_0277_push_record formmain0277PushRecord);

    // 记录客户推送记录
    void recordFormmain_0037(Formmain_0037_push_record formmain0037PushRecord);

    // 记录项目立项
    void recordFormmain_0139(Formmain_0139_push_record formmain0139PushRecord);

    // 记录收款合同
    void recordFormmain_0027(Formmain_0027_push_record formmain0027PushRecord);

    // 记录合同开票
    void recordFormmain_0117(Formmain_0117_push_record formmain0117PushRecord);

    // 记录付款合同
    void recordFormmain_0451(Formmain_0451_push_record formmain0451PushRecord);

    // 记录专业拆分
    void recordFormmain_0027_zycf(Formmain_0027_zycf_push_record formmain0027ZycfPushRecord);

    // 记录收款认领
    void recordFormmain_0506(Formmain_0506_push_record formmain0506PushRecord);

    // 记录收款计划
    void recordFormmain_0240(Formmain_0240_push_record formmain0240PushRecord);

    // 产值计划
    void recordFormmain_0617(Formmain_0617_push_record formmain0617PushRecord);

    // 产值上报
    void recordFormmain_0255(Formmain_0255_push_record formmain0255PushRecord);

    // 计量应收
    void recordFormmain_0159(Formmain_0159_push_record formmain0159PushRecord);

    void recordFormmain_0533(Formmain_0533_push_record formmain0533PushRecord);
}
