package com.jack.qqrebot.jst;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: mujj
 * @Date: 2019/7/3 10:30
 * @Description:
 * @Version: 1.0
 */
public interface ModianProjectDao extends JpaRepository<ModianProject,Integer> {

    ModianProject findByTid(Integer tid);

    List<ModianProject> findByStatusCode(Integer statusCode);

    @Query("select tid ,name from ModianProject where DATE(endTime) > CURDATE() -2 order by endTime desc")
    List getYestAllProject();

}
