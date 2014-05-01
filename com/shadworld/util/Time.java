/*     */ package com.shadworld.util;
/*     */ 
/*     */ import com.shadworld.utils.L;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public class Time
/*     */ {
/*  14 */   public static final SimpleDateFormat sqlDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*  15 */   public static final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
/*  16 */   public static final SimpleDateFormat sqlTimeFormat = new SimpleDateFormat("HH:mm:ss");
/*  17 */   public static final SimpleDateFormat javaDate = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
/*  18 */   public static final TimeZone tzGMT = TimeZone.getTimeZone("GMT");
/*  19 */   public static final TimeZone tzSyd = TimeZone.getTimeZone("Australia/Sydney");
/*  20 */   public static final TimeZone tzBris = TimeZone.getTimeZone("Australia/Brisbane");
/*     */   public static final long SEC = 1000L;
/*     */   public static final long MIN = 60000L;
/*     */   public static final long HR = 3600000L;
/*     */   public static final long DAY = 86400000L;
/*     */   public static final long WK = 604800000L;
/*     */   public static final long YR = 31536000000L;
/*     */ 
/*     */   public static String sqlNowDate()
/*     */   {
/*  50 */     return sqlDateFormat.format(new Date());
/*     */   }
/*     */ 
/*     */   public static String sqlNowTime() {
/*  54 */     return sqlTimeFormat.format(new Date());
/*     */   }
/*     */ 
/*     */   public static String sqlNowDateTime() {
/*  58 */     return sqlDateTimeFormat.format(new Date());
/*     */   }
/*     */ 
/*     */   public static Date trimToMinute(Date date)
/*     */   {
/*  65 */     Calendar cal = calendar(date);
/*  66 */     cal.set(14, 0);
/*  67 */     cal.set(13, 0);
/*  68 */     return cal.getTime();
/*     */   }
/*     */ 
/*     */   public static Date trimToHour(Date date)
/*     */   {
/*  75 */     Calendar cal = calendar(date);
/*  76 */     cal.set(14, 0);
/*  77 */     cal.set(13, 0);
/*  78 */     cal.set(12, 0);
/*  79 */     return cal.getTime();
/*     */   }
/*     */ 
/*     */   public static Date trimToDay(Date date, TimeZone tz)
/*     */   {
/*  86 */     Calendar cal = calendar(date, tz);
/*  87 */     cal.set(14, 0);
/*  88 */     cal.set(13, 0);
/*  89 */     cal.set(12, 0);
/*  90 */     cal.set(11, 0);
/*  91 */     return cal.getTime();
/*     */   }
/*     */ 
/*     */   public static long trimToDay(long timestamp, TimeZone tz)
/*     */   {
/*  99 */     return trimToDay(new Date(timestamp), tz).getTime();
/*     */   }
/*     */ 
/*     */   public static Date trimToWeek(Date date, TimeZone tz, int dayOfWeekConstant)
/*     */   {
/* 108 */     Calendar cal = calendar(date, tz);
/* 109 */     cal.set(14, 0);
/* 110 */     cal.set(13, 0);
/* 111 */     cal.set(12, 0);
/* 112 */     cal.set(11, 0);
/* 113 */     cal.set(7, dayOfWeekConstant);
/* 114 */     return cal.getTime();
/*     */   }
/*     */ 
/*     */   public static Date trimToWeek(Date date, TimeZone tz) {
/* 118 */     Calendar cal = calendar(date, tz);
/* 119 */     cal.set(14, 0);
/* 120 */     cal.set(13, 0);
/* 121 */     cal.set(12, 0);
/* 122 */     cal.set(11, 0);
/* 123 */     cal.set(7, cal.getFirstDayOfWeek());
/* 124 */     return cal.getTime();
/*     */   }
/*     */ 
/*     */   public static Date trimToMonth(Date date, TimeZone tz) {
/* 128 */     Calendar cal = calendar(date, tz);
/* 129 */     cal.set(14, 0);
/* 130 */     cal.set(13, 0);
/* 131 */     cal.set(12, 0);
/* 132 */     cal.set(11, 0);
/* 133 */     cal.set(5, 1);
/* 134 */     return cal.getTime();
/*     */   }
/*     */ 
/*     */   public static Date trimToYear(Date date, TimeZone tz) {
/* 138 */     Calendar cal = calendar(date, tz);
/* 139 */     cal.set(14, 0);
/* 140 */     cal.set(13, 0);
/* 141 */     cal.set(12, 0);
/* 142 */     cal.set(11, 0);
/* 143 */     cal.set(6, 1);
/* 144 */     return cal.getTime();
/*     */   }
/*     */ 
/*     */   public static Date currentDate()
/*     */   {
/* 151 */     return new Date(System.currentTimeMillis() / 86400000L * 86400000L);
/*     */   }
/*     */ 
/*     */   public static long currentDateStamp()
/*     */   {
/* 158 */     return System.currentTimeMillis() / 86400000L * 86400000L;
/*     */   }
/*     */ 
/*     */   public static Calendar calendar(Date date)
/*     */   {
/* 167 */     Calendar cal = Calendar.getInstance();
/* 168 */     cal.setTime(date);
/* 169 */     return cal;
/*     */   }
/*     */ 
/*     */   public static Calendar calendar(Date date, TimeZone timezone)
/*     */   {
/* 179 */     Calendar cal = Calendar.getInstance(timezone);
/* 180 */     cal.setTime(date);
/* 181 */     return cal;
/*     */   }
/*     */ 
/*     */   public static Calendar calendarDate(Date date, TimeZone timezone)
/*     */   {
/* 191 */     Calendar cal = Calendar.getInstance(timezone);
/* 192 */     cal.setTime(trimToDay(date, timezone));
/* 193 */     return cal;
/*     */   }
/*     */ 
/*     */   public static List<Date> trimToDays(List<Date> dates, TimeZone tz) {
/* 197 */     List newDates = new ArrayList();
/* 198 */     for (Date date : dates) {
/* 199 */       newDates.add(trimToDay(date, tz));
/*     */     }
/* 201 */     return newDates;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args) {
/* 205 */     L.println(trimToDay(new Date(), tzBris));
/* 206 */     L.println(trimToWeek(new Date(), tzBris));
/* 207 */     L.println(trimToMonth(new Date(), tzBris));
/* 208 */     L.println(trimToYear(new Date(), tzBris));
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.util.Time
 * JD-Core Version:    0.6.2
 */