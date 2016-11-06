package com.thom.hystrix.common;

/**
 * Created by ThinkPad on 2016/11/5.
 */
public interface IWeiXinUrl {

    /**
     *
     */
    String access_token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    String menu_create = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";
}
