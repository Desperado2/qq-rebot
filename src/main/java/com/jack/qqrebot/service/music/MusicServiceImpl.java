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
        if(StringUtils.isEmpty(musicName)){
            return "命令错误，正确格式给 [音乐 歌曲名称]";
        }
        String message ="";
        String s = null;
        try {
            s = HttpUtils.sendGet("http://s.music.163.com/search/get/","type=1&s="+URLEncoder.encode(musicName,"utf-8")+"&limit=1&offset=0");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(!StringUtils.isEmpty(s)){
            JSONObject jsonObject = JSON.parseObject(s);
            String link = jsonObject.getJSONObject("result").getJSONArray("songs").getJSONObject(0).getString("id");
            message ="[CQ:music,type=163,id="+link+"]";
        }else {
            message="抱歉，没有找到";
        }
        return message;
    }
}
