package com.jack.qqrebot.service.programer;

import javax.persistence.*;
import java.util.Date;

/**
 * @Auther: mujj
 * @Date: 2019/6/14 19:08
 * @Description:
 * @Version: 1.0
 */

@Table(name = "pro_res_table")
@Entity
public class ResourceVo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="tid")
    private Integer tid;

    @Column(name = "link")
    private String value;

    @Column(name = "user_qq")
    private String userqq;

    @Column(name = "group_id")
    private String groupId;

    @Column(name = "new_request")
    private Integer newRequest;

    @Column(name = "group_file_exist")
    private Integer groupFileExist=0;

    @Column(name="get_count")
    private Integer findCount=1;

    @Column(name = "create_time")
    private Date createDate = new Date();

    @Column(name = "update_time")
    private Date updateDate;


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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUserqq() {
        return userqq;
    }

    public void setUserqq(String userqq) {
        this.userqq = userqq;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Integer getNewRequest() {
        return newRequest;
    }

    public void setNewRequest(Integer newRequest) {
        this.newRequest = newRequest;
    }

    public Integer getGroupFileExist() {
        return groupFileExist;
    }

    public void setGroupFileExist(Integer groupFileExist) {
        this.groupFileExist = groupFileExist;
    }

    public Integer getFindCount() {
        return findCount;
    }

    public void setFindCount(Integer findCount) {
        this.findCount = findCount;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
