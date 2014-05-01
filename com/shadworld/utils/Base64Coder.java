/*     */ package com.shadworld.utils;
/*     */ 
/*     */ public class Base64Coder
/*     */ {
/*  44 */   private static char[] map1 = new char[64];
/*     */   private static byte[] map2;
/*     */ 
/*     */   static
/*     */   {
/*  46 */     int i = 0;
/*  47 */     for (char c = 'A'; c <= 'Z'; c = (char)(c + '\001')) map1[(i++)] = c;
/*  48 */     for (char c = 'a'; c <= 'z'; c = (char)(c + '\001')) map1[(i++)] = c;
/*  49 */     for (char c = '0'; c <= '9'; c = (char)(c + '\001')) map1[(i++)] = c;
/*  50 */     map1[(i++)] = '+'; map1[(i++)] = '/';
/*     */ 
/*  53 */     map2 = new byte[''];
/*     */ 
/*  55 */     for (int i = 0; i < map2.length; i++) map2[i] = -1;
/*  56 */     for (int i = 0; i < 64; i++) map2[map1[i]] = ((byte)i);
/*     */   }
/*     */ 
/*     */   public static String encodeString(String s)
/*     */   {
/*  65 */     return new String(encode(s.getBytes()));
/*     */   }
/*     */ 
/*     */   public static char[] encode(byte[] in)
/*     */   {
/*  74 */     return encode(in, in.length);
/*     */   }
/*     */ 
/*     */   public static char[] encode(byte[] in, int iLen)
/*     */   {
/*  84 */     int oDataLen = (iLen * 4 + 2) / 3;
/*  85 */     int oLen = (iLen + 2) / 3 * 4;
/*  86 */     char[] out = new char[oLen];
/*  87 */     int ip = 0;
/*  88 */     for (int op = 0; 
/*  89 */       ip < iLen; 
/* 100 */       op++)
/*     */     {
/*  90 */       int i0 = in[(ip++)] & 0xFF;
/*  91 */       int i1 = ip < iLen ? in[(ip++)] & 0xFF : 0;
/*  92 */       int i2 = ip < iLen ? in[(ip++)] & 0xFF : 0;
/*  93 */       int o0 = i0 >>> 2;
/*  94 */       int o1 = (i0 & 0x3) << 4 | i1 >>> 4;
/*  95 */       int o2 = (i1 & 0xF) << 2 | i2 >>> 6;
/*  96 */       int o3 = i2 & 0x3F;
/*  97 */       out[(op++)] = map1[o0];
/*  98 */       out[(op++)] = map1[o1];
/*  99 */       out[op] = (op < oDataLen ? map1[o2] : '='); op++;
/* 100 */       out[op] = (op < oDataLen ? map1[o3] : '=');
/* 101 */     }return out;
/*     */   }
/*     */ 
/*     */   public static String decodeString(String s)
/*     */   {
/* 110 */     return new String(decode(s));
/*     */   }
/*     */ 
/*     */   public static byte[] decode(String s)
/*     */   {
/* 119 */     return decode(s.toCharArray());
/*     */   }
/*     */ 
/*     */   public static byte[] decode(char[] in)
/*     */   {
/* 129 */     int iLen = in.length;
/* 130 */     if (iLen % 4 != 0) throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
/* 131 */     while ((iLen > 0) && (in[(iLen - 1)] == '=')) iLen--;
/* 132 */     int oLen = iLen * 3 / 4;
/* 133 */     byte[] out = new byte[oLen];
/* 134 */     int ip = 0;
/* 135 */     int op = 0;
/* 136 */     while (ip < iLen) {
/* 137 */       int i0 = in[(ip++)];
/* 138 */       int i1 = in[(ip++)];
/* 139 */       int i2 = ip < iLen ? in[(ip++)] : 65;
/* 140 */       int i3 = ip < iLen ? in[(ip++)] : 65;
/* 141 */       if ((i0 > 127) || (i1 > 127) || (i2 > 127) || (i3 > 127))
/* 142 */         throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
/* 143 */       int b0 = map2[i0];
/* 144 */       int b1 = map2[i1];
/* 145 */       int b2 = map2[i2];
/* 146 */       int b3 = map2[i3];
/* 147 */       if ((b0 < 0) || (b1 < 0) || (b2 < 0) || (b3 < 0))
/* 148 */         throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
/* 149 */       int o0 = b0 << 2 | b1 >>> 4;
/* 150 */       int o1 = (b1 & 0xF) << 4 | b2 >>> 2;
/* 151 */       int o2 = (b2 & 0x3) << 6 | b3;
/* 152 */       out[(op++)] = ((byte)o0);
/* 153 */       if (op < oLen) out[(op++)] = ((byte)o1);
/* 154 */       if (op < oLen) out[(op++)] = ((byte)o2); 
/*     */     }
/* 155 */     return out;
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.Base64Coder
 * JD-Core Version:    0.6.2
 */