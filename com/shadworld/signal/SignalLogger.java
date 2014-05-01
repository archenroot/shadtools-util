/*    */ package com.shadworld.signal;
/*    */ 
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class SignalLogger extends SignalInterceptor
/*    */ {
/*    */   private final Logger logger;
/*    */   private final Level level;
/*    */ 
/*    */   public SignalLogger()
/*    */   {
/* 40 */     this(null, null);
/*    */   }
/*    */ 
/*    */   public SignalLogger(Logger logger, Level level)
/*    */   {
/* 51 */     if (logger == null) {
/* 52 */       logger = Logger.getLogger("");
/*    */     }
/* 54 */     if (level == null) {
/* 55 */       level = Level.INFO;
/*    */     }
/* 57 */     this.logger = logger;
/* 58 */     this.level = level;
/*    */   }
/*    */ 
/*    */   protected boolean handle(String signame)
/*    */   {
/* 65 */     this.logger.log(this.level, "Received signal: SIG{0}", signame);
/* 66 */     return true;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.signal.SignalLogger
 * JD-Core Version:    0.6.2
 */