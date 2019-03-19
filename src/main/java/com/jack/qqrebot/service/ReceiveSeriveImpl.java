package com.jack.qqrebot.service;

import com.jack.qqrebot.CQApiServices.CqApi;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;


import java.io.*;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service("receiveService")
public class ReceiveSeriveImpl implements ReceiveServiceI {

    String url ="http://127.0.0.1:5300/";
    @Override
    public void phb(int groupId,String message) throws UnsupportedEncodingException {
        String[] strings = message.split("\\|");
        String phb = "";
        if(strings.length < 4){
            phb = getPhb();
        }else{
            String name = strings[1];
            String type = strings[2].equals("+")?"add":"sub";
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(strings[3]);
            if( isNum.matches() ){
                int score = Integer.parseInt(strings[3]);
                phb = addScore(name, score, type);
            }else {
                phb = "参数格式错误\n格式：排行榜|黄博|+|20";
            }
        }
        HttpUtils.sendPost(url+CqApi.SEND_GROUP_MSG.getName(),"group_id="+groupId+"&message="+URLEncoder.encode(phb,"utf-8"));
    }

    private String getPhb(){
        String text = "";
        String text1 = "";
        try {
            File file = new File("C:\\CQPro\\data\\phb\\score.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            while ((text=bufferedReader.readLine()) != null){
                text1+=text+"\n";
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text1;
    }

    private String addScore(String name,int score,String type){

        String text1 = "";
        try {
            File file = new File("C:\\CQPro\\data\\phb\\score.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String text = "";
            while ((text=bufferedReader.readLine()) != null){
                if(text.contains(name)){
                    String regEx="[^0-9]";
                    Pattern p = Pattern.compile(regEx);
                    Matcher m = p.matcher(text);
                    if(type.equals("add")){
                        text = name+"       "+(Integer.parseInt(m.replaceAll("").trim())+score)+"分";
                    }else{
                        text = name+"       "+(Integer.parseInt(m.replaceAll("").trim())-score)+"分";
                    }

                }
                text1 += text+"\n";

            }
           bufferedReader.close();

            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write(text1);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text1;
    }
}
