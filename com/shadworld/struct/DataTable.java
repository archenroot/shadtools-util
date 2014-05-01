/*     */ package com.shadworld.struct;
/*     */ 
/*     */ import com.shadworld.struct.filter.IFilter;
/*     */ import com.shadworld.struct.transform.DataTransform;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class DataTable extends ArrayList<Record>
/*     */ {
/*  26 */   private static Random rand = new Random();
/*     */ 
/*     */   public DataTable(int size)
/*     */   {
/*  30 */     super(size);
/*     */   }
/*     */ 
/*     */   public DataTable()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Record getDataRecord(int index)
/*     */   {
/*  43 */     return (Record)get(index);
/*     */   }
/*     */ 
/*     */   public Record getRecord(int index) {
/*  47 */     return (Record)get(index);
/*     */   }
/*     */ 
/*     */   public int getNumDataRecords() {
/*  51 */     return size();
/*     */   }
/*     */ 
/*     */   public Iterator getDataRecordIterator() {
/*  55 */     return iterator();
/*     */   }
/*     */ 
/*     */   public void clearDataRecords() {
/*  59 */     clear();
/*     */   }
/*     */ 
/*     */   public void addDataRecord(Record dataRecord) {
/*  63 */     add(dataRecord);
/*     */   }
/*     */ 
/*     */   public void deleteDataRecord(int index) {
/*  67 */     remove(index);
/*     */   }
/*     */ 
/*     */   public Object get(int index, Object key)
/*     */   {
/*  75 */     Record dataRecord = (Record)get(index);
/*  76 */     if (dataRecord == null) {
/*  77 */       return null;
/*     */     }
/*  79 */     return dataRecord.get(key);
/*     */   }
/*     */ 
/*     */   public void append(DataTable dataTable)
/*     */   {
/*  88 */     addAll(dataTable);
/*     */   }
/*     */ 
/*     */   public void join(DataTable dataSet, boolean overwriteSource)
/*     */   {
/*  98 */     int largestSize = size() > dataSet.size() ? size() : dataSet.size();
/*  99 */     for (int i = 0; i < largestSize; i++) {
/* 100 */       Record dr1 = (Record)get(i);
/* 101 */       Record dr2 = (Record)dataSet.get(i);
/* 102 */       if (overwriteSource) {
/* 103 */         Iterator iterator = dr2.keySet().iterator();
/* 104 */         while (iterator.hasNext()) {
/* 105 */           Object key = iterator.next();
/* 106 */           dr1.put(key, dr2.get(key));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ArrayList<DataTable> split(int size)
/*     */   {
/* 118 */     ArrayList smallTables = new ArrayList();
/* 119 */     DataTable smallTable = new DataTable();
/* 120 */     smallTables.add(smallTable);
/* 121 */     for (int i = 0; i < size(); i++) {
/* 122 */       smallTable.add((Record)get(i));
/* 123 */       if (smallTable.size() >= size) {
/* 124 */         smallTable = new DataTable();
/* 125 */         smallTables.add(smallTable);
/*     */       }
/*     */     }
/* 128 */     return smallTables;
/*     */   }
/*     */ 
/*     */   public void addColumn(Object newColumnKey, Object defaultValue) {
/* 132 */     for (Iterator i = iterator(); i.hasNext(); ) {
/* 133 */       Record dr = (Record)i.next();
/* 134 */       if (!dr.containsKey(newColumnKey))
/* 135 */         dr.put(newColumnKey, defaultValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addColumn(Object newColumnKey)
/*     */   {
/* 141 */     addColumn(newColumnKey, null);
/*     */   }
/*     */ 
/*     */   public void removeColumn(Object key) {
/* 145 */     for (Iterator i = iterator(); i.hasNext(); )
/* 146 */       ((Record)i.next()).remove(key);
/*     */   }
/*     */ 
/*     */   public void renameColumn(Object key, Object newKey)
/*     */   {
/* 156 */     for (Iterator i = iterator(); i.hasNext(); ) {
/* 157 */       Record dr = (Record)i.next();
/* 158 */       if (dr.containsKey(key)) {
/* 159 */         dr.insert(key, newKey, dr.get(key));
/* 160 */         dr.remove(key);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void transformColumn(Object key, DataTransform transform) {
/* 166 */     for (Iterator i = iterator(); i.hasNext(); )
/* 167 */       transform.transform(key);
/*     */   }
/*     */ 
/*     */   public void filterTable(IFilter filter)
/*     */   {
/* 172 */     for (ListIterator i = listIterator(); i.hasNext(); ) {
/* 173 */       Record rec = (Record)i.next();
/* 174 */       if (!filter.accept(rec))
/* 175 */         i.remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void filterByColumn(IFilter filter, Object key)
/*     */   {
/* 181 */     for (ListIterator i = listIterator(); i.hasNext(); ) {
/* 182 */       Record rec = (Record)i.next();
/* 183 */       if (!filter.accept(rec.get(key)))
/* 184 */         i.remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   public ArrayList getColumnValues(Object key)
/*     */   {
/* 190 */     ArrayList values = new ArrayList();
/* 191 */     for (int i = 0; i < size(); i++) {
/* 192 */       values.add(((Record)get(i)).get(key));
/*     */     }
/* 194 */     return values;
/*     */   }
/*     */ 
/*     */   public DataTable getCopy() {
/* 198 */     DataTable newTable = new DataTable();
/* 199 */     for (Iterator i = iterator(); i.hasNext(); ) {
/* 200 */       newTable.add((Record)i.next());
/*     */     }
/* 202 */     return newTable;
/*     */   }
/*     */ 
/*     */   public Object[][] getObjectArrays() {
/* 206 */     if (size() > 0) {
/* 207 */       Record rec = (Record)get(0);
/* 208 */       int size = -1;
/* 209 */       if (rec != null)
/* 210 */         size = rec.size();
/* 211 */       Object[][] objs = new Object[size()][size];
/* 212 */       for (int i = 0; i < size(); i++) {
/* 213 */         rec = (Record)get(i);
/* 214 */         objs[i] = rec.getValueArray();
/*     */       }
/* 216 */       return objs;
/*     */     }
/* 218 */     return new Object[0][0];
/*     */   }
/*     */ 
/*     */   public Object[] getKeyArray() {
/* 222 */     if (size() > 0) {
/* 223 */       Record rec = (Record)get(0);
/* 224 */       if (rec != null)
/* 225 */         return rec.getKeyArray();
/*     */     }
/* 227 */     return null;
/*     */   }
/*     */ 
/*     */   public static DataTable randomizeOrder(DataTable tbl)
/*     */   {
/* 232 */     if (tbl == null)
/* 233 */       return null;
/* 234 */     int size = tbl.size();
/* 235 */     DataTable table = new DataTable(size);
/* 236 */     for (int i = 0; i < size; i++) {
/* 237 */       int idx = rand.nextInt(tbl.size());
/* 238 */       table.add((Record)tbl.get(idx));
/* 239 */       tbl.remove(idx);
/*     */     }
/* 241 */     return table;
/*     */   }
/*     */ 
/*     */   public void sortByColumn(String key) {
/* 245 */     Collections.sort(this, new ColumnComparator(key));
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public Record ensureConsistentColumns(Object defaultValue)
/*     */   {
/* 255 */     Record cols = null;
/* 256 */     for (Record rec : this)
/* 257 */       cols = Record.getSuperSet(cols, rec);
/*     */     Iterator localIterator2;
/* 259 */     for (??? = iterator(); ???.hasNext(); 
/* 260 */       localIterator2.hasNext())
/*     */     {
/* 259 */       Record rec = (Record)???.next();
/* 260 */       localIterator2 = cols.keySet().iterator(); continue; Object key = localIterator2.next();
/* 261 */       if (!rec.containsKey(key)) {
/* 262 */         rec.put(key, defaultValue);
/*     */       }
/*     */     }
/*     */ 
/* 266 */     return cols;
/*     */   }
/*     */ 
/*     */   public DataTable keysToCaseInsensitive() {
/* 270 */     for (Record rec : this) {
/* 271 */       rec.convertToCaseInSensitive();
/*     */     }
/* 273 */     return this;
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.struct.DataTable
 * JD-Core Version:    0.6.2
 */