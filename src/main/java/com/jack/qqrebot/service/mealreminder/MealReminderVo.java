package com.jack.qqrebot.service.mealreminder;

import javax.persistence.*;

@Table(name = "meal_reminder")
@Entity
public class MealReminderVo {
    @Id
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "group_id")
    private Integer groupId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }
}
