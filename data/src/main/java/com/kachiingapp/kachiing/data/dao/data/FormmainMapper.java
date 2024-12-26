package com.kachiingapp.kachiing.data.dao.data;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.kachiingapp.kachiing.domain.entity.gongyingshang.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName: Formmain_0277Mapper
 * @Auther: liujunhan
 * @Date: 2023/11/6 13:41
 * @Description:
 */

@Mapper
@Repository
@DS("master")
public interface FormmainMapper {

    /***
     * 将sqlsession切换到mysql数据库。
     * 在多数据源场景下，开启事务后，会导致数据源切换失败。
     * 所以在执行带有事务的方法前，先将数据源从oracle切换到mysql
     */
    void switchToMysql();

    // 供应商
    List<Formmain_0277> getSupplierData(Timestamp lock);

    // 客户
    List<Formmain_0037> getCustomerData(Timestamp lock);

    // 项目立项
    List<Formmain_0139> getProjectData(Timestamp lock);

    // 收款合同
    List<Formmain_0027> getCollectContractData(Timestamp lock);

    List<Formmain_0777> getCollectContractData2(Timestamp lock);

    // 合同开票
    List<Formmain_0117> getInvoiceData(Timestamp lock);

    // 付款合同
    List<Formmain_0451> getPayContractData(Timestamp lock);
    List<Formmain_0542> getPayContractData2(Timestamp lock);

    // 收款认领
    List<Formmain_0506> getCollectionReal(Timestamp lock);

    // 收款计划
    List<Formmain_0240> getCollectionPlanData(Timestamp lock);

    // 产值计划
    List<Formmain_0617> getProductPlanData(Timestamp lock);

    // 产值上报
    List<Formmain_0765> getFormmain0765(Timestamp lock);

    // 产值上报
    List<String> getProductRealID(Timestamp lock);

    // 产值上报
    List<Formmain_0255> getFormson0255(String formmainID);

    // 合同收款附件
    List<CtpFile0027> collectContract0027(String id);

    // 附件
    CtpFile0027 getCtpFile(String fileUrl);

    // 收款委托协议附件
    CtpFile0027 getCtpFileWtxy(String fileUrl);

    // 待认领款项插入formmain_0717中
    int insertIntoFormmain_0717(Formmain_0717 formmain0717);

    List<Formmain_0265> getEstimateAccountsData(Timestamp lock);

    List<Formmain_0533> getEstimateAccountsDataHistory();

    /****/

    // 获取OA工号
    String getPersonOACode(String orgMemberId);

    // 获取OA部门ID
    String getOADeptCode(String deptId);

    // 获取表单下拉框枚举值
    Integer getEnumValue(String id);

    // 获取下拉框枚举显示文字
    String getEnumString(String id);

    // 评审日期
    String tdrq(String id);

    String getSupplierFID(String province);

    String getCustomerFID(String province);

    String shenqingriqi(String contractId);

    String sktj(String contractId);

    // 乙方
    String jfgys(String name);

    // 收款银行
    String skyx(String name);

    // 收款账号
    String yxzh(String name);

    // 收款省
    String skfs(String name);

    String skfxs(String name);

    String getCustomerSSN(String name);

    String getBankId(String name);

    String xmfgld(String id);

    String glalht(String id);

    // 收款量化指标
    String sklhzb(String id);

    // 专业拆分，申请人
    String majorsqr(String id);

    // 合同专业
    String realhtzy(String id);

    // 计量结算金额
    BigDecimal jljsje(String id);

    // 收款认领，新合同编号
    String realhtbh(String id);

    // 收款计划，登记日期
    String collectplantdrq(String id);

    // Formmain_0265条件时间
    String collectplansj(String id);

    // 项目执行状态
    String xmzxzt(String id);

    // 合同状态
    String collectplanhtzt(String id);

    // 本期计划回款
    BigDecimal bqjhhk(String id);

    // 应支付进度
    Float ljbl(String id);

    String htlb(String id);

    String planqyjey(String id);

    String planqyjebhs(String id);

    String planqyse(String id);

    String zxhtje(String id);

    String zxhtjebhs(String id);

    String htksje(String id);

    Float ksjezy(String id);

    Float ksjewb(String id);

    String invoicehtmc(String id);

    String hetongzhuanye(String id);

    String rzbm(String id);

    String xiangmuzhuangtai(String id);

    void recordFailedId(String id);

    List<CollectReal_0027> getCollectionRealData();

    String getCompanyIdFromCode(String code);

    BigInteger getFormmain_0717MaxID();

    // 根据合同id，获取收款合同的核算方式
    String hsfs0027(String id);

    String hsfs0451(String id);

    List<Formson_0243> getAccumulatedCollection(String id);

    String getProductPlanSqr(String id);

    String productPlanHtbh(String id);

    String productPlanSj(String id);

    String productPlanHtzy(String id);

