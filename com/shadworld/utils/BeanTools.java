/*    */ package com.shadworld.utils;
/*    */ 
/*    */ import java.beans.BeanInfo;
/*    */ import java.beans.IntrospectionException;
/*    */ import java.beans.Introspector;
/*    */ import java.beans.PropertyDescriptor;
/*    */ import java.io.PrintStream;
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ public class BeanTools
/*    */ {
/*    */   public static void printBeanString(Object bean)
/*    */   {
/* 16 */     if (bean == null) {
/* 17 */       System.out.println("bean is null");
/* 18 */       return;
/*    */     }
/* 20 */     System.out.println(getBeanString(bean));
/*    */   }
/*    */ 
/*    */   public static void printBeanOrThrow(Object bean) {
/* 24 */     if (bean == null) {
/* 25 */       throw new RuntimeException("tried to print null bean.");
/*    */     }
/* 27 */     System.out.println(getBeanString(bean));
/*    */   }
/*    */ 
/*    */   public static String getBeanString(Object bean) {
/* 31 */     StringBuilder sb = new StringBuilder(bean.getClass().getName() + "\n");
/* 32 */     PropertyDescriptor[] props = propertyDescriptors(bean.getClass());
/*    */ 
/* 34 */     for (PropertyDescriptor prop : props) {
/* 35 */       sb.append(prop.getName() + ": ");
/* 36 */       Method getter = prop.getReadMethod();
/*    */       try
/*    */       {
/* 39 */         sb.append(getter.invoke(bean, new Object[0]));
/*    */       } catch (Exception e) {
/* 41 */         sb.append(e.getMessage());
/*    */       }
/* 43 */       sb.append("\n");
/*    */     }
/*    */ 
/* 46 */     return sb.toString();
/*    */   }
/*    */ 
/*    */   public static PropertyDescriptor[] propertyDescriptors(Class<?> c)
/*    */   {
/* 61 */     BeanInfo beanInfo = null;
/*    */     try {
/* 63 */       beanInfo = Introspector.getBeanInfo(c);
/*    */     }
/*    */     catch (IntrospectionException localIntrospectionException)
/*    */     {
/*    */     }
/* 68 */     return beanInfo.getPropertyDescriptors();
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.BeanTools
 * JD-Core Version:    0.6.2
 */