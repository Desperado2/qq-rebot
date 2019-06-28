package com.jack.qqrebot.service.notice;

import com.jack.qqrebot.enumm.CqApi;
import com.jack.qqrebot.utils.CQUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Auther: mujj
 * @Date: 2019/5/7 14:10
 * @Description:
 * @Version: 1.0
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

    @Override
    public String groupIncrease(String userId) {
        return ("[CQ:at,qq=" + userId + "] 欢迎加入本群！") + "\n" +
                "请注意以下事项哦，只有这样才可以玩的愉快哦" + "\n\n" +
                "0.本群最厉害的是管理员，请不要轻易招惹" + "\n" +
                "1.禁止黄赌毒，不要讨论这些，不然无条件飞机" + "\n" +
                "2.不要过分讨论设计政治的话题" + "\n" +
                "3.无事不要过分调戏机器人，毕竟服务器、资源都是收费的" + "\n" +
                "4.有什么问题询问请详细描述，能截图尽量截图" + "\n" +
                "5.询问资料的，请注明 机构名称+资源精确全名，能截图的尽量截图" + "\n" +
                "6.群里机器人是 【机器人小布】,可@它发生 菜单 查看功能" + "\n" +
                "7.资料请看置顶群公告，加入百度云群，资料都在里面哦（如果失效可联系群主）" + "\n" +
                "9.最后祝身体健康，工作愉快";
    }

    @Override
    public String groupDecrease(String userId) {
        String strangerInfo = CQUtils.getStrangerInfo(userId);
        if(!StringUtils.isEmpty(strangerInfo)){
            return "用户 ["+strangerInfo+"]("+userId+") 离开了本群";
        }
        return "用户 ["+userId+"] 离开了本群";
    }
}
