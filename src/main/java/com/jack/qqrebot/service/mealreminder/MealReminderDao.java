package com.jack.qqrebot.service.mealreminder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MealReminderDao extends JpaRepository<MealReminderVo,Integer> {
    MealReminderVo findByUserId(Integer userId);
    List<MealReminderVo> queryAllByGroupId(Integer groupId);
}
