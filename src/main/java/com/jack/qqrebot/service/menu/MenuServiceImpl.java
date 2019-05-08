package com.jack.qqrebot.service.menu;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 16:38
 * @Description:
 * @Version: 1.0
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Override
    public String getMenus() {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("功能列表\n\n");
        String[] array = new String[]{"诗","新闻","笑话/段子","美图","音乐","天气","微博/热搜","老黄历","星座","毒鸡汤","干货",
                "干货日报类型","资源","v2ex","leetcode","土味情话","二次元图片","menhera酱","snh","买女装"};
        for (int i=0;i<array.length;i++){
            stringBuffer.append((i+1)).append(". ").append(array[i]).append("\n");
        }
        return stringBuffer.toString();
    }
}
