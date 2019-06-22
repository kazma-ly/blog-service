package com.kazma233.blog.middleware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

/**
 * to Date
 * Created by mac_zly on 2017/3/10.
 *
 * @author kazma
 */
@Component
public class StringToDateConverter implements Converter<String, Date> {

    private static final DateTimeFormatter EASY_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter SIMPLE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter COMPLEXE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSz");

    private static final Logger LOGGER = LoggerFactory.getLogger(StringToDateConverter.class);

    @Override
    public Date convert(String source) {
        try {
            long millis = Long.parseLong(source);
            return new Date(millis);
        } catch (NumberFormatException ignore) {
        }
        try {
            LocalDate localDate = LocalDate.parse(source, EASY_DATE_FORMATTER);
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }catch (DateTimeParseException ignore) {
        }

        Date parseDate = getDate(source, SIMPLE_DATE_FORMATTER);
        if (parseDate != null)
            return parseDate;
        parseDate = getDate(source, COMPLEXE_DATE_FORMATTER);
        return parseDate;
    }

    private Date getDate(String source, DateTimeFormatter easyDateFormatter) {
        try {
            LocalDateTime parseDate = LocalDateTime.parse(source, easyDateFormatter);
            return Date.from(parseDate.atZone(ZoneId.systemDefault()).toInstant());
        } catch (DateTimeParseException e) {
            LOGGER.error("时间格式化错误，没有符合的格式: " + e);
        }
        return null;
    }
}
