package com.bluezhang.baseappframwork.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.DatePicker;

import com.bluezhang.baseappframwork.constance.BLConstantData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;

/**
 * DateTime util
 * <pre>
 * EN_YYYY_MM_DD = 0;//format to yyyy-mm-dd
 * EN_MM_DD = 1;//format to mm-dd
 * EN_YY_MM_DD_HH_MM = 4;//format yyyy-mm-dd hh:mm
 * CN_YYYY_MM_DD = 2;//format to XXXX年XX月XX日
 * CN_MM_DD = 3;//format to XX月XX日
 * HH_MM = 5;//format time to  hh : mm
 * YY_MM_DD_E = 6;//format time to  yyyy-mm-dd E
 * MM_DD_A = 7;//format time to  mm-dd a(AP/PM)
 * E = 8;//format time to   E
 * </pre>
 */
public class BLDateTimeUtil {
    public static final int DATETIME_FIELD_REFERSH = 20;
    public static final String HH_mm = "HH:mm";
    public static final String HH_mm_ss = "HH:mm:ss";
    public static final String MM_yy = "MM/yy";
    public static final String HHhmmm = "HH'h'mm'm'";
    public static final long ONE_DAY = 86400000L;
    public static final long ONE_HOUR = 3600000L;
    public static final long ONE_MINUTE = 60000L;
    public static final long ONE_SECOND = 1000L;
    public static final String dd_MM = "dd/MM";
    public static final String yyyyMMdd = "yyyyMMdd";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyyy_MM = "yyyy-MM";
    public static final String yyyy_MM_dd = "yyyy-MM-dd";
    public static final String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final int EN_YYYY_MM_DD = 0;//format yyyy-mm-dd
    public static final int EN_MM_DD = 1;//format mm-dd
    public static final int CN_YYYY_MM_DD = 2;//format XXXX年XX月XX日
    public static final int CN_MM_DD = 3;//fromat XX月XX日
    public static final int EN_YY_MM_DD_HH_MM = 4;//format yyyy-mm-dd hh:mm
    public static final int HH_MM = 5;//format time to  hh : mm
    public static final int YY_MM_DD_E = 6;//format time to  yyyy-mm-dd E
    public static final int YY_MM_DD_E_HH_MM = 10;//format time to yyyy-mm-dd E hh mm
    public static final int MM_DD_A = 7;//format time to  mm-dd a(AP/PM)
    public static final int E = 8;//format time to   E
    public static final int EN_MM = 9;//format mm
    /**
     * Format date to week
     */
    public static final String EEEE = "EEEE";
    private static final String[] PATTERNS = {"yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm", "yyyy-MM-dd", "yyyyMMdd"};
    public static boolean hasServerTime = false;
    public static long tslgapm = 0L;
    public static String tss;
    private static Context mContext;
    private static StringBuilder sb = new StringBuilder();
    private int year;

    public static void initATDateTimeUtil(Context context) {
        mContext = context;
    }

    /**
     * @param time date time
     * @param day  The current time is greater than the number of days this time
     * @return
     */
    public static boolean greaterThanDay(long time, int day) {
        long currentTime = new Date().getTime();
        int diffDay = (int) ((currentTime - time) / (1000 * 60 * 60 * 24));
        return diffDay > day;
    }

    private static String fixDateString(String paramString) {
        if (TextUtils.isEmpty(paramString))
            return paramString;
        String[] arrayOfString = paramString.split("[" + BLConstantData.CALENDAR_SPLIT_STRING + "]");
        if (arrayOfString.length == 1)
            arrayOfString = paramString.split("-");
        for (int i = 0; i < 3; i++) {
            if (arrayOfString[i].length() != 1)
                continue;
            arrayOfString[i] = ("0" + arrayOfString[i]);
        }
        return arrayOfString[0] + "-" + arrayOfString[1] + "-"
                + arrayOfString[2];
    }

    public static boolean sameDate(Calendar cal, Calendar selectedDate) {
        return cal.get(MONTH) == selectedDate.get(MONTH)
                && cal.get(YEAR) == selectedDate.get(YEAR)
                && cal.get(DAY_OF_MONTH) == selectedDate.get(DAY_OF_MONTH);
    }

