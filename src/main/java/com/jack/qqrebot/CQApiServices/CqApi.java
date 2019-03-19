package com.jack.qqrebot.CQApiServices;

public enum CqApi {
    SEND_PRIVATE_MSG("send_private_msg"),
    SEND_GROUP_MSG("send_group_msg");
    /*SEND_DISCUSS_MSG,
    SEND_MSG,
    DELETE_MSG,
    SEND_LIKE,
    SET_GROUP_KICK,
    SET_GROUP_BAN,
    SET_GROUP_ANONYMOUS_BAN,
    SET_GROUP_WHOLE_BAN,
    SET_GROUP_ADMIN,
    SET_GROUP_ANONYMOUS,
    SET_GROUP_CARD,
    SET_GROUP_LEAVE,
    SET_GROUP_SPECIAL_TITLE,
    SET_DISCUSS_LEAVE,
    SET_FRIEND_ADD_REQUEST,
    SET_GROUP_ADD_REQUEST,
    GET_LOGIN_INFO,
    GET_STRANGER_INFO,
    GET_GROUP_LIST,
    GET_GROUP_MEMBER_INFO,
    GET_GROUP_MEMBER_LIST,
    GET_COOKIES,
    GET_CSRF_TOKEN,
    GET_CREDENTIALS,
    GET_RECORD,
    GET_IMAGE,
    CAN_SEND_IMAGE,
    CAN_SEND_RECORD,
    GET_STATUS,
    GET_VERSION_INFO,
    SET_RESTART,
    SET_RESTART_PLUGIN,
    CLEAN_DATA_DIR,
    CLEAN_PLUGIN_LOG,
    _GET_FRIEND_LIST,
    _GET_GROUP_INFO,
    _GET_VIP_INFO;*/

    private  String name;

    CqApi(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
