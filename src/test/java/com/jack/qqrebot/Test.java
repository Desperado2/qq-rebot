package com.jack.qqrebot;

import com.jack.qqrebot.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Test {



    public static void main(String[] args) {
        String url ="https://www.xzw.com/fortune/aries";
        String get = HttpUtils.sendGet(url, "");
        Document document = Jsoup.parse(get);

        Elements elements = document.select("dd").select("li");
        for (int i =0 ;i <elements.size(); i++){
            Element element = elements.get(i);
            if(element.select("em").size() > 0){
                String label = element.select("label").text();
                int i1 = Integer.parseInt(element.select("em").attr("style").replace("px;", "").replace("width:","").trim())/12;
                System.out.println(label + i1+"颗星");
            }else{
                System.out.println(element.select("label").text()+element.text());
            }

        }
        Elements elements1 = document.select("div[class=c_cont]").select("p");
        for (int i =0; i<elements1.size(); i++){
            Element element = elements1.get(i);
            System.out.println(element.select("strong").text()+":"+element.select("span").text());
        }
    }
}
