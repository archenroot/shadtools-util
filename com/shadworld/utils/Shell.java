/*     */ package com.shadworld.utils;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class Shell
/*     */ {
/*  20 */   private static Shell instance = new Shell();
/*     */   Process proc;
/*     */   InputStream in;
/*     */   OutputStream out;
/*     */   InputStream err;
/*     */   PrintWriter print;
/*     */   BufferedReader read;
/*     */   BufferedReader readErr;
/*  39 */   private long commandResponseTimeout = 500L;
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IOException, InterruptedException
/*     */   {
/*  23 */     String cmd = "ls -l";
/*     */ 
/*  28 */     System.out.println(Bsh().command(cmd));
/*     */   }
/*     */ 
/*     */   public static Shell Cmd()
/*     */     throws IOException
/*     */   {
/*  42 */     return new Shell("cmd.exe");
/*     */   }
/*     */ 
/*     */   public static Shell Bsh()
/*     */     throws IOException
/*     */   {
/*     */     Shell tmp7_4 = instance; tmp7_4.getClass(); return new Bsh(null);
/*     */   }
/*     */ 
/*     */   private Shell() {
/*     */   }
/*     */ 
/*     */   public Shell(String shellCommand) throws IOException {
/*  59 */     this.proc = Runtime.getRuntime().exec(shellCommand);
/*  60 */     this.in = this.proc.getInputStream();
/*  61 */     this.out = this.proc.getOutputStream();
/*  62 */     this.err = this.proc.getErrorStream();
/*  63 */     this.print = new PrintWriter(this.out);
/*  64 */     this.read = new BufferedReader(new InputStreamReader(this.in));
/*  65 */     this.readErr = new BufferedReader(new InputStreamReader(this.err));
/*  66 */     eatInput();
/*     */   }
/*     */ 
/*     */   public String run(String cmd)
/*     */     throws IOException
/*     */   {
/*  76 */     return command(cmd);
/*     */   }
/*     */ 
/*     */   private String eatInput() throws IOException {
/*  80 */     StringBuilder sb = new StringBuilder();
/*     */ 
/*  82 */     long start = System.currentTimeMillis();
/*     */     do {
/*     */       do {
/*  85 */         String line = this.read.readLine();
/*  86 */         if (line != null)
/*  87 */           sb.append(line).append("\n");
/*  88 */         if (sb.length() == 0)
/*     */           try {
/*  90 */             Thread.sleep(1L);
/*     */           }
/*     */           catch (InterruptedException e) {
/*  93 */             e.printStackTrace();
/*     */           }
/*     */       }
/*  84 */       while (this.in.available() > 0); if (sb.length() != 0) break;  } while (System.currentTimeMillis() - start < this.commandResponseTimeout);
/*     */ 
/*  96 */     while ((this.err.available() > 0) || ((sb.length() == 0) && (System.currentTimeMillis() - start < this.commandResponseTimeout))) {
/*  97 */       String line = this.readErr.readLine();
/*  98 */       if (line != null)
/*  99 */         sb.append(line).append("\n");
/* 100 */       if (sb.length() == 0) {
/*     */         try {
/* 102 */           Thread.sleep(1L);
/*     */         }
/*     */         catch (InterruptedException e) {
/* 105 */           e.printStackTrace();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 113 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public String command(String command) throws IOException {
/* 117 */     this.print.println(command);
/* 118 */     this.print.flush();
/* 119 */     return eatInput();
/*     */   }
/*     */ 
/*     */   public int exit() throws InterruptedException {
/* 123 */     this.print.println("exit");
/* 124 */     this.print.flush();
/* 125 */     int returnVal = this.proc.waitFor();
/* 126 */     this.proc = null;
/* 127 */     return returnVal;
/*     */   }
/*     */ 
/*     */   public class Bsh extends Shell
/*     */   {
/*     */     private int shellExitStatus;
/*     */ 
/*     */     private Bsh()
/*     */     {
/* 140 */       super();
/*     */     }
/*     */ 
/*     */     public String command(String cmd) throws IOException
/*     */     {
/* 145 */       ProcessBuilder pb = new ProcessBuilder(new String[] { "bash", "-c", cmd });
/* 146 */       pb.redirectErrorStream(true);
/*     */ 
/* 149 */       Process shell = pb.start();
/* 150 */       InputStream shellIn = shell.getInputStream();
/*     */       try
/*     */       {
/* 160 */         this.shellExitStatus = shell.waitFor();
/*     */       } catch (InterruptedException e) {
/* 162 */         this.shellExitStatus = -1;
/* 163 */         return null;
/*     */       }
/*     */ 
/* 166 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*     */       int c;
/* 167 */       while ((c = shellIn.read()) != -1)
/*     */       {
/*     */         int c;
/* 168 */         bos.write(c);
/*     */       }
/*     */       try
/*     */       {
/* 172 */         shellIn.close();
/*     */       } catch (IOException localIOException) {
/*     */       }
/* 175 */       return new String(bos.toByteArray());
/*     */     }
/*     */ 
/*     */     public int exit() {
/* 179 */       return this.shellExitStatus;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.Shell
 * JD-Core Version:    0.6.2
 */