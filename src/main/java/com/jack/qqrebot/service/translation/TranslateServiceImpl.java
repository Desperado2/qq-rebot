package com.jack.qqrebot.service.translation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;

/**
 * @Auther: mujj
 * @Date: 2019/3/22 13:50
 * @Description:
 * @Version: 1.0
 */
@Service("translateService")
public class TranslateServiceImpl implements TranslateService{
    @Override
    public String translate(String word) {
        StringBuilder stringBuilder = new StringBuilder();
        String langUrl="https://fanyi.baidu.com/langdetect";
        String transUrl="https://fanyi.baidu.com/v2transapi";

        String langs = HttpUtils.sendPost(langUrl, "query=" + word);
        String lan = JSONObject.parseObject(langs).getString("lan");

        String query = "from="+lan+"&to=en&query="+word+"&transtype=translang&simple_means_flag=3&sign=635104.873425";
        String result = HttpUtils.sendPost(transUrl, query);
        JSONArray array = JSONObject.parseObject(result).getJSONObject("trans_result").getJSONArray("data");

        for (int i = 0; i< array.size(); i++) {
            JSONObject object = array.getJSONObject(i);
            stringBuilder.append(object.getString("dst"));
        }

        return stringBuilder.toString();
    }
}
