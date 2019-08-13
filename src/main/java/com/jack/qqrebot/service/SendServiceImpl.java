package com.jack.qqrebot.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.enumm.ConnType;
import com.jack.qqrebot.jst.GroupNoticeService;
import com.jack.qqrebot.service.articles.ArticlesService;
import com.jack.qqrebot.service.baiduyundisk.BaiduDiskSearchService;
import com.jack.qqrebot.service.blacklist.BlackListService;
import com.jack.qqrebot.service.book.BookService;
import com.jack.qqrebot.service.codercalendar.CodeCalendarService;
import com.jack.qqrebot.service.constellation.ConstellationService;
import com.jack.qqrebot.service.dashang.DashangService;
import com.jack.qqrebot.service.duyan.DuyanService;
import com.jack.qqrebot.service.emoticonpackage.EmoticonPackageService;
import com.jack.qqrebot.service.erciyuna.PicService;
import com.jack.qqrebot.service.gankService.GankeService;
import com.jack.qqrebot.service.historyontoday.HistoryOnTodayService;
import com.jack.qqrebot.service.leetcode.LeetCodeService;
import com.jack.qqrebot.service.mealreminder.MealReminderService;
import com.jack.qqrebot.service.mealreminder.MealReminderVo;
import com.jack.qqrebot.service.meitu.MeituService;
import com.jack.qqrebot.service.menu.MenuService;
import com.jack.qqrebot.service.music.MusicService;
import com.jack.qqrebot.service.news.NewsService;
import com.jack.qqrebot.service.notice.NoticeService;
import com.jack.qqrebot.service.poetry.PoetryService;
import com.jack.qqrebot.service.programer.ProgramerService;
import com.jack.qqrebot.service.ranking.RankingService;
import com.jack.qqrebot.service.satin.SatinService;
import com.jack.qqrebot.service.saylove.SayLoveService;
import com.jack.qqrebot.service.snh.SNHMembersService;
import com.jack.qqrebot.service.tuling.TulingService;
import com.jack.qqrebot.service.v2ex.V2exService;
import com.jack.qqrebot.service.vedio.VideoService;
import com.jack.qqrebot.service.visitcontoller.VisitService;
import com.jack.qqrebot.service.weather.WeatherService;
import com.jack.qqrebot.service.weibo.WeiboService;
import com.jack.qqrebot.utils.CQUtils;
import com.jack.qqrebot.utils.SendMsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service("sendService")
public class SendServiceImpl implements SendServiceI {

    @Value("${desperado.admin.qq:#{null}}")
    private String adminqq;

    @Value("${desperado.wuai.group.id:#{null}}")
    private String wuaiGroupId;

    @Value("${desperado.rebot.qq:#{null}}")
    private String rebotQQ;

    private final CodeCalendarService codeCalendarService;
    private final ConstellationService constellationService;
    private final MeituService meituService;
    private final MenuService menuService;
    private final MusicService musicService;
    private final PoetryService poetryService;
    private final RankingService rankingService;
    private final SatinService satinService;
    private final TulingService tulingService;
    private final WeatherService weatherService;
    private final WeiboService weiboService;
    private final NewsService newsService;
    private final DuyanService duyanService;
    private final ArticlesService articlesService;
    private final HistoryOnTodayService historyOnTodayService;
    private final EmoticonPackageService emoticonPackageService;
    private final BaiduDiskSearchService baiduDiskSearchService;
    private final GankeService gankeService;
    private final V2exService v2exService;
    private final LeetCodeService leetCodeService;
    private final SayLoveService sayLoveService;
    private final PicService picService;
    private final SNHMembersService snhMembersService;
    private final DashangService dashangService;
    private final NoticeService noticeService;
    private final VideoService videoService;
    private final BookService bookService;
    private final VisitService visitService;
    private final BlackListService blackListService;
    private final ProgramerService programerService;
    private final MealReminderService mealReminderService;
    private final GroupNoticeService groupNoticeService;

    private Map<String, Integer> map = new HashMap<>();

