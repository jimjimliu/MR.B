package com.kachiingapp.kachiing.api.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kachiingapp.kachiing.api.controller.util.Utils;
import com.kachiingapp.kachiing.domain.entity.gongyingshang.CtpFile0027;
import com.kachiingapp.kachiing.domain.entity.gongyingshang.CtpFile0256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.time.format.DateTimeFormatter;

import static com.kachiingapp.kachiing.api.controller.http.HttpUtils.createRestTemplate;

/**
 * @ClassName: DownloadSeeYonFileController
 * @Auther: liujunhan
 * @Date: 2024/3/28 08:45
 * @Description:
 */

@RestController
@RequestMapping("/seeyon")
public class DownloadSeeYonFileController {

    @Autowired
    private Utils utils;

    private final RestTemplate restTemplate = createRestTemplate(1000 * 3600, 3 * 60 * 60 * 1000, new ObjectMapper());

    private static final String PYSICAL_ROOT_PATH = "F:\\Seeyon\\A8\\base\\upload\\";

    private static final DateTimeFormatter dayOfMonthFormatter = DateTimeFormatter.ofPattern("yyyy\\MM\\dd");

//    @RequestMapping("/contractcollect/download")
//    public void downloadContractcollect(@RequestParam("file") String fileUrl, HttpServletResponse response) {
//        try {
//            System.out.println(fileUrl);
//            CtpFile0027 ctpFile0027 = utils.getCtpFile(fileUrl);
//            String path = PYSICAL_ROOT_PATH
//                    + ctpFile0027.getCREATE_DATE().format(dayOfMonthFormatter)
//                    + "\\" + ctpFile0027.getFileurl();
//
//            // path是指想要下载的文件的路径
//            File file = new File(path);
//
//            // 将文件写入输入流
//            FileInputStream fileInputStream = new FileInputStream(file);
//            InputStream fis = new BufferedInputStream(fileInputStream);
//            byte[] buffer = new byte[fis.available()];
//            fis.read(buffer);
//            fis.close();
//
//            // 清空response
//            response.reset();
//            // 设置response的Header
//            response.setCharacterEncoding("UTF-8");
//            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
//            //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
//            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
//            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(ctpFile0027.getFILENAME(), "UTF-8"));
//            // 告知浏览器文件的大小
//            response.addHeader("Content-Length", "" + file.length());
//            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/octet-stream");
//            outputStream.write(buffer);
//            outputStream.flush();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    @RequestMapping("/contractcollectwtxy/download")
//    public void downloadContractcollectWtxy(@RequestParam("file") String fileUrl, HttpServletResponse response) {
//        try {
//            System.out.println(fileUrl);
//            CtpFile0027 ctpFile0027 = utils.getCtpFileWtxy(fileUrl);
//            String path = PYSICAL_ROOT_PATH
//                    + ctpFile0027.getCREATE_DATE().format(dayOfMonthFormatter)
//                    + "\\" + ctpFile0027.getFileurl();
//
//            // path是指想要下载的文件的路径
//            File file = new File(path);
//
//            // 将文件写入输入流
//            FileInputStream fileInputStream = new FileInputStream(file);
//            InputStream fis = new BufferedInputStream(fileInputStream);
//            byte[] buffer = new byte[fis.available()];
//            fis.read(buffer);
//            fis.close();
//
//            // 清空response
//            response.reset();
//            // 设置response的Header
//            response.setCharacterEncoding("UTF-8");
//            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
//            //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
//            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
//            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(ctpFile0027.getFILENAME(), "UTF-8"));
//            // 告知浏览器文件的大小
//            response.addHeader("Content-Length", "" + file.length());
//            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/octet-stream");
//            outputStream.write(buffer);
//            outputStream.flush();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    @RequestMapping("/productreal/download")
//    public void downloadProductreal(@RequestParam("file") String fileUrl, HttpServletResponse response) {
//        try {
//            System.out.println(fileUrl);
//            CtpFile0256 ctpFile0256 = utils.getAttachedFile0256(fileUrl);
//
//            String url = "http://192.168.9.88:8090/seeyon/rest/token/adminUser/a4d84b85-8a82-4acb-a4f5-f559012da41a?loginName=oa3";
//            String token = restTemplate.getForObject(url, String.class);
//
//            String getFile = "http://192.168.9.88:8090/seeyon/rest/attachment/file/" +
//                    fileUrl + "?fileName="+ ctpFile0256.getFILENAME() +"&token=" + token;
//
//            HttpHeaders httpHeaders = new HttpHeaders();
//            HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);
//            ResponseEntity<byte[]> responseEntity = restTemplate.exchange(getFile, HttpMethod.GET, httpEntity, byte[].class);
//            byte[] data = responseEntity.getBody();
//
//            // 清空response
//            response.reset();
//            // 设置response的Header
//            response.setCharacterEncoding("UTF-8");
//            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
//            //attachment表示以附件方式下载   inline表示在线打开   "Content-Disposition: inline; filename=文件名.mp3"
//            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
//            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(ctpFile0256.getFILENAME(), "UTF-8"));
//            // 告知浏览器文件的大小
////            response.addHeader("Content-Length", "" + file.length());
//            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
//            response.setContentType("application/octet-stream");
//            outputStream.write(responseEntity.getBody());
//            outputStream.flush();
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
}
