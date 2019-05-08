package com.jack.qqrebot.service.dailyenglish;

import com.jack.qqrebot.utils.HttpUtils;
import com.jack.qqrebot.utils.ZHConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 17:18
 * @Description:
 * @Version: 1.0
 */
@Service("dailyEnglishService")
public class DailyEnglishServiceImpl implements DailyEnglishService {

    @Override
    public String getDailyEnglish()  {
        String s = HttpUtils.sendGet("https://www.dailyenglishquote.com/", "");
        StringBuilder sb = new StringBuilder();
        Document document = Jsoup.parse(s);
        Element element = document.getElementById("content");
        element = element.getElementsByClass("post").get(0);
        sb.append(element.getElementsByTag("strong").get(0).text()).append("\n");
        sb.append( element.getElementsByTag("p").get(1).text()).append("\n\n");
        Elements lis = element.getElementsByTag("li");

        lis.forEach(li->sb.append(li.text()).append("\n"));

        try {
            return ZHConverter.transformation(sb.toString(),ZHConverter.target.TCcharacter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return "";
    }
}
