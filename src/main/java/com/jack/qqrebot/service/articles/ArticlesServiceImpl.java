package com.jack.qqrebot.service.articles;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        for (int i= 0; i<articles1.size(); i++){
            articles.add(articles1.getJSONObject(i));
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dayseed = sdf.format(new Date());
        int dseed = Integer.parseInt(dayseed);
        int n = dseed % 11117;
        for (int i = 0; i < 100 ; i++) {
            n = n * n;
            n = n % 11117;   // 11117 是个质数
        }
        int random = n % articles.size();
        JSONObject article = articles.getJSONObject(random);
        String url= "https://www.bilibili.com/read/mobile/"+article.getString("id");
        String title = article.getString("title");
        String content = article.getString("summary").substring(0,30);
        String imgUrl ="";
        if (article.getJSONArray("image_urls").size() > 0){
            imgUrl = article.getJSONArray("image_urls").getString(0);
        }else{
            imgUrl="";
        }
        String msg ="[CQ:share,url="+url+",title="+title+",content=" +content+ ",image="+imgUrl+"] ";

        return msg;
    }
}
