package com.jack.qqrebot.service.book;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: mujj
 * @Date: 2019/5/10 00:04
 * @Description:
 * @Version: 1.0
 */
@Service("bookService")
public class BookServiceImpl implements BookService {
    @Override
    public String getBookByKeyword(String keyword) {
        if(StringUtils.isEmpty(keyword)){
            return "命令错误，正确格式给 [电子书 电子书名称]";
        }
        String id = getId(keyword);
        if(StringUtils.isEmpty(id)){
            return "查询失败";
        }
        StringBuilder stringBuffer = new StringBuilder();
        String url="https://www.jiumodiary.com/ajax_fetch_hubs.php";
        String param = "id="+id+"&set=0";
        String result = HttpUtils.sendPost(url, param);
        JSONObject object = JSONObject.parseObject(result);
        List<BookEntity> list = null;
        if("succeed".equals(object.getString("status"))){
            JSONArray sources = object.getJSONArray("sources");
            list = getData(sources, "pan.baidu.com", 10);
            if(list.size() < 10){
                List<BookEntity> data = getData(sources, "vdisk.weibo.com", 10 - list.size());
                list.addAll(data);
            }
        }
        if(!StringUtils.isEmpty(list) && list.size() > 0){
            AtomicInteger index = new AtomicInteger(1);
            list.forEach(bookEntity -> {
                stringBuffer.append(index.get()).append(".").append(bookEntity.getTitle()).append("\n").append(bookEntity.getLink()).append("\n");
                index.getAndIncrement();
            });
        }
        return stringBuffer.toString();
    }

    private List<BookEntity> getData(JSONArray sources,String keyword,int size){
        int count = 1;
        BookEntity bookEntity = null;
        List<BookEntity> list = new ArrayList<>();
        for (int i=0; i< sources.size();i++){
            JSONArray array = sources.getJSONObject(i).getJSONObject("details").getJSONArray("data");
            for (int j=0;j<array.size(); j++){
                JSONObject jsonObject = array.getJSONObject(j);
                String link = jsonObject.getString("link");
                if(!StringUtils.isEmpty(link) && link.contains(keyword)){
                    String title = jsonObject.getString("title");
                    bookEntity = new BookEntity(link,title);
                    list.add(bookEntity);
                    if(++count > size){
                        return list;
                    }
                }
            }
        }
        return list;
    }
    private String getId(String keyword){
        String url="https://www.jiumodiary.com/init_hubs.php";
        String param ="q="+keyword+"&remote_ip=&time_int=1557417585387";
        String result = HttpUtils.sendPost(url, param);
        JSONObject object = JSONObject.parseObject(result);
        if("succeed".equals(object.getString("status"))){
            return object.getString("id");
        }
        return null;
    }
}
