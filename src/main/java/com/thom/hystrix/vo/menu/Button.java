package com.thom.hystrix.vo.menu;

/**
 * Created by ThinkPad on 2016/11/6.
 */
public class Button {

    private String name;//所有一级菜单、二级菜单都共有一个相同的属性，那就是name

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
