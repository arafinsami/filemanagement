package com.filemanagement.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;

public class DateUtils {

    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy hh:mm a";

    public static final Map<String, String> TIME_PATTERN_FORMAT_MAP;

    static {
        TIME_PATTERN_FORMAT_MAP = new HashMap<>();
        TIME_PATTERN_FORMAT_MAP.put("^(1[0-2]):[0-5][0-9](\\s)?(am|AM|pm|PM)$", "hh:mm a");
        TIME_PATTERN_FORMAT_MAP.put("^(0[0-9]):[0-5][0-9](\\s)?(am|AM|pm|PM)$", "hh:mm a");
        TIME_PATTERN_FORMAT_MAP.put("^([0-9]):[0-5][0-9](\\s)?(am|AM|pm|PM)$", "h:mm a");
    }

    public static String format(Date date, String format) {
        return isNull(date) ? null : new SimpleDateFormat(format).format(date);
    }

}