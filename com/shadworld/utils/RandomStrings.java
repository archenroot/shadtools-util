/*    */ package com.shadworld.utils;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.Inet4Address;
/*    */ import java.net.Inet6Address;
/*    */ import java.net.InetAddress;
/*    */ import java.net.UnknownHostException;
/*    */ import org.apache.commons.lang.RandomStringUtils;
/*    */ 
/*    */ public class RandomStrings
/*    */ {
/*    */   public static String keyString(int size)
/*    */   {
/* 17 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 18 */     DataOutputStream dos = new DataOutputStream(bos);
/*    */     try {
/* 20 */       dos.writeLong(System.currentTimeMillis());
/* 21 */       dos.flush(); } catch (IOException localIOException) {
/*    */     }
/* 23 */     byte[] data = bos.toByteArray();
/* 24 */     String base = String.valueOf(Base64Coder.encode(data)).replace("=", "_").replace("/", "!");
/*    */ 
/* 26 */     if (base.length() > size) {
/* 27 */       return base;
/*    */     }
/* 29 */     return base + RandomStringUtils.randomAlphanumeric(size - base.length());
/*    */   }
/*    */ 
/*    */   public static String keyString(int size, String ip)
/*    */   {
/* 39 */     byte[] ipdata = new byte[0];
/* 40 */     char iptype = '0';
/*    */     try
/*    */     {
/* 43 */       InetAddress inet = InetAddress.getByName(ip);
/* 44 */       ipdata = inet.getAddress();
/* 45 */       if ((inet instanceof Inet4Address))
/* 46 */         iptype = '4';
/* 47 */       if ((inet instanceof Inet6Address))
/* 48 */         iptype = '6';
/*    */     }
/*    */     catch (UnknownHostException localUnknownHostException)
/*    */     {
/*    */     }
/* 53 */     String ipString = 
/* 54 */       iptype + String.valueOf(Base64Coder.encode(ipdata));
/*    */ 
/* 56 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 57 */     DataOutputStream dos = new DataOutputStream(bos);
/*    */     try {
/* 59 */       dos.writeLong(System.currentTimeMillis());
/*    */ 
/* 61 */       dos.flush(); } catch (IOException localIOException) {
/*    */     }
/* 63 */     byte[] data = bos.toByteArray();
/* 64 */     String base = (String.valueOf(Base64Coder.encode(data)) + ipString).replace("=", "_").replace("/", "!");
/*    */ 
/* 66 */     if (base.length() > size)
/* 67 */       return base;
/* 68 */     return base + RandomStringUtils.randomAlphanumeric(size - base.length());
/*    */   }
/*    */ 
/*    */   public static String streamToString(InputStream in) {
/* 72 */     StringBuffer out = new StringBuffer();
/* 73 */     byte[] b = new byte[4096];
/*    */     try
/*    */     {
/*    */       int n;
/* 75 */       while ((n = in.read(b)) != -1)
/*    */       {
/*    */         int n;
/* 76 */         out.append(new String(b, 0, n));
/*    */       }
/*    */     } catch (IOException e) {
/* 79 */       e.printStackTrace();
/*    */     }
/* 81 */     return out.toString();
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.RandomStrings
 * JD-Core Version:    0.6.2
 */