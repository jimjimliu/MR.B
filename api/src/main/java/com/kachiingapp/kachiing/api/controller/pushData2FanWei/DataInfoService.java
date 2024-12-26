package com.kachiingapp.kachiing.api.controller.pushData2FanWei;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kachiingapp.kachiing.api.controller.http.HttpRestService;
import com.kachiingapp.kachiing.api.controller.util.Utils;
import com.kachiingapp.kachiing.api.controller.pushData2FanWei.token.MainTest;
import com.kachiingapp.kachiing.data.dao.data.FormmainMapper;
import com.kachiingapp.kachiing.data.dao.data.PushFeedBackRecordMapper;
import com.kachiingapp.kachiing.data.dao.data.TimeLockMapper;
import com.kachiingapp.kachiing.domain.entity.gongyingshang.*;
import com.kachiingapp.kachiing.domain.entity.gongyingshang.pushFeedBackRecordEntity.*;
import com.sun.corba.se.spi.ior.ObjectKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @ClassName: DataInfoService
 * @Date: 2023/11/6 13:44
 * @Description:
 *      推送致远系统数据到集团泛微
 */
@Component
public class DataInfoService implements Runnable{
    private static final Logger logger = LogManager.getLogger(DataInfoService.class);

    @Autowired
    private FormmainMapper formmainMapper;

    @Autowired
    private PushFeedBackRecordMapper pushFeedBackRecordMapper;

    @Autowired
    private TimeLockMapper timeLockMapper;

    @Autowired
    private Utils utils;

    @Autowired
    private MainTest mainTest;

    @Autowired
    private HttpRestService httpRestService;

    private ObjectMapper mapper = new ObjectMapper();

    private static final String SUPPLYIER_API_IDENTIFIER = "hc_supplierforout";

    private static final String COSTOMER_API_IDENTIFIER = "hc_customforout";

    private static final String CONTRACT_PAYMENT = "hc_AContractPayment";

    private static final String PROJECT_IDENTIFIER = "hc_Projectnew";

    private static final String COLLECT_CONTRACT = "hc_AContractCollect";

    private static final String INVOICE_OUT = "hc_hcInvoiceOut";

    private static final String MAJOR = "hc_hcmajor";

    private static final String COLLECTION_REAL = "hc_hcCollectionReal";

    private static final String COLLECTION_PLAN = "hc_hcCollectionplan";

    private static final String PRODUCT_PLAN = "hc_hcProductplan";

    private static final String INCOME_PLAN = "hc_hcIncomeplan";

    private static final String ESTIMATE_ACCOUNTS = "hc_hcestimateAccounts";

    private static final String PRODUCT_REAL = "hc_hcProductReal";

    private static final String APPID = "seeyon";

