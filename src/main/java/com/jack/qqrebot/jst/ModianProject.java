package com.jack.qqrebot.jst;

import javax.persistence.*;
import java.util.Date;

/**
 * @Auther: mujj
 * @Date: 2019/7/3 10:16
 * @Description:
 * @Version: 1.0
 */

@Entity
@Table(name = "modian_project")
public class ModianProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "tid")
    private Integer tid;

    @Column(name = "pid")
    private Integer pid;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "goal")
    private Double goal;

    @Column(name = "backer_money")
    private Double backerMoney;

    @Column(name = "progress")
    private Double progress;

    @Column(name = "backer_count")
    private Integer backerCount;

    @Column(name = "status_code")
    private Integer statusCode;

    @Column(name = "subscribe_count")
    private Integer subscribeCount;

    @Column(name = "logo")
    private String logo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Double getGoal() {
        return goal;
    }

    public void setGoal(Double goal) {
        this.goal = goal;
    }

    public Double getBackerMoney() {
        return backerMoney;
    }

    public void setBackerMoney(Double backerMoney) {
        this.backerMoney = backerMoney;
    }

    public Double getProgress() {
        return progress;
    }

    public void setProgress(Double progress) {
        this.progress = progress;
    }

    public Integer getBackerCount() {
        return backerCount;
    }

    public void setBackerCount(Integer backerCount) {
        this.backerCount = backerCount;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getSubscribeCount() {
        return subscribeCount;
    }

    public void setSubscribeCount(Integer subscribeCount) {
        this.subscribeCount = subscribeCount;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
