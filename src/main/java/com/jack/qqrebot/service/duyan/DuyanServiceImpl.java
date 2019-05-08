package com.jack.qqrebot.service.duyan;

import com.jack.qqrebot.utils.HttpUtils;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 16:35
 * @Description:
 * @Version: 1.0
 */
@Service("duyanService")
public class DuyanServiceImpl implements DuyanService{

    @Override
    public String getDuyanRandom()  {
        String url1 ="http://duyan.fooor.cn/word.php";
        return HttpUtils.sendGet(url1, "");
    }
}
