package com.jack.qqrebot;

import com.jack.qqrebot.utils.CQUtils;
import com.jack.qqrebot.utils.SendMsgUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            List<Integer> groupList = CQUtils.getGroupList();
            String messages = "通知\n机器人升级完成，谢谢";
            groupList.forEach(groupId -> SendMsgUtils.sendGroupMsg(groupId, messages));
        }
    }
}
