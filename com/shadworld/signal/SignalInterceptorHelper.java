/*    */ package com.shadworld.signal;
/*    */ 
/*    */ import sun.misc.Signal;
/*    */ import sun.misc.SignalHandler;
/*    */ 
/*    */ final class SignalInterceptorHelper
/*    */   implements SignalHandler
/*    */ {
/*    */   private final SignalHandler oldHandler;
/*    */   private final SignalInterceptor interceptor;
/*    */ 
/*    */   SignalInterceptorHelper(String signame, SignalInterceptor interceptor)
/*    */   {
/* 45 */     this.interceptor = interceptor;
/* 46 */     Signal signal = new Signal(signame);
/*    */ 
/* 48 */     this.oldHandler = Signal.handle(signal, this);
/*    */   }
/*    */ 
/*    */   public void handle(Signal sig) {
/* 52 */     if ((this.interceptor.handle(sig.getName())) && (this.oldHandler != null))
/* 53 */       this.oldHandler.handle(sig);
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.signal.SignalInterceptorHelper
 * JD-Core Version:    0.6.2
 */