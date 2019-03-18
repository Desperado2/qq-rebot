package com.jack.qqrebot.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: mujj
 * @Date: 2019/3/18 14:45
 * @Description:
 * @Version: 1.0
 */
public class CoderCalendar {

    private static JSONArray activities = JSONArray.parseArray(" [\n" +
            "    {name:\"写单元测试\", good:\"写单元测试将减少出错\",bad:\"写单元测试会降低你的开发效率\"},\n" +
            "    {name:\"洗澡\", good:\"你几天没洗澡了？\",bad:\"会把设计方面的灵感洗掉\"},\n" +
            "    {name:\"锻炼一下身体\", good:\"\",bad:\"能量没消耗多少，吃得却更多\"},\n" +
            "    {name:\"抽烟\", good:\"抽烟有利于提神，增加思维敏捷\",bad:\"除非你活够了，死得早点没关系\"},\n" +
            "    {name:\"白天上线\", good:\"今天白天上线是安全的\",bad:\"可能导致灾难性后果\"},\n" +
            "    {name:\"重构\", good:\"代码质量得到提高\",bad:\"你很有可能会陷入泥潭\"},\n" +
            "    {name:\"使用%t\", good:\"你看起来更有品位\",bad:\"别人会觉得你在装逼\"},\n" +
            "    {name:\"跳槽\", good:\"该放手时就放手\",bad:\"鉴于当前的经济形势，你的下一份工作未必比现在强\"},\n" +
            "    {name:\"招人\", good:\"你遇到千里马的可能性大大增加\",bad:\"你只会招到一两个混饭吃的外行\"},\n" +
            "    {name:\"面试\", good:\"面试官今天心情很好\",bad:\"面试官不爽，会拿你出气\"},\n" +
            "    {name:\"提交辞职申请\", good:\"公司找到了一个比你更能干更便宜的家伙，巴不得你赶快滚蛋\",bad:\"鉴于当前的经济形势，你的下一份工作未必比现在强\"},\n" +
            "    {name:\"申请加薪\", good:\"老板今天心情很好\",bad:\"公司正在考虑裁员\"},\n" +
            "    {name:\"晚上加班\", good:\"晚上是程序员精神最好的时候\",bad:\"\"},\n" +
            "    {name:\"在妹子面前吹牛\", good:\"改善你矮穷挫的形象\",bad:\"会被识破\"},\n" +
            "    {name:\"浏览成人网站\", good:\"重拾对生活的信心\",bad:\"你会心神不宁\"},\n" +
            "    {name:\"命名变量\\\"%v\\\"\", good:\"\",bad:\"\"},\n" +
            "    {name:\"写超过%l行的方法\", good:\"你的代码组织的很好，长一点没关系\",bad:\"你的代码将混乱不堪，你自己都看不懂\"},\n" +
            "    {name:\"提交代码\", good:\"遇到冲突的几率是最低的\",bad:\"你遇到的一大堆冲突会让你觉得自己是不是时间穿越了\"},\n" +
            "    {name:\"代码复审\", good:\"发现重要问题的几率大大增加\",bad:\"你什么问题都发现不了，白白浪费时间\"},\n" +
            "    {name:\"开会\", good:\"写代码之余放松一下打个盹，有益健康\",bad:\"你会被扣屎盆子背黑锅\"},\n" +
            "    {name:\"打DOTA\", good:\"你将有如神助\",bad:\"你会被虐的很惨\"},\n" +
            "    {name:\"晚上上线\", good:\"晚上是程序员精神最好的时候\",bad:\"你白天已经筋疲力尽了\"},\n" +
            "    {name:\"修复BUG\", good:\"你今天对BUG的嗅觉大大提高\",bad:\"新产生的BUG将比修复的更多\"},\n" +
            "    {name:\"设计评审\", good:\"设计评审会议将变成头脑风暴\",bad:\"人人筋疲力尽，评审就这么过了\"},\n" +
            "    {name:\"需求评审\", good:\"\",bad:\"\"},\n" +
            "    {name:\"上微博\", good:\"今天发生的事不能错过\",bad:\"会被老板看到\"},\n" +
            "    {name:\"上AB站\", good:\"还需要理由吗？\",bad:\"会被老板看到\"}\n" +
            "]\n");

