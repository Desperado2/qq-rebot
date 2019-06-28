package com.jack.qqrebot.service.weibo;

import com.jack.qqrebot.utils.HttpUtils;
import com.jack.qqrebot.utils.LongUrlToShortUrlUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 17:10
 * @Description:
 * @Version: 1.0
 */
@Service("weiboService")
public class WeiboServiceImpl implements WeiboService{

    @Override
    public String getWeiboHot() {
        StringBuilder message = new StringBuilder();
        message.append("微博实时热搜榜如下:\n\n");
        String url1 ="https://s.weibo.com/top/summary?Refer=top_hot&topnav=1&wvr=6";
        String get = HttpUtils.sendGet(url1, "");
        Document document = Jsoup.parse(get);
        Element element = document.select("div[id=pl_top_realtimehot]").get(0);
        Elements elements = element.select("table").get(0).select("tbody").select("tr");
        AtomicInteger index = new AtomicInteger(1);
        elements.forEach(element1 -> {
            if(index.get() <= 10){
                Element element2 = element1.select("td").get(1);
                String linkUrl =element2.select("a").attr("href");
                String title = element2.select("a").text();
                String hot = element2.select("span").text();
                if(hot.length() < 1){
                    title = title+"[置顶热搜↑↑↑]";
                }else{
                    title = title+"[实时热度:"+hot+"]";
                }
                String url = "https://s.weibo.com"+linkUrl;
                url = LongUrlToShortUrlUtils.longToShort(url);

                message.append(index.getAndIncrement()).append(".").append(title);
                if(!StringUtils.isEmpty(url)){
                    message.append("\n").append(url);
                }
                message.append("\n\n");
            }
        });

        return message.toString();
    }
}
