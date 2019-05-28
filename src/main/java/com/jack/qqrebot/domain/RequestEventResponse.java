package com.jack.qqrebot.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @Auther: mujj
 * @Date: 2019/5/17 11:54
 * @Description:
 * @Version: 1.0
 */
public class RequestEventResponse {

    private boolean approve;
    private String reason;

    public boolean isApprove() {
        return approve;
    }

    public RequestEventResponse setApprove(boolean approve) {
        this.approve = approve;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public RequestEventResponse setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public JSONObject toJSON(){
        JSONObject object = new JSONObject();
        object.put("approve",isApprove());
        object.put("reason",getReason());
        return object;
    }
}
