package com.jack.qqrebot.service.ranking;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service("rankingService")
public class RankingServiceImpl implements RankingService {

    @Value("${desperado.cq.locatiion:#{null}}")
    private String cqLocation;

    @Value("${desperado.ranking.users:#{null}}")
    private String[] rankUsers;

    @Override
    public String getRanking(){
        if(StringUtils.isEmpty(cqLocation)){
            try {
                throw new Exception("请配置机器人跟目录");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String text = "";
        StringBuilder text1 = new StringBuilder();
        try {
            File file = new File(cqLocation+"data\\phb\\score.txt");
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

    @Override
    public String updateRanking(String message){

        String[] strings = message.split("\\|");
        if(strings.length < 4){
            return getRanking();
        }else {
            String name = strings[1];
            String type = strings[2];
            if(type.equals("+")){
                type = "add";
            }else if(type.equals("-")){
                type = "sub";
            }else{
                return "只支持 + 和 - 操作符";
            }
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(strings[3]);
            if (isNum.matches()) {
                int score = Integer.parseInt(strings[3]);
                if(score ==10 || score == 5){
                    return update(name,type,score);
                }
                return "只接受 +10 和 -5 两个分值";
            } else {
                return "参数格式错误\n格式：排行榜|黄博|+|20";
            }
        }
    }

    private String update(String name,String type,int score){
        if(StringUtils.isEmpty(cqLocation)){
            try {
                throw new Exception("请配置机器人跟目录");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        StringBuilder sb = new StringBuilder();
        try {
            File file = new File(cqLocation+"data\\phb\\score.txt");
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
        if(StringUtils.isEmpty(rankUsers)){
            return false;
        }
        for (String name:rankUsers){
            if (text.contains(name)){
                return true;
            }
        }
        return false;
    }
}
