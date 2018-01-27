package com.example.demo.utils;

import java.util.Date;

public class DateUtil {


    public static String TimeStamp2String(String timestampString) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(timestamp));
        return date;
    }


    public static Date TimeStamp2Date(Long timestampString) {
        Long timestamp = timestampString * 1000;
        Date date = new java.util.Date(timestamp);
        return date;
    }

}
