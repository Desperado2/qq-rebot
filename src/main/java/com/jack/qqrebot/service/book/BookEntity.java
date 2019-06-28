package com.jack.qqrebot.service.book;

/**
 * @Auther: mujj
 * @Date: 2019/5/10 00:28
 * @Description:
 * @Version: 1.0
 */

public class BookEntity {
    private String link;
    private String title;

    public BookEntity(String link,String title){
        this.link = link;
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
