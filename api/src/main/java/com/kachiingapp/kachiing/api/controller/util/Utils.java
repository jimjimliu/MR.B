package com.kachiingapp.kachiing.api.controller.util;

import com.alibaba.druid.support.monitor.annotation.MField;
import com.kachiingapp.kachiing.data.dao.data.FormmainMapper;
import com.kachiingapp.kachiing.domain.entity.gongyingshang.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @ClassName: Utils
 * @Auther: liujunhan
 * @Date: 2023/11/30 10:59
 * @Description:
 */

@Component
public class Utils {

    @Autowired
    private FormmainMapper formmainMapper;

    // 征信情况
    public String zxqkqxb(String id) {
        if (id == null) {
            return "正常";
        }
        return formmainMapper.getEnumString(id);
    }

    // 是否封存
    public String sffc(String id) {
        if (id == null) {
            return "1";
        }
        return String.valueOf(formmainMapper.getEnumValue(id));
    }

    // 纳税人类型
    public String nsrlx(String id) {
        if (id == null) {
            return "1";
        }
        String[] result = {"1", "2"};
        return result[formmainMapper.getEnumValue(id)];

    }

    // 是否关联单位
    public String sfgldw(String id) {
        if (id == null) {
            return "1";
        }
        return String.valueOf(formmainMapper.getEnumValue(id));
    }

    // 开户行所在省
    public String khxszs(String id) {
        if (id == null) {
            return "未知";
        }
        return formmainMapper.getEnumString(id);
    }

    // 开户行所在市
    public String khxszsi(String id) {
        if (id == null) {
            return "未知";
        }
        return formmainMapper.getEnumString(id);
    }

    public String sfhcnbdw(String id) {
        if (id == null) {
            return "0";
        }
        String[] result = {"1", "0"};
        return String.valueOf(result[formmainMapper.getEnumValue(id)]);
    }

    // 合同额是否预估
    public String htjydms(String id) {
        if (id == null) {
            return "0";
        }
        String[] result = {"1", "0"};
        return result[formmainMapper.getEnumValue(id)];
    }

    // 合同状态
    public String htzt(String id) {
        if (id == null) {
            return "";
        }
        String[] result = {"0", "3", "2", "6", "1", "0", "5"};

        return result[formmainMapper.getEnumValue(id)];
    }

    // 经营类型
    public String jylx(String id) {
        if (id == null) {
            return "0";
        }
        return String.valueOf(formmainMapper.getEnumValue(id));
    }

    // 是否联合体
    public String sflhtht(String id) {
        if (id == null) {
            return "";
        }
        return String.valueOf(formmainMapper.getEnumValue(id));
    }

    // 开票类型
    public String kplx(String id) {
        if (id == null) {
            return "";
        }
        String[] result = {"0", "1", "2", "3", "3", "3"};
        return String.valueOf(result[formmainMapper.getEnumValue(id)]);
    }

    // 税率
    public String slv(String id) {
        if (id == null) {
            return "";
        }
        String[] result = {"0", "1", "2", "6", "3", "4", "5"};
        return String.valueOf(result[formmainMapper.getEnumValue(id)]);
    }

    // 是否为合同开票
    public String sfwhtkp(String value) {
        if (value == null) {
            return "1";
        }
        return "0";
    }

    // 获取部门OA编号
    public String getDeptCode(String id) {
        if (id == null) {
            return "";
        }
        return formmainMapper.getOADeptCode(id);
    }

    // 获取员工OA编号
    public String getMemberOA(String id) {
        if (id == null) {
            return "";
        }
        return formmainMapper.getPersonOACode(id);
    }

    public String getEnumString(String id) {
        if (id == null) {
            return "";
        }
        return formmainMapper.getEnumString(id);
    }

    public String getEnumValue(String id) {
        if (id == null) {
            return "";
        }
        return String.valueOf(formmainMapper.getEnumValue(id));
    }

