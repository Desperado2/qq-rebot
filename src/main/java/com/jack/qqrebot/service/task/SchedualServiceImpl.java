package com.jack.qqrebot.service.task;


import com.jack.qqrebot.service.SendServiceI;
import com.jack.qqrebot.service.articles.ArticlesService;
import com.jack.qqrebot.service.codercalendar.CodeCalendarService;
import com.jack.qqrebot.service.dailyenglish.DailyEnglishService;
import com.jack.qqrebot.service.duyan.DuyanService;
import com.jack.qqrebot.service.gankService.GankeService;
import com.jack.qqrebot.service.historyontoday.HistoryOnTodayService;
import com.jack.qqrebot.service.leetcode.LeetCodeService;
import com.jack.qqrebot.service.mealreminder.MealReminderService;
import com.jack.qqrebot.service.news.NewsService;
import com.jack.qqrebot.service.proxy.ProxyService;
import com.jack.qqrebot.service.snh.SNHMembersService;
import com.jack.qqrebot.service.visitcontoller.VisitService;
import com.jack.qqrebot.service.weather.WeatherService;
import com.jack.qqrebot.service.weibo.WeiboService;
import com.jack.qqrebot.utils.CQUtils;
import com.jack.qqrebot.utils.SendMsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@Service("schedualService")
public class SchedualServiceImpl implements SchedualServiceI {

    private final WeatherService weatherService;
    private final DailyEnglishService dailyEnglishService;
    private final WeiboService weiboService;
    private final NewsService newsService;
    private final DuyanService duyanService;
    private final CodeCalendarService codeCalendarService;
    private final ArticlesService articlesService;
    private final HistoryOnTodayService historyOnTodayService;
    private final GankeService gankeService;
    private final LeetCodeService leetCodeService;
    private final SNHMembersService snhMembersService;
    private final VisitService visitService;
    private final ProxyService proxyService;
    private final SendServiceI sendService;
    private final MealReminderService mealReminderService;

    @Autowired
    public SchedualServiceImpl(ArticlesService articlesService, WeatherService weatherService, DailyEnglishService dailyEnglishService, WeiboService weiboService,
                               NewsService newsService, DuyanService duyanService, CodeCalendarService codeCalendarService, HistoryOnTodayService historyOnTodayService,
                               GankeService gankeService, LeetCodeService leetCodeService, SNHMembersService snhMembersService, VisitService visitService,
                               ProxyService proxyService, SendServiceI sendService,MealReminderService mealReminderService) {
        this.articlesService = articlesService;
        this.weatherService = weatherService;
        this.dailyEnglishService = dailyEnglishService;
        this.weiboService = weiboService;
        this.newsService = newsService;
        this.duyanService = duyanService;
        this.codeCalendarService = codeCalendarService;
        this.historyOnTodayService = historyOnTodayService;
        this.gankeService = gankeService;
        this.leetCodeService = leetCodeService;
        this.snhMembersService = snhMembersService;
        this.visitService = visitService;
        this.proxyService = proxyService;
        this.sendService = sendService;
        this.mealReminderService = mealReminderService;
    }



    @Override
    public void goodMorning() {
        //获取天气
        boolean flag = false;
        try {
            String weatherInfo = weatherService.getTodayWeather();
            String msg  = dailyEnglishService.getDailyEnglish();
            String messages = "天气:\n\n"+weatherInfo+"\n\n"+msg;

            List<String> groupList = CQUtils.getGroupList();
            groupList.forEach(groupId->SendMsgUtils.sendGroupMsg(groupId,messages));
        }catch (Exception e){
            flag = true;
        }finally {
           if(flag){
               ScheduledExecutorService scheduledExecutorService =
                       Executors.newScheduledThreadPool(1);
                scheduledExecutorService.schedule(this::goodMorning,60,TimeUnit.SECONDS);
           }
        }
    }

    @Override
    public void weibo() {
        boolean flag = false;
        try {
            List<String> groupList = CQUtils.getGroupList();
            String messages = weiboService.getWeiboHot();

            groupList.forEach(groupId->SendMsgUtils.sendGroupMsg(groupId,messages));
        }catch (Exception e){
            flag = true;
        }finally {
            if(flag){
                ScheduledExecutorService scheduledExecutorService =
                        Executors.newScheduledThreadPool(1);
                scheduledExecutorService.schedule(this::weibo,60,TimeUnit.SECONDS);
            }
        }

    }

