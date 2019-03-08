package br.com.londrisoft.uniqueweb.service.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static Date getNextDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, 1);
        return c.getTime();
    }
}
