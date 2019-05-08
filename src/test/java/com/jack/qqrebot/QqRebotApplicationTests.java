package com.jack.qqrebot;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jack.qqrebot.service.SendServiceI;
import com.jack.qqrebot.service.articles.ArticlesService;
import com.jack.qqrebot.service.baiduyundisk.BaiduDiskSearchService;
import com.jack.qqrebot.service.codercalendar.CodeCalendarService;
import com.jack.qqrebot.service.constellation.ConstellationService;
import com.jack.qqrebot.service.dailyenglish.DailyEnglishService;
import com.jack.qqrebot.service.dashang.DashangService;
import com.jack.qqrebot.service.emoticonpackage.EmoticonPackageService;
import com.jack.qqrebot.service.erciyuna.PicService;
import com.jack.qqrebot.service.gankService.GankeService;
import com.jack.qqrebot.service.historyontoday.HistoryOnTodayService;
import com.jack.qqrebot.service.leetcode.LeetCodeService;
import com.jack.qqrebot.service.meitu.MeituService;
import com.jack.qqrebot.service.news.NewsService;
import com.jack.qqrebot.service.notice.NoticeService;
import com.jack.qqrebot.service.ranking.RankingService;
import com.jack.qqrebot.service.satin.SatinService;
import com.jack.qqrebot.service.saylove.SayLoveService;
import com.jack.qqrebot.service.snh.SNHMembersService;
import com.jack.qqrebot.service.task.SchedualServiceI;
import com.jack.qqrebot.service.translation.TranslateService;
import com.jack.qqrebot.service.tuling.TulingService;
import com.jack.qqrebot.service.v2ex.V2exService;
import com.jack.qqrebot.service.weibo.WeiboService;
import com.jack.qqrebot.utils.HttpUtils;
import com.jack.qqrebot.utils.LongUrlToShortUrlUtils;
import com.jack.qqrebot.utils.SendMsgUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QqRebotApplicationTests {

	@Autowired
	private TulingService tulingService;
	@Test
	public void contextLoads() {
		String randomMember = null;
		try {
			randomMember = tulingService.getMsgByMsg("hello|语音");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println(randomMember);

    }


}
