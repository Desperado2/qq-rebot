package com.jack.qqrebot.utils;

import com.jack.qqrebot.enumm.CqApi;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 16:22
 * @Description:
 * @Version: 1.0
 */
public class SendMsgUtils {

    private static final  String preUrl="http://127.0.0.1:5300/";

    public static String sendGroupMsg(int groupId, String message){

        try {
            String params = "group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8");
            String url = preUrl+CqApi.SEND_GROUP_MSG.getName();
            return HttpUtils.sendPost(url, params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
