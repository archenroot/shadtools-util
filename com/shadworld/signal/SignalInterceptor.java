/*     */ package com.shadworld.signal;
/*     */ 
/*     */ public abstract class SignalInterceptor
/*     */ {
/*     */   protected void register(String signame)
/*     */     throws SignalInterceptorException
/*     */   {
/*     */     try
/*     */     {
/*  98 */       new SignalInterceptorHelper(signame, this);
/*     */     } catch (Throwable e) {
/* 100 */       throw new SignalInterceptorException(signame, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean registerQuietly(String signame)
/*     */   {
/*     */     try
/*     */     {
/* 111 */       register(signame);
/*     */     } catch (Throwable e) {
/* 113 */       return false;
/*     */     }
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */   protected abstract boolean handle(String paramString);
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.signal.SignalInterceptor
 * JD-Core Version:    0.6.2
 */