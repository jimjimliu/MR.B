package com.kachiingapp.kachiing.api.controller.pullDataFromKingDee;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPHeaderElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: EAS
 * @Auther: liujunhan
 * @Date: 2024/1/19 09:25
 * @Description:
 */
public class EAS {

    private static final Logger logger = LoggerFactory.getLogger(EAS.class);

    public static boolean updateEASClaimStatus(Map<String, String> param) throws Exception {
        setParameter();
        //1.µÇÂ¼
        Service s = new Service();
        Call call = (Call) s.createCall();
        call.setOperationName("login");
        call.setTargetEndpointAddress(url + "EASLogin?wsdl");
        call.setReturnType(new QName("urn:client", "WSContext"));
        call.setReturnClass(WSContext.class);
        call.setReturnQName(new QName("", "loginReturn"));
        call.setTimeout(Integer.valueOf(1000 * 600000 * 60));
        call.setMaintainSession(true);
        //µÇÂ½½Ó¿Ú²ÎÊý
        WSContext rs =
                (WSContext) call.invoke(new Object[]{user, pwd, slnName, dcName, language, dbType});
        if (rs.getSessionId() == null) {
            throw new Exception("login fail");
        }

//        System.out.println(rs.getSessionId());
        //ÇåÀí
        call.clearOperation();
        // »ñÈ¡½×¶ÎÐÅÏ¢½Ó¿Ú
        call.setOperationName("UpdateTransDetailInfo");
        call.setTargetEndpointAddress(url + "WSOAFacade?wsdl");
        call.setReturnQName(new QName("", "UpdateTransDetailInfoReturn"));
        call.setTimeout(Integer.valueOf(1000 * 600000 * 60));
        call.setMaintainSession(true);
        //ÉèÖÃµÇÂ¼·µ»ØµÄsessionÔÚsoapÍ· "http://login.webservice.bos.kingdee.com"ÊÇ¹Ì¶¨µÄ
        SOAPHeaderElement header = new SOAPHeaderElement("http://login.webservice.bos.kingdee.com",
                "SessionId", rs.getSessionId());
        call.addHeader(header);

        //·µ»Ø±¨ÎÄ
//            String parameter = contractInvoice();

        String aa = (String) call.invoke(new Object[]{JSON.toJSONString(param)});

        Map<String, String> result = new ObjectMapper().readValue(aa, HashMap.class);

        String status = result.getOrDefault("state", "500");
//        System.out.println(result);
        if (status.equals("200")) {
            logger.info("【金蝶状态更新】【成功】");
            return true;
        } else {
            logger.error("【金蝶状态更新】【失败】");
            throw new RuntimeException("更新状态失败");
        }
    }

    public static void main(String[] args) {
        try {
            Map<String, String> param = new HashMap<>();
            param.put("id", "FIwAAABPULM4DU9j");
            param.put("claimStatus", "0");
            JSON.toJSONString(param);
            EAS.updateEASClaimStatus(param);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //¿ªÆ±½ð¶îÈ·ÈÏ
    private static String contractInvoice() {
        Map<String, String> param = new HashMap<>();
        param.put("id", "FIwAAABPULM4DU9j");
        param.put("claimStatus", "0");
        JSON.toJSONString(param);
        return JSON.toJSONString(param);
    }


    private static String url = "";
    private static String user = "";
    private static String pwd = "";
    private static String slnName = "";
    private static String dcName = "";
    private static String language = "";
    private static int dbType = 2;

    /**
     * @return void
     * @Author nzs
     * @Description ÉèÖÃ²ÎÊý
     * @Param []
     **/

    public static void setParameter() {
        user = "zyuser";
        pwd = "a_123456";
//        url = "http://192.168.22.116:56898/ormrpc/services/";
//        url = "http://192.168.22.101:6890/ormrpc/services/";
        url = "http://192.168.22.99:8080/ormrpc/services/";
//        dcName = "uat202304";
        dcName = "JFHCXIN";
        language = "L2";
        slnName = "eas";
    }
}

