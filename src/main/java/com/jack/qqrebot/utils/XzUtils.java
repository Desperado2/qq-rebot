package com.jack.qqrebot.utils;

import java.util.HashMap;
import java.util.Map;

public class XzUtils {

    public static String getXz(String xzName){
        Map<String ,String> map= new HashMap<>();
        map.put("白羊","aries");
        map.put("金牛","taurus");
        map.put("双子","gemini");
        map.put("巨蟹","cancer");
        map.put("狮子","leo");
        map.put("处女","virgo");
        map.put("天秤","libra");
        map.put("天蝎","scorpio");
        map.put("射手","sagittarius");
        map.put("摩羯","capricorn");
        map.put("水瓶","aquarius");
        map.put("双鱼","pisces");

        for (String str : map.keySet()){
            if(xzName.contains(str)){
                return map.get(str);
            }
        }
       return null;
    }
}
