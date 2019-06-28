package com.jack.qqrebot.service.codercalendar;

import com.jack.qqrebot.utils.CoderCalendar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 16:27
 * @Description:
 * @Version: 1.0
 */
@Service("codeCalendarService")
public class CodeCalendarServiceImpl implements CodeCalendarService{

    @Value("${desperado.cq.location:#{null}}")
    private String cqLocation;

    @Override
    public String getTodayCoderCalendar()  {
        if(StringUtils.isEmpty(cqLocation)){
            try {
                throw new Exception("请配置机器人跟目录");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = now.format(dtf);

        File file  = new File(cqLocation+"data\\image\\"+today+".jpg");
        if(!file.exists()){
            CoderCalendar.createImage(cqLocation);
        }
        String message = today+".jpg";
        message = "[CQ:image,file="+message+"]";
        return message;
    }
}
