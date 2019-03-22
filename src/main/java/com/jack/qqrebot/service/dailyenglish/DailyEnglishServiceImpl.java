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
        Document document = Jsoup.parse(s);
        Element element = document.getElementById("content");
        Element element1 = element.getElementsByClass("post").get(0);
        String msg = element1.getElementsByTag("strong").get(0).text()+"\n\n";
        msg += element1.getElementsByTag("p").get(1).text()+"\n";

        Elements lis = element1.getElementsByTag("li");
        for (Element li : lis){
            msg += li.text()+"\n";
        }
        try {
            msg = ZHConverter.transformation(msg,ZHConverter.target.TCcharacter);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return msg;
    }
}
