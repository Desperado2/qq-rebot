package com.jack.qqrebot.service.modian;

import java.util.Date;
import java.util.List;

/**
 * @Auther: mujj
 * @Date: 2019/6/28 22:37
 * @Description:
 * @Version: 1.0
 */
public class ProjectDateVo {

    private Long todayUserCount;

    private Long todayUserTotal;

    private Double todayMoney;

    private Long allUserCount;

    private Long allUserTotal;

    private Double allMoney;

    private List<String> dates;

    private List<Long> usersCount;

    private List<Long> usersTotal;

    private List<Double> moneys;

    private List<String> todayUsers;

    public Long getTodayUserCount() {
        return todayUserCount;
    }

    public void setTodayUserCount(Long todayUserCount) {
        this.todayUserCount = todayUserCount;
    }

    public Long getTodayUserTotal() {
        return todayUserTotal;
    }

    public void setTodayUserTotal(Long todayUserTotal) {
        this.todayUserTotal = todayUserTotal;
    }

    public Double getTodayMoney() {
        return todayMoney;
    }

    public void setTodayMoney(Double todayMoney) {
        this.todayMoney = todayMoney;
    }

    public Long getAllUserCount() {
        return allUserCount;
    }

    public void setAllUserCount(Long allUserCount) {
        this.allUserCount = allUserCount;
    }

    public Long getAllUserTotal() {
        return allUserTotal;
    }

    public void setAllUserTotal(Long allUserTotal) {
        this.allUserTotal = allUserTotal;
    }

    public Double getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(Double allMoney) {
        this.allMoney = allMoney;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<Long> getUsersCount() {
        return usersCount;
    }

    public void setUsersCount(List<Long> usersCount) {
        this.usersCount = usersCount;
    }

    public List<Long> getUsersTotal() {
        return usersTotal;
    }

    public void setUsersTotal(List<Long> usersTotal) {
        this.usersTotal = usersTotal;
    }

    public List<Double> getMoneys() {
        return moneys;
    }

    public void setMoneys(List<Double> moneys) {
        this.moneys = moneys;
    }

    public List<String> getTodayUsers() {
        return todayUsers;
    }

    public void setTodayUsers(List<String> todayUsers) {
        this.todayUsers = todayUsers;
    }
}
