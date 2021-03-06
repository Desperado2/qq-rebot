package com.jack.qqrebot.service.programer;

import com.jack.qqrebot.utils.HttpUtils;
import com.jack.qqrebot.utils.SendMsgUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: mujj
 * @Date: 2019/6/14 18:12
 * @Description:
 * @Version: 1.0
 */

@Service("programerService")
public class ProgramerServiceImpl implements ProgramerService {

    @Autowired
    private ProgrammerDao programmerDao;

    @Override
    public String dealRequest(String groupId, String qq, String msg) {
        String[] split = msg.split("\\*\\*");
        List<String> list = Arrays.stream(split).filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.toList());
        if(list.size() == 1){
            return getNewRequest();
        } else if (list.size() == 2) {
            String s = list.get(1);
            return getRequest(groupId, qq, Integer.parseInt(s));
        } else if (list.size() == 3) {
            String s = list.get(1);
            return addRequest(groupId, Integer.parseInt(s), list.get(2));
        }
        return "[CQ:at,qq=" + qq + "] 命令格式错误\r\n格式为 吾爱**tid";
    }

    @Override
    public void updateStatus(Integer tid) {
        synchronized (this){
            ResourceVo  resourceVo = programmerDao.findByTid(tid);
            if (resourceVo != null) {
                resourceVo.setGroupFileExist(1);
                programmerDao.save(resourceVo);
            }
        }
    }

    private String getNewRequest(){
        List<ResourceVo> newRequest = programmerDao.findByNewRequest(0);
        StringBuilder sb = new StringBuilder();
        sb.append("需要获取的id列表").append("\n").append("\n");
        newRequest.forEach(s->sb.append(s.getTid()).append("\n"));
        return sb.toString();
    }
    private String getRequest(String groupId, String qq, Integer tid) {
        ResourceVo resourceVo = null;
        String title = null;
        synchronized (this){
            resourceVo = programmerDao.findByTid(tid);
            if (resourceVo == null) {
                ResourceVo resourceVo1 = new ResourceVo();
                resourceVo1.setGroupId(groupId);
                resourceVo1.setTid(tid);
                resourceVo1.setUserqq(qq);
                resourceVo1.setNewRequest(0);
                resourceVo1.setFindCount(1);
                title = getTitleByCode(tid);
                resourceVo1.setTitle(title);
                programmerDao.save(resourceVo1);
                return "[CQ:at,qq=" + qq + "] 你的需求" + tid + "已记录";
            }
        }
        String value = resourceVo.getValue();
        String userqq = resourceVo.getUserqq();
        Integer findCount = resourceVo.getFindCount();
        title = resourceVo.getTitle();
        if (StringUtils.isEmpty(value)) {
            resourceVo.setFindCount(findCount+1);
            if(StringUtils.isEmpty(userqq)){
                resourceVo.setUserqq(qq);
                programmerDao.save(resourceVo);
            }else if(!userqq.contains(qq)){
                resourceVo.setUserqq(userqq + "," + qq);
                programmerDao.save(resourceVo);
            }
            return "[CQ:at,qq=" + qq + "] 你的需求" + tid + "已记录";
        }
        resourceVo.setFindCount(findCount+1);
        programmerDao.save(resourceVo);
        return "[CQ:at,qq=" + qq + "] 你的需求" + tid + " ["+title+"] 已找到\n" + value;
    }

    private String addRequest(String groupId, Integer tid, String value) {

        ResourceVo resourceVo = programmerDao.findByTid(tid);
        String title = null;
        if (resourceVo == null) {
            title = getTitleByCode(tid);
            ResourceVo resourceVo1 = new ResourceVo();
            resourceVo1.setTid(tid);
            resourceVo1.setValue(value);
            resourceVo1.setGroupId(groupId);
            resourceVo1.setNewRequest(1);
            resourceVo1.setUpdateDate(new Date());
            resourceVo1.setTitle(title);
            programmerDao.save(resourceVo1);
            return "添加成功";
        }
        resourceVo.setValue(value);
        resourceVo.setNewRequest(1);
        resourceVo.setUpdateDate(new Date());
        title = resourceVo.getTitle();
        programmerDao.save(resourceVo);
        String userqq = resourceVo.getUserqq();
        if (!StringUtils.isEmpty(userqq)) {
            String[] split = userqq.split(",");
            String finalTitle = title;
            Arrays.stream(split).filter(s -> !StringUtils.isEmpty(s)).collect(Collectors.toList()).forEach(s -> {
                SendMsgUtils.sendGroupMsg(groupId, "[CQ:at,qq=" + resourceVo.getUserqq() + "] 你的需求" + tid + " ["+ finalTitle +"] 已找到\n" + resourceVo.getValue()+"\n\n[麻烦保存到群文件,谢谢]");
            });
        }
        return "更新成功";
    }


    private String getTitleByCode(Integer code){
        String url = "https://www.52programer.com/thread-"+code+"-1-1.html";
        String result = HttpUtils.sendGetUseGBK(url,null);
        Document document = Jsoup.parse(result);
        Element body = document.body();
        Element elements = body.getElementById("thread_subject");
        return elements.text();
    }
}
