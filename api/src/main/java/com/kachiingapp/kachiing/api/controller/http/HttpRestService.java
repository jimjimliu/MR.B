package com.kachiingapp.kachiing.api.controller.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kachiingapp.kachiing.data.dao.data.FormmainMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.kachiingapp.kachiing.api.controller.http.HttpUtils.createRestTemplate;

/**
 * @ClassName: HttpRestService
 * @Auther: liujunhan
 * @Date: 2023/11/6 15:38
 * @Description:
 */

@Component
public class HttpRestService {
    private static final Logger logger = LogManager.getLogger(HttpRestService.class);

    private final RestTemplate restTemplate = createRestTemplate(1000 * 3600, 3 * 60 * 60 * 1000, new ObjectMapper());

    private static final String HOST = "http://192.168.22.89/api/cus/moduleData/pushData";

    private static final String DEPLOY_HOST = "http://192.168.22.59/api/cus/moduleData/pushData";

    private static final String APPID = "seeyon";

    public Map<String, Object> post(String body, String token) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        headers.add("appid", APPID);
        headers.add("token", token);
        headers.add("skipsession", "1");

        HttpEntity<String> formEntity = new HttpEntity<String>(body, headers);
        String str = restTemplate.postForObject(DEPLOY_HOST, formEntity, String.class);
        JSONObject jsonObject = JSON.parseObject(str);
        if (jsonObject.get("code").equals("SUCCESS")) {
            logger.info("【API返回结果】" + str);
            logger.info("【提交数据记录】" + body);
            logger.info("【执行结果】【SUCCESS】");

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("apiResult", str);
            result.put("jsonData", body);
            return result;
        } else {
            logger.error("【API返回结果】" + str);
            logger.error("【提交数据记录】" + body);
            logger.error("【执行结果】【FAILURE】");

            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("apiResult", str);
            result.put("jsonData", body);
            return result;
        }
    }
}
