package com.jack.qqrebot.service.poetry;

import java.io.UnsupportedEncodingException;

public interface PoetryService {

    String getPoetryByKeyWord(String keyWord) throws UnsupportedEncodingException;
}
