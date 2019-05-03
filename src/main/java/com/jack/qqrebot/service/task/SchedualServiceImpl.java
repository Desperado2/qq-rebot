package com.jack.qqrebot.service.task;


import com.jack.qqrebot.service.articles.ArticlesService;
import com.jack.qqrebot.service.codercalendar.CodeCalendarService;
import com.jack.qqrebot.service.dailyenglish.DailyEnglishService;
import com.jack.qqrebot.service.duyan.DuyanService;
import com.jack.qqrebot.service.gankService.GankeService;
import com.jack.qqrebot.service.historyontoday.HistoryOnTodayService;
import com.jack.qqrebot.service.leetcode.LeetCodeService;
import com.jack.qqrebot.service.news.NewsService;
import com.jack.qqrebot.service.snh.SNHMembersService;
import com.jack.qqrebot.service.weather.WeatherService;
import com.jack.qqrebot.service.weibo.WeiboService;
import com.jack.qqrebot.utils.CQUtils;
import com.jack.qqrebot.utils.SendMsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;
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
    @Autowired
    private GankeService gankeService;
    @Autowired
    private LeetCodeService leetCodeService;
    @Autowired
    private SNHMembersService snhMembersService;
    @Override
    public void goodMorning() {
        //获取天气
        String weatherInfo = weatherService.getTodayWeather();
        String msg  = dailyEnglishService.getDailyEnglish();
        String messages = "天气:\n\n"+weatherInfo+"\n\n"+msg;

        List<Integer> groupList = CQUtils.getGroupList();

        groupList.forEach(groupId->SendMsgUtils.sendGroupMsg(groupId,messages));
    }

    @Override
    public void weibo() {
        List<Integer> groupList = CQUtils.getGroupList();
        String messages = weiboService.getWeiboHot();

        groupList.forEach(groupId->SendMsgUtils.sendGroupMsg(groupId,messages));
    }

    @Override
    public void everyDayNews() {
        List<Integer> groupList = CQUtils.getGroupList();
        String messages = newsService.getNewsByRandom();
        String message1 = gankeService.report("wanqu");
        groupList.forEach(groupId->{
            if(groupId == 89303705 || groupId == 604195931) {
                SendMsgUtils.sendGroupMsg(groupId, message1);
            }else{
                SendMsgUtils.sendGroupMsg(groupId,messages);
            }
        });
    }

    @Override
    public void goodLight() {
        List<Integer> groupList = CQUtils.getGroupList();
        String messages =  duyanService.getDuyanRandom() +"\n\n各位晚安";
        groupList.forEach(groupId->SendMsgUtils.sendGroupMsg(groupId, messages));
    }

    @Override
    public void coderCalendar() {
        String coderCalendar = codeCalendarService.getTodayCoderCalendar();
        Arrays.stream(new Integer[]{89303705,604195931}).forEach(groupId -> {
            SendMsgUtils.sendGroupMsg(groupId,coderCalendar);
        });
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

    @Override
    public void leetCode() {
        String msg = "[CQ:at,qq=all] 如果没事，就赶快来显示一下自己的实力吧！";
        String articleByRandom = leetCodeService.randomProblem();
        Arrays.stream(new Integer[]{89303705,604195931}).forEach(groupId -> {
            SendMsgUtils.sendGroupMsg(groupId,msg);
            SendMsgUtils.sendGroupMsg(groupId,articleByRandom);
        });

    }

    @Override
    public void sNHMember() {
        List<Integer> groupList = CQUtils.getGroupList();
        String messages = snhMembersService.getRandomMember();
        groupList.forEach(groupId->SendMsgUtils.sendGroupMsg(groupId, messages));
    }
}
