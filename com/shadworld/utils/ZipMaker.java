/*     */ package com.shadworld.utils;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ 
/*     */ public class ZipMaker
/*     */ {
/*  12 */   public static int BUFFER_SIZE = 10240;
/*  13 */   private int compressionLevel = 9;
/*  14 */   private boolean showResults = false;
/*     */ 
/*     */   public void createZip(File archiveFile, File[] tobeZippedFiles)
/*     */   {
/*  19 */     createZip(archiveFile, tobeZippedFiles, 8);
/*     */   }
/*     */ 
/*     */   public void createZip(File archiveFile, ArrayList<File> tobeZippedFiles, int zipMethod) {
/*  23 */     File[] files = new File[tobeZippedFiles.size()];
/*  24 */     for (int i = 0; i < files.length; i++) {
/*  25 */       files[i] = ((File)tobeZippedFiles.get(i));
/*     */     }
/*  27 */     createZip(archiveFile, files, zipMethod);
/*     */   }
/*     */ 
/*     */   public void createZip(File archiveFile, ArrayList<File> tobeZippedFiles) {
/*  31 */     createZip(archiveFile, tobeZippedFiles, 8);
/*     */   }
/*     */ 
/*     */   public void createZip(File archiveFile, File[] tobeZippedFiles, int zipMethod) {
/*     */     try {
/*  36 */       byte[] buffer = new byte[BUFFER_SIZE];
/*     */ 
/*  38 */       FileOutputStream stream = new FileOutputStream(archiveFile);
/*  39 */       ZipOutputStream out = new ZipOutputStream(stream);
/*  40 */       out.setMethod(zipMethod);
/*  41 */       out.setLevel(this.compressionLevel);
/*     */ 
/*  43 */       ArrayList doneFiles = new ArrayList();
/*     */ 
/*  45 */       for (int i = 0; i < tobeZippedFiles.length; i++) {
/*  46 */         if ((tobeZippedFiles[i] != null) && (tobeZippedFiles[i].exists()) && 
/*  47 */           (!tobeZippedFiles[i].isDirectory()) && (!tobeZippedFiles[i].equals(archiveFile)))
/*     */         {
/*  50 */           if (!doneFiles.contains(tobeZippedFiles[i])) {
/*  51 */             if (this.showResults) {
/*  52 */               System.out.println("Adding " + tobeZippedFiles[i].getName());
/*     */             }
/*     */ 
/*  56 */             ZipEntry zipAdd = new ZipEntry(tobeZippedFiles[i].getName());
/*  57 */             zipAdd.setTime(tobeZippedFiles[i].lastModified());
/*  58 */             out.putNextEntry(zipAdd);
/*  59 */             doneFiles.add(tobeZippedFiles[i]);
/*     */ 
/*  62 */             FileInputStream in = new FileInputStream(tobeZippedFiles[i]);
/*     */             while (true) {
/*  64 */               int nRead = in.read(buffer, 0, buffer.length);
/*  65 */               if (nRead <= 0) {
/*     */                 break;
/*     */               }
/*  68 */               out.write(buffer, 0, nRead);
/*     */             }
/*  70 */             in.close();
/*     */           }
/*     */         }
/*     */       }
/*  74 */       out.close();
/*  75 */       stream.close();
/*  76 */       if (this.showResults)
/*  77 */         System.out.println("Adding completed OK");
/*     */     } catch (Exception e) {
/*  79 */       e.printStackTrace();
/*  80 */       System.out.println("Error: " + e.getMessage());
/*  81 */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getCompressionLevel() {
/*  86 */     return this.compressionLevel;
/*     */   }
/*     */ 
/*     */   public void setCompressionLevel(int compressionLevel) {
/*  90 */     if (compressionLevel > 9)
/*  91 */       this.compressionLevel = 9;
/*  92 */     else if (compressionLevel < 0)
/*  93 */       this.compressionLevel = 0;
/*     */     else
/*  95 */       this.compressionLevel = compressionLevel;
/*     */   }
/*     */ 
/*     */   public boolean isShowResults() {
/*  99 */     return this.showResults;
/*     */   }
/*     */ 
/*     */   public void setShowResults(boolean showResults) {
/* 103 */     this.showResults = showResults;
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.ZipMaker
 * JD-Core Version:    0.6.2
 */