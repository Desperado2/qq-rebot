package com.jack.qqrebot.service.baiduyundisk;

import com.jack.qqrebot.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;
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
            if(items == null || items.size() == 0){
                searchByKey(keyword,stringBuffer);
            }else{
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
            }
            return stringBuffer.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "查询失败请重试";
    }

    private String searchByKey(String keyword,StringBuffer stringBuffer){
        String url ="https://www.57fx.com/search/";
        try {
            Random random = new Random();
            int x = random.nextInt(119);
            int y = random.nextInt(42);
            String param = "kw="+URLEncoder.encode(keyword,"UTF-8")+"&button_search.x="+x+"&button_search.y="+y+"&searchType=all";
            String result = HttpUtils.sendPost(url, param);

            Document document = Jsoup.parse(result);
            Element body = document.body();
            Elements items = body.getElementsByTag("dd");
            AtomicInteger index = new AtomicInteger(1);

            items.forEach(item -> {
                Elements tag = item.getElementsByTag("a");
                String title = tag.attr("title");
                if(index.get() < 14){
                    String href = tag.attr("href");
                    String userable = checkUrlUserable(href);
                    if(!StringUtils.isEmpty(userable)){
                        stringBuffer.append(index.get()).append(". ").append(title).append("\n").append(userable).append("\n");
                        index.getAndIncrement();
                    }
                }
            });
            return stringBuffer.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "查询失败请重试";
    }

    private String checkUrlUserable(String url){
        if(StringUtils.isEmpty(url)){
            return null;
        }
        url="https://www.57fx.com"+url;
        String result = HttpUtils.sendGet(url, null);
        String bdyUrl = null;
        Document document = Jsoup.parse(result);
        Element body = document.body();
        Elements items1 = body.getElementsByClass("toyunDown");
        Elements items2 = body.getElementsByTag("span").select("[style]");

        if(items1 != null && items1.size() > 0){
             bdyUrl = items1.get(0).attr("href");
        }
        boolean b = checkBaiduUrl(bdyUrl);
        if(b){
            return bdyUrl;
        }
        if(items2 != null && items2.size() > 0){
            bdyUrl = items2.get(0).text().replace("原资源链接：","");
        }
        b = checkBaiduUrl(bdyUrl);
        if(b){
            return bdyUrl;
        }
        return null;
    }

    private boolean checkBaiduUrl(String url){
        if(StringUtils.isEmpty(url)){
            return false;
        }
        if(!url.contains("https")){
            url = url.replace("http","https");
        }
        String result = HttpUtils.sendGet(url, null);
        Document document = Jsoup.parse(result);
        Element body = document.body();
        Elements elements = body.getElementsByClass("error-img");
        if(elements == null || elements.size() == 0){
            return true;
        }
        return false;
    }
}
