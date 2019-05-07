package com.jack.qqrebot.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

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

    public static String getMemberName(String gruopId,String qq){
        String params ="group_id="+gruopId+"&user_id="+qq+"&no_cache=true";
        String groups = HttpUtils.sendPost("http://127.0.0.1:5300/get_group_member_info", params);
        JSONObject object = JSONObject.parseObject(groups);
        String nickname = object.getString("card");
        if(StringUtils.isEmpty(nickname)){
            nickname = object.getString("nickname");
        }
        return nickname;
    }
}
