package com.thom.hystrix.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ThinkPad on 2016/11/5.
 */
@JsonIgnoreProperties
public class MenuCreate {
    private String errcode;

    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
