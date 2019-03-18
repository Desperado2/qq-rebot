package com.jack.qqrebot.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.CoderCalendar;
import com.jack.qqrebot.utils.HttpUtils;
import com.jack.qqrebot.utils.XzUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;


@Service("sendService")
public class SendServiceImpl implements SendServiceI {

    String url ="http://127.0.0.1:5300/";

    @Override
    public String sendTask(int groupId, String message) throws UnsupportedEncodingException{
        return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8"));
    }

    @Override
    public String sendPoetry(int groupId, String message) throws UnsupportedEncodingException {
        String s ="";
        JSONObject jsonObject= null;
        JSONObject jsonObject1 = null;
        if(StringUtils.isEmpty(message)){
             s = HttpUtils.sendGet("https://api.apiopen.top/recommendPoetry", "");
            jsonObject = JSONObject.parseObject(s);
            jsonObject1 = jsonObject.getJSONObject("result");
        }else {
            s = HttpUtils.sendGet("https://api.apiopen.top/likePoetry", "name="+URLEncoder.encode(message,"utf-8"));
            jsonObject = JSONObject.parseObject(s);
            JSONArray result = jsonObject.getJSONArray("result");
            if(result .size() > 0){
                jsonObject1 = jsonObject.getJSONArray("result").getJSONObject(0);
            }else{
                s = HttpUtils.sendGet("https://api.apiopen.top/recommendPoetry", "");
                jsonObject = JSON.parseObject(s);
                jsonObject1 = jsonObject.getJSONObject("result");
            }
        }
        message = "\n"+jsonObject1.getString("title")+"\n"+jsonObject1.getString("authors")+"\n"+jsonObject1.getString("content").replace("|","\n");
        return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8"));
    }

    @Override
    public String sendNews(int groupId, String message) throws UnsupportedEncodingException{
        message = "";
        String s = HttpUtils.sendGet("https://www.apiopen.top/journalismApi", "");
        JSONObject jsonObject = JSON.parseObject(s);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        JSONArray toutiao = jsonObject1.getJSONArray("toutiao");
        if(!StringUtils.isEmpty(toutiao) &&toutiao.size() > 0){
            for (int i=0;i<(toutiao.size()>5?5:toutiao.size());i++){
                JSONObject object = toutiao.getJSONObject(i);
                message += (i+1)+".["+object.getString("source")+"]"+object.getString("title")+"\n\n";
            }
        }
        return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8"));
    }

    @Override
    public String sendSatin(int groupId, String message) throws UnsupportedEncodingException{
        String s = HttpUtils.sendGet("https://www.apiopen.top/satinApi?type=2", "");
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray satin = jsonObject.getJSONArray("data");
        if(!StringUtils.isEmpty(satin) &&satin.size() > 0){
            Random rand = new Random();
            int i = rand.nextInt(satin.size());
           message = satin.getJSONObject(i).getString("text");
        }
        return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8"));
    }

    @Override
    public String sendMeiTu(int groupId, String message) throws UnsupportedEncodingException {
        String s = HttpUtils.sendGet("https://www.apiopen.top/meituApi", "");
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray musics = jsonObject.getJSONArray("data");

        if(!StringUtils.isEmpty(musics) &&musics.size() > 0){
            JSONObject music = musics.getJSONObject(0);
            message = music.getString("url");
            System.out.println(message);
        }
        //String image = new MessageBuilder().add(new ComponentImage(message)).toString();
        message = "找到的美图如下\n[CQ:image,file="+message+"]";
        return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8"));
    }

    @Override
    public String sendMusic(int groupId, String message) throws UnsupportedEncodingException {
        String s = HttpUtils.sendGet("https://api.apiopen.top/searchMusic", "name="+URLEncoder.encode(message,"utf-8"));
        JSONObject jsonObject = JSON.parseObject(s);
        JSONArray musics = jsonObject.getJSONArray("result");
        if(!StringUtils.isEmpty(musics) &&musics.size() > 0){
            JSONObject music = musics.getJSONObject(0);
            String link = music.getString("link").split("\\?")[1].split("=")[1];
            message ="[CQ:music,type=163,id="+link+"]";
        }else {
            message="抱歉，没有找到";
        }
        return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8"));
    }

    @Override
    public String sendWeather(int groupId, String message) throws UnsupportedEncodingException {
        String s = HttpUtils.sendGet("https://www.apiopen.top/weatherApi", "city="+URLEncoder.encode(message,"utf-8"));
        message = "";
        JSONObject jsonObject = JSON.parseObject(s);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        JSONArray forecast = jsonObject1.getJSONArray("forecast");
        if(!StringUtils.isEmpty(forecast) &&forecast.size() > 0){
            for (int i =0;i<forecast.size();i++){
                JSONObject object = forecast.getJSONObject(i);
                String msg = object.getString("date")+"\n"+object.getString("type")+"\n"+object.getString("low")+"~"+object.getString("high");
                message += msg+"\n\n";
            }
        }
        message += jsonObject1.getString("ganmao");
        return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8"));
    }

