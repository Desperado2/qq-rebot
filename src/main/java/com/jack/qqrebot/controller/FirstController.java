package com.jack.qqrebot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.service.ReceiveServiceI;
import com.jack.qqrebot.service.SendServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;


@RestController
public class FirstController {

    @Autowired
    private SendServiceI sendService;
    @Autowired
    private ReceiveServiceI receiveService;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public void get(@RequestBody String message) throws UnsupportedEncodingException {

        JSONObject jsonObject = JSON.parseObject(message);
        String message1 = jsonObject.getString("message");
        String messageType = jsonObject.getString("message_type");
        if("group".equals(messageType)){
            if(message1.contains("[CQ:at,qq=1244623542]")){
                Integer group_id = jsonObject.getInteger("group_id");
                message1 = message1.replace("[CQ:at,qq=1244623542]","").trim();
                if(!StringUtils.isEmpty(message1) && message1.contains("诗")){
                    sendService.sendPoetry(group_id,message1.replace("诗","").replace(" ",""));
                }else if(!StringUtils.isEmpty(message1) && message1.contains("新闻")){
                    sendService.sendNews(group_id,message);
                }else if(!StringUtils.isEmpty(message1) && (message1.contains("段子") || message1.contains("笑话"))){
                    sendService.sendSatin(group_id,message);
                }else if(!StringUtils.isEmpty(message1) && message1.contains("美图")){
                    sendService.sendMeiTu(group_id,message1.replace("美图","").replace(" ",""));
                }else if(!StringUtils.isEmpty(message1) && message1.contains("音乐")){
                    sendService.sendMusic(group_id,message1.replace("音乐","").replace(" ",""));
                }else if(!StringUtils.isEmpty(message1) && message1.contains("天气")){
                    sendService.sendWeather(group_id,message1.replace("天气","").replace(" ",""));
                }else if(!StringUtils.isEmpty(message1) && (message1.contains("微博") || (message1.contains("热搜")))){
                    sendService.sendWeibo(group_id,message1.replace("微博","").replace("热搜","").replace(" ",""));
                }else if(!StringUtils.isEmpty(message1) && (message1.contains("菜单"))){
                    sendService.sendMenu(group_id,message1);
                }else if(!StringUtils.isEmpty(message1) && (message1.contains("运势") || (message1.contains("星座")))){
                    sendService.sendLuck(group_id,message1.replace("运势","").replace("星座","").replace(" ",""));
                }else if(!StringUtils.isEmpty(message1) && (message1.contains("黄历"))){
                    sendService.coderCalendar(group_id,message);
                }else if(!StringUtils.isEmpty(message1) && (message1.contains("毒鸡汤"))){
                    sendService.goodLight(group_id,message);
                }else if(!StringUtils.isEmpty(message1) && (message1.contains("排行榜"))){
                    receiveService.phb(group_id,message1);
                }else {
                    sendService.tuling(group_id,message1);
                }
            }
        }
    }
}
