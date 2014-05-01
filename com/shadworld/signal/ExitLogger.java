/*     */ package com.shadworld.signal;
/*     */ 
/*     */ import java.security.Permission;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class ExitLogger extends SecurityManager
/*     */ {
/*     */   private final Logger logger;
/*     */   private final Level level;
/*     */   private final SecurityManager parent;
/*     */ 
/*     */   public static void install(Logger logger, Level level)
/*     */   {
/*  62 */     new ExitLogger(logger, level);
/*     */   }
/*     */ 
/*     */   private ExitLogger(Logger logger, Level level)
/*     */   {
/*  70 */     if ((logger == null) || (level == null)) {
/*  71 */       throw new NullPointerException();
/*     */     }
/*  73 */     this.logger = logger;
/*  74 */     this.level = level;
/*  75 */     this.parent = System.getSecurityManager();
/*  76 */     System.setSecurityManager(this);
/*     */   }
/*     */ 
/*     */   public void checkPermission(Permission perm)
/*     */   {
/*  83 */     if (this.parent != null)
/*  84 */       this.parent.checkPermission(perm);
/*     */   }
/*     */ 
/*     */   public void checkExit(int status)
/*     */   {
/*  94 */     Exception e = new Exception();
/*  95 */     StackTraceElement[] trace = e.getStackTrace();
/*  96 */     String method = "<unknown>";
/*  97 */     if ((trace != null) && (trace.length > 2)) {
/*  98 */       int idx = 2;
/*     */ 
/* 102 */       if ((trace.length > 3) && 
/* 103 */         ("java.lang.System".equals(trace[2].getClassName())) && 
/* 104 */         ("exit".equals(trace[2].getMethodName()))) {
/* 105 */         idx++;
/*     */       }
/* 107 */       method = trace[idx].toString();
/*     */     }
/* 109 */     this.logger.log(this.level, "exit({0}) called at {1}", new Object[] { 
/* 110 */       new Integer(status), method });
/* 111 */     super.checkExit(status);
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.signal.ExitLogger
 * JD-Core Version:    0.6.2
 */