    @Autowired
    public SendServiceImpl(CodeCalendarService codeCalendarService, ConstellationService constellationService, SayLoveService sayLoveService,
                           PicService picService, MeituService meituService, MenuService menuService, MusicService musicService, PoetryService poetryService,
                           NewsService newsService, ArticlesService articlesService, SNHMembersService snhMembersService, RankingService rankingService,
                           HistoryOnTodayService historyOnTodayService, LeetCodeService leetCodeService, DuyanService duyanService, SatinService satinService,
                           TulingService tulingService, NoticeService noticeService, GankeService gankeService, V2exService v2exService, WeatherService weatherService,
                           DashangService dashangService, WeiboService weiboService, BaiduDiskSearchService baiduDiskSearchService, EmoticonPackageService emoticonPackageService,
                           VideoService videoService, BookService bookService, VisitService visitService, BlackListService blackListService, ProgramerService programerService,
                           MealReminderService mealReminderService,GroupNoticeService groupNoticeService) {
        this.codeCalendarService = codeCalendarService;
        this.constellationService = constellationService;
        this.sayLoveService = sayLoveService;
        this.picService = picService;
        this.meituService = meituService;
        this.menuService = menuService;
        this.musicService = musicService;
        this.poetryService = poetryService;
        this.newsService = newsService;
        this.articlesService = articlesService;
        this.snhMembersService = snhMembersService;
        this.rankingService = rankingService;
        this.historyOnTodayService = historyOnTodayService;
        this.leetCodeService = leetCodeService;
        this.duyanService = duyanService;
        this.satinService = satinService;
        this.tulingService = tulingService;
        this.noticeService = noticeService;
        this.gankeService = gankeService;
        this.v2exService = v2exService;
        this.weatherService = weatherService;
        this.dashangService = dashangService;
        this.weiboService = weiboService;
        this.baiduDiskSearchService = baiduDiskSearchService;
        this.emoticonPackageService = emoticonPackageService;
        this.videoService = videoService;
        this.bookService = bookService;
        this.visitService = visitService;
        this.blackListService = blackListService;
        this.programerService = programerService;
        this.mealReminderService = mealReminderService;
        this.groupNoticeService = groupNoticeService;
    }

