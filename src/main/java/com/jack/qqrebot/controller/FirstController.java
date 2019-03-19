package com.jack.qqrebot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.service.ranking.RankingService;
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


    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public void get(@RequestBody String message) throws UnsupportedEncodingException {

        JSONObject jsonObject = JSON.parseObject(message);
        String messageType = jsonObject.getString("message_type");
        if("group".equals(messageType)){
            sendService.dealGroupMsg(message);
        }
    }
}
