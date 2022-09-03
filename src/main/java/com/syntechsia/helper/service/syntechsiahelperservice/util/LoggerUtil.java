package com.syntechsia.helper.service.syntechsiahelperservice.util;

import org.slf4j.MDC;

import java.util.Date;

public class LoggerUtil {

    private LoggerUtil() {
    }

    private static final String TRACKING_REF = "trackingRef";

    public static void removeUniqueId() {
        MDC.remove(TRACKING_REF);
    }

    public static void setUniqueIdByDate() {
        MDC.put(TRACKING_REF, DateUtil.dateToString(new Date(), "yyyyMMddHHmmssSSS"));
    }

}
