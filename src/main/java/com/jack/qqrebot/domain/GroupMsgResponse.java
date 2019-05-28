package com.jack.qqrebot.domain;

import com.alibaba.fastjson.JSONObject;

/**
 * @Auther: mujj
 * @Date: 2019/5/17 11:28
 * @Description:
 * @Version: 1.0
 */
public class GroupMsgResponse {

    private String message;
    private boolean auto_escape = false;
    private boolean at_sender = true;
    private boolean delete = false;
    private boolean kick = false;
    private boolean ban = false;
    private int ban_duration = 0;

    public String getMessage() {
        return message;
    }

    public GroupMsgResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public boolean isAuto_escape() {
        return auto_escape;
    }

    public GroupMsgResponse setAuto_escape(boolean auto_escape) {
        this.auto_escape = auto_escape;
        return this;
    }

    public boolean isAt_sender() {
        return at_sender;
    }

    public GroupMsgResponse setAt_sender(boolean at_sender) {
        this.at_sender = at_sender;
        return this;
    }

    public boolean isDelete() {
        return delete;
    }

    public GroupMsgResponse setDelete(boolean delete) {
        this.delete = delete;
        return this;
    }

    public boolean isKick() {
        return kick;
    }

    public GroupMsgResponse setKick(boolean kick) {
        this.kick = kick;
        return this;
    }

    public boolean isBan() {
        return ban;
    }

    public GroupMsgResponse setBan(boolean ban) {
        this.ban = ban;
        return this;
    }

    public int getBan_duration() {
        return ban_duration;
    }

    public GroupMsgResponse setBan_duration(int ban_duration) {
        this.ban_duration = ban_duration;
        return this;
    }

    public JSONObject toJSON(){
        JSONObject object = new JSONObject();
        object.put("message",getMessage());
        object.put("auto_escape",isAuto_escape());
        object.put("at_sender",isAt_sender());
        object.put("delete",isDelete());
        object.put("kick",isKick());
        object.put("ban",isBan());
        object.put("ban_duration",getBan_duration());
        return object;
    }
}
