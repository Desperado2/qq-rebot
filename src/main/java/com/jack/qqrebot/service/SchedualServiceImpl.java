package com.jack.qqrebot.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service("schedualService")
public class SchedualServiceImpl implements SchedualServiceI {

    @Autowired
    private SendServiceI sendService;

    @Override
    public void goodMorning() {
        //获取天气
        String weatherUrl ="http://t.weather.sojson.com/api/weather/city/101210101";
        String result = HttpUtils.sendGet(weatherUrl,"");
        JSONObject jsonObject = JSON.parseObject(result);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        JSONArray jsonArray = jsonObject1.getJSONArray("forecast");
        JSONObject object =jsonArray.getJSONObject(0);
        String morning = "Good Morning";
        String timeAndLocal = jsonObject.getString("date").substring(0,jsonObject.getString("date").length()-2)+ object.get("date")+"   "+"杭州市";
        String weather = object.getString("type");
        String wendu = object.getString("low")+" -- "+object.getString("high");
        String feng = object.getString("fx")+object.getString("fl");
        String notice = object.getString("notice");
        String weatherInfo = morning+"\n"+timeAndLocal+"\n"+weather+"\n"+wendu+"\n"+feng+"\n"+notice;

        //获取新闻
        String message = "";
        String s = HttpUtils.sendGet("https://www.apiopen.top/journalismApi", "");
        JSONObject object1 = JSON.parseObject(s);
        JSONObject object2 = object1.getJSONObject("data");
        JSONArray toutiao = object2.getJSONArray("toutiao");
        if(!StringUtils.isEmpty(toutiao) &&toutiao.size() > 0){
            for (int i=0;i<toutiao.size();i++){
                JSONObject object3 = toutiao.getJSONObject(i);
                message += (i+1)+".["+object3.getString("source")+"]"+object3.getString("title")+"\n\n";
            }
        }

        String groups = HttpUtils.sendPost("http://127.0.0.1:5300/get_group_list", "");
        String messages = "天气:\n\n"+weatherInfo+"\n\n早间新闻:\n\n"+message;
        JSONArray array = JSONObject.parseObject(groups).getJSONArray("data");
        for (int i=0;i<array.size();i++){
            JSONObject object3 = array.getJSONObject(i);
            Integer group_id = object3.getInteger("group_id");
            sendService.sendTask(group_id,messages);
        }

    }
}
