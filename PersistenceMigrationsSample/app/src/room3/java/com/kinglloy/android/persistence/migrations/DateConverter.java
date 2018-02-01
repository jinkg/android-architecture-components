package com.kinglloy.android.persistence.migrations;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * @author jinyalin
 * @since 2018/2/1.
 */

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
