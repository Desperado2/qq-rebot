package com.jack.qqrebot.service.gankService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import com.jack.qqrebot.utils.LongUrlToShortUrlUtils;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @Auther: mujj
 * @Date: 2019/4/30 13:24
 * @Description:
 * @Version: 1.0
 */
@Service("gankeService")
public class GankeServiceImpl implements GankeService {
    @Override
    public String getToadyData() {
        String url="http://gank.io/api/today";
        String result = HttpUtils.sendGet(url, "");
        JSONObject object = JSONObject.parseObject(result);
        if(object.getBoolean("error")){
            return "干货获取失败";
        }
        JSONObject results = object.getJSONObject("results");
        Set<String> keys = results.keySet();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("今日干货:").append("\n\n");
        keys.forEach( key -> {
            JSONArray array = results.getJSONArray(key);
            JSONObject arrayJSONObject = array.getJSONObject(0);
            String desc = arrayJSONObject.getString("desc");
            String urlText = arrayJSONObject.getString("url");
            urlText = LongUrlToShortUrlUtils.longToShort(urlText);
            stringBuffer.append(key).append("\n").append(desc).append("\n").append(urlText).append("\n");
        });
        stringBuffer.append("\n").append("查看更多干货，回复 \"干货+今日+类型\",如 干货今日App");
        return stringBuffer.toString();
    }

    @Override
    public String getTodayDataByCategory(String category) {
        String url="http://gank.io/api/today";
        String result = HttpUtils.sendGet(url, "");
        JSONObject object = JSONObject.parseObject(result);
        if(object.getBoolean("error")){
            return "干货获取失败";
        }
        JSONObject results = object.getJSONObject("results");
        JSONArray categorys = results.getJSONArray(category);
        StringBuffer stringBuffer = parseJsonArray(categorys,"desc","url","\n");
        stringBuffer.append("\n").append("查看更多干货，回复 \"干货+类型\",如 干货App");
        return stringBuffer.toString();
    }

    @Override
    public String report(String kw) {
        String url ="http://gank.io/api/xiandu/data/id/qdaily/count/15/page/1";
        if("qdaily".equalsIgnoreCase(kw)){
            url ="http://gank.io/api/xiandu/data/id/qdaily/count/15/page/1";
        }else if("zhihu".equalsIgnoreCase(kw)){
            url ="http://gank.io/api/xiandu/data/id/zhihu/count/15/page/1";
        }else if("vice".equalsIgnoreCase(kw)){
            url ="http://gank.io/api/xiandu/data/id/vice/count/15/page/1";
        }else if("ifanr".equalsIgnoreCase(kw)){
            url ="http://gank.io/api/xiandu/data/id/ifanr/count/15/page/1";
        }else if("engadget".equalsIgnoreCase(kw)){
            url ="http://gank.io/api/xiandu/data/id/engadget/count/15/page/1";
        }else if("ipc".equalsIgnoreCase(kw)){
            url ="http://gank.io/api/xiandu/data/id/ipc/count/15/page/1";
        }else if("techcrunch".equalsIgnoreCase(kw)){
            url ="http://gank.io/api/xiandu/data/id/techcrunch/count/15/page/1";
        }else if("wanqu".equalsIgnoreCase(kw)){
            url ="http://gank.io/api/xiandu/data/id/wanqu/count/15/page/1";
        }else if("solidot".equalsIgnoreCase(kw)){
            url ="http://gank.io/api/xiandu/data/id/solidot/count/15/page/1";
        }else if("williamlong".equalsIgnoreCase(kw)){
            url ="http://gank.io/api/xiandu/data/id/williamlong/count/15/page/1";
        }else if("toodaylab".equalsIgnoreCase(kw)){
            url ="http://gank.io/api/xiandu/data/id/toodaylab/count/15/page/1";
        }

        String result = HttpUtils.sendGet(url, "");
        JSONObject object = JSONObject.parseObject(result);
        if(object.getBoolean("error")){
            return "干货获取失败";
        }
        JSONArray results = object.getJSONArray("results");
        StringBuffer stringBuffer = parseJsonArray(results,"title","url","\n");
        return stringBuffer.toString();
    }

    @Override
    public String reportType() {
        String url ="http://gank.io/api/xiandu/category/wow";
        String result = HttpUtils.sendGet(url, "");
        JSONObject object = JSONObject.parseObject(result);
        if(object.getBoolean("error")){
            return "干货获取失败";
        }
        JSONArray results = object.getJSONArray("results");
        StringBuffer stringBuffer = parseJsonArray(results, "title", "id", ":");
        stringBuffer.append("\n回复 干货日报+日报代号 即可查看,如 干货日报zhihu");
        return stringBuffer.toString();
    }


    @Override
    public String getDataByCategory(String category) {
        String url ="http://gank.io/api/search/query/listview/category/"+category+"/count/15/page/1";
        String result = HttpUtils.sendGet(url, "");
        JSONObject object = JSONObject.parseObject(result);
        if(object.getBoolean("error")){
            return "干货获取失败";
        }
        JSONArray results = object.getJSONArray("results");
        StringBuffer stringBuffer = parseJsonArray(results,"desc","url","\n");
        return stringBuffer.toString();
    }

    private StringBuffer parseJsonArray(JSONArray results,String key1,String key2,String separator){

        StringBuffer stringBuffer = new StringBuffer();
        for (int i =0 ;i < results.size(); i++){
            JSONObject object1 = results.getJSONObject(i);
            String desc = object1.getString(key1);
            String urlText = object1.getString(key2);
            urlText = LongUrlToShortUrlUtils.longToShort(urlText);
            stringBuffer.append(i+1).append(".").append(desc).append(separator).append(urlText).append("\n");
        }
        return stringBuffer;
    }
}
