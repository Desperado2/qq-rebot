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
        return morning+"\n"+timeAndLocal+"\n"+weather+"\n"+wendu+"\n"+feng+"\n"+notice;
    }

    @Override
    public String getWeatherByCity(String cityName) throws UnsupportedEncodingException {
        String s = HttpUtils.sendGet("https://www.apiopen.top/weatherApi", "city="+URLEncoder.encode(cityName,"utf-8"));
        StringBuilder message = new StringBuilder();
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

    @Override
    public String getCurrWeatherByCity(String cityName) throws UnsupportedEncodingException {
        cityName = cityName.replace("市","").replace("区","");
        String s = HttpUtils.sendGet(" https://www.tianqiapi.com/api/", "version=v6&city="+URLEncoder.encode(cityName,"utf-8"));
        StringBuilder message = new StringBuilder();
        if(StringUtils.isEmpty(s)){
            return "很抱歉，没有查询到";
        }
        JSONObject jsonObject = JSON.parseObject(s);
        message.append(jsonObject.getString("city")).append("实时天气情况如下：\n\n");
        message.append("天    气: ").append(jsonObject.getString("wea")).append("\n");
        message.append("当前温度: ").append(jsonObject.getString("tem")).append("\n");
        message.append("风    向: ").append(jsonObject.getString("win")).append("\n");
        message.append("风    速: ").append(jsonObject.getString("win_speed")).append("   ").append(jsonObject.getString("win_meter")).append("\n");
        message.append("湿    度：").append(jsonObject.getString("humidity")).append("\n");
        message.append("能 见 度: ").append(jsonObject.getString("visibility")).append("\n");
        message.append("气    压: ").append(jsonObject.getString("pressure")).append("hPa").append("\n");
        message.append("空气质量: ").append(jsonObject.getString("air_level")).append("(").append(jsonObject.getString("air")).append(")\n");
        message.append("PM  2.5: ").append(jsonObject.getString("air_pm25")).append("\n");
        message.append("提    示：").append(jsonObject.getString("air_tips")).append("\n\n");
        JSONObject alarm = jsonObject.getJSONObject("alarm");
        String alarmType = alarm.getString("alarm_type");
        String alarmLevel = alarm.getString("alarm_level");
        String alarmContent = alarm.getString("alarm_content");

        if(!StringUtils.isEmpty(alarmType)){
            message.append("最新天气预警：").append("\n");
            message.append("预警类型：").append(alarmType).append("\n");
            message.append("预警等级：").append(alarmLevel).append("\n");
            message.append("预警内容：").append(alarmContent).append("\n\n");
        }

        message.append("查询多日天气请发送 [多日天气 杭州]");
        return message.toString();
    }
}
