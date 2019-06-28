package com.jack.qqrebot.service.blacklist;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: mujj
 * @Date: 2019/5/28 16:38
 * @Description:
 * @Version: 1.0
 */
@Service("blackListService")
public class BlackListServiceImpl implements BlackListService {

    @Value("${desperado.cq.locatiion:#{null}}")
    private String cqLocation;

    @Override
    public String addBackList(String userId) {
        StringBuilder sb = new StringBuilder();
        String msg = null;
        try {
            File file = new File(cqLocation+"data\\phb\\dashang.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
            String text = "";
            while ((text=bufferedReader.readLine()) != null){
                if(text.contains(userId)){
                    msg = "用户["+userId+"]已在黑名单之中";
                }
                sb.append(text).append("\n");
            }
            bufferedReader.close();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
            bufferedWriter.write(sb.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg == null?"[CQ:at,qq=" + userId + "] 您已被机器人加入黑名单，无法再使用机器人了，拜拜了":msg;
    }

    @Override
    public String delBackList(String userId) {
        StringBuilder sb = new StringBuilder();
        String msg = null;
        try {
            File file = new File(cqLocation+"data\\phb\\dashang.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
            String text = "";
            while ((text=bufferedReader.readLine()) != null){
                if(text.contains(userId)){
                    msg = "[CQ:at,qq=" + userId + "] 恭喜你，可以使用机器人了，欢迎";
                    continue;
                }
                sb.append(text).append("\n");
            }
            bufferedReader.close();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
            bufferedWriter.write(sb.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg == null?"该用户不在黑名单中":msg;
    }
}