    @Override
    public String sendWeibo(int groupId, String message) throws UnsupportedEncodingException {
        message ="微博实时热搜榜如下:\n\n";
        String url1 ="https://s.weibo.com/top/summary?Refer=top_hot&topnav=1&wvr=6";
        String get = HttpUtils.sendGet(url1, "");
        Document document = Jsoup.parse(get);
        Element element = document.select("div[id=pl_top_realtimehot]").get(0);
        Elements elements = element.select("table").get(0).select("tbody").select("tr");
        for (int i =0;i<Math.min(elements.size(),10);i++){
            Element element1 = elements.get(i);
            Element element2 = element1.select("td").get(1);
            message += (i+1)+". "+element2.text()+"\n\n";
        }
        return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8"));
    }

    @Override
    public String sendMenu(int groupId, String message) throws UnsupportedEncodingException {
        message = "功能列表\n\n";
        String[] array = new String[]{"诗","新闻","笑话/段子","美图","音乐","天气","微博/热搜","老黄历","星座","毒鸡汤"};
        for (int i=0;i<array.length;i++){
            message += (i+1)+". "+array[i]+"\n";
        }
        return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+message+"&auto_escape=true");
    }

    @Override
    public String sendLuck(int groupId, String message) throws UnsupportedEncodingException {
        String s =XzUtils.getXz(message);
        if(StringUtils.isEmpty(s)){
            return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message=没有找到你要查询的星座&auto_escape=true");
        }
        message =message+"今日运势:\n\n";
        String url1 ="https://www.xzw.com/fortune/"+s;
        String get = HttpUtils.sendGet(url1, "");
        Document document = Jsoup.parse(get);
        Elements elements = document.select("dd").select("li");
        for (int i =0 ;i <elements.size(); i++){
            Element element = elements.get(i);
            if(element.select("em").size() > 0){
                String label = element.select("label").text();
                int i1 = Integer.parseInt(element.select("em").attr("style").replace("px;", "").replace("width:","").trim())/12;
                message+= label + i1+"颗星\n";
            }
        }
        message += "详情:"+url1;
        return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8")+"&auto_escape=true");
    }

    @Override
    public String tuling(int groupId, String message) throws UnsupportedEncodingException {
        boolean type = message.contains("|语音");
        message = message.replace("|","").replace("语音","");
        String ulr="http://openapi.tuling123.com/openapi/api/v2";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reqType","0");

        JSONObject jsonObject1 = new JSONObject();

        JSONObject o = new JSONObject();
        o.put("text",message);

        jsonObject1.put("inputText",o);
        jsonObject.put("perception",jsonObject1);

        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("apiKey","43e9392c847443c187f6f813f17370d9");
        jsonObject2.put("userId","Jack1995");
        jsonObject.put("userInfo",jsonObject2);

        String s = HttpUtils.sendPost(ulr, jsonObject.toJSONString());
        JSONObject object = JSONObject.parseObject(s);
        JSONArray result = object.getJSONArray("results");
        message = result.getJSONObject(0).getJSONObject("values").getString("text");
        String finalMessage = message;
        if(type){
            try {
                return sendVoice(groupId, finalMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8"));
        }
        return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8"));
    }

    @Override
    public String coderCalendar(int groupId, String message) throws UnsupportedEncodingException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String iday = sdf.format(new Date());
        File file  = new File("C:\\CQPro\\data\\image\\"+iday+".jpg");
        if(!file.exists()){
            CoderCalendar.createImage();
        }
        message = iday+".jpg";
        message = "[CQ:image,file="+message+"]";
        return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8"));
    }

    @Override
    public String goodLight(int groupId, String message) throws UnsupportedEncodingException {
        String url1 ="http://duyan.fooor.cn/word.php";
        message = HttpUtils.sendGet(url1, "");
        return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8"));

    }

    private String sendVoice(Integer groupId,String message) throws Exception{
        String token_url ="https://aip.baidubce.com/oauth/2.0/token?" +
                "grant_type=client_credentials&" +
                "client_id=FECheo7ltG2FTm3y173abLTM&" +
                "client_secret=1IIug14Gyz5EqNTQ2G1oHfQ2deBxzAcm&";

        String result = HttpUtils.sendPost(token_url, "");
        String access_token = JSONObject.parseObject(result).getString("access_token");
        String voice_url="https://tsn.baidu.com/text2audio";
        if(message.startsWith("?")){
            message="你说什么偶没有听清啊";
        }
        String params = "tex="+URLEncoder.encode(URLEncoder.encode(message,"utf-8"),"utf-8")+"&";
        params+="tok="+access_token+"&cuid="+UUID.randomUUID().toString()+"&ctp=1&lan=zh&per=4&aue=6";
        result = HttpUtils.getWav(voice_url, params);
        message = "[CQ:record,file="+result+"]";
        return HttpUtils.sendPost(url+"send_group_msg","group_id="+groupId+"&message="+URLEncoder.encode(message,"utf-8"));
    }
}
