package com.jack.qqrebot.utils;

import java.util.HashMap;
import java.util.Map;

public class XzUtils {

    public static String getXz(String xzName){
        Map<String ,String> map= new HashMap<>();
        map.put("白羊座","aries");
        map.put("金牛座","taurus");
        map.put("双子座","gemini");
        map.put("巨蟹座","cancer");
        map.put("狮子座","leo");
        map.put("处女座","virgo");
        map.put("天秤座","libra");
        map.put("天蝎座","scorpio");
        map.put("射手座","sagittarius");
        map.put("摩羯座","capricorn");
        map.put("水瓶座","aquarius");
        map.put("双鱼座","pisces");

        for (String str : map.keySet()){
            if(xzName.contains(str)){
                return map.get(str);
            }
        }
       return null;
    }
}
