package com.jack.qqrebot.jst;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import com.jack.qqrebot.utils.SendMsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class GroupNoticeService {

    @Autowired
    private ModianProjectDao modianProjectDao;
    @Autowired
    private ModianProjectService modianProjectService;
    @Autowired
    private ProjectDao projectDao;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd");

    public String groupIncreaseNotice(String userId){
        return ("[CQ:at,qq=" + userId + "] 欢迎聚聚～这里是蒋舒婷安利站大礼包，带你一键了解优雅的17岁高中生小偶像\n" +
                "https://v.xiumi.us/board/v5/3ZjK5/155825273\n") + "\n" +
               "[CQ:image,file=https://t1.picb.cc/uploads/2019/08/13/g35YWD.md.jpg]";
    }


    public void updateData()  {
        System.out.println("---------开始更新------------");
        modianProjectService.updateProjectsData();
        List<ModianProject> modianProjects = modianProjectDao.findByStatusCode(0);
        li :for(ModianProject modianProject : modianProjects){
            long millis = System.currentTimeMillis()/1000;
            String url="http://mapi.modian.com/v45/product/comment_list?_t="+millis+4+"&client=2&json_type=1" +
                    "&mapi_query_time="+millis+"&moxi_post_id="+modianProject.getPid();
            double currMoney = modianProject.getBackerMoney();
            for (int i= 0; i< modianProject.getBackerCount(); i=i+10){
                url = url + "&page_index="+i+"&page_rows=10&pro_class=202&pro_id="+modianProject.getTid()+"&code=6fb4088b1b45a6b5";

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
                    if(isExists(pid,modianProject.getTid())){
                        continue li;
                    }
                    Integer userId = jsonObject.getInteger("user_id");
                    String userName = jsonObject.getJSONObject("user_info").getString("username");
                    double money = Double.valueOf(jsonObject.getString("content").replace("支持了","")
                            .replace("元","").trim());

                    Date date = null;
                    try {
                        date = sdf.parse(jsonObject.getString("ctime"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    ProjectVo projectVo = new ProjectVo();
                    projectVo.setPid(pid);
                    projectVo.setTid(modianProject.getTid());
                    projectVo.setUserId(userId);
                    projectVo.setUsername(userName);
                    projectVo.setMoney(money);
                    projectVo.setCreateDate(date);
                    projectDao.save(projectVo);
                    currMoney += money;
                    String result ="ID: "+userName+" 的聚聚刚刚在【"+modianProject.getName()+"】中支持了 ¥"+money+"元，" +
                            "感谢这位聚聚对蒋舒婷的支持\n\n"
                            +"【摩点】:" +modianProject.getName()+"\n"+
                            "目前集资进度：¥" +currMoney+"\n"+
                            "目标：¥" + modianProject.getGoal()+"\n\n"
                            +"https://zhongchou.modian.com/item/"+modianProject.getTid()+".html";

                    System.out.println(result);
                    SendMsgUtils.sendGroupMsg("261434765", result);
                }
            }
        }
    }

    private boolean isExists(Integer pid,Integer tid){
        ProjectVo projectVo = projectDao.findByPidAndTid(pid,tid);
        return projectVo != null;
    }


    public String getJz(){
        List<ModianProject> modianProjects = modianProjectDao.findByStatusCode(0);
        StringBuilder sb = new StringBuilder();
        sb.append("当前集资链接:\n");
        for (ModianProject modianProject : modianProjects){
            sb.append("[").append(modianProject.getName()).append("]\n");
            sb.append("当前进度:").append(modianProject.getBackerMoney()).append("/").append(modianProject.getGoal()).append("\n");
            sb.append("https://zhongchou.modian.com/item/").append(modianProject.getTid()).append(".html").append("\n\n");
        }
        sb.append("谢谢聚聚众筹支持小猪蹄!");
        return sb.toString();
    }

}
