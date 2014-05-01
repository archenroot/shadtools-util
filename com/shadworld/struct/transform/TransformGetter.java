/*    */ package com.shadworld.struct.transform;
/*    */ 
/*    */ public class TransformGetter
/*    */ {
/*    */   protected TimeEbayDateToMysqlDateTime timeEbayDateToMysqlDateTime;
/*    */   protected TimeJavaDateToMysqlDateTime timeJavaDateToMysqlDateTime;
/*    */   protected CurrencyStripToDecimal currencyStripToDecimal;
/*    */ 
/*    */   private void init()
/*    */   {
/* 26 */     this.timeEbayDateToMysqlDateTime = new TimeEbayDateToMysqlDateTime();
/* 27 */     this.timeJavaDateToMysqlDateTime = new TimeJavaDateToMysqlDateTime();
/* 28 */     this.currencyStripToDecimal = new CurrencyStripToDecimal();
/*    */   }
/*    */ 
/*    */   public TransformGetter() {
/* 32 */     init();
/*    */   }
/*    */ 
/*    */   public TimeEbayDateToMysqlDateTime timeEbayDateToMysqlDateTime() {
/* 36 */     return this.timeEbayDateToMysqlDateTime;
/*    */   }
/*    */ 
/*    */   public TimeJavaDateToMysqlDateTime timeJavaDateToMysqlDateTime() {
/* 40 */     return this.timeJavaDateToMysqlDateTime;
/*    */   }
/*    */ 
/*    */   public CurrencyStripToDecimal currencyStripToDecimal() {
/* 44 */     return this.currencyStripToDecimal;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.struct.transform.TransformGetter
 * JD-Core Version:    0.6.2
 */