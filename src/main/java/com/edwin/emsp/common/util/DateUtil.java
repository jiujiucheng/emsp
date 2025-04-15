package com.edwin.emsp.common.util;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: jiucheng
 * @Description: TODO
 * @Date: 2025/4/12
 */
@Slf4j
public class DateUtil {

    public static final int ONE_HOUR_SECOND = 60 * 60;

    public static final int ONE_DAY_SECOND = 60 * 60 * 24;

    public static final int ONE_HOUR_MILLISECOND = 60 * 60 * 1000;

    public static final int ONE_DAY_MILLISECOND = 24 * ONE_HOUR_MILLISECOND;

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final DateTimeFormatter YM_LINE_DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM");
    private static final DateTimeFormatter YM_DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyyMM");
    private static final DateTimeFormatter YMD_DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd");
    private static final DateTimeFormatter Y_M_D_DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");
    private static final DateTimeFormatter Y_M_D_DATE_TIME_FORMATTER2 = DateTimeFormat.forPattern("yyyy/MM/dd");
    private static final DateTimeFormatter Y_M_D_DATE_TIME_FORMATTER3 = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter Y_M_D_DATE_TIME_FORMATTER4 = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm");
    private static final DateTimeFormatter BRACKET_Y_M_D_DATE_TIME_FORMATTER = DateTimeFormat.forPattern("(yyyy/MM/dd)");
    private static final DateTimeFormatter FULL_DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter Y_M_D_H_M_DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter SIMPLE_DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    private static final DateTimeFormatter Y_M_D_H_M_FORMATTER = DateTimeFormat.forPattern("yyyyMMddHHmm");
    private static final DateTimeFormatter YMD_CHINESE_FORMATTER = DateTimeFormat.forPattern("yyyy年MM月dd日");
    private static final DateTimeFormatter HH_MM_FORMATTER = DateTimeFormat.forPattern("HH:mm");

    private static final DateTimeFormatter YMD_FORMATTER = DateTimeFormat.forPattern("yyyy.MM.dd");
    private static final DateTimeFormatter FULL_DATE_TIME_FORMATTER_WITH_MILL = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS");

    /**
     * getYearMonth(获取年月)
     *
     * @param
     * @param
     * @return
     */
    public static String getYearMonth(Date date) {
        return new DateTime(date).toString(YM_DATE_TIME_FORMATTER);
    }

    public static Integer getYearMonthInt(Date date) {
        return Integer.valueOf(new DateTime(date).toString(YM_DATE_TIME_FORMATTER));
    }

    //带毫秒的日期字符串
    public static String getFullDateWithMillStr(Date date) {
        if (date == null) {
            return "";
        }
        return new DateTime(date).toString(FULL_DATE_TIME_FORMATTER_WITH_MILL);
    }

    /**
     * 获取日期时间字符串
     *
     * @param date date
     * @return string like 'yyyy-MM-dd HH:mm:ss'
     */

    public static String getDateString(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(FULL_DATE_TIME_FORMATTER);
    }

    public static String getDateStringIgnoreNull(Date date) {
        if (date == null) {
            return "";
        }
        return new DateTime(date).toString(FULL_DATE_TIME_FORMATTER);
    }


    public static String getYMDHMS(Date date) {
        return new DateTime(date).toString(SIMPLE_DATE_TIME_FORMATTER);
//     return sdfYMDHMSSimple.format(date);
    }


    public static String getYMDByYMDHMS(String yyyyMMddHHmmss) {
        try {
            return (SIMPLE_DATE_TIME_FORMATTER.parseDateTime(yyyyMMddHHmmss)).toString(YMD_DATE_TIME_FORMATTER);
        } catch (Exception e) {
            return null;
        }
    }