    public String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        return str.replace("-", "");
    }

    public String khxz(String id) {
        if (id == null) {
            return "0";
        }
        String[] results = {"0", "1", "2"};
        return results[formmainMapper.getEnumValue(id)];
    }

    public String hwhyslwfwmc(String id) {
        if (id == null) {
            return "50";
        }

        Map<String, String> map = new HashMap<>();
        map.put("鉴证服务*工程监理服务","0");
        map.put("鉴证咨询服务*检测费","1");
        map.put("鉴证咨询服务*试验检测费","2");
        map.put("鉴证咨询服务*桩基检测费","3");
        map.put("鉴证咨询服务*试验检测服务费","4");
        map.put("经营租赁*房屋租赁","5");
        map.put("经营租赁*车位租赁","6");
        map.put("研发和技术服务*技术服务费","7");
        map.put("鉴证咨询服务*监测费","8");
        map.put("非金属矿物制品","9");
        map.put("化学合成材料","10");
        map.put("化学试剂和助剂","11");
        map.put("鉴证咨询服务*其他鉴证服务","12");
        map.put("设计服务*工程设计服务","13");
        map.put("建筑服务*修缮服务","14");
        map.put("建筑服务*工程服务","15");
        map.put("建筑服务*其他","16");
        map.put("建筑服务*机械费","17");
        map.put("研发和技术服务*技术服务","18");
        map.put("咨询服务*会计咨询","19");
        map.put("经营租赁*不动产租赁*海沧基地租金","20");
        map.put("经营租赁*不动产租赁*集美区珩田路456号租金","21");
        map.put("货物*（根据具体货物类型开具）","22");
        map.put("生活服务*其他生活服务","23");
        map.put("经营租赁*不动产租赁","24");
        map.put("非金属矿物制品*沥青混合料","25");
        map.put("咨询服务*其他咨询服务","26");
        map.put("建筑工程机械*公共工程用机械","27");
        map.put("试验检测机械","28");
        map.put("*鉴证咨询服务*施工招标服务费","29");
        map.put("*鉴证咨询服务*监理招标服务费","30");
        map.put("经营租赁*机械租赁","31");
        map.put("经营租赁*桥检车租赁","32");
        map.put("*经纪代理服务*标书费","33");
        map.put("生活服务*维保服务","34");
        map.put("*鉴证咨询服务*预算审核","35");
        map.put("*鉴证咨询服务*结算审核","36");
        map.put("设计服务*测量费","37");
        map.put("设计服务*勘察费","38");
        map.put("设计服务*可研编制费","39");
        map.put("设计服务*测绘费","40");
        map.put("设计服务*检测费","41");
        map.put("设计服务*规划费","42");
        map.put("建筑服务*工程款","43");
        map.put("其他咨询服务*招标代理费","44");
        map.put("其他咨询服务*标书费","45");
        map.put("现代服务*总承包管理费","46");
        map.put("*供电*电费","47");
        map.put("设计服务*咨询费","48");
        map.put("*现代服务*其他现代服务","49");
        map.put("*其他咨询服务*评审费","50");
        map.put("金融服务*贷款利息收入","51");

        return map.getOrDefault(id, "50");
    }

    // 申请日期
    public String tdrq(String contractId) {
        if (contractId == "" || contractId == null) {
            return "1900-01-01";
        }
        return formmainMapper.shenqingriqi(contractId);
    }

    // 合同执行人
    public String htzxr(String ids) {
        if (ids == null || ids.equals("")) {
            return "";
        }
        String[] persons = ids.split(",");
        return persons[0];
    }

    public String sktj(String contractId) {
        if (contractId == "" || contractId == null) {
            return "";
        }
        return formmainMapper.sktj(contractId);
    }

    public String getCompanySSN(String name) {
        if (name == "" || name == null) {
            return "";
        }
        return formmainMapper.jfgys(name);
    }

    // 查询客户社会信用号
    public String getCustomerSSN(String name) {
        if (name == "" || name == null) {
            return "";
        }
        return formmainMapper.getCustomerSSN(name);
    }

    public String cbbm(String ids) {
        if (Objects.equals(ids, "") || ids == null) {
            return "";
        }
        String[] result = ids.split(",");
        return result[0];
    }

    // 收款银行
    public String skyx(String name) {
        if (Objects.equals(name, "") || name == null) {
            return "";
        }
        return formmainMapper.skyx(name);
    }

    public String yxzh(String name) {
        if (Objects.equals(name, "") || name == null) {
            return "";
        }
        return formmainMapper.yxzh(name);
    }

    public String skfs(String name) {
        if (Objects.equals(name, "") || name == null) {
            return "";
        }
        return formmainMapper.skfs(name);
    }

    public String skfxs(String name) {
        if (name == null || name == "") {
            return "";
        }
        return formmainMapper.skfxs(name);
    }

    public String sqr(String id) {
        if (id == null || id == "") {
            return "43100969";
        }
        return formmainMapper.getPersonOACode(id);
    }

    public String hetongzhuangtai(String id) {
        if (id == "" || id == null) {
            return "履约中";
        }
        return formmainMapper.getEnumString(id);
    }

    // 登记日期
    public String collectDjrq(Formmain_0027 formmain0027) {
        // 如果没有登记日期，用签订日期
        if (Objects.equals(formmain0027.getField0013(), "") || formmain0027.getField0013() == null) {
            return formmain0027.getField0014();
        }
        return formmain0027.getField0013();
    }

    public String getBankId(String name) {
        if (name == "" || name == null) {
            return name;
        }
        return formmainMapper.getBankId(name);
    }

    // 可收金额 自营
    public BigDecimal ksjezy(BigDecimal value, BigDecimal ratio) {
        if (value.compareTo(BigDecimal.ZERO)==0) {
            return new BigDecimal(0);
        }
        return value.multiply(ratio).divide(new BigDecimal(100), 2);
    }

    // 可收金额 外部
    public BigDecimal ksjewb(BigDecimal value, BigDecimal ratio) {
        if (value.compareTo(BigDecimal.ZERO)==0) {
            return new BigDecimal(0);
        }
        return value.multiply(ratio).divide(new BigDecimal(100), 2);
    }

    public BigDecimal ksjenb(BigDecimal total, BigDecimal ratio1, BigDecimal ratio2) {
        BigDecimal n1 = total.multiply(ratio1).divide(new BigDecimal(100), 2);
        BigDecimal n2 = total.multiply(ratio2).divide(new BigDecimal(100), 2);
        return total.subtract(n1).subtract(n2);
    }

    public String projectSqr(String id) {
        if (id == null || id == "") {
            return "43100967";
        }
        return formmainMapper.getPersonOACode(id);
    }

    public String xmfgld(String id) {
        if (id == "" || id == null) {
            return "";
        }
        return formmainMapper.xmfgld(id);
    }

    public String glalht(String id) {
        return formmainMapper.glalht(id);
    }

    // 收款量化指标
    public String sklhzb(String value) {
        if (value == "咨询") {
            return "按照咨询进度支付费用";
        } else if (value == "设计") {
            return "按照设计进度支付费用";
        } else if (value == "正常监理") {
            return "按照工程形象进度支付费用";
        } else if (value == "勘测") {
            return "按照勘察进度支付费用";
        } else {
            return "根据工程形象进度付款";
        }
    }

    // 专业拆分，申请人
    public String majorsqr(String id) {
        String person = formmainMapper.majorsqr(id);
        if (person == null || person == "") {
            return "43100969";
        }
        return getMemberOA(person);
    }

    // 外部合作金额
    public BigDecimal wbhzje(BigDecimal field0039, float ratio) {
        return field0039.multiply(BigDecimal.valueOf(ratio)).divide(new BigDecimal(100),2);
    }

    // 专业拆分，合计
    public BigDecimal majorhj(BigDecimal value1, BigDecimal value2, BigDecimal value3) {
        return value1.add(value2).add(value3);
    }

    // 本期自营比例
    public BigDecimal bqzybl(BigDecimal value1, BigDecimal total) {
        if (total.compareTo(BigDecimal.ZERO)==0) {
            return new BigDecimal(0);
        }
        return value1.divide(total, RoundingMode.DOWN);
    }

    // 外部自营比例
    public BigDecimal wbhzbl(BigDecimal value1, BigDecimal total) {
        if (total.compareTo(BigDecimal.ZERO)==0) {
            return new BigDecimal(0);
        }
        return value1.divide(total, RoundingMode.DOWN);
    }

    // 内部自营比例
    public BigDecimal nbhzbl(BigDecimal value1, BigDecimal total) {
        if (total.compareTo(BigDecimal.ZERO)==0) {
            return new BigDecimal(0);
        }
        return value1.divide(total, RoundingMode.DOWN);
    }

    // 收款， 招标简述
    // 定点=直接委托，空=直接委托，合同=直接委托，中标=招标
    public String zbjs(String id) {
        String[] result = {"2", "1", "1"};
        if (id == "" || id == null) {
            return result[1];
        }
        return result[Integer.parseInt(getEnumValue(id))];
    }

    public String realhtzy(String id) {
        if (id == "" || id == null) {
            return "";
        }
        return formmainMapper.realhtzy(id);
    }

    public BigDecimal jljsje(String id) {
        return formmainMapper.jljsje(id);
    }

    public String realhtbh(String id) {
        return formmainMapper.realhtbh(id);
    }

    public String collectplansqr(String id) {
        if (id == "" || id == null) {
            return "43100971";
        }
        return getMemberOA(id);
    }

    public String collectplantdrq(String id) {
        return formmainMapper.collectplantdrq(id);
    }

    public String collectplansj(String id) {
        return formmainMapper.collectplansj(id);
    }

    // 项目执行状态
    public String xmzxzt(String id) {
        return formmainMapper.xmzxzt(id);
    }

    public String collectplanhtzt(String id) {
        return formmainMapper.collectplanhtzt(id);
    }

    public BigDecimal bqjhhk(String id) {
        return formmainMapper.bqjhhk(id);
    }

    public Float ljbl(String id) {
        return formmainMapper.ljbl(id);
    }

    public String htlb(String id) {
        return formmainMapper.htlb(id);
    }

    public String planqyjey(String id) {
        return formmainMapper.planqyjey(id);
    }

    public String planqyjebhs(String id) {
        return formmainMapper.planqyjebhs(id);
    }

    public String planqyse(String id) {
        return formmainMapper.planqyse(id);
    }

    public String zxhtje(String id) {
        return formmainMapper.zxhtje(id);
    }

    public String zxhtjebhs(String id) {
        return formmainMapper.zxhtjebhs(id);
    }

    public String htksje(String id) {
        return formmainMapper.htksje(id);
    }

    public float ksjezy(String id) {
        Float ratio = formmainMapper.ksjezy(id);
        if (ratio == null) {
            return 0;
        }
        return ratio;
    }

    public float planksjewb(String id) {
        Float ratio = formmainMapper.ksjewb(id);
        if (ratio == null) {
            return 0;
        }
        return ratio;
    }

    public String invoicehtmc(String id) {
        return formmainMapper.invoicehtmc(id);
    }

    public String realywkm(String id) {
        String value = formmainMapper.hetongzhuanye(id);
        if (value == "咨询") {
            return "016006005";
        } else if (value == "设计") {
            return "016003002";
        } else if (value == "正常监理") {
            return "016006005";
        } else if (value == "勘测") {
            return "016004002";
        } else {
            return "016006005";
        }
    }

    public String rzbm(String id) {
        return formmainMapper.rzbm(id);
    }

    public String planhtzt(String value) {
        if (value == "中止" || value == "作废" || value == "异常" || value == "终止") {
            return "设计合同异常";
        } else if (value == "已完结") {
            return "设计合同履约中（合同额决算）";
        } else if (value == "履约中" || value == "待决算") {
            return "设计合同履约中（生产进行）";
        } else {
            return "设计合同履约中（生产进行）";
        }
    }

    public String planxmzxzt(String id) {
        return getEnumString(formmainMapper.xiangmuzhuangtai(id));
    }

    public String getCompanyIdFromCode(String code) {
        return formmainMapper.getCompanyIdFromCode(code);
    }

    public BigInteger getFormmain_0717ID() {
        BigInteger id = formmainMapper.getFormmain_0717MaxID();
        if (id == null) {
            return new BigInteger("2430239558686690400");
        } else {
            return id.add(new BigInteger("1"));
        }
    }

    // 核算方式，根据合同ID获取核算方式
    public int hsfs0027(String id) {
        String hsfs = formmainMapper.hsfs0027(id);
        // 如果获取不到核算方式，按照不创建自定义核算项处理
        if (hsfs == null || hsfs.equals("")) {
            return 3;
        }

        int result = 0;
        if (hsfs != null) {
            result = formmainMapper.getEnumValue(hsfs);
        }

        // 如果是0， 代表新增
        if (result == 0) {
            return 1;
        } else {
            return 3;
        }
    }

    public int hsfs0451(String id) {
        String hsfs = formmainMapper.hsfs0451(id);
        int result = 0;
        if (hsfs != null) {
            result = formmainMapper.getEnumValue(hsfs);
        }

        // 如果是0， 代表新增
        if (result == 0) {
            return 1;
        } else {
            return 3;
        }
    }

    public String productPlanSqr(String id) {
        String ids = formmainMapper.getProductPlanSqr(id);
        if (ids == null || ids.equals("")) {
            return "43100967";
        }

        String[] persons = ids.split(",");
        return getMemberOA(persons[0]);
    }

    public String productPlanHtbh(String id) {
        return formmainMapper.productPlanHtbh(id);
    }

    public String productPlanSj(String id) {
        return formmainMapper.productPlanSj(id);
    }

    public String productPlanHtzy(String id) {
        return formmainMapper.productPlanHtzy(id);
    }

    public String productPlanSrqrff(String id) {
        return formmainMapper.productPlanSrqrff(id);
    }

    public String productPlanZxbm(String id) {
        if (formmainMapper.productPlanZxbm(id) == null) {
            return "";
        }
        String[] ids = formmainMapper.productPlanZxbm(id).split(",");
        return getDeptCode(ids[0]);
    }

    public String productPlanXmzxzt(String id) {
        if (getEnumString(formmainMapper.productPlanXmzxzt(id)) == null) {
            return "";
        }
        return getEnumString(formmainMapper.productPlanXmzxzt(id));
    }

    public String productPlanHtzt(String id) {
        String enumValue = formmainMapper.productPlanHtzt(id);
        if (enumValue == null) {
            return "异常";
        }
        switch (enumValue) {
            case "-1990640152697616217":
                return "中止";
            case "-1909549471229327137":
                return "决算（财审）";
            case "-1488099260878273480":
                return "履约中（在建/生产）";
            case "-1299559296124631086":
                return "关闭（异常）";
            case "3303181539035922716":
                return "关闭（正常）";
            case "7272675396345873261":
                return "关闭（异常）";
            case "8358136034279309995":
                return "关闭（异常）";
            default:
                return "关闭（异常）";
        }
    }

    public BigDecimal productPlanQyjey(String id) {
        if (formmainMapper.productPlanQyjey(id) == null) {
            return new BigDecimal("0");
        }
        return formmainMapper.productPlanQyjey(id);
    }

    public String productPlanHtlb(String id) {
        return formmainMapper.productPlanHtlb(id);
    }

    public String productPlanHtmc(String id) {
        return formmainMapper.productPlanHtmc(id);
    }

    public String productPlanQyjebhs(String id) {
        return formmainMapper.productPlanQyjebhs(id);
    }

    public String productPlanQyse(String id) {
        return formmainMapper.productPlanQyse(id);
    }

    public String productPlanZxhtje(String id) {
        return formmainMapper.zxhtjebhs(id);
    }

    public BigDecimal productPlanHtksje(String id) {
        if (formmainMapper.productPlanHtksje(id) == null) {
            return new BigDecimal("0");
        }
        return formmainMapper.productPlanHtksje(id);
    }

    public float productPlanKsjezy(String id) {
        if (formmainMapper.productPlanKsjezy(id) == null) {
            return 0;
        }
        return formmainMapper.productPlanKsjezy(id);
    }

    public float productPlanKsjewb(String id) {
        if (formmainMapper.productPlanKsjewb(id) == null) {
            return 0;
        }
        return formmainMapper.productPlanKsjewb(id);
    }

    public String productRealSqr(String id) {
        if (id == null || id.equals("")) {
            return "";
        }
        return formmainMapper.getPersonOACode(id);
    }

    public String productRealhtbh(String id) {
        return formmainMapper.productRealhtbh(id);
    }

    public String productRealhtzy(String id) {
        return formmainMapper.productRealhtzy(id);
    }

    public String productRealzxbm(String id) {
        return formmainMapper.getOADeptCode(id);
    }

    public BigDecimal productRealbqqrzycz(Formmain_0255 formmain0255) {
        String dept = formmainMapper.productReal0260(formmain0255.getField0010());
        // 如果等于厦门
        if (Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0604(formmain0255.getField0010());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return formmain0255.getField0028().multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0225(formmain0255.getField0010());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return formmain0255.getField0028().multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal productRealbqqrnbhzcz(Formmain_0255 formmain0255) {
        String dept = formmainMapper.productReal0260(formmain0255.getField0010());
        // 如果等于厦门
        if (Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0602(formmain0255.getField0010());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return formmain0255.getField0028().multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0477(formmain0255.getField0010());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return formmain0255.getField0028().multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal productRealbqqrwbhzcz(Formmain_0255 formmain0255) {
        String dept = formmainMapper.productReal0260(formmain0255.getField0010());
        // 如果等于厦门
        if (Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal592(formmain0255.getField0010());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return formmain0255.getField0028().multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal603(formmain0255.getField0010());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return formmain0255.getField0028().multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal productRealwgl(Formmain_0255 formmain0255) {
        BigDecimal result = formmainMapper.productRealwgl(formmain0255.getField0010());
        BigDecimal y = (result == null) ? new BigDecimal(0) : result;
        return formmain0255.getField0028().multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
    }

    public BigDecimal productRealwgl1(Formmain_0255 formmain0255) {
        BigDecimal result = formmainMapper.productRealwgl(formmain0255.getField0010());
        BigDecimal y = (result == null) ? new BigDecimal(0) : result;
        return formmain0255.getField0030().multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
    }

    public BigDecimal productRealjzsqljzycz(Formmain_0255 formmain0255) {
        String dept = formmainMapper.productReal0260(formmain0255.getField0010());
        // 如果等于厦门
        if (Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0604(formmain0255.getField0010());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return formmain0255.getField0030().multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0225(formmain0255.getField0010());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return formmain0255.getField0030().multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal productRealjzsqljwbhzcz(Formmain_0255 formmain0255) {
        String dept = formmainMapper.productReal0260(formmain0255.getField0010());
        // 如果等于厦门
        if (Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0602(formmain0255.getField0010());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return formmain0255.getField0030().multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0477(formmain0255.getField0010());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return formmain0255.getField0030().multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal productRealjzsqljnbhzcz(Formmain_0255 formmain0255) {
        String dept = formmainMapper.productReal0260(formmain0255.getField0010());
        // 如果等于厦门
        if (Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal603(formmain0255.getField0010());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return formmain0255.getField0030().multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal592(formmain0255.getField0010());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return formmain0255.getField0030().multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal productRealbqzycz(Formmain_0255 formmain0255) {
        String dept = formmainMapper.productReal0260(formmain0255.getField0010());
        // 如果等于厦门
        if (Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0604(formmain0255.getField0010());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return formmain0255.getField0028().multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0225(formmain0255.getField0010());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return formmain0255.getField0028().multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public String productRealhtlb(String id) {
        return formmainMapper.productRealhtlb(id);
    }

    public String productRealxmxx(String id) {
        return formmainMapper.productRealxmxx(id);
    }

    public String productRealhtmc(String id) {
        return formmainMapper.productRealhtmc(id);
    }

    public BigDecimal productRealqyjey(String id) {
        return formmainMapper.productRealqyjey(id);
    }

    public BigDecimal productRealqyjebhs(String id) {
        return formmainMapper.productRealqyjebhs(id);
    }

    public BigDecimal productRealqyse(String id) {
        return formmainMapper.productRealqyse(id);
    }

    public BigDecimal productRealzxhtje(String id) {
        return formmainMapper.productRealzxhtje(id);
    }

    public BigDecimal productRealzxhtjebhs(String id) {
        return formmainMapper.productRealzxhtjebhs(id);
    }

    public BigDecimal productRealhtksje(String id) {
        BigDecimal result = formmainMapper.productRealhtksje(id);
        return result == null ? new BigDecimal(0) : result;
    }

    public BigDecimal productRealksjezy(Formmain_0255 formmain0255) {
        String dept = formmainMapper.productReal0260(formmain0255.getField0010());
        BigDecimal field0593 = productRealhtksje(formmain0255.getField0010());
        field0593 = field0593 == null ? new BigDecimal(0) : field0593;

        // 如果不等于厦门
        if (!Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0225(formmain0255.getField0010());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return field0593.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0604(formmain0255.getField0010());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return field0593.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal productRealksjenb(Formmain_0255 formmain0255) {
        String dept = formmainMapper.productReal0260(formmain0255.getField0010());
        BigDecimal field0593 = productRealhtksje(formmain0255.getField0010());
        field0593 = field0593 == null ? new BigDecimal(0) : field0593;

        // 如果不等于厦门
        if (!Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0477(formmain0255.getField0010());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return field0593.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0602(formmain0255.getField0010());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return field0593.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal productRealksjewb(Formmain_0255 formmain0255) {
        String dept = formmainMapper.productReal0260(formmain0255.getField0010());
        BigDecimal field0593 = productRealhtksje(formmain0255.getField0010());
        field0593 = field0593 == null ? new BigDecimal(0) : field0593;

        // 如果不等于厦门
        if (!Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal592(formmain0255.getField0010());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return field0593.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal603(formmain0255.getField0010());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return field0593.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public String productRealsqrszbm(String id) {
        return formmainMapper.getEnumString(formmainMapper.productRealsqrszbm(id));
    }

    public BigDecimal productRealbqzybl(BigDecimal x, BigDecimal y) {
        if (y == null || y.compareTo(new BigDecimal(0)) == 0) {
            return new BigDecimal(0);
        }
        return x.divide(y, RoundingMode.DOWN);
    }

    public BigDecimal productRealwglratio(String id) {
        BigDecimal result = formmainMapper.productRealwgl(id);
        if (result == null) {
            return new BigDecimal(0);
        }
        return result;
    }

    public List<CtpFile0027> collectContract0027(String id) {
        return formmainMapper.collectContract0027(id);
    }

    public CtpFile0027 getCtpFile(String fileurl) {
        return formmainMapper.getCtpFile(fileurl);
    }

    public CtpFile0027 getCtpFileWtxy(String fileurl) {
        return formmainMapper.getCtpFileWtxy(fileurl);
    }

    public CtpFile0256 getAttachedFile0256(String fileurl) {
        return formmainMapper.getAttachedFile0256(fileurl);
    }

    public String getFileUrl(CtpFile0027 attchedFile) {
        String rootUrl = "http://192.168.9.88:8069/seeyon/contractcollect/download?file=";
        return rootUrl + attchedFile.getFileurl();
    }

    public String getFileUrlWtxy(CtpFile0027 attchedFile) {
        String rootUrl = "http://192.168.9.88:8069/seeyon/contractcollectwtxy/download?file=";
        return rootUrl + attchedFile.getFileurl();
    }

    public List<CtpFile0256> getCtpFile0256(Formmain_0255 formmain0255) {
        List<CtpFileChild0256> files = new ArrayList<>();
        CtpFileChild0256 file0159 = formmainMapper.getFileid0159(formmain0255.getField0010());
        CtpFileChild0256 file0055 = formmainMapper.getFileid0055(formmain0255.getField0010());
        CtpFileChild0256 file0063 = formmainMapper.getFileid0063(formmain0255.getField0010());
        if (file0159 != null) files.add(file0159);
        if (file0055 != null) files.add(file0055);
        if (file0063 != null) files.add(file0063);

        if (files.size() == 0) {
            return null;
        }

        files.sort(Comparator.comparing(CtpFileChild0256::getStart_date).reversed());

        String fileid = files.get(0).getFile();
        return formmainMapper.getFile0256(fileid);
    }

    public String getFileUrl0256(CtpFile0256 file0256) {
        String rootUrl = "http://192.168.9.88:8069/seeyon/productreal/download?file=";
        return rootUrl + file0256.getFILE_URL();
    }

    public String jskh0159(String id) {
        String field0022 = formmainMapper.getField0022(id);
        return getCustomerSSN(field0022);
    }

    public String xmzxzt0159(String id) {
        if (formmainMapper.xmzxzt0159(id) == null) {
            return "";
        }
        return getEnumString(formmainMapper.xmzxzt0159(id));
    }

    public String rzbm0159(String id) {
        if (formmainMapper.rzbm0159(id) == null) {
            return "";
        }
        return formmainMapper.rzbm0159(id);
    }

    public BigDecimal htyssqje0159(String id) {
        BigDecimal result = formmainMapper.htyssqje0159(id);
        if (result == null) {
            return new BigDecimal(0);
        }
        return formmainMapper.htyssqje0159(id);
    }

    public BigDecimal jzsqljqrje0159(String id) {
        BigDecimal total = formmainMapper.jzsqljqrje0159(id);
        if (total == null || total.compareTo(new BigDecimal(0)) == 0) {
            return new BigDecimal(0);
        }

        return total.subtract(htyssqje0159(id));
    }

    public String qrysrq0159(Formmain_0159 formmain0159) {
        LocalDateTime field0048 = formmainMapper.qrysrq0159(formmain0159.getField0006());
        if (field0048 == null) {
            return formmain0159.getField0004().toLocalDate().toString();
        }
        return field0048.toLocalDate().toString();
    }

    public String zfzs0159(String id) {
        List<CtpFile0256> result = formmainMapper.getFile0256(id);
        if (result.size() == 0) {
            return "";
        }
        return getFileUrl0256(result.get(0));
    }

    public String sklhzb0159(String id) {
        String field0589 = formmainMapper.hetongzhuanye(id);
        return sklhzb(field0589);
    }

    public String xzht0159(String id) {
        return formmainMapper.xzht0159(id);
    }

    public String tdrqriqi(LocalDateTime field0003, String field0001) {
        if (field0003 == null) {
            return tdrq00159(field0001);
        }
        return field0003.toString();
    }
    public String tdrq00159(String field0001) {
        return formmainMapper.tdrq00159(field0001);
    }

    public BigDecimal bqqrcz(Formmain_0533 formmain0533) {
        BigDecimal zxhtje = formmainMapper.zxhtje(formmain0533.getField0001()) == null?
                new BigDecimal(0) : new BigDecimal(formmainMapper.zxhtje(formmain0533.getField0001()));
        BigDecimal field0011 = formmain0533.getField0011() == null ? new BigDecimal(0) : formmain0533.getField0011();
        return zxhtje.multiply(field0011).multiply(new BigDecimal("0.01"));
    }

    public BigDecimal bqqrzycz0159(BigDecimal total, Formmain_0533 formmain0533) {
        String dept = formmainMapper.productReal0260(formmain0533.getField0001());
        // 如果等于厦门
        if (Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0604(formmain0533.getField0001());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0225(formmain0533.getField0001());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal bqqrnbhzcz0159(BigDecimal total, Formmain_0533 formmain0533) {
        String dept = formmainMapper.productReal0260(formmain0533.getField0001());
        // 如果等于厦门
        if (Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal592(formmain0533.getField0001());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal603(formmain0533.getField0001());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal wgl0159(BigDecimal total, Formmain_0533 formmain0533) {
        BigDecimal result = formmainMapper.productRealwgl(formmain0533.getField0001());
        BigDecimal y = (result == null) ? new BigDecimal(0) : result;
        return total.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
    }

    public BigDecimal ksjezy0533(Formmain_0533 formmain0533) {
        String dept = formmainMapper.productReal0260(formmain0533.getField0001());
        BigDecimal field0593 = productRealhtksje(formmain0533.getField0001());
        field0593 = field0593 == null ? new BigDecimal(0) : field0593;

        // 如果不等于厦门
        if (!Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0225(formmain0533.getField0001());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return field0593.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0604(formmain0533.getField0001());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return field0593.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal ksjenb0533(Formmain_0533 formmain0533) {
        String dept = formmainMapper.productReal0260(formmain0533.getField0001());
        BigDecimal field0593 = productRealhtksje(formmain0533.getField0001());
        field0593 = field0593 == null ? new BigDecimal(0) : field0593;

        // 如果不等于厦门
        if (!Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0477(formmain0533.getField0001());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return field0593.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0602(formmain0533.getField0001());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return field0593.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal ksjewb0533(Formmain_0533 formmain0533) {
        String dept = formmainMapper.productReal0260(formmain0533.getField0001());
        BigDecimal field0593 = productRealhtksje(formmain0533.getField0001());
        field0593 = field0593 == null ? new BigDecimal(0) : field0593;

        // 如果不等于厦门
        if (!Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal592(formmain0533.getField0001());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return field0593.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal603(formmain0533.getField0001());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return field0593.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public String zxbm0474(String id) {
        return formmainMapper.getOADeptCode(formmainMapper.zxbm0474(id));
    }

    public BigDecimal bqqrzycz0765(BigDecimal total, Formmain_0765 formmain0765) {
        String dept = formmainMapper.productReal0260(formmain0765.getField0004());
        // 如果等于厦门
        if (Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0604(formmain0765.getField0004());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0225(formmain0765.getField0004());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal bqqrwbhzcz0765(BigDecimal total, Formmain_0765 formmain0765) {
        String dept = formmainMapper.productReal0260(formmain0765.getField0004());
        // 如果等于厦门
        if (Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal592(formmain0765.getField0004());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal603(formmain0765.getField0004());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal bqqrnbhzcz0765(BigDecimal total, Formmain_0765 formmain0765) {
        String dept = formmainMapper.productReal0260(formmain0765.getField0004());

        // 如果不等于厦门
        if (!Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0477(formmain0765.getField0004());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0602(formmain0765.getField0004());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal jzsqljcz0765(BigDecimal total, Formmain_0765 formmain0765) {
        String dept = formmainMapper.productReal0260(formmain0765.getField0004());
        // 如果等于厦门
        if (Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0604(formmain0765.getField0004());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0225(formmain0765.getField0004());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal jzsqljwbhzcz0765(BigDecimal total, Formmain_0765 formmain0765) {
        String dept = formmainMapper.productReal0260(formmain0765.getField0004());
        // 如果等于厦门
        if (Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0602(formmain0765.getField0004());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0477(formmain0765.getField0004());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal jzsqljnbhzcz0765(BigDecimal total, Formmain_0765 formmain0765) {
        String dept = formmainMapper.productReal0260(formmain0765.getField0004());
        // 如果等于厦门
        if (Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal603(formmain0765.getField0004());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal592(formmain0765.getField0004());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal ksjezy0765(BigDecimal total, Formmain_0765 formmain0765) {
        String dept = formmainMapper.productReal0260(formmain0765.getField0004());

        // 如果不等于厦门
        if (!Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0225(formmain0765.getField0004());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0604(formmain0765.getField0004());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal ksjenb0765(BigDecimal total, Formmain_0765 formmain0765) {
        String dept = formmainMapper.productReal0260(formmain0765.getField0004());

        // 如果不等于厦门
        if (!Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal0477(formmain0765.getField0004());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal0602(formmain0765.getField0004());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public BigDecimal ksjewb0765(BigDecimal total, Formmain_0765 formmain0765) {
        String dept = formmainMapper.productReal0260(formmain0765.getField0004());

        // 如果不等于厦门
        if (!Objects.equals(dept, "-6380744868045831639")) {
            BigDecimal result = formmainMapper.productReal592(formmain0765.getField0004());
            BigDecimal y = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(y).divide(new BigDecimal(100), RoundingMode.CEILING);
        } else {
            BigDecimal result = formmainMapper.productReal603(formmain0765.getField0004());
            BigDecimal x = (result == null) ? new BigDecimal(0) : result;
            return total.multiply(x).divide(new BigDecimal(100), RoundingMode.CEILING);
        }
    }

    public String zfzs0265(Formmain_0265 formmain0265) {
        String fileid = formmainMapper.zfzs0265(formmain0265);

        if (fileid == null) {
            fileid = formmainMapper.zfzs0265History(formmain0265);
        }

        return zfzs0159(fileid);
    }

    public BigDecimal qyjey0027(Formmain_0027 formmain0027) {
        BigDecimal supAmount = formmainMapper.bcxyje0351(formmain0027);

        if (supAmount == null) {
            supAmount = new BigDecimal(0);
        }
        if (formmain0027.getField0015() == null) {
            return supAmount;
        } else {
            return formmain0027.getField0015().add(supAmount);
        }
    }

    public String tdrqweituoxieyi(Formmain_0777 formmain0777) {
        return formmainMapper.tdrqweituoxieyi(formmain0777);
    }

    public String htjydmsweituoxieyi(Formmain_0777 formmain0777) {
        String type = formmainMapper.htjydmsweituoxieyi(formmain0777);
        return htjydms(type);
    }

    public String jylxweituoxieyi(Formmain_0777 formmain0777) {
        String type = formmainMapper.jylxweituoxieyi(formmain0777);
        return jylx(type);
    }

    public String htqdrqweituoxieyi(Formmain_0777 formmain0777) {
        return formmainMapper.htqdrqweituoxieyi(formmain0777);
    }

    public String htqxbweituoxieyi(Formmain_0777 formmain0777) {
        return formmainMapper.htqxbweituoxieyi(formmain0777);
    }

    public String htqxweituoxieyi(Formmain_0777 formmain0777) {
        return formmainMapper.htqxweituoxieyi(formmain0777);
    }

    public String cbbmweituoxieyi(Formmain_0777 formmain0777) {
        return getDeptCode(formmainMapper.cbbmweituoxieyi(formmain0777));
    }

    public String htzxrweituoxieyi(Formmain_0777 formmain0777) {
        return getMemberOA(formmainMapper.htzxrweituoxieyi(formmain0777));
    }

    public String sktjweituoxieyi(Formmain_0777 formmain0777) {
        return formmainMapper.sktjweituoxieyi(formmain0777);
    }

    public Integer hsfsweituoxieyi(Formmain_0777 formmain0777) {
        String hsfs = formmainMapper.hsfsweituoxieyi(formmain0777);

        // 如果获取不到核算方式，按照不创建自定义核算项处理
        if (hsfs == null || hsfs.equals("")) {
            return 3;
        }

        int result = 0;
        result = formmainMapper.getEnumValue(hsfs);

        // 如果是0， 代表新增
        if (result == 0) {
            return 1;
        } else {
            return 3;
        }
    }

    public String sklhzbweituoxieyi(Formmain_0777 formmain0777) {
        return sklhzb(formmainMapper.sklhzbweituoxieyi(formmain0777));
    }

    public String djrqweituoxieyi(Formmain_0777 formmain0777) {
        // 如果没有登记日期，用签订日期
        String field0013 = formmainMapper.djrqweituoxieyi(formmain0777);
        String field0014 = formmainMapper.djrqweituoxieyi2(formmain0777);

        if (Objects.equals(field0013, "") || field0013 == null) {
            return field0014;
        }
        return field0013;
    }

    public List<CtpFile0027> htfjweituoxieyi(String id) {
        try {
            return formmainMapper.htfjweituoxieyi(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<CtpFile0027> htfjweituoxieyifukuan(String id) {
        try {
            return formmainMapper.htfjweituoxieyifukuan(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String tdrqfukuan(Formmain_0542 formmain0542) {
        return formmainMapper.tdrqfukuan(formmain0542);
    }

    public String htqdrqfukuan(Formmain_0542 formmain0542) {
        return formmainMapper.htqdrqfukuan(formmain0542);
    }

    public String htqxbfukuan(Formmain_0542 formmain0542) {
        return formmainMapper.htqxbfukuan(formmain0542);
    }

    public String htqxfukuan(Formmain_0542 formmain0542) {
        return formmainMapper.htqxfukuan(formmain0542);
    }

    public String sktjfukuan(Formmain_0542 formmain0542) {
        return formmainMapper.sktjfukuan(formmain0542);
    }

    public Integer hsfsfukuan(Formmain_0542 formmain0542) {
        String hsfs = formmainMapper.hsfsfukuan(formmain0542);
        int result = 0;
        if (hsfs != null) {
            result = formmainMapper.getEnumValue(hsfs);
        }

        // 如果是0， 代表新增
        if (result == 0) {
            return 1;
        } else {
            return 3;
        }
    }

    public Integer continuousfukuan(Formmain_0542 formmain0542) {
        String result = getEnumValue(formmainMapper.continuousfukuan(formmain0542));
        if (Objects.equals(result, "0")) {
            return 1;
        } else {
            return 0;
        }
    }

    public String xzsjhsxmfukuan(Formmain_0542 formmain0542) {
        return formmainMapper.xzsjhsxmfukuan(formmain0542);
    }

    public String skdwfukuan(Formmain_0542 formmain0542) {
        return formmainMapper.skdwfukuan(formmain0542);
    }

    public String skyxfukuan(Formmain_0542 formmain0542) {
        return formmainMapper.skyxfukuan(formmain0542);
    }

    public String yxzhfukaun(Formmain_0542 formmain0542) {
        return formmainMapper.yxzhfukaun(formmain0542);
    }

    public String skfsfukuan(Formmain_0542 formmain0542) {
        return formmainMapper.skfsfukuan(formmain0542);
    }

    public String skfxsfukuan(Formmain_0542 formmain0542) {
        return formmainMapper.skfxsfukuan(formmain0542);
    }

    public BigDecimal bqjhhkratio(Formson_0618 formson0618) {
        if (formson0618.getField0006().compareTo(new BigDecimal("1")) == 0) {
            return formson0618.getField0011().divide(new BigDecimal("100"), 4, RoundingMode.DOWN);
        } else {
            formson0618.setLastIndex(formson0618.getField0006().subtract(new BigDecimal(1)));
            BigDecimal lastRatio = formmainMapper.bqjhhkratiolast(formson0618);
            BigDecimal newRatio = formson0618.getField0011().subtract(lastRatio);
            return newRatio.divide(new BigDecimal("100"), 4, RoundingMode.DOWN);
        }
    }

    public String smgjjd(Formson_0618 formson0618) {
        return getEnumString(formson0618.getField0007());
    }

    public Formmain_0027 build0027(Formmain_0777 formmain0777) {
        Formmain_0027 formmain0027 = formmainMapper.get0027(formmain0777);
        formmain0027.setField0582(formmain0777.getField0078());
        return formmain0027;
    }

    public List<Formmain_0765> build0765(Formmain_0777 formmain0777) {
        List<Formmain_0765> formmain0765 = formmainMapper.get0765(formmain0777);
        return formmain0765;
    }

    public List<Formmain_0265> build0265(Formmain_0777 formmain0777) {
        List<Formmain_0265> formmain0265 = formmainMapper.get0265(formmain0777);
        return formmain0265;
    }

    public List<Formmain_0506> build0506(Formmain_0777 formmain0777) {
        List<Formmain_0506> formmain0506 = formmainMapper.get0506(formmain0777);
        return formmain0506;
    }

    public BigDecimal totalwtxy(Formmain_0777 formmain0777) {
        return formmainMapper.totalwtxy(formmain0777);
    }

    public List<Formmain_0240> build0240(Formmain_0777 formmain0777) {
        return formmainMapper.get0240(formmain0777);
    }
}
