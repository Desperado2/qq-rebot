package com.jack.qqrebot.service.modian;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: mujj
 * @Date: 2019/6/28 22:02
 * @Description:
 * @Version: 1.0
 */
public interface ProjectService {

    void updateData() throws ParseException;

    ProjectDateVo getCountGroupByDate();

}