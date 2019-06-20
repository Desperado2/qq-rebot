package com.jack.qqrebot.service.programer;

import javax.persistence.criteria.CriteriaBuilder;

/**
 * @Auther: mujj
 * @Date: 2019/6/14 18:10
 * @Description:
 * @Version: 1.0
 */
public interface ProgramerService  {

    String dealRequest(String groupId,String qq,String msg);

    void updateStatus(Integer tid);
}
