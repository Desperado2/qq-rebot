package com.jack.qqrebot.controller;

import com.jack.qqrebot.service.SendServiceI;
import com.jack.qqrebot.service.modian.ProjectDateVo;
import com.jack.qqrebot.service.modian.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @Auther: mujj
 * @Date: 2019/6/29 09:34
 * @Description:
 * @Version: 1.0
 */

@Controller
public class SecondController {

    private final ProjectService projectService;

    @Autowired
    public SecondController( ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value="/md",method = RequestMethod.GET)
    public String getChart(Map<String,Object> map){
        ProjectDateVo projectDateVo = projectService.getCountGroupByDate();
        if(projectDateVo != null){
            map.put("data",projectDateVo);
        }else {
            map.put("data",new ProjectDateVo());
        }
        return "index";
    }

}
