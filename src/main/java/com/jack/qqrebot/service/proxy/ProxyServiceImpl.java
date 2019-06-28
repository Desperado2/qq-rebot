package com.jack.qqrebot.service.proxy;

import com.jack.qqrebot.utils.HttpUtils;
import com.jack.qqrebot.utils.ProxyUtils;
import com.jack.qqrebot.utils.Proxys;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * @Auther: mujj
 * @Date: 2019/5/13 19:37
 * @Description:
 * @Version: 1.0
 */
@Service("proxyService")
public class ProxyServiceImpl implements ProxyService {
    @Override
    public void getProxyIp() {
        ArrayList<String> arrayList = getIpFromNetwork();
        StringBuffer sb = new StringBuffer();
        arrayList.forEach(sb::append);
        writeProxyIpFile(sb.toString());
        Proxys.updateProxy();
    }


    private void writeProxyIpFile(String text){
        FileOutputStream fos = null;
        File file = null;
        try {
             file = ResourceUtils.getFile("classpath:proxyip.txt");
             fos = new FileOutputStream(file);
            fos.write(text.getBytes());
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private ArrayList<String> getIpFromNetwork(){
        ArrayList<String> list = new ArrayList<>();
        int index = 1;
        while (list.size() < 100){

            String url ="https://www.kuaidaili.com/free/inha/"+index+"/";
            String result = HttpUtils.sendGet(url, null);
            Document document = Jsoup.parse(result);
            Element body = document.body();
            Elements items = body.getElementsByTag("table").select("tr");
            if(result.equals("") || items.size() == 0){
                break;
            }
            items.forEach(item->{
                Elements tds = item.getElementsByTag("td");
                if(tds != null && tds.size() > 1){
                    String ip = tds.get(0).text();
                    String port = tds.get(1).text();
                    if(ProxyUtils.checkIp(ip,port)){
                        list.add(ip+":"+port+"\n");
                    }
                }
            });
            index++;
        }
        return list;
    }

}
