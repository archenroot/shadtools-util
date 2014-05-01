/*    */ package com.shadworld.struct.transform;
/*    */ 
/*    */ public class CurrencyStripToDecimal extends DataTransformer
/*    */ {
/*    */   public String transform(Object currencyObj)
/*    */   {
/* 20 */     String currencyString = "";
/* 21 */     if (currencyObj != null) {
/* 22 */       currencyString = currencyObj.toString();
/*    */     }
/* 24 */     currencyString = currencyString.replaceAll("[^\\d\\.]+", "");
/* 25 */     if (currencyString.equals("")) {
/* 26 */       currencyString = "0";
/*    */     }
/* 28 */     return currencyString;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.struct.transform.CurrencyStripToDecimal
 * JD-Core Version:    0.6.2
 */