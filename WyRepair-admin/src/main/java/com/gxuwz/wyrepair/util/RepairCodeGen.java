package com.gxuwz.wyrepair.util;

import com.gxuwz.wyrepair.common.utils.DateUtils;

import java.time.Instant;

public class RepairCodeGen {


    public static String genApplyNo() {
        return "GXUA" + DateUtils.dateTimeNow() + "MD" + Instant.now().toEpochMilli();
    }

    public static String genRepairNo() {
        return "GXUR" + Instant.now().toEpochMilli() + "UN" + DateUtils.dateTimeNow();
    }

}
