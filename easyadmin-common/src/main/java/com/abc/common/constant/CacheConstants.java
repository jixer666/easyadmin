package com.abc.common.constant;

public class CacheConstants {

    public static final String SYSTEM_NAME = "easyadmin:";

    public static final String LOGIN_TOKEN_KEY = "loginToken:%s";




    public static String getFinalKey(String key, Object ...values) {
        return String.format(SYSTEM_NAME + key, values);
    }





}
