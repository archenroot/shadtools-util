/*    */ package com.shadworld.cache;
/*    */ 
/*    */ import java.util.ArrayDeque;
/*    */ 
/*    */ public class ArrayDequeResourcePool<R>
/*    */ {
/*    */   final ArrayDeque<R> pool;
/*    */   final int maxSize;
/*    */   final boolean hasMax;
/* 17 */   int hits = 1;
/* 18 */   double total = 1.0D;
/*    */ 
/*    */   public ArrayDequeResourcePool(int startSize, int maxSize)
/*    */   {
/* 27 */     this.pool = new ArrayDeque(startSize);
/* 28 */     this.maxSize = maxSize;
/* 29 */     this.hasMax = (maxSize <= 0);
/*    */   }
/*    */ 
/*    */   public R getResource()
/*    */   {
/*    */     Object r;
/* 40 */     synchronized (this.pool) {
/* 41 */       r = this.pool.pollFirst();
/*    */     }
/*    */     Object r;
/* 43 */     this.total += 1.0D;
/* 44 */     if (r != null)
/* 45 */       this.hits += 1;
/* 46 */     return r;
/*    */   }
/*    */ 
/*    */   public void addResource(R r)
/*    */   {
/* 55 */     synchronized (this.pool) {
/* 56 */       this.pool.add(r);
/*    */     }
/*    */   }
/*    */ 
/*    */   public void returnResource(R r) {
/* 61 */     if ((this.hasMax) && (this.pool.size() >= this.maxSize))
/* 62 */       return;
/* 63 */     synchronized (this.pool) {
/* 64 */       this.pool.add(r);
/*    */     }
/*    */   }
/*    */ 
/*    */   public double hitRate()
/*    */   {
/* 76 */     return this.hits / this.total;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.cache.ArrayDequeResourcePool
 * JD-Core Version:    0.6.2
 */