package com.jack.qqrebot.service.gankService;

/**
 * @Auther: mujj
 * @Date: 2019/4/30 13:20
 * @Description:
 * @Version: 1.0
 */
public interface GankeService {
    String getToadyData();

    String getTodayDataByCategory(String category);

    String report(String kw);

    String reportType();

    String getDataByCategory(String category);


}
