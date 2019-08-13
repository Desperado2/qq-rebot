package com.jack.qqrebot.jst.wanan;

import com.jack.qqrebot.service.duyan.DuyanService;
import com.jack.qqrebot.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class WananService {

    @Autowired
    private DuyanService duyanService;

    public String getResult() {
        String url="https://nongli.911cha.com/";
        String result = HttpUtils.sendGet(url, null);
        System.out.println(result);
        Document document = Jsoup.parse(result);
        Element elementsByClass = document.getElementsByClass("mt f14").get(0);
        Elements elements = elementsByClass.getElementsByTag("tr");
        StringBuilder sb = new StringBuilder();
        sb.append("锵锵锵,午时已到我来报时\n\n");
        elements.forEach(element -> {
            element.getElementsByTag("th").forEach(element1 -> {
                if(element1.text().equals("公历")){
                    Element td = element.getElementsByTag("td").get(0);
                    sb.append(td.text()).append("\n");
                }
                if(element1.text().equals("农历")){
                    Element td = element.getElementsByTag("td").get(0);
                    sb.append("农历:").append(td.text()).append("\n\n");
                }
            });
        });
        String duyanRandom = duyanService.getDuyanRandom();
        sb.append(duyanRandom).append("\n\n");


        List<String> list = Arrays.asList("https://t1.picb.cc/uploads/2019/08/13/g3Q4xe.png",
                "https://t1.picb.cc/uploads/2019/08/13/g3QHks.png",
                "https://t1.picb.cc/uploads/2019/08/13/g3Q5S6.png",
                "https://t1.picb.cc/uploads/2019/08/13/g3Q8oT.png",
                "https://t1.picb.cc/uploads/2019/08/13/g3Q1WN.png",
                "https://t1.picb.cc/uploads/2019/08/13/g3Qrr7.png");
        String imgurl = list.get(Math.abs(new Random().nextInt() % list.size()));
        sb.append("[CQ:image,file="+imgurl+"]");
        sb.append("各位，晚安好梦");
        return sb.toString();
    }
}
