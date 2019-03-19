package com.jack.qqrebot.service;

import com.jack.qqrebot.CQApiServices.CqApi;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;


import java.io.*;
import java.net.URLEncoder;
import java.util.*;
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
        StringBuilder text1 = new StringBuilder();
        try {
            File file = new File("C:\\CQPro\\data\\phb\\score.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));

            while ((text=bufferedReader.readLine()) != null){
                text1.append(text).append("\n");
            }
            bufferedReader.close();
            sort(text1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text1.toString();
    }

    private String addScore(String name,int score,String type){

        StringBuilder sb = new StringBuilder();
        try {
            File file = new File("C:\\CQPro\\data\\phb\\score.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
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
               sb.append(text).append("\n");
            }
           bufferedReader.close();
            sort(sb);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"utf-8"));
            bufferedWriter.write(sb.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void sort(StringBuilder text){
        String[] strings = text.toString().split("\n");
        TreeMap<Integer,String> treeMap = new TreeMap<>((o1, o2) -> -o1.compareTo(o2));
        int index=0;
        int index1= 0;
        for (int i=0;i<strings.length; i++){
            if(isContains(strings[i])){
                index1 = index;
                String regEx="[^0-9]";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(strings[i]);
                int key =Integer.parseInt(m.replaceAll("").trim());
                if(treeMap.containsKey(key))
                    key++;
                treeMap.put(key,strings[i]);
            }else{
                index++;
            }
        }

        for (Integer key : treeMap.keySet()){
            strings[index1] = treeMap.get(key);
            index1++;
        }
        text.delete(0,text.length());
        for (int i=0;i<strings.length; i++){
            text.append(strings[i]).append("\n");
        }
    }

    private boolean isContains(String text){
        List<String> list = new ArrayList<>();
        list.add("张亚泰");
        list.add("黄博");
        list.add("郝红剑");
        list.add("胡军");
        list.add("辛季凯");
        list.add("陈宗明");

        for (String name:list){
            if (text.contains(name)){
                return true;
            }
        }
        return false;
    }
}
