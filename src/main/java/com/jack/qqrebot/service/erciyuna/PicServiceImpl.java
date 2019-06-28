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
        String result = getImageUrl();
        return "[CQ:image,file="+result+"]";
    }

    private String getImageUrl(){

        String url= "https://acg.toubiec.cn/random";
        String result = HttpUtils.sendGet(url, "return=json");
        if(!StringUtils.isEmpty(result)){
            JSONObject object = JSONObject.parseObject(result);
            result = object.getString("acgurl");
            return result;
        }

        url= "http://www.dmoe.cc/random.php";
        result = HttpUtils.sendGet(url, "return=json");
        if(!StringUtils.isEmpty(result)){
            JSONObject object = JSONObject.parseObject(result);
            result = object.getString("imgurl");
            return result;
        }

        url= "https://api.apizz.cn/ecyt/api.php";
        result = HttpUtils.sendGet(url, "return=json");
        if(!StringUtils.isEmpty(result)){
            JSONObject object = JSONObject.parseObject(result);
            result = object.getString("imgurl");
            return result;
        }

        url= "https://yesos.cn/api/acgurl.php";
        result = HttpUtils.sendGet(url, "return=json");
        if(!StringUtils.isEmpty(result)){
            JSONObject object = JSONObject.parseObject(result);
            result = object.getString("imgurl");
            return result;
        }

        url="http://acg.bakayun.cn/randbg.php";
        result = HttpUtils.sendGet(url, "Type=json");
        if(!StringUtils.isEmpty(result)){
            JSONObject object = JSONObject.parseObject(result);
            result = object.getString("ImgUrl");
            return result;
        }

        return "60-1Z1241G152212.jpg";
    }

    @Override
    public String getRandomMenhera() {
        String url="https://api.ixiaowai.cn/mcapi/mcapi.php";
        String result = HttpUtils.getPic(url, "");
        return "[CQ:image,file="+result+"]";
    }
}
