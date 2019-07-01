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

        @Query("SELECT createDate,COUNT(DISTINCT userId) as count,COUNT(userId) as totle,sum(money)as money FROM ProjectVo where tid= 69011 GROUP BY DATE(createDate)")
        public List getCountGroupByDate();

        @Query("SELECT COUNT(DISTINCT userId) as count,COUNT(userId) as total,COALESCE(sum(money),0) as money FROM ProjectVo where tid= 69011 and DATE(createDate)=CURDATE() ")
        public List getTodayCount();

        @Query("SELECT COUNT(DISTINCT userId) as count,COUNT(userId) as total, COALESCE(sum(money),0) as money FROM ProjectVo where tid= 69011")
        public List allCount();

        @Query("SELECT username,sum(money) FROM ProjectVo where tid= 69011 and DATE(createDate)=CURDATE() group by username order by createDate asc")
        public List getTodayUser();

        public ProjectVo findByPidAndTid(Integer pid,Integer tid);


        @Query("SELECT username,sum(money) FROM ProjectVo where tid <> 69011 group by username order by sum(money) desc  ")
        public List getUserRanking();

}
