/*    */ package com.shadworld.struct.transform;
/*    */ 
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class TimeEbayDateToMysqlDateTime extends DataTransformer
/*    */ {
/*    */   public String transform(Object timeObj)
/*    */   {
/* 27 */     String timeString = "";
/* 28 */     if (timeObj != null)
/* 29 */       timeString = timeObj.toString();
/* 30 */     SimpleDateFormat formatter = new SimpleDateFormat("MMM-dd-yy");
/* 31 */     Date date = null;
/* 32 */     String sqlTime = "1970-01-01 00:00:00";
/* 33 */     if ((timeString != null) && (!timeString.equals(""))) {
/*    */       try {
/* 35 */         date = formatter.parse(timeString);
/*    */       } catch (ParseException ex) {
/* 37 */         Logger.getLogger(TimeEbayDateToMysqlDateTime.class.getName()).log(Level.SEVERE, null, ex);
/*    */       }
/* 39 */       SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/* 40 */       sqlTime = SQL_DATE_FORMAT.format(date);
/*    */     }
/* 42 */     return sqlTime;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.struct.transform.TimeEbayDateToMysqlDateTime
 * JD-Core Version:    0.6.2
 */