package com.jack.qqrebot.service.constellation;

import com.jack.qqrebot.utils.HttpUtils;
import com.jack.qqrebot.utils.XzUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 16:31
 * @Description:
 * @Version: 1.0
 */
@Service("constellationService")
public class ConstellationServiceImpl implements ConstellationService{

    @Override
    public String getMsgByConstellationName(String constellationName) throws UnsupportedEncodingException {
        String s =XzUtils.getXz(constellationName);
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isEmpty(s)){
            sb.append("没有找到你要查询的星座,查询格式:巨蟹座运势");
        }else{
            sb.append(constellationName).append("今日运势:\n\n");
            String url ="https://www.xzw.com/fortune/"+s;
            String get = HttpUtils.sendGet(url, "");
            Document document = Jsoup.parse(get);
            Elements elements = document.select("dd").select("li");
            for (int i =0 ;i <elements.size(); i++){
                Element element = elements.get(i);
                if(element.select("em").size() > 0){
                    String label = element.select("label").text();
                    int star = Integer.parseInt(element.select("em").attr("style").replace("px;", "").replace("width:","").trim())/12;
                    sb.append(label).append(Stream.of("★★★★★".split("")).limit(star).collect(Collectors.joining()))
                            .append(Stream.of("☆☆☆☆☆".split("")).limit(5-star).collect(Collectors.joining())).append("\n");
                }else{
                    String label = element.text();
                    sb.append(label).append("\n");
                }
            }
            elements = document.select("div[class=c_cont]").select("p");
            for (Element element : elements){
                String label = element.select("strong").text();
                String value = element.select("span").text();
                sb.append(label).append(":").append(value).append("\n");
            }
            sb.append("详情:").append(url);
        }
        return sb.toString();
    }
}
