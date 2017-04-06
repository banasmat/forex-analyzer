package com.jorm.forex.util;

import java.time.format.DateTimeFormatter;

public class Format {

    //public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static final String dateTimeFormatString = "dd-MM-yyyy HH:mm:ss";
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatString);
}
