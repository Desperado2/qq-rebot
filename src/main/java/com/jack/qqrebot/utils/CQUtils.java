package com.jack.qqrebot.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.enumm.CqApi;
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
        String groups = HttpUtils.sendPost("http://127.0.0.1:5300/"+CqApi.GET_GROUP_LIST.getName(), "");
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
        String groups = HttpUtils.sendPost("http://127.0.0.1:5300/"+CqApi.GET_GROUP_MEMBER_INFO.getName(), params);
        JSONObject object = JSONObject.parseObject(groups);
        object = object.getJSONObject("data");
        String nickname = object.getString("card");
        if(StringUtils.isEmpty(nickname)){
            nickname = object.getString("nickname");
        }
        //String groupMsg = getGroupMsg(gruopId);
        return nickname;
    }

    public static void ban(Integer gruopId,Integer qq,int second){
        String params ="group_id="+gruopId+"&user_id="+qq+"&duration="+second;
        HttpUtils.sendPost("http://127.0.0.1:5300/"+CqApi.SET_GROUP_BAN.getName(), params);
    }

    public static String getGroupMsg(String gruopId){
        String params ="group_id="+gruopId;
        String result = HttpUtils.sendPost("http://127.0.0.1:5300/" + CqApi._GET_GROUP_INFO.getName(), params);
        JSONObject object = JSONObject.parseObject(result);
        object = object.getJSONObject("data");
        return object.getString("group_name");
    }

    public static void sendMsg(Integer userId,String msg){
        String params ="message_type=private"+"&user_id="+userId+"&message="+msg;
        HttpUtils.sendPost("http://127.0.0.1:5300/"+CqApi.SEND_MSG.getName(), params);
    }


    public static String getStrangerInfo(String userId){
        String params ="user_id="+userId+"&no_cache=false";
        String result = HttpUtils.sendPost("http://127.0.0.1:5300/" + CqApi.GET_STRANGER_INFO.getName(), params);
        JSONObject object = JSONObject.parseObject(result);
        object = object.getJSONObject("data");
        return object.getString("nickname");
    }
}
