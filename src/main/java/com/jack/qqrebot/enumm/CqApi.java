package com.jack.qqrebot.enumm;

public enum CqApi {
    SEND_PRIVATE_MSG("send_private_msg"),
    SEND_GROUP_MSG("send_group_msg"),
    SEND_DISCUSS_MSG("send_discuss_msg"),
    SEND_MSG("send_msg"),
    DELETE_MSG("delete_msg"),
    SEND_LIKE("send_like"),
    SET_GROUP_KICK("set_group_kick"),
    SET_GROUP_BAN("set_group_ban"),
    SET_GROUP_ANONYMOUS_BAN("set_group_anonymous_ban"),
    SET_GROUP_WHOLE_BAN("set_group_whole_ban"),
    SET_GROUP_ADMIN("set_group_admin"),
    SET_GROUP_ANONYMOUS("set_group_anonymous"),
    SET_GROUP_CARD("set_group_card"),
    SET_GROUP_LEAVE("set_group_leave"),
    SET_GROUP_SPECIAL_TITLE("set_group_special_title"),
    SET_DISCUSS_LEAVE("set_discuss_leave"),
    SET_FRIEND_ADD_REQUEST("set_friend_add_request"),
    SET_GROUP_ADD_REQUEST("set_group_add_request"),
    GET_LOGIN_INFO("get_login_info"),
    GET_STRANGER_INFO("get_stranger_info"),
    GET_GROUP_LIST("get_group_list"),
    GET_GROUP_MEMBER_INFO("get_group_member_info"),
    GET_GROUP_MEMBER_LIST("get_group_member_list"),
    GET_COOKIES("get_cookies"),
    GET_CSRF_TOKEN("get_csrf_token"),
    GET_CREDENTIALS("get_credentials"),
    GET_RECORD("get_record"),
    GET_IMAGE("get_image"),
    CAN_SEND_IMAGE("can_send_image"),
    CAN_SEND_RECORD("can_send_record"),
    GET_STATUS("get_status"),
    GET_VERSION_INFO("get_version_info"),
    SET_RESTART("set_restart"),
    SET_RESTART_PLUGIN("set_restart_plugin"),
    CLEAN_DATA_DIR("clean_data_dir"),
    CLEAN_PLUGIN_LOG("clean_plugin_log"),
    _GET_FRIEND_LIST("_get_friend_list"),
    _GET_GROUP_INFO("_get_group_info"),
    _GET_VIP_INFO("_get_vip_info");

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
