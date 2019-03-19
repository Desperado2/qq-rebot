package com.jack.qqrebot.service.weather;

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
 * @Date: 2019/3/19 17:08
 * @Description:
 * @Version: 1.0
 */
@Service("weatherService")
public class WeatherServiceImpl implements WeatherService {
    @Override
    public String getTodayWeather() {
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
        return weatherInfo;
    }

    @Override
    public String getWeatherByCity(String cityName) throws UnsupportedEncodingException {

        String s = HttpUtils.sendGet("https://www.apiopen.top/weatherApi", "city="+URLEncoder.encode(cityName,"utf-8"));
        StringBuffer message = new StringBuffer();
        JSONObject jsonObject = JSON.parseObject(s);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        JSONArray forecast = jsonObject1.getJSONArray("forecast");
        if(!StringUtils.isEmpty(forecast) &&forecast.size() > 0){
            for (int i =0;i<forecast.size();i++){
                JSONObject object = forecast.getJSONObject(i);
                String msg = object.getString("date")+"\n"+object.getString("type")+"\n"+object.getString("low")+"~"+object.getString("high");
                message.append(msg).append("\n").append("\n");
            }
        }
        message.append(jsonObject1.getString("ganmao"));
       return message.toString();
    }
}
