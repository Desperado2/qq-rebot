package com.jack.qqrebot.service.erciyuna;

import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;

/**
 * @Auther: mujj
 * @Date: 2019/5/3 13:05
 * @Description:
 * @Version: 1.0
 */
@Service("picService")
public class PicServiceImpl implements PicService {
    @Override
    public String getRandomPic() {
        String url="https://api.ixiaowai.cn/api/api.php";
        String result = HttpUtils.getPic(url, "");
        return "[CQ:image,file="+result+"]";
    }

    @Override
    public String getRandomMenhera() {
        String url="https://api.ixiaowai.cn/mcapi/mcapi.php";
        String result = HttpUtils.getPic(url, "");
        return "[CQ:image,file="+result+"]";
    }
}
