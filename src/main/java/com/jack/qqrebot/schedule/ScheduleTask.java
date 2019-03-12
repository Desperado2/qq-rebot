package com.jack.qqrebot.schedule;

import com.jack.qqrebot.service.SchedualServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTask {
    @Autowired
    private SchedualServiceI schedualService;
    //早上问候（每天早上八点）
    @Scheduled(cron = "0 0 8 * * ?")
    public void goodMorning() {
        schedualService.goodMorning();
    }
}
