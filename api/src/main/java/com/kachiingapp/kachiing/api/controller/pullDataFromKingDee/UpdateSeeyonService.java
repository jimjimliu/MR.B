package com.kachiingapp.kachiing.api.controller.pullDataFromKingDee;

import com.alibaba.fastjson.JSON;
import com.kachiingapp.kachiing.api.controller.util.Utils;
import com.kachiingapp.kachiing.data.dao.data.FormmainMapper;
import com.kachiingapp.kachiing.domain.entity.gongyingshang.Formmain_0717;
import com.kachiingapp.kachiing.domain.entity.gongyingshang.TransDetailView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: UpdateSeeyong
 * @Auther: liujunhan
 * @Date: 2024/1/29 11:30
 * @Description:
 */
@Service("UpdateSeeyon")
public class UpdateSeeyonService {
    private static final Logger logger = LoggerFactory.getLogger(UpdateSeeyonService.class);

    @Autowired
    private FormmainMapper formmainMapper;

    @Autowired
    private Utils utils;

    @Transactional(rollbackFor = Exception.class)
    public void updateSeeyon(TransDetailView item) {
        try {
            Formmain_0717 formmain0717 = buildFormmain_0717(item);
            int rowAffected = formmainMapper.insertIntoFormmain_0717(formmain0717);

            logger.info("【更新金蝶款项待认领状态】");
            boolean updateClaimStatus = EAS.updateEASClaimStatus(postBody(item.getID()));
            logger.info("【更新金蝶状态结果】【"+ updateClaimStatus +"】");

            if (rowAffected != 1) {
                logger.error("【认领款项记录】" + JSON.toJSONString(item));
                logger.error("【执行结果】【FAILURE】");
            } else {
                logger.error("【认领款项记录】" + JSON.toJSONString(item));
                logger.error("【执行结果】【SUCCESS】");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error("【获取待认领款项异常】【金蝶款项ID：" + item.getID() + "】");
            logger.error("【开始执行回滚】");
            //实现手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    private Map<String, String> postBody(String id) {
        Map<String, String> param = new HashMap<>();
        param.put("id", id);
        param.put("claimStatus", "2");
        return param;
    }

    private Formmain_0717 buildFormmain_0717(TransDetailView detailView) {
        Formmain_0717 formmain0717 = new Formmain_0717();
        formmain0717.setID(utils.getFormmain_0717ID());
        formmain0717.setState(1);
        formmain0717.setStart_member_id("-8598946999666717884"); // 刘梦琳
        formmain0717.setStart_date(new Timestamp(System.currentTimeMillis()));
        formmain0717.setApprove_member_id("0");
//        formmain0717.setApprove_date("");
        formmain0717.setFinishedflag(0);
        formmain0717.setRatifyflag(0);
        formmain0717.setRatify_member_id("0");
//        formmain0717.setRatify_date("");
        formmain0717.setSort(0);
        formmain0717.setModify_member_id("-8598946999666717884");
        formmain0717.setModify_date(new Timestamp(System.currentTimeMillis()));
        formmain0717.setField0001(detailView.getCompanyID());
        formmain0717.setField0002(detailView.getBIZTIME());
        formmain0717.setField0003(detailView.getAMOUNT());
        formmain0717.setField0004("1047884241100426883"); // 未认领
        formmain0717.setField0005(detailView.getOPPUNIT());
        formmain0717.setField0007(detailView.getID());
        formmain0717.setField0008(detailView.getACCOUNTBANKNAME());
        formmain0717.setField0009(detailView.getDESCRIPTION());
        formmain0717.setField0010(detailView.getOPPBANK());
        formmain0717.setField0011(detailView.getOPPBANKNUMBER());
        return formmain0717;
    }
}
