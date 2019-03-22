package com.jack.qqrebot.service.meitu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 16:36
 * @Description:
 * @Version: 1.0
 */
@Service("meituService")
public class MeituServiceImpl implements MeituService {

    @Override
    public String getImageByRandom()  {
        String s = HttpUtils.sendGet("https://www.apiopen.top/meituApi", "");
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray musics = jsonObject.getJSONArray("data");
        String message ="";
        if(!StringUtils.isEmpty(musics) &&musics.size() > 0){
            JSONObject music = musics.getJSONObject(0);
            message = music.getString("url");
        }
        message = "找到的美图如下\n[CQ:image,file="+message+"]";
        return message;
    }
}
