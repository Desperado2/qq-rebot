package com.jack.qqrebot.utils;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @Auther: mujj
 * @Date: 2019/3/22 16:11
 * @Description:
 * @Version: 1.0
 */
public class LongUrlToShortUrlUtils {
    
    public static String longToShort(String longUrl){
        String url="https://xgg.kim/api/";
        try {
            longUrl = URLEncoder.encode(longUrl,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = HttpUtils.sendGet(url,"key=TscAjTarPtVD&url="+longUrl);
        return result;
    }
}
