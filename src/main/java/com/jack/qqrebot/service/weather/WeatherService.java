package com.jack.qqrebot.service.weather;

import java.io.UnsupportedEncodingException;

/**
 * @Auther: mujj
 * @Date: 2019/3/19 16:16
 * @Description:
 * @Version: 1.0
 */
public interface WeatherService {

    String getTodayWeather();

    String getWeatherByCity(String cityName) throws UnsupportedEncodingException;

    String getCurrWeatherByCity(String cityName) throws UnsupportedEncodingException;
}
