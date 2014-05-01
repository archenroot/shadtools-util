/*    */ package com.shadworld.utils;
/*    */ 
/*    */ import com.shadworld.struct.DataTable;
/*    */ import com.shadworld.struct.Record;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class StructUtils
/*    */ {
/*    */   public static DataTable padTable(DataTable table, Map keys, boolean returnCopy)
/*    */   {
/* 23 */     if (table == null)
/* 24 */       return null;
/* 25 */     DataTable target = table;
/* 26 */     if (returnCopy)
/* 27 */       target = new DataTable();
/* 28 */     for (int i = 0; i < table.size(); i++) {
/* 29 */       Record rec = (Record)table.get(i);
/* 30 */       Record tRec = rec;
/* 31 */       if (returnCopy) {
/* 32 */         tRec = new Record();
/* 33 */         target.add(tRec);
/*    */       }
/* 35 */       for (Iterator localIterator = keys.keySet().iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
/* 36 */         tRec.put(keys, rec.get(key));
/*    */       }
/*    */     }
/* 39 */     return target;
/*    */   }
/*    */ 
/*    */   public static DataTable padTable(DataTable table, boolean returnCopy)
/*    */   {
/* 49 */     return padTable(table, getKeySuperSet(table), returnCopy);
/*    */   }
/*    */ 
/*    */   public static DataTable padTable(DataTable table, Map keys)
/*    */   {
/* 59 */     return padTable(table, keys, false);
/*    */   }
/*    */ 
/*    */   public static DataTable padTable(DataTable table)
/*    */   {
/* 68 */     return padTable(table, false);
/*    */   }
/*    */ 
/*    */   public static Record getKeySuperSet(DataTable table)
/*    */   {
/* 78 */     Record cols = new Record();
/*    */     Iterator localIterator2;
/* 79 */     for (Iterator localIterator1 = table.iterator(); localIterator1.hasNext(); 
/* 80 */       localIterator2.hasNext())
/*    */     {
/* 79 */       Record record = (Record)localIterator1.next();
/* 80 */       localIterator2 = record.keySet().iterator(); continue; Object col = localIterator2.next();
/* 81 */       cols.put(col, null);
/*    */     }
/* 83 */     return cols;
/*    */   }
/*    */ 
/*    */   public static <K, V> K keyForValue(Map<K, V> map, V value)
/*    */   {
/* 88 */     for (Map.Entry entry : map.entrySet()) {
/* 89 */       if (value == null) {
/* 90 */         if (entry.getValue() == null)
/* 91 */           return entry.getKey();
/* 92 */       } else if (value.equals(entry.getValue()))
/* 93 */         return entry.getKey();
/*    */     }
/* 95 */     return null;
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.StructUtils
 * JD-Core Version:    0.6.2
 */