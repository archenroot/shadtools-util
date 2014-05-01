/*     */ package com.shadworld.util;
/*     */ 
/*     */ import com.shadworld.utils.L;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class MovingAverage<T extends Number>
/*     */ {
/*     */   protected final int period;
/*     */   protected final T[] values;
/*  38 */   protected int marker = 0;
/*  39 */   protected boolean reachedCapacity = false;
/*  40 */   protected Number total = Integer.valueOf(0);
/*     */   protected final boolean trackVariance;
/*     */   protected double[] variances;
/*  44 */   protected double varianceTotal = 0.0D;
/*     */ 
/* 189 */   boolean doDebugStop = true;
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  20 */     MovingAverage ma = new MovingAverage(10);
/*  21 */     for (int i = 0; i < 8; i++) {
/*  22 */       ma.addValue(Integer.valueOf(i));
/*  23 */       L.print(" " + i);
/*     */     }
/*  25 */     L.println("");
/*  26 */     for (int i = 0; i < 8; i++) {
/*  27 */       L.print(" " + ma.getValue(i));
/*     */     }
/*  29 */     L.println("");
/*  30 */     L.println("first (oldest): " + ma.getOldestValue());
/*  31 */     L.println("last (newset): " + ma.getNewestValue());
/*     */   }
/*     */ 
/*     */   public MovingAverage(int period)
/*     */   {
/*  48 */     this(period, false);
/*     */   }
/*     */ 
/*     */   public MovingAverage(int period, boolean trackVariance) {
/*  52 */     this.period = period;
/*  53 */     this.trackVariance = trackVariance;
/*     */ 
/*  55 */     this.values = new Number[period];
/*  56 */     if (trackVariance)
/*  57 */       this.variances = new double[period];
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*  65 */     this.marker = 0;
/*  66 */     this.reachedCapacity = false;
/*  67 */     this.total = Integer.valueOf(0);
/*  68 */     this.varianceTotal = 0.0D;
/*     */   }
/*     */ 
/*     */   public T getValue(int index)
/*     */   {
/*  77 */     int realindex = this.marker - index - 1;
/*  78 */     if (realindex < 0)
/*  79 */       realindex += this.period;
/*  80 */     return this.values[realindex];
/*     */   }
/*     */ 
/*     */   public T getOldestValue()
/*     */   {
/*  87 */     return getValue(this.reachedCapacity ? this.period - 1 : this.marker - 1);
/*     */   }
/*     */ 
/*     */   public T getNewestValue()
/*     */   {
/*  94 */     return getValue(0);
/*     */   }
/*     */ 
/*     */   public int getPeriod() {
/*  98 */     return this.period;
/*     */   }
/*     */ 
/*     */   public int getMarker()
/*     */   {
/* 106 */     return this.marker;
/*     */   }
/*     */ 
/*     */   public boolean isReachedCapacity() {
/* 110 */     return this.reachedCapacity;
/*     */   }
/*     */ 
/*     */   public Number getTotal() {
/* 114 */     return this.total;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 122 */     return this.reachedCapacity ? this.period : this.marker;
/*     */   }
/*     */ 
/*     */   public List<T> values()
/*     */   {
/* 130 */     ArrayList values = new ArrayList(this.reachedCapacity ? this.period : this.marker);
/* 131 */     if (this.reachedCapacity) {
/* 132 */       for (int i = 0; i < this.marker; i++)
/* 133 */         values.add(this.values[i]);
/*     */     }
/*     */     else {
/* 136 */       for (int i = this.marker; i < this.period; i++) {
/* 137 */         values.add(this.values[i]);
/*     */       }
/* 139 */       for (int i = 0; i < this.marker; i++) {
/* 140 */         values.add(this.values[i]);
/*     */       }
/*     */     }
/* 143 */     return values;
/*     */   }
/*     */ 
/*     */   public T[] valuesArray()
/*     */   {
/* 152 */     Number[] vals = new Number[this.reachedCapacity ? this.period : this.marker];
/* 153 */     if (this.reachedCapacity) {
/* 154 */       System.arraycopy(this.values, this.marker, vals, 0, this.marker);
/* 155 */       System.arraycopy(this.values, 0, vals, this.marker + 1, this.period - this.marker);
/*     */     } else {
/* 157 */       System.arraycopy(this.values, 0, vals, 0, this.marker);
/*     */     }
/*     */ 
/* 160 */     return vals;
/*     */   }
/*     */ 
/*     */   public double getAvg() {
/* 164 */     if (this.reachedCapacity) {
/* 165 */       return this.total.doubleValue() / this.period;
/*     */     }
/* 167 */     return this.total.doubleValue() / this.marker;
/*     */   }
/*     */ 
/*     */   public double getVariance()
/*     */   {
/* 172 */     if (this.reachedCapacity) {
/* 173 */       return this.varianceTotal / this.period;
/*     */     }
/* 175 */     return this.varianceTotal / this.marker;
/*     */   }
/*     */ 
/*     */   public double getStandardDeviation()
/*     */   {
/* 180 */     return StrictMath.sqrt(getVariance());
/*     */   }
/*     */ 
/*     */   public double getStandardDeviations(double value) {
/* 184 */     double varianceSqrt = StrictMath.abs((value - this.total.doubleValue()) / (this.reachedCapacity ? this.period : this.marker));
/* 185 */     return varianceSqrt / getStandardDeviation();
/*     */   }
/*     */ 
/*     */   public synchronized void addValue(T value)
/*     */   {
/* 192 */     if (this.reachedCapacity) {
/*     */       try {
/* 194 */         this.total = Double.valueOf(this.total.doubleValue() - this.values[this.marker].doubleValue());
/* 195 */         this.total = Double.valueOf(this.total.doubleValue() + value.doubleValue());
/*     */       } catch (Exception e) {
/* 197 */         if (this.doDebugStop) {
/* 198 */           this.doDebugStop = false;
/* 199 */           L.println(e.getMessage());
/*     */         }
/*     */       }
/*     */ 
/* 203 */       this.values[this.marker] = value;
/* 204 */       if (this.trackVariance) {
/* 205 */         double variance = StrictMath.pow((value.doubleValue() - this.total.doubleValue()) / this.period, 2.0D);
/* 206 */         this.varianceTotal = (this.varianceTotal - this.variances[this.marker] + variance);
/* 207 */         this.variances[this.marker] = variance;
/*     */       }
/* 209 */       this.marker += 1;
/* 210 */       if (this.marker >= this.period)
/* 211 */         this.marker = 0;
/*     */     }
/*     */     else {
/* 214 */       this.total = Double.valueOf(this.total.doubleValue() + value.doubleValue());
/* 215 */       this.values[this.marker] = value;
/* 216 */       if (this.trackVariance) {
/* 217 */         double variance = StrictMath.pow((value.doubleValue() - this.total.doubleValue()) / this.marker, 2.0D);
/* 218 */         this.varianceTotal = (this.varianceTotal - this.variances[this.marker] + variance);
/* 219 */         this.variances[this.marker] = variance;
/*     */       }
/* 221 */       this.marker += 1;
/* 222 */       if (this.marker >= this.period) {
/* 223 */         this.reachedCapacity = true;
/* 224 */         this.marker = 0;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.util.MovingAverage
 * JD-Core Version:    0.6.2
 */