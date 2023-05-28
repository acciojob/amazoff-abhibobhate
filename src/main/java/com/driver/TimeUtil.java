package com.driver;

public class TimeUtil {
    private TimeUtil(){}

    public static int convertToInt(String s){
        return Integer.parseInt(s.substring(0,2))*60+Integer.parseInt(s.substring(3,5));
    }
    public static String convertToString(Integer t){
        int hh = t%60;
        int mm = t/60;

        String h = hh+"";
        String m = mm+"";

        if(h.length()==1){
            h = "0"+h;
        }
        if(m.length()==1){
            m = "0"+m;
        }
        return h+":"+m;
    }
}