    //输入输出没有任何变化……
    public static String getYMDHMSByYMDHMS(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(sdf.parse(date));
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static String getYMDByDate(Date date) {
        return new DateTime(date).toString(YMD_DATE_TIME_FORMATTER);
//     return sdfYMD.format(date);
    }

    public static String getYMDHMByDate(Date date) {
        return new DateTime(date).toString(Y_M_D_H_M_FORMATTER);
    }

    /**
     * getMonth(获得当前月)
     *
     * @param
     * @param
     * @return
     */
    public static int getMonth() {
        Calendar ca = Calendar.getInstance();
        return ca.get(Calendar.MONTH) + 1;
    }

    /**
     * getDay(获得当前日)
     *
     * @param
     * @param
     * @return
     */
    public static String getDay() {
        DateTime dt = new DateTime();
        return dt.dayOfMonth().getAsText();
    }

    /**
     * 得到某个日期上月的月份
     *
     * @return
     */
    public static String getLastMonth() {
        DateTime dt = new DateTime();
        return dt.minusMonths(1).toString("yyyyMM");
    }

    /**
     * yearMonth2DateTime(根据年月获取时间)
     *
     * @param
     * @param
     * @return
     */
    public static long yearMonth2DateTime(int yearMonth) {
        return yearMonth;
    }

    /**
     * 根据年月获取日期
     *
     * @return
     * @throws ParseException
     */
    public static Date getDate(String yearMonth) {
        try {
            return YM_DATE_TIME_FORMATTER.parseDateTime(yearMonth).toDate();
        } catch (IllegalArgumentException e) {
            return new Date();
        }
    }

    /**
     * 根据年月获取日期
     *
     * @return
     * @throws ParseException
     */
    public static Date getDateYM(String yearMonth) {
        try {
            return YM_LINE_DATE_TIME_FORMATTER.parseDateTime(yearMonth).toDate();
        } catch (IllegalArgumentException e) {
            return new Date();
        }
    }

    public static Date getYesterday() {
        return new DateTime().minusDays(1).toDate();
    }

    public static Date transferStringToDateWithNull(String str) {
        try {
            return FULL_DATE_TIME_FORMATTER.parseDateTime(str).toDate();
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public static Date transferStringToDate(String str) {
        try {
            return FULL_DATE_TIME_FORMATTER.parseDateTime(str).toDate();
        } catch (IllegalArgumentException e) {
            return new Date();
        }
    }

    /**
     * date为null时，返回null字符串
     *
     * @param pattern
     * @param date
     * @return
     */
    public static String getDateFormatWithNullByCustomization(String pattern, Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(pattern);
    }

    /**
     * date为null时，返回null字符串
     *
     * @param pattern
     * @param date
     * @return
     */
    public static String getDateFormat(String pattern, Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(pattern);
    }

    public static String getDateFormat(String pattern, String date) {
        if (date == null) {
            return null;
        }

        try {
            return new DateTime(new SimpleDateFormat("yyyyMMdd").parse(date)).toString(pattern);
        } catch (ParseException e) {
            logger.warn("解析日期格式异常，{}", e);
        }

        return null;
    }

    /**
     * date为null时，返回当前时间now()的个数化
     *
     * @param pattern
     * @param date
     * @return
     */
    public static String getDateStrByCustomization(String pattern, Date date) {
        return new DateTime(date).toString(pattern);
    }

    /**
     * 根据指定的字符串日期格式，转换成另一个格式
     */
    public static String getDateStrByCustomization(String sourceFormat, String destFormat) {
        try {
            return SIMPLE_DATE_TIME_FORMATTER.parseDateTime(sourceFormat).toString(destFormat);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 注意特殊的月份判断，当前时间是2016-5-31， month为4、add-month，那么时间是2016-9-30
     * add方法内部特别实现的，避免月数跳转
     *
     * @param month
     * @param date
     * @return
     */
    public static Date monthBeforeOrAfter(int month, Date date) {
        return new DateTime(date).plusMonths(month).toDate();
    }

    public static Date dayBeforeOrAfter(int day, Date date) {
        return new DateTime(date).plusDays(day).toDate();
    }

    public static Date yearBeforeOrAfter(int year, Date date) {
        return new DateTime(date).plusYears(year).toDate();
    }

    //    private static SimpleDateFormat sdfYMD_ = new SimpleDateFormat("yyyy-MM-dd");
    public static Date getDateByYMD(String ymd) {
        try {
            return Y_M_D_DATE_TIME_FORMATTER.parseDateTime(ymd).toDate();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 20160825格式
     *
     * @param ymd
     * @return
     */
    public static Date getDateByYMDWithout_(String ymd) {
        try {
            return YMD_DATE_TIME_FORMATTER.parseDateTime(ymd).toDate();
        } catch (Exception e) {
            return null;
        }
    }


    public static String getBracketYMDByDate(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(BRACKET_Y_M_D_DATE_TIME_FORMATTER);
    }

    public static String getSimpleYMDByDate(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(Y_M_D_DATE_TIME_FORMATTER);
    }

    public static String getSimpleYMDByDate2(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(Y_M_D_DATE_TIME_FORMATTER2);
    }

    public static String getSimpleYMDByDate3(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(YMD_FORMATTER);
    }

    public static String getSimpleTMDHMS(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(Y_M_D_DATE_TIME_FORMATTER3);
    }

    public static String getSimpleTMDHMS2(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(Y_M_D_DATE_TIME_FORMATTER4);
    }

    public static String getYMDHSDate(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(Y_M_D_H_M_DATE_FORMATTER);
    }

    public static String getSimpleChineseYMDByDate(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(YMD_CHINESE_FORMATTER);
    }

    public static String getTimeByDate(Date date) {
        if (date == null) {
            return null;
        }
        return new DateTime(date).toString(HH_MM_FORMATTER);
    }

    /**
     * 返回两个日期相差的天数
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static int getDistDates(Date startDate, Date endDate) {
        DateTime startTime = new DateTime(startDate);
        DateTime endTime = new DateTime(endDate);
        return Math.abs(Days.daysBetween(startTime, endTime).getDays());
    }

    /**
     * 对当前的时间增加x秒
     *
     * @param second
     * @return
     * @return Date
     * @Title: addSecondForNowDate
     */
    public static Date addSecondForNowDate(Long second) {
        return new DateTime().plusSeconds(second.intValue()).toDate();
    }

    /**
     * 获取后几天的日期 拼接上 00:00:00
     *
     * @param num
     * @return
     */
    public static Date getNextDayBegin(int num) {
        return new DateTime().plusDays(num).withTimeAtStartOfDay().toDate();
    }

    public static Date getPriorDay(int num) {
        return DateTime.now().minusDays(num).toDate();
    }

    /**
     * 获取前几天的日期 拼接上  00:00:00
     *
     * @param num
     * @return
     */
    public static Date getPriorDayBegin(int num) {
        return new DateTime().minusDays(num).withTimeAtStartOfDay().toDate();
    }

    /**
     * 获取前几天的日期 拼接上 23:59:59
     *
     * @param num
     * @return
     * @return Date
     * @Title: getPriorDayLastTime
     */
    public static Date getPriorDayEnd(int num) {
        return new DateTime().minusDays(num).millisOfDay().withMaximumValue().toDate();
    }

    /**
     * 当前年月日拼接上 23:59:59
     *
     * @param num
     * @param startTime
     * @return
     * @return Date
     * @Title: getPriorDayEndTime
     */
    public static Date getPriorDayEndTime(Date startTime, int num) {
        if (startTime == null) {
            return new DateTime().minusDays(num).millisOfDay().withMaximumValue().toDate();
        }
        return new DateTime(startTime).minusDays(num).millisOfDay().withMaximumValue().toDate();
    }

    public static Date getPriorDayEndTimeWithoutNull(Date startTime, int num) {
        if (startTime == null) {
            return null;
        }
        return new DateTime(startTime).minusDays(num).millisOfDay().withMaximumValue().toDate();
    }

    /**
     * 当前年月日拼接上 00:00:00
     *
     * @param num
     * @return
     */
    public static Date getNextDayBeginTime(Date startTime, int num) {
        if (startTime == null) {
            return new DateTime().plusDays(num).millisOfDay().withMinimumValue().toDate();
        }
        return new DateTime(startTime).plusDays(num).millisOfDay().withMinimumValue().toDate();

    }

    public static Date getNextDayBeginTimeWithoutNull(Date startTime, int num) {
        if (startTime == null) {
            return null;
        }
        return new DateTime(startTime).plusDays(num).millisOfDay().withMinimumValue().toDate();
    }

    /**
     * 返回两个日期相差的秒数
     *
     * @param startTime
     * @param endTime
     * @return
     * @return int
     * @Title: getDistSecond
     */
    public static int getDistSecond(Date startTime, Date endTime) {
        long num = (endTime.getTime() - startTime.getTime()) / (1000);
        return (int) num;
    }

    public static int getDistHour(Date startTime, Date endTime) {
        long num = (endTime.getTime() - startTime.getTime()) / (3600000);
        return (int) num;
    }


    /**
     * 返回上个月的第一天或者最后一天，加上时分秒
     *
     * @param isFirstDay true代表第一天 false代表最后一天
     * @return
     * @return Date
     * @Title: getLastMonthDay
     */
    public static Date getLastMonthDay(Boolean isFirstDay) {
        if (isFirstDay) {
            return new DateTime().minusMonths(1).dayOfMonth().withMinimumValue().withTimeAtStartOfDay().toDate();
        } else {
            return new DateTime().minusMonths(1).dayOfMonth().withMaximumValue().millisOfDay().withMaximumValue().toDate();
        }
    }

    public static Date getNextDay(int num) {
        return DateTime.now().plusDays(num).toDate();
    }

    /**
     * 获取后几天的日期
     *
     * @param num
     * @return
     */
    public static Date getNextDayDate(Date cDate, int num) {
        return new DateTime(cDate).plusDays(num).toDate();
    }

    public static Date getNextDayEnd(Date begin, int num) {
        return new DateTime(begin).plusDays(num).millisOfDay().withMaximumValue().toDate();
    }

    public static Date getNextDayBegin(Date begin, int num) {
        return new DateTime(begin).plusDays(num).millisOfDay().withMinimumValue().toDate();
    }

    public static Date getMonthFirstDate() {
        return new DateTime().dayOfMonth().withMinimumValue().toDate();
    }

    public static boolean isSameDay(Date date, Date date2) {
        String str = getSimpleYMDByDate(date);
        String str2 = getSimpleYMDByDate(date2);
        return Objects.equals(str, str2);
    }

    /**
     * 获取最早的日期
     *
     * @param dates
     * @return
     */
    public static Date getEarlyDate(Date... dates) {
        Date early = null;
        for (Date date : dates) {
            if (date == null) {
                continue;
            }
            if (early == null) {
                early = date;
                continue;
            }
            if (early.after(date)) {
                early = date;
            }
        }
        return early;
    }

    /**
     * 获取固定日期某小时后的日期
     *
     * @param date      计算日期
     * @param plusHours 增加小时数
     * @return date
     */
    public static Date getPlusHourDate(Date date, int plusHours) {
        return new DateTime(date).plusHours(plusHours).toDate();
    }

    /**
     * 获取格式化时间
     *
     * @param start 开始日期
     * @param end   结束日期
     * @return string
     */
    public static String getTimeStr(Date start, Date end) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        String startTime = getHourMin(cal);

        cal.setTime(end);
        String entTime = getHourMin(cal);

        if (Objects.equals(startTime, "08:00") && Objects.equals(entTime, "21:00")) {
            return startTime + "-" + entTime + "(全天)";
        } else {
            return startTime + "-" + entTime;
        }
    }

    private static String getHourMin(Calendar cal) {
        return (cal.get(Calendar.HOUR_OF_DAY) > 9 ? cal.get(Calendar.HOUR_OF_DAY) + "" : "0" + cal.get(Calendar.HOUR_OF_DAY))
                + ":"
                + (cal.get(Calendar.MINUTE) > 9 ? cal.get(Calendar.MINUTE) + "" : "0" + cal.get(Calendar.MINUTE));
    }

    /**
     * 获取前几分钟
     *
     * @param num
     * @return Date
     * @Title: getPriorMinute
     */
    public static Date getPriorMinute(int num) {
        return new DateTime().minusMinutes(num).toDate();
    }

    public static String getDiffDateStr(Date d1, Date d2) {
        long l = d1.getTime() - d2.getTime();
        if (l < 0) {
            l = l * (-1);
        }
        long hour = l / (ONE_HOUR_MILLISECOND);
        long min = ((l / (60 * 1000)) - hour * 60);
        return new StringBuffer().append(hour).append("小时").append(min).append("分").toString();
    }

    /**
     * 获取与当前时间差几小时
     * 剩余24小时20分钟 显示剩余24小时20分
     * 剩余2小时20分钟 显示2小时20分；
     * 剩余20分钟 显示20分
     * 剩余-90分钟的时候 显示0小时0分；
     *
     * @param date
     * @return
     */
    public static String getRemindTime(Date date) {
        Instant now = Instant.now();
        Instant endTime = date.toInstant();
        long remainingSecond = Duration.between(now, endTime).getSeconds();
        if (remainingSecond <= 0) {
            return "0小时0分";
        }
        int remainingHour = (int) (Duration.between(now, endTime).getSeconds() / 3600);
        int remainingMin = (int) ((Duration.between(now, endTime).getSeconds() % 3600) / 60);
        if (remainingHour > 0) {
            StringBuffer sb = new StringBuffer();
            sb.append(remainingHour).append("小时").append(remainingMin).append("分");
            return sb.toString();
        }
        return "0小时" + remainingMin + "分";
    }

    public static String howLongAgo(Date date) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        long years = localDateTime.until(LocalDateTime.now(), ChronoUnit.YEARS);
        long months = localDateTime.until(LocalDateTime.now(), ChronoUnit.MONTHS);
        long days = localDateTime.until(LocalDateTime.now(), ChronoUnit.DAYS);
        if (years > 0) {
            return years + "年前";
        } else if (months > 0) {
            return months + "个月前";
        } else if (days > 0) {
            return days + "天前";
        } else {
            return "今天";
        }
    }
}

