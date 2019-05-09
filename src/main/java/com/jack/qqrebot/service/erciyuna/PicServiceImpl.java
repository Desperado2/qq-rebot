package com.jack.qqrebot.service.erciyuna;

import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        String url="https://www.loli.ci/api.php";
        String result = HttpUtils.sendGet(url, "return=json&size=large");
        if(!StringUtils.isEmpty(result)){
            JSONObject object = JSONObject.parseObject(result);
            if(object.getInteger("code") == 200){
                result = object.getString("imgurl");
            }
        }
        return "[CQ:image,file="+result+"]";
    }

    @Override
    public String getRandomMenhera() {
        String url="https://api.ixiaowai.cn/mcapi/mcapi.php";
        String result = HttpUtils.getPic(url, "");
        return "[CQ:image,file="+result+"]";
    }
}
