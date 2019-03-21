package com.jack.qqrebot.service.tuling;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 17:03
 * @Description:
 * @Version: 1.0
 */
@Service("tulingService")
public class TulingServiceImpl implements TulingService{
    @Override
    public String getMsgByMsg(String message) throws UnsupportedEncodingException {
        boolean type = message.contains("|语音");
        message = message.replace("|","").replace("语音","");
        String ulr="http://openapi.tuling123.com/openapi/api/v2";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reqType","0");

        JSONObject jsonObject1 = new JSONObject();

        JSONObject o = new JSONObject();
        o.put("text",message);

        jsonObject1.put("inputText",o);
        jsonObject.put("perception",jsonObject1);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("apiKey","43e9392c847443c187f6f813f17370d9");
        jsonObject2.put("userId","Jack1995");
        jsonObject.put("userInfo",jsonObject2);

        String s = HttpUtils.sendPost(ulr, jsonObject.toJSONString());
        JSONObject object = JSONObject.parseObject(s);
        JSONArray result = object.getJSONArray("results");
        message = result.getJSONObject(0).getJSONObject("values").getString("text");
        String finalMessage = message;
        if(type){
            try {
                return sendVoice(finalMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return message;
        }
        return "";
    }

    private String sendVoice(String message) throws Exception{
        String token_url ="https://aip.baidubce.com/oauth/2.0/token?" +
                "grant_type=client_credentials&" +
                "client_id=FECheo7ltG2FTm3y173abLTM&" +
                "client_secret=1IIug14Gyz5EqNTQ2G1oHfQ2deBxzAcm&";

        String result = HttpUtils.sendPost(token_url, "");
        String access_token = JSONObject.parseObject(result).getString("access_token");
        String voice_url="https://tsn.baidu.com/text2audio";
        if(message.startsWith("?")){
            message="你说什么偶没有听清啊";
        }
        String params = "tex="+URLEncoder.encode(URLEncoder.encode(message,"utf-8"),"utf-8")+"&";
        params+="tok="+access_token+"&cuid="+UUID.randomUUID().toString()+"&ctp=1&lan=zh&per=4&aue=6";
        result = HttpUtils.getWav(voice_url, params);
        message = "[CQ:record,file="+result+"]";
        return message;
    }
}
