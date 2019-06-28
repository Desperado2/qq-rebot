package com.jack.qqrebot.utils;

import java.util.ResourceBundle;

public class ZHConverter {

    public enum target {
        TCcharacter,//繁体
        SCcharacter//简体
    }

    //    简体转换器
    private final static ZHConverter SimplifiedConverter = new ZHConverter();
    //繁体转换器
    private final static ZHConverter ComplexConverter = new ZHConverter();
    //对应表
    private ResourceBundle CorrespondingTable = null;

    //获取实例
    public static ZHConverter getExample(target converter) {
        if (converter.equals(target.TCcharacter)) {//繁体
            ComplexConverter.CorrespondingTable = ResourceBundle.getBundle("ComplexConverter");
            return ComplexConverter;
        } else {
            SimplifiedConverter.CorrespondingTable = ResourceBundle.getBundle("SimplifiedConverter");
            return SimplifiedConverter;
        }
    }

    private ZHConverter() {
    }

    /**
     * 不需自行创建转换器即可转换. 内部调用{@link #transformation(String) 转换}方法.
     *
     * @param content   任意长度
     * @param converter 目标格式
     * @return 转换为目标格式的文本
     * @throws IllegalArgumentException 文本为null时
     */
    public static String transformation(String content, target converter) throws IllegalAccessException {
        return getExample(converter).transformation(content);
    }


    public String transformation(String content) throws IllegalAccessException {
        if (content == null) {
            throw new IllegalAccessException("content is null");
        }

        StringBuilder str = new StringBuilder();
        if (content.length() > 1 && CorrespondingTable.containsKey(content)) {
            return CorrespondingTable.getString(content);
        }
        for (char c : content.toCharArray()) {
            String s = String.valueOf(c);
            //如果有多个对应字符，暂时用第一个，如果没有，则保留文字
            str.append(CorrespondingTable.containsKey(s) ? CorrespondingTable.getString(s).charAt(0) : s);
        }
        return str.toString();
    }
}
