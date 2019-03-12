package com.jack.qqrebot.service;

import java.io.UnsupportedEncodingException;

public interface SendServiceI {
    public String sendTask(int groupId, String message);

    public String sendPoetry(int groupId, String message) throws UnsupportedEncodingException;

    public String sendNews(int groupId, String message);

    public String sendSatin(int groupId, String message);

    public String sendMeiTu(int groupId, String message) throws UnsupportedEncodingException;

    public String sendMusic(int groupId, String message) throws UnsupportedEncodingException;

    public String sendWeather(int groupId, String message) throws UnsupportedEncodingException;

    public String sendWeibo(int groupId, String message) throws UnsupportedEncodingException;

    public String sendMenu(int groupId, String message) throws UnsupportedEncodingException;

    public String sendLuck(int groupId, String message) throws UnsupportedEncodingException;

    public String tuling(int groupId, String message) throws UnsupportedEncodingException;
}