    private static JSONArray weeks = JSONArray.parseArray("[\"日\",\"一\",\"二\",\"三\",\"四\",\"五\",\"六\"]");
    private static JSONArray directions = JSONArray.parseArray("[\"北方\",\"东北方\",\"东方\",\"东南方\",\"南方\",\"西南方\",\"西方\",\"西北方\"]");

    private static JSONArray specials = JSONArray.parseArray("[{date:20130221, type:'good', name:'防核演习', description:'万一哪个疯子丢颗核弹过来...'}\n" +
            "]");

    private static JSONArray tools = JSONArray.parseArray(" [\"Eclipse写程序\", \"MSOffice写文档\", \"记事本写程序\", \"Windows8\", \"Linux\", \"MacOS\", \"IE\", \"Android设备\", \"iOS设备\"]\n");
    private static JSONArray varNames = JSONArray.parseArray("[\"jieguo\", \"huodong\", \"pay\", \"expire\", \"zhangdan\", \"every\", \"free\", \"i1\", \"a\", \"virtual\", \"ad\", \"spider\", \"mima\", \"pass\", \"ui\"]");

    private static JSONArray drinks = JSONArray.parseArray("[\"水\",\"茶\",\"红茶\",\"绿茶\",\"咖啡\",\"奶茶\",\"可乐\",\"牛奶\",\"豆奶\",\"果汁\",\"果味汽水\",\"苏打水\",\"运动饮料\",\"酸奶\",\"酒\"]");

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");


    private static JSONArray getBads(){
        String iday = sdf.format(new Date());
        int numGood = random(iday, 98) % 3 + 2;
        int numBad = random(iday, 87) % 3 + 2;
        JSONArray eventArr = pickRandomActivity(numGood + numBad);

        JSONArray bads =new JSONArray();
        for (int i=0;i<numBad;i++){
            JSONObject object = eventArr.getJSONObject(numGood+i);
            bads.add(object);
        }
       return bads;
    }


    private static JSONArray getGoods(){
        String iday = sdf.format(new Date());
        int numGood = random(iday, 98) % 3 + 2;
        int numBad = random(iday, 87) % 3 + 2;
        JSONArray eventArr = pickRandomActivity(numGood + numBad);

        JSONArray goods = new JSONArray();
        for (int i=0;i<numGood;i++){
            JSONObject object = eventArr.getJSONObject(i);
            goods.add(object);
        }
        return goods;
    }

    private static Integer random(String dayseed, int indexseed) {

        int dseed = Integer.parseInt(dayseed);
        int n = dseed % 11117;
        for (int i = 0; i < 100 + indexseed; i++) {
            n = n * n;
            n = n % 11117;   // 11117 是个质数
        }
        return n;
    }

    // 添加预定义事件
    private static Integer[] pickSpecials() {
        Integer specialSize[] = new Integer[]{0,0};
        String iday = sdf.format(new Date());
        for (int i = 0; i < specials.size(); i++) {
            JSONObject special = specials.getJSONObject(i);

            if (iday.equals(special.getInteger("date"))) {
                if ("good".equals(special.getString("type"))) {
                    specialSize[0]++;
                } else {
                    specialSize[1]++;
                }
            }
        }
        return specialSize;
    }

    // 从 activities 中随机挑选 size 个
    private static JSONArray pickRandomActivity(int size) {
        JSONArray picked_events = pickRandom(activities, size);

        for (int i = 0; i < picked_events.size(); i++) {
            picked_events.set(i,parse(picked_events.getJSONObject(i)));
        }

        return picked_events;
    }

