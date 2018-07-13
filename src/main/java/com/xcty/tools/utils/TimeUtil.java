package com.xcty.tools.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/********************************
 * @Title TimeUtil
 * @package com.xcty.tools.utils
 * @Description:TODO
 *
 * @author XCTY
 * @date 2018/7/4 13:40
 * @version
 *********************************/
public class TimeUtil {
    public static String utc2Local(String utcTime) {
        String tmpUtcTime = StringUtils.replace(StringUtils.replace(utcTime, "Z", ""), "T", " ");
        SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormat.parse(tmpUtcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        localFormat.setTimeZone(TimeZone.getDefault());
        return localFormat.format(gpsUTCDate != null ? gpsUTCDate.getTime() : 0);
    }

    public static String local2Utc(String localTime) {
        SimpleDateFormat localFormat = new SimpleDateFormat(DatetimeUtil.DATE_TIME_PATTERN);
        localFormat.setTimeZone(TimeZone.getDefault());
        Date gpsLocalDate = null;
        try {
            gpsLocalDate = localFormat.parse(localTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        localFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timeStr = localFormater.format(gpsLocalDate != null ? gpsLocalDate.getTime() : 0);
        return StringUtils.replace(timeStr, " ", "T") + "Z";
    }
}
