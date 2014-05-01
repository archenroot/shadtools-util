/*     */ package com.shadworld.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import org.apache.log4j.BasicConfigurator;
/*     */ import org.apache.log4j.FileAppender;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.PatternLayout;
/*     */ import org.apache.log4j.spi.Filter;
/*     */ import org.apache.log4j.spi.LoggerRepository;
/*     */ import org.apache.log4j.spi.LoggingEvent;
/*     */ 
/*     */ public class L
/*     */ {
/*  44 */   public static Logger log = Logger.getLogger(L.class);
/*     */ 
/*     */   static
/*     */   {
/*  40 */     BasicConfigurator.configure();
/*     */ 
/*  42 */     Logger.getRootLogger().getLoggerRepository().getLogger("org.apache.commons.beanutils.converters").setLevel(Level.INFO);
/*     */   }
/*     */ 
/*     */   public static void filterHibernate(Level logLevel)
/*     */   {
/*  50 */     Logger.getRootLogger().getLoggerRepository().getLogger("org.hibernate").setLevel(logLevel);
/*     */   }
/*     */ 
/*     */   public static void logToFile(Level logLevel) {
/*  54 */     logToFile("c:/java.log", logLevel);
/*     */   }
/*     */ 
/*     */   public static void logToFile(String path, Level logLevel) {
/*  58 */     FileAppender fileApp = null;
/*     */     try {
/*  60 */       fileApp = new FileAppender(new PatternLayout("%r [%t] %p %c %x - %m%n"), path);
/*     */     } catch (IOException localIOException) {
/*     */     }
/*  63 */     fileApp.addFilter(new Filter()
/*     */     {
/*     */       public int decide(LoggingEvent event) {
/*  66 */         return event.getLevel().isGreaterOrEqual(L.this) ? 1 : -1;
/*     */       }
/*     */     });
/*  69 */     BasicConfigurator.configure(fileApp);
/*     */   }
/*     */ 
/*     */   public static void println(Object o) {
/*  73 */     System.out.println(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void printlnErr(Object o) {
/*  77 */     System.err.println(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void print(Object o) {
/*  81 */     System.out.print(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void printErr(Object o) {
/*  85 */     System.err.print(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void format(Object o, Object[] args) {
/*  89 */     System.out.format(String.valueOf(o), args);
/*     */   }
/*     */ 
/*     */   public static void formatErr(Object o, Object[] args) {
/*  93 */     System.err.format(String.valueOf(o), new Object[0]);
/*     */   }
/*     */ 
/*     */   public static void trace(Object o, Throwable t) {
/*  97 */     log.trace(String.valueOf(o), t);
/*     */   }
/*     */ 
/*     */   public static void trace(Object o) {
/* 101 */     log.trace(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void trace(Object thisO, Object o) {
/* 105 */     Logger.getLogger(thisO.getClass()).trace(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void debug(Object thisO, Object o, Throwable t) {
/* 109 */     Logger.getLogger(thisO.getClass()).debug(String.valueOf(o), t);
/*     */   }
/*     */ 
/*     */   public static void debug(Object thisO, Object o) {
/* 113 */     Logger.getLogger(thisO.getClass()).debug(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void debug(Object o) {
/* 117 */     log.debug(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void info(Object o, Throwable t) {
/* 121 */     log.info(String.valueOf(o), t);
/*     */   }
/*     */ 
/*     */   public static void info(Object thisO, Object o, Throwable t) {
/* 125 */     Logger.getLogger(thisO.getClass()).info(String.valueOf(o), t);
/*     */   }
/*     */ 
/*     */   public static void info(Object thisO, Object o) {
/* 129 */     Logger.getLogger(thisO.getClass()).info(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void info(Object o) {
/* 133 */     log.info(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void warn(Object o, Throwable t) {
/* 137 */     log.warn(String.valueOf(o), t);
/*     */   }
/*     */ 
/*     */   public static void warn(Object thisO, Object o) {
/* 141 */     Logger.getLogger(thisO.getClass()).warn(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void warn(Object thisO, Object o, Throwable t) {
/* 145 */     Logger.getLogger(thisO.getClass()).warn(String.valueOf(o), t);
/*     */   }
/*     */ 
/*     */   public static void warn(Object o) {
/* 149 */     log.warn(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void error(Object thisO, Object o, Throwable t) {
/* 153 */     Logger.getLogger(thisO.getClass()).error(String.valueOf(o), t);
/*     */   }
/*     */ 
/*     */   public static void error(Object thisO, Object o) {
/* 157 */     Logger.getLogger(thisO.getClass()).error(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void error(Object o, Throwable t) {
/* 161 */     log.error(String.valueOf(o), t);
/*     */   }
/*     */ 
/*     */   public static void error(Object o) {
/* 165 */     log.error(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void fatal(Object thisO, Object o, Throwable t) {
/* 169 */     Logger.getLogger(thisO.getClass()).fatal(String.valueOf(o), t);
/*     */   }
/*     */ 
/*     */   public static void fatal(Object thisO, Object o) {
/* 173 */     Logger.getLogger(thisO.getClass()).fatal(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void fatal(Object o, Throwable t) {
/* 177 */     log.fatal(String.valueOf(o), t);
/*     */   }
/*     */ 
/*     */   public static void fatal(Object o) {
/* 181 */     log.fatal(String.valueOf(o));
/*     */   }
/*     */ 
/*     */   public static void pause(int millis) {
/*     */     try {
/* 186 */       Thread.sleep(millis);
/*     */     }
/*     */     catch (InterruptedException localInterruptedException)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.L
 * JD-Core Version:    0.6.2
 */