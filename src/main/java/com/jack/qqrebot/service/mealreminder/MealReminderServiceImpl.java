package com.jack.qqrebot.service.mealreminder;

import com.jack.qqrebot.utils.SendMsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mealReminderService")
public class MealReminderServiceImpl implements MealReminderService {
    @Autowired
    MealReminderDao mealReminderDao;

    @Override
    public void reminder() {
        List<MealReminderVo> list = mealReminderDao.findAll();
        list.forEach(mealReminderVo -> {
            String result = "[CQ:at,qq=" + mealReminderVo.getUserId() + "] 该点外卖了!";
            SendMsgUtils.sendGroupMsg(mealReminderVo.getGroupId().toString(), result);
        });
    }

    @Override
    public void add(MealReminderVo mealReminderVo) {
        mealReminderDao.save(mealReminderVo);
    }
}
