package com.jack.qqrebot.service.leetcode;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Random;

/**
 * @Auther: mujj
 * @Date: 2019/4/30 15:14
 * @Description:
 * @Version: 1.0
 */
@Service("leetCodeService")
public class LeetCodeServiceImpl implements LeetCodeService {
    @Override
    public String randomProblem() {
        String url="https://leetcode-cn.com/api/problems/all/";
        String result = HttpUtils.sendGet(url, "");
        JSONObject object = JSONObject.parseObject(result);

        JSONArray statStatusPairs = object.getJSONArray("stat_status_pairs");

        Random random = new Random();
        int i = random.nextInt(statStatusPairs.size());

        JSONObject object1 = statStatusPairs.getJSONObject(i);

        JSONObject stat = object1.getJSONObject("stat");
        String title = stat.getString("question__title");
        String id = stat.getString("question_id");
        Integer ace = stat.getInteger("total_acs");
        String slug = stat.getString("question__title_slug");
        Integer commitedCount = stat.getInteger("total_submitted");
        Integer level = object1.getJSONObject("difficulty").getInteger("level");
        double pass = Double.valueOf(ace) / commitedCount;
        StringBuffer sb = new StringBuffer();

        sb.append("为你找到一道题目为：【");
        sb.append(title);
        sb.append("】\n");
        sb.append("id为：").append(id).append("\n");
        sb.append("难度为：").append(levelMatch(level)).append("\n");
        sb.append("点击挑战自我吧：");
        sb.append("https://leetcode-cn.com/problems/");
        sb.append(slug);
        sb.append("\n");
        sb.append("该题通过数为：").append(ace).append("\n总提交数为:").append(commitedCount);
        sb.append("\n");
        sb.append("最新通过率为：").append(NumberFormat.getPercentInstance().format(pass));
        sb.append("\n");
        sb.append("不要提交后成为分母拉低通过率哦~~");
        return sb.toString();
    }

    private String levelMatch(Integer level) {
        String res;
        switch (level) {
            case 1:
                res = "简单难度";
                break;
            case 2:
                res = "中等难度";
                break;
            case 3:
                res = "困难难度";
                break;
            default:
                res = String.valueOf(level);
                break;
        }
        return res;
    }
}
