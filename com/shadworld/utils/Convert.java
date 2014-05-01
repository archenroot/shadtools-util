/*     */ package com.shadworld.utils;
/*     */ 
/*     */ import com.shadworld.util.Time;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ 
/*     */ public class Convert
/*     */ {
/*     */   public static String toString(Object o)
/*     */   {
/*  10 */     if (o == null)
/*  11 */       return null;
/*  12 */     return o.toString();
/*     */   }
/*     */ 
/*     */   public static Float toFloat(Object o) {
/*  16 */     if (o == null)
/*  17 */       return null;
/*  18 */     if ((o instanceof Float))
/*  19 */       return (Float)o;
/*  20 */     Float r = null;
/*     */     try {
/*  22 */       r = Float.valueOf(o.toString());
/*     */     } catch (Exception localException) {
/*     */     }
/*  25 */     return r;
/*     */   }
/*     */ 
/*     */   public static Double toDouble(Object o) {
/*  29 */     if (o == null)
/*  30 */       return null;
/*  31 */     if ((o instanceof Double))
/*  32 */       return (Double)o;
/*  33 */     Double r = null;
/*     */     try {
/*  35 */       r = Double.valueOf(o.toString());
/*     */     } catch (Exception localException) {
/*     */     }
/*  38 */     return r;
/*     */   }
/*     */ 
/*     */   public static Integer toInteger(Object o) {
/*  42 */     if (o == null)
/*  43 */       return null;
/*  44 */     if ((o instanceof Integer))
/*  45 */       return (Integer)o;
/*  46 */     Integer r = null;
/*     */     try {
/*  48 */       r = Integer.valueOf(o.toString());
/*     */     } catch (Exception localException) {
/*     */     }
/*  51 */     return r;
/*     */   }
/*     */ 
/*     */   public static Long toLong(Object o) {
/*  55 */     if (o == null)
/*  56 */       return null;
/*  57 */     if ((o instanceof Long))
/*  58 */       return (Long)o;
/*  59 */     Long r = null;
/*     */     try {
/*  61 */       r = Long.valueOf(o.toString());
/*     */     } catch (Exception localException) {
/*     */     }
/*  64 */     return r;
/*     */   }
/*     */ 
/*     */   public static Boolean toBoolean(Object o) {
/*  68 */     if (o == null)
/*  69 */       return null;
/*  70 */     if ((o instanceof Boolean))
/*  71 */       return (Boolean)o;
/*  72 */     Boolean r = null;
/*  73 */     if (o.toString().trim().equals("1"))
/*  74 */       return Boolean.valueOf(true);
/*  75 */     if (o.toString().trim().equals("0"))
/*  76 */       return Boolean.valueOf(false);
/*     */     try {
/*  78 */       r = Boolean.valueOf(o.toString().trim().toLowerCase());
/*     */     } catch (Exception localException) {
/*     */     }
/*  81 */     return r;
/*     */   }
/*     */ 
/*     */   public static Date toDate(Object o) {
/*  85 */     if (o == null)
/*  86 */       return null;
/*  87 */     if ((o instanceof Date))
/*  88 */       return (Date)o;
/*  89 */     String s = o.toString();
/*  90 */     Date r = null;
/*     */     try {
/*  92 */       r = Time.javaDate.parse(s);
/*     */     } catch (Exception ex) {
/*     */       try {
/*  95 */         r = Time.sqlDateTimeFormat.parse(s);
/*     */       } catch (Exception e) {
/*     */         try {
/*  98 */           r = Time.sqlDateFormat.parse(s);
/*     */         } catch (Exception e1) {
/*     */           try {
/* 101 */             r = Time.sqlTimeFormat.parse(s);
/*     */           }
/*     */           catch (Exception localException1)
/*     */           {
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 109 */     return r;
/*     */   }
/*     */ 
/*     */   public static <E extends Enum> E toEnum(Object o, Class<E> e) {
/* 113 */     if (e == null)
/* 114 */       return null;
/* 115 */     if (o == null)
/* 116 */       return null;
/* 117 */     String s = o.toString();
/* 118 */     return Enum.valueOf(e, s);
/*     */   }
/*     */ 
/*     */   public static Integer BooleanAsInt(Boolean bool) {
/* 122 */     if (bool == null)
/* 123 */       return null;
/* 124 */     return Integer.valueOf(bool.booleanValue() ? 1 : 0);
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.Convert
 * JD-Core Version:    0.6.2
 */