package com.jack.qqrebot;

import com.jack.qqrebot.service.ReceiveServiceI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QqRebotApplicationTests {

	@Autowired
	private ReceiveServiceI receiveService;
	@Test
	public void contextLoads() {
		try {
			receiveService.phb(112,"排行榜|hb|+|dd");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
