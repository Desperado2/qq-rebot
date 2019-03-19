package com.jack.qqrebot.service.constellation;

import com.jack.qqrebot.CQApiServices.CqApi;
import com.jack.qqrebot.utils.HttpUtils;
import com.jack.qqrebot.utils.XzUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

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
        if(StringUtils.isEmpty(s)){
            constellationName ="message=没有找到你要查询的星座";
        }else{
            constellationName =constellationName+"今日运势:\n\n";
            String url1 ="https://www.xzw.com/fortune/"+s;
            String get = HttpUtils.sendGet(url1, "");
            Document document = Jsoup.parse(get);
            Elements elements = document.select("dd").select("li");
            for (int i =0 ;i <elements.size(); i++){
                Element element = elements.get(i);
                if(element.select("em").size() > 0){
                    String label = element.select("label").text();
                    int i1 = Integer.parseInt(element.select("em").attr("style").replace("px;", "").replace("width:","").trim())/12;
                    constellationName+= label + i1+"颗星\n";
                }
            }
            constellationName += "详情:"+url1;
        }
        return constellationName;
    }
}
