package com.jack.qqrebot.service.modian;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: mujj
 * @Date: 2019/6/28 21:59
 * @Description:
 * @Version: 1.0
 */
public interface ProjectDao extends JpaRepository<ProjectVo,Integer> {

        @Query("SELECT createDate,COUNT(DISTINCT userId) as count,COUNT(userId) as totle,sum(money)as money FROM ProjectVo GROUP BY DATE(createDate)")
        public List getCountGroupByDate();

        @Query("SELECT COUNT(DISTINCT userId) as count,COUNT(userId) as total,COALESCE(sum(money),0) as money FROM ProjectVo WHERE DATE(createDate)=CURDATE()")
        public List getTodayCount();

        @Query("SELECT COUNT(DISTINCT userId) as count,COUNT(userId) as total, COALESCE(sum(money),0) as money FROM ProjectVo")
        public List allCount();

        public ProjectVo findByPid(Integer pid);

}
