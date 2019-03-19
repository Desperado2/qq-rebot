package com.jack.qqrebot.service.weibo;

import com.jack.qqrebot.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 17:10
 * @Description:
 * @Version: 1.0
 */
@Service("weiboService")
public class WeiboServiceImpl implements WeiboService{

    @Override
    public String getWeiboHot() throws UnsupportedEncodingException {
        StringBuilder message = new StringBuilder();
        message.append("微博实时热搜榜如下:\n\n");
        String url1 ="https://s.weibo.com/top/summary?Refer=top_hot&topnav=1&wvr=6";
        String get = HttpUtils.sendGet(url1, "");
        Document document = Jsoup.parse(get);
        Element element = document.select("div[id=pl_top_realtimehot]").get(0);
        Elements elements = element.select("table").get(0).select("tbody").select("tr");
        for (int i =0;i<Math.min(elements.size(),10);i++){
            Element element1 = elements.get(i);
            Element element2 = element1.select("td").get(1);
            message.append(i+1).append(".").append(element2.text()).append("\n\n");
        }
        return message.toString();
    }
}
