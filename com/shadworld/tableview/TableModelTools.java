/*     */ package com.shadworld.tableview;
/*     */ 
/*     */ import com.shadworld.struct.DataTable;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ 
/*     */ public class TableModelTools
/*     */ {
/*     */   public static DefaultTableModel getStaticDataTableModel(DataTable table, boolean addRow)
/*     */   {
/*  19 */     Object[][] data = addRowColumn(table.getObjectArrays(), addRow);
/*  20 */     Object[] labels = addRowColumnName(table.getKeyArray(), addRow);
/*  21 */     if (data == null) {
/*  22 */       data = new Object[0][0];
/*  23 */       labels = new Object[0];
/*     */     }
/*  25 */     return new DefaultTableModel(data, labels);
/*     */   }
/*     */ 
/*     */   public static DefaultTableModel getStaticDataTableModel(DataTable table) {
/*  29 */     return getStaticDataTableModel(table, true);
/*     */   }
/*     */ 
/*     */   public static Object[] addRowColumnName(Object[] names, boolean doAdd) {
/*  33 */     if (names == null)
/*  34 */       names = new Object[1];
/*  35 */     if (doAdd) {
/*  36 */       Object[] newNames = new Object[names.length + 1];
/*  37 */       newNames[0] = "Col";
/*  38 */       for (int i = 0; i < names.length; i++)
/*  39 */         newNames[(i + 1)] = names[i];
/*  40 */       return newNames;
/*     */     }
/*  42 */     return names;
/*     */   }
/*     */ 
/*     */   public static Object[][] addRowColumn(Object[][] objs, boolean doAdd) {
/*  46 */     if ((doAdd) && (objs.length > 0)) {
/*  47 */       Object[][] newO = new Object[objs.length][objs[0].length + 1];
/*  48 */       for (int i = 0; i < objs.length; i++) {
/*  49 */         Object[] o = new Object[objs[i].length + 1];
/*  50 */         o[0] = Integer.valueOf(i);
/*  51 */         for (int j = 0; j < o.length - 1; j++) {
/*  52 */           o[(j + 1)] = objs[i][j];
/*     */         }
/*  54 */         newO[i] = o;
/*     */       }
/*  56 */       return newO;
/*     */     }
/*  58 */     return objs;
/*     */   }
/*     */ 
/*     */   public static DefaultTableModel getStaticBeanTableModel(Collection col) {
/*  62 */     return getStaticBeanTableModel(col, true);
/*     */   }
/*     */ 
/*     */   public static DefaultTableModel getStaticBeanTableModel(Collection col, boolean addRow) {
/*  66 */     if ((col != null) && (col.size() > 0)) {
/*  67 */       Iterator i = col.iterator();
/*  68 */       Object o = i.next();
/*  69 */       if (o == null)
/*  70 */         return null;
/*  71 */       Method[] methods = getters(o.getClass());
/*  72 */       String[] names = new String[methods.length];
/*  73 */       for (int j = 0; j < methods.length; j++) {
/*  74 */         if (methods[j].getName().startsWith("get"))
/*  75 */           names[j] = methods[j].getName().substring(3);
/*  76 */         else if (methods[j].getName().startsWith("is"))
/*  77 */           names[j] = methods[j].getName();
/*     */       }
/*  79 */       i = col.iterator();
/*  80 */       int r = 0;
/*     */ 
/*  82 */       Object[][] objs = new Object[col.size()][methods.length];
/*  83 */       while (i.hasNext()) {
/*  84 */         o = i.next();
/*  85 */         for (int c = 0; c < methods.length; c++) {
/*  86 */           if (o == null) {
/*  87 */             objs[r][c] = null;
/*     */           } else {
/*  89 */             Object prop = null;
/*     */             try {
/*  91 */               prop = methods[c].invoke(o, new Object[0]);
/*     */             } catch (Exception localException) {
/*     */             }
/*  94 */             objs[r][c] = prop;
/*     */           }
/*     */         }
/*  97 */         r++;
/*     */       }
/*  99 */       Object[][] data = addRowColumn(objs, addRow);
/* 100 */       Object[] labels = addRowColumnName(names, addRow);
/* 101 */       if (data == null) {
/* 102 */         data = new Object[0][0];
/* 103 */         labels = new Object[0];
/*     */       }
/* 105 */       return new DefaultTableModel(data, labels);
/*     */     }
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */   public static boolean hasGetter(Object o) {
/* 111 */     if (o == null)
/* 112 */       return false;
/* 113 */     return hasGetter(o.getClass());
/*     */   }
/*     */   public static boolean hasGetter(Class clazz) {
/* 116 */     if (clazz == null)
/* 117 */       return false;
/* 118 */     Method[] methods = clazz.getMethods();
/* 119 */     for (Method method : methods) {
/* 120 */       if (((method.getName().startsWith("get")) || (method.getName().startsWith("is"))) && (method.getParameterTypes().length == 0)) {
/* 121 */         return true;
/*     */       }
/*     */     }
/* 124 */     return false;
/*     */   }
/*     */ 
/*     */   public static Method[] getters(Class clazz) {
/* 128 */     if (clazz == null)
/* 129 */       return null;
/* 130 */     Method[] methods = clazz.getMethods();
/* 131 */     ArrayList list = new ArrayList();
/* 132 */     for (Method method : methods) {
/* 133 */       if (((method.getName().startsWith("get")) || (method.getName().startsWith("is"))) && (method.getParameterTypes().length == 0)) {
/* 134 */         list.add(method);
/*     */       }
/*     */     }
/* 137 */     return (Method[])list.toArray(new Method[list.size()]);
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.tableview.TableModelTools
 * JD-Core Version:    0.6.2
 */