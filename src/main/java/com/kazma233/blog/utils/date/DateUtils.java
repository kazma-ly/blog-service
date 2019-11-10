package com.kazma233.blog.utils.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

    public static Date dateTimeToDateZH(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.of("Asia/Shanghai");
        return Date.from(localDateTime.atZone(zoneId).toInstant());
    }

}
