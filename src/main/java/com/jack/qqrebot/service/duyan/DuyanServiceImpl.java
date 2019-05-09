package com.jack.qqrebot.service.duyan;

import com.jack.qqrebot.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 16:35
 * @Description:
 * @Version: 1.0
 */
@Service("duyanService")
public class DuyanServiceImpl implements DuyanService{

    @Override
    public String getDuyanRandom()  {
        String url1 ="http://duyan.fooor.cn/word.php";
        return HttpUtils.sendGet(url1, "");
    }

    @Override
    public String getJitangRandom() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddHHmmss");
        String format = LocalDateTime.now().format(dtf);
        int totalPage = 365;
        int page = Integer.parseInt(format) % totalPage;
        String result = HttpUtils.sendGet("http://www.59xihuan.cn/inidex_"+page+".html", "");
        Document document = Jsoup.parse(result);
        Elements elements = document.select("div[class=post]");
        Random random = new Random();
        int index = random.nextInt(elements.size()-1);
        Element element = elements.get(index);
        Elements elements1 = element.select("div[class=pic_text1]");
        String text = elements1.text();
        String attr = "http://www.59xihuan.cn"+elements1.select("img").attr("src");
        return text+"\n[CQ:image,file="+attr+"]";
    }


}
