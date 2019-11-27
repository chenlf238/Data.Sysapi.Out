package data.sysapi.service.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.URL;
import java.util.List;

/**
 * @className PicHttpUtil
 * @Description 图片云存储-http工具类
 * @Author zt
 * @Date 2019/11/5 14:21
 * @Version 1.0
 **/
public class PicHttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(PicHttpUtil.class);

    public static void main(String[] args) {
        FileOutputStream out = null;
        try {
            File file = new File("d://e.jpg");
            out = new FileOutputStream(file);
            downImage("http://parkingphoto.oss-cn-qingdao.aliyuncs.com/520113001006/2018/5/16/DI520113001006009320180516112754000.jpg?Expires=1574821141&OSSAccessKeyId=t8aDdX9z3nTiEvWK&Signature=PPQEqqHVrzhBOiLPOg39UekMEoE%3D", out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end");

    }

    /**
     * 图片写出，直接在此方法写出到指定url
     */
    public static void downImage(String picUrl, OutputStream out) throws IOException {
        if (StringUtils.isBlank(picUrl)) {
            return;
        }
        InputStream in = null;
        try {
            URL url = new URL(picUrl);
            in = url.openStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 批量写出图片
     */
    public static void downImageBatch(List<String> urls, OutputStream out) throws IOException {
        if (CollectionUtils.isEmpty(urls)) {
            return;
        }
        InputStream in = null;
        try {
            for (String url : urls) {
                URL obj = new URL(url);
                in = obj.openStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