    @Override
    public void dealGroupMsg(String message) throws UnsupportedEncodingException {
        JSONObject jsonObject = JSON.parseObject(message);
        String result = "";
        message = jsonObject.getString("message");

        if (message.contains("[CQ:at,qq="+rebotQQ+"]")) {
            String group_id = jsonObject.getString("group_id");
            String user_id = jsonObject.getString("user_id");
            if (addMap(user_id + "")) {
                return;
            }
            ConnType count = visitService.addVisitRecord(String.valueOf(user_id), System.currentTimeMillis());
            if (count == ConnType.IS_WARN) {
                result = "[CQ:at,qq=" + user_id + "] 警告，您在一分钟之内超过5次使用机器人，请注意";
                SendMsgUtils.sendGroupMsg(group_id, result);
                return;
            }

            if (count == ConnType.IS_JINYAN) {
                result = "[CQ:at,qq=" + user_id + "] 警告，您在一分钟之内超过5次使用机器人多次，禁言5分钟";
                SendMsgUtils.sendGroupMsg(group_id, result);
                CQUtils.ban(group_id, user_id, 5 * 60);
                return;
            }

            if (count == ConnType.IS_HMD) {
                result = "[CQ:at,qq=" + user_id + "] 警告，您在一分钟之内超过5次使用机器人5次，机器人不在提供服务，请3小时后在试";
                SendMsgUtils.sendGroupMsg(group_id, result);
                return;
            }

            message = message.replace("[CQ:at,qq=1244623542]", "").trim();
            if (!StringUtils.isEmpty(message) && message.contains("诗")) {
                result = poetryService.getPoetryByKeyWord(message.replace("诗", "").replace(" ", ""));
            }  if (!StringUtils.isEmpty(message) && (message.toLowerCase().contains("jz"))) {
                result = groupNoticeService.getJz();
            } else if (!StringUtils.isEmpty(message) && message.contains("新闻")) {
                result = newsService.getNewsByRandom();
            } else if (!StringUtils.isEmpty(message) && (message.contains("段子") || message.contains("笑话"))) {
                result = satinService.getSatinByRandom();
            } else if (!StringUtils.isEmpty(message) && (message.contains("二次元美图"))) {
                result = picService.getRandomPic();
            } else if (!StringUtils.isEmpty(message) && message.contains("美图")) {
                result = meituService.getImageByRandom();
            } else if (!StringUtils.isEmpty(message) && message.contains("影视")) {
                result = videoService.getVideoByKeyword(message.replace("影视", "").replace(" ", ""));
            } else if (!StringUtils.isEmpty(message) && message.contains("电子书")) {
                result = bookService.getBookByKeyword(message.replace("电子书", "").replace(" ", ""));
            } else if (!StringUtils.isEmpty(message) && message.contains("音乐")) {
                result = musicService.getMusicByName(message.replace("音乐", "").replace(" ", ""));
            } else if (!StringUtils.isEmpty(message) && message.contains("多日天气")) {
                result = weatherService.getWeatherByCity(message.replace("多日天气", "").replace(" ", ""));
            } else if (!StringUtils.isEmpty(message) && message.contains("天气")) {
                result = weatherService.getCurrWeatherByCity(message.replace("天气", "").replace(" ", ""));
            } else if (!StringUtils.isEmpty(message) && (message.contains("微博") || (message.contains("热搜")))) {
                result = weiboService.getWeiboHot();
            } else if (!StringUtils.isEmpty(message) && (message.contains("菜单"))) {
                result = menuService.getMenus();
            } else if (!StringUtils.isEmpty(message) && (message.contains("运势") || (message.contains("星座")))) {
                result = constellationService.getMsgByConstellationName(message.replace("运势", "").replace("星座", "").replace(" ", ""));
            } else if (!StringUtils.isEmpty(message) && (message.contains("黄历"))) {
                result = codeCalendarService.getTodayCoderCalendar();
            } else if (!StringUtils.isEmpty(message) && (message.contains("毒鸡汤"))) {
                result = duyanService.getDuyanRandom();
            } else if (!StringUtils.isEmpty(message) && (message.contains("心灵鸡汤"))) {
                result = duyanService.getJitangRandom();
            } else if (!StringUtils.isEmpty(message) && (message.contains("排行榜"))) {
                result = rankingService.updateRanking(message);
            } else if (!StringUtils.isEmpty(message) && (message.contains("文章"))) {
                result = articlesService.getArticleByRandom();
            } else if (!StringUtils.isEmpty(message) && (message.contains("历史上的今天"))) {
                result = historyOnTodayService.getHistory();
            } else if (!StringUtils.isEmpty(message) && (message.contains("表情包"))) {
                result = emoticonPackageService.getEmoticonPackageByKeyWord(message.replace("表情包*", ""));
            } else if (!StringUtils.isEmpty(message) && (message.contains("资源"))) {
                result = baiduDiskSearchService.SearchByKeyWords(message.replace("资源", "").replace(" ", ""));
            } else if (!StringUtils.isEmpty(message) && (message.equals("干货"))) {
                result = gankeService.getToadyData();
            } else if (!StringUtils.isEmpty(message) && (message.contains("干货今日"))) {
                result = gankeService.getTodayDataByCategory(message.replace("干货今日", "").replace(" ", ""));
            } else if (!StringUtils.isEmpty(message) && (message.contains("干货日报类型"))) {
                result = gankeService.reportType();
            } else if (!StringUtils.isEmpty(message) && (message.contains("干货日报"))) {
                result = gankeService.report(message.replace("干货日报", "").replace(" ", ""));
            } else if (!StringUtils.isEmpty(message) && (message.contains("干货"))) {
                result = gankeService.getDataByCategory(message.replace("干货", "").replace(" ", ""));
            } else if (!StringUtils.isEmpty(message) && (message.contains("v2ex"))) {
                result = v2exService.hotTopics();
            } else if (!StringUtils.isEmpty(message) && (message.contains("leetcode"))) {
                result = leetCodeService.randomProblem();
            } else if (!StringUtils.isEmpty(message) && (message.contains("情话"))) {
                result = sayLoveService.getLoveRandom();
            } else if (!StringUtils.isEmpty(message) && (message.contains("短视频"))) {
                result = videoService.getVideoRealUrl(message.replace("短视频", "").replace(" ", ""));
            } else if (!StringUtils.isEmpty(message) && (message.contains("menhera"))) {
                result = picService.getRandomMenhera();
            } else if (!StringUtils.isEmpty(message) && (message.toLowerCase().contains("snh"))) {
                result = snhMembersService.getRandomMember();
            } else if (!StringUtils.isEmpty(message) && (message.contains("买女装排行"))) {
                result = dashangService.getRank();
            } else if (!StringUtils.isEmpty(message) && (message.contains("支持买女装"))) {
                if (user_id.equals(adminqq)) {
                    result = dashangService.updateRank(message.replace("支持买女装", "").trim());
                } else {
                    result = "[CQ:at,qq=" + user_id + "] 你无权执行该操作";
                }
            } else if (!StringUtils.isEmpty(message) && (message.contains("买女装"))) {
                result = dashangService.getUlr();
            } else if (!StringUtils.isEmpty(message) && (message.contains("添加黑名单"))) {
                if (user_id.equals(adminqq)) {
                    result = blackListService.addBackList(message.replace("添加黑名单", "").trim());
                } else {
                    result = "[CQ:at,qq=" + user_id + "] 你无权执行该操作";
                }

            } else if (!StringUtils.isEmpty(message) && (message.contains("解除黑名单"))) {
                if (user_id.equals(adminqq)) {
                    result = blackListService.addBackList(message.replace("添加黑名单", "").trim());
                } else {
                    result = "[CQ:at,qq=" + user_id + "] 你无权执行该操作";
                }
            } else if (!StringUtils.isEmpty(message) && message.startsWith("吾爱")) {
                if (wuaiGroupId.contains(group_id)) {
                    result = programerService.dealRequest(group_id + "", user_id + "", message);
                } else {
                    result = "无权操作";
                }
            } else if (!StringUtils.isEmpty(message) && message.contains("午餐提醒")) {
                MealReminderVo mealReminderVo = new MealReminderVo();
                Integer userId = Integer.parseInt(user_id);
                Integer groupId = Integer.parseInt(group_id);
                mealReminderVo.setUserId(userId);
                mealReminderVo.setGroupId(groupId);
                System.out.println(user_id);
                System.out.println(group_id);
                mealReminderService.add(mealReminderVo);
                result = "[CQ:at,qq=" + user_id + "]已设置好提醒!";
                SendMsgUtils.sendGroupMsg(user_id, result);
            } else {
                result = tulingService.getMsgByMsg(message);
            }
            SendMsgUtils.sendGroupMsg(group_id, result);
        }
    }

