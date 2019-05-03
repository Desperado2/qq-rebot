package com.jack.qqrebot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.service.ranking.RankingService;
import com.jack.qqrebot.service.SendServiceI;
import com.jack.qqrebot.utils.CQUtils;
import com.jack.qqrebot.utils.SendMsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;


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

    @RequestMapping(value = "/github/notice", method = RequestMethod.POST)
    public void github(@RequestBody String message) throws UnsupportedEncodingException {
        JSONObject jsonObject = JSON.parseObject(message);
        String action = jsonObject.getString("action");
        String pName = jsonObject.getJSONObject("repository").getString("name");
        String userName = jsonObject.getJSONObject("sender").getString("login");
        String msg = "用户:"+userName+" "+action +"了 "+pName+"项目";
        SendMsgUtils.sendGroupMsg(89303705,msg);
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public void github() {
        List<Integer> groupList = CQUtils.getGroupList();
        String messages = "通知\n机器人即将开始升级，升级期间无法使用，升级完成后会通知，谢谢";
        groupList.forEach(groupId->SendMsgUtils.sendGroupMsg(groupId, messages));
    }
}
