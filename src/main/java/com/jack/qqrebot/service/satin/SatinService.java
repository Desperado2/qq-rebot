package com.jack.qqrebot.service.satin;

import java.io.UnsupportedEncodingException;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 16:15
 * @Description:
 * @Version: 1.0
 */
public interface SatinService {
    //随机获取段子
    String getSatinByRandom() throws UnsupportedEncodingException;
}
