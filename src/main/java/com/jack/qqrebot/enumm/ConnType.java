package com.jack.qqrebot.enumm;

/**
 * @Auther: mujj
 * @Date: 2019/5/11 00:24
 * @Description:
 * @Version: 1.0
 */
public enum  ConnType {
    IS_CAN(0),
    IS_WARN(1),
    IS_JINYAN(2),
    IS_HMD(5);

    int type;

    ConnType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
