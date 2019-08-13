package com.jack.qqrebot.jst;

import org.springframework.stereotype.Service;

@Service
public class GroupNoticeService {

    public String groupIncreaseNotice(String userId){
        return ("[CQ:at,qq=" + userId + "] 欢迎聚聚～这里是蒋舒婷安利站大礼包，带你一键了解优雅的17岁高中生小偶像\n" +
                "https://v.xiumi.us/board/v5/3ZjK5/155825273\n") + "\n" +
               "[CQ:image,file=https://t1.picb.cc/uploads/2019/08/13/g35YWD.md.jpg]";
    }
}
