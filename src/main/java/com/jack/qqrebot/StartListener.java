package com.jack.qqrebot;

import com.jack.qqrebot.utils.CQUtils;
import com.jack.qqrebot.utils.SendMsgUtils;
import com.jack.qqrebot.utils.UpdateUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: mujj
 * @Date: 2019/5/3 16:42
 * @Description:
 * @Version: 1.0
 */
@Component
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {

    private static boolean flag = false;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(flag){
            if (contextRefreshedEvent.getApplicationContext().getParent() == null) {

            }
            flag = false;
        }
    }
}
