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
}
