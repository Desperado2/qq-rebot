package com.jack.qqrebot.service;

import java.io.UnsupportedEncodingException;

public interface SendServiceI {

    void dealGroupMsg(String message) throws UnsupportedEncodingException;

    void dealNotice(String noticeType);

    void clearCount();
}
