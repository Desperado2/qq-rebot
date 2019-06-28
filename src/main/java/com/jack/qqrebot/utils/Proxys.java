package com.jack.qqrebot.utils;

import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.ArrayList;

/**
 * @Auther: mujj
 * @Date: 2019/5/13 21:04
 * @Description:
 * @Version: 1.0
 */
public class Proxys {
    private volatile static ArrayList<String> proxys = new ArrayList<>();
    static {
        readProxyIpFile();
    }

    public static ArrayList<String> getProxy(){
        return proxys;
    }

    public  static void updateProxy(){
        synchronized (proxys){
            readProxyIpFile();
        }
    }

    private static void readProxyIpFile(){
        proxys.clear();
        BufferedReader bf = null;
        InputStreamReader inputReader = null;
        try {
            File file = ResourceUtils.getFile("classpath:proxyip.txt");
            inputReader = new InputStreamReader(new FileInputStream(file));
            bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;
            while ((str = bf.readLine()) != null) {
                String[] split = str.split(":");
                if( ProxyUtils.checkIp(split[0],split[1])){
                     proxys.add(str);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bf.close();
                inputReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
