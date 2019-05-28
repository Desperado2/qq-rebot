package com.jack.qqrebot.enumm;

/**
 * @Auther: mujj
 * @Date: 2019/5/17 11:34
 * @Description:
 * @Version: 1.0
 */
public enum  EventType {
    GROUP_MSG(0),
    REQUEST_MSG(1);


    private int type;

    EventType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
