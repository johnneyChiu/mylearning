package personal.xiaoq.utils;



import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;



/**
 * @author Johnney.Chiu
 * create on 2018/4/24 18:14
 * @Description
 * @title
 */
public class DateTimeUtils {


    // 默认时间格式
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = TimeFormat.SHORT_DATE_PATTERN_LINE.formatter;
    private static final String YEAR_STR_FORMAT = "%s-01-01 00:00:00";
    private static final String MONTH_STR_FORMAT = "%s-01 00:00:00";
    private static final String DAY_STR_FORMAT = "%s 00:00:00";
    private static final String HOUR_STR_FORMAT = "%s:00:00";

    private DateTimeUtils() {};
    /**
     * String 转化为 LocalDate
     *
     * @param timeStr
     *            被转化的字符串
     * @return LocalDate
     */
    public static LocalDate parseDate(String timeStr) {
       return  LocalDate.parse(timeStr, DEFAULT_DATETIME_FORMATTER);

    }
    /**
     * String 转化为 LocalDate
     *
     * @param timeStr
     *            被转化的字符串
     * @return LocalDate
     */
    public static LocalDate parseDate(String timeStr, TimeFormat timeFormat) {
        return  LocalDate.parse(timeStr, timeFormat.formatter);

    }


    /**
     * String 转化为 LocalDateTime
     *
     * @param timeStr
     *            被转化的字符串
     * @param timeFormat
     *            转化的时间格式
     * @return LocalDateTime
     */
    public static LocalDateTime parseTime(String timeStr, TimeFormat timeFormat) {
        return LocalDateTime.parse(timeStr, timeFormat.formatter);

    }

    /**
     * LocalDateTime 转化为String
     *
     * @param time
     *            LocalDateTime
     * @return String
     */
    public static String parseTime(LocalDateTime time) {
        return DEFAULT_DATETIME_FORMATTER.format(time);

    }


