/*    */ package com.shadworld.utils;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Enumeration;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ClassUtils
/*    */ {
/*    */   public static List<Class> getClasses(String packageName)
/*    */     throws ClassNotFoundException, IOException
/*    */   {
/* 22 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 23 */     assert (classLoader != null);
/* 24 */     String path = packageName.replace('.', '/');
/* 25 */     Enumeration resources = classLoader.getResources(path);
/* 26 */     List dirs = new ArrayList();
/* 27 */     while (resources.hasMoreElements()) {
/* 28 */       URL resource = (URL)resources.nextElement();
/* 29 */       dirs.add(new File(resource.getFile()));
/*    */     }
/* 31 */     ArrayList classes = new ArrayList();
/* 32 */     for (File directory : dirs) {
/* 33 */       classes.addAll(findClasses(directory, packageName));
/*    */     }
/* 35 */     return classes;
/*    */   }
/*    */ 
/*    */   private static List<Class> findClasses(File directory, String packageName)
/*    */     throws ClassNotFoundException
/*    */   {
/* 47 */     List classes = new ArrayList();
/* 48 */     if (!directory.exists()) {
/* 49 */       return classes;
/*    */     }
/* 51 */     File[] files = directory.listFiles();
/* 52 */     for (File file : files) {
/* 53 */       if (file.isDirectory()) {
/* 54 */         assert (!file.getName().contains("."));
/* 55 */         classes.addAll(findClasses(file, packageName + "." + file.getName()));
/* 56 */       } else if (file.getName().endsWith(".class")) {
/* 57 */         classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
/*    */       }
/*    */     }
/* 60 */     return classes;
/*    */   }
/*    */ 
/*    */   public static Class classOf(Object o) {
/* 64 */     return o == null ? null : o.getClass();
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.ClassUtils
 * JD-Core Version:    0.6.2
 */