package com.jack.qqrebot.service.articles;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 22:54
 * @Description:
 * @Version: 1.0
 */
@Service("articlesService")
public class ArticlesServiceImpl implements ArticlesService{
    @Override
    public String getArticleByRandom() {
        String url1 ="https://api.bilibili.com/x/article/list/articles?id=51558&jsonp=jsonp";
        String url2 ="https://api.bilibili.com/x/article/list/articles?id=71434&jsonp=jsonp";

        String result = HttpUtils.sendGet(url1, "");
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray articles = jsonObject.getJSONObject("data").getJSONArray("articles");

        result = HttpUtils.sendGet(url2, "");
        jsonObject = JSONObject.parseObject(result);
        JSONArray articles1 = jsonObject.getJSONObject("data").getJSONArray("articles");

        articles1.stream().forEach(item -> articles.add(item));

        int random = new Random().nextInt(articles.size());
        JSONObject article = articles.getJSONObject(random);
        String url= "https://www.bilibili.com/read/mobile/"+article.getString("id");
        String title = article.getString("title");
        String content = article.getString("summary").substring(0,30);

        Object imageUrl = article.getJSONArray("image_urls").stream().findFirst().orElse(" ");

        String msg ="[CQ:share,url="+url+",title="+title+",content=" +content+ ",image="+imageUrl+"] ";
        return msg;
    }
}