    public static <T> Calendar getCalendar(T paramT) {
        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.setLenient(false);
        if (paramT == null)
            return null;
        if ((paramT instanceof Calendar)) {
            localCalendar1.setTimeInMillis(((Calendar) paramT)
                    .getTimeInMillis());
            return localCalendar1;
        }
        if ((paramT instanceof Date)) {
            localCalendar1.setTime((Date) paramT);
            return localCalendar1;
        }
        if ((paramT instanceof Long)) {
            localCalendar1.setTimeInMillis(((Long) paramT).longValue());
            return localCalendar1;
        }
        if ((paramT instanceof String)) {
            String str = (String) paramT;
            if (Pattern.compile("\\d{4}" + BLConstantData.CHINES_YEAR + "\\d{1,2}" + BLConstantData.CHINES_MONTH + "\\d{1,2}" + BLConstantData.CHINES_DAY + "").matcher(str)
                    .find()) {
                str = fixDateString(str);
                return getCalendarByPattern(str, "yyyy-MM-dd");
            }
            try {
                Calendar localCalendar2 = getCalendarByPatterns(str, PATTERNS);
                return localCalendar2;
            } catch (Exception localException) {
                try {
                    localCalendar1.setTimeInMillis(Long.valueOf(str)
                            .longValue());
                    return localCalendar1;
                } catch (NumberFormatException localNumberFormatException) {
                    throw new IllegalArgumentException(
                            localNumberFormatException);
                }
            }
        }
        throw new IllegalArgumentException();
    }


    public static <T> Calendar getCalendar(T paramT, Calendar paramCalendar) {
        if (paramT != null)
            try {
                return getCalendar(paramT);
            } catch (Exception localException) {
            }
        return (Calendar) paramCalendar.clone();
    }

    public static void cleanCalendarTime(Calendar paramCalendar) {
        paramCalendar.set(Calendar.HOUR_OF_DAY, 0);
        paramCalendar.set(Calendar.MINUTE, 0);
        paramCalendar.set(Calendar.SECOND, 0);
        paramCalendar.set(Calendar.MILLISECOND, 0);
    }

    public static Date getAfterYearDay() {
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        nextYear.add(Calendar.DAY_OF_YEAR, -1);
        nextYear.set(HOUR_OF_DAY, 0);
        nextYear.set(MINUTE, 0);
        nextYear.set(SECOND, 0);
        nextYear.set(MILLISECOND, 0);
        return nextYear.getTime();
    }

