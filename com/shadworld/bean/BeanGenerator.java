/*     */ package com.shadworld.bean;
/*     */ 
/*     */ import com.shadworld.utils.L;
/*     */ import java.beans.BeanInfo;
/*     */ import java.beans.IntrospectionException;
/*     */ import java.beans.Introspector;
/*     */ import java.beans.MethodDescriptor;
/*     */ import java.beans.ParameterDescriptor;
/*     */ import java.beans.PropertyDescriptor;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import sun.reflect.generics.reflectiveObjects.NotImplementedException;
/*     */ 
/*     */ public class BeanGenerator
/*     */ {
/*     */   BeansGenerator parent;
/*     */   Class cls;
/*     */   String targName;
/*     */   String srcPkg;
/*     */   String targPkg;
/*     */   BeanInfo beanInfo;
/*     */   List<Class> interfaces;
/*     */   Class superClass;
/*  49 */   boolean extend = false;
/*  50 */   boolean implement = true;
/*     */ 
/*  52 */   Set<String> propNames = new HashSet();
/*     */   List<PropertyDescriptor> props;
/*     */   List<MethodDescriptor> methods;
/*  56 */   List<Class> importClasses = new ArrayList();
/*     */ 
/*  58 */   boolean doFieldComments = false;
/*  59 */   boolean doMethodComments = true;
/*  60 */   boolean useSeeCommentsForProperties = true;
/*     */ 
/*  62 */   boolean ignoreMethods = false;
/*  63 */   boolean ignoreProperties = false;
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws IntrospectionException
/*     */   {
/*  36 */     BeanGenerator gen = new BeanGenerator(new BeansGenerator(), BeanGenerator.class, "BG", "com.shadword.beangen");
/*  37 */     L.println(gen.generateCode());
/*     */   }
/*     */ 
/*     */   public BeanGenerator(BeansGenerator parent, Class cls, String targName, String targPkg)
/*     */     throws IntrospectionException
/*     */   {
/*  68 */     this.parent = parent;
/*  69 */     this.cls = cls;
/*  70 */     this.targPkg = targPkg;
/*  71 */     this.targName = targName;
/*  72 */     this.beanInfo = Introspector.getBeanInfo(cls);
/*  73 */     this.props = new ArrayList(Arrays.asList(this.beanInfo.getPropertyDescriptors()));
/*  74 */     this.methods = new ArrayList(Arrays.asList(this.beanInfo.getMethodDescriptors()));
/*  75 */     this.srcPkg = cls.getPackage().getName();
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/*  82 */     for (PropertyDescriptor prop : this.props) {
/*  83 */       this.propNames.add(prop.getName());
/*     */     }
/*  85 */     List removedMethods = new ArrayList();
/*  86 */     for (MethodDescriptor method : this.methods) {
/*  87 */       if (method.getMethod().getParameterTypes().length <= 1) {
/*  88 */         for (PropertyDescriptor prop : this.props) {
/*  89 */           if ((method.getMethod().equals(prop.getReadMethod())) || 
/*  90 */             (method.getMethod().equals(prop.getWriteMethod()))) {
/*  91 */             removedMethods.add(method);
/*  92 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*  97 */     this.methods.removeAll(removedMethods);
/*     */ 
/*  99 */     if (this.methods.size() > 0) {
/* 100 */       this.importClasses.add(NotImplementedException.class);
/*     */     }
/*     */ 
/* 104 */     for (PropertyDescriptor prop : this.props)
/* 105 */       indexImportClass(prop.getPropertyType());
/*     */     int j;
/*     */     int i;
/* 107 */     for (??? = this.methods.iterator(); ???.hasNext(); 
/* 109 */       i < j)
/*     */     {
/* 107 */       MethodDescriptor method = (MethodDescriptor)???.next();
/* 108 */       indexImportClass(method.getMethod().getReturnType());
/*     */       Class[] arrayOfClass;
/* 109 */       j = (arrayOfClass = method.getMethod().getParameterTypes()).length; i = 0; continue; Class c = arrayOfClass[i];
/* 110 */       indexImportClass(c);
/*     */ 
/* 109 */       i++;
/*     */     }
/*     */ 
/* 114 */     if ((this.superClass != null) && (this.extend))
/* 115 */       indexImportClass(this.superClass);
/* 116 */     if ((this.implement) && (this.interfaces != null))
/* 117 */       for (Class c : this.interfaces)
/* 118 */         indexImportClass(c);
/*     */   }
/*     */ 
/*     */   public String generateCode()
/*     */   {
/* 124 */     init();
/* 125 */     StringBuilder sb = new StringBuilder();
/*     */ 
/* 127 */     if (this.doMethodComments) {
/* 128 */       sb.append("\n/* auto-genned class code from original class: ").append(this.cls.getName()).append(" */\n\n");
/*     */     }
/* 130 */     sb.append("package ").append(this.targPkg).append(";\n\n");
/*     */ 
/* 133 */     Collections.sort(this.importClasses, new Comparator()
/*     */     {
/*     */       public int compare(Class o1, Class o2) {
/* 136 */         return o1.getName().compareToIgnoreCase(o2.getName());
/*     */       }
/*     */     });
/* 139 */     for (Class importClass : this.importClasses) {
/* 140 */       sb.append("import ").append(importClass.getName()).append(";\n");
/*     */     }
/*     */ 
/* 144 */     sb.append("\n\npublic class ").append(this.targName).append(" ");
/*     */ 
/* 146 */     if ((this.extend) && (this.superClass != null)) {
/* 147 */       sb.append("extends ").append(this.superClass.getSimpleName()).append(" ");
/*     */     }
/*     */ 
/* 150 */     if ((this.implement) && (this.interfaces != null) && (this.interfaces.size() > 0)) {
/* 151 */       sb.append("implements ");
/* 152 */       int len = sb.length();
/* 153 */       for (Iterator localIterator2 = this.interfaces.iterator(); localIterator2.hasNext(); ) { c = (Class)localIterator2.next();
/* 154 */         if (sb.length() > len)
/* 155 */           sb.append(", ");
/* 156 */         sb.append(((Class)c).getSimpleName());
/*     */       }
/*     */     }
/*     */ 
/* 160 */     sb.append("{\n\n");
/*     */ 
/* 164 */     for (Object c = this.props.iterator(); ((Iterator)c).hasNext(); ) { PropertyDescriptor prop = (PropertyDescriptor)((Iterator)c).next();
/*     */ 
/* 170 */       sb.append("\t").append(getClassString(prop.getPropertyType()));
/* 171 */       sb.append(" ").append(prop.getName()).append(";\n");
/*     */     }
/* 173 */     sb.append("\n");
/*     */ 
/* 176 */     for (c = this.methods.iterator(); ((Iterator)c).hasNext(); ) { MethodDescriptor method = (MethodDescriptor)((Iterator)c).next();
/* 177 */       if ((this.doMethodComments) && 
/* 178 */         (this.useSeeCommentsForProperties)) {
/* 179 */         sb.append(getSeeComment(method.getMethod()));
/*     */       }
/*     */ 
/* 182 */       sb.append("\t");
/* 183 */       sb.append(getMethodSignatureString(method.getMethod(), method, null));
/* 184 */       sb.append(" {\n");
/* 185 */       sb.append("\t\tthrow new NotImplementedException();\n\t}\n\n");
/*     */     }
/*     */ 
/* 189 */     for (c = this.props.iterator(); ((Iterator)c).hasNext(); ) { PropertyDescriptor prop = (PropertyDescriptor)((Iterator)c).next();
/*     */ 
/* 191 */       if (prop.getReadMethod() != null) {
/* 192 */         if ((this.doMethodComments) && 
/* 193 */           (this.useSeeCommentsForProperties)) {
/* 194 */           sb.append(getSeeComment(prop.getReadMethod()));
/*     */         }
/*     */ 
/* 197 */         sb.append("\t");
/* 198 */         sb.append(getMethodSignatureString(prop.getReadMethod(), null, prop));
/* 199 */         sb.append(" {\n");
/* 200 */         sb.append("\t\treturn ").append(prop.getName()).append(";\n\t}\n\n");
/*     */       }
/*     */ 
/* 203 */       if (prop.getWriteMethod() != null) {
/* 204 */         if ((this.doMethodComments) && 
/* 205 */           (this.useSeeCommentsForProperties)) {
/* 206 */           sb.append(getSeeComment(prop.getWriteMethod()));
/*     */         }
/*     */ 
/* 209 */         sb.append("\t");
/* 210 */         sb.append(getMethodSignatureString(prop.getWriteMethod(), null, prop));
/* 211 */         sb.append(" {\n");
/* 212 */         sb.append("\t\tthis.").append(prop.getName()).append(" = ").append(prop.getName()).append(";\n\t}\n\n");
/*     */       }
/*     */     }
/*     */ 
/* 216 */     sb.append("}\n");
/*     */ 
/* 220 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private StringBuilder getMethodSignatureString(Method method, MethodDescriptor mInfo, PropertyDescriptor pInfo) {
/* 224 */     StringBuilder sb = new StringBuilder();
/* 225 */     int mod = method.getModifiers();
/* 226 */     if (Modifier.isPublic(mod))
/* 227 */       sb.append("public ");
/* 228 */     if (Modifier.isProtected(mod))
/* 229 */       sb.append("protected ");
/* 230 */     if (Modifier.isPrivate(mod))
/* 231 */       sb.append("private ");
/* 232 */     if (Modifier.isSynchronized(mod))
/* 233 */       sb.append("synchronized ");
/* 234 */     if (Modifier.isTransient(mod))
/* 235 */       sb.append("transient ");
/* 236 */     if (Modifier.isAbstract(mod))
/* 237 */       sb.append("abstract ");
/* 238 */     if (Modifier.isStatic(mod))
/* 239 */       sb.append("static ");
/* 240 */     if (Modifier.isFinal(mod))
/* 241 */       sb.append("final ");
/* 242 */     sb.append(getClassString(method.getReturnType())).append(" ");
/* 243 */     sb.append(method.getName()).append("(");
/* 244 */     int len = sb.length();
/*     */ 
/* 246 */     if (mInfo != null) {
/* 247 */       ParameterDescriptor[] params = mInfo.getParameterDescriptors();
/* 248 */       Class[] paramTypes = mInfo.getMethod().getParameterTypes();
/* 249 */       if (params != null) {
/* 250 */         for (int i = 0; i < params.length; i++) {
/* 251 */           ParameterDescriptor param = params[i];
/* 252 */           Class c = paramTypes[i];
/* 253 */           if (sb.length() > len)
/* 254 */             sb.append(", ");
/* 255 */           sb.append(getClassString(c)).append(" ");
/* 256 */           sb.append(param.getName());
/*     */         }
/*     */       } else {
/* 259 */         HashMap map = new HashMap();
/* 260 */         for (int i = 0; i < paramTypes.length; i++) {
/* 261 */           Class c = paramTypes[i];
/* 262 */           if (sb.length() > len)
/* 263 */             sb.append(", ");
/* 264 */           sb.append(getClassString(c)).append(" ");
/* 265 */           Integer num = (Integer)map.get(c);
/* 266 */           if (num == null)
/* 267 */             num = Integer.valueOf(0);
/* 268 */           map.put(c, num = Integer.valueOf(num.intValue() + 1));
/* 269 */           sb.append(c.getSimpleName().toLowerCase()).append(num);
/*     */         }
/*     */       }
/* 272 */     } else if (pInfo != null) {
/* 273 */       if ((pInfo.getWriteMethod() != null) && (pInfo.getWriteMethod().equals(method))) {
/* 274 */         sb.append(getClassString(pInfo.getPropertyType())).append(" ");
/* 275 */         sb.append(pInfo.getName());
/* 276 */       } else if (pInfo.getReadMethod() != null) { pInfo.getReadMethod().equals(method); }
/*     */ 
/*     */     }
/* 279 */     sb.append(")");
/* 280 */     return sb;
/*     */   }
/*     */ 
/*     */   private StringBuilder getClassString(Class c) {
/* 284 */     StringBuilder sb = new StringBuilder(c.getSimpleName());
/*     */     try {
/* 286 */       ParameterizedType parameterizedType = (ParameterizedType)c.getGenericSuperclass();
/* 287 */       if ((parameterizedType != null) && (parameterizedType.getActualTypeArguments().length > 0)) {
/* 288 */         sb.append("<");
/* 289 */         int len = sb.length();
/* 290 */         for (Type type : parameterizedType.getActualTypeArguments()) {
/* 291 */           if (len < sb.length())
/* 292 */             sb.append(", ");
/* 293 */           sb.append(getClassString(type.getClass()));
/*     */         }
/* 295 */         sb.append(">");
/*     */       }
/*     */     }
/*     */     catch (ClassCastException localClassCastException) {
/*     */     }
/* 300 */     return sb;
/*     */   }
/*     */ 
/*     */   private StringBuilder getSeeComment(Method method) {
/* 304 */     StringBuilder sb = new StringBuilder("\t/*\n\t *@see ");
/* 305 */     sb.append(method.getDeclaringClass().getName()).append("#");
/* 306 */     sb.append(method.getName()).append("(");
/* 307 */     int len = sb.length();
/* 308 */     for (Class c : method.getParameterTypes()) {
/* 309 */       if (len < sb.length())
/* 310 */         sb.append(", ");
/* 311 */       sb.append(c.getName());
/*     */     }
/* 313 */     sb.append(")\n");
/* 314 */     sb.append("\t */\n");
/* 315 */     return sb;
/*     */   }
/*     */ 
/*     */   private void indexImportClass(Class c) {
/* 319 */     indexImportClass(c, null);
/*     */   }
/*     */ 
/*     */   private void indexImportClass(Class c, Set<Class> indexed) {
/* 323 */     if (c == null)
/* 324 */       return;
/* 325 */     if (indexed == null)
/* 326 */       indexed = new HashSet();
/* 327 */     if (indexed.contains(c))
/* 328 */       return;
/* 329 */     indexed.add(c);
/* 330 */     if (c.isArray()) {
/* 331 */       c = c.getGenericSuperclass().getClass();
/* 332 */       indexed.add(c);
/*     */     }
/* 334 */     String pkg = c.getPackage() == null ? null : c.getPackage().getName();
/* 335 */     if ((pkg == null) && (!c.isPrimitive()))
/* 336 */       L.println(null);
/* 337 */     if ((c.isPrimitive()) || ((pkg != null) && ((pkg.equals("java.lang")) || (pkg.equals(this.targPkg)))))
/* 338 */       return;
/* 339 */     if (!this.importClasses.contains(c)) {
/* 340 */       this.importClasses.add(c);
/*     */     }
/* 342 */     ParameterizedType parameterizedType = (ParameterizedType)c.getGenericSuperclass();
/* 343 */     if (parameterizedType != null)
/* 344 */       for (Type type : parameterizedType.getActualTypeArguments())
/* 345 */         indexImportClass(type.getClass(), indexed);
/*     */   }
/*     */ 
/*     */   private PropertyDescriptor getProperty(String name)
/*     */   {
/* 352 */     for (PropertyDescriptor prop : this.props) {
/* 353 */       if (prop.getName().equals(name))
/* 354 */         return prop;
/*     */     }
/* 356 */     return null;
/*     */   }
/*     */ 
/*     */   public List<Class> getInterfaces()
/*     */   {
/* 363 */     return this.interfaces;
/*     */   }
/*     */ 
/*     */   public void setInterfaces(List<Class> interfaces)
/*     */   {
/* 371 */     this.interfaces = interfaces;
/*     */   }
/*     */ 
/*     */   public Class getSuperClass()
/*     */   {
/* 378 */     return this.superClass;
/*     */   }
/*     */ 
/*     */   public void setSuperClass(Class superClass)
/*     */   {
/* 386 */     this.superClass = superClass;
/*     */   }
/*     */ 
/*     */   public boolean isExtend()
/*     */   {
/* 393 */     return this.extend;
/*     */   }
/*     */ 
/*     */   public void setExtend(boolean extend)
/*     */   {
/* 401 */     this.extend = extend;
/*     */   }
/*     */ 
/*     */   public boolean isImplement()
/*     */   {
/* 408 */     return this.implement;
/*     */   }
/*     */ 
/*     */   public void setImplement(boolean implement)
/*     */   {
/* 416 */     this.implement = implement;
/*     */   }
/*     */ 
/*     */   public boolean isDoFieldComments()
/*     */   {
/* 423 */     return this.doFieldComments;
/*     */   }
/*     */ 
/*     */   public void setDoFieldComments(boolean doFieldComments)
/*     */   {
/* 431 */     this.doFieldComments = doFieldComments;
/*     */   }
/*     */ 
/*     */   public boolean isDoMethodComments()
/*     */   {
/* 438 */     return this.doMethodComments;
/*     */   }
/*     */ 
/*     */   public void setDoMethodComments(boolean doMethodComments)
/*     */   {
/* 446 */     this.doMethodComments = doMethodComments;
/*     */   }
/*     */ 
/*     */   public boolean isUseSeeCommentsForProperties()
/*     */   {
/* 453 */     return this.useSeeCommentsForProperties;
/*     */   }
/*     */ 
/*     */   public void setUseSeeCommentsForProperties(boolean useSeeCommentsForProperties)
/*     */   {
/* 461 */     this.useSeeCommentsForProperties = useSeeCommentsForProperties;
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.bean.BeanGenerator
 * JD-Core Version:    0.6.2
 */