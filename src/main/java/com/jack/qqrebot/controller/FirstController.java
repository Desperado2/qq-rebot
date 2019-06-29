package com.jack.qqrebot.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.service.SendServiceI;
import com.jack.qqrebot.service.modian.ProjectService;
import com.jack.qqrebot.utils.CQUtils;
import com.jack.qqrebot.utils.SendMsgUtils;
import com.jack.qqrebot.utils.UpdateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;


@RestController
public class FirstController {

    private final SendServiceI sendService;

    private final ProjectService projectService;

    @Autowired
    public FirstController(SendServiceI sendService,ProjectService projectService) {
        this.sendService = sendService;
        this.projectService = projectService;
    }


    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public void get(@RequestBody String message) throws UnsupportedEncodingException {
        JSONObject jsonObject = JSON.parseObject(message);
        String messageType = jsonObject.getString("message_type");
        String postType = jsonObject.getString("post_type");
        if(postType.equals("notice")){
             sendService.dealNotice(message);
        }else if("group".equals(messageType)) {
            sendService.dealGroupMsg(message);
        }else if("private".equals(messageType)){
            sendService.dealPrivateMsg(message);
        }
    }



    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public void update() {
        String messages = "通知\n机器人将于10秒后开始升级，升级期间无法使用，升级完成后会通知，谢谢";
       SendMsgUtils.sendGroupMsg("604195931", messages);
    }

    @RequestMapping(value = "/updateSuccess", method = RequestMethod.GET)
    public void updateSuccess() {
        String messages = "通知\n机器人升级完成,\n"+UpdateUtils.getUpdate();
        SendMsgUtils.sendGroupMsg("604195931", messages);
    }


}
