/*     */ package com.shadworld.util;
/*     */ 
/*     */ import com.shadworld.utils.L;
/*     */ import java.util.Random;
/*     */ 
/*     */ public class RateCalculator
/*     */ {
/*     */   MovingAverage<Double> events;
/*     */   MovingAverage<Double> ma;
/*     */   MovingAverage<Double> trend;
/*  43 */   long starttime = System.currentTimeMillis();
/*  44 */   long numEvents = 0L;
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws InterruptedException
/*     */   {
/*  10 */     RateCalculator calc = new RateCalculator(5, 5);
/*  11 */     Random rand = new Random();
/*  12 */     int num = 0;
/*  13 */     long sleep = 250L;
/*  14 */     calc.registerEvent();
/*  15 */     double lastAvg = -1.0D;
/*     */     while (true) {
/*  17 */       num++;
/*     */ 
/*  22 */       long now = System.currentTimeMillis();
/*  23 */       if (num % 20 == 0) {
/*  24 */         L.println("20: ");
/*  25 */         sleep += 250L;
/*     */       }
/*  27 */       double rate = calc.currentRate(now, 1000L);
/*  28 */       lastAvg = calc.avgRate(1000L);
/*  29 */       calc.registerEvent();
/*  30 */       double diff = lastAvg - calc.avgRate(1000L);
/*  31 */       L.println("Rate: " + rate + " /sec Avg Rate: " + calc.avgRate(1000L) + " / sec " + " Trend: " + 
/*  32 */         calc.trend() + " Trend Avg: " + calc.trendAvg() + "  - Rest = " + sleep);
/*     */ 
/*  35 */       Thread.sleep(rand.nextInt((int)sleep));
/*     */     }
/*     */   }
/*     */ 
/*     */   public RateCalculator(int historySize, int avgPeriod, int trendPeriod, boolean trackVariance)
/*     */   {
/*  55 */     this.events = new MovingAverage(historySize, trackVariance);
/*  56 */     if (avgPeriod > 0)
/*  57 */       this.ma = new MovingAverage(avgPeriod);
/*  58 */     if (trendPeriod > 0)
/*  59 */       this.trend = new MovingAverage(avgPeriod);
/*     */   }
/*     */ 
/*     */   public RateCalculator(int historySize, int avgPeriod) {
/*  63 */     this(historySize, avgPeriod, -1, true);
/*     */   }
/*     */ 
/*     */   public RateCalculator(int historySize) {
/*  67 */     this(historySize, -1, -1, true);
/*     */   }
/*     */ 
/*     */   public void registerEvent() {
/*  71 */     registerEvent(System.currentTimeMillis());
/*     */   }
/*     */ 
/*     */   public void registerEvent(long time) {
/*  75 */     if ((this.ma != null) && (this.numEvents > 0L)) {
/*  76 */       Double rate = Double.valueOf(currentRate(time, 1L));
/*  77 */       if ((!rate.isInfinite()) && (!rate.isNaN())) {
/*  78 */         this.ma.addValue(rate);
/*     */       }
/*     */     }
/*  81 */     this.events.addValue(Double.valueOf(time));
/*  82 */     if ((this.numEvents++ > 1L) && (this.trend != null)) {
/*  83 */       double last = ((Double)this.events.getValue(0)).doubleValue();
/*  84 */       double prevLast = ((Double)this.events.getValue(1)).doubleValue();
/*  85 */       double prevDiff = last - prevLast;
/*  86 */       double currentDiff = time - last;
/*     */ 
/*  88 */       this.trend.addValue(Double.valueOf(prevDiff - currentDiff));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void registerEvents(long time, int numEvents) {
/*  93 */     for (int i = 0; i < numEvents; i++)
/*  94 */       registerEvent(time);
/*     */   }
/*     */ 
/*     */   public void registerEvents(int numEvents) {
/*  98 */     registerEvents(System.currentTimeMillis(), numEvents);
/*     */   }
/*     */ 
/*     */   public double currentRate(long atTime, long perInterval)
/*     */   {
/* 105 */     double interval = atTime - ((Double)this.events.getOldestValue()).doubleValue();
/* 106 */     return perInterval * this.events.size() / interval;
/*     */   }
/*     */ 
/*     */   public double currentRate(long perInterval) {
/* 110 */     return currentRate(System.currentTimeMillis(), perInterval);
/*     */   }
/*     */ 
/*     */   public double avgRate(long perInterval) {
/* 114 */     return perInterval * this.ma.getAvg();
/*     */   }
/*     */ 
/*     */   public double rateVariance(long perInterval) {
/* 118 */     return this.ma.getVariance() / StrictMath.pow(perInterval, 2.0D);
/*     */   }
/*     */ 
/*     */   public double rateStandardDeviation(long perInterval) {
/* 122 */     return this.ma.getStandardDeviation() / perInterval;
/*     */   }
/*     */ 
/*     */   public double rateStandardDeviations(double value, long perInterval) {
/* 126 */     return this.ma.getStandardDeviations(value * perInterval);
/*     */   }
/*     */ 
/*     */   public double rateStandardDeviations(double value) {
/* 130 */     return this.ma.getStandardDeviations(value);
/*     */   }
/*     */ 
/*     */   public int trend()
/*     */   {
/* 139 */     Double val = (Double)this.trend.getNewestValue();
/* 140 */     return val == null ? 0 : val.intValue();
/*     */   }
/*     */ 
/*     */   public int trendAvg()
/*     */   {
/* 149 */     Double val = Double.valueOf(this.trend.getAvg());
/* 150 */     return val == null ? 0 : val.intValue();
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.util.RateCalculator
 * JD-Core Version:    0.6.2
 */