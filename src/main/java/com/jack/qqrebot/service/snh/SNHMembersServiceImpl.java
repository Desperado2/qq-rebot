package com.jack.qqrebot.service.snh;

import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Auther: mujj
 * @Date: 2019/5/3 14:06
 * @Description:
 * @Version: 1.0
 */
@Service("sNHMembersService")
public class SNHMembersServiceImpl implements SNHMembersService {
    @Override
    public String getRandomMember() {
        String url ="http://h5.snh48.com/resource/jsonp/members.php?gid=00&callback=get_members_success";
        String result = HttpUtils.sendGet(url, "");
        int startIndex = result.indexOf("(");
        int endIndex = result.lastIndexOf(")");
        String json = result.substring(startIndex+1, endIndex);
        JSONObject object = JSONObject.parseObject(json);

        Integer total = object.getInteger("total");
        Random random = new Random();
        int index = random.nextInt(total);
        JSONObject row = object.getJSONArray("rows").getJSONObject(index);
        Integer sid = row.getInteger("sid");
        String name = row.getString("sname");
        String gname = row.getString("gname");
        String pinyin = row.getString("pinyin");
        String tname = row.getString("tname");
        String pname = row.getString("pname");
        String nickName = row.getString("nickname");
        String height = row.getString("height");
        String blood_type = row.getString("blood_type");
        String birth_day = row.getString("birth_day");
        String star_sign_12 = row.getString("star_sign_12");
        String birth_place = row.getString("birth_place");
        String speciality = row.getString("speciality");
        String hobby = row.getString("hobby");
        String join_day = row.getString("join_day");
        Integer tid = row.getInteger("tid");
        String company = row.getString("company");
        String detail_url = "http://www.snh48.com/member_details.html?sid="+sid;
        String imgPath = getImageUrl(tid,sid);

        String plohe="";
        if(tid==150){
          plohe =" SNH48 TEAM 预备生";
        }else if(tid==151){
            plohe="海外练习生";
        }else if(sid==10218){
            plohe="GNZ48 G队 兼 SNH48 X队";
        }else{
           plohe=gname+" " + getTeamByTid(tid).toUpperCase() + "队（TEAM "+tname+")";
        }


        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("推荐成员：").append("\n\n");
        stringBuffer.append("[CQ:image,file=").append(imgPath).append("]").append("\n");
        stringBuffer.append(name).append(" ").append(pinyin).append(" -TEAM ").append(tname).append(" (").append(pname.substring(6)).append(")").append("\n");
        stringBuffer.append("昵称: ").append(nickName).append("\n");
        stringBuffer.append("生日: ").append(birth_day).append("\n");
        stringBuffer.append("星座: ").append(star_sign_12).append("\n");
        stringBuffer.append("出生地: ").append(birth_place).append("\n");
        stringBuffer.append("身高: ").append(height).append("cm").append("\n");
        stringBuffer.append("个人特长: ").append(speciality).append("\n");
        stringBuffer.append("兴趣爱好: ").append(hobby).append("\n");
        stringBuffer.append("加入所属: ").append(pname).append("\n");
        stringBuffer.append("最终归属: ").append(plohe).append("\n");
        stringBuffer.append("所属公司: ").append(company).append("\n\n");

        stringBuffer.append("详细信息: ").append(detail_url);

        return stringBuffer.toString();
    }

    private String getTeamByTid(Integer tid){
        Map<Integer,String> team = new HashMap<>();
        team.put(101,"s");
        team.put(102,"n");
        team.put(103,"h");
        team.put(104,"x");
        team.put(105,"xii");
        team.put(201,"b");
        team.put(202,"e");
        team.put(203,"j");
        team.put(301,"g");
        team.put(302,"Niii");
        team.put(303,"z");
        team.put(401,"SIII");
        team.put(402,"HIII");
        team.put(501,"C");
        team.put(502,"k");
        team.put(504,"预备生");
        team.put(701,"idft");

        return team.get(tid);
    }

    private String getImageUrl(Integer tid,Integer sid){
        String imgPath = "http://www.snh48.com/images/member/zp_"+sid+".jpg";
        if(tid > 700){
            imgPath = "http://idft.snh48.com/images/member/zp_"+sid+".jpg";
        }else if(tid > 500){
            imgPath = "http://www.ckg48.com/images/member/zp_"+sid+".jpg";
        }else if(tid > 400){
            imgPath = "http://www.shy48.com/images/member/zp_"+sid+".jpg";
        }else if(tid > 300){
            imgPath = "http://www.gnz48.com/images/member/zp_"+sid+".jpg";
        }else if(tid > 200){
            imgPath = "http://www.bej48.com/images/member/zp_"+sid+".jpg";
        }else if(tid > 100){
            imgPath = "http://www.snh48.com/images/member/zp_"+sid+".jpg";
        }
        return imgPath;
    }

    private String getDetailUrl(Integer tid,Integer sid){
        String detail_url = "http://www.snh48.com/member_details.html?sid="+sid;
        if(tid > 700){
            detail_url = "http://idft.snh48.com/member_details.html?sid="+sid;
        }else if(tid > 500){
            detail_url = "http://www.ckg48.com/member_details.html?sid="+sid;
        }else if(tid > 400){
            detail_url = "http://www.shy48.com/member_details.html?sid="+sid;
        }else if(tid > 300){
            detail_url = "http://www.gnz48.com/member_details.html?sid="+sid;
        }else if(tid > 200){
            detail_url = "http://www.bej48.com/member_details.html?sid="+sid;
        }else if(tid > 100){
            detail_url = "http://www.snh48.com/member_details.html?sid="+sid;
        }
        return detail_url;
    }
}
