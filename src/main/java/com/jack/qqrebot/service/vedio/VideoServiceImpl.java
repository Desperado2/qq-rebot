package com.jack.qqrebot.service.vedio;

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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: mujj
 * @Date: 2019/5/9 23:40
 * @Description:
 * @Version: 1.0
 */
@Service("videoService")
public class VideoServiceImpl implements VideoService {
    @Override
    public String getVideoByKeyword(String keyword) {
        if(StringUtils.isEmpty(keyword)){
            return "命令错误，正确格式给 [影视 影视名称]";
        }
        StringBuffer stringBuffer = new StringBuffer();
        String url="http://ibahu.com/index.php?m=vod-search";
        AtomicInteger index = new AtomicInteger(1);
        String param =null;
        try {
             param ="wd="+URLEncoder.encode(keyword,"utf-8") +"&submit=";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = HttpUtils.sendPost(url, param);
        Document document = Jsoup.parse(result);
        Elements elements = document.select("ul[class=stui-vodlist__media col-pd clearfix]").select("li");
        elements.forEach(element -> {
            Element element1 = element.select("h3[class=title]").select("a").get(0);
            String title = element1.text();
            String href = "http://ibahu.com"+element1.attr("href");
            stringBuffer.append(index.get()).append(".").append(title).append("\n").append(href).append("\n");
            index.getAndIncrement();
        });
        return stringBuffer.toString();
    }

    @Override
    public String getVideoRealUrl(String shortUrl) {
        if(StringUtils.isEmpty(shortUrl)){
            return "命令错误，正确格式给 [短视频 短链接],本接口支持：快手,抖音,微视,皮皮虾,最右,火山,WIDE,IM短影,映客IN,小红书,小咖秀，陌陌,微博,秒拍,梨视频,美拍,今日头条,西瓜视频,宽频,音悦台,YY神曲,唱吧,全民K歌等视频解析";
        }
        String msg = null;
        String url="https://api.w0ai1uo.org/shipin.php";
        String param ="url="+shortUrl;
        String result = HttpUtils.sendGet(url, param);
        JSONObject object = JSONObject.parseObject(result);
        Integer code = object.getInteger("code");
        if(code == 101){
            msg = object.getJSONObject("data").getString("videourl");
        }else{
            msg = object.getString("msg");
            msg += "\n本接口支持：快手,抖音,微视,皮皮虾,最右,火山,WIDE,IM短影,映客IN,小红书,小咖秀，陌陌,微博,秒拍,梨视频,美拍,今日头条,西瓜视频,宽频,音悦台,YY神曲,唱吧,全民K歌等视频解析";
        }

        if(StringUtils.isEmpty(msg)){
            msg ="解析失败\n本接口支持：快手,抖音,微视,皮皮虾,最右,火山,WIDE,IM短影,映客IN,小红书,小咖秀，陌陌,微博,秒拍,梨视频,美拍,今日头条,西瓜视频,宽频,音悦台,YY神曲,唱吧,全民K歌等视频解析";
        }else if(msg.contains("http")){
            JSONObject data = object.getJSONObject("data");
            msg = customMsg(data);
        }
        return msg;
    }

    private String customMsg(JSONObject object){
        String url = object.getString("videourl");
        String title = object.getString("title");
        String image = object.getString("img");

        StringBuilder sb = new StringBuilder();
        sb.append("[CQ:share,url=");
        sb.append(url).append(",").append("title=").append("解析结果如下").append(",")
                .append("content=").append(title).append(",").append("image=").append(image)
                .append("]");

        return sb.toString();
    }
}
