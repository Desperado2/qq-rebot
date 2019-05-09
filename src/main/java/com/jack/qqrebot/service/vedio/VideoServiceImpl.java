package com.jack.qqrebot.service.vedio;

import com.jack.qqrebot.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: mujj
 * @Date: 2019/5/9 23:40
 * @Description:
 * @Version: 1.0
 */
@Service("videoService")
public class VideoServiceImpl implements VideoService {
    @Override
    public String getVideoByKeyword(String keyword) {
        if(StringUtils.isEmpty(keyword)){
            return "命令错误，正确格式给 [影视 影视名称]";
        }
        StringBuffer stringBuffer = new StringBuffer();
        String url="http://ibahu.com/index.php?m=vod-search";
        AtomicInteger index = new AtomicInteger(1);
        String param =null;
        try {
             param ="wd="+URLEncoder.encode(keyword,"utf-8") +"&submit=";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = HttpUtils.sendPost(url, param);
        Document document = Jsoup.parse(result);
        Elements elements = document.select("ul[class=stui-vodlist__media col-pd clearfix]").select("li");
        elements.forEach(element -> {
            Element element1 = element.select("h3[class=title]").select("a").get(0);
            String title = element1.text();
            String href = "http://ibahu.com"+element1.attr("href");
            stringBuffer.append(index.get()).append(".").append(title).append("\n").append(href).append("\n");
            index.getAndIncrement();
        });
        return stringBuffer.toString();
    }
}
