package com.jack.qqrebot.service.visitcontoller;

import com.jack.qqrebot.enumm.ConnType;

/**
 * @Auther: mujj
 * @Date: 2019/5/10 23:39
 * @Description:
 * @Version: 1.0
 */
public interface VisitService {

    ConnType addVisitRecord(String userId, long timestamp );

    void check();

    void checkHMD();

}
