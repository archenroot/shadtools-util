/*    */ package com.shadworld.exception;
/*    */ 
/*    */ import com.shadworld.struct.Record;
/*    */ 
/*    */ public class ShadException extends Exception
/*    */ {
/*    */   private Record rec;
/*    */ 
/*    */   public ShadException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ShadException(String message, Throwable cause)
/*    */   {
/* 15 */     super(message, cause);
/*    */   }
/*    */ 
/*    */   public ShadException(String message)
/*    */   {
/* 20 */     super(message);
/*    */   }
/*    */ 
/*    */   public ShadException(Throwable cause)
/*    */   {
/* 25 */     super(cause);
/*    */   }
/*    */ 
/*    */   public ShadException add(String key, Object value)
/*    */   {
/* 36 */     if (this.rec == null)
/* 37 */       this.rec = new Record();
/* 38 */     this.rec.put(key, value);
/* 39 */     return this;
/*    */   }
/*    */ 
/*    */   public String getMessage()
/*    */   {
/* 47 */     if (this.rec == null)
/* 48 */       return super.getMessage();
/* 49 */     return super.getMessage() + ": " + this.rec;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.exception.ShadException
 * JD-Core Version:    0.6.2
 */