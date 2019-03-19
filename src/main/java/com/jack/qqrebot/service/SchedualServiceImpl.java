package com.jack.qqrebot.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.CQApiServices.CqApi;
import com.jack.qqrebot.utils.HttpUtils;
import com.jack.qqrebot.utils.ZHConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

        String s = HttpUtils.sendGet("https://www.dailyenglishquote.com/", "");
        Document document = Jsoup.parse(s);
        Element element = document.getElementById("content");
        Element element1 = element.getElementsByClass("post").get(0);
        String msg = element1.getElementsByTag("strong").get(0).text()+"\n";
        msg += element1.getElementsByTag("p").get(1).text()+"\n";
        msg += element1.getElementsByTag("li").get(0).text()+"\n";
        msg += element1.getElementsByTag("li").get(1).text();
        try {
            msg = ZHConverter.transformation(msg,ZHConverter.target.TCcharacter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        String groups = HttpUtils.sendPost("http://127.0.0.1:5300/get_group_list", "");
        String messages = "天气:\n\n"+weatherInfo+"\n\n"+msg;
        JSONArray array = JSONObject.parseObject(groups).getJSONArray("data");
        for (int i=0;i<array.size();i++){
            JSONObject object3 = array.getJSONObject(i);
            Integer group_id = object3.getInteger("group_id");
            try {
                sendService.sendTask(group_id,messages);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void weibo() {
        String groups = HttpUtils.sendPost("http://127.0.0.1:5300/get_group_list", "");
        JSONArray array = JSONObject.parseObject(groups).getJSONArray("data");
        for (int i=0;i<array.size();i++){
            JSONObject object3 = array.getJSONObject(i);
            Integer group_id = object3.getInteger("group_id");
            try {
                sendService.sendWeibo(group_id,"");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void everyDayNews() {
        String groups = HttpUtils.sendPost("http://127.0.0.1:5300/get_group_list", "");
        JSONArray array = JSONObject.parseObject(groups).getJSONArray("data");
        for (int i=0;i<array.size();i++){
            JSONObject object3 = array.getJSONObject(i);
            Integer group_id = object3.getInteger("group_id");
            try {
                sendService.sendNews(group_id,"");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void goodLight() {
        String groups = HttpUtils.sendPost("http://127.0.0.1:5300/get_group_list", "");
        JSONArray array = JSONObject.parseObject(groups).getJSONArray("data");
        for (int i=0;i<array.size();i++){
            JSONObject object3 = array.getJSONObject(i);
            Integer groupId = object3.getInteger("group_id");
            try {
                String url1 ="http://duyan.fooor.cn/word.php";
                String message = HttpUtils.sendGet(url1, "");
                message += "\n各位晚安";
                 HttpUtils.sendPost("http://127.0.0.1:5300/"+CqApi.SEND_GROUP_MSG,"group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void coderCalendar() {
        try {
            sendService.coderCalendar(89303705,"");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
