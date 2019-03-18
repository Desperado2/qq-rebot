package com.jack.qqrebot.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.springframework.util.StringUtils;

public class ChartGraphics {
    private static  BufferedImage image;


    //生成图片文件
    @SuppressWarnings("restriction")
    public static  void createImage(String fileLocation) {
        BufferedOutputStream bos = null;
        if(image != null){
            try {
                FileOutputStream fos = new FileOutputStream(fileLocation);
                bos = new BufferedOutputStream(fos);

                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
                encoder.encode(image);
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if(bos!=null){//关闭输出流
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void graphicsGeneration(JSONArray goods,JSONArray bads,String direction,String drink,String godds, int goods_H, int bads_H,int maxWidth) {

        int H_title= 30;
        int H_date = 40;     //日期高度
        int date_top = H_title;
        int goods_top = H_date+H_title;
        int bad_top = goods_top + goods_H;
        int other_top = bad_top+bads_H;

        int imageWidth = 320 > (maxWidth+120)?320:maxWidth+120;  //图片的宽度
        int imageHeight = H_title+H_date+goods_H+bads_H+100; //图片的高度

        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.setColor(Color.white);
        main.fillRect(0, 0, imageWidth, imageHeight);

        //***********************页面头部
        Graphics title = image.createGraphics();
        //设置区域颜色
        title.setColor(new Color(187,187, 187));
        //填充区域并确定区域大小位置
        title.fillRect(0, 0, imageWidth, H_title);
        //设置字体颜色，先设置颜色，再填充内容
        title.setColor(Color.white);
        //设置字体
        Font titleFont = new Font("Consolas Microsoft Yahei  Arial sans-serif", Font.BOLD, 18);
        title.setFont(titleFont);
        title.drawString("程序员老黄历", 0, H_title/2+6);

        titleFont = new Font("Consolas Microsoft Yahei  Arial sans-serif", Font.BOLD, 12);
        title.setFont(titleFont);
        title.drawString("beta", 17*6+15, H_title/2+2);

        //***********************页面头部
        Graphics date = image.createGraphics();
        //设置区域颜色
        date.setColor(new Color(255,255, 255));
        //填充区域并确定区域大小位置
        date.fillRect(0, date_top, imageWidth, H_date);
        //设置字体颜色，先设置颜色，再填充内容
        date.setColor(Color.BLACK);
        //设置字体
        Font dateFont = new Font("Consolas Microsoft Yahei  Arial sans-serif", Font.BOLD, 17);
        date.setFont(dateFont);
        date.drawString("今天是2019年3月18日 星期一", imageWidth/2-120, H_title+25);

        //***********************设置下面的提示框

        Graphics2D good = image.createGraphics();
        //设置区域颜色
        good.setColor(new Color(255, 238, 68));
        //填充区域并确定区域大小位置
        good.fillRect(0, goods_top, 97, goods_H);
        //设置字体颜色，先设置颜色，再填充内容
        good.setColor(Color.black);
        //设置字体
        Font tipFont = new Font("Consolas Microsoft Yahei  Arial sans-serif", Font.BOLD, 40);
        good.setFont(tipFont);
        good.drawString("宜",97/2-20, goods_top+(goods_H)/2+20);


        //***********************设置下面的按钮块
        Graphics2D goodItem = image.createGraphics();
        //设置区域颜色
        goodItem.setColor(new Color(255, 255, 170));
        //填充区域并确定区域大小位置
        goodItem.fillRect(97, goods_top, imageWidth-97, goods_H);
        //设置字体颜色，先设置颜色，再填充内容
        goodItem.setColor(Color.black);
        //设置字体
        Font itemFont = new Font("Consolas Microsoft Yahei  Arial sans-serif", Font.BOLD, 20);
        Font itemFont1 = new Font("Consolas Microsoft Yahei  Arial sans-serif", Font.PLAIN, 15);

        int y = goods_top+30;
        for (int i=0 ;i <goods.size(); i++){
            JSONObject object = goods.getJSONObject(i);
            String name = object.getString("name");
            if(!StringUtils.isEmpty(name)){
                goodItem.setFont(itemFont);
                goodItem.drawString(name,(imageWidth-97)/2, y);
                y += 20;
            }

            String gd = object.getString("good");
            if(!StringUtils.isEmpty(gd)){
                goodItem.setFont(itemFont1);
                goodItem.drawString(gd,(imageWidth-97)/2, y);
                y += 15;
            }
            y+=20;
        }

        //***********************设置下面的提示框

        Graphics2D bad = image.createGraphics();
        //设置区域颜色
        bad.setColor(new Color(255, 68, 68));
        //填充区域并确定区域大小位置
        bad.fillRect(0, bad_top, 97, bads_H);
        //设置字体颜色，先设置颜色，再填充内容
        bad.setColor(Color.WHITE);
        //设置字体
        Font badFont = new Font("Consolas Microsoft Yahei  Arial sans-serif", Font.BOLD, 40);
        bad.setFont(badFont);
        bad.drawString("不宜",97/2-40, bad_top+(bads_H/2+20));


        //***********************设置下面的按钮块
        Graphics2D badItem = image.createGraphics();
        //设置区域颜色
        badItem.setColor(new Color(255, 221, 211));
        //填充区域并确定区域大小位置
        badItem.fillRect(97, bad_top, imageWidth-97, bads_H);
        //设置字体颜色，先设置颜色，再填充内容
        badItem.setColor(Color.black);
        //设置字体
         y = bad_top+30;
        for (int i=0 ;i <bads.size(); i++){
            JSONObject object = bads.getJSONObject(i);
            String name = object.getString("name");
            if(!StringUtils.isEmpty(name)){
                badItem.setFont(itemFont);
                badItem.drawString(name,(imageWidth-97)/2, y);
                y += 20;
            }

            String gd = object.getString("bad");
            if(!StringUtils.isEmpty(gd)){
                badItem.setFont(itemFont1);
                badItem.drawString(gd,(imageWidth-97)/2, y);
                y += 15;
            }
            y+=20;
        }


        //***********************设置下面的按钮块
        Graphics2D otherItem = image.createGraphics();
        //设置区域颜色
        otherItem.setColor(new Color(255, 255, 170));
        //填充区域并确定区域大小位置
        otherItem.fillRect(0, bad_top+bads_H, imageWidth, 100);
        //设置字体颜色，先设置颜色，再填充内容
        otherItem.setColor(Color.black);
        //设置字体
        otherItem.setFont(itemFont1);
        otherItem.drawString(direction,10, other_top+30);
        otherItem.drawString(drink,10, other_top+60);
        otherItem.drawString(godds,10, other_top+90);

         SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
         String iday = sdf.format(new Date());

        createImage("C:\\CQPro\\data\\image\\"+iday+".jpg");
    }

}