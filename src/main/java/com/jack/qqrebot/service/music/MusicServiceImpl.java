package com.jack.qqrebot.service.music;

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
 * @Date: 2019/3/19 16:42
 * @Description:
 * @Version: 1.0
 */
@Service("musicService")
public class MusicServiceImpl implements MusicService {

    @Override
    public String getMusicByName(String musicName) {
        String message ="";
        String s = null;
        try {
            s = HttpUtils.sendGet("https://api.apiopen.top/searchMusic", "name="+URLEncoder.encode(musicName,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray musics = jsonObject.getJSONArray("result");
        if(!StringUtils.isEmpty(musics) &&musics.size() > 0){
            JSONObject music = musics.getJSONObject(0);
            String link = music.getString("link").split("\\?")[1].split("=")[1];
            message ="[CQ:music,type=163,id="+link+"]";
        }else {
            message="抱歉，没有找到";
        }
        return message;
    }
}
