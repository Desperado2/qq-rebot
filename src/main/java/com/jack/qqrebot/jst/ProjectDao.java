package com.jack.qqrebot.jst;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Auther: mujj
 * @Date: 2019/6/28 21:59
 * @Description:
 * @Version: 1.0
 */
public interface ProjectDao extends JpaRepository<ProjectVo,Integer> {

        @Query("SELECT createDate,COUNT(DISTINCT userId) as count,COUNT(userId) as totle,sum(money)as money FROM ProjectVo where tid= :tid GROUP BY DATE(createDate)")
        public List getCountGroupByDate(@Param("tid") Integer tid);

        @Query("SELECT COUNT(DISTINCT userId) as count,COUNT(userId) as total,COALESCE(sum(money),0) as money FROM ProjectVo where tid= :tid and DATE(createDate)=CURDATE() ")
        public List getTodayCount(@Param("tid") Integer tid);

        @Query("SELECT COUNT(DISTINCT userId) as count,COUNT(userId) as total, COALESCE(sum(money),0) as money FROM ProjectVo where tid= :tid")
        public List allCount(@Param("tid") Integer tid);

        @Query(value = "SELECT substring_index(group_concat(username order by create_date desc),',',1),sum(money) FROM project where tid= :tid and DATE(create_date)=CURDATE() group by user_id order by create_date asc",nativeQuery = true)
        public List getTodayUser(@Param("tid") Integer tid);

        public ProjectVo findByPidAndTid(Integer pid, Integer tid);

        @Query(value = "SELECT substring_index(group_concat(username order by create_date desc),',',1),sum(money) FROM project where tid in(55259,56636,57733,58723,61098,61747,62689,63446,64301,65522,66263,66752,67290) group by user_id order by sum(money) desc",nativeQuery = true)
        public List getUserRanking();

}
