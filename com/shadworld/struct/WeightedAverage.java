/*     */ package com.shadworld.struct;
/*     */ 
/*     */ import java.text.NumberFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Random;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ 
/*     */ public class WeightedAverage<T>
/*     */ {
/*  13 */   private double total = 0.0D;
/*  14 */   private ArrayList<Double> weight = new ArrayList();
/*  15 */   private ArrayList<T> value = new ArrayList();
/*  16 */   private HashMap<String, Integer> labels = new HashMap();
/*     */ 
/*  18 */   private Random rand = new Random();
/*     */ 
/*     */   public Double add(Number weighting, T value) {
/*  21 */     double wt = weighting.doubleValue();
/*  22 */     this.weight.add(Double.valueOf(wt));
/*  23 */     this.value.add(value);
/*  24 */     this.total += wt;
/*  25 */     return Double.valueOf(wt / this.total);
/*     */   }
/*     */ 
/*     */   public Double set(String label, Number weighting, T value) {
/*  29 */     Integer index = (Integer)this.labels.get(label);
/*  30 */     if (index == null) {
/*  31 */       Double d = add(weighting, value);
/*  32 */       this.labels.put(label, Integer.valueOf(this.weight.size() - 1));
/*  33 */       return d;
/*     */     }
/*  35 */     double wt = weighting.doubleValue();
/*  36 */     double oldWt = ((Double)this.weight.get(index.intValue())).doubleValue();
/*  37 */     this.total -= oldWt;
/*  38 */     this.total += wt;
/*  39 */     this.weight.set(index.intValue(), Double.valueOf(wt));
/*  40 */     this.value.set(index.intValue(), value);
/*  41 */     return Double.valueOf(wt / this.total);
/*     */   }
/*     */ 
/*     */   public T get(String label)
/*     */   {
/*  46 */     Integer index = (Integer)this.labels.get(label);
/*  47 */     if (index == null)
/*  48 */       return null;
/*  49 */     return this.value.get(index.intValue());
/*     */   }
/*     */ 
/*     */   public Double getWeighting(String label) {
/*  53 */     Integer index = (Integer)this.labels.get(label);
/*  54 */     if (index == null)
/*  55 */       return null;
/*  56 */     return (Double)this.weight.get(index.intValue());
/*     */   }
/*     */ 
/*     */   public void remove(String label) {
/*  60 */     Integer index = (Integer)this.labels.get(label);
/*  61 */     if (index != null) {
/*  62 */       double oldWt = ((Double)this.weight.get(index.intValue())).doubleValue();
/*  63 */       this.total -= oldWt;
/*  64 */       for (String key : this.labels.keySet()) {
/*  65 */         Integer ind = (Integer)this.labels.get(key);
/*  66 */         if ((ind != null) && (ind.intValue() > index.intValue()))
/*  67 */           this.labels.put(key, Integer.valueOf(ind.intValue() - 1));
/*     */       }
/*  69 */       this.weight.remove(index);
/*  70 */       this.value.remove(index);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean remove(T value) {
/*  75 */     int i = this.value.indexOf(value);
/*  76 */     if (i == -1)
/*  77 */       return false;
/*  78 */     this.value.remove(i);
/*  79 */     this.total -= ((Double)this.weight.get(i)).doubleValue();
/*  80 */     this.weight.remove(i);
/*  81 */     return true;
/*     */   }
/*     */ 
/*     */   public Object chooseRandomChild() {
/*  85 */     Object o = chooseRandom();
/*  86 */     if ((o instanceof WeightedAverage)) {
/*  87 */       WeightedAverage wa = (WeightedAverage)o;
/*  88 */       return wa.chooseRandomChild();
/*     */     }
/*  90 */     return o;
/*     */   }
/*     */ 
/*     */   public T chooseRandom() {
/*  94 */     double r = this.rand.nextDouble();
/*  95 */     double t = 0.0D;
/*  96 */     for (int i = 0; i < this.weight.size(); i++) {
/*  97 */       double w = ((Double)this.weight.get(i)).doubleValue();
/*  98 */       double lim = (t + w) / this.total;
/*  99 */       if (r < lim)
/* 100 */         return this.value.get(i);
/* 101 */       t += w;
/*     */     }
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */   public String getWeightingString() {
/* 107 */     return getWeightingString(0);
/*     */   }
/*     */ 
/*     */   private String getWeightingString(int indent) {
/* 111 */     StringBuilder sb = new StringBuilder();
/* 112 */     NumberFormat pfmt = NumberFormat.getPercentInstance();
/* 113 */     pfmt.setMaximumFractionDigits(2);
/* 114 */     NumberFormat dfmt = NumberFormat.getInstance();
/* 115 */     dfmt.setMaximumFractionDigits(2);
/* 116 */     double tp = 0.0D;
/* 117 */     for (int i = 0; i < this.weight.size(); i++) {
/* 118 */       double pc = ((Double)this.weight.get(i)).doubleValue() / this.total;
/* 119 */       tp += pc;
/* 120 */       String perc = pfmt.format(pc);
/* 121 */       if (indent != 0) {
/* 122 */         sb.append(StringUtils.repeat(" ", indent));
/* 123 */         sb.append("-- ");
/*     */       }
/* 125 */       sb.append("[").append(i).append("] - ");
/* 126 */       sb.append(" wt: ").append(StringUtils.rightPad(dfmt.format(this.weight.get(i)), 8));
/* 127 */       sb.append(" pc: ").append(StringUtils.rightPad(perc, 7));
/* 128 */       sb.append(" targ: ").append(String.valueOf(this.value.get(i)));
/* 129 */       sb.append("\n");
/* 130 */       if ((this.value.get(i) instanceof WeightedAverage)) {
/* 131 */         WeightedAverage child = (WeightedAverage)this.value.get(i);
/* 132 */         sb.append(child.getWeightingString(indent + 1));
/*     */       }
/*     */     }
/* 135 */     if (indent != 0) {
/* 136 */       sb.append(StringUtils.repeat(" ", indent));
/* 137 */       sb.append("-- ");
/*     */     }
/* 139 */     sb.append("entries: ").append(this.weight.size()).append(" total: ").append(this.total).append(" total perc: ").append(pfmt.format(tp));
/* 140 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.struct.WeightedAverage
 * JD-Core Version:    0.6.2
 */