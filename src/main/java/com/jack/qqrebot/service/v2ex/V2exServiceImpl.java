package com.jack.qqrebot.service.v2ex;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;

/**
 * @Auther: mujj
 * @Date: 2019/4/30 14:44
 * @Description:
 * @Version: 1.0
 */
@Service("v2exService")
public class V2exServiceImpl implements V2exService {
    @Override
    public String hotTopics() {
        String url ="https://www.v2ex.com/api/topics/hot.json";
        String result = HttpUtils.sendGet(url, "");
        JSONArray array = JSONArray.parseArray(result);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0 ;i < array.size(); i++){
            JSONObject object = array.getJSONObject(i);
            String nodeName = object.getJSONObject("node").getString("title");
            String title = object.getString("title");
            String urlText = object.getString("url");
            stringBuffer.append(i+1).append("[").append(nodeName).append("] ").append(title).append("\n").append(urlText).append("\n");
        }
        return stringBuffer.toString();
    }
}
