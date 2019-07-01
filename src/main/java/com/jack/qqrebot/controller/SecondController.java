package com.jack.qqrebot.controller;

import com.jack.qqrebot.service.SendServiceI;
import com.jack.qqrebot.service.modian.ProjectDateVo;
import com.jack.qqrebot.service.modian.ProjectService;
import com.jack.qqrebot.service.modian.UserRankingVo;
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

    @RequestMapping(value="/jst",method = RequestMethod.GET)
    public String index(Map<String,Object> map){
        List<UserRankingVo> userRanking = projectService.getUserRanking();
        if(userRanking != null){
            map.put("userRanking",userRanking);
        }else {
            map.put("userRanking",new UserRankingVo());
        }
        return "jst";
    }

    @RequestMapping(value="/rank",method = RequestMethod.GET)
    public String rank(Map<String,Object> map){
        List<UserRankingVo> userRanking = projectService.getUserRanking();
        if(userRanking != null){
            map.put("userRanking",userRanking);
        }else {
            map.put("userRanking",new UserRankingVo());
        }
        return "ranking";
    }

}