    // 从数组中随机挑选 size 个
    private static JSONArray pickRandom(JSONArray array, int size) {
        JSONArray result = new JSONArray();

        for (int i = 0; i < array.size(); i++) {
            result.add(array.getJSONObject(i));
        }

        for (int j = 0; j < array.size() - size; j++) {
            String iday = sdf.format(new Date());
            int index = random(iday, j) % result.size();
            result.remove(index);
        }
        return result;
    }

    // 从数组中随机挑选 size 个
    private static JSONArray pickRandomString(JSONArray array, int size) {
        JSONArray result = new JSONArray();
        for (int i = 0; i < array.size(); i++) {
            result.add(array.getString(i));
        }

        for (int j = 0; j < array.size() - size; j++) {
            String iday = sdf.format(new Date());//0-13
            int index = random(iday, j) % result.size(); // 15
            result.remove(index);
        }
        return result;
    }

    // 解析占位符并替换成随机内容
    private static JSONObject parse(JSONObject object) {
        String iday = sdf.format(new Date());
        if (object.getString("name").indexOf("%v") != -1) {
            object.put("name",object.getString("name").replace("%v", varNames.getString(random(iday, 12) % varNames.size())));
        }
        if (object.getString("name").indexOf("%t") != -1) {
            object.put("name",object.getString("name").replace("%t", tools.getString(random(iday, 12) % tools.size())));
        }
        if (object.getString("name").indexOf("%l") != -1) {
            object.put("name",object.getString("name").replace("%l", String.valueOf((random(iday, 12) % 247 + 30))));
        }
        return object;
    }

    private static String getDirection(){
        String iday = sdf.format(new Date());
        String direction = directions.getString(random(iday, 2) % directions.size());
        return "座位朝向：面向direction写程序，BUG 最少。".replace("direction",direction);
    }

    private static String getDrink(){
        JSONArray array = pickRandomString(drinks, 2);
        StringBuilder drink = new StringBuilder("今日宜饮: ");
        for (int i=0;i<array.size();i++){
            drink.append(array.getString(i)).append("、");
        }
        return drink.toString().substring(0,drink.length()-1);
    }

    private static String getGoddes(){
        String iday = sdf.format(new Date());
        double goddes = random(iday, 6) % 50 / 10.0;
        return "女神亲近指数："+goddes;
    }

    private static String getTodayString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String iday = sdf.format(new Date());
        String[] split = iday.split(",");
        return "今天是" + split[0] + "年" + split[1] + "月" + split[2] + "日 星期" + weeks.getString(new Date().getDay());
    }

    public static void createImage() {
        JSONArray goods = getGoods();
        JSONArray bads = getBads();
        String direction = getDirection();
        String drink = getDrink();
        String goddes = getGoddes();

        int good_H = 0;
        int bad_H = 0;
        int maxWidth = 0;
        for (int i=0;i<goods.size(); i++){
            JSONObject good = goods.getJSONObject(i);
            if(!StringUtils.isEmpty(good.getString("name"))){
                good_H += 30;
                int width =  good.getString("name").toCharArray().length*15;
                maxWidth = maxWidth > width ? maxWidth : width;
            }
            if(!StringUtils.isEmpty(good.getString("good"))){
                good_H += 14;
                int width =  good.getString("good").toCharArray().length*15;
                maxWidth = maxWidth > width ? maxWidth : width;
            }
            good_H += 20;
        }

        for (int i=0;i<bads.size(); i++){
            JSONObject bad = bads.getJSONObject(i);
            if(!StringUtils.isEmpty(bad.getString("name"))){
                bad_H += 30;
                int width =  bad.getString("name").toCharArray().length*15;
                maxWidth = maxWidth > width ? maxWidth : width;
            }
            if(!StringUtils.isEmpty(bad.getString("bad"))){
                bad_H += 14;
                int width =  bad.getString("bad").toCharArray().length*15;
                maxWidth = maxWidth > width ? maxWidth : width;
            }
            bad_H += 20;
        }
        ChartGraphics.graphicsGeneration(goods,bads,direction,drink,goddes,good_H-10,bad_H-10,maxWidth);
    }

    public static void main(String[] args) {
        createImage();
    }
}
