package zoowebapp.service.utils;

import java.util.Calendar;
import java.util.Date;

public interface DateUtils {

    static Date plusOneDay(Date date){
        if (date != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(date.getTime());
            calendar.add(Calendar.DATE, 1);
            date = calendar.getTime();
        }
        return date;
    }

    static Date convertStringToDate(String date) {
        if (date.equals("")) {
            return null;
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.parseInt(date.substring(0, date.indexOf("-"))),
                    Integer.parseInt(date.substring(date.indexOf("-") + 1, date.lastIndexOf("-"))) - 1,
                    Integer.parseInt(date.substring(date.lastIndexOf("-") + 1)),
                    0,0,0);
            return calendar.getTime();
        }
    }
}
