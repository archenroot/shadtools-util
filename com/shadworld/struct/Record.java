/*     */ package com.shadworld.struct;
/*     */ 
/*     */ import com.shadworld.util.Time;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Method;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class Record extends LinkedHashMap
/*     */   implements Serializable
/*     */ {
/*  31 */   private boolean caseSensitive = true;
/*  32 */   private boolean alertOnAmbiguousKeys = false;
/*  33 */   private LinkedHashMap<String, Object> lowerKeys = new LinkedHashMap();
/*     */ 
/*     */   public Record()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Record(boolean caseInSensitive)
/*     */   {
/*  42 */     this.caseSensitive = (!caseInSensitive);
/*     */   }
/*     */ 
/*     */   public Iterator getOrderedKeysIterator()
/*     */   {
/*  52 */     return keySet().iterator();
/*     */   }
/*     */ 
/*     */   public void insert(Object keyBefore, Object newKey, Object value)
/*     */   {
/*  67 */     Record temp = new Record();
/*  68 */     boolean foundKey = false;
/*  69 */     for (Iterator i = keySet().iterator(); i.hasNext(); ) {
/*  70 */       Object key = i.next();
/*  71 */       if ((foundKey) || (key == keyBefore)) {
/*  72 */         foundKey = true;
/*  73 */         temp.put(key, get(key));
/*     */       }
/*     */     }
/*  76 */     for (Iterator i = temp.keySet().iterator(); i.hasNext(); ) {
/*  77 */       remove(i.next());
/*     */     }
/*  79 */     put(newKey, value);
/*  80 */     append(temp);
/*     */   }
/*     */ 
/*     */   public void append(Record rec)
/*     */   {
/*  89 */     if (rec == null)
/*  90 */       return;
/*  91 */     for (Iterator i = rec.keySet().iterator(); i.hasNext(); ) {
/*  92 */       Object key = i.next();
/*  93 */       put(key, rec.get(key));
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getKeyAfter(Object key) {
/*  98 */     for (Iterator i = keySet().iterator(); i.hasNext(); ) {
/*  99 */       if (i.next() == key) {
/* 100 */         return i.next();
/*     */       }
/*     */     }
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */   public Object getKeyBefore(Object key) {
/* 107 */     Object lastKey = null;
/* 108 */     for (Iterator i = keySet().iterator(); i.hasNext(); ) {
/* 109 */       Object thisKey = i.next();
/* 110 */       if (thisKey == key) {
/* 111 */         return lastKey;
/*     */       }
/* 113 */       lastKey = thisKey;
/*     */     }
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */   public Object getColumn(int index) {
/* 119 */     Iterator i = getOrderedKeysIterator();
/* 120 */     int col = 0;
/* 121 */     while (i.hasNext()) {
/* 122 */       if (col == index)
/* 123 */         return get(i.next());
/* 124 */       i.next();
/*     */     }
/* 126 */     return null;
/*     */   }
/*     */ 
/*     */   public Object getColumnKey(int index) {
/* 130 */     Iterator i = getOrderedKeysIterator();
/* 131 */     int col = 0;
/* 132 */     while (i.hasNext()) {
/* 133 */       if (col == index)
/* 134 */         return i.next();
/* 135 */       i.next();
/*     */     }
/* 137 */     return null;
/*     */   }
/*     */ 
/*     */   public Object[] getValueArray() {
/* 141 */     Object[] vals = new Object[size()];
/* 142 */     Iterator i = getOrderedKeysIterator();
/* 143 */     int idx = 0;
/* 144 */     while (i.hasNext()) {
/* 145 */       vals[idx] = get(i.next());
/* 146 */       idx++;
/*     */     }
/* 148 */     return vals;
/*     */   }
/*     */ 
/*     */   public Object[] getKeyArray() {
/* 152 */     Object[] keys = new Object[size()];
/* 153 */     Iterator i = getOrderedKeysIterator();
/* 154 */     int idx = 0;
/* 155 */     while (i.hasNext()) {
/* 156 */       keys[idx] = i.next();
/* 157 */       idx++;
/*     */     }
/* 159 */     return keys;
/*     */   }
/*     */ 
/*     */   public static DataTable invertToTable(Record rec) {
/* 163 */     if (rec == null)
/* 164 */       return null;
/* 165 */     DataTable tbl = new DataTable();
/* 166 */     Iterator i = rec.getOrderedKeysIterator();
/* 167 */     while (i.hasNext()) {
/* 168 */       Object key = i.next();
/* 169 */       Record dr = new Record();
/* 170 */       dr.put("KEY", key);
/* 171 */       dr.put("VALUE", rec.get(key));
/* 172 */       tbl.add(dr);
/*     */     }
/* 174 */     return tbl;
/*     */   }
/*     */ 
/*     */   public static Record recordFromBean(Object o) {
/* 178 */     Record rec = new Record();
/* 179 */     if (o == null)
/* 180 */       return rec;
/* 181 */     for (Method method : getters(o.getClass())) {
/* 182 */       String key = method.getName().substring(3);
/* 183 */       Object value = null;
/*     */       try {
/* 185 */         value = method.invoke(o, new Object[0]);
/*     */       } catch (Exception localException) {
/*     */       }
/* 188 */       rec.put(key, value);
/*     */     }
/* 190 */     return rec;
/*     */   }
/*     */ 
/*     */   public static Method[] getters(Class clazz) {
/* 194 */     if (clazz == null)
/* 195 */       return null;
/* 196 */     Method[] methods = clazz.getMethods();
/* 197 */     ArrayList list = new ArrayList();
/* 198 */     for (Method method : methods) {
/* 199 */       if ((method.getName().startsWith("get")) && (method.getParameterTypes().length == 0)) {
/* 200 */         list.add(method);
/*     */       }
/*     */     }
/* 203 */     return (Method[])list.toArray(new Method[list.size()]);
/*     */   }
/*     */ 
/*     */   public String getString(Object key) {
/* 207 */     Object o = get(key);
/* 208 */     if (o == null)
/* 209 */       return null;
/* 210 */     return o.toString();
/*     */   }
/*     */ 
/*     */   public Float getFloat(Object key) {
/* 214 */     Object o = get(key);
/* 215 */     if (o == null)
/* 216 */       return null;
/* 217 */     Float r = null;
/*     */     try {
/* 219 */       r = Float.valueOf(o.toString());
/*     */     } catch (Exception localException) {
/*     */     }
/* 222 */     return r;
/*     */   }
/*     */ 
/*     */   public Double getDouble(Object key) {
/* 226 */     Object o = get(key);
/* 227 */     if (o == null)
/* 228 */       return null;
/* 229 */     Double r = null;
/*     */     try {
/* 231 */       r = Double.valueOf(o.toString());
/*     */     } catch (Exception localException) {
/*     */     }
/* 234 */     return r;
/*     */   }
/*     */ 
/*     */   public Integer getInteger(Object key) {
/* 238 */     Object o = get(key);
/* 239 */     if (o == null)
/* 240 */       return null;
/* 241 */     Integer r = null;
/*     */     try {
/* 243 */       r = Integer.valueOf(o.toString());
/*     */     } catch (Exception localException) {
/*     */     }
/* 246 */     return r;
/*     */   }
/*     */ 
/*     */   public Long getLong(Object key) {
/* 250 */     Object o = get(key);
/* 251 */     if (o == null)
/* 252 */       return null;
/* 253 */     Long r = null;
/*     */     try {
/* 255 */       r = Long.valueOf(o.toString());
/*     */     } catch (Exception localException) {
/*     */     }
/* 258 */     return r;
/*     */   }
/*     */ 
/*     */   public Boolean getBoolean(Object key) {
/* 262 */     Object o = get(key);
/* 263 */     if (o == null)
/* 264 */       return null;
/* 265 */     Boolean r = null;
/* 266 */     if (o.toString().equals("1"))
/* 267 */       return Boolean.valueOf(true);
/* 268 */     if (o.toString().equals("0"))
/* 269 */       return Boolean.valueOf(false);
/*     */     try {
/* 271 */       r = Boolean.valueOf(o.toString().toLowerCase());
/*     */     } catch (Exception localException) {
/*     */     }
/* 274 */     return r;
/*     */   }
/*     */ 
/*     */   public Date getDate(Object key) {
/* 278 */     Object o = get(key);
/* 279 */     if (o == null)
/* 280 */       return null;
/* 281 */     String s = o.toString();
/* 282 */     Date r = null;
/*     */     try {
/* 284 */       r = Time.sqlDateTimeFormat.parse(s);
/*     */     } catch (Exception e) {
/*     */       try {
/* 287 */         r = Time.sqlDateFormat.parse(s);
/*     */       } catch (Exception e1) {
/*     */         try {
/* 290 */           r = Time.sqlTimeFormat.parse(s);
/*     */         }
/*     */         catch (Exception localException1) {
/*     */         }
/*     */       }
/*     */     }
/* 296 */     return r;
/*     */   }
/*     */ 
/*     */   public <E extends Enum> E getEnum(Object key, Class<E> e) {
/* 300 */     if (e == null)
/* 301 */       return null;
/* 302 */     Object o = get(key);
/* 303 */     if (o == null)
/* 304 */       return null;
/* 305 */     String s = o.toString();
/* 306 */     return Enum.valueOf(e, s);
/*     */   }
/*     */ 
/*     */   public static Integer BooleanAsInt(Boolean bool) {
/* 310 */     if (bool == null)
/* 311 */       return null;
/* 312 */     return Integer.valueOf(bool.booleanValue() ? 1 : 0);
/*     */   }
/*     */ 
/*     */   public static List getColumnAsList(Collection<Record> col, Object key) {
/* 316 */     if (col == null)
/* 317 */       return null;
/* 318 */     List list = new ArrayList();
/* 319 */     for (Record rec : col) {
/* 320 */       if (rec == null)
/* 321 */         list.add(null);
/*     */       else
/* 323 */         list.add(rec.get(key));
/*     */     }
/* 325 */     return list;
/*     */   }
/*     */ 
/*     */   public Record getCopy() {
/* 329 */     Record rec = new Record();
/* 330 */     rec.caseSensitive = this.caseSensitive;
/* 331 */     for (Iterator localIterator = keySet().iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
/* 332 */       rec.put(key, get(key)); }
/* 333 */     return rec;
/*     */   }
/*     */ 
/*     */   private Record getCopyWithClass() {
/* 337 */     Record rec = new Record();
/* 338 */     rec.caseSensitive = this.caseSensitive;
/* 339 */     for (Iterator localIterator = keySet().iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
/* 340 */       Object val = get(key);
/* 341 */       rec.put(key, (val instanceof Class) ? val : val == null ? null : val.getClass());
/*     */     }
/* 343 */     return rec;
/*     */   }
/*     */ 
/*     */   public static Record getSuperSet(Record base, Record add)
/*     */   {
/* 356 */     if (base == null) {
/* 357 */       return add.getCopyWithClass();
/*     */     }
/* 359 */     Record result = base.getCopyWithClass();
/* 360 */     if (add == null)
/* 361 */       return result;
/* 362 */     for (Iterator localIterator = add.keySet().iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
/* 363 */       Object val = add.get(key);
/* 364 */       Object targ = result.get(key);
/* 365 */       if ((targ == null) && (val != null)) {
/* 366 */         result.put(key, val.getClass());
/*     */       }
/*     */     }
/* 369 */     return result;
/*     */   }
/*     */ 
/*     */   private String getLowerKey(Object key) {
/* 373 */     return String.valueOf(key).toLowerCase();
/*     */   }
/*     */ 
/*     */   private Object getRealKey(Object key) {
/* 377 */     if ((key instanceof CharSequence))
/* 378 */       return getLowerKey(key);
/* 379 */     return key;
/*     */   }
/*     */ 
/*     */   public Object get(Object key)
/*     */   {
/* 384 */     key = getRealKey(key);
/* 385 */     Object val = super.get(key);
/* 386 */     if ((val == null) && (!this.caseSensitive))
/* 387 */       key = this.lowerKeys.get(getLowerKey(key));
/* 388 */     return super.get(key);
/*     */   }
/*     */ 
/*     */   public boolean containsKey(Object key)
/*     */   {
/* 393 */     key = getRealKey(key);
/* 394 */     if (super.containsKey(key))
/* 395 */       return true;
/* 396 */     return this.caseSensitive ? false : super.containsKey(this.lowerKeys.get(getLowerKey(key)));
/*     */   }
/*     */ 
/*     */   public Object put(Object key, Object value)
/*     */   {
/* 402 */     key = getRealKey(key);
/* 403 */     this.lowerKeys.put(getLowerKey(key), key);
/* 404 */     Object val = super.put(key, value);
/* 405 */     if ((this.alertOnAmbiguousKeys) && (this.caseSensitive) && (size() != this.lowerKeys.size()))
/* 406 */       throw new RuntimeException("ambiguous key values in lowercase keyset");
/* 407 */     return val;
/*     */   }
/*     */ 
/*     */   public void putAll(Map m)
/*     */   {
/* 412 */     super.putAll(m);
/*     */   }
/*     */ 
/*     */   public Object remove(Object key)
/*     */   {
/* 417 */     key = getRealKey(key);
/* 418 */     this.lowerKeys.remove(getLowerKey(key));
/* 419 */     return super.remove(key);
/*     */   }
/*     */ 
/*     */   public void convertToCaseInSensitive() {
/* 423 */     if (this.caseSensitive) {
/* 424 */       this.lowerKeys.clear();
/* 425 */       for (Iterator localIterator = keySet().iterator(); localIterator.hasNext(); ) { Object key = localIterator.next();
/* 426 */         this.lowerKeys.put(getLowerKey(key), get(key)); }
/* 427 */       this.caseSensitive = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isAlertOnAmbiguousKeys() {
/* 432 */     return this.alertOnAmbiguousKeys;
/*     */   }
/*     */ 
/*     */   public void setAlertOnAmbiguousKeys(boolean alertOnAmbiguousKeys) {
/* 436 */     this.alertOnAmbiguousKeys = alertOnAmbiguousKeys;
/*     */   }
/*     */ 
/*     */   public LinkedHashMap<String, Object> getLowerKeys() {
/* 440 */     return this.lowerKeys;
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.struct.Record
 * JD-Core Version:    0.6.2
 */