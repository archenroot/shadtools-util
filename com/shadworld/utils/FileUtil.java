/*     */ package com.shadworld.utils;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.Reader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class FileUtil
/*     */ {
/*     */   public static String getFileAsString(File file)
/*     */   {
/*  18 */     StringBuilder sb = null;
/*  19 */     Reader in = null;
/*     */     try {
/*  21 */       in = new BufferedReader(new FileReader(file));
/*  22 */       sb = new StringBuilder();
/*  23 */       char[] chars = new char[65536];
/*     */       int length;
/*  25 */       while ((length = in.read(chars)) > 0)
/*     */       {
/*     */         int length;
/*  26 */         sb.append(chars, 0, length);
/*     */       }
/*     */     } catch (Exception e) {
/*  29 */       e.printStackTrace();
/*  30 */       sb = null;
/*     */       try {
/*  32 */         in.close();
/*     */       } catch (Exception e1) {
/*  34 */         e1.printStackTrace();
/*     */       }
/*     */     }
/*  37 */     return sb == null ? null : sb.toString();
/*     */   }
/*     */ 
/*     */   public static String getFileAsString(String file) {
/*  41 */     return getFileAsString(new File(file));
/*     */   }
/*     */ 
/*     */   public static boolean appendStringToFile(String string, File outFile) {
/*  45 */     return saveStringAsFile(string, outFile, true);
/*     */   }
/*     */ 
/*     */   public static boolean appendStringToFile(String string, String outFile) {
/*  49 */     return saveStringAsFile(string, outFile, true);
/*     */   }
/*     */ 
/*     */   public static boolean saveStringAsFile(String string, File outFile) {
/*  53 */     return saveStringAsFile(string, outFile, false);
/*     */   }
/*     */ 
/*     */   public static boolean saveStringAsFile(String string, File outFile, boolean append) {
/*  57 */     FileWriter writer = null;
/*  58 */     boolean result = false;
/*     */     try
/*     */     {
/*  61 */       if (outFile.getParentFile() != null)
/*  62 */         outFile.getParentFile().mkdirs();
/*  63 */       writer = new FileWriter(outFile, append);
/*  64 */       writer.write(string);
/*  65 */       writer.close();
/*  66 */       result = true;
/*     */     }
/*     */     catch (IOException ex)
/*     */     {
/*  70 */       System.out.println(ex.getMessage());
/*     */       try
/*     */       {
/*  73 */         if (writer != null)
/*  74 */           writer.close();
/*     */       } catch (IOException ex) {
/*  76 */         Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try
/*     */       {
/*  73 */         if (writer != null)
/*  74 */           writer.close();
/*     */       } catch (IOException ex) {
/*  76 */         Logger.getLogger(FileUtil.class.getName()).log(Level.SEVERE, null, ex);
/*     */       }
/*     */     }
/*  79 */     return result;
/*     */   }
/*     */ 
/*     */   public static boolean saveStringAsFile(String string, String outFile) {
/*  83 */     return saveStringAsFile(string, new File(outFile));
/*     */   }
/*     */ 
/*     */   public static boolean saveStringAsFile(String string, String outFile, boolean append) {
/*  87 */     return saveStringAsFile(string, new File(outFile), append);
/*     */   }
/*     */ 
/*     */   public static List<File> getSubFiles(File file, FilenameFilter filter)
/*     */   {
/*  97 */     List fileList = new ArrayList();
/*     */ 
/*  99 */     File[] files = file.listFiles(filter);
/* 100 */     for (File f : files) {
/* 101 */       if (f.isDirectory())
/* 102 */         fileList.addAll(getSubFiles(f, filter));
/*     */       else {
/* 104 */         fileList.add(f);
/*     */       }
/*     */     }
/* 107 */     return fileList;
/*     */   }
/*     */ 
/*     */   public static List<File> getSubDirs(File file, FilenameFilter filter)
/*     */   {
/* 117 */     List fileList = new ArrayList();
/*     */ 
/* 119 */     File[] files = file.listFiles(filter);
/* 120 */     for (File f : files) {
/* 121 */       if (f.isDirectory()) {
/* 122 */         fileList.add(f);
/* 123 */         fileList.addAll(getSubDirs(f, filter));
/*     */       }
/*     */     }
/* 126 */     return fileList;
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.FileUtil
 * JD-Core Version:    0.6.2
 */