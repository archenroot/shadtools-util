/*     */ package com.shadworld.concurrent;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ 
/*     */ public class ParallelTaskRunner
/*     */ {
/*  52 */   private ConcurrentLinkedQueue<Thread> tasks = new ConcurrentLinkedQueue();
/*     */   private ArrayList<Thread> running;
/*  55 */   private int maxThreads = -1;
/*     */ 
/*  57 */   private int reservedSlot = -1;
/*     */ 
/*  59 */   private int pauseBetweenStarts = 0;
/*  60 */   private boolean firstStart = true;
/*     */ 
/*  62 */   private boolean submitInProgress = false;
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  12 */     ParallelTaskRunner runner = new ParallelTaskRunner(3);
/*  13 */     runner.reserveSlot();
/*  14 */     for (int i = 0; i < 5; i++) {
/*  15 */       int j = i + 1;
/*  16 */       int idx = runner.submitAndBlock(new Thread()
/*     */       {
/*     */         public void run()
/*     */         {
/*  20 */           System.out.println("sleeping for " + this.val$j + " secs");
/*     */           try {
/*  22 */             Thread.sleep(this.val$j * 1000L);
/*     */           } catch (InterruptedException localInterruptedException) {
/*     */           }
/*  25 */           System.out.println(this.val$j + " awake");
/*     */         }
/*     */       });
/*  28 */       System.out.println("Started thread in slot: " + idx);
/*  29 */       int res = runner.reserveSlot();
/*  30 */       Thread thread = new Thread()
/*     */       {
/*     */         public void run()
/*     */         {
/*  34 */           System.out.println("sleeping for " + this.val$j + " secs");
/*     */           try {
/*  36 */             Thread.sleep(this.val$j * 1000L);
/*     */           } catch (InterruptedException localInterruptedException) {
/*     */           }
/*  39 */           System.out.println(this.val$j + " awake");
/*     */         }
/*     */       };
/*  42 */       idx = runner.submitImmediate(thread);
/*  43 */       System.out.println("Started reserved thread in slot: " + idx + " res: " + res + " indexof: " + 
/*  44 */         runner.indexOf(thread));
/*     */     }
/*     */ 
/*  47 */     System.out.println("Blocking...");
/*  48 */     runner.block();
/*  49 */     System.out.println("Finished...");
/*     */   }
/*     */ 
/*     */   public ParallelTaskRunner(int maxThreads)
/*     */   {
/*  72 */     this.maxThreads = maxThreads;
/*  73 */     this.running = new ArrayList(maxThreads);
/*  74 */     for (int i = 0; i < maxThreads; i++)
/*  75 */       this.running.add(null);
/*     */   }
/*     */ 
/*     */   public ParallelTaskRunner()
/*     */   {
/*  83 */     this.running = new ArrayList();
/*     */   }
/*     */ 
/*     */   public void submitAndWait(Thread thread)
/*     */   {
/*  93 */     this.tasks.add(thread);
/*     */   }
/*     */ 
/*     */   public int submitAndBlock(Thread thread)
/*     */   {
/* 105 */     this.submitInProgress = true;
/* 106 */     this.tasks.add(thread);
/* 107 */     while (!hasSpace()) {
/* 108 */       clearFinished();
/*     */       try {
/* 110 */         Thread.sleep(1L);
/*     */       } catch (InterruptedException e) {
/* 112 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 115 */     int idx = startThread(thread);
/* 116 */     this.submitInProgress = false;
/* 117 */     return idx;
/*     */   }
/*     */ 
/*     */   public int submitImmediate(Thread thread)
/*     */   {
/* 131 */     this.submitInProgress = true;
/* 132 */     submitAndWait(thread);
/* 133 */     int idx = -1;
/* 134 */     if (this.reservedSlot != -1) {
/* 135 */       idx = startThread(thread, this.reservedSlot);
/* 136 */       this.reservedSlot = -1;
/* 137 */       this.submitInProgress = false;
/*     */     } else {
/* 139 */       if (hasSpace())
/* 140 */         this.reservedSlot = -1;
/* 141 */       idx = startThread(thread);
/* 142 */       this.submitInProgress = false;
/*     */     }
/* 144 */     return idx;
/*     */   }
/*     */ 
/*     */   private int startThread(Thread thread) {
/* 148 */     return startThread(thread, -1);
/*     */   }
/*     */ 
/*     */   private int startThread(Thread thread, int reservedSlot) {
/* 152 */     if (thread == null)
/* 153 */       return -1;
/* 154 */     int idx = addRunning(thread, reservedSlot);
/* 155 */     if (idx == -1)
/* 156 */       return -1;
/* 157 */     this.tasks.remove(thread);
/* 158 */     thread.start();
/* 159 */     if (this.pauseBetweenStarts > 0) {
/*     */       try {
/* 161 */         Thread.sleep(this.pauseBetweenStarts);
/*     */       } catch (InterruptedException e) {
/* 163 */         e.printStackTrace();
/*     */       }
/*     */     }
/* 166 */     return idx;
/*     */   }
/*     */ 
/*     */   private int addRunning(Thread thread) {
/* 170 */     return addRunning(thread, -1);
/*     */   }
/*     */ 
/*     */   private int addRunning(Thread thread, int reservedSlot) {
/* 174 */     if (this.maxThreads == -1) {
/* 175 */       if (!this.running.contains(thread)) {
/* 176 */         if (reservedSlot != -1) {
/* 177 */           this.running.set(reservedSlot, thread);
/* 178 */           return reservedSlot;
/*     */         }
/* 180 */         this.running.add(thread);
/*     */       }
/*     */ 
/* 183 */       return this.running.indexOf(thread);
/*     */     }
/* 185 */     int idx = -1;
/* 186 */     if (reservedSlot != -1) {
/* 187 */       if (this.running.get(reservedSlot) != null)
/* 188 */         return -1;
/* 189 */       this.running.set(reservedSlot, thread);
/* 190 */       idx = reservedSlot;
/*     */     } else {
/* 192 */       idx = firstNull(this.running);
/* 193 */       if (idx < 0)
/* 194 */         return -1;
/* 195 */       if ((this.reservedSlot != -1) && (idx == this.reservedSlot))
/* 196 */         throw new RuntimeException("Tried to put a thread in a reserved slot");
/* 197 */       this.running.set(idx, thread);
/*     */     }
/*     */ 
/* 200 */     return idx;
/*     */   }
/*     */ 
/*     */   private boolean hasSpace() {
/* 204 */     return (this.maxThreads < 0) || (firstNull(this.running) >= 0);
/*     */   }
/*     */ 
/*     */   public void block()
/*     */   {
/* 211 */     System.out.println("hasNonNull " + hasNonNull(this.running));
/* 212 */     System.out.println("tasks.size " + this.tasks.size());
/* 213 */     System.out.println("submit in prog " + this.submitInProgress);
/*     */ 
/* 215 */     while ((hasNonNull(this.running)) || (this.tasks.size() > 0) || (this.submitInProgress))
/*     */     {
/* 217 */       clearFinished();
/* 218 */       while ((hasSpace()) && (this.tasks.peek() != null))
/* 219 */         startThread((Thread)this.tasks.poll());
/*     */       try
/*     */       {
/* 222 */         Thread.sleep(1L);
/*     */       } catch (InterruptedException e) {
/* 224 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void clearFinished() {
/* 230 */     for (Thread thread : this.running)
/* 231 */       if ((thread != null) && (!thread.isAlive()))
/* 232 */         this.running.set(this.running.indexOf(thread), null);
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/* 241 */     Thread thread = new Thread("parallel-task-runner")
/*     */     {
/*     */       public void run() {
/* 244 */         ParallelTaskRunner.this.block();
/*     */       }
/*     */     };
/* 247 */     thread.setDaemon(true);
/* 248 */     thread.start();
/*     */   }
/*     */ 
/*     */   public int indexOf(Thread thread) {
/* 252 */     return this.running.indexOf(thread);
/*     */   }
/*     */ 
/*     */   public int reserveSlot()
/*     */   {
/* 267 */     if (this.maxThreads == -1) {
/* 268 */       int idx = this.running.size();
/* 269 */       this.running.add(null);
/* 270 */       this.reservedSlot = idx;
/* 271 */       return idx;
/*     */     }
/* 273 */     this.reservedSlot = -1;
/* 274 */     int reserved = firstNull(this.running);
/* 275 */     while (reserved == -1) {
/*     */       try {
/* 277 */         Thread.sleep(1L);
/*     */       } catch (InterruptedException e) {
/* 279 */         e.printStackTrace();
/*     */       }
/* 281 */       clearFinished();
/* 282 */       reserved = firstNull(this.running);
/*     */     }
/* 284 */     this.reservedSlot = reserved;
/* 285 */     return reserved;
/*     */   }
/*     */ 
/*     */   public int firstNull(List list) {
/* 289 */     for (int i = 0; i < list.size(); i++) {
/* 290 */       if ((list.get(i) == null) && 
/* 291 */         (this.reservedSlot != i)) {
/* 292 */         return i;
/*     */       }
/*     */     }
/* 295 */     return -1;
/*     */   }
/*     */ 
/*     */   public static boolean hasNonNull(List list) {
/* 299 */     for (int i = 0; i < list.size(); i++) {
/* 300 */       if (list.get(i) != null)
/* 301 */         return true;
/*     */     }
/* 303 */     return false;
/*     */   }
/*     */ 
/*     */   public int getPauseBetweenStarts() {
/* 307 */     return this.pauseBetweenStarts;
/*     */   }
/*     */ 
/*     */   public void setPauseBetweenStarts(int pauseBetweenStarts) {
/* 311 */     this.pauseBetweenStarts = pauseBetweenStarts;
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.concurrent.ParallelTaskRunner
 * JD-Core Version:    0.6.2
 */