package com.jack.qqrebot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.service.SendServiceI;
import com.jack.qqrebot.utils.CQUtils;
import com.jack.qqrebot.utils.SendMsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.List;


@RestController
public class FirstController {

    private final SendServiceI sendService;

    @Autowired
    public FirstController(SendServiceI sendService) {
        this.sendService = sendService;
    }


    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public void get(@RequestBody String message) throws UnsupportedEncodingException {

        JSONObject jsonObject = JSON.parseObject(message);
        String messageType = jsonObject.getString("message_type");
        String postType = jsonObject.getString("post_type");
        if(postType.equals("notice")){
           sendService.dealNotice(message);
        }else if("group".equals(messageType)){
            sendService.dealGroupMsg(message);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public void github() {
        List<Integer> groupList = CQUtils.getGroupList();
        String messages = "通知\n机器人将于10秒后开始升级，升级期间无法使用，升级完成后会通知，谢谢";
        groupList.forEach(groupId->SendMsgUtils.sendGroupMsg(groupId, messages));
    }
}
