package com.sjh.news.StaicInfo;

/**
 * Created by 25235 on 2018/7/11.
 */

public class HttpInfo {
    final public static String KEY = "2bb70d514061c77d14a8cdfdf8c35259";
    public static Integer NUM = 50;

    public static String URL_RECOMMEND = "";
    public static String URL_TECHNOLOGY = "http://api.tianapi.com/keji/?key=" + KEY + "&num=" + NUM + "&rand=1";
    public static String URL_FUN = "http://api.tianapi.com/huabian/?key=" + KEY + "&num=" + NUM + "&rand=1";
    public static String URL_MILITARY = "http://api.tianapi.com/military/?key=" + KEY + "&num=" + NUM + "&rand=1";
    public static String URL_IT = "http://api.tianapi.com/it/?key=" + KEY + "&num=" + NUM + "&rand=1";
    public static String URL_FOOTBALL = "http://api.tianapi.com/football/?key=" + KEY + "&num=" + NUM + "&rand=1";
    public static String URL_NBA = "http://api.tianapi.com/nba/?key=" + KEY + "&num=" + NUM + "&rand=1";

    public static Boolean getNewData = false;
    public static Integer code = 0;
    public static String msg = "";
}
