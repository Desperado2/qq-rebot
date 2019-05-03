package com.jack.qqrebot;

import com.jack.qqrebot.utils.CQUtils;
import com.jack.qqrebot.utils.SendMsgUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: mujj
 * @Date: 2019/5/3 16:42
 * @Description:
 * @Version: 1.0
 */
@Component
public class StartService implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Integer> groupList = CQUtils.getGroupList();
        String messages = "通知\n机器人升级完成，谢谢";
        groupList.forEach(groupId->SendMsgUtils.sendGroupMsg(groupId, messages));
    }
}
