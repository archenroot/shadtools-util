/*    */ package com.shadworld.utils;
/*    */ 
/*    */ import java.math.BigDecimal;
/*    */ import java.math.BigInteger;
/*    */ 
/*    */ public class EndianTools
/*    */ {
/*    */   public static void main(String[] args)
/*    */   {
/*  9 */     String d1 = "0000000000000000000000000000000000000000000000000000ffff00000000";
/* 10 */     String le = "ffffffffffffffffffffffffffffffffffffffffffffffffffffffff00000000";
/*    */ 
/* 15 */     String be = "00000000ffff0000000000000000000000000000000000000000000000000000";
/*    */ 
/* 17 */     int blocksize = 1;
/* 18 */     L.println(le);
/* 19 */     L.println(swapHexString(le, blocksize));
/* 20 */     L.println(be);
/* 21 */     L.println(swapHexString(be, blocksize));
/*    */ 
/* 23 */     BigInteger big = new BigInteger(le, 16);
/*    */ 
/* 25 */     big = new BigInteger(swapHexString(le, 1), 16);
/* 26 */     L.println(big.toString());
/* 27 */     BigInteger big2 = new BigInteger(swapHexString(d1, 1), 16);
/* 28 */     L.println(big2.toString());
/* 29 */     L.println(Double.valueOf(1.0D / new BigDecimal(big).divide(new BigDecimal(big2)).doubleValue()));
/*    */ 
/* 31 */     big = new BigInteger(le, 16);
/* 32 */     L.println(big.toString());
/* 33 */     big2 = new BigInteger(d1, 16);
/* 34 */     L.println(big2.toString());
/* 35 */     L.println(new BigDecimal(big).divide(new BigDecimal(big2)));
/*    */   }
/*    */ 
/*    */   public static String swapHexString(String hex, int blocksize, StringBuilder sb)
/*    */   {
/* 47 */     blocksize *= 2;
/* 48 */     if (hex.length() % blocksize != 0)
/* 49 */       throw new IllegalArgumentException("hex string length must be a multiple of blocksize*2");
/* 50 */     int start = sb.length();
/* 51 */     for (int i = hex.length() - blocksize; i >= 0; i -= blocksize) {
/* 52 */       sb.append(hex.substring(i, i + blocksize));
/*    */     }
/* 54 */     return start > 0 ? sb.substring(start) : sb.toString();
/*    */   }
/*    */ 
/*    */   public static String swapHexString(String hex, int blocksize)
/*    */   {
/* 64 */     return swapHexString(hex, blocksize, new StringBuilder());
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.EndianTools
 * JD-Core Version:    0.6.2
 */