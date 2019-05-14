package com.jack.qqrebot.utils;

import org.springframework.util.ResourceUtils;

import java.io.*;

/**
 * @Auther: mujj
 * @Date: 2019/5/14 17:47
 * @Description:
 * @Version: 1.0
 */
public class UpdateUtils {

    public static String getUpdate(){
        StringBuffer sb = new StringBuffer();
        BufferedReader bf = null;
        InputStreamReader inputReader = null;
        try {
            File file = ResourceUtils.getFile("classpath:updateinfo.txt");
            inputReader = new InputStreamReader(new FileInputStream(file));
            bf = new BufferedReader(inputReader);
            // 按行读取字符串
            String str;
            while ((str = bf.readLine()) != null) {
                if(str.contains("===============")){
                   return sb.toString();
                }
                sb.append(str).append("\n");
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
        return sb.toString();
    }
}