    public static Date getAfterDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + 1);
        return calendar.getTime();
    }

    public static Date getAfterNumDay(Date date, int numDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day + numDay);
        return calendar.getTime();
    }

    public static String getFlightTotalTime(int totalMinute) {
        String totalTime;
        int minute = totalMinute % 60;
        int hour = totalMinute / 60;
        totalTime = sb.delete(0, sb.length()).append(hour).append("h").append(minute).append("m").toString();
        totalTime = sb.delete(0, sb.length()).append(hour).append("h").append(minute).append("m").toString();
        return totalTime;
    }

    public static String getGetLag(long time) {
        String totalTime;
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;
        long day = time / dd;
        long hour = (time) / hh;
        long minute = (time - day * dd - hour * hh) / mi;
        long second = (time - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = time - day * dd - hour * hh - minute * mi - second * ss;
        totalTime = sb.delete(0, sb.length()).append((day * 24) + hour).append("h")
                .append(minute).append("m").toString();
        return totalTime;
    }

    public static int getGetLagYear(long time) {
        String totalTime;
        long ss = 1000;
        long mi = ss * 60;
        long hh = mi * 60;
        long dd = hh * 24;
        long day = time / dd;
        long year = day / 365;
        totalTime = sb.delete(0, sb.length()).append(year).toString();
        return (int) year;
    }

    public static Date getBeforeDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - 1);
        return calendar.getTime();
    }

    public static Calendar getCalendarByPattern(String paramString1,
                                                String paramString2) {
        try {
            SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
                    paramString2, Locale.US);
            localSimpleDateFormat.setLenient(false);
            Date localDate = localSimpleDateFormat.parse(paramString1);
            Calendar localCalendar = Calendar.getInstance();
            localCalendar.setLenient(false);
            localCalendar.setTimeInMillis(localDate.getTime());
            return localCalendar;
        } catch (Exception localException) {
            throw new IllegalArgumentException(localException);
        }

    }

    public static Calendar getCalendarByPatterns(String paramString,
                                                 String[] paramArrayOfString) {
        int i = paramArrayOfString.length;
        int j = 0;
        while (j < i) {
            String str = paramArrayOfString[j];
            try {
                Calendar localCalendar = getCalendarByPattern(paramString, str);
                return localCalendar;
            } catch (Exception localException) {
                j++;
            }
        }
        throw new IllegalArgumentException();
    }

    public static Calendar getCurrentDateTime() {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setLenient(false);
        if (hasServerTime)
            localCalendar.setTimeInMillis(localCalendar.getTimeInMillis()
                    + tslgapm);
        return localCalendar;
    }

    public static Calendar getDateAdd(Calendar paramCalendar, int paramInt) {
        if (paramCalendar == null)
            return null;
        Calendar localCalendar = (Calendar) paramCalendar.clone();
        localCalendar.add(Calendar.DATE, paramInt);
        return localCalendar;
    }

    public static int getIntervalDays(String paramString1, String paramString2,
                                      String paramString3) {
        if ((paramString1 == null) || (paramString2 == null))
            return 0;
        return getIntervalDays(
                getCalendarByPattern(paramString1, paramString3),
                getCalendarByPattern(paramString2, paramString3));
    }

    public static long getIntervalTimes(Calendar paramCalendar1,
                                        Calendar paramCalendar2, long paramLong) {
        if ((paramCalendar1 == null) || (paramCalendar2 == null))
            return 0L;
        return Math.abs(paramCalendar1.getTimeInMillis()
                - paramCalendar2.getTimeInMillis())
                / paramLong;
    }

    public static Calendar getLoginServerDate() {
        return getCalendar(tss);
    }

    public static String getHH_mm(String date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    public static String getHH_mm(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }


    public static <T> int getIntervalDays(T paramT1, T paramT2) {
        Calendar localCalendar1 = getCalendar(paramT1);
        Calendar localCalendar2 = getCalendar(paramT2);
        cleanCalendarTime(localCalendar1);
        cleanCalendarTime(localCalendar2);
        return (int) getIntervalTimes(localCalendar1, localCalendar2, 86400000L);
    }

    public static boolean isLeapyear(String paramString) {
        Calendar localCalendar = getCalendar(paramString);
        if (localCalendar != null) {
            int i = localCalendar.get(Calendar.YEAR);
            return (i % 4 == 0) && ((i % 100 != 0) || (i % 400 == 0));
        }
        return false;
    }

    public static boolean isRefersh(long paramLong) {
        return isRefersh(1200000L, paramLong);
    }

    public static boolean isRefersh(long paramLong1, long paramLong2) {
        return new Date().getTime() - paramLong2 >= paramLong1;
    }

    public static String printCalendarByPattern(Calendar paramCalendar,
                                                String paramString) {
        if ((paramCalendar == null) || (paramString == null))
            return null;
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
                paramString, Locale.US);
        localSimpleDateFormat.setLenient(false);
        return localSimpleDateFormat.format(paramCalendar.getTime());
    }

    public static String getDateStringWithTimeZone(String date) {
        return date + "T00:00:00";
    }

    /**
     * Formate the date Object to at_calendar_week
     *
     * @param context context
     * @param date    date
     * @return String at_calendar_week
     */
    public static String dataToWeek(Context context, Date date) {
        String now = new SimpleDateFormat("E").format(date);
        String ret = now;
        return dataToWeek(context, ret);
    }

    /**
     * Formate the date Object to at_calendar_week
     *
     * @param context context
     * @return String at_calendar_week
     */
    public static String dataToWeek(Context context, String now) {
        String ret = now;
        if (BLConstantData.BL_MONDAY.equals(now)) {
            ret = BLConstantData.MONDAY;
        } else if (BLConstantData.BL_TUESDAY.equals(now)) {
            ret = BLConstantData.TUESDAY;
        } else if (BLConstantData.BL_WEDNESDAY.equals(now)) {
            ret = BLConstantData.WEDNESDAY;
        } else if (BLConstantData.THURSDAY.equals(now)) {
            ret = BLConstantData.THURSDAY;
        } else if (BLConstantData.BL_FRIDAY.equals(now)) {
            ret = BLConstantData.FRIDAY;
        } else if (BLConstantData.BL_SATURDAY.equals(now)) {
            ret = BLConstantData.SATURDAY;
        } else if (BLConstantData.BL_SUNDAY.equals(now)) {
            ret = BLConstantData.SUNDAY;
        }
        return ret;
    }

    /**
     * Format the date Object to at_calendar_week num
     *
     * @param context context
     * @return String at_calendar_week
     */
    public static int dateToWeekNum(Context context, Date date) {
        String now = dataToWeek(context, date);
        int ret = -1;
        if (BLConstantData.MONDAY.equals(now)) {
            ret = 1;
        } else if (BLConstantData.TUESDAY.equals(now)) {
            ret = 2;
        } else if (BLConstantData.WEDNESDAY.equals(now)) {
            ret = 3;
        } else if (BLConstantData.TUESDAY.equals(now)) {
            ret = 4;
        } else if (BLConstantData.FRIDAY.equals(now)) {
            ret = 5;
        } else if (BLConstantData.SATURDAY.equals(now)) {
            ret = 6;
        } else if (BLConstantData.SUNDAY.equals(now)) {
            ret = 0;
        }
        return ret;
    }

    public static boolean betweenDates(Date date, long minCal, long maxCal) {
        return (date.equals(new Date(minCal)) || date.after(new Date(minCal))) // >= minCal
                && date.before(new Date(maxCal)); // && < maxCal
    }

    /**
     * Get Date object from milliseconds
     *
     * @param milliSeconds seconds
     * @return Date object
     * @see Date
     */
    public static Date getDate(long milliSeconds) {
        return new Date(milliSeconds);
    }

    /**
     * Param must be one type of milliseconds / date
     *
     * @param param milliseconds / date
     * @return String mm-dd
     * @see SimpleDateFormat
     */
    public static String getEN_MM_DD(Object param) {
        return getFormatDate(param, EN_MM_DD);
    }

    /**
     * Param must be one type of milliseconds / date
     *
     * @param param milliseconds / date
     * @return String yyyy-mm-dd
     * @see SimpleDateFormat
     */
    public static String getEN_YYYY_MM_DD(Object param) {
        return getFormatDate(param, EN_YYYY_MM_DD);
    }

    /**
     * Param must be one type of milliseconds / date
     *
     * @param param milliseconds / date
     * @return String cn yyyy年mm月dd日
     * @see SimpleDateFormat
     */
    public static String getCN_YYYY_MM_DD(Object param) {
        return getFormatDate(param, CN_YYYY_MM_DD);
    }

    /**
     * Param must be one type of milliseconds / date
     *
     * @param param milliseconds / date
     * @return String cn mm月dd日
     * @see SimpleDateFormat
     */
    public static String getCN_MM_DD(Object param) {
        return getFormatDate(param, CN_MM_DD);
    }

    /**
     * Param must be one type of milliseconds / date
     *
     * @param param milliseconds / date
     * @return String format yyyy-mm-dd hh:mm
     * @see SimpleDateFormat
     */
    public static String getENYY_MM_DD_HH_MM(Object param) {
        return getFormatDate(param, EN_YY_MM_DD_HH_MM);
    }

    /**
     * Param must be one type of milliseconds / date
     *
     * @param param milliseconds / date
     * @return String format hh:mm
     * @see SimpleDateFormat
     */
    public static String getHH_MM(Object param) {
        return getFormatDate(param, HH_MM);
    }

    /**
     * Param must be one type of milliseconds / date
     *
     * @param param milliseconds / date
     * @return String format yyy-mm-dd E
     * @see SimpleDateFormat
     */
    public static String getCN_YY_MM_DD(Object param) {
        return getFormatDate(param, YY_MM_DD_E);
    }

    /**
     * Param must be one type of milliseconds / date
     *
     * @param param milliseconds / date
     * @return String format yyy-mm-dd E
     * @see SimpleDateFormat
     */
    public static String getCN_YY_MM_DD_E_HH_MM(Object param) {
        return getFormatDate(param, YY_MM_DD_E_HH_MM);
    }

    public static String getE(Object param) {
        return getFormatDate(param, E);
    }

    /**
     * Constructs a new {@code SimpleDateFormat} using the specified use the type ,and return the format character
     * <pre>
     *     type :
     *       EN_YYYY_MM_DD = 0;//format to yyyy-mm-dd
     *       EN_MM_DD = 1;//format to mm-dd
     *       EN_YY_MM_DD_HH_MM = 4;//format yyyy-mm-dd hh:mm
     *       CN_YYYY_MM_DD = 2;//format to XXXX年XX月XX日
     *       CN_MM_DD = 3;//format to XX月XX日
     *       HH_MM = 5;//format time to  hh : mm
     *       YY_MM_DD_E = 6;//format time to  yyyy-mm-dd E
     *       MM_DD_A = 7;//format time to  mm-dd a(AP/PM)
     *       E = 8;//format time to   E
     *  </pre>
     *
     * @param param milliseconds / date
     * @param which format type
     * @return String data
     * @see BLDateTimeUtil
     */
    public static String getFormatDate(Object param, int which) {
        String ret = null;
        if (param != null) {
            try {
                if (which == EN_YYYY_MM_DD) {
                    ret = new SimpleDateFormat(BLConstantData.YY_MM_DD).format(param);
                } else if (which == EN_MM_DD) {
                    ret = new SimpleDateFormat(BLConstantData.MM_DD).format(param);
                } else if (which == CN_YYYY_MM_DD) {
                    ret = new SimpleDateFormat(BLConstantData.CN_YY_MM_DD).format(param);
                } else if (which == CN_MM_DD) {
                    ret = new SimpleDateFormat(BLConstantData.CN_MM_DD).format(param);
                } else if (which == EN_YY_MM_DD_HH_MM) {
                    ret = new SimpleDateFormat(BLConstantData.YYYY_MM_DD_HH_MM).format(param);
                } else if (which == HH_MM) {
                    ret = new SimpleDateFormat(BLConstantData.HH_MM).format(param);
                } else if (which == YY_MM_DD_E) {
                    ret = new SimpleDateFormat(BLConstantData.YY_MM_DD_E).format(param);
                } else if (which == E) {
                    ret = new SimpleDateFormat(BLConstantData.E).format(param);
                } else if (which == MM_DD_A) {
                    ret = new SimpleDateFormat(BLConstantData.MM_DD_A).format(param);
                } else if (which == EN_MM) {
                    ret = new SimpleDateFormat(BLConstantData.MM).format(param);
                } else if (which == YY_MM_DD_E_HH_MM) {
                    ret = new SimpleDateFormat(BLConstantData.YY_MM_DD_E_H_M).format(param);
                } else {
                    throw new IllegalArgumentException("Argument is wrong ,see ATDateTImeUtil !");
                }
            } catch (Exception e) {
                LogUtil.d(e.toString() + "Object type incompatible");
            }
        }
        return ret;
    }

    /**
     * <h1>Get Target day </h1>
     *
     * @param param the day need to add
     * @return date
     */
    public static Date getTargetDate(int param) {
        Date ret = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, param);
        ret = calendar.getTime();
        return ret;
    }

    /**
     * Get Target day
     *
     * @param param the day need to add
     * @param date  default date
     * @return date
     */
    public static Date getTargetDate(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, param);
        return calendar.getTime();
    }

    /**
     * Get Target day
     *
     * @param param the month need to add
     * @param date  default date
     * @return date
     */
    public static Date getTargetMonthDate(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, param);
        return calendar.getTime();
    }


    /**
     * <h1>Get Target year </h1>
     *
     * @param param the year need to add
     * @return date
     */
    public static Date getTargetDateYear(int param) {
        Date ret = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, param);
        ret = calendar.getTime();
        return ret;
    }

    /**
     * <h1>Get Target time </h1>
     *
     * @param param the hour need to add
     * @return date
     */
    public static Date getTargetTime(int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, param);
        return calendar.getTime();
    }

    /**
     * Get target time
     *
     * @param date  start date
     * @param param seconds need add
     * @return need date
     */
    public static Date getTargetTime(Date date, int param) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, param);
        return calendar.getTime();
    }

    /**
     * String to Date        2016-03-28T11:35:00   the format of server
     *
     * @param dateStr string
     * @return Date date
     */
    public static Date toDate(String dateStr) {
        Date date = new Date();
        SimpleDateFormat formater = new SimpleDateFormat();
        if (!TextUtils.isEmpty(dateStr) && dateStr.contains("T")) {
            formater.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        } else {
            formater.applyPattern("yyyy-MM-dd");
        }
        try {
            if (!BLStringUtil.isEmpty(dateStr)) {
                date = formater.parse(dateStr);
            }
        } catch (ParseException e) {
            LogUtil.e(e.getMessage());
        }
        return date;
    }

    /**
     * formateDate        2016-03-28T11:35:00   the format of server
     *
     * @param dateStr string
     * @return Date date
     */
    public static Date formateDate(String dateStr) {
        Date date = new Date();
        SimpleDateFormat formater = new SimpleDateFormat();
        if (!TextUtils.isEmpty(dateStr) && dateStr.contains("T")) {
            formater.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
        } else {
            formater.applyPattern("yyyy-MM-dd");
        }
        try {
            if (!BLStringUtil.isEmpty(dateStr)) {
                date = formater.parse(dateStr);
            }
        } catch (ParseException e) {
            LogUtil.d(e.getMessage());
        }
        return date;
    }

    /**
     * String to date use formaterString
     *
     * @param dateStr        old string
     * @param formaterString new string
     * @return Date date
     */
    public static Date toDate(String dateStr, String formaterString) {
        Date date = new Date();
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern(formaterString);
        try {
            date = formater.parse(dateStr);
        } catch (ParseException e) {
            LogUtil.d(e.getMessage());
        }
        return date;
    }

    /**
     * Date to String
     *
     * @param date date
     * @return string
     */
    public static String toString(Date date) {
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern("yyyy-MM-dd");
        return formater.format(date);
    }

    /**
     * Use formaterString return string after formate
     *
     * @param date           date
     * @param formaterString formaterString
     * @return string
     */
    public static String toString(Date date, String formaterString) {
        SimpleDateFormat formater = new SimpleDateFormat();
        formater.applyPattern(formaterString);
        return formater.format(date);
    }

    /**
     * Get date object
     *
     * @param picker DatePicker
     * @return Date
     */
    public static Date getDate(DatePicker picker) {
        Calendar calendar = Calendar.getInstance();
        if (picker != null) {
            calendar.set(Calendar.YEAR, picker.getYear());
            calendar.set(Calendar.MONTH, picker.getMonth());
            calendar.set(Calendar.DAY_OF_MONTH, picker.getDayOfMonth());
        }
        return calendar.getTime();
    }

    public static String dateToValidityDate(Date date) {
        return new SimpleDateFormat("MM/yy").format(date);
    }

    public static Date processDate(Date param, Date defaultDate) {
        if (param != null && defaultDate != null) {
            if (param.getTime() < new Date().getTime()) {
                return defaultDate;
            }
            return param;
        } else {
            return new Date();
        }
    }


    /**
     * long to yyyy-MM-ddTHH:mm:ss
     *
     * @param currentTime
     * @return
     * @throws ParseException
     */
    public static String longToString(long currentTime) {
        Date date = getDate(currentTime);
        String strTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(date);
        return strTime;
    }


    /**
     * yyyy-MM-ddTHH:mm:ss  to mouth-day chinese
     *
     * @param dataStr
     * @return
     * @throws ParseException
     */
    public static String tdataToMouthDay(String dataStr) {
        Date d = toDate(dataStr);
        return getCN_MM_DD(d);
    }

    /**
     * yyyy-MM-ddTHH:mm:ss  to mouth-day
     *
     * @param dataStr
     * @return
     * @throws ParseException
     */
    public static String tdataToMouthDayE(String dataStr) {
        Date d = toDate(dataStr);
        return getEN_MM_DD(d);
    }

    /**
     * yyyy-MM-ddTHH:mm:ss  to mouth chinese
     *
     * @param dataStr
     * @return
     * @throws ParseException
     */
    public static String tdataToMouth(String dataStr) {
        Date d = toDate(dataStr);
        return getEN_MM(d);
    }

    /**
     * Param must be one type of milliseconds / date
     *
     * @param param milliseconds / date
     * @return String mm-dd
     * @see SimpleDateFormat
     */
    public static String getEN_MM(Object param) {
        return getFormatDate(param, EN_MM);
    }

    public static int numberOfDays(long endTime, long startTime) {
        return (int) ((endTime - startTime) / (24 * 60 * 60 * 1000));
    }

    /**
     * to HHMM
     *
     * @param arriveDate
     * @param departDate
     * @return
     */
    public static String getTwoDataHHMM(String arriveDate, String departDate) {
        Date arri = toDate(arriveDate);
        Date depa = toDate(departDate);
        long between = arri.getTime() - depa.getTime();
        return getFlightTotalTime((int) (between / (1000 * 60)));
    }

    /**
     * date between today
     *
     * @param date
     * @return month num
     */
    public static long getPassPortIsOk(String date) {
        Date arri = formateDate(date);
        Date now = new Date();
        long time = arri.getTime() - now.getTime();
        return (time / (ONE_DAY * 30));
    }

    /**
     * long to yyyy-MM-dd HH:mm:ss
     *
     * @return
     * @throws ParseException
     */
    public static String dateToString() {
        Date date = new Date();
        String strTime = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss).format(date);
        return strTime;
    }

    public static String getCurrenDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyy_MM_dd);
        simpleDateFormat.format(new Date());
        return simpleDateFormat.format(new Date());
    }

    public static int compareDate(String DATE1, String DATE2) {
        SimpleDateFormat df = new SimpleDateFormat(yyyy_MM_dd);
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }
}
