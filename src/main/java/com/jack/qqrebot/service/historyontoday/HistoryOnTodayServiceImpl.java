package com.jack.qqrebot.service.historyontoday;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: mujj
 * @Date: 2019/3/22 15:27
 * @Description:
 * @Version: 1.0
 */
@Service("historyOnTodayService")
public class HistoryOnTodayServiceImpl implements HistoryOnTodayService {
    @Override
    public String getHistory() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String iday = sdf.format(new Date());
        StringBuffer result= new StringBuffer();
        String s = HttpUtils.sendGet("https://baike.baidu.com/cms/home/eventsOnHistory/03.json?_=1553238091404", "");
        JSONArray array = JSONObject.parseObject(s).getJSONObject(iday.substring(0,2)).getJSONArray(iday);
        result.append("今天是").append(sdf1.format(new Date())).append(",历史上的今天发生了一下的大事").append("\n\n");


        for (int i= 0;i < array.size() ;i++){
            JSONObject object = array.getJSONObject(i);
            String year = object.getString("year");
            String title = object.getString("title");
            String link = object.getString("link");
            title  = title.replaceAll("<.*?>", "").replace("\n","");
            result.append(year).append(" ").append(title).append("\n").append(link).append("\n\n");
        }
        return result.toString();
    }
}
