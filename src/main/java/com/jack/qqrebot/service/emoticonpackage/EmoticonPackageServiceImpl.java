package com.jack.qqrebot.service.emoticonpackage;

import com.jack.qqrebot.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Auther: mujj
 * @Date: 2019/3/22 21:51
 * @Description:
 * @Version: 1.0
 */
@Service("emoticonPackageService")
public class EmoticonPackageServiceImpl implements EmoticonPackageService {
    @Override
    public String getEmoticonPackageByKeyWord(String keyWord) {
        String url ="https://pic.sogou.com/pic/emo/searchList.jsp?statref=home_form&keyword=";
        String[] strings = keyWord.split("\\*");
        if(strings.length <1){
            return "格式错误，正确格式: 表情包*关键字*数量(买买买*3)";
        }
        int num = 5;
        if(strings.length >= 2) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(strings[1]);
            if( isNum.matches() ){
                num = Integer.parseInt(strings[1]) > 10 ? 10 : Integer.parseInt(strings[1]);
            }
        }
        List<String> list = null;
        Object[] array= null;
        try {
            String html = HttpUtils.sendGet(url+URLEncoder.encode(strings[0],"UTF-8"), "");
            Document document = Jsoup.parse(html);
            Element body = document.body();
            Elements elements = body.select("section[class=recall-module]").select("li");
            List<String> imgurls = elements.stream().map(element -> element.select("img").attr("rsrc")).collect(Collectors.toList());
            list = imgurls.size() > num ? imgurls.subList(0, num) : imgurls;

            Elements name =  body.select("section[class=recall-module]").select("a[uigs=gnameClick]");
            Elements total = body.select("section[class=recall-module]").select("a[uigs=emocntClick]");

            array = Stream.iterate(0, item -> item + 1).limit(name.size()).map(i -> name.get(i).text() + "*" + total.get(i).text().replace("张","")).toArray();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Object[] optional = Optional.ofNullable(list).orElse(new ArrayList<>()).stream().map(imgurl->"[CQ:image,file="+imgurl+"]").toArray();
        String message = "\n\n没找到想要的，可搜索下面关键词\n"+Arrays.toString(array).replace("[","").replace("]","").replace(",","\n");
        String images =Arrays.toString(optional);
        return images.substring(1,images.length()-1).replace("],","]")+message;
    }
}
