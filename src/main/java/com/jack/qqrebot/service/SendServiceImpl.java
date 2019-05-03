package com.jack.qqrebot.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.service.articles.ArticlesService;
import com.jack.qqrebot.service.baiduyundisk.BaiduDiskSearchService;
import com.jack.qqrebot.service.codercalendar.CodeCalendarService;
import com.jack.qqrebot.service.constellation.ConstellationService;
import com.jack.qqrebot.service.duyan.DuyanService;
import com.jack.qqrebot.service.emoticonpackage.EmoticonPackageService;
import com.jack.qqrebot.service.erciyuna.PicService;
import com.jack.qqrebot.service.gankService.GankeService;
import com.jack.qqrebot.service.historyontoday.HistoryOnTodayService;
import com.jack.qqrebot.service.leetcode.LeetCodeService;
import com.jack.qqrebot.service.meitu.MeituService;
import com.jack.qqrebot.service.menu.MenuService;
import com.jack.qqrebot.service.music.MusicService;
import com.jack.qqrebot.service.news.NewsService;
import com.jack.qqrebot.service.poetry.PoetryService;
import com.jack.qqrebot.service.ranking.RankingService;
import com.jack.qqrebot.service.satin.SatinService;
import com.jack.qqrebot.service.saylove.SayLoveService;
import com.jack.qqrebot.service.tuling.TulingService;
import com.jack.qqrebot.service.v2ex.V2exService;
import com.jack.qqrebot.service.weather.WeatherService;
import com.jack.qqrebot.service.weibo.WeiboService;
import com.jack.qqrebot.utils.SendMsgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;



@Service("sendService")
public class SendServiceImpl implements SendServiceI {
    @Autowired
    private CodeCalendarService codeCalendarService;
    @Autowired
    private ConstellationService constellationService;
    @Autowired
    private MeituService meituService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MusicService musicService;
    @Autowired
    private PoetryService poetryService;
    @Autowired
    private RankingService rankingService;
    @Autowired
    private SatinService satinService;
    @Autowired
    private TulingService tulingService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private WeiboService weiboService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private DuyanService duyanService;
    @Autowired
    private ArticlesService articlesService;
    @Autowired
    private HistoryOnTodayService historyOnTodayService;
    @Autowired
    private EmoticonPackageService emoticonPackageService;
    @Autowired
    private BaiduDiskSearchService baiduDiskSearchService;
    @Autowired
    private GankeService gankeService;
    @Autowired
    private V2exService v2exService;
    @Autowired
    private LeetCodeService leetCodeService;
    @Autowired
    private SayLoveService sayLoveService;
    @Autowired
    private PicService picService;
    @Override
    public void dealGroupMsg(String message) throws UnsupportedEncodingException {
        JSONObject jsonObject = JSON.parseObject(message);
        String result = "";
        message = jsonObject.getString("message");
        Integer group_id = jsonObject.getInteger("group_id");
        if (message.contains("[CQ:at,qq=1244623542]")) {
            message = message.replace("[CQ:at,qq=1244623542]", "").trim();
            if (!StringUtils.isEmpty(message) && message.contains("诗")) {
                result = poetryService.getPoetryByKeyWord(message.replace("诗", "").replace(" ", ""));
            } else if (!StringUtils.isEmpty(message) && message.contains("新闻")) {
                result = newsService.getNewsByRandom();
            } else if (!StringUtils.isEmpty(message) && (message.contains("段子") || message.contains("笑话"))) {
                result = satinService.getSatinByRandom();
            }else if(!StringUtils.isEmpty(message) && (message.contains("二次元美图"))){
                result = picService.getRandomPic();
            } else if (!StringUtils.isEmpty(message) && message.contains("美图")) {
                result = meituService.getImageByRandom();
            } else if (!StringUtils.isEmpty(message) && message.contains("音乐")) {
                result = musicService.getMusicByName(message.replace("音乐", "").replace(" ", ""));
            } else if (!StringUtils.isEmpty(message) && message.contains("天气")) {
                result = weatherService.getWeatherByCity(message.replace("天气", "").replace(" ", ""));
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
            } else if (!StringUtils.isEmpty(message) && (message.contains("排行榜"))) {
                result = rankingService.updateRanking(message);
            } else if (!StringUtils.isEmpty(message) && (message.contains("文章"))) {
                result = articlesService.getArticleByRandom();
            } else if (!StringUtils.isEmpty(message) && (message.contains("历史上的今天"))) {
                result = historyOnTodayService.getHistory();
            } else if (!StringUtils.isEmpty(message) && (message.contains("表情包"))) {
                result = emoticonPackageService.getEmoticonPackageByKeyWord(message.replace("表情包*",""));
            }else if(!StringUtils.isEmpty(message) && (message.contains("资源"))){
                result = baiduDiskSearchService.SearchByKeyWords(message.replace("资源","").replace(" ",""));
            } else if(!StringUtils.isEmpty(message) && (message.equals("干货"))){
                result = gankeService.getToadyData();
            } else if(!StringUtils.isEmpty(message) && (message.contains("干货今日"))){
                result = gankeService.getTodayDataByCategory(message.replace("干货今日","").replace(" ",""));
            } else if(!StringUtils.isEmpty(message) && (message.contains("干货日报类型"))){
                result = gankeService.reportType();
            }else if(!StringUtils.isEmpty(message) && (message.contains("干货日报"))){
                result = gankeService.report(message.replace("干货日报","").replace(" ",""));
            }  else if(!StringUtils.isEmpty(message) && (message.contains("干货"))){
                result = gankeService.getDataByCategory(message.replace("干货","").replace(" ",""));
            }else if(!StringUtils.isEmpty(message) && (message.contains("v2ex"))){
                result = v2exService.hotTopics();
            } else if(!StringUtils.isEmpty(message) && (message.contains("leetcode"))){
                result = leetCodeService.randomProblem();
            } else if(!StringUtils.isEmpty(message) && (message.contains("情话"))){
                result = sayLoveService.getLoveRandom();
            }else if(!StringUtils.isEmpty(message) && (message.contains("menhera"))){
                result = picService.getRandomMenhera();
            }else {
                result = tulingService.getMsgByMsg(message);
            }
            SendMsgUtils.sendGroupMsg(group_id, result);
        }
    }
}
