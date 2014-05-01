/*    */ package com.shadworld.utils;
/*    */ 
/*    */ public class Seq
/*    */ {
/* 10 */   private static Seq instance = new Seq();
/*    */ 
/* 12 */   private long sequenceId = 0L;
/*    */ 
/*    */   public Seq() {
/*    */   }
/*    */   public Seq(long start) {
/* 17 */     this.sequenceId = start;
/*    */   }
/*    */ 
/*    */   public int nextInt() {
/* 21 */     return (int)this.sequenceId++;
/*    */   }
/*    */ 
/*    */   public long next() {
/* 25 */     return this.sequenceId++;
/*    */   }
/*    */ 
/*    */   public static int newIntSeqId()
/*    */   {
/* 34 */     return (int)instance.sequenceId++;
/*    */   }
/*    */ 
/*    */   public static long newSeqId()
/*    */   {
/* 42 */     return instance.sequenceId++;
/*    */   }
/*    */ 
/*    */   public boolean isOverflowed()
/*    */   {
/* 49 */     return this.sequenceId > 2147483647L;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.Seq
 * JD-Core Version:    0.6.2
 */