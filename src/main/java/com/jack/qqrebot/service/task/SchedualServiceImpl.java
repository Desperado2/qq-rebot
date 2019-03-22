package com.jack.qqrebot.service.task;


import com.jack.qqrebot.service.articles.ArticlesService;
import com.jack.qqrebot.service.codercalendar.CodeCalendarService;
import com.jack.qqrebot.service.dailyenglish.DailyEnglishService;
import com.jack.qqrebot.service.duyan.DuyanService;
import com.jack.qqrebot.service.historyontoday.HistoryOnTodayService;
import com.jack.qqrebot.service.news.NewsService;
import com.jack.qqrebot.service.weather.WeatherService;
import com.jack.qqrebot.service.weibo.WeiboService;
import com.jack.qqrebot.utils.CQUtils;
import com.jack.qqrebot.utils.SendMsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.UnsupportedEncodingException;
import java.util.List;

@Service("schedualService")
public class SchedualServiceImpl implements SchedualServiceI {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private DailyEnglishService dailyEnglishService;
    @Autowired
    private WeiboService weiboService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private DuyanService duyanService;
    @Autowired
    private CodeCalendarService codeCalendarService;
    @Autowired
    private ArticlesService articlesService;
    @Autowired
    private HistoryOnTodayService historyOnTodayService;

    @Override
    public void goodMorning() {
        //获取天气
        String weatherInfo = weatherService.getTodayWeather();
        String msg  = dailyEnglishService.getDailyEnglish();
        String messages = "天气:\n\n"+weatherInfo+"\n\n"+msg;

        List<Integer> groupList = CQUtils.getGroupList();

        groupList.stream().forEach(groupId->SendMsgUtils.sendGroupMsg(groupId,messages));
    }

    @Override
    public void weibo() {
        List<Integer> groupList = CQUtils.getGroupList();
        String messages = weiboService.getWeiboHot();

        groupList.stream().forEach(groupId->SendMsgUtils.sendGroupMsg(groupId,messages));
    }

    @Override
    public void everyDayNews() {
        List<Integer> groupList = CQUtils.getGroupList();
        String messages = newsService.getNewsByRandom();

        groupList.stream().forEach(groupId->SendMsgUtils.sendGroupMsg(groupId,messages));
    }

    @Override
    public void goodLight() {
        List<Integer> groupList = CQUtils.getGroupList();
        String messages =  duyanService.getDuyanRandom() +"\n\n各位晚安";
        groupList.stream().forEach(groupId->SendMsgUtils.sendGroupMsg(groupId, messages));
    }

    @Override
    public void coderCalendar() {
        String coderCalendar = codeCalendarService.getTodayCoderCalendar();
        SendMsgUtils.sendGroupMsg(89303705,coderCalendar);
    }

    @Override
    public void articles() {
        String msg = "[CQ:at,qq=all] 如果没事，就来看看文章学习吧！";
        SendMsgUtils.sendGroupMsg(89303705,msg);
        String articleByRandom = articlesService.getArticleByRandom();
        SendMsgUtils.sendGroupMsg(89303705,articleByRandom);
    }

    @Override
    public void historyOnToday() {
        String history = historyOnTodayService.getHistory();
        SendMsgUtils.sendGroupMsg(89303705,history);
    }
}