    String productPlanSrqrff(String id);

    String productPlanZxbm(String id);

    String productPlanXmzxzt(String id);

    String productPlanHtzt(String id);

    List<Formson_0618> getAccumulatedProduct(String id);

    BigDecimal productPlanQyjey(String id);

    String productPlanHtlb(String id);

    String productPlanHtmc(String id);

    String productPlanQyjebhs(String id);

    String productPlanQyse(String id);

    String productPlanZxhtje(String id);

    BigDecimal productPlanHtksje(String id);

    Float productPlanKsjezy(String id);

    Float productPlanKsjewb(String id);

    String productRealhtbh(String id);

    String productRealhtzy(String id);

    BigDecimal productReal0225(String id);

    BigDecimal productReal0604(String id);

    BigDecimal productReal0602(String id);

    BigDecimal productReal0477(String id);

    BigDecimal productReal592(String id);
    BigDecimal productReal603(String id);

    String productReal0260(String id);

    String productRealhtlb(String id);

    String productRealxmxx(String id);

    String productRealhtmc(String id);

    BigDecimal productRealqyjey(String id);

    BigDecimal productRealqyjebhs(String id);

    BigDecimal productRealqyse(String id);

    BigDecimal productRealzxhtje(String id);
    BigDecimal productRealzxhtjebhs(String id);

    BigDecimal productRealhtksje(String id);

    String productRealsqrszbm(String id);

    BigDecimal productRealwgl(String id);

    void updateProductRealField0055(String id);

    CtpFileChild0256 getFileid0159(String field0010);

    CtpFileChild0256 getFileid0055(String field0010);

    CtpFileChild0256 getFileid0063(String field0010);

    List<CtpFile0256> getFile0256(String id);

    CtpFile0256 getAttachedFile0256(String fileurl);

    String getField0022(String id);

    String rzbm0159(String id);

    String xmzxzt0159(String id);

    BigDecimal htyssqje0159(String id);

    BigDecimal jzsqljqrje0159(String id);

    LocalDateTime qrysrq0159(String id);

    String xzht0159(String id);

    String tdrq00159(String field0001);

    String htbh00195(String field0001);

    String zxbm0474(String id);

    String zfzs0265(Formmain_0265 formmain0265);

    String zfzs0265History(Formmain_0265 formmain0265);

    BigDecimal bcxyje0351(Formmain_0027 formmain0027);

    String tdrqweituoxieyi(Formmain_0777 formmain0777);

    String htjydmsweituoxieyi(Formmain_0777 formmain0777);

    String jylxweituoxieyi(Formmain_0777 formmain0777);

    String htqdrqweituoxieyi(Formmain_0777 formmain0777);

    String htqxbweituoxieyi(Formmain_0777 formmain0777);

    String htqxweituoxieyi(Formmain_0777 formmain0777);

    String cbbmweituoxieyi(Formmain_0777 formmain0777);

    String htzxrweituoxieyi(Formmain_0777 formmain0777);

    String sktjweituoxieyi(Formmain_0777 formmain0777);

    String hsfsweituoxieyi(Formmain_0777 formmain0777);

    String sklhzbweituoxieyi(Formmain_0777 formmain0777);

    String djrqweituoxieyi(Formmain_0777 formmain0777);
    String djrqweituoxieyi2(Formmain_0777 formmain0777);

    List<CtpFile0027> htfjweituoxieyi(String id);
    List<CtpFile0027> htfjweituoxieyifukuan(String id);

    String tdrqfukuan(Formmain_0542 formmain0542);

    String htqdrqfukuan(Formmain_0542 formmain0542);

    String htqxbfukuan(Formmain_0542 formmain0542);
    String htqxfukuan(Formmain_0542 formmain0542);
    String sktjfukuan(Formmain_0542 formmain0542);
    String hsfsfukuan(Formmain_0542 formmain0542);
    String continuousfukuan(Formmain_0542 formmain0542);
    String xzsjhsxmfukuan(Formmain_0542 formmain0542);
    String skdwfukuan(Formmain_0542 formmain0542);
    String skyxfukuan(Formmain_0542 formmain0542);
    String yxzhfukaun(Formmain_0542 formmain0542);
    String skfsfukuan(Formmain_0542 formmain0542);
    String skfxsfukuan(Formmain_0542 formmain0542);

    BigDecimal bqjhhkratiolast(Formson_0618 formson0618);

    Formmain_0027 get0027(Formmain_0777 formmain0777);

    List<Formmain_0765> get0765(Formmain_0777 formmain0777);

    List<Formmain_0265> get0265(Formmain_0777 formmain0777);

    List<Formmain_0506> get0506(Formmain_0777 formmain0777);

    BigDecimal totalwtxy(Formmain_0777 formmain0777);

    List<Formmain_0240> get0240(Formmain_0777 formmain0777);
}
