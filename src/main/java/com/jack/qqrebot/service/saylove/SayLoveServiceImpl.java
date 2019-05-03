package com.jack.qqrebot.service.saylove;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;

/**
 * @Auther: mujj
 * @Date: 2019/5/3 12:40
 * @Description:
 * @Version: 1.0
 */
@Service("sayLoveService")
public class SayLoveServiceImpl implements SayLoveService {
    @Override
    public String getLoveRandom() {
        String url ="http://api.tianapi.com/txapi/saylove/";
        String param ="key=e597d3305ebe6ffa035e90110c86235f";
        String result = HttpUtils.sendGet(url, param);
        JSONObject object = JSONObject.parseObject(result);
        int code = object.getInteger("code");
        if(code == 200){
            JSONArray newslist = object.getJSONArray("newslist");
            String content = newslist.getJSONObject(0).getString("content");
            return content;
        }else{
            String msg = object.getString("msg");
            return msg;
        }
    }
}
