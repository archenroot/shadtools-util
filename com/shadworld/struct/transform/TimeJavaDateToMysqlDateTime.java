/*    */ package com.shadworld.struct.transform;
/*    */ 
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class TimeJavaDateToMysqlDateTime extends DataTransformer
/*    */ {
/*    */   public String transform(Object timeObj)
/*    */   {
/* 25 */     String timeString = "";
/* 26 */     if (timeObj != null)
/* 27 */       timeString = timeObj.toString();
/* 28 */     SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
/* 29 */     Date date = null;
/* 30 */     String sqlTime = "1970-01-01 00:00:01";
/* 31 */     if (timeString.equals("0"))
/* 32 */       timeString = "";
/* 33 */     if ((timeString != null) && (!timeString.equals(""))) {
/*    */       try {
/* 35 */         date = formatter.parse(timeString);
/*    */       } catch (ParseException ex) {
/* 37 */         Logger.getLogger(TimeJavaDateToMysqlDateTime.class.getName()).log(Level.SEVERE, null, ex);
/*    */       }
/* 39 */       SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 40 */       sqlTime = SQL_DATE_FORMAT.format(date);
/*    */     }
/* 42 */     return sqlTime;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.struct.transform.TimeJavaDateToMysqlDateTime
 * JD-Core Version:    0.6.2
 */