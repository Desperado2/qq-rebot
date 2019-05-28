package com.jack.qqrebot.service.blacklist;

/**
 * @Auther: mujj
 * @Date: 2019/5/28 16:38
 * @Description:
 * @Version: 1.0
 */
public interface BlackListService {

    String addBackList(String userId);
    String delBackList(String userId);

}
