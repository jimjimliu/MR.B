package com.kachiingapp.kachiing.api.controller.pullDataFromKingDee;

import com.kachiingapp.kachiing.api.controller.util.Utils;
import com.kachiingapp.kachiing.data.dao.data.TimeLockMapper;
import com.kachiingapp.kachiing.data.dao.oracle.OracleMapper;
import com.kachiingapp.kachiing.domain.entity.gongyingshang.TimeLock;
import com.kachiingapp.kachiing.domain.entity.gongyingshang.TransDetailView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: PullDataFromEAS
 * @Auther: liujunhan
 * @Date: 2024/1/17 09:21
 * @Description:
 */
@Service("PullDataFromEAS")
public class PullDataFromEAS {
    private static final Logger logger = LoggerFactory.getLogger(PullDataFromEAS.class);

    @Autowired
    private UpdateSeeyonService updateSeeyonService;

    @Autowired
    private OracleMapper oracleMapper;

    @Autowired
    private TimeLockMapper timeLockMapper;

    @Autowired
    private Utils utils;

    public void collectTransDetails() {
        // 获取取数的日期范围
        TimeLock lock = getLock();
        Timestamp timeCondition = lock.getTime_lock_TRANSDETAILVIEW_DLY();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        oracleMapper.changeSession();

        logger.info("时间: " + df.format(new Date()));
        List<TransDetailView> transDetailsData = oracleMapper.getCollectTransDetailsData(df.format(timeCondition));

        logger.info("【拉取金蝶款项认领】【认领数量】【" + transDetailsData.size() + "】");
        int i = 0;
        for (TransDetailView item : transDetailsData) {
            logger.info("【待认领款项】【index: "+ i +"】【待认领款项ID: "+ item.getID() +"】");
            item.setCompanyID(utils.getCompanyIdFromCode(item.getFNUMBER()));

            updateSeeyonService.updateSeeyon(item);

            i++;
        }
    }

    private TimeLock getLock() {
        try {
            timeLockMapper.getLock();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return timeLockMapper.getLock();
    }
}
