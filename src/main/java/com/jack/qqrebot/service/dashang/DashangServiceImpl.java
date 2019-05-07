package com.jack.qqrebot.service.dashang;

import com.jack.qqrebot.utils.CQUtils;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: mujj
 * @Date: 2019/5/3 16:31
 * @Description:
 * @Version: 1.0
 */
@Service("dashangService")
public class DashangServiceImpl implements DashangService {

    @Override
    public String getUlr() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("众筹卖女装项目").append("\n\n");
        stringBuffer.append("支付宝收款:").append("\n").append("[CQ:image,file=zfb.png]").append("\n");
        stringBuffer.append("微信收款:").append("\n").append("[CQ:image,file=wx.png]").append("\n");
        stringBuffer.append("QQ收款:").append("\n").append("[CQ:image,file=qq.png]").append("\n\n");
        stringBuffer.append("支付请备注自己qq号哦");
        return stringBuffer.toString();
    }

    @Override
    public String getRank() {
        String text = "";
        StringBuilder text1 = new StringBuilder();
        try {
            File file = new File("C:\\CQPro\\data\\phb\\dashang.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
            while ((text=bufferedReader.readLine()) != null){
                String[] split = text.split(" ");
                String memberName = CQUtils.getMemberName(split[0], split[1]);
                if(StringUtils.isEmpty(memberName)){
                    memberName = split[1];
                }
                text1.append(memberName).append("    ").append(split[2]).append("元").append("\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return text1.toString();
    }

    @Override
    public String updateRank(String message){

        String[] strings = message.split("\\|");
        if(strings.length < 3){
            return "失败";
        }else {
            String name = strings[0];
            String type = strings[1];
            String money = strings[2];

            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(money);
            if (isNum.matches()) {
                int score = Integer.parseInt(strings[2]);
                return update(name,type,score);
            } else {
                return "参数格式错误\n格式：支持买女装 群号|qq号|20";
            }
        }
    }

    private String update(String name,String type,int score){
        StringBuilder sb = new StringBuilder();
        boolean flag = true;
        try {
            File file = new File("C:\\CQPro\\data\\phb\\dashang.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
            String text = "";
            while ((text=bufferedReader.readLine()) != null){
                if(text.contains(type)){
                    String s = text.split(" ")[2];
                    text = name+" " +type +" "+(Integer.parseInt(s.trim())+score);
                    flag = false;
                }
                sb.append(text).append("\n");
            }
            if(flag){
                text = name+" " +type +" "+score;
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
        String memberName = CQUtils.getMemberName(name,type);
        return "感谢 ["+memberName+"]("+type+") 支持了 "+score+" 元";

    }

    private void sort(StringBuilder text){
        String[] strings = text.toString().split("\n");
        TreeMap<Integer,String> treeMap = new TreeMap<>((o1, o2) -> -o1.compareTo(o2));
        int index1= 0;
        for (int i=0;i<strings.length; i++){
            int key = Integer.parseInt(strings[i].split(" ")[2].trim());
            treeMap.put(key,strings[i]);
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

}
