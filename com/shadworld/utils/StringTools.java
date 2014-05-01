/*     */ package com.shadworld.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class StringTools
/*     */ {
/*     */   public static String concatStrings(Collection<? extends Object> col, String delimiter)
/*     */   {
/*  17 */     return concatStrings(col, delimiter, "");
/*     */   }
/*     */ 
/*     */   public static String concatStrings(Collection<? extends Object> col, String delimiter, String encloser) {
/*  21 */     StringBuilder sb = new StringBuilder();
/*  22 */     for (Iterator i = col.iterator(); i.hasNext(); ) {
/*  23 */       sb.append(encloser);
/*  24 */       sb.append(String.valueOf(i.next()));
/*  25 */       sb.append(encloser);
/*  26 */       if ((i.hasNext()) && (delimiter != null))
/*  27 */         sb.append(delimiter);
/*     */     }
/*  29 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String concatStrings(Object[] col, String delimiter) {
/*  33 */     return concatStrings(Arrays.asList(col), delimiter);
/*     */   }
/*     */ 
/*     */   public static List<String> split(String string, String pattern)
/*     */   {
/*  43 */     return split(string, pattern, true, false);
/*     */   }
/*     */ 
/*     */   public static List<String> split(String string, String pattern, boolean trim, boolean includeEmpty) {
/*  47 */     if ((string == null) || (pattern == null))
/*  48 */       return new ArrayList();
/*  49 */     String[] parts = string.split(pattern);
/*  50 */     ArrayList list = new ArrayList(parts.length);
/*  51 */     for (int i = 0; i < parts.length; i++) {
/*  52 */       String part = parts[i];
/*  53 */       if (part != null) {
/*  54 */         if (trim)
/*  55 */           part = part.trim();
/*  56 */         if ((!part.isEmpty()) || (includeEmpty)) {
/*  57 */           list.add(part);
/*     */         }
/*     */       }
/*     */     }
/*  61 */     return list;
/*     */   }
/*     */ 
/*     */   public static String[] splitToArray(String string, String pattern, boolean trim, boolean includeEmpty) {
/*  65 */     List list = split(string, pattern, trim, includeEmpty);
/*  66 */     return (String[])list.toArray(new String[list.size()]);
/*     */   }
/*     */ 
/*     */   public static List<String> splitCamelCase(String string) {
/*  70 */     List list = new ArrayList();
/*  71 */     if (string == null)
/*  72 */       return list;
/*  73 */     Pattern camelPattern = Pattern.compile("([a-z])([A-Z]*)([A-Z])");
/*  74 */     Matcher m = camelPattern.matcher(string);
/*  75 */     StringBuffer sb = new StringBuffer();
/*  76 */     while (m.find()) {
/*  77 */       m.appendReplacement(sb, m.group(1));
/*  78 */       list.add(sb.toString());
/*  79 */       sb.setLength(0);
/*  80 */       String group2 = m.group(2);
/*  81 */       String group3 = m.group(3);
/*  82 */       if (m.end(3) == string.length()) {
/*  83 */         group2 = group2 + group3;
/*  84 */         group3 = "";
/*     */       }
/*  86 */       if ((group2 != null) && (!group2.isEmpty())) {
/*  87 */         list.add(group2);
/*     */       }
/*     */ 
/*  90 */       sb.append(group3);
/*     */     }
/*     */ 
/*  93 */     m.appendTail(sb);
/*  94 */     if (sb.length() > 0)
/*  95 */       list.add(sb.toString());
/*  96 */     return list;
/*     */   }
/*     */ 
/*     */   public static String toCamelCase(String string, String splitPattern) {
/* 100 */     return toCamelCase(split(string, splitPattern));
/*     */   }
/*     */   public static String toCamelCase(List<String> strings) {
/* 103 */     StringBuilder sb = new StringBuilder();
/* 104 */     for (int i = 0; i < strings.size(); i++) {
/* 105 */       String string = String.valueOf(strings.get(i));
/* 106 */       if (i != 0)
/* 107 */         sb.append(Character.toUpperCase(string.charAt(0)));
/*     */       else
/* 109 */         sb.append(Character.toLowerCase(string.charAt(0)));
/* 110 */       sb.append(string.substring(1));
/*     */     }
/*     */ 
/* 113 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String replaceAll(String string, String regex, String replacement, int flags) {
/* 117 */     return Pattern.compile(regex, flags).matcher(string).replaceAll(replacement);
/*     */   }
/*     */ 
/*     */   public static String replaceToken(String text, String token, String replacement)
/*     */   {
/* 130 */     return replaceAll(text, Pattern.quote("~@" + token + "@~"), replacement, 2);
/*     */   }
/*     */ 
/*     */   public static String wrap(CharSequence string, CharSequence encloser) {
/* 134 */     return encloser + string + encloser;
/*     */   }
/*     */ 
/*     */   public static String wrapCDATA(CharSequence string) {
/* 138 */     return "<![CDATA[" + string + "]]>";
/*     */   }
/*     */ 
/*     */   public static String wrap(CharSequence string, CharSequence prefix, CharSequence suffix) {
/* 142 */     return prefix + string + suffix;
/*     */   }
/*     */ 
/*     */   public static String wrap(CharSequence string, char encloser) {
/* 146 */     return encloser + string + encloser;
/*     */   }
/*     */ 
/*     */   public static boolean nullOrEmpty(String string) {
/* 150 */     return (string == null) || (string.isEmpty());
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.StringTools
 * JD-Core Version:    0.6.2
 */