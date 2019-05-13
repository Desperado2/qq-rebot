package com.jack.qqrebot.utils;


import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;


/**
 * @Auther: mujj
 * @Date: 2019/5/13 20:43
 * @Description:
 * @Version: 1.0
 */
public class ProxyUtils {

    public static String getOneProxyIp() {
        return readProxyIpFile();
    }

    private static String readProxyIpFile(){
        int count = 0;
        ArrayList<String> proxys = Proxys.getProxy();
        if(proxys.size() == 0){
            return null;
        }
        while (count++ > 5) {
            Random random = new Random();
            int index = random.nextInt(proxys.size());
            String[] split = proxys.get(index).split(":");
            if( checkIp(split[0],split[1])){
                return proxys.get(index);
            }
        }
        return null;
    }



    public static    boolean checkIp(String ip,String port) {
        URL url = null;
        try {
            url = new URL("http://www.baidu.com");
        } catch (MalformedURLException e) {
            System.out.println("url invalidate");
        }
        InetSocketAddress addr = null;
        addr = new InetSocketAddress(ip, Integer.parseInt(port));
        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); // http proxy
        InputStream in = null;
        try {
            assert url != null;
            URLConnection conn = url.openConnection(proxy);
            conn.setConnectTimeout(1000);
            in = conn.getInputStream();
        } catch (Exception e) {
            return false;//异常IP
        }
        String s = convertStreamToString(in);
        return s.indexOf("baidu") > 0;
    }


    private static String convertStreamToString(InputStream is) {
        if (is == null)
            return "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("/n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();

    }
}
