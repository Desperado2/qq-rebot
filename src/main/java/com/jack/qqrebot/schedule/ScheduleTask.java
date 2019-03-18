package com.jack.qqrebot.schedule;

import com.jack.qqrebot.service.SchedualServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {
    @Autowired
    private SchedualServiceI schedualService;


    @Scheduled(cron = "0 0 0 * * ?")
    public void coderCalendar() {
        schedualService.coderCalendar();
    }

    //早上问候（每天早上八点）
    @Scheduled(cron = "0 0 8 * * ?")
    public void goodMorning() {
        schedualService.goodMorning();
    }
    //微博热搜
    @Scheduled(cron = "0 40 11 * * ?")
    public void sendWeiBo(){
        schedualService.weibo();
    }

    //每日新闻
    @Scheduled(cron = "0 30 17 * * ?")
    public void everyDayNews(){
        schedualService.everyDayNews();
    }

    @Scheduled(cron = "0 30 22 * * ?")
    public void light(){
        schedualService.goodLight();
    }


}
