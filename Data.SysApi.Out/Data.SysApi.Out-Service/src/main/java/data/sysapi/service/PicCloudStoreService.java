package data.sysapi.service;

import com.alibaba.fastjson.JSONObject;
import data.sysapi.interfaces.IPicCloudStoreService;
import data.sysapi.service.utils.PicHttpUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import witparking.core.entitys.ReturnMessage;
import witparking.core.exceptions.BusinessException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * @className PicCloudStoreService
 * @Description 图片云存储service
 * @Author zt
 * @Date 2019/11/5 10:00
 * @Version 1.0
 **/
@Service
public class PicCloudStoreService implements IPicCloudStoreService {

    private static final Logger logger = LoggerFactory.getLogger(PicCloudStoreService.class);

    @Value("${in_hisense_ip}")
    private String in_hisense_ip;

    @Value("${in_hisense_port}")
    private String in_hisense_port;

    public String doPictureStore(String jsonStr) throws BusinessException {
        logger.info("【图片云存储】#############入参打印：{}##########################", jsonStr);
        if (StringUtils.isBlank(jsonStr)) {
            throw new BusinessException("入参不能为空");
        }
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String picUrl = jsonObject.getString("picUrl");
        if (StringUtils.isBlank(picUrl)) {
            throw new BusinessException("图片地址不能为空");
        }
        HttpURLConnection conn = null;
        OutputStream out = null;
        Scanner in = null;
        //TODO  设置请求mapping
        String url = in_hisense_ip + ":" + in_hisense_port + "/picCloudStoreIn/doPictureStore";
        logger.info("【out】##url:{}##", url);
        try {
            URL obj = new URL(url);
            conn = (HttpURLConnection) obj.openConnection();
            conn.setRequestMethod("POST");
            // POST请求必须设置
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);

            // 设置请求头
           /* String frontier = "---------------------------" + System.currentTimeMillis();
            String newLine = "\r\n";
            String contentType = "multipart/form-data;";*/
            // 设置请求头
            conn.setRequestProperty("Accept", "text/html, application/xhtml+xml, */*");
            conn.setRequestProperty("Accept-Language", "zh-CN");
            conn.setRequestProperty("Content-Type", "application/text");
            conn.setRequestProperty("Host", in_hisense_ip + ":" + in_hisense_port);
            out = new DataOutputStream(conn.getOutputStream());

            PicHttpUtil.downImage(picUrl, out);
            //下面是服务器端如果有返回数据的话，做接收用的
            StringBuilder response = new StringBuilder();
            in = new Scanner(conn.getInputStream());
            while (in.hasNextLine()) {
                response.append(in.nextLine());
                response.append("\n");
            }
            logger.info("【out】##response：{}##", response.toString());
        } catch (Exception e) {
            logger.error("【图片云存储】################系统异常：{}############", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ReturnMessage(true, "操作成功", 30, "").toJsonString();
    }



}
