package org.cmu.rmcs.util;

public class ContantUtil {

    public static final int CMD_LED=0;
    public static final int WS_CMD_ADD_GROUP=0;
    public static final int WS_CMD_DELETE_GROUP=1;
    public static final int WS_CMD_STATE_GROUP=2;
    public static final int ANAY_GROUP_INCRMENT=3;
    public static final int ANAY_GROUP_NOCHANGE=4;
    public static final int ANAY_GROUP_DEC=5;
    public static final int SIGN_PACKAGE_GROUP_ADD=6;
    public static final int SIGN_PACKAGE_GROUP_DEC=7;
    public static final int SIGN_PACKAGE_GROUP_STATE=8;
    public static final int GROUP_MAP_PUT=9;
    public static final int GROUP_MAP_DELETE=10;
    public static final long THREAD_SLEEP_TIME=700; 
    public static final String GROUP_SET_KEY="group"; 
    public static final String FAMILY_SET_KEY="family"; 
    public static final String POSTFIX_GROUP_FEEDBACK_KEY="_gfd";
    public static final String POSTFIX_GROUP_KEY="_g";
    public static final int LEN_TYPE_LIST=11;
    public static final int LEN_TYPE_SET=12;
    public static final int SET_UPDATE_ADD=13;
    public static final int SET_UPDATE_DELETE=14;
    public static final int MAX_FEEDBACK_LENTH=200;
    public static final String POSTFIX_FIXED_GROUP_KEY="_fix";
    public static final String MODULE_START_TIME="start";
    public static final String MODULE_INFIX="_";
    public static final String TIME_STR="%dd %dhr %dmin %dsec";
    public static String formatTime(long ms) {  
        
        int ss = 1000;  
        int mi = ss * 60;  
        int hh = mi * 60;  
        int dd = hh * 24;  

        long day = ms / dd;  
        long hour = (ms - day * dd) / hh;  
        long minute = (ms - day * dd - hour * hh) / mi;  
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;  
        String timeString=new String(ContantUtil.TIME_STR);
        return String.format(timeString, day,hour,minute,second);  
}  
}
