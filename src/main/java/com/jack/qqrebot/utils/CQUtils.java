package com.jack.qqrebot.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 17:28
 * @Description:
 * @Version: 1.0
 */
public class CQUtils {

    public static List<Integer> getGroupList(){
        List<Integer> groupList = new ArrayList<>();
        String groups = HttpUtils.sendPost("http://127.0.0.1:5300/get_group_list", "");
        JSONArray array = JSONObject.parseObject(groups).getJSONArray("data");
        for (int i=0;i<array.size();i++){
            JSONObject object3 = array.getJSONObject(i);
            Integer group_id = object3.getInteger("group_id");
            groupList.add(group_id);
        }
        return groupList;
    }
}
