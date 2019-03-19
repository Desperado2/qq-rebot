package com.jack.qqrebot.service.satin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 17:01
 * @Description:
 * @Version: 1.0
 */
@Service("satinService")
public class SatinServiceImpl implements SatinService {

    @Override
    public String getSatinByRandom() throws UnsupportedEncodingException {
        String message = "";
        String s = HttpUtils.sendGet("https://www.apiopen.top/satinApi?type=2", "");
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray satin = jsonObject.getJSONArray("data");
        if(!StringUtils.isEmpty(satin) &&satin.size() > 0){
            Random rand = new Random();
            int i = rand.nextInt(satin.size());
            message = satin.getJSONObject(i).getString("text");
        }
        return message;
    }
}
