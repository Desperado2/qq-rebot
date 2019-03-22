package com.jack.qqrebot.service.news;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import com.jack.qqrebot.utils.LongUrlToShortUrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 16:44
 * @Description:
 * @Version: 1.0
 */
@Service("newsService")
public class NewsServiceImpl implements NewsService {

    @Override
    public String getNewsByRandom()  {
        StringBuffer message = new StringBuffer();
        String s = HttpUtils.sendGet("https://www.apiopen.top/journalismApi", "");
        JSONObject jsonObject = JSON.parseObject(s);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        JSONArray toutiao = jsonObject1.getJSONArray("toutiao");
        message.append("今日头条新闻如下:").append("\n\n");
        if(!StringUtils.isEmpty(toutiao) &&toutiao.size() > 0){
            for (int i=0;i<toutiao.size();i++){
                JSONObject object = toutiao.getJSONObject(i);
                message.append((i+1));
                message.append(".[");
                message.append(object.getString("source"));
                message.append("]");
                message.append(object.getString("title")).append("\n");
                message.append(LongUrlToShortUrlUtils.longToShort(object.getString("link")));
                message.append("\n\n");
            }
        }
        return message.toString();
    }
}
