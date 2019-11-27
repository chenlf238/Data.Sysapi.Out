package data.controller;

import com.alibaba.fastjson.JSON;
import data.sysapi.interfaces.IPicCloudStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @className PicCloudStoreController
 * @Description 图片云存储外网请求controller
 * @Author zt
 * @Date 2019/11/6 10:16
 * @Version 1.0
 **/
@Controller
@RequestMapping(value = "picCloudStoreOut")
public class PicCloudStoreController {

    @Autowired
    IPicCloudStoreService picCloudStoreService;

    /**
     * 图片存储
     */
    @ResponseBody
    @RequestMapping(value = "doPictureStore", method = RequestMethod.POST)
    public String doPictureStore(@RequestBody Map<String, String> map) {
        return picCloudStoreService.doPictureStore(JSON.toJSONString(map));
    }

}
