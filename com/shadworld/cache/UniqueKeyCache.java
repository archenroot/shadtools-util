/*     */ package com.shadworld.cache;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class UniqueKeyCache<K, T>
/*     */ {
/*  10 */   long objectFlushTimeout = -1L;
/*     */   Thread flushThread;
/*  13 */   protected long currentKey = 0L;
/*  14 */   private HashMap<K, UniqueKeyCache<K, T>.Entry<T>> cache = new HashMap();
/*  15 */   private HashMap<T, K> reverse = new HashMap();
/*     */ 
/*     */   public K add(T t) {
/*  18 */     synchronized (this) {
/*  19 */       Object key = this.reverse.get(t);
/*  20 */       if (key != null)
/*  21 */         return key;
/*  22 */       key = getUniqueKey();
/*  23 */       this.cache.put(key, new Entry(t));
/*  24 */       this.reverse.put(t, key);
/*  25 */       return key;
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<K> add(Collection<T> ts) {
/*  30 */     List keys = new ArrayList();
/*  31 */     synchronized (this) {
/*  32 */       for (Object t : ts)
/*  33 */         keys.add(add(t));
/*     */     }
/*  35 */     return keys;
/*     */   }
/*     */ 
/*     */   public T get(K key) {
/*  39 */     Entry entry = (Entry)this.cache.get(key);
/*  40 */     return entry == null ? null : entry.getObject();
/*     */   }
/*     */ 
/*     */   public List<T> get(Collection<K> keys) {
/*  44 */     List ts = new ArrayList();
/*  45 */     synchronized (this) {
/*  46 */       for (Object k : keys)
/*  47 */         ts.add(get(k));
/*     */     }
/*  49 */     return ts;
/*     */   }
/*     */ 
/*     */   public K getKeyFor(T t) {
/*  53 */     return this.reverse.get(t);
/*     */   }
/*     */ 
/*     */   public List<K> getKeysFor(Collection<T> ts) {
/*  57 */     List keys = new ArrayList();
/*  58 */     synchronized (this) {
/*  59 */       for (Object t : ts)
/*  60 */         keys.add(this.reverse.get(t));
/*     */     }
/*  62 */     return keys;
/*     */   }
/*     */ 
/*     */   public K removeObject(T t) {
/*  66 */     synchronized (this) {
/*  67 */       Object key = this.reverse.remove(t);
/*  68 */       if (key != null) {
/*  69 */         this.cache.remove(key);
/*     */       }
/*  71 */       return key;
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<K> removeObjects(Collection<T> ts) {
/*  76 */     List keys = new ArrayList();
/*  77 */     synchronized (this) {
/*  78 */       for (Object t : ts)
/*  79 */         keys.add(removeObject(t));
/*     */     }
/*  81 */     return keys;
/*     */   }
/*     */ 
/*     */   public T remove(K key)
/*     */   {
/*  86 */     synchronized (this) {
/*  87 */       Entry e = (Entry)this.cache.remove(key);
/*  88 */       if (e != null) {
/*  89 */         this.reverse.remove(e.getObject());
/*  90 */         return e.getObject();
/*     */       }
/*     */     }
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */   public List<T> removeAll(Collection<K> keys) {
/*  97 */     List ts = new ArrayList();
/*  98 */     synchronized (this) {
/*  99 */       for (Object k : keys)
/* 100 */         ts.add(remove(k));
/*     */     }
/* 102 */     return ts;
/*     */   }
/*     */ 
/*     */   public void startFlushThread(final long interval) {
/* 106 */     if (this.flushThread != null)
/* 107 */       return;
/* 108 */     this.flushThread = new Thread("cache-flush-thread") {
/*     */       public void run() {
/* 110 */         long lastRun = 0L;
/* 111 */         boolean stop = false;
/* 112 */         while (!stop) {
/* 113 */           UniqueKeyCache.this.flushAll();
/*     */           try {
/* 115 */             Thread.sleep(interval);
/*     */           } catch (InterruptedException e) {
/* 117 */             stop = true;
/*     */           }
/*     */         }
/*     */       }
/*     */     };
/* 122 */     this.flushThread.start();
/*     */   }
/*     */ 
/*     */   public void stopFlushThread() {
/* 126 */     if (this.flushThread == null)
/* 127 */       return;
/* 128 */     this.flushThread.interrupt();
/*     */   }
/*     */ 
/*     */   public void flushAll() {
/* 132 */     synchronized (this) {
/* 133 */       for (Entry e : this.cache.values())
/* 134 */         flushObjectIfNeeded(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void flushObjectIfNeeded(UniqueKeyCache<K, T>.Entry<T> e) {
/* 139 */     if (isFlushable(e))
/* 140 */       removeObject(e.getObject());
/*     */   }
/*     */ 
/*     */   protected boolean isFlushable(UniqueKeyCache<K, T>.Entry<T> e) {
/* 144 */     if (this.objectFlushTimeout == -1L)
/* 145 */       return false;
/* 146 */     return (e == null) || (e.lastAccessTime < System.currentTimeMillis() - this.objectFlushTimeout);
/*     */   }
/*     */ 
/*     */   protected abstract K getUniqueKey();
/*     */ 
/*     */   protected Long getLongKey() {
/* 152 */     if (this.currentKey == 9223372036854775807L)
/* 153 */       throw new IllegalStateException("Cache has exceeded size limit of Long.MAX_VALUE");
/* 154 */     return Long.valueOf(this.currentKey++);
/*     */   }
/*     */ 
/*     */   public int size() {
/* 158 */     return this.cache.size();
/*     */   }
/*     */ 
/*     */   public long getObjectFlushTimeout()
/*     */   {
/* 165 */     return this.objectFlushTimeout;
/*     */   }
/*     */ 
/*     */   public void setObjectFlushTimeout(long objectFlushTimeout)
/*     */   {
/* 173 */     this.objectFlushTimeout = objectFlushTimeout;
/*     */   }
/*     */   protected class Entry<T> {
/*     */     private T t;
/*     */     long createTime;
/*     */     long lastAccessTime;
/*     */ 
/* 182 */     public Entry() { this(t, System.currentTimeMillis()); }
/*     */ 
/*     */ 
/*     */     public Entry(long t)
/*     */     {
/* 187 */       this.t = t;
/* 188 */       this.createTime = createTime;
/* 189 */       this.lastAccessTime = System.currentTimeMillis();
/*     */     }
/*     */ 
/*     */     public T getObject() {
/* 193 */       this.lastAccessTime = System.currentTimeMillis();
/* 194 */       return this.t;
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.cache.UniqueKeyCache
 * JD-Core Version:    0.6.2
 */