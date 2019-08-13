package com.jack.qqrebot.jst.wanan;

import com.jack.qqrebot.service.duyan.DuyanService;
import com.jack.qqrebot.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        sb.append("各位，晚安好梦");
        return null;
    }
}
