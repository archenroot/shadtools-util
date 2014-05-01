/*    */ package com.shadworld.signal;
/*    */ 
/*    */ public class SignalInterceptorException extends Exception
/*    */ {
/*    */   SignalInterceptorException(String signal, Throwable cause)
/*    */   {
/* 30 */     super("Unable to register for SIG" + signal, cause);
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.signal.SignalInterceptorException
 * JD-Core Version:    0.6.2
 */