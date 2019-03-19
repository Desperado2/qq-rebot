package com.jack.qqrebot.service.codercalendar;

import com.jack.qqrebot.utils.CoderCalendar;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 16:27
 * @Description:
 * @Version: 1.0
 */
@Service("codeCalendarService")
public class CodeCalendarServiceImpl implements CodeCalendarService{

    @Override
    public String getTodayCoderCalendar()  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String today = sdf.format(new Date());
        File file  = new File("C:\\CQPro\\data\\image\\"+today+".jpg");
        if(!file.exists()){
            CoderCalendar.createImage();
        }
        String message = today+".jpg";
        message = "[CQ:image,file="+message+"]";
        return message;
    }
}