    @Override
    public void dealNotice(String message) {
        JSONObject jsonObject = JSON.parseObject(message);
        String result = "";
        String noticeType = jsonObject.getString("notice_type");
        String group_id = jsonObject.getString("group_id");
        if (noticeType.equals("group_increase")) {
            String user_id = jsonObject.getString("user_id");
            if(("261434765").equals(group_id)){
                result= groupNoticeService.groupIncreaseNotice(user_id);
            }else{
                result = noticeService.groupIncrease(user_id);
            }
        } else if (noticeType.equals("group_decrease")) {
            String user_id = jsonObject.getString("user_id");
            result = noticeService.groupDecrease(user_id);
        } else if (noticeType.equals("group_upload")) {
            if (group_id.equals(wuaiGroupId)) {
                JSONObject file = jsonObject.getJSONObject("file");
                if (!StringUtils.isEmpty(file)) {
                    String filename = file.getString("name").replace(".txt", "").trim();
                    Integer tid = Integer.parseInt(filename);
                    programerService.updateStatus(tid);
                }
            }
        }
        SendMsgUtils.sendGroupMsg(group_id, result);
    }

    @Override
    public void clearCount() {
        map.clear();
    }

    @Override
    public void dealPrivateMsg(String message) throws UnsupportedEncodingException {
        JSONObject jsonObject = JSON.parseObject(message);
        String result = "";
        String userId = jsonObject.getString("user_id");
        message = jsonObject.getString("message");
        if (adminqq.equals(userId)) {
            if (!StringUtils.isEmpty(message) && message.startsWith("吾爱")) {
                result = programerService.dealRequest(wuaiGroupId + "", adminqq + "", message);
            }
        } else {
            result = tulingService.getMsgByMsg(message);
        }
        SendMsgUtils.sendPrivateMsg(userId, result);
    }

    private boolean addMap(String userId) {
        if (map.containsKey(userId)) {
            Integer count = map.get(userId) + 1;
            map.put(userId, count);
            return count > 20;
        }
        map.put(userId, 1);
        return false;
    }
}
