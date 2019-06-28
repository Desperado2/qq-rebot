package com.jack.qqrebot.service.saylove;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Auther: mujj
 * @Date: 2019/5/3 12:40
 * @Description:
 * @Version: 1.0
 */
@Service("sayLoveService")
public class SayLoveServiceImpl implements SayLoveService {

    @Value("${desperado.saylove.apikey:#{null }}")
    private String apiKey;

    @Override
    public String getLoveRandom() {
        if(StringUtils.isEmpty(apiKey)){
            try {
                throw new Exception("请配置apikey");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String url ="http://api.tianapi.com/txapi/saylove/";
        String param ="key="+apiKey;
        String result = HttpUtils.sendGet(url, param);
        JSONObject object = JSONObject.parseObject(result);
        int code = object.getInteger("code");
        if(code == 200){
            JSONArray newslist = object.getJSONArray("newslist");
            return newslist.getJSONObject(0).getString("content");
        }else{
            return object.getString("msg");
        }
    }
}
