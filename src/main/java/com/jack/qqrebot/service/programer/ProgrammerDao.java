package com.jack.qqrebot.service.programer;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * @Auther: mujj
 * @Date: 2019/6/14 19:16
 * @Description:
 * @Version: 1.0
 */
public interface ProgrammerDao extends JpaRepository<ResourceVo,Integer> {

    ResourceVo findByTid(Integer tid);

    List<ResourceVo> findByNewRequest(Integer newRequest);
}
