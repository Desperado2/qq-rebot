package com.jack.qqrebot.service.modian;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: mujj
 * @Date: 2019/6/28 22:06
 * @Description:
 * @Version: 1.0
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    Logger logger = LoggerFactory.getLogger(ProjectServiceImpl.class);
    @Autowired
    private ProjectDao projectDao;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd");
    @Override
    public void updateData() throws ParseException {
        logger.info(sdf.format(new Date())+"  摩点数据更新开始");
        long millis = System.currentTimeMillis()/1000;
        String url="http://mapi.modian.com/v45/product/comment_list?_t=1561727048&client=2&json_type=1" +
                "&mapi_query_time="+millis+"&moxi_post_id=92546";
        for (int i= 0; i< 200; i=i+10){
            url = url + "&page_index="+i+"&page_rows=10&pro_class=202&pro_id=69011&code=6496ffb6d99007b4";

            String get = HttpUtils.sendGet(url, null);
            JSONObject object = JSONObject.parseObject(get);
            JSONArray data = object.getJSONArray("data");

            for (int j=0; j<data.size(); j++){
                JSONObject jsonObject = data.getJSONObject(j);
                Integer pay_amount = jsonObject.getInteger("pay_amount");
                if(pay_amount == 0){
                    continue;
                }
                Integer pid = jsonObject.getInteger("id");
                if(isExists(pid)){
                    return;
                }
                Integer userId = jsonObject.getInteger("user_id");
                String userName = jsonObject.getJSONObject("user_info").getString("username");
                double money = Double.valueOf(jsonObject.getString("content").replace("支持了","")
                        .replace("元","").trim());
                Date date = sdf.parse(jsonObject.getString("ctime"));

                ProjectVo projectVo = new ProjectVo();
                projectVo.setPid(pid);
                projectVo.setUserId(userId);
                projectVo.setUsername(userName);
                projectVo.setMoney(money);
                projectVo.setCreateDate(date);
                projectDao.save(projectVo);
            }
        }

    }

    private boolean isExists(Integer pid){
        ProjectVo projectVo = projectDao.findByPid(pid);
        return projectVo != null;
    }

    @Override
    public ProjectDateVo getCountGroupByDate() {
        ProjectDateVo projectDateVo = new ProjectDateVo();
        getTodayData(projectDateVo);
        getAllData(projectDateVo);
        getChartData(projectDateVo);
        getTodayUsers(projectDateVo);
        return projectDateVo;
    }

    private void getTodayData(ProjectDateVo projectDateVo){
        List todayCount = projectDao.getTodayCount();
        for (Object object : todayCount){
            Object[] cells = (Object[]) object;
            projectDateVo.setTodayUserCount((Long) cells[0]);
            projectDateVo.setTodayUserTotal((Long) cells[1]);
            projectDateVo.setTodayMoney((Double) cells[2]);
        }
    }

    private void getAllData(ProjectDateVo projectDateVo){
        List allCount = projectDao.allCount();
        for (Object object : allCount){
            Object[] cells = (Object[]) object;
            projectDateVo.setAllUserCount((Long) cells[0]);
            projectDateVo.setAllUserTotal((Long) cells[1]);
            projectDateVo.setAllMoney((Double) cells[2]);
        }
    }

    private void getChartData(ProjectDateVo projectDateVo){
        List data = projectDao.getCountGroupByDate();

        List<String> dates = new ArrayList<>();
        List<Long> count = new ArrayList<>();
        List<Long> total= new ArrayList<>();
        List<Double> money= new ArrayList<>();

        for (Object object : data){
            Object[] cells = (Object[]) object;
            dates.add(sdf1.format((Date) cells[0]));
            count.add((Long) cells[1]);
            total.add((Long) cells[2]);
            money.add((Double) cells[3]);
        }
        projectDateVo.setDates(dates);
        projectDateVo.setUsersCount(count);
        projectDateVo.setUsersTotal(total);
        projectDateVo.setMoneys(money);
    }

    private void getTodayUsers(ProjectDateVo projectDateVo){
        List todayUser = projectDao.getTodayUser();
        if(todayUser.size() > 24){
            List<String> todays = new ArrayList<>();
            for (Object object : todayUser){
               todays.add(object.toString());
            }
            projectDateVo.setTodayUsers(todays);
        }
    }
}