    @Override
    public void everyDayNews() {
        boolean flag = false;
        try {
            List<String> groupList = CQUtils.getGroupList();
            String messages = newsService.getNewsByRandom();
            String message1 = gankeService.report("wanqu");
            groupList.forEach(groupId->{
                if(groupId.equals("89303705") || groupId.equals("604195931")) {
                    SendMsgUtils.sendGroupMsg(groupId, message1);
                }else{
                    SendMsgUtils.sendGroupMsg(groupId,messages);
                }
            });
        }catch (Exception e){
            flag = true;
        }finally {
            if(flag){
                ScheduledExecutorService scheduledExecutorService =
                        Executors.newScheduledThreadPool(1);
                scheduledExecutorService.schedule(this::everyDayNews,60,TimeUnit.SECONDS);
            }
        }

    }

    @Override
    public void goodLight() {
        boolean flag = false;
        try {
            List<String> groupList = CQUtils.getGroupList();
            String messages =  duyanService.getJitangRandom() +"\n\n各位晚安";
            groupList.forEach(groupId->SendMsgUtils.sendGroupMsg(groupId, messages));
        }catch (Exception e){
            flag = true;
        }finally {
            if(flag){
                ScheduledExecutorService scheduledExecutorService =
                        Executors.newScheduledThreadPool(1);
                scheduledExecutorService.schedule(this::goodLight,60,TimeUnit.SECONDS);
            }
        }

    }

    @Override
    public void coderCalendar() {
        boolean flag = false;
        try {
            String coderCalendar = codeCalendarService.getTodayCoderCalendar();
            Arrays.stream(new String[]{"89303705","604195931"}).forEach(groupId -> {
                SendMsgUtils.sendGroupMsg(groupId,coderCalendar);
            });
        }catch (Exception e){
            flag = true;
        }finally {
            if(flag){
                ScheduledExecutorService scheduledExecutorService =
                        Executors.newScheduledThreadPool(1);
                scheduledExecutorService.schedule(this::coderCalendar,60,TimeUnit.SECONDS);
            }
        }

    }

    @Override
    public void articles() {
        boolean flag = false;
        try {
            String msg = "如果没事，就来看看文章学习吧！";
            String articleByRandom = articlesService.getArticleByRandom();
            SendMsgUtils.sendGroupMsg("89303705",msg+"\n\n"+articleByRandom);
        }catch (Exception e){
            flag = true;
        }finally {
            if(flag){
                ScheduledExecutorService scheduledExecutorService =
                        Executors.newScheduledThreadPool(1);
                scheduledExecutorService.schedule(this::articles,60,TimeUnit.SECONDS);
            }
        }

    }

    @Override
    public void historyOnToday() {
        boolean flag = false;
        try {
            String history = historyOnTodayService.getHistory();
            SendMsgUtils.sendGroupMsg("89303705",history);
        }catch (Exception e){
            flag = true;
        }finally {
            if(flag){
                ScheduledExecutorService scheduledExecutorService =
                        Executors.newScheduledThreadPool(1);
                scheduledExecutorService.schedule(this::historyOnToday,60,TimeUnit.SECONDS);
            }
        }

    }

    @Override
    public void leetCode() {
        boolean flag = false;
        try {
            String msg = "如果没事，就赶快来显示一下自己的实力吧！";
            String articleByRandom = leetCodeService.randomProblem();
            Arrays.stream(new String[]{"89303705","604195931"}).forEach(groupId -> {
                SendMsgUtils.sendGroupMsg(groupId,msg+"\n\n"+articleByRandom);
            });
        }catch (Exception e){
            flag = true;
        }finally {
            if(flag){
                ScheduledExecutorService scheduledExecutorService =
                        Executors.newScheduledThreadPool(1);
                scheduledExecutorService.schedule(this::leetCode,60,TimeUnit.SECONDS);
            }
        }


    }

    @Override
    public void sNHMember() {
        boolean flag = false;
        try {
            List<String> groupList = CQUtils.getGroupList();
            String messages = snhMembersService.getRandomMember();
            groupList.forEach(groupId->SendMsgUtils.sendGroupMsg(groupId, messages));
        }catch (Exception e){
            flag = true;
        }finally {
            if(flag){
                ScheduledExecutorService scheduledExecutorService =
                        Executors.newScheduledThreadPool(1);
                scheduledExecutorService.schedule(this::sNHMember,60,TimeUnit.SECONDS);
            }
        }

    }

    @Override
    public void checkVisit() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                visitService.check();
            }
        });

    }

    @Override
    public void checkHMD() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                visitService.checkHMD();
            }
        });
    }

    @Override
    public void getProxyIp() {
        proxyService.getProxyIp();
    }

    @Override
    public void clearCount() {
        sendService.clearCount();
    }

    @Override
    public void reminderMeal() {
        mealReminderService.reminder();
    }


}
