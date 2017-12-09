package com.sv.gauge.controller;

import java.text.SimpleDateFormat;

/**
 * Created by mohan on 3/3/2016.
 */
public class AppEnvironmentValues {
    public static final String SERVER_ADDRESS
            = "http://www.la-gauge.supervisionglobal.com";
//            = "http://mohan-laptop:8080";
//            = "http://www.test.supervisionglobal.com";
    //

    public static int USER_ID = -1;
    public static String USER_NAME = "NONE";
    //
    public static final SimpleDateFormat SIMPLE_DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat SIMPLE_TIME_FORMAT = new SimpleDateFormat("HH:mm");

}
