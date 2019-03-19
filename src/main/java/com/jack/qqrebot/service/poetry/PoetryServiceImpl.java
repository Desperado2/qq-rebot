package com.jack.qqrebot.service.poetry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 16:48
 * @Description:
 * @Version: 1.0
 */
@Service("poetryService")
public class PoetryServiceImpl implements PoetryService {
    @Override
    public String getPoetryByKeyWord(String keyWord) throws UnsupportedEncodingException {
        String s ="";
        JSONObject jsonObject= null;
        JSONObject jsonObject1 = null;
        if(StringUtils.isEmpty(keyWord)){
            s = HttpUtils.sendGet("https://api.apiopen.top/recommendPoetry", "");
            jsonObject = JSONObject.parseObject(s);
            jsonObject1 = jsonObject.getJSONObject("result");
        }else {
            s = HttpUtils.sendGet("https://api.apiopen.top/likePoetry", "name="+URLEncoder.encode(keyWord,"utf-8"));
            jsonObject = JSONObject.parseObject(s);
            JSONArray result = jsonObject.getJSONArray("result");
            if(result .size() > 0){
                jsonObject1 = jsonObject.getJSONArray("result").getJSONObject(0);
            }else{
                s = HttpUtils.sendGet("https://api.apiopen.top/recommendPoetry", "");
                jsonObject = JSON.parseObject(s);
                jsonObject1 = jsonObject.getJSONObject("result");
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n");
        stringBuffer.append(jsonObject1.getString("title"));
        stringBuffer.append("\n");
        stringBuffer.append(jsonObject1.getString("authors"));
        stringBuffer.append("\n");
        stringBuffer.append(jsonObject1.getString("content").replace("|","\n"));
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }
}
