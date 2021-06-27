package cn.hjf.lzsdq.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    public String getNow() {
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        return sdf.format(date);
    }

    public int betweenDays(Date old, Date now) {
        int days = (int) ((now.getTime() - old.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    public int betweenDaysToNow(Date date) {
        return betweenDays(date, new Date());
    }
}
