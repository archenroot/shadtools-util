/*    */ package com.shadworld.struct;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class ColumnComparator
/*    */   implements Comparator<Record>
/*    */ {
/*    */   private String col;
/*    */ 
/*    */   public ColumnComparator(String columnKey)
/*    */   {
/* 11 */     this.col = columnKey;
/*    */   }
/*    */ 
/*    */   public int compare(Record o1, Record o2)
/*    */   {
/* 16 */     if (o1 == null) {
/* 17 */       if (o2 == null)
/* 18 */         return 0;
/* 19 */       return -1;
/*    */     }
/* 21 */     if (o2 == null)
/* 22 */       return 1;
/* 23 */     String s1 = (String)o1.get(this.col);
/* 24 */     String s2 = (String)o2.get(this.col);
/* 25 */     if (s1 == null) {
/* 26 */       if (s2 == null)
/* 27 */         return 0;
/* 28 */       return -1;
/*    */     }
/* 30 */     if (s2 == null)
/* 31 */       return 1;
/* 32 */     Double d1 = null;
/* 33 */     Double d2 = null;
/*    */     try {
/* 35 */       d1 = Double.valueOf(Double.parseDouble(s1)); } catch (Exception localException) {
/*    */     }
/*    */     try {
/* 38 */       d2 = Double.valueOf(Double.parseDouble(s2));
/*    */     } catch (Exception localException1) {
/*    */     }
/* 41 */     if (d1 == null) {
/* 42 */       if (d2 == null)
/* 43 */         return 0;
/* 44 */       return -1;
/*    */     }
/* 46 */     if (d2 == null) {
/* 47 */       return 1;
/*    */     }
/* 49 */     if (d1.doubleValue() < d2.doubleValue())
/* 50 */       return -1;
/* 51 */     if (d1.doubleValue() > d2.doubleValue())
/* 52 */       return 1;
/* 53 */     return 0;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.struct.ColumnComparator
 * JD-Core Version:    0.6.2
 */