    //获取指定日期的毫秒
    public static Long getMilliByTime(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    //获取指定日期的毫秒
    public static Long getMilliByDate(LocalDate date) {
        LocalDateTime localDateTime = LocalDateTime.of(date,LocalTime.of(0,0,0));

        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * LocalDateTime 时间转 String
     *
     * @param time
     *            LocalDateTime
     * @param format
     *            时间格式
     * @return String
     */
    public static String parseTime(LocalDateTime time, TimeFormat format) {
        return format.formatter.format(time);

    }

    /**
     * LocalDate 时间转 String
     *
     * @param time
     *            LocalDate
     * @param format
     *            时间格式
     * @return String
     */
    public static String parseDate(LocalDate time, TimeFormat format) {
        return format.formatter.format(time);

    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentDateDateAndTime() {
        return  TimeFormat.LONG_DATE_PATTERN_LINE.formatter.format(LocalDateTime.now());
    }


    /**
     * 获取当前时间
     *
     * @param timeFormat
     *            时间格式
     * @return
     */
    public static String getCurrentDateTime(TimeFormat timeFormat) {
        return timeFormat.formatter.format(LocalDateTime.now());
    }
    public static String geLocalDateTimeStr(LocalDateTime localDateTime,TimeFormat timeFormat) {
        return timeFormat.formatter.format(localDateTime);
    }


    public static Date convertDateFromStr(String str,TimeFormat timeFormat){
        LocalDateTime ldt = LocalDateTime.parse(str,timeFormat.formatter);
        return convertLDTToDate(ldt);

    }

    public static Date convertLDTToDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static String parseTime(Date date,TimeFormat timeFormat){
        return timeFormat.simpleDateFormat.format(date);
    }

    public static LocalDateTime convertDateoLDTT(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDateTime getFirstOfDateBymillSeconds(Date date){
        return getSpecialOfDateBymillSeconds(date, "00:00:00.000");
    }
    public static LocalDateTime getLastOfDateBymillSeconds(Date date){
        return getSpecialOfDateBymillSeconds(date, "23:59:59.999");

    }

    public static LocalDateTime getSpecialOfDateBymillSeconds(Date date,String time){
        LocalDate ld = parseDate(TimeFormat.SHORT_DATE_PATTERN_LINE.simpleDateFormat.format(date));
        LocalTime lt = LocalTime.parse(time, TimeFormat.SHORT_TIME_PATTERN_NONE.formatter);
        return LocalDateTime.of(ld, lt);
    }

    public static long getAddTime(long timeStamp,CstConstants.TIME_SELECT timeSelect) {
        LocalDateTime localDateTime=convertDateoLDTT(new Date(timeStamp));
        LocalDateTime localDateTimeResult = LocalDateTime.now();
        switch (timeSelect) {
            case YEAR:
                localDateTimeResult=localDateTime.plusYears(1);
                break;
            case DAY:
                localDateTimeResult=localDateTime.plusDays(1);
                break;
            case MONTH:
                localDateTimeResult = localDateTime.plusMonths(1);
                break;
            case HOUR:
                localDateTimeResult = localDateTime.plusHours(1);
                break;
        }
        return getTimestampOfDateTime(localDateTimeResult);
    }
    public static long getMinusTime(long timeStamp,CstConstants.TIME_SELECT timeSelect) {
        LocalDateTime localDateTime=convertDateoLDTT(new Date(timeStamp));
        LocalDateTime localDateTimeResult = LocalDateTime.now();
        switch (timeSelect) {
            case YEAR:
                localDateTimeResult=localDateTime.minusYears(1);
                break;
            case DAY:
                localDateTimeResult=localDateTime.minusDays(1);
                break;
            case MONTH:
                localDateTimeResult = localDateTime.minusMonths(1);
                break;
            case HOUR:
                localDateTimeResult = localDateTime.minusHours(1);
                break;
        }
        return getTimestampOfDateTime(localDateTimeResult);
    }

    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }
    public static LocalDateTime getSpecialOfDateBymillSeconds(Date date,String time,TimeFormat timeFormat){
        LocalDate ld = parseDate(TimeFormat.SHORT_DATE_PATTERN_LINE.simpleDateFormat.format(date));
        LocalTime lt = LocalTime.parse(time, timeFormat.formatter);
        return LocalDateTime.of(ld, lt);
    }

    public static List<String> getBetweenDateStr(long lessTime,long moreTime,CstConstants.TIME_SELECT timeSelect){
        ZoneId zone = ZoneId.systemDefault();
        List<String> dateStrs = new LinkedList<>();
        if(lessTime>=moreTime)
            return dateStrs;
        Instant instantLess = Instant.ofEpochMilli(lessTime);
        Instant instantMore = Instant.ofEpochMilli(moreTime);
        LocalDateTime localDateTimeLess=LocalDateTime.ofInstant(instantLess, zone);
        LocalDateTime localDateTimeMore=LocalDateTime.ofInstant(instantMore, zone);


        String time;
        boolean flag=false;
        String yearMore=geLocalDateTimeStr(localDateTimeMore, TimeFormat.SHORT_YEAR_PATTERN);
        String monthMore=geLocalDateTimeStr(localDateTimeMore, TimeFormat.SHORT_MONTH_PATTERN);
        String dayMore=geLocalDateTimeStr(localDateTimeMore, TimeFormat.SHORT_DATE_PATTERN_LINE);
        String hourMore=geLocalDateTimeStr(localDateTimeMore, TimeFormat.SHORT_DATE_HOUR_PATTERN_LINE);
        while (true) {
            switch (timeSelect){
                case YEAR:{
                    localDateTimeLess=localDateTimeLess.plusYears(1);
                    time=geLocalDateTimeStr(localDateTimeLess, TimeFormat.SHORT_YEAR_PATTERN);
                    if (time.equals(yearMore)||localDateTimeLess.isAfter(localDateTimeMore)) {
                        flag = true;
                    }else{
                        time=String.format(YEAR_STR_FORMAT, time);
                    }

                }
                break;
                case MONTH:{
                    localDateTimeLess=localDateTimeLess.plusMonths(1);
                    time=geLocalDateTimeStr(localDateTimeLess, TimeFormat.SHORT_MONTH_PATTERN);
                    if (time.equals(monthMore)||localDateTimeLess.isAfter(localDateTimeMore)) {
                        flag = true;
                    }else{
                        time=String.format(MONTH_STR_FORMAT, time);
                    }
                }
                break;
                case DAY:{
                    localDateTimeLess=localDateTimeLess.plusDays(1);
                    time=geLocalDateTimeStr(localDateTimeLess, TimeFormat.SHORT_DATE_PATTERN_LINE);
                    if (time.equals(dayMore)||localDateTimeLess.isAfter(localDateTimeMore)) {
                        flag = true;
                    }else{
                        time=String.format(DAY_STR_FORMAT, time);
                    }
                }
                break;
                case HOUR:{
                    localDateTimeLess=localDateTimeLess.plusHours(1);
                    time=geLocalDateTimeStr(localDateTimeLess, TimeFormat.SHORT_DATE_HOUR_PATTERN_LINE);
                    if (time.equals(hourMore)||localDateTimeLess.isAfter(localDateTimeMore)) {
                        flag = true;
                    }else{
                        time=String.format(HOUR_STR_FORMAT, time);
                    }
                }
                break;
                default:
                {
                    localDateTimeLess=localDateTimeLess.plusHours(1);
                    time=geLocalDateTimeStr(localDateTimeLess, TimeFormat.SHORT_DATE_HOUR_PATTERN_LINE);
                    if (time.equals(hourMore)||localDateTimeLess.isAfter(localDateTimeMore)) {
                        flag = true;
                    }else{
                        time=String.format(HOUR_STR_FORMAT, time);
                    }
                }
            }
            if(flag)
                break;
            dateStrs.add(time);

        }
        return dateStrs;
    }

    public static List<Long> getBetweenTimestamp(long lessTime,long moreTime,CstConstants.TIME_SELECT timeSelect){
        ZoneId zone = ZoneId.systemDefault();
        List<Long> list=new ArrayList<>();
        if(lessTime>=moreTime)
            return list;

        Instant instantLess = Instant.ofEpochMilli(lessTime);
        Instant instantMore = Instant.ofEpochMilli(moreTime);
        LocalDateTime localDateTimeLess=LocalDateTime.ofInstant(instantLess, zone);
        LocalDateTime localDateTimeMore=LocalDateTime.ofInstant(instantMore, zone);


        long time;
        boolean flag=false;

        while (true) {
            switch (timeSelect){
                case YEAR:{
                    localDateTimeLess=localDateTimeLess.plusYears(1);
                    time=getTimestampOfDateTime(localDateTimeLess);
                    if (localDateTimeLess.isAfter(localDateTimeMore)) {
                        flag = true;
                    }

                }
                break;
                case MONTH:{
                    localDateTimeLess=localDateTimeLess.plusMonths(1);
                    time=getTimestampOfDateTime(localDateTimeLess);
                    if (localDateTimeLess.isAfter(localDateTimeMore)) {
                        flag = true;
                    }
                }
                break;
                case DAY:{
                    localDateTimeLess=localDateTimeLess.plusDays(1);
                    time=getTimestampOfDateTime(localDateTimeLess);
                    if (localDateTimeLess.isAfter(localDateTimeMore)) {
                        flag = true;
                    }
                }
                break;
                case HOUR:{
                    localDateTimeLess=localDateTimeLess.plusHours(1);
                    time=getTimestampOfDateTime(localDateTimeLess);
                    if (localDateTimeLess.isAfter(localDateTimeMore)) {
                        flag = true;
                    }
                }
                break;
                default:
                {
                    localDateTimeLess=localDateTimeLess.plusHours(1);
                    time=getTimestampOfDateTime(localDateTimeLess);
                    if (localDateTimeLess.isAfter(localDateTimeMore)) {
                        flag = true;
                    }
                }
            }
            if(flag)
                break;
            list.add(time);

        }
        return list;
    }

    /**
     * 计算两个时间片相差多少天
     * @param oldDateStr  老的时间片
     * @param newDateStr  新的时间片
     * @return
     */
    public static Long numOfDaysApart(String oldDateStr,String newDateStr){
        LocalDate oldDate = DateTimeUtils.parseDate(oldDateStr,DateTimeUtils.TimeFormat.SHORT_DATE_PATTERN_LINE);
        LocalDate newDate = DateTimeUtils.parseDate(newDateStr,DateTimeUtils.TimeFormat.SHORT_DATE_PATTERN_LINE);
        Long numOfDaysApart = newDate.toEpochDay() - oldDate.toEpochDay();
        return numOfDaysApart;
    }

    /**
     * 对时间字符串 进行天的加计算
     * @param dateStr  时间
     * @param n
     * @return
     */
    public static String addNumOfDays(String dateStr, int n){
        LocalDate date = DateTimeUtils.parseDate(dateStr,DateTimeUtils.TimeFormat.SHORT_DATE_PATTERN_LINE);
        LocalDate result =   date.plusDays(n);
        String s = DateTimeUtils.parseDate(result,DateTimeUtils.TimeFormat.SHORT_DATE_PATTERN_LINE);
        return s;
    }

    /**
     * 对时间字符串 进行天的减计算
     * @param dateStr  时间
     * @param n
     * @return
     */
    public static String subNumOfDays(String dateStr,int n){
        LocalDate date = DateTimeUtils.parseDate(dateStr,DateTimeUtils.TimeFormat.SHORT_DATE_PATTERN_LINE);
        LocalDate result =   date.minusDays(n);
        String s = DateTimeUtils.parseDate(result,DateTimeUtils.TimeFormat.SHORT_DATE_PATTERN_LINE);
        return s;
    }
    /**
     * 获取本周的第几天
     * @param localDate
     * @param n
     * @return
     */
    public static LocalDate getStartDayOfWeek(LocalDate localDate,int n) {
        TemporalField fieldISO = WeekFields.of(Locale.CHINA).dayOfWeek();
        return localDate.with(fieldISO, n);
    }

    public static List<String> getDateStringList( LocalDate startDate,LocalDate targetDate){
        List<String> list = new ArrayList<>();
        if(targetDate.isEqual(startDate)){
             list.add(parseDate(startDate, DateTimeUtils.TimeFormat.SHORT_DATE_PATTERN_LINE));
        }else{
            while(!targetDate.isEqual(startDate)){
                if(targetDate.isBefore(startDate)) {
                    targetDate=targetDate.plusDays(1);
                    list.add(parseDate(targetDate, DateTimeUtils.TimeFormat.SHORT_DATE_PATTERN_LINE));
                }
                else {
                    startDate = startDate.plusDays(1);
                    list.add(parseDate(startDate, DateTimeUtils.TimeFormat.SHORT_DATE_PATTERN_LINE));
                }
            }
        }
        return list;
    }

    // 01. java.util.Date --> java.time.LocalDateTime
    public static  LocalDateTime dateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return  LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 判断date1 是否在 date2之后
     * @param date1
     * @param date2
     * @return 是 返回true 否则返回false
     */
    public static boolean compareLocalDateTime(LocalDateTime date1,LocalDateTime date2){
        return date2.isBefore(date1);
    }


    public enum TimeFormat {

        /**
         * 时间格式 yyyy
         */
        SHORT_YEAR_PATTERN("yyyy"),

        /**
         * 时间格式 yyyy-MM
         */
        SHORT_MONTH_PATTERN("yyyy-MM"),

        //短时间格式 年月日
        /**
         *时间格式：yyyy-MM-dd
         */
        SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),

        /**
         *时间格式：yyyy-MM-dd HH
         */
        SHORT_DATE_HOUR_PATTERN_LINE("yyyy-MM-dd HH"),
        /**
         *时间格式：yyyy-MM-dd HH
         */
        SHORT_DATE_MINUTE_PATTERN_LINE("yyyy-MM-dd HH:mm"),
        /**
         *时间格式：yyyy/MM/dd
         */
        SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
        /**
         *时间格式：yyyy\\MM\\dd
         */
        SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
        /**
         *时间格式：yyyyMMdd
         */
        SHORT_DATE_PATTERN_NONE("yyyyMMdd"),

        /**
         *时间格式：yyyyMMdd
         */
        SHORT_HOUR_PATTERN_NONE("yyyyMMddHH"),
        /**
         *时间格式：yyyyMMdd
         */
        SHORT_MINUTE_PATTERN_NONE("yyyyMMddHHmm"),

        /**
         *时间格式：yyyyMMddHHmmss
         */
        SHORT_SECOND_PATTERN_NONE("yyyyMMddHHmmss"),

        /**
         *时间格式：HH:mm:ss.SSS
         */
        SHORT_TIME_PATTERN_NONE("HH:mm:ss.SSS"),

        /**
         *时间格式：HH:mm:ss
         */
        SHORT_TIME_PATTERN_NONE_WITHOUT_MIS("HH:mm:ss"),

        // 长时间格式 年月日时分秒
        /**
         *时间格式：yyyy-MM-dd HH:mm:ss
         */
        LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),

        /**
         *时间格式：yyyy/MM/dd HH:mm:ss
         */
        LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
        /**
         *时间格式：yyyy\\MM\\dd HH:mm:ss
         */
        LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
        /**
         *时间格式：yyyyMMdd HH:mm:ss
         */
        LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),


        // 长时间格式 年月日时分秒 带毫秒
        LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSSSSS"),

        LONG_DATE_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),

        LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),

        LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMdd HH:mm:ss.SSS");


        private transient DateTimeFormatter formatter;
        private transient SimpleDateFormat simpleDateFormat;


        TimeFormat(String pattern) {
            formatter = DateTimeFormatter.ofPattern(pattern);
            simpleDateFormat = new SimpleDateFormat(pattern);
        }
    }

    public static void main(String... args){
//        System.out.println(getMilliByTime(parseTime("2017-1-28 22:14:51", LONG_DATE_PATTERN_LINE)));
//        //获取当前时间
//        System.out.println(DateTimeUtils.getCurrentDateTime());
//
//        System.out.println(DateTimeUtils.getCurrentDateTime(TimeFormat.LONG_DATE_PATTERN_LINE));
//
//        System.out.println(DateTimeUtils.getMilliByTime(LocalDateTime.now()));
//
//        System.out.println(DateTimeUtils.geLocalDateTimeStr(DateTimeUtils.getFirstOfDateBymillSeconds(new Date()), TimeFormat.LONG_DATE_PATTERN_WITH_MILSEC_LINE));
//        System.out.println(DateTimeUtils.geLocalDateTimeStr(DateTimeUtils.getLastOfDateBymillSeconds(new Date()), TimeFormat.LONG_DATE_PATTERN_WITH_MILSEC_LINE));
//
//        LocalDateTime ldt = DateTimeUtils.getFirstOfDateBymillSeconds(new Date());
//
//        Arrays.asList(1,2,3,6,7,8,10,3,2,1,100).stream().forEach(
//                i->System.out.println(DateTimeUtils.geLocalDateTimeStr(ldt.plusDays(i), TimeFormat.LONG_DATE_PATTERN_WITH_MILSEC_LINE)));
//
//        System.out.println(DateTimeUtils.getMilliByTime(DateTimeUtils.getFirstOfDateBymillSeconds(new Date())));
//        System.out.println(DateTimeUtils.getMilliByTime(DateTimeUtils.getLastOfDateBymillSeconds(new Date())));
//
//        String test = "2018-06-21 10:00:00";
//        System.out.println(test);
//        Date fromDate= convertDateFromStr(test, TimeFormat.LONG_DATE_PATTERN_LINE);
//        for (int i=0;i<20;i++) {
//            fromDate = convertLDTToDate(convertDateoLDTT(fromDate).plusHours(1));
//            System.out.println(parseTime(fromDate, TimeFormat.LONG_DATE_PATTERN_LINE));
//        }
//
//        System.out.println("#######################HOUR");
//        List<String> list = DateTimeUtils.getBetweenDateStr(1531813888000L, 1531831888000L, CstConstants.TIME_SELECT.HOUR);
//        list.stream().forEach(System.out::println);
//        System.out.println("#######################DAY");
//        list= DateTimeUtils.getBetweenDateStr(1531554688000L, 1531831888000L, CstConstants.TIME_SELECT.DAY);
//        list.stream().forEach(System.out::println);
//        System.out.println("#######################MONTH");
//        list= DateTimeUtils.getBetweenDateStr(1531811802000L, 1531811802001L, CstConstants.TIME_SELECT.MONTH);
//        list.stream().forEach(System.out::println);
//        System.out.println("#######################YEAR");
//        list= DateTimeUtils.getBetweenDateStr(1436860288000L, 1531831888000L, CstConstants.TIME_SELECT.YEAR);
//        list.stream().forEach(System.out::println);
//
//        System.out.println(DateTimeUtils.getMilliByTime(parseTime("2018-07-17 19", TimeFormat.SHORT_DATE_HOUR_PATTERN_LINE)));
//        System.out.println(DateTimeUtils.getMilliByDate(parseDate("2018-01-01", TimeFormat.SHORT_DATE_PATTERN_LINE)));
//        System.out.println(DateTimeUtils.getMilliByTime(parseTime("2016-01-01 00:00:00", TimeFormat.LONG_DATE_PATTERN_LINE)));
//
//        System.out.println(getSpecialOfDateBymillSeconds(new Date(),"01:00:00", TimeFormat.SHORT_TIME_PATTERN_NONE_WITHOUT_MIS));
//
//        System.out.println("#######################DAY");
//        List<Long> longs = DateTimeUtils.getBetweenTimestamp(1531554688000L, 1531831888000L, CstConstants.TIME_SELECT.DAY);
//        longs.stream().forEach(System.out::println);
//        System.out.println("#######################DAY2");
//        longs = DateTimeUtils.getBetweenTimestamp(1531554688000L, 1531554688000L, CstConstants.TIME_SELECT.DAY);
//        longs.stream().forEach(System.out::println);

//        System.out.println(numOfDaysApart("2019-06-30","2019-07-01"));
          LocalDateTime date1 = DateTimeUtils.parseTime("2019-05-14 00:00:00", DateTimeUtils.TimeFormat.LONG_DATE_PATTERN_LINE);
          LocalDateTime date2 = DateTimeUtils.parseTime("2019-05-20 00:00:00.000000", DateTimeUtils.TimeFormat.LONG_DATE_PATTERN_WITH_MILSEC_LINE);
        boolean b = compareLocalDateTime(date1, date2);
        System.out.println(b);
    }
}


