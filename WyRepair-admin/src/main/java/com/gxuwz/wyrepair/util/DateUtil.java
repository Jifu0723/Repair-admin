package com.gxuwz.wyrepair.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {


    public static LocalDateTime parseToLocaDateTime(String date,String dformat){
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(dformat));
    }

}
