package com.thom.hystrix.lang;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;

/**
 * Created by ThinkPad on 2016/11/5.
 */
public class Wechat {
    public static void main(String[] args) {
        String[] values = {"thomweixin", "1478347459", "1627171434"};
        Arrays.sort(values); // 字典序排序
        String value = values[0] + values[1] + values[2];
        String sign = DigestUtils.shaHex(value);
        if ("3eb181e493aa95855956e7c073667366ca6ea2ad".equals(sign)) {
            System.out.println(sign + "ok");
        }
        System.out.println(sign);
    }
}
