/*    */ package com.shadworld.utils;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ 
/*    */ public class CryptUtils
/*    */ {
/* 11 */   public static String DEFAULT_SALT = "rXMxBmTYdWXYyIH7NGT4KSlkh5bFVOs0kNRHntuOKRb";
/*    */ 
/*    */   public static String MD5EncryptString(String string, String salt) {
/* 14 */     if (salt == null)
/* 15 */       salt = DEFAULT_SALT;
/* 16 */     int len = 12;
/* 17 */     if (string == null)
/* 18 */       return null;
/* 19 */     if (salt != null) {
/* 20 */       if (salt.length() > len)
/* 21 */         salt = salt.substring(salt.length() - len);
/* 22 */       else if (salt.length() < len) {
/* 23 */         salt = salt + StringUtils.repeat("s", len - salt.length());
/*    */       }
/*    */     }
/*    */     try
/*    */     {
/* 28 */       MessageDigest algorithm = MessageDigest.getInstance("MD5");
/* 29 */       algorithm.reset();
/* 30 */       salt = salt + string;
/* 31 */       algorithm.update(salt.getBytes());
/*    */ 
/* 33 */       byte[] messageDigest = algorithm.digest();
/* 34 */       String hash = new BigInteger(1, messageDigest)
/* 35 */         .toString(16);
/*    */ 
/* 37 */       StringBuffer hexString = new StringBuffer();
/* 38 */       for (int i = 0; i < messageDigest.length; i++) {
/* 39 */         hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
/*    */       }
/*    */ 
/* 42 */       return hexString.toString();
/*    */     }
/*    */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
/*    */     }
/* 46 */     return null;
/*    */   }
/*    */ 
/*    */   public static void main(String[] args) {
/* 50 */     MD5EncryptString("fasheeeee", "cuuFjlbb");
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.CryptUtils
 * JD-Core Version:    0.6.2
 */