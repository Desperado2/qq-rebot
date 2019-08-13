package com.jack.qqrebot.jst;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * @Auther: mujj
 * @Date: 2019/7/3 10:32
 * @Description:
 * @Version: 1.0
 */

@Service("modianProjectService")
public class ModianProjectServiceImpl implements ModianProjectService {

    @Autowired
    private ModianProjectDao modianProjectDao;

    @Override
    public void updateProjectsData() {
        HashSet<Integer> tidSet = new HashSet<>();
        Long timeStamp = System.currentTimeMillis() /1000 ;

        String url = "http://mapi.modian.com//v45/user/build_product_list";
        String param = "code=148ed474a1028443&user_id=1431046&_t="+timeStamp+"&to_user_id=5299088&json_type=1&page_index=0&page_rows=100&client=2&";

        String result = HttpUtils.sendPost(url, param);
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray datas = jsonObject.getJSONArray("data");
        for (int j=0; j<datas.size(); j++){
            JSONObject data = datas.getJSONObject(j);
            Integer statusCode = data.getInteger("status_code");
            if(statusCode != 0){
                continue;
            }
            Integer tid = data.getInteger("id");
            if(tidSet.contains(tid)){
                return;
            }
            System.out.println("----------更新摩点项目----------");
            Integer userId = data.getInteger("user_id");
            String username = data.getString("username");
            String name = data.getString("name");
            Date startTime = data.getDate("start_time");
            Date endTime = data.getDate("end_time");
            Double goal = data.getDouble("goal");
            Double backerMoney = data.getDouble("backer_money");
            Double progress = data.getDouble("progress");
            Integer backerCount = data.getInteger("backer_count");

            String logo = data.getString("logo");
            Integer subscribeCount = data.getInteger("subscribe_count");

            Integer postId = getPostIdByTid(tid);
            ModianProject modianProject = modianProjectDao.findByTid(tid);
            if(modianProject == null){
                modianProject = new ModianProject();
            }
            modianProject.setTid(tid);
            modianProject.setUserId(userId);
            modianProject.setUsername(username);
            modianProject.setName(name);
            modianProject.setStartTime(startTime);
            modianProject.setEndTime(endTime);
            modianProject.setGoal(goal);
            modianProject.setBackerMoney(backerMoney);
            modianProject.setBackerCount(backerCount);
            modianProject.setStatusCode(statusCode);
            modianProject.setLogo(logo);
            modianProject.setProgress(progress);
            modianProject.setSubscribeCount(subscribeCount);
            modianProject.setPid(postId);
            modianProjectDao.save(modianProject);

            tidSet.add(tid);
        }

    }

    private Integer getPostIdByTid(Integer tid){
        String url =" http://sapi.modian.com/v45/main/productInfo";
        Long time = System.currentTimeMillis() / 1000;
        String param = "code=6fb4088b1b45a6b5&pro_id="+tid+"&user_id=1431046&_t="+time+"&json_type=1&client=2&";
        String s = HttpUtils.sendPost(url, param);
        JSONObject object = JSONObject.parseObject(s).getJSONObject("data").getJSONObject("product_info");
        return  object.getInteger("moxi_post_id");
    }
}
