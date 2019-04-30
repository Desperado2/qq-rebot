package com.jack.qqrebot.service.baiduyundisk;

import com.jack.qqrebot.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: mujj
 * @Date: 2019/4/30 12:05
 * @Description:
 * @Version: 1.0
 */
@Service("baiduDiskSearchService")
public class BaiduDiskSearchServiceImpl implements BaiduDiskSearchService {
    @Override
    public String SearchByKeyWords(String keyword) {
        String url ="https://www.xiaobd.net/m/search";
        try {
            String param = "wd="+URLEncoder.encode(keyword,"UTF-8");
            String result = HttpUtils.sendGet(url, param);
            Document document = Jsoup.parse(result);
            Element body = document.body();
            Elements items = body.getElementsByAttributeValueContaining("class","item_");
            StringBuffer stringBuffer = new StringBuffer();
            AtomicInteger index = new AtomicInteger(1);
            items.forEach(item -> {
                Elements tag = item.getElementsByTag("a");
                String title = tag.attr("title");
                if(!title.contains("[+]") && index.get() < 14){
                    String href = tag.attr("href");
                    stringBuffer.append(index.get()).append(". ").append(title).append("\n").append(href).append("\n");
                    index.getAndIncrement();
                }
            });
            stringBuffer.append(index.get()).append(". 查看").append(keyword).append("全部资源").append("\n")
                    .append("https://xiaobd.net/index.php?mod=search&").append(param);
            return stringBuffer.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "查询失败请重试";
    }
}