    @Override
    public void run() {
        try {
            logger.info("【开始执行周期】");
            TimeLock lock = getLock();
            pushSupplierData(lock.getTime_lock_supplier()); // 供应商
            pushCustomerData(lock.getTime_lock_customer()); // 客户
            pushProjectData(lock.getTime_lock_xiangMuLiXiang()); // 项目立项
            Timestamp collectTime = lock.getTime_lock_collectContract();
            pushCollectContractData(collectTime); // 收款合同
            pushCollectContractData2(lock.getTime_lock_collectContract2()); // 收款合同委托协议
            pushMajorData(collectTime); // 专业拆分
            pushPayContractData(lock.getTime_lock_payContract()); // 付款合同
            pushPayContractData2(lock.getTime_lock_payContract2()); // 付款合同委托协议
            pushInvoiceOutData(lock.getTime_lock_heTongKaiPiao()); // 合同开票
            pushCollectionReal(lock.getTime_lock_collectionReal()); // 收款认领
            pushCollectionPlanData(lock.getTime_lock_collectionPlan()); // 收款计划
            pushProductPlanData(lock.getTime_lock_productPlan()); // 产值计划
            pushEstimateAccountsData(lock.getTime_lock_estimateAccountsData()); // 计量应收
            pushProductRealData(lock.getTime_lock_productReal()); // 产值上报

//            chanzhishangbao();
//            pushCollectionRealHistory();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /** 供应商(结束) **/
    public void pushSupplierData(Timestamp lock) throws JsonProcessingException {
        // 从本地mysql获取供应商数据
        List<Formmain_0277> supplierData = formmainMapper.getSupplierData(lock);
        logger.info("【开始推送供应商】【数据起始时间】【" + lock + "】【推送数量】【" + supplierData.size() + "条】");
        // 取完数据，更新时间
        updateSupplierLock();
        // 获取请求API需要的token
        String token = getToken();
        int i = 0;
        for (Formmain_0277 item : supplierData) {
            logger.info("【供应商】【index: "+ i +"】【表ID: "+ item.getID() +"】");
            Map<String, Object> bodyData = buildSupplierBody(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result = httpRestService.post(body, token);
            // 记录提交是否成功或失败
            recordFeedBackFormmain_0277(item, result);
            i += 1;
        }
    }

    private Map<String, Object> buildSupplierBody(Formmain_0277 formmain0277) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("qymc", isNullString(formmain0277.getField0001())); //供应商名称
        mainTable.put("tyshxydm", isNullString(formmain0277.getField0002())); //供应商编码 统一社会信用代码、身份证
        mainTable.put("zcnf", isNullYear(formmain0277.getField0008())); //注册年份
        mainTable.put("dzdh", isNullString(formmain0277.getField0016())); //地址
        mainTable.put("khxjzh", isNullString(formmain0277.getField0019())); //开户行
        mainTable.put("zxqkqxb", utils.zxqkqxb(formmain0277.getField0056())); //征信情况（企信宝）
        mainTable.put("zh", isNullString(formmain0277.getField0020())); //账号
        mainTable.put("sffc", utils.sffc(formmain0277.getField0046())); //是否封存 是=0,否=1
        mainTable.put("fddbr", isNullString(formmain0277.getField0005())); //法人代表
        mainTable.put("gszch", isNullString(formmain0277.getField0002())); //工商注册号
        // TODO
        // FID
        String provinceId = formmain0277.getField0038();
        String supplierFID = "FIwAAAAAAVx6JWmi"; // 默认
        if (provinceId != null && !provinceId.equals("")) {
            supplierFID = formmainMapper.getSupplierFID(utils.getEnumString(provinceId));
        }
        mainTable.put("gysjbfl", supplierFID); //客户/供应商基本分类 需要传金蝶EAS中【T_BD_CSSPGroup】表中的FID
//        mainTable.put("gysjbfl", "FIwAAAAAAWJ6JWmi"); //客户/供应商基本分类 需要传金蝶EAS中【T_BD_CSSPGroup】表中的FID
        mainTable.put("nsrlx", utils.nsrlx(formmain0277.getField0053())); //int枚举：[0:枚举,1:一般纳税人,2:小规模纳税人] KingDee:纳税人类型 枚举，=-1,一般纳税人=0,小规模纳税人=1
        mainTable.put("sfgldw", utils.sfgldw(formmain0277.getField0051())); //是否关联单位 是=0,否=1
        mainTable.put("khxszs", utils.khxszs(formmain0277.getField0054())); //开户行所在省
        mainTable.put("khxszsi", utils.khxszsi(formmain0277.getField0055())); //开户行所在市
        mainTable.put("sfhcnbdw", utils.sfhcnbdw(formmain0277.getField0052())); //是否合诚内部单位 0:否,1:是
        mainTable.put("type", "1"); //客商类型 0：客户 1：供应商

        Map<String, Object> data = new HashMap<>();
        data.put("identifying", SUPPLYIER_API_IDENTIFIER);
        data.put("maintable", mainTable);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);

        return container;
    }

    private void recordFeedBackFormmain_0277(Formmain_0277 formmain0277, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0277_push_record formmain0277PushRecord = new Formmain_0277_push_record();
        formmain0277PushRecord.setId(formmain0277.getID());
        formmain0277PushRecord.setField0001(formmain0277.getField0001());
        formmain0277PushRecord.setLog(apiResult);
        formmain0277PushRecord.setJson_data(jsonData);

        if (successful) {
            formmain0277PushRecord.setStatus(1);
        } else {
            formmain0277PushRecord.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0277(formmain0277PushRecord);
    }

    /** 客户(结束) **/
    public void pushCustomerData(Timestamp lock) throws JsonProcessingException {
        List<Formmain_0037> customerData = formmainMapper.getCustomerData(lock);
        logger.info("【开始推送客户】【数据起始时间】【" + lock + "】【推送数量】【" + customerData.size() + "条】");
        updateConsumerLock();
        String token = getToken();
        int i = 0;
        for (Formmain_0037 item : customerData) {
            logger.info("【客户】【index: " + i + "】【表ID: " + item.getId() + "】");
            Map<String, Object> bodyData = buildCustomerBody(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result = httpRestService.post(body, token);
            // 记录推送记录
            recordFeedBackFormmain_0037(item, result);
            i += 1;
        }
    }

    private Map<String, Object> buildCustomerBody(Formmain_0037 formmain0037) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("sqr", "43100969"); // 申请人
        mainTable.put("khmc", isNullString(formmain0037.getField0004())); // 客户名称
        mainTable.put("tyshxydm", isNullString(formmain0037.getField0005())); // 社会统一信用码
        // TODO
        // FID
        String provinceId = formmain0037.getField0086();
        String customerFID = "FIwAAAAAYNt6JWmi"; // 默认
        if (provinceId != null && !provinceId.equals("")) {
            customerFID = formmainMapper.getCustomerFID(utils.getEnumString(provinceId));
        }
        mainTable.put("khjbfl", customerFID); // 客户基本分类
        mainTable.put("gszch", isNullString(formmain0037.getField0005())); // 工商注册号
        mainTable.put("frdb", isNullString(formmain0037.getField0006())); // 法人代表
        mainTable.put("zcnf", isNullYear(String.valueOf(formmain0037.getField0084()))); // 注册年份
        mainTable.put("dzdh", isNullString(formmain0037.getField0009())); // 地址
        mainTable.put("zyyw", isNullString(formmain0037.getField0098())); // 主营业务
        mainTable.put("zxqkqxb", isNullString(formmain0037.getField0099())); // 征信情况（企信宝）
        mainTable.put("khxz", utils.khxz(formmain0037.getField0095())); // 客户性质
        mainTable.put("sffc", utils.sffc(formmain0037.getField0017())); // 是否封存
        mainTable.put("sfgldw", utils.sfgldw(formmain0037.getField0096())); // 是否关联单位
        mainTable.put("sfhcnbdw", utils.sfhcnbdw(formmain0037.getField0097())); // 是否合诚内部单位

        Map<String, Object> data = new HashMap<>();
        data.put("identifying", COSTOMER_API_IDENTIFIER);
        data.put("maintable", mainTable);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);
        return container;
    }

    private void recordFeedBackFormmain_0037(Formmain_0037 formmain0037, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0037_push_record formmain0037PushRecord = new Formmain_0037_push_record();
        formmain0037PushRecord.setId(formmain0037.getId());
        formmain0037PushRecord.setField0004(formmain0037.getField0004());
        formmain0037PushRecord.setLog(apiResult);
        formmain0037PushRecord.setJson_data(jsonData);

        if (successful) {
            formmain0037PushRecord.setStatus(1);
        } else {
            formmain0037PushRecord.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0037(formmain0037PushRecord);
    }

    /** 项目立项 (结束) **/
    public void pushProjectData(Timestamp lock) throws JsonProcessingException {
        List<Formmain_0139> projectData = formmainMapper.getProjectData(lock);
        logger.info("【开始推送项目立项】【数据起始时间】【" + lock + "】【推送数量】【" + projectData.size() + "条】");
        updateProjectLock();
        String token = getToken();
        int i = 0;
        for (Formmain_0139 item : projectData) {
            logger.info("【项目立项】【index:" + i + "】【表ID: "+ item.getId() +"】");
            Map<String, Object> bodyData = buildProjectBody(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result  = httpRestService.post(body, token);
            // 记录推送结果
            recordFeedBackFormmain_0139(item, result);
            i += 1;
        }
    }

    private Map<String, Object> buildProjectBody(Formmain_0139 formmain0139) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("sqr", utils.projectSqr(formmain0139.getField0045())); // 申请人
        mainTable.put("tdrq", formmain0139.getField0220()); // 申请日期
        mainTable.put("lxrq", formmain0139.getField0220()); // 立项日期
        mainTable.put("xmmc", isNullString(formmain0139.getField0001())); //项目名称
        mainTable.put("gzgs", getOADeptCode(formmain0139.getField0262())); //归属公司 OA分部编号
        mainTable.put("xmjc", isNullString(formmain0139.getField0001())); //项目简称
        mainTable.put("ygxmbm", isNullString(formmain0139.getField0039())); //异构项目编码
        mainTable.put("cbbm", utils.getDeptCode(formmain0139.getField0048())); //承办部门 OA部门编号
        mainTable.put("xmje", formmain0139.getField0264()); //项目金额(元)
        mainTable.put("xmjedx", formmain0139.getField0264()); //项目金额(大写)
        mainTable.put("xmfzr", getPersonOaCode(formmain0139.getField0049())); //项目负责人 员工工号多个人员英文逗号隔开
        mainTable.put("yzlx", "0"); //客商类型 0:客户,1:供应商
        mainTable.put("khyz", utils.getCustomerSSN(formmain0139.getField0040())); //客户
        mainTable.put("jylx", utils.jylx(formmain0139.getField0050())); //经营类型 0:自营项目,1:合作项目
        mainTable.put("jbqk", isNullString(formmain0139.getField0169())); //基本情况
        mainTable.put("zxbm", utils.getDeptCode(formmain0139.getField0048())); // 执行部门
        // TODO
        mainTable.put("xmfgld", utils.xmfgld(formmain0139.getField0048())); // 项目分管领导

        Map<String, Object> data = new HashMap<>();
        data.put("identifying", PROJECT_IDENTIFIER);
        data.put("mainTable", mainTable);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);

        return container;
    }

    private void recordFeedBackFormmain_0139(Formmain_0139 formmain0139, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0139_push_record formmain_0139_push_record = new Formmain_0139_push_record();
        formmain_0139_push_record.setId(formmain0139.getId());
        formmain_0139_push_record.setField0001(formmain0139.getField0001());
        formmain_0139_push_record.setField0039(formmain0139.getField0039());
        formmain_0139_push_record.setLog(apiResult);
        formmain_0139_push_record.setJson_data(jsonData);

        if (successful) {
            formmain_0139_push_record.setStatus(1);
        } else {
            formmain_0139_push_record.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0139(formmain_0139_push_record);
    }

    /** 收款合同 **/
    public void pushCollectContractData(Timestamp lock) throws JsonProcessingException {
        List<Formmain_0027> collectContractData = formmainMapper.getCollectContractData(lock);
        logger.info("【开始推送收款合同】【数据起始时间】【" + lock + "】【推送数量】【" + collectContractData.size() + "条】");
        String token = getToken();
        int i = 0;
        for (Formmain_0027 item : collectContractData) {
            logger.info("【收款合同】【index:" + i + "】【表ID: " + item.getID() + "】");
            Map<String, Object> bodyData = buildCollectContractBody(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result = httpRestService.post(body, token);
            recordFeedBackFormmain_0027(item, result);
            i += 1;
        }
    }

    private Map<String, Object> buildCollectContractBody(Formmain_0027 formmain0027) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("SourceBillID", "seeyon," + formmain0027.getID());
        mainTable.put("sqr", utils.majorsqr(formmain0027.getField0134())); // 申请人
        mainTable.put("tdrq", isNullDate(formmain0027.getField0014())); // 申请日期
        mainTable.put("htfl", isNullString(formmain0027.getField0587())); // 合同分类
        mainTable.put("sfwwbht", 1); // 是否无文本合同
        mainTable.put("xm", isNullString(formmain0027.getField0003())); // 项目编号
        mainTable.put("sfzyjd", 1); // 是否做预交底
        mainTable.put("llyjdht", ""); // 浏览预交底合同
        mainTable.put("yjdbh", ""); // 预交底编号
        mainTable.put("htmc", isNullString(formmain0027.getField0007())); // 合同名称
        mainTable.put("lcbh", formmain0027.getField0582()); // 合同编号
//        mainTable.put("lcbh", utils.getUUID()); // 合同编号
        mainTable.put("jf", utils.getCustomerSSN(formmain0027.getField0022())); // 甲方
        mainTable.put("yf", "43100776"); // 乙方
        mainTable.put("bf", ""); // 丙方
        mainTable.put("htjydms", utils.htjydms(formmain0027.getField0016())); // 合同价约定模式
        mainTable.put("htqrkj", 1);  // 合同确认口径
        mainTable.put("htxs", "0"); // 合同形式
        mainTable.put("zht", ""); // 主合同
        mainTable.put("kjzht", ""); // 框架主合同
        mainTable.put("sfglht", 0); // 是否关联合同
        mainTable.put("glht", ""); // 关联合同
        mainTable.put("jylx", utils.jylx(formmain0027.getField0223())); // 经营类型
        mainTable.put("zbjs", utils.zbjs(formmain0027.getField0033())); // 招标简述
        mainTable.put("lhtf", ""); // 联合体
        mainTable.put("khzffs", ""); // 客户支付方式
        mainTable.put("sf", utils.getEnumString(formmain0027.getField0034())); // 省份
//        mainTable.put("cs", utils.getEnumString(formmain0027.getField0035())); // 城市
        mainTable.put("cs", ""); // 城市
        mainTable.put("gcgsj", ""); // 工程概算价
        mainTable.put("gczj", isNullNumber(formmain0027.getField0511())); // 工程造价
        mainTable.put("qyjey", utils.qyjey0027(formmain0027)); // 签约金额
        mainTable.put("qyjedx", isNullNumber(formmain0027.getField0015())); // 签约金额大写
        mainTable.put("qyjebhs", isNullNumber(formmain0027.getField0585())); // 签约金额不含税
        mainTable.put("qyse", isNullNumber(formmain0027.getField0584())); // 签约税额
        mainTable.put("htqdrq", isNullDate(formmain0027.getField0014())); // 合同签订日期
        mainTable.put("htqxb", isNullDate(formmain0027.getField0014())); // 合同开始日期
        mainTable.put("htqx", formmain0027.getField0439()); // 合同结束日期
        mainTable.put("cbbm", utils.getDeptCode(formmain0027.getField0008())); // 承办部门
        String zxbm = "";
        // 如果所在单位是总院，执行部门为生产牵头部门
        if (Objects.equals(utils.getDeptCode(formmain0027.getField0260()), "43100776")) {
            zxbm = utils.getDeptCode(formmain0027.getField0474());
        } else {
            // 如果所在单位不是总院，执行部门为运营管理部
            zxbm = "43100779";
        }
        mainTable.put("zxbm", zxbm); // 执行部门
        mainTable.put("sjzxgs", utils.getDeptCode(formmain0027.getField0260())); // 实际执行公司
        mainTable.put("sjzxbm", utils.getDeptCode(formmain0027.getField0474())); // 实际执行部门
        mainTable.put("htzxr", utils.getMemberOA(formmain0027.getField0018())); // 合同执行人
        mainTable.put("hkfzr", utils.getMemberOA(formmain0027.getField0018())); // 回款负责人
        mainTable.put("sktj", isNullString(formmain0027.getField0037())); // 收付款条件
        mainTable.put("jftk", ""); // 奖罚条件
        mainTable.put("htfxys", ""); // 合同风险因素
        mainTable.put("scwj", ""); // 审查文件
        mainTable.put("xgbz", isNullString(formmain0027.getField0502())); // 备注
//        mainTable.put("htfj", ""); // 合同附件
        mainTable.put("hsfs", utils.hsfs0027(formmain0027.getField0134())); // 核算方式
        mainTable.put("continuous", 1); // 是否连续
        mainTable.put("xzsjhsxm", "FIwAAAAAdQf5CwEz"); // 新增上级核算项目
        mainTable.put("yyhsxm", ""); // 已有核算项目
        mainTable.put("orgId", "FIwAAAAAOFPM567U"); // EAS成本组织ID
//        mainTable.put("zdyhsxmid", ""); // 自定义核算项目ID
        mainTable.put("hsxmgs", utils.getDeptCode(formmain0027.getField0260())); // 核算项目公司
        mainTable.put("bklx", "设计板块"); // 板块类型
        mainTable.put("sklhzb", utils.sklhzb(formmain0027.getField0589())); // 收款量化指标
        mainTable.put("qyzt", "43100776"); // 签约主体
        mainTable.put("zxhtje", isNullNumber(formmain0027.getField0039())); // 最新合同额
        mainTable.put("zxhtjebhs", isNullNumber(formmain0027.getField0586())); // 最新合同额不含税
        mainTable.put("htksje", isNullNumber(formmain0027.getField0039())); // 合同可收金额
        mainTable.put("ksjezy", utils.ksjezy(formmain0027.getField0039(), new BigDecimal(formmain0027.getField0225()))); // 可收金额自营
        mainTable.put("ksjenb", utils.ksjenb(formmain0027.getField0039(), new BigDecimal(formmain0027.getField0225()), new BigDecimal(formmain0027.getField0592()))); // 可收金额内部
        mainTable.put("ksjewb", utils.ksjewb(formmain0027.getField0039(), new BigDecimal(formmain0027.getField0592()))); // 可收金额外部
//        mainTable.put("sflhtht", utils.sflhtht(formmain0027.getField0012())); // 是否联合体合同
        mainTable.put("jsje", isNullNumber(formmain0027.getField0147())); // 结算金额
        mainTable.put("djrq", utils.collectDjrq(formmain0027)); // 登记日期
        mainTable.put("gdrq", utils.collectDjrq(formmain0027)); // 归档日期
        mainTable.put("htlx", 0); // 合同收付类型
//        mainTable.put("htzt", utils.htzt(formmain0027.getField0043())); // 合同状态
        mainTable.put("htzt", "7"); // 合同状态
        mainTable.put("xmjc", isNullString(formmain0027.getField0007())); // 项目名称

        List<CtpFile0027> attchedFile = utils.collectContract0027(formmain0027.getID());
        List<Map<String, Object>> lst = new LinkedList<>();
        for (CtpFile0027 item : attchedFile) {
            Map<String, Object> file = new HashMap<>();
            if (item != null) {
                file.put("name", item.getFILENAME());
                file.put("content", utils.getFileUrl(item));

                lst.add(file);
            }
        }

        Object[] arr = (Object[]) lst.toArray();
        mainTable.put("htfj", arr);

        Map<String, Object> data = new HashMap<>();
        data.put("identifying", COLLECT_CONTRACT);
        data.put("mainTable", mainTable);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);
        return container;
    }

    private void recordFeedBackFormmain_0027(Formmain_0027 formmain0027, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0027_push_record formmain_0027_push_record = new Formmain_0027_push_record();
        formmain_0027_push_record.setId(formmain0027.getID());
        formmain_0027_push_record.setField0001(formmain0027.getField0001());
        formmain_0027_push_record.setLog(apiResult);
        formmain_0027_push_record.setJson_data(jsonData);

        if (successful) {
            formmain_0027_push_record.setStatus(1);
        } else {
            formmain_0027_push_record.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0027(formmain_0027_push_record);
    }

    /**
     * 收款委托合同
     */
    public void pushCollectContractData2(Timestamp lock) throws JsonProcessingException {
        List<Formmain_0777> collectContractData = formmainMapper.getCollectContractData2(lock);
        logger.info("【开始推送收款合同2】【数据起始时间】【" + lock + "】【推送数量】【" + collectContractData.size() + "条】");
//        updateCollectContractLock2();
        String token = getToken();
        int i = 0;
        for (Formmain_0777 item : collectContractData) {
            logger.info("【收款合同】【index:" + i + "】【表ID: " + item.getID() + "】");
            Map<String, Object> bodyData = buildCollectContractBody2(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result = httpRestService.post(body, token);
            recordFeedBackFormmain_0777(item, result);

            // 推送专业拆分
            Formmain_0027 formmain0027 = utils.build0027(item);
            Map<String, Object> body0027 = buildMajorBodyWtxy(formmain0027, item);
            String postbody0027 = mapper.writeValueAsString(body0027);
            Map<String, Object> result0027 = httpRestService.post(postbody0027, token);
            recordFeedBackFormmain_0027_zycf(formmain0027, result0027);

            // 推送产值上报
            List<Formmain_0765> formmain0765 = utils.build0765(item);
            for (Formmain_0765 item0765 : formmain0765) {
                Map<String, Object> body0765 = buildProductRealBodyWtxy(item0765, item);
                String postbody0765 = mapper.writeValueAsString(body0765);
                Map<String, Object> result0765 = httpRestService.post(postbody0765, token);
                recordFeedBackFormmain_0255(item0765, result0765);
            }

            // 推送计量应收
            List<Formmain_0265> formmain0265 = utils.build0265(item);
            for (Formmain_0265 item0265 : formmain0265) {
                Map<String, Object> body0265 = buildEstimateAccountsBodyWtxy(item0265, item);
                String postbody0265 = mapper.writeValueAsString(body0265);
                Map<String, Object> result0265 = httpRestService.post(postbody0265, token);
                recordFeedBackFormmain_0265(item0265, result0265);
            }

            // 收款认领
            List<Formmain_0506> formmain0506 = utils.build0506(item);
            for (Formmain_0506 item0506 : formmain0506) {
                Map<String, Object> body0506 = buildCollectionRealBodyWtxy(item0506, item);
                String postbody0506 = mapper.writeValueAsString(body0506);
                Map<String, Object> result0506 = httpRestService.post(postbody0506, token);
                recordFeedBackFormmain_0506(item0506, result0506);
            }

            // 收款计划
            List<Formmain_0240> formmain0240s = utils.build0240(item);
            for (Formmain_0240 item0240 : formmain0240s) {
                Map<String, Object> body0240 = buildCollectionPlanBodyWtxy(item0240, item);
                String postbody0240 = mapper.writeValueAsString(body0240);
                Map<String, Object> result0240 = httpRestService.post(postbody0240, token);
                recordFeedBackFormmain_0240(item0240, result0240);
            }

            i += 1;
        }
    }

    private Map<String, Object> buildCollectContractBody2(Formmain_0777 formmain0777) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("SourceBillID", "seeyon," + formmain0777.getID());
        mainTable.put("sqr", "43100969"); // 申请人
        mainTable.put("tdrq", utils.tdrqweituoxieyi(formmain0777)); // 申请日期
        mainTable.put("htfl", formmain0777.getField0172()); // 合同分类
        mainTable.put("sfwwbht", 1); // 是否无文本合同
        mainTable.put("xm", formmain0777.getField0018()); // 项目编号
        mainTable.put("sfzyjd", 1); // 是否做预交底
        mainTable.put("llyjdht", ""); // 浏览预交底合同
        mainTable.put("yjdbh", ""); // 预交底编号
        mainTable.put("htmc", formmain0777.getField0017()); // 合同名称
        mainTable.put("lcbh", formmain0777.getField0078()); // 合同编号
//        mainTable.put("lcbh", utils.getUUID()); // 合同编号
        mainTable.put("jf", utils.getCustomerSSN("大连市市政设计研究院有限责任公司")); // 甲方
        mainTable.put("yf", getOADeptCode(formmain0777.getField0027())); // 乙方
        mainTable.put("bf", ""); // 丙方
        mainTable.put("htjydms", utils.htjydmsweituoxieyi(formmain0777)); // 合同价约定模式
        mainTable.put("htqrkj", 1);  // 合同确认口径
        mainTable.put("htxs", "0"); // 合同形式
        mainTable.put("zht", ""); // 主合同
        mainTable.put("kjzht", ""); // 框架主合同
        mainTable.put("sfglht", 0); // 是否关联合同
        mainTable.put("glht", ""); // 关联合同
        mainTable.put("jylx", utils.jylxweituoxieyi(formmain0777)); // 经营类型
        mainTable.put("zbjs", "3"); // 招标简述
        mainTable.put("lhtf", ""); // 联合体
        mainTable.put("khzffs", ""); // 客户支付方式
        mainTable.put("sf", "辽宁省"); // 省份
        mainTable.put("cs", "大连市"); // 城市
        mainTable.put("gcgsj", ""); // 工程概算价
        mainTable.put("gczj", ""); // 工程造价
        mainTable.put("qyjey", formmain0777.getField0045()); // 签约金额
        mainTable.put("qyjedx", formmain0777.getField0045()); // 签约金额大写
        mainTable.put("qyjebhs", formmain0777.getField0173().multiply(new BigDecimal("0.94"))); // 签约金额不含税
        mainTable.put("qyse", formmain0777.getField0173().multiply(new BigDecimal("0.06"))); // 签约税额
        mainTable.put("htqdrq", utils.htqdrqweituoxieyi(formmain0777)); // 合同签订日期
        mainTable.put("htqxb", utils.htqxbweituoxieyi(formmain0777)); // 合同开始日期
        mainTable.put("htqx", utils.htqxweituoxieyi(formmain0777)); // 合同结束日期
        mainTable.put("cbbm", utils.cbbmweituoxieyi(formmain0777)); // 承办部门
        String zxbm = "";

        mainTable.put("zxbm", utils.cbbmweituoxieyi(formmain0777)); // 执行部门
        mainTable.put("sjzxgs", utils.getDeptCode(formmain0777.getField0027())); // 实际执行公司
        mainTable.put("sjzxbm", utils.cbbmweituoxieyi(formmain0777)); // 实际执行部门
        mainTable.put("htzxr", utils.htzxrweituoxieyi(formmain0777)); // 合同执行人
        mainTable.put("hkfzr", utils.htzxrweituoxieyi(formmain0777)); // 回款负责人
        mainTable.put("sktj", utils.sktjweituoxieyi(formmain0777)); // 收付款条件
        mainTable.put("jftk", ""); // 奖罚条件
        mainTable.put("htfxys", ""); // 合同风险因素
        mainTable.put("scwj", ""); // 审查文件
        mainTable.put("xgbz", formmain0777.getField0047()); // 备注
//        mainTable.put("htfj", ""); // 合同附件
        mainTable.put("hsfs", utils.hsfsweituoxieyi(formmain0777)); // 核算方式
//        mainTable.put("hsfs", "1"); // 核算方式
        mainTable.put("continuous", 1); // 是否连续
        mainTable.put("xzsjhsxm", "FIwAAAAAdQf5CwEz"); // 新增上级核算项目
        mainTable.put("yyhsxm", ""); // 已有核算项目
        mainTable.put("orgId", "FIwAAAAAOFPM567U"); // EAS成本组织ID
//        mainTable.put("zdyhsxmid", ""); // 自定义核算项目ID
        mainTable.put("hsxmgs", utils.getDeptCode(formmain0777.getField0027())); // 核算项目公司
        mainTable.put("bklx", "设计板块"); // 板块类型
        mainTable.put("sklhzb", utils.sklhzbweituoxieyi(formmain0777)); // 收款量化指标
        mainTable.put("qyzt", utils.getDeptCode(formmain0777.getField0027())); // 签约主体
        mainTable.put("zxhtje", formmain0777.getField0045()); // 最新合同额
        mainTable.put("zxhtjebhs", formmain0777.getField0173().multiply(new BigDecimal("0.94"))); // 最新合同额不含税
        mainTable.put("htksje", formmain0777.getField0081()); // 合同可收金额

        BigDecimal totalwtxy = utils.totalwtxy(formmain0777).multiply(formmain0777.getField0043()).divide(new BigDecimal(100));

        mainTable.put("ksjezy", totalwtxy); // 可收金额自营
        mainTable.put("ksjenb", "0"); // 可收金额内部
        mainTable.put("ksjewb", "0"); // 可收金额外部
//        mainTable.put("sflhtht", utils.sflhtht(formmain0027.getField0012())); // 是否联合体合同
//        mainTable.put("jsje", isNullNumber(formmain0027.getField0147())); // 结算金额
        mainTable.put("djrq", utils.djrqweituoxieyi(formmain0777)); // 登记日期
        mainTable.put("gdrq", utils.djrqweituoxieyi(formmain0777)); // 归档日期
        mainTable.put("htlx", 0); // 合同收付类型
        mainTable.put("htzt", utils.htzt(formmain0777.getField0028())); // 合同状态
//        mainTable.put("xmjc", isNullString(formmain0027.getField0007())); // 项目名称

        List<CtpFile0027> attchedFile = utils.htfjweituoxieyi(formmain0777.getID());
        List<Map<String, Object>> lst = new LinkedList<>();
        for (CtpFile0027 item : attchedFile) {
            Map<String, Object> file = new HashMap<>();
            if (item != null) {
                file.put("name", item.getFILENAME());
                file.put("content", utils.getFileUrlWtxy(item));

                lst.add(file);
            }
        }

        Object[] arr = (Object[]) lst.toArray();
        mainTable.put("htfj", arr);

        Map<String, Object> data = new HashMap<>();
        data.put("identifying", COLLECT_CONTRACT);
        data.put("mainTable", mainTable);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);
        return container;
    }

    private void recordFeedBackFormmain_0777(Formmain_0777 formmain0777, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0027_push_record formmain_0027_push_record = new Formmain_0027_push_record();
        formmain_0027_push_record.setId(formmain0777.getID());
        formmain_0027_push_record.setLog(apiResult);
        formmain_0027_push_record.setJson_data(jsonData);

        if (successful) {
            formmain_0027_push_record.setStatus(1);
        } else {
            formmain_0027_push_record.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0027(formmain_0027_push_record);
    }

    /** 合同开票 (结束) **/
    public void pushInvoiceOutData(Timestamp lock) throws JsonProcessingException {
        List<Formmain_0117> invoiceData = formmainMapper.getInvoiceData(lock);
        logger.info("【开始推送合同开票】【数据起始时间】【" + lock + "】【推送数量】【" + invoiceData.size() + "条】");
        updateInvoiceLock();
        String token = getToken();
        int i = 0;
        for (Formmain_0117 item : invoiceData) {
            logger.info("【合同开票】【index: "+ i +"】【表ID: "+ item.getID() +"】");
            Map<String, Object> bodyData = buildInvoiceBody(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result = httpRestService.post(body, token);
            recordFeedBackFormmain_0117(item, result);
            i += 1;
        }
    }

    private Map<String, Object> buildInvoiceBody(Formmain_0117 formmain0117) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("sqr", utils.getMemberOA(formmain0117.getField0011())); // 申请人
        mainTable.put("tdrq", isNullDate(formmain0117.getField0012())); // 申请日期
        mainTable.put("htmc", utils.invoicehtmc(formmain0117.getField0049())); // 合同编号
        mainTable.put("fptt", utils.getCustomerSSN(formmain0117.getField0016())); // 客户单位名称
        mainTable.put("khx", isNullString(formmain0117.getField0020())); // 开户行
        mainTable.put("zh", isNullString(formmain0117.getField0019())); // 账号
        mainTable.put("dh", isNullString(formmain0117.getField0018())); // 电话
        mainTable.put("dz", isNullString(formmain0117.getField0017())); // 地址
        mainTable.put("hwhyslwfwmc", utils.hwhyslwfwmc(utils.getEnumString(formmain0117.getField0060()))); // 货物 或应 税劳 务、 服务 名称
        mainTable.put("slv", utils.slv(formmain0117.getField0025())); // 税率
        mainTable.put("kpjehsxin", formmain0117.getField0022()); // 开票金额含税
        mainTable.put("kpjebhs", formmain0117.getField0023()); // 开票金额不含税
        mainTable.put("se", formmain0117.getField0024()); // 税额
        mainTable.put("kplx", utils.kplx(formmain0117.getField0021())); // 开票类型
        mainTable.put("ggxh", ""); // 规格型号
        mainTable.put("dw", "项"); // 单位
        mainTable.put("sl", "1"); // 数量
        mainTable.put("sfzyjyjs", utils.getEnumValue(formmain0117.getField0029())); // 是否属于简易计税
        mainTable.put("sfwzzsybnsr", utils.getEnumValue(formmain0117.getField0030())); // 是否为增值税一般纳税人
        mainTable.put("kpbzl", isNullString(formmain0117.getField0031())); // 开票备注
        mainTable.put("fph", isNullString(formmain0117.getField0033())); // 发票号
        mainTable.put("fpdm", isNullString(formmain0117.getField0032())); // 发票代码
        mainTable.put("sprq", isNullDate(formmain0117.getField0035())); // 开票日期
        mainTable.put("fpje", isNullString(String.valueOf(formmain0117.getField0034()))); // 发票金额
        mainTable.put("sfwhtkp", utils.sfwhtkp(formmain0117.getField0001())); // 是否为合同开票
        mainTable.put("bm", utils.getDeptCode(formmain0117.getField0013())); // 部门
        mainTable.put("zt", "0"); // 状态
        mainTable.put("qyzt", utils.getDeptCode(formmain0117.getField0050())); // 签约主体
        mainTable.put("htmcb", formmain0117.getField0051()); // 合同名称
        mainTable.put("nsrsbh", utils.getCustomerSSN(formmain0117.getField0016())); // 纳税人识别号

        Map<String, Object> data = new HashMap<>();
        data.put("identifying", INVOICE_OUT);
        data.put("mainTable", mainTable);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);
        return container;
    }

    private void recordFeedBackFormmain_0117(Formmain_0117 formmain0117, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0117_push_record formmain_0117_push_record = new Formmain_0117_push_record();
        formmain_0117_push_record.setId(formmain0117.getID());
        formmain_0117_push_record.setField0049(formmain0117.getField0049());
        formmain_0117_push_record.setLog(apiResult);
        formmain_0117_push_record.setJson_data(jsonData);

        if (successful) {
            formmain_0117_push_record.setStatus(1);
        } else {
            formmain_0117_push_record.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0117(formmain_0117_push_record);
    }

    /** 付款合同 **/
    public void pushPayContractData(Timestamp lock) throws JsonProcessingException {
        List<Formmain_0451> payContractData = formmainMapper.getPayContractData(lock);
        logger.info("【开始推送付款合同】【数据起始时间】【" + lock + "】【推送数量】【" + payContractData.size() + "条】");
        updatePayContract();
        String token = getToken();
        int i = 0;
        for (Formmain_0451 item : payContractData) {
            logger.info("【付款合同】【index:" + i + "】【表ID: "+ item.getID() +"】");
            Map<String, Object> bodyData = buildPayContractBody(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result = httpRestService.post(body, token);
            recordFeedBackFormmain_0451(item, result);
            i += 1;
        }
    }

    private Map<String, Object> buildPayContractBody(Formmain_0451 formmain0451) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("SourceBillID", "seeyon," + formmain0451.getID());
        mainTable.put("sqr", utils.sqr(formmain0451.getField0004())); // 申请人
        mainTable.put("tdrq", formmain0451.getField0047()); // 申请日期
        mainTable.put("htfl", isNullString(formmain0451.getField0095())); // 合同分类
        if (formmain0451.getField0095() != null && formmain0451.getField0095().substring(0, 1).equalsIgnoreCase("c")) {
            // TODO 合同分类为C类，关联主合同号
            mainTable.put("glalht", utils.glalht(formmain0451.getID()));
        }
        mainTable.put("sfwwbht", 1); // 是否无文本合同
        mainTable.put("htmc", isNullString(formmain0451.getField0003())); // 合同名称
        mainTable.put("lcbh", isNullString(formmain0451.getField0015())); // 合同编号
//        mainTable.put("lcbh", utils.getUUID());
        mainTable.put("jfgys", utils.getDeptCode(formmain0451.getField0001())); // 甲 方
        mainTable.put("yfgys", isNullString(utils.getCompanySSN(formmain0451.getField0005()))); // 乙 方
        mainTable.put("bfgys", ""); // 丙 方
        mainTable.put("qyjey", isNullString(String.valueOf(formmain0451.getField0006()))); // 签约金额(元)
        mainTable.put("qyjedx", isNullString(String.valueOf(formmain0451.getField0006()))); // 签约金额大写
        mainTable.put("qyjebhs", isNullString(formmain0451.getField0090())); // 签约金额（不含税）
        mainTable.put("qyse", formmain0451.getField0092());// 签约税额
        mainTable.put("htqdrq", formmain0451.getField0047()); // 合同签订日期
        mainTable.put("htqxb", formmain0451.getField0047()); // 合同开始日期
        mainTable.put("htqx", formmain0451.getField0047()); // 合同结束日期
        mainTable.put("htxs", 0); // 合同形式
        mainTable.put("zht", isNullString(formmain0451.getField0015())); // 主合同
        mainTable.put("kjzht", ""); // 框架主合同
        mainTable.put("sfglht", 0);// 是否关联合同
        mainTable.put("glht", ""); // 关联合同
        mainTable.put("cbbm", isNullString(utils.getDeptCode(formmain0451.getField0007()))); // 承办部门
        mainTable.put("zxbm", isNullString(utils.getDeptCode(formmain0451.getField0007()))); // 执行部门
        mainTable.put("htzxr", utils.sqr(utils.htzxr(formmain0451.getField0093()))); // 合同执行人
        mainTable.put("sktj", utils.sktj(formmain0451.getField0015())); // 收/付款条件
        mainTable.put("jftk", ""); // 奖罚条款
        mainTable.put("htfxys", ""); // 合同风险因素
        mainTable.put("scwj", ""); // 审查文件
        mainTable.put("htfj", ""); // 合同附件
        mainTable.put("xgbz", isNullString(formmain0451.getField0083())); // 合同备注
        mainTable.put("hsfs", utils.hsfs0451(formmain0451.getField0015())); // 核算方式
        mainTable.put("continuous", 1); // 是否连续
        mainTable.put("xzsjhsxm", "FIwAAAAAdQf5CwEz"); // 新增上级核算项目
        mainTable.put("yyhsxm", ""); // 已有核算项目
        mainTable.put("orgId", "FIwAAAAAOFPM567U"); // EAS成本组织ID
        mainTable.put("zdyhsxmid", ""); // 自定义核算项目ID
        mainTable.put("hsxmgs", utils.getDeptCode(formmain0451.getField0001())); // 核算项目公司
        mainTable.put("qyzt", utils.getDeptCode(formmain0451.getField0001())); // 签约主体
        mainTable.put("zxhtje", isNullString(String.valueOf(formmain0451.getField0006()))); // 最新合同金额
        mainTable.put("zxhtjebhs", isNullString(formmain0451.getField0090())); // 最新合同金额（不含税）
        mainTable.put("htzt", utils.htzt(formmain0451.getField0096())); // 合同状态
        mainTable.put("jsje", isNullString(String.valueOf(formmain0451.getField0038()))); // 结算金额
        mainTable.put("sjzxgs", utils.getDeptCode(formmain0451.getField0001())); // 实际执行公司
        mainTable.put("sjzxbm", utils.getDeptCode(formmain0451.getField0007())); //实际执行部门
        mainTable.put("sf", ""); // 省份
        mainTable.put("cs", ""); // 城市
        mainTable.put("gdrq", isNullDate(formmain0451.getField0047())); // 归档日期
        mainTable.put("htlx", 1);

        Map<String, Object> detail3 = new HashMap<>();
        detail3.put("skdw", isNullString(formmain0451.getField0005())); // 收款单位
//        detail3.put("skyx", isNullString(utils.skyx(formmain0451.getField0005()))); // 收款银行
        detail3.put("skyx", utils.getBankId(utils.skyx(formmain0451.getField0005()))); // 收款银行
        detail3.put("yxzh", isNullString(utils.yxzh(formmain0451.getField0005()))); // 银行账号
        detail3.put("skfs", utils.khxszs(utils.skfs(formmain0451.getField0005()))); // 收款方省
        detail3.put("skfxs", utils.khxszsi(utils.skfxs(formmain0451.getField0005()))); // 收款方县市
        detail3.put("hsxmgs", utils.getDeptCode(formmain0451.getField0001())); // 核算项目公司

        Object[] arr = new Object[]{detail3};
        Map<String, Object> data = new HashMap<>();
        data.put("identifying", CONTRACT_PAYMENT);
        data.put("mainTable", mainTable);
        data.put("detail3", arr);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);
        return container;
    }

    private void recordFeedBackFormmain_0451(Formmain_0451 formmain0451, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0451_push_record formmain0451PushRecord = new Formmain_0451_push_record();
        formmain0451PushRecord.setId(formmain0451.getID());
        formmain0451PushRecord.setField0015(formmain0451.getField0015());
        formmain0451PushRecord.setLog(apiResult);
        formmain0451PushRecord.setJson_data(jsonData);

        if (successful) {
            formmain0451PushRecord.setStatus(1);
        } else {
            formmain0451PushRecord.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0451(formmain0451PushRecord);
    }

    /** 付款合同委托档案 **/
    public void pushPayContractData2(Timestamp lock) throws JsonProcessingException {
        List<Formmain_0542> payContractData = formmainMapper.getPayContractData2(lock);
        logger.info("【开始推送付款合同委托协议】【数据起始时间】【" + lock + "】【推送数量】【" + payContractData.size() + "条】");
        updatePayContractLock2();
        String token = getToken();
        int i = 0;
        for (Formmain_0542 item : payContractData) {
            logger.info("【付款合同】【index:" + i + "】【表ID: "+ item.getID() +"】");
            Map<String, Object> bodyData = buildPayContractBody2(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result = httpRestService.post(body, token);
            recordFeedBackFormmain_0542(item, result);
            i += 1;
        }
    }

    private Map<String, Object> buildPayContractBody2(Formmain_0542 formmain0542) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("SourceBillID", "seeyon," + formmain0542.getID());
        mainTable.put("sqr", "43100969"); // 申请人
        mainTable.put("tdrq", utils.tdrqfukuan(formmain0542)); // 申请日期
        mainTable.put("htfl", formmain0542.getField0173()); // 合同分类
        mainTable.put("glalht", formmain0542.getField0008());
        mainTable.put("sfwwbht", 2); // 是否无文本合同
        mainTable.put("htmc", formmain0542.getField0006()); // 合同名称
        mainTable.put("lcbh", formmain0542.getField0084()); // 合同编号
//        mainTable.put("lcbh", utils.getUUID());
        mainTable.put("jfgys", "43100776"); // 甲 方
        mainTable.put("yfgys", formmain0542.getField0015()); // 乙 方
        mainTable.put("bfgys", ""); // 丙 方
        mainTable.put("qyjey", formmain0542.getField0033()); // 签约金额(元)
        mainTable.put("qyjedx", formmain0542.getField0033()); // 签约金额大写
        mainTable.put("qyjebhs", formmain0542.getField0174()); // 签约金额（不含税）
        mainTable.put("qyse", formmain0542.getField0175());// 签约税额
        mainTable.put("htqdrq", utils.htqdrqfukuan(formmain0542)); // 合同签订日期
        mainTable.put("htqxb", utils.htqxbfukuan(formmain0542)); // 合同开始日期
        mainTable.put("htqx", utils.htqxfukuan(formmain0542)); // 合同结束日期
        mainTable.put("htxs", 0); // 合同形式
        mainTable.put("zht", formmain0542.getField0084()); // 主合同
        mainTable.put("kjzht", ""); // 框架主合同
        mainTable.put("sfglht", 0);// 是否关联合同
        mainTable.put("glht", ""); // 关联合同
        mainTable.put("cbbm", "43100779"); // 承办部门
        mainTable.put("zxbm", "43100779"); // 执行部门
        mainTable.put("htzxr", "1801809"); // 合同执行人
        mainTable.put("sktj", utils.sktjfukuan(formmain0542)); // 收/付款条件
        mainTable.put("jftk", ""); // 奖罚条款
        mainTable.put("htfxys", ""); // 合同风险因素
        mainTable.put("scwj", ""); // 审查文件
        mainTable.put("htfj", ""); // 合同附件
        mainTable.put("xgbz", formmain0542.getField0102()); // 合同备注
        mainTable.put("hsfs", utils.hsfsfukuan(formmain0542)); // 核算方式
        mainTable.put("continuous", utils.continuousfukuan(formmain0542)); // 是否连续
        mainTable.put("xzsjhsxm", "FIwAAAAAdQf5CwEz"); // 新增上级核算项目
        mainTable.put("yyhsxm", ""); // 已有核算项目
        mainTable.put("orgId", "FIwAAAAAOFPM567U"); // EAS成本组织ID
        mainTable.put("zdyhsxmid", ""); // 自定义核算项目ID
        mainTable.put("hsxmgs", utils.getDeptCode(formmain0542.getField0015())); // 核算项目公司
        mainTable.put("qyzt", "43100776"); // 签约主体
        mainTable.put("zxhtje", formmain0542.getField0072()); // 最新合同金额
        mainTable.put("zxhtjebhs", formmain0542.getField0178()); // 最新合同金额（不含税）
        mainTable.put("htzt", utils.htzt(formmain0542.getField0061())); // 合同状态
        mainTable.put("jsje", formmain0542.getField0075()); // 结算金额
        mainTable.put("sjzxgs", "43100779"); // 实际执行公司
        mainTable.put("sjzxbm", "43100779"); //实际执行部门
        mainTable.put("sf", ""); // 省份
        mainTable.put("cs", ""); // 城市
        mainTable.put("gdrq", utils.htqdrqfukuan(formmain0542)); // 归档日期
        mainTable.put("htlx", 1);

        Map<String, Object> detail3 = new HashMap<>();
        detail3.put("skdw", utils.skdwfukuan(formmain0542)); // 收款单位
        detail3.put("skyx", utils.skyxfukuan(formmain0542)); // 收款银行
        detail3.put("yxzh", utils.yxzhfukaun(formmain0542)); // 银行账号
        detail3.put("skfs", utils.skfsfukuan(formmain0542)); // 收款方省
        detail3.put("skfxs", utils.skfxsfukuan(formmain0542)); // 收款方县市

//        List<CtpFile0027> attchedFile = utils.htfjweituoxieyifukuan(formmain0542.getID());
//        List<Map<String, Object>> lst = new LinkedList<>();
//        for (CtpFile0027 item : attchedFile) {
//            Map<String, Object> file = new HashMap<>();
//            if (item != null) {
//                file.put("name", item.getFILENAME());
//                file.put("content", utils.getFileUrl(item));
//
//                lst.add(file);
//            }
//        }
//
//        Object[] arr = (Object[]) lst.toArray();
//        mainTable.put("htfj", arr);

        Object[] detail3arr = new Object[]{detail3};
        Map<String, Object> data = new HashMap<>();
        data.put("identifying", CONTRACT_PAYMENT);
        data.put("mainTable", mainTable);
        data.put("detail3", detail3arr);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);
        return container;
    }

    private void recordFeedBackFormmain_0542(Formmain_0542 formmain0542, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0451_push_record formmain0451PushRecord = new Formmain_0451_push_record();
        formmain0451PushRecord.setId(formmain0542.getID());
        formmain0451PushRecord.setLog(apiResult);
        formmain0451PushRecord.setJson_data(jsonData);

        if (successful) {
            formmain0451PushRecord.setStatus(1);
        } else {
            formmain0451PushRecord.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0451(formmain0451PushRecord);
    }

    /**
     * 专业拆分
     **/
    public void pushMajorData(Timestamp lock) throws JsonProcessingException {
        List<Formmain_0027> majorData = formmainMapper.getCollectContractData(lock);
        logger.info("【开始推送专业拆分】【数据起始时间】【" + lock + "】【推送数量】【" + majorData.size() + "条】");
        String token = getToken();
        updateCollectContractLock();
        int i = 0;
        for (Formmain_0027 item : majorData) {
            logger.info("【专业拆分】【index:" + i + "】【表ID: "+ item.getID() +"】");
            Map<String, Object> bodyData = buildMajorBody(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result = httpRestService.post(body, token);
            recordFeedBackFormmain_0027_zycf(item, result);
            i += 1;
        }
    }

    private Map<String, Object> buildMajorBody(Formmain_0027 formmain0027) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("sqr", utils.majorsqr(formmain0027.getField0134())); // 申请人
        mainTable.put("tdrq", isNullDate(formmain0027.getField0014())); // 申请日期
        mainTable.put("htbh", formmain0027.getField0582()); // 合同编号
        mainTable.put("htksje", formmain0027.getField0039()); // 合同可收金额
        mainTable.put("ksjezy", utils.ksjezy(formmain0027.getField0039(), new BigDecimal(formmain0027.getField0225()))); // 可收金额（自营）
        mainTable.put("ksjenb", utils.ksjenb(formmain0027.getField0039(), new BigDecimal(formmain0027.getField0225()),
                new BigDecimal(formmain0027.getField0592()))); // 可收金额内部
        mainTable.put("ksjewb", utils.ksjewb(formmain0027.getField0039(), new BigDecimal(formmain0027.getField0592()))); // 可收金额（外部）
        mainTable.put("bklx", "设计板块"); // 板块类型
        mainTable.put("sklhzb", utils.sklhzb(formmain0027.getField0589())); // 收款量化指标

        Map<String, Object> detail1 = new HashMap<>();
        detail1.put("zy", formmain0027.getField0588()); // 专业
        String zxbm = "";
        // 如果所在单位是总院，执行部门为生产牵头部门
        if (Objects.equals(utils.getDeptCode(formmain0027.getField0260()), "43100776")) {
            zxbm = utils.getDeptCode(formmain0027.getField0474());
        } else {
            // 如果所在单位不是总院，执行部门为运营管理部
            zxbm = "43100779";
        }
        detail1.put("zxbm", zxbm); // 执行部门
        detail1.put("zmsrqrff", formmain0027.getField0590()); // 账面收入 确认方法
        detail1.put("zysl", "0.06"); // 专业税率
        detail1.put("zyhtje", formmain0027.getField0039()); // 专业合同金额
        detail1.put("zyje", formmain0027.getField0464()); // 自营金额
        detail1.put("wbhzje", utils.wbhzje(formmain0027.getField0039(), formmain0027.getField0592())); // 外部合作金额
        detail1.put("nbhzje", utils.ksjenb(formmain0027.getField0039(), new BigDecimal(formmain0027.getField0225()),
                new BigDecimal(formmain0027.getField0592()))); // 内部合作金额
        BigDecimal total = utils.majorhj(formmain0027.getField0464(),
                utils.wbhzje(formmain0027.getField0039(), formmain0027.getField0592()),
                utils.ksjenb(formmain0027.getField0039(), new BigDecimal(formmain0027.getField0225()),
                        new BigDecimal(formmain0027.getField0592())));
        detail1.put("hj", total); // 合计
        detail1.put("zyhtjebhs", formmain0027.getField0586()); //专业合同金额（不含税）
        // TODO
        detail1.put("cwrzbm", ""); // 财务入账部门
        BigDecimal ziying = utils.bqzybl(formmain0027.getField0464(), total);
        BigDecimal waibu = utils.wbhzbl(utils.wbhzje(formmain0027.getField0039(), formmain0027.getField0592()),
                total);
        BigDecimal neibu = new BigDecimal(1).subtract(ziying).subtract(waibu);
        detail1.put("bqzybl", ziying); // 本期自营比例
        detail1.put("wbhzbl", waibu); // 外部合作比例
        detail1.put("nbhzbl", neibu); // 内部合作比例

        mainTable.put("htlb", isNullString(formmain0027.getField0587())); // 合同类别
        mainTable.put("xmxx", isNullString(formmain0027.getField0003())); // 项目信息
        mainTable.put("htmc", isNullString(formmain0027.getField0007())); // 合同名称
        mainTable.put("qyjey", isNullString(String.valueOf(formmain0027.getField0015()))); // 签约金额
        mainTable.put("qyjedx", isNullString(String.valueOf(formmain0027.getField0015()))); // 签约金额大写
        mainTable.put("qyjebhs", isNullString(String.valueOf(formmain0027.getField0585()))); // 签约金额不含税
        mainTable.put("qyse", isNullString(String.valueOf(formmain0027.getField0584()))); // 签约税额
        mainTable.put("zxhtje", isNullString(String.valueOf(formmain0027.getField0039()))); // 最新合同额
        mainTable.put("zxhtjebhs", isNullString(String.valueOf(formmain0027.getField0586()))); // 最新合同额不含税

        Object[] arr = new Object[]{detail1};
        Map<String, Object> data = new HashMap<>();
        data.put("identifying", MAJOR);
        data.put("mainTable", mainTable);
        data.put("detail1", arr);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);
        return container;
    }

    private Map<String, Object> buildMajorBodyWtxy(Formmain_0027 formmain0027, Formmain_0777 formmain0777) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("SourceBillID", "seeyon," + formmain0027.getID() + formmain0777.getID());
        BigDecimal ratio = formmain0777.getField0043().divide(new BigDecimal(100)); // 委托协议占比
        mainTable.put("sqr", "43100969"); // 申请人
        mainTable.put("tdrq", utils.tdrqweituoxieyi(formmain0777)); // 申请日期
        mainTable.put("htbh", formmain0777.getField0078()); // 合同编号

        BigDecimal totalwtxy = utils.totalwtxy(formmain0777).multiply(formmain0777.getField0043()).divide(new BigDecimal(100));
        BigDecimal zyratio = formmain0777.getField0133();
        BigDecimal wbratio = new BigDecimal(0);
        BigDecimal nbRatio = new BigDecimal(1).subtract(zyratio).subtract(wbratio);

        mainTable.put("htksje", formmain0777.getField0081()); // 合同可收金额
        mainTable.put("ksjezy", totalwtxy); // 可收金额（自营）
        mainTable.put("ksjenb", 0); // 可收金额内部
        mainTable.put("ksjewb", 0); // 可收金额（外部）
        mainTable.put("bklx", "设计板块"); // 板块类型
        mainTable.put("sklhzb", utils.sklhzbweituoxieyi(formmain0777)); // 收款量化指标

        Map<String, Object> detail1 = new HashMap<>();
        detail1.put("zy", formmain0027.getField0588()); // 专业

        detail1.put("zxbm", utils.cbbmweituoxieyi(formmain0777)); // 执行部门
        detail1.put("zmsrqrff", formmain0027.getField0590()); // 账面收入 确认方法
        detail1.put("zysl", "0.06"); // 专业税率
        detail1.put("zyhtje", formmain0777.getField0081()); // 专业合同金额
        detail1.put("zyje", totalwtxy); // 自营金额
        detail1.put("wbhzje", 0); // 外部合作金额
        detail1.put("nbhzje", 0); // 内部合作金额

        detail1.put("hj", totalwtxy); // 合计
        detail1.put("zyhtjebhs", formmain0777.getField0173().multiply(new BigDecimal("0.94"))); //专业合同金额（不含税）
        // TODO
        detail1.put("cwrzbm", ""); // 财务入账部门

        detail1.put("bqzybl", 100); // 本期自营比例
        detail1.put("wbhzbl", 0); // 外部合作比例
        detail1.put("nbhzbl", 0); // 内部合作比例

        mainTable.put("htlb", isNullString(formmain0027.getField0587())); // 合同类别
        mainTable.put("xmxx", isNullString(formmain0027.getField0003())); // 项目信息
        mainTable.put("htmc", isNullString(formmain0027.getField0007())); // 合同名称
        mainTable.put("qyjey", formmain0777.getField0045()); // 签约金额
        mainTable.put("qyjedx", formmain0777.getField0045()); // 签约金额大写
        mainTable.put("qyjebhs", formmain0777.getField0173().multiply(new BigDecimal("0.94"))); // 签约金额不含税
        mainTable.put("qyse", formmain0777.getField0173().multiply(new BigDecimal("0.06"))); // 签约税额
        mainTable.put("zxhtje", formmain0777.getField0045()); // 最新合同额
        mainTable.put("zxhtjebhs", formmain0777.getField0173().multiply(new BigDecimal("0.94"))); // 最新合同额不含税

        Object[] arr = new Object[]{detail1};
        Map<String, Object> data = new HashMap<>();
        data.put("identifying", MAJOR);
        data.put("mainTable", mainTable);
        data.put("detail1", arr);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);
        return container;
    }

    private void recordFeedBackFormmain_0027_zycf(Formmain_0027 formmain0027, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0027_zycf_push_record formmain0027ZycfPushRecord = new Formmain_0027_zycf_push_record();
        formmain0027ZycfPushRecord.setId(formmain0027.getID());
        formmain0027ZycfPushRecord.setField0001(formmain0027.getField0001());
        formmain0027ZycfPushRecord.setLog(apiResult);
        formmain0027ZycfPushRecord.setJson_data(jsonData);

        if (successful) {
            formmain0027ZycfPushRecord.setStatus(1);
        } else {
            formmain0027ZycfPushRecord.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0027_zycf(formmain0027ZycfPushRecord);
    }

    /** 收款认领 **/
    public void pushCollectionReal(Timestamp lock) throws JsonProcessingException {
        List<Formmain_0506> majorData = formmainMapper.getCollectionReal(lock);
        logger.info("【开始推送收款认领】【数据起始时间】【" + lock + "】【推送数量】【" + majorData.size() + "条】");
        updateCollectionRealLock();
        String token = getToken();
        int i = 0;
        for (Formmain_0506 item : majorData) {
            logger.info("【收款认领】【index:" + i + "】【表ID: "+ item.getID() +"】");
            Map<String, Object> bodyData = buildCollectionRealBody(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result = httpRestService.post(body, token);
            recordFeedBackFormmain_0506(item, result);
            i += 1;
        }
    }

    public Map<String, Object> buildCollectionRealBody(Formmain_0506 formmain0506) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("SourceBillID", "seeyon," + formmain0506.getID());
        mainTable.put("skdw", utils.getDeptCode(formmain0506.getField0069())); // 收款单位
        mainTable.put("fkdw", formmain0506.getField0091()); // 付款单位
        mainTable.put("dyht", utils.realhtbh(formmain0506.getField0057())); // 对应合同
        mainTable.put("htbh", utils.realhtbh(formmain0506.getField0057())); // 合同编号
        mainTable.put("htzy", utils.realhtzy(formmain0506.getField0057())); // 合同专业
        mainTable.put("ywkm", utils.realywkm(formmain0506.getField0057())); // 业务科目
        mainTable.put("zxbm", utils.getDeptCode(formmain0506.getField0073())); // 执行部门
        mainTable.put("jljsje", utils.jljsje(formmain0506.getField0057())); // 计量结算金额
        mainTable.put("je", isNullNumber(formmain0506.getField0050())); // 金额
        mainTable.put("skrq", formmain0506.getField0051()); // 收款日期
        mainTable.put("rzbm", utils.rzbm(formmain0506.getField0022())); // 入账部门
        mainTable.put("szfb", utils.getDeptCode(formmain0506.getField0069())); // 所属分部

        Map<String, Object> data = new HashMap<>();
        data.put("identifying", COLLECTION_REAL);
        data.put("mainTable", mainTable);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);
        return container;
    }

    public Map<String, Object> buildCollectionRealBodyWtxy(Formmain_0506 formmain0506, Formmain_0777 formmain0777) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("SourceBillID", "seeyon," + formmain0506.getID() + formmain0777.getID());
        BigDecimal ratio = formmain0777.getField0043().divide(new BigDecimal(100)); // 委托协议占比
        mainTable.put("skdw", utils.getDeptCode(formmain0506.getField0069())); // 收款单位
        mainTable.put("fkdw", formmain0506.getField0091()); // 付款单位
        mainTable.put("dyht", formmain0777.getField0078()); // 对应合同
        mainTable.put("htbh", formmain0777.getField0078()); // 合同编号
        mainTable.put("htzy", utils.realhtzy(formmain0506.getField0057())); // 合同专业
        mainTable.put("ywkm", utils.realywkm(formmain0506.getField0057())); // 业务科目
        mainTable.put("zxbm", utils.getDeptCode(formmain0506.getField0073())); // 执行部门
        mainTable.put("jljsje", utils.jljsje(formmain0506.getField0057()).multiply(ratio)); // 计量结算金额
        mainTable.put("je", formmain0506.getField0050().multiply(ratio)); // 金额
        mainTable.put("skrq", formmain0506.getField0051()); // 收款日期
        mainTable.put("rzbm", utils.rzbm(formmain0506.getField0022())); // 入账部门
        mainTable.put("szfb", utils.getDeptCode(formmain0506.getField0069())); // 所属分部

        Map<String, Object> data = new HashMap<>();
        data.put("identifying", COLLECTION_REAL);
        data.put("mainTable", mainTable);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);
        return container;
    }

    private void recordFeedBackFormmain_0506(Formmain_0506 formmain0506, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0506_push_record formmain0506PushRecord = new Formmain_0506_push_record();
        formmain0506PushRecord.setId(formmain0506.getID());
        formmain0506PushRecord.setField0023(formmain0506.getField0023());
        formmain0506PushRecord.setLog(apiResult);
        formmain0506PushRecord.setJson_data(jsonData);

        if (successful) {
            formmain0506PushRecord.setStatus(1);
        } else {
            formmain0506PushRecord.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0506(formmain0506PushRecord);
    }

    /** 收款计划 **/
    public void pushCollectionPlanData(Timestamp lock) throws JsonProcessingException {
        List<Formmain_0240> majorData = formmainMapper.getCollectionPlanData(lock);
        logger.info("【开始推送收款计划】【数据起始时间】【" + lock + "】【推送数量】【" + majorData.size() + "条】");
        updateCollectionPlanLock();
        String token = getToken();
        int i = 0;
        for (Formmain_0240 item : majorData) {
            logger.info("【收款计划】【index:" + i + "】【表ID: "+ item.getID() +"】");
            Map<String, Object> bodyData = buildCollectionPlanBody(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result = httpRestService.post(body, token);
            recordFeedBackFormmain_0240(item, result);
            i += 1;
        }
    }

    public Map<String, Object> buildCollectionPlanBody(Formmain_0240 formmain0240) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("SourceBillID", "seeyon," + formmain0240.getID());
        mainTable.put("sqr", utils.collectplansqr(formmain0240.getField0134())); // 申请人
        mainTable.put("tdrq", utils.collectplantdrq(formmain0240.getField0073())); // 申请日期
        mainTable.put("htbh", utils.realhtbh(formmain0240.getField0073())); // 合同编号
        mainTable.put("sxzt", 0); // 生效状态

        List<Formson_0243> formson0243s = formmainMapper.getAccumulatedCollection(formmain0240.getID());
        List<Map<String, Object>> lst = new LinkedList<>();
        float accRatio = 0;
        for (Formson_0243 item : formson0243s) {
            Map<String, Object> detail1 = new HashMap<>();
            detail1.put("SourceBillID", "seeyon," + item.getID());
            detail1.put("sj", item.getField0060().toString()); // 时间
            detail1.put("htzy", utils.realhtzy(formmain0240.getField0073())); // 合同专业
            detail1.put("zy", utils.realhtzy(formmain0240.getField0073())); // 合同专业
            detail1.put("zxbm", utils.getDeptCode(formmain0240.getField0018())); // 执行部门
            detail1.put("xmzxzt", utils.planxmzxzt(formmain0240.getID())); // 项目执行状态
            detail1.put("htzt", utils.planhtzt(utils.getEnumString(utils.collectplanhtzt(formmain0240.getField0073())))); // 合同状态
            detail1.put("bqjhhk", item.getField0062()); // 本期计划回款
            accRatio += item.getField0064();
            detail1.put("ljbl", accRatio / 100); // 累积比例

            lst.add(detail1);
        }

        Object[] arr = (Object[]) lst.toArray();

        mainTable.put("htlb", utils.htlb(formmain0240.getField0073())); // 合同类别
        mainTable.put("xmxx", formmain0240.getField0004()); // 项目信息
        mainTable.put("htmc", formmain0240.getField0008()); // 合同名称
        mainTable.put("qyjey", utils.planqyjey(formmain0240.getField0073())); // 签约金额
        mainTable.put("qyjedx", utils.planqyjey(formmain0240.getField0073())); // 签约金额大写
        mainTable.put("qyjebhs", utils.planqyjebhs(formmain0240.getField0073())); // 签约金额不含税
        mainTable.put("qyse", utils.planqyse(formmain0240.getField0073())); // 签约税额
        mainTable.put("zxhtje", utils.zxhtje(formmain0240.getField0073())); // 最新合同额
        mainTable.put("zxhtjebhs", utils.zxhtjebhs(formmain0240.getField0073())); // 最新合同额不含税
        mainTable.put("htksje", utils.htksje(formmain0240.getField0073())); // 合同可收金额
        BigDecimal total = new BigDecimal(utils.htksje(formmain0240.getField0073()));
        float ratio1 = utils.ksjezy(formmain0240.getField0073()); // 内部比例
        float ratio2 = utils.planksjewb(formmain0240.getField0073()); // 外部比例
        mainTable.put("ksjezy", utils.ksjezy(total, new BigDecimal(ratio1))); // 可收自营
        mainTable.put("ksjenb", utils.ksjenb(total, new BigDecimal(ratio1), new BigDecimal(ratio2))); // 可收内部
        mainTable.put("ksjewb", utils.ksjewb(total, new BigDecimal(ratio2))); // 可收外部

        Map<String, Object> data = new HashMap<>();
        data.put("identifying", COLLECTION_PLAN);
        data.put("mainTable", mainTable);
        data.put("detail1", arr);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);
        return container;
    }

    public Map<String, Object> buildCollectionPlanBodyWtxy(Formmain_0240 formmain0240, Formmain_0777 formmain0777) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("SourceBillID", "seeyon," + formmain0240.getID() + formmain0777.getID());
        BigDecimal ratio = formmain0777.getField0043().divide(new BigDecimal(100)); // 委托协议占比
        mainTable.put("sqr", utils.collectplansqr(formmain0240.getField0134())); // 申请人
        mainTable.put("tdrq", utils.collectplantdrq(formmain0240.getField0073())); // 申请日期
        mainTable.put("htbh", formmain0777.getField0078()); // 合同编号
        mainTable.put("sxzt", 0); // 生效状态

        List<Formson_0243> formson0243s = formmainMapper.getAccumulatedCollection(formmain0240.getID());
        List<Map<String, Object>> lst = new LinkedList<>();
        float accRatio = 0;
        for (Formson_0243 item : formson0243s) {
            Map<String, Object> detail1 = new HashMap<>();
            detail1.put("SourceBillID", "seeyon," + item.getID() + formmain0777.getID());
            detail1.put("sj", item.getField0060().toString()); // 时间
            detail1.put("htzy", utils.realhtzy(formmain0240.getField0073())); // 合同专业
            detail1.put("zy", utils.realhtzy(formmain0240.getField0073())); // 合同专业
            detail1.put("zxbm", utils.getDeptCode(formmain0240.getField0018())); // 执行部门
            detail1.put("xmzxzt", utils.planxmzxzt(formmain0240.getID())); // 项目执行状态
            detail1.put("htzt", utils.planhtzt(utils.getEnumString(utils.collectplanhtzt(formmain0240.getField0073())))); // 合同状态
            detail1.put("bqjhhk", item.getField0062().multiply(ratio)); // 本期计划回款
            accRatio += item.getField0064();
            detail1.put("ljbl", accRatio / 100); // 累积比例

            lst.add(detail1);
        }

        Object[] arr = (Object[]) lst.toArray();

        mainTable.put("htlb", utils.htlb(formmain0240.getField0073())); // 合同类别
        mainTable.put("xmxx", formmain0240.getField0004()); // 项目信息
        mainTable.put("htmc", formmain0777.getField0017()); // 合同名称
        mainTable.put("qyjey", formmain0777.getField0045()); // 签约金额
        mainTable.put("qyjedx", formmain0777.getField0045()); // 签约金额大写
        mainTable.put("qyjebhs", formmain0777.getField0173().multiply(new BigDecimal("0.94"))); // 签约金额不含税
        mainTable.put("qyse", formmain0777.getField0173().multiply(new BigDecimal("0.06"))); // 签约税额
        mainTable.put("zxhtje", formmain0777.getField0045()); // 最新合同额
        mainTable.put("zxhtjebhs", formmain0777.getField0173().multiply(new BigDecimal("0.94"))); // 最新合同额不含税
        mainTable.put("htksje", formmain0777.getField0081()); // 合同可收金额

        BigDecimal totalwtxy = utils.totalwtxy(formmain0777).multiply(formmain0777.getField0043()).divide(new BigDecimal(100));

        mainTable.put("ksjezy", totalwtxy); // 可收自营
        mainTable.put("ksjenb", 0); // 可收内部
        mainTable.put("ksjewb", 0); // 可收外部

        Map<String, Object> data = new HashMap<>();
        data.put("identifying", COLLECTION_PLAN);
        data.put("mainTable", mainTable);
        data.put("detail1", arr);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);
        return container;
    }

    private void recordFeedBackFormmain_0240(Formmain_0240 formmain0240, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0240_push_record formmain0240PushRecord = new Formmain_0240_push_record();
        formmain0240PushRecord.setId(formmain0240.getID());
        formmain0240PushRecord.setField0073(formmain0240.getField0073());
        formmain0240PushRecord.setLog(apiResult);
        formmain0240PushRecord.setJson_data(jsonData);

        if (successful) {
            formmain0240PushRecord.setStatus(1);
        } else {
            formmain0240PushRecord.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0240(formmain0240PushRecord);
    }

    /** 产值计划 **/
    public void pushProductPlanData(Timestamp lock) throws JsonProcessingException {
        List<Formmain_0617> data = formmainMapper.getProductPlanData(lock);
        logger.info("【开始推送产值计划】【数据起始时间】【" + lock + "】【推送数量】【" + data.size() + "条】");
        String token = getToken();
        updateProductPlanLock();
        int i = 0;
        for (Formmain_0617 item : data)  {
            logger.info("【产值计划】【index:" + i + "】【表ID: "+ item.getID() +"】");
            Map<String, Object> bodyData = buildProductPlanBody(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result = httpRestService.post(body, token);
            recordFeedBackFormmain_0617(item, result);
            i += 1;
        }
    }

    public Map<String, Object> buildProductPlanBody(Formmain_0617 formmain0617) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("SourceBillID", "seeyon," + formmain0617.getID());
        mainTable.put("sqr", utils.productPlanSqr(formmain0617.getField0003()));// 申请人
        mainTable.put("tdrq", formmain0617.getField0016().toString()); // 申请日期
        mainTable.put("htbh", utils.productPlanHtbh(formmain0617.getField0003())); // 合同编号

        List<Formson_0618> formson0618s = formmainMapper.getAccumulatedProduct(formmain0617.getID());
        List<Map<String, Object>> lst = new LinkedList<>();
        for (Formson_0618 item : formson0618s) {
            Map<String, Object> detail1 = new HashMap<>();
            detail1.put("SourceBillID", "seeyon," + item.getID());
            if (item.getField0019() == null) {
                detail1.put("sj", item.getField0009().toString()); // 时间
            } else {
                detail1.put("sj", item.getField0019().toString()); // 时间
            }
            detail1.put("htzy", utils.productPlanHtzy(formmain0617.getField0003())); // 合同专业
            detail1.put("srqrff", utils.productPlanSrqrff(formmain0617.getField0003())); // 收入确认方法
            detail1.put("zxbm", utils.productPlanZxbm(formmain0617.getField0003())); // 执行部门
            detail1.put("xmzxzt", utils.productPlanXmzxzt(formmain0617.getField0003())); // 项目执行状态
            detail1.put("htzt", utils.productPlanHtzt(formmain0617.getField0003())); // 合同状态
            BigDecimal ratio = utils.bqjhhkratio(item);
            BigDecimal je = utils.productPlanQyjey(formmain0617.getField0003());
            detail1.put("bqjhhk", je.multiply(ratio)); // 本期计划产值
            detail1.put("ljbl", item.getField0011()); // 累计产值比例
            detail1.put("smgjjd", utils.smgjjd(item)); // 节点描述

            lst.add(detail1);
        }

        Object[] arr = (Object[]) lst.toArray();

        mainTable.put("htlb", utils.productPlanHtlb(formmain0617.getField0003())); // 合同类别
        mainTable.put("xmxx", formmain0617.getField0003()); // 项目信息
        mainTable.put("htmc", utils.productPlanHtmc(formmain0617.getField0003())); // 合同名称
        mainTable.put("qyjey", utils.productPlanQyjey(formmain0617.getField0003())); // 签约金额
        mainTable.put("qyjedx", utils.productPlanQyjey(formmain0617.getField0003())); // 签约金额大写
        mainTable.put("qyjebhs", utils.productPlanQyjebhs(formmain0617.getField0003())); // 签约金额不含税
        mainTable.put("qyse", utils.productPlanQyse(formmain0617.getField0003())); // 签约税额
        mainTable.put("zxhtje", utils.productPlanZxhtje(formmain0617.getField0003())); // 最新合同额
        mainTable.put("zxhtjebhs", utils.productPlanZxhtje(formmain0617.getField0003())); // 最新合同额不含税
        mainTable.put("htksje", utils.productPlanHtksje(formmain0617.getField0003())); // 合同可收金额
        float zyratio = utils.productPlanKsjezy(formmain0617.getField0003());
        float wbratio = utils.productPlanKsjewb(formmain0617.getField0003());
        BigDecimal je = utils.productPlanHtksje(formmain0617.getField0003());
        mainTable.put("ksjezy", utils.ksjezy(je, new BigDecimal(zyratio))); // 合同可收金额自营
        mainTable.put("ksjenb", utils.ksjenb(je, new BigDecimal(zyratio), new BigDecimal(wbratio))); // 合同可收金额内部
        mainTable.put("ksjewb", utils.ksjezy(je, new BigDecimal(wbratio))); // 合同可收金额外部

        Map<String, Object> data = new HashMap<>();
        data.put("identifying", PRODUCT_PLAN);
        data.put("mainTable", mainTable);
        data.put("detail1", arr);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);
        return container;
    }

    private void recordFeedBackFormmain_0617(Formmain_0617 formmain0617, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0617_push_record formmain0617PushRecord = new Formmain_0617_push_record();
        formmain0617PushRecord.setId(formmain0617.getID());
        formmain0617PushRecord.setField0003(formmain0617.getField0003());
        formmain0617PushRecord.setLog(apiResult);
        formmain0617PushRecord.setJson_data(jsonData);

        if (successful) {
            formmain0617PushRecord.setStatus(1);
        } else {
            formmain0617PushRecord.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0617(formmain0617PushRecord);
    }

    /** 计量应收 **/
    public void pushEstimateAccountsData(Timestamp lock) throws JsonProcessingException {
        List<Formmain_0265> data = formmainMapper.getEstimateAccountsData(lock);
        logger.info("【开始推送计量应收】【数据起始时间】【" + lock + "】【推送数量】【" + data.size() + "条】");
        String token = getToken();
        updateEstimateAccountsLock();
        int i = 0;
        for (Formmain_0265 item : data) {
            try {
                logger.info("【计量应收】【index:" + i + "】【表ID: " + item.getId() + "】");
                Map<String, Object> bodyData = buildEstimateAccountsBody(item);
                String body = mapper.writeValueAsString(bodyData);
                Map<String, Object> result = httpRestService.post(body, token);
                recordFeedBackFormmain_0265(item, result);
                i += 1;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Map<String, Object> buildEstimateAccountsBody(Formmain_0265 formmain0265) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("sqr", "43100971"); // 申请人
        if (formmain0265.getField0060() == null) {
            mainTable.put("tdrq", "1900-01-01"); // 申请日期
        } else {
            mainTable.put("tdrq", formmain0265.getField0060().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); // 申请日期
        }
        mainTable.put("xzht", utils.realhtbh(formmain0265.getField0110())); // 合同编号
        mainTable.put("jskh", utils.jskh0159(formmain0265.getField0110())); // 结算客户
        mainTable.put("htzy", utils.realhtzy(formmain0265.getField0110())); // 合同专业
        mainTable.put("ywkm", utils.realywkm(formmain0265.getField0110())); // 业务科目
        mainTable.put("zxbm", utils.zxbm0474(formmain0265.getField0110())); // 执行部门
        mainTable.put("srqrff", utils.productPlanSrqrff(formmain0265.getField0110())); // 收入确认方法
        mainTable.put("rzbm", utils.rzbm0159(formmain0265.getField0110())); // 入账部门
        mainTable.put("xmzxzt", utils.xmzxzt0159(formmain0265.getField0110())); // 项目执行状态
        mainTable.put("htzt", utils.planhtzt(utils.getEnumString(utils.collectplanhtzt(formmain0265.getField0110())))); // 合同状态
        mainTable.put("htyssqje", formmain0265.getField0157()); // 合同应收申请金额
        mainTable.put("bqysqrje", formmain0265.getField0062()); // 本期应收确认金额
//        mainTable.put("jzsqljqrje", ""); // 截止上期累计确认金额
        if (formmain0265.getField0060() == null) {
            mainTable.put("qrysrq", "1900-01-01"); // 确认应收日期
        } else {
            mainTable.put("qrysrq", formmain0265.getField0060().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); // 确认应收日期
        }
        mainTable.put("zt", "1"); // 状态
        mainTable.put("qrysz", formmain0265.getField0062()); // 确认应收值
        mainTable.put("zfzs", utils.zfzs0265(formmain0265)); // 支付证书
        mainTable.put("htmc", formmain0265.getField0007()); // 合同名称
        mainTable.put("bklx", "设计"); // 板块类型
        mainTable.put("sklhzb", utils.sklhzb0159(formmain0265.getField0110())); // 收款量化指标
        mainTable.put("yslx", 2); // 应收类型
        mainTable.put("qyzt", "43100776"); // 签约主体
        mainTable.put("SourceBillID", "seeyon," + formmain0265.getId());


        Map<String, Object> map = new HashMap<>();
        map.put("identifying", ESTIMATE_ACCOUNTS);
        map.put("mainTable", mainTable);

        Map<String, Object> container = new HashMap<>();
        container.put("data", map);
        return container;
    }

    private Map<String, Object> buildEstimateAccountsBodyWtxy(Formmain_0265 formmain0265, Formmain_0777 formmain0777) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("SourceBillID", "seeyon," + formmain0265.getId() + formmain0777.getID());
        BigDecimal ratio = formmain0777.getField0043().divide(new BigDecimal(100)); // 委托协议占比
        mainTable.put("sqr", "43100971"); // 申请人
        if (formmain0265.getField0060() == null) {
            mainTable.put("tdrq", "1900-01-01"); // 申请日期
        } else {
            mainTable.put("tdrq", formmain0265.getField0060().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); // 申请日期
        }
        mainTable.put("xzht", formmain0777.getField0078()); // 合同编号
        mainTable.put("jskh", utils.jskh0159(formmain0265.getField0110())); // 结算客户
        mainTable.put("htzy", utils.realhtzy(formmain0265.getField0110())); // 合同专业
        mainTable.put("ywkm", utils.realywkm(formmain0265.getField0110())); // 业务科目
        mainTable.put("zxbm", utils.zxbm0474(formmain0265.getField0110())); // 执行部门
        mainTable.put("srqrff", utils.productPlanSrqrff(formmain0265.getField0110())); // 收入确认方法
        mainTable.put("rzbm", utils.rzbm0159(formmain0265.getField0110())); // 入账部门
        mainTable.put("xmzxzt", utils.xmzxzt0159(formmain0265.getField0110())); // 项目执行状态
        mainTable.put("htzt", utils.planhtzt(utils.getEnumString(utils.collectplanhtzt(formmain0265.getField0110())))); // 合同状态
        mainTable.put("htyssqje", formmain0265.getField0157().multiply(ratio)); // 合同应收申请金额
        mainTable.put("bqysqrje", formmain0265.getField0062().multiply(ratio)); // 本期应收确认金额
//        mainTable.put("jzsqljqrje", ""); // 截止上期累计确认金额
        if (formmain0265.getField0060() == null) {
            mainTable.put("qrysrq", "1900-01-01"); // 确认应收日期
        } else {
            mainTable.put("qrysrq", formmain0265.getField0060().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); // 确认应收日期
        }
        mainTable.put("zt", "1"); // 状态
        mainTable.put("qrysz", formmain0265.getField0062().multiply(ratio)); // 确认应收值
        mainTable.put("zfzs", utils.zfzs0265(formmain0265)); // 支付证书
        mainTable.put("htmc", formmain0265.getField0007()); // 合同名称
        mainTable.put("bklx", "设计"); // 板块类型
        mainTable.put("sklhzb", utils.sklhzb0159(formmain0265.getField0110())); // 收款量化指标
        mainTable.put("yslx", 2); // 应收类型
        mainTable.put("qyzt", "43100776"); // 签约主体


        Map<String, Object> map = new HashMap<>();
        map.put("identifying", ESTIMATE_ACCOUNTS);
        map.put("mainTable", mainTable);

        Map<String, Object> container = new HashMap<>();
        container.put("data", map);
        return container;
    }

    private void recordFeedBackFormmain_0265(Formmain_0265 formmain0265, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0159_push_record formmain0159PushRecord = new Formmain_0159_push_record();
        formmain0159PushRecord.setId(formmain0265.getId());
        formmain0159PushRecord.setField0008("");
        formmain0159PushRecord.setLog(apiResult);
        formmain0159PushRecord.setJson_data(jsonData);

        if (successful) {
            formmain0159PushRecord.setStatus(1);
        } else {
            formmain0159PushRecord.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0159(formmain0159PushRecord);
    }

    /** 产值上报 **/
    public void pushProductRealData(Timestamp lock) throws JsonProcessingException {
        List<Formmain_0765> data = formmainMapper.getFormmain0765(lock);
        logger.info("【开始推送产值上报】【数据起始时间】【" + lock + "】【推送数量】【" + data.size() + "条】");
        String token = getToken();
//        updateProductRealLock();
        int i = 0;
        for (Formmain_0765 item : data) {
            logger.info("【产值上报】【index:" + i + "】【表ID: " + item.getId() + "】");
            Map<String, Object> bodyData = buildProductRealBody(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result = httpRestService.post(body, token);
            recordFeedBackFormmain_0255(item, result);
            i += 1;
        }
    }

    public Map<String, Object> buildProductRealBody(Formmain_0765 formmain0765) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("SourceBillID", "seeyon," + formmain0765.getId());
        mainTable.put("sqr", utils.getMemberOA(formmain0765.getField0064())); // 申请人
        mainTable.put("tdrq", formmain0765.getField0065().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); // 申请日期
        mainTable.put("htbh", formmain0765.getField0005()); // 合同编号

        List<Map<String, Object>> lst = new LinkedList<>();
        Map<String, Object> detail1 = new HashMap<>();
        detail1.put("SourceBillID", "seeyon," + formmain0765.getId());
        detail1.put("czrq", formmain0765.getField0090().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); // 产值日期
        detail1.put("htzy", utils.realhtzy(formmain0765.getField0004())); // 合同专业

        detail1.put("zxbm", utils.getDeptCode(formmain0765.getField0011())); // 执行部门

        BigDecimal total = formmain0765.getField0019();
        BigDecimal zy = utils.bqqrzycz0765(total, formmain0765);
        BigDecimal nb = utils.bqqrnbhzcz0765(total, formmain0765);
        BigDecimal wb = utils.bqqrwbhzcz0765(total, formmain0765);
        detail1.put("htzysl", 0.06);
        detail1.put("bqqrczbhs", total.divide(new BigDecimal(1.06), RoundingMode.UP));
        detail1.put("bqqrcz", total); // 本期确认产值
        detail1.put("bqqrzycz", zy);
        detail1.put("bqqrnbhzcz", nb);
        detail1.put("bqqrwbhzcz", wb);

        BigDecimal total1 = formmain0765.getField0017();
        BigDecimal zy1 = utils.jzsqljcz0765(total1, formmain0765);
        BigDecimal wb1 = utils.jzsqljwbhzcz0765(total1, formmain0765);
        BigDecimal nb1 = utils.jzsqljnbhzcz0765(total1, formmain0765);
        detail1.put("jzsqljcz", total1);
        detail1.put("jzsqljzycz", zy1);
        detail1.put("jzsqljwbhzcz", wb1);
        detail1.put("bqzycz", zy);
        detail1.put("bqzybl", utils.productRealbqzybl(zy, total));
        detail1.put("bqqrnbhzbl", utils.productRealbqzybl(nb, total));
        detail1.put("bqqrwbhzbl", utils.productRealbqzybl(wb, total));
        detail1.put("sjqrsr", total.divide(new BigDecimal("1.06"), RoundingMode.DOWN));
        detail1.put("jdjd1", formmain0765.getField0013());

        lst.add(detail1);
        Object[] arr = (Object[]) lst.toArray();

        mainTable.put("htlb", utils.productRealhtlb(formmain0765.getField0004()));
        mainTable.put("xmxx", formmain0765.getField0004());
        mainTable.put("htmc", utils.productPlanHtmc(formmain0765.getField0004()));
        mainTable.put("qyjey", utils.productRealqyjey(formmain0765.getField0004()));
        mainTable.put("qyjedx", utils.productRealqyjey(formmain0765.getField0004()));
        mainTable.put("qyjebhs", utils.productRealqyjebhs(formmain0765.getField0004()));
        mainTable.put("qyse", utils.productRealqyse(formmain0765.getField0004()));
        mainTable.put("zxhtje", utils.productRealzxhtje(formmain0765.getField0004()));
        mainTable.put("zxhtjebhs", utils.productRealzxhtjebhs(formmain0765.getField0004()));
        BigDecimal total2 = utils.productRealhtksje(formmain0765.getField0004());
        mainTable.put("htksje", total2);
        mainTable.put("ksjezy", utils.ksjezy0765(total2, formmain0765));
        mainTable.put("ksjenb", utils.ksjenb0765(total2, formmain0765));
        mainTable.put("ksjewb", utils.ksjewb0765(total2, formmain0765));

        List<CtpFile0256> files = formmainMapper.getFile0256(formmain0765.getField0033());
        if (files != null) {
            List<Map<String, Object>> attachedFiles = new LinkedList<>();
            for (CtpFile0256 item : files) {
                Map<String, Object> file = new HashMap<>();
                if (item != null) {
                    file.put("name", item.getFILENAME());
                    file.put("content", utils.getFileUrl0256(item));

                    attachedFiles.add(file);
                }
            }

            Object[] arr2 = (Object[]) attachedFiles.toArray();

            mainTable.put("xgfj", arr2);
        } else {
            mainTable.put("xgfj", "");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("identifying", PRODUCT_REAL);
        map.put("mainTable", mainTable);
        map.put("detail1", arr);

        Map<String, Object> container = new HashMap<>();
        container.put("data", map);
        return container;
    }

    public Map<String, Object> buildProductRealBodyWtxy(Formmain_0765 formmain0765, Formmain_0777 formmain0777) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("SourceBillID", "seeyon," + formmain0765.getId() + formmain0777.getID());
        BigDecimal ratio = formmain0777.getField0043().divide(new BigDecimal(100)); // 委托协议占比
        mainTable.put("sqr", utils.getMemberOA(formmain0765.getField0064())); // 申请人
        mainTable.put("tdrq", formmain0765.getField0065().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); // 申请日期
        mainTable.put("htbh", formmain0777.getField0078()); // 合同编号

        List<Map<String, Object>> lst = new LinkedList<>();
        Map<String, Object> detail1 = new HashMap<>();
        detail1.put("SourceBillID", "seeyon," + formmain0765.getId() + formmain0777.getID());
        detail1.put("czrq", formmain0765.getField0090().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))); // 产值日期
        detail1.put("htzy", utils.realhtzy(formmain0765.getField0004())); // 合同专业
        detail1.put("zxbm", utils.getDeptCode(formmain0765.getField0011())); // 执行部门

        BigDecimal total = formmain0765.getField0019();
        BigDecimal zy = utils.bqqrzycz0765(total, formmain0765);
        BigDecimal nb = utils.bqqrnbhzcz0765(total, formmain0765);
        BigDecimal wb = utils.bqqrwbhzcz0765(total, formmain0765);
        detail1.put("bqqrcz", total.multiply(ratio)); // 本期确认产值
        detail1.put("bqqrzycz", zy.multiply(ratio));
        detail1.put("bqqrnbhzcz", nb.multiply(ratio));
        detail1.put("bqqrwbhzcz", wb.multiply(ratio));

        BigDecimal total1 = formmain0765.getField0017();
        BigDecimal zy1 = utils.jzsqljcz0765(total1, formmain0765);
        BigDecimal wb1 = utils.jzsqljwbhzcz0765(total1, formmain0765);
        BigDecimal nb1 = utils.jzsqljnbhzcz0765(total1, formmain0765);
        detail1.put("jzsqljcz", total1.multiply(ratio));
        detail1.put("jzsqljzycz", zy1.multiply(ratio));
        detail1.put("jzsqljwbhzcz", wb1.multiply(ratio));
        detail1.put("bqzycz", zy.multiply(ratio));
        detail1.put("bqzybl", utils.productRealbqzybl(zy, total).multiply(ratio));
        detail1.put("bqqrnbhzbl", utils.productRealbqzybl(nb, total).multiply(ratio));
        detail1.put("bqqrwbhzbl", utils.productRealbqzybl(wb, total).multiply(ratio));
        detail1.put("sjqrsr", total.divide(new BigDecimal("1.06"), RoundingMode.DOWN));
        detail1.put("jdjd1", formmain0765.getField0013());
        detail1.put("bqqrczbhs", total.multiply(ratio).multiply(new BigDecimal("0.94")));

        lst.add(detail1);
        Object[] arr = (Object[]) lst.toArray();

        mainTable.put("htlb", utils.productRealhtlb(formmain0765.getField0004()));
        mainTable.put("xmxx", formmain0765.getField0004());
        mainTable.put("htmc", utils.productPlanHtmc(formmain0765.getField0004()));
        mainTable.put("qyjey", utils.productRealqyjey(formmain0765.getField0004()).multiply(ratio));
        mainTable.put("qyjedx", utils.productRealqyjey(formmain0765.getField0004()).multiply(ratio));
        mainTable.put("qyjebhs", utils.productRealqyjebhs(formmain0765.getField0004()).multiply(ratio));
        mainTable.put("qyse", utils.productRealqyse(formmain0765.getField0004()).multiply(ratio));
        mainTable.put("zxhtje", utils.productRealzxhtje(formmain0765.getField0004()).multiply(ratio));
        mainTable.put("zxhtjebhs", utils.productRealzxhtjebhs(formmain0765.getField0004()).multiply(ratio));
        BigDecimal total2 = utils.productRealhtksje(formmain0765.getField0004());
        mainTable.put("htksje", total2.multiply(ratio));
        mainTable.put("ksjezy", utils.ksjezy0765(total2, formmain0765).multiply(ratio));
        mainTable.put("ksjenb", utils.ksjenb0765(total2, formmain0765).multiply(ratio));
        mainTable.put("ksjewb", utils.ksjewb0765(total2, formmain0765).multiply(ratio));

        List<CtpFile0256> files = formmainMapper.getFile0256(formmain0765.getField0033());
        if (files != null) {
            List<Map<String, Object>> attachedFiles = new LinkedList<>();
            for (CtpFile0256 item : files) {
                Map<String, Object> file = new HashMap<>();
                if (item != null) {
                    file.put("name", item.getFILENAME());
                    file.put("content", utils.getFileUrl0256(item));

                    attachedFiles.add(file);
                }
            }

            Object[] arr2 = (Object[]) attachedFiles.toArray();

            mainTable.put("xgfj", arr2);
        } else {
            mainTable.put("xgfj", "");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("identifying", PRODUCT_REAL);
        map.put("mainTable", mainTable);
        map.put("detail1", arr);

        Map<String, Object> container = new HashMap<>();
        container.put("data", map);
        return container;
    }

    private void recordFeedBackFormmain_0255(Formmain_0765 formmain0765, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0255_push_record formmain0255PushRecord = new Formmain_0255_push_record();
        formmain0255PushRecord.setId(formmain0765.getId());
        formmain0255PushRecord.setLog(apiResult);
        formmain0255PushRecord.setJson_data(jsonData);

        if (successful) {
            formmain0255PushRecord.setStatus(1);
        } else {
            formmain0255PushRecord.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0255(formmain0255PushRecord);
    }


    /***
     * 产值上报历史
     */
    public void chanzhishangbao() throws JsonProcessingException {
        List<Formmain_0533> data = formmainMapper.getEstimateAccountsDataHistory();
        logger.info("【开始推送产值上报】【数据起始时间】【】【推送数量】【" + data.size() + "条】");
        String token = getToken();

        int i = 0;
        for (Formmain_0533 item : data) {
            logger.info("【产值上报】【index:" + i + "】【表ID: " + item.getID() + "】");
            Map<String, Object> bodyData = buildEstimateAccountsBodyHistory(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result = httpRestService.post(body, token);
            recordFeedBackFormmain_0533(item, result);
            i += 1;
        }
    }

    public Map<String, Object> buildEstimateAccountsBodyHistory(Formmain_0533 formmain0533) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("sqr", "43101176"); // 申请人 王云
        mainTable.put("tdrq", utils.tdrqriqi(formmain0533.getField0003(), formmain0533.getField0001())); // 申请日期
        mainTable.put("htbh", utils.realhtbh(formmain0533.getField0001())); // 合同编号
        mainTable.put("SourceBillID", "seeyon," + formmain0533.getID());

        List<Map<String, Object>> lst = new LinkedList<>();
        Map<String, Object> detail1 = new HashMap<>();
        detail1.put("czrq", utils.tdrqriqi(formmain0533.getField0003(), formmain0533.getField0001())); // 产值日期
        detail1.put("htzy", utils.realhtzy(formmain0533.getField0001())); // 合同专业
        detail1.put("zxbm", utils.productPlanZxbm(formmain0533.getField0001())); // 执行部门
        detail1.put("jdjd", formmain0533.getField0010()); // 进度节点

        BigDecimal total = utils.bqqrcz(formmain0533);
        BigDecimal zy = utils.bqqrzycz0159(total, formmain0533);
        BigDecimal wb = utils.bqqrnbhzcz0159(total, formmain0533);
        BigDecimal wgl = utils.wgl0159(total, formmain0533);
        BigDecimal nb = total.subtract(zy).subtract(wb).subtract(wgl);

        detail1.put("SourceBillID", "seeyon," + formmain0533.getID());
//        detail1.put("bqqrcz", total); // 本期确认产值
        detail1.put("bqqrcz", total); // 本期确认产值
        detail1.put("bqqrzycz", zy); // 本期确认产值自营
        detail1.put("bqqrnbhzcz", nb); // 本期确认产值内部
        detail1.put("bqqrwbhzcz", wb); // 本期确认产值外部
        detail1.put("jzsqljcz", 0); // 截止上期累计产值
        detail1.put("jzsqljzycz", 0); // 截止上期累计自营产值
        detail1.put("jzsqljwbhzcz", 0); // 截止上期累计外部合作产值
        detail1.put("jzsqljnbhzcz", 0); // 截止上期累计内部合作产值
        detail1.put("bqzycz", zy); // 本期自营产值
        detail1.put("bqzydz", 0); // 本期自营调整
        detail1.put("bqnbhzcz", 0); // 本期内部合作产值
        detail1.put("bqnbhzdz", 0); // 本期内部合作调整
        detail1.put("wbhzcz", 0); // 外部合作产值
        detail1.put("wbhzczdz", 0); // 外部合作产值调整
        detail1.put("bqzybl", utils.productRealbqzybl(zy, total)); // 本期自营比例
        detail1.put("bqqrnbhzbl", 0); // 本期确认内部合作比例
        detail1.put("bqqrwbhzbl", 0); // 本期确认外部合作比例
        detail1.put("sjqrsr", total.divide(new BigDecimal("1.06"), RoundingMode.DOWN)); // 设计确认收入
        detail1.put("jdjd1", formmain0533.getField0011()); // 节点进度（%）

        lst.add(detail1);
        Object[] arr = (Object[]) lst.toArray();

        mainTable.put("htlb", utils.htlb(formmain0533.getField0001())); // 合同类别
        mainTable.put("xmxx", formmain0533.getField0011()); // 项目信息
        mainTable.put("htmc", utils.productPlanHtmc(formmain0533.getField0001())); // 合同名称
        mainTable.put("qyjey", utils.planqyjey(formmain0533.getField0001())); // 签约金额(元)
        mainTable.put("qyjedx", utils.planqyjey(formmain0533.getField0001())); // 签约金额大写
        mainTable.put("qyjebhs", utils.planqyjebhs(formmain0533.getField0001())); // 签约金额（不含税）
        mainTable.put("qyse", utils.planqyse(formmain0533.getField0001())); // 签约税额
        mainTable.put("zxhtje", utils.zxhtje(formmain0533.getField0001())); // 最新合同金额
        mainTable.put("zxhtjebhs", utils.zxhtjebhs(formmain0533.getField0001())); // 最新合同金额 （不含税）
        mainTable.put("htksje", utils.productRealhtksje(formmain0533.getField0001())); // 合同可收金额
        mainTable.put("ksjezy", utils.ksjezy0533(formmain0533)); // 可收金额（自营）
        mainTable.put("ksjenb", utils.ksjenb0533(formmain0533)); // 可收金额（内部）
        mainTable.put("ksjewb", utils.ksjewb0533(formmain0533)); // 可收金额（外部）
        mainTable.put("xgfj", utils.zfzs0159(formmain0533.getField0013()));

        Map<String, Object> map = new HashMap<>();
        map.put("identifying", PRODUCT_REAL);
        map.put("mainTable", mainTable);
        map.put("detail1", arr);

        Map<String, Object> container = new HashMap<>();
        container.put("data", map);
        return container;
    }

    public void recordFeedBackFormmain_0533(Formmain_0533 formmain0533, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0533_push_record formmain0533PushRecord = new Formmain_0533_push_record();
        formmain0533PushRecord.setId(formmain0533.getID());
        formmain0533PushRecord.setLog(apiResult);
        formmain0533PushRecord.setJson_data(jsonData);

        if (successful) {
            formmain0533PushRecord.setStatus(1);
        } else {
            formmain0533PushRecord.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0533(formmain0533PushRecord);
    }

    /***
     * 收款认领历史
     */
    public void pushCollectionRealHistory() throws JsonProcessingException {
        List<CollectReal_0027> data = formmainMapper.getCollectionRealData();
        logger.info("【开始推送收款认领】【数据起始时间】【】【推送数量】【" + data.size() + "条】");
        String token = getToken();
        int i = 0;
        for (CollectReal_0027 item : data) {
            logger.info("【收款认领】【index:" + i + "】【表ID: "+ item.getId() +"】");
            Map<String, Object> bodyData = buildCollectionRealBodyHistory(item);
            String body = mapper.writeValueAsString(bodyData);
            Map<String, Object> result = httpRestService.post(body, token);
            recordFeedBackFormmain_0506History(item, result);
            i += 1;
        }
    }

    public Map<String, Object> buildCollectionRealBodyHistory(CollectReal_0027 collectReal0027) {
        Map<String, Object> mainTable = new HashMap<>();
        mainTable.put("APPID", APPID);
        mainTable.put("SourceBillID", "seeyon," + collectReal0027.getId());
        mainTable.put("skdw", utils.getDeptCode(collectReal0027.getSkdw())); // 收款单位
        mainTable.put("fkdw", collectReal0027.getFkdw()); // 付款单位
        mainTable.put("dyht", collectReal0027.getHtbh()); // 对应合同
        mainTable.put("htbh", collectReal0027.getHtbh()); // 合同编号
        mainTable.put("htzy", collectReal0027.getHtzy()); // 合同专业
        mainTable.put("ywkm", collectReal0027.getYwkm()); // 业务科目
        mainTable.put("zxbm", utils.getDeptCode(collectReal0027.getZxbm())); // 执行部门
        mainTable.put("jljsje", isNullNumber(collectReal0027.getJe())); // 计量结算金额
        mainTable.put("je", isNullNumber(collectReal0027.getJe())); // 金额
        mainTable.put("skrq", collectReal0027.getSkrq()); // 收款日期
        mainTable.put("rzbm", utils.getDeptCode(collectReal0027.getRzbm())); // 入账部门
        mainTable.put("szfb", utils.getDeptCode(collectReal0027.getSzfb())); // 所属分部

        Map<String, Object> data = new HashMap<>();
        data.put("identifying", COLLECTION_REAL);
        data.put("mainTable", mainTable);

        Map<String, Object> container = new HashMap<>();
        container.put("data", data);
        return container;
    }

    private void recordFeedBackFormmain_0506History(CollectReal_0027 collectReal0027, Map<String, Object> result) {
        Boolean successful = (Boolean) result.get("success");
        String apiResult = (String) result.get("apiResult");
        String jsonData = (String) result.get("jsonData");

        Formmain_0506_push_record formmain0506PushRecord = new Formmain_0506_push_record();
        formmain0506PushRecord.setId(collectReal0027.getId());
        formmain0506PushRecord.setField0023("");
        formmain0506PushRecord.setLog(apiResult);
        formmain0506PushRecord.setJson_data(jsonData);

        if (successful) {
            formmain0506PushRecord.setStatus(1);
        } else {
            formmain0506PushRecord.setStatus(0);
        }
        pushFeedBackRecordMapper.recordFormmain_0506(formmain0506PushRecord);
    }


    private String getToken() {
//                Map<String, Object> token = mainTest.testGetoken("http://192.168.22.89");
        Map<String, Object> token = mainTest.testGetoken("http://192.168.22.59:80");
        return (String)token.get("token");
    }

    private String isNullString(String value) {
        if (value == null || value.equals("")) {
            return "无";
        }
        return value;
    }

    private String isNullStr(String value) {
        if (value == null || value.equals("")) {
            return "";
        }
        return value;
    }

    private String isNullDate(String value) {
        if (value == null || value.equals("")) {
            return "1900-01-01";
        }
        return value;
    }

    private String isNullYear(String value) {
        if (value == null || value.equals("")) {
            return "1900";
        }
        return value;
    }

    private BigDecimal isNullNumber(BigDecimal value) {
        if (Objects.equals(value, "") || value == null) {
            return new BigDecimal(0);
        }
        return value;
    }

    private String getDate(LocalDateTime time) {
        if (time == null) {
            return "1900-01-01";
        }
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return df.format(time);
    }

    /***
     * @description
     *     获取OA工号
     *
     * @param ids
     * @return java.lang.String
     * @date 2023/11/27 09:24:11
     */
    private String getPersonOaCode(String ids) {
        StringBuilder result = new StringBuilder();
        if (ids == null || ids.equals("")) {
            return "";
        }
        String[] persons = ids.split(",");
        String[] codes = new String[persons.length];
        for (int i =0; i < persons.length; i++) {
            codes[i] = formmainMapper.getPersonOACode(persons[i]);
        }
        return StringUtils.join(codes, ',');
    }

    private String getOADeptCode(String ids) {
        StringBuilder result = new StringBuilder();
        if (ids == null || ids.equals("")) {
            return "";
        }
        String[] persons = ids.split(",");
        for (String item : persons) {
            result.append(formmainMapper.getOADeptCode(item));
        }
        return result.toString();
    }

    private TimeLock getLock() {
        try {
            timeLockMapper.getLock();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return timeLockMapper.getLock();
    }

    private void updateSupplierLock() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        timeLockMapper.updateSupplierLock(date);
    }

    private void updateConsumerLock() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        timeLockMapper.updateCustomerLock(date);
    }

    private void updateInvoiceLock() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        timeLockMapper.updateInvoiceLock(date);
    }

    private void updateProjectLock() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        timeLockMapper.updateProjectLock(date);
    }

    private void updateCollectContractLock() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        timeLockMapper.updateCollectContractLock(date);
    }

    private void updatePayContract() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        timeLockMapper.updatePayContractLock(date);
    }

    private void updateCollectionRealLock() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        timeLockMapper.updateCollectionRealLock(date);
    }

    private void updateCollectionPlanLock() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        timeLockMapper.updateCollectionPlanLock(date);
    }

    private void updateProductPlanLock() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        timeLockMapper.updateProductPlanLock(date);
    }

    private void updateProductRealLock() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        timeLockMapper.updateProductRealLock(date);
    }

    private void updateEstimateAccountsLock() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        timeLockMapper.updateEstimateAccountsLock(date);
    }

    private void updateCollectContractLock2() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        timeLockMapper.updateCollectContractLock2(date);
    }

    private void updatePayContractLock2() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        timeLockMapper.updatePayContractLock2(date);
    }
}
