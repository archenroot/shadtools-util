/*     */ package com.shadworld.utils;
/*     */ 
/*     */ import com.sun.org.apache.xerces.internal.util.URI;
/*     */ import com.sun.org.apache.xerces.internal.util.URI.MalformedURIException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLDecoder;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.lang.StringEscapeUtils;
/*     */ 
/*     */ public class UrlTools
/*     */ {
/*  44 */   public static Pattern pAddPath = Pattern.compile("([^\\?]*)");
/*  45 */   public static Pattern pQuery = Pattern.compile("[^\\?]*\\?(.*)");
/*  46 */   public static Pattern pHostPath = Pattern.compile("^[\\w]+://[^/]+/([^\\?]*)");
/*  47 */   public static Pattern pProtHostSection = Pattern.compile("^[\\w]+://[^/]+");
/*  48 */   public static Pattern pFullyQualifiedUrl = Pattern.compile("^[\\w]+://.+");
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws UnsupportedEncodingException, URI.MalformedURIException
/*     */   {
/*  27 */     String url = concatUrls("http://google.com/search/in?q=someqskjs#id", "../my/bum/hole?myQery=2323#id");
/*  28 */     System.out.println(url);
/*  29 */     System.out.println(URLEncoder.encode(url, "UTF-8"));
/*  30 */     System.out.println(URLDecoder.decode(URLEncoder.encode(url, "UTF-8"), "UTF-8"));
/*  31 */     System.out.println(new URI(url).toString());
/*  32 */     url = "http%3A%2F%2Fgoogle.com%2Fsearch%2Fin%3Fq%3Dsomeqskjs%23id";
/*  33 */     url = concatUrls(url, "../my/bum/hole?myQery=2323#id");
/*  34 */     url = concatUrls(url, url);
/*  35 */     System.out.println(url);
/*     */ 
/*  37 */     url = "/search?num=100&amp;hl=en&amp;safe=off&amp;client=firefox-a&amp;rls=org.mozilla:en-GB:official&amp;q=ugly+bugs&amp;start=100&amp;sa=N";
/*     */ 
/*  39 */     url = concatUrls("http://google.com/search", url);
/*  40 */     System.out.println(url);
/*     */   }
/*     */ 
/*     */   public static Map<String, String> parseQuery(String url)
/*     */   {
/*  51 */     return parseQuery(url, true);
/*     */   }
/*     */   public static Map<String, String> parseQuery(String url, boolean decode) {
/*  54 */     HashMap params = new HashMap();
/*  55 */     String query = null;
/*  56 */     Matcher m = pQuery.matcher(url);
/*  57 */     if ((m.find()) && (m.groupCount() > 0))
/*  58 */       query = m.group(0);
/*  59 */     if (query == null)
/*  60 */       return params;
/*  61 */     String[] parts = query.split("&");
/*  62 */     for (int i = 0; i < parts.length; i++) {
/*  63 */       String[] bits = parts[i].split("=");
/*  64 */       if (bits.length > 0) {
/*  65 */         String key = bits[0].trim();
/*  66 */         if (key.toLowerCase().startsWith("amp;"))
/*  67 */           key = key.substring(4);
/*  68 */         String val = "";
/*  69 */         if (bits.length > 1) {
/*  70 */           val = bits[1].trim();
/*     */         }
/*  72 */         if (decode) {
/*     */           try {
/*  74 */             key = URLDecoder.decode(key, "UTF-8");
/*     */           } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/*     */           }
/*     */           try {
/*  78 */             val = URLDecoder.decode(val, "UTF-8");
/*     */           } catch (UnsupportedEncodingException localUnsupportedEncodingException1) {
/*     */           }
/*  81 */           key = StringEscapeUtils.unescapeHtml(key);
/*  82 */           val = StringEscapeUtils.unescapeHtml(val);
/*     */         }
/*  84 */         params.put(key, val);
/*     */       }
/*     */     }
/*  87 */     return params;
/*     */   }
/*     */ 
/*     */   public static String concatUrls(String base, String add)
/*     */   {
/*  99 */     return concatUrls(base, add, true);
/*     */   }
/*     */ 
/*     */   public static String concatUrls(String base, String add, boolean resolveRelativeReferences)
/*     */   {
/* 111 */     boolean isBaseEncoded = false;
/*     */     try {
/* 113 */       String newBase = URLDecoder.decode(base, "UTF-8");
/* 114 */       if ((base != null) && (!base.equals(newBase))) {
/* 115 */         isBaseEncoded = true;
/* 116 */         base = newBase;
/*     */       }
/*     */     } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
/*     */     }
/* 120 */     base = base != null ? base.trim() : null;
/* 121 */     if (add == null)
/* 122 */       return base;
/* 123 */     boolean isAddEncoded = false;
/*     */     try {
/* 125 */       String newAdd = URLDecoder.decode(add, "UTF-8");
/* 126 */       if ((add != null) && (!add.equals(newAdd))) {
/* 127 */         isBaseEncoded = true;
/* 128 */         add = newAdd;
/*     */       }
/*     */     } catch (Exception localException) {
/*     */     }
/* 132 */     if (isFullyQualifiedUrl(add))
/* 133 */       return add;
/* 134 */     String host = null;
/* 135 */     Matcher m = pProtHostSection.matcher(base);
/* 136 */     if (m.find()) {
/* 137 */       host = m.group();
/*     */     }
/* 139 */     if (host == null)
/* 140 */       return null;
/* 141 */     StringBuilder sb = new StringBuilder();
/* 142 */     if (isAbsolutePath(add)) {
/* 143 */       return host + concatPaths("", add, resolveRelativeReferences);
/*     */     }
/* 145 */     String hostPath = null;
/* 146 */     m = pHostPath.matcher(base);
/* 147 */     if ((m.find()) && (m.groupCount() > 0)) {
/* 148 */       hostPath = m.group(1);
/*     */     }
/* 150 */     if (hostPath == null) {
/* 151 */       hostPath = "";
/*     */     }
/* 153 */     String addPath = null;
/* 154 */     m = pAddPath.matcher(add);
/* 155 */     if ((m.find()) && (m.groupCount() > 0)) {
/* 156 */       addPath = m.group(1);
/*     */     }
/* 158 */     if (addPath == null)
/* 159 */       addPath = "";
/* 160 */     String path = concatPaths(hostPath, addPath, resolveRelativeReferences);
/* 161 */     if (path == null)
/* 162 */       return null;
/* 163 */     int qindex = add.indexOf("?");
/*     */     String query;
/*     */     String query;
/* 165 */     if (qindex < 0)
/* 166 */       query = "";
/* 167 */     else query = add.substring(qindex);
/* 168 */     sb.setLength(0);
/* 169 */     sb.append(host).append(path).append(query);
/* 170 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String concatPaths(String base, String add, boolean resolveRelativeReferences)
/*     */   {
/* 182 */     boolean isTrailingSlash = add.endsWith("/");
/* 183 */     if (add.isEmpty()) {
/* 184 */       isTrailingSlash = base.endsWith("/");
/*     */     }
/* 186 */     if ((isTrailingSlash) && (!add.isEmpty()))
/* 187 */       add = add.substring(0, add.length() - 1);
/* 188 */     else if ((isTrailingSlash) && (!base.isEmpty())) {
/* 189 */       base = base.substring(0, base.length() - 1);
/*     */     }
/* 191 */     if (add.startsWith("/"))
/* 192 */       add = add.substring(1);
/* 193 */     if (base.startsWith("/"))
/* 194 */       base = base.substring(1);
/* 195 */     if (base.endsWith("/"))
/* 196 */       base = base.substring(0, base.length() - 1);
/* 197 */     String[] baseParts = base.split("/");
/* 198 */     String[] addParts = add.split("/");
/* 199 */     if (base.isEmpty())
/* 200 */       baseParts = new String[0];
/* 201 */     if (add.isEmpty())
/* 202 */       addParts = new String[0];
/* 203 */     ArrayList path = new ArrayList(baseParts.length + addParts.length);
/* 204 */     for (int i = 0; i < baseParts.length; i++) {
/* 205 */       String part = baseParts[i];
/* 206 */       if ((part.equals("..")) && (resolveRelativeReferences)) {
/* 207 */         if (path.size() > 0)
/* 208 */           path.remove(path.size() - 1);
/*     */         else
/* 210 */           return null;
/*     */       }
/* 212 */       else if ((i != baseParts.length - 1) || (!part.contains(".")))
/*     */       {
/* 214 */         if (!part.equals("."))
/* 215 */           path.add(part);
/*     */       }
/*     */     }
/* 218 */     for (int i = 0; i < addParts.length; i++) {
/* 219 */       String part = addParts[i];
/* 220 */       if ((part.equals("..")) && (resolveRelativeReferences)) {
/* 221 */         if (path.size() > 0)
/* 222 */           path.remove(path.size() - 1);
/*     */         else
/* 224 */           return null;
/*     */       }
/* 226 */       else if (!part.equals(".")) {
/* 227 */         path.add(part);
/*     */       }
/*     */     }
/* 230 */     StringBuilder sb = new StringBuilder();
/* 231 */     for (int i = 0; i < path.size(); i++) {
/* 232 */       sb.append("/").append((String)path.get(i));
/*     */     }
/* 234 */     if (isTrailingSlash)
/* 235 */       sb.append("/");
/* 236 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static boolean isFullyQualifiedUrl(String url)
/*     */   {
/* 245 */     if (url == null)
/* 246 */       return false;
/* 247 */     url = url.toLowerCase().trim();
/* 248 */     if (pFullyQualifiedUrl.matcher(url).find())
/* 249 */       return true;
/* 250 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean isAbsolutePath(String urlPath)
/*     */   {
/* 260 */     if (urlPath == null)
/* 261 */       return false;
/* 262 */     urlPath = urlPath.trim();
/* 263 */     return (urlPath.startsWith("/")) || (urlPath.startsWith("?"));
/*     */   }
/*     */ 
/*     */   public static boolean isSameDomain(String url, String otherUrl) {
/*     */     try {
/* 268 */       URL url1 = new URL(url);
/* 269 */       URL url2 = new URL(otherUrl);
/* 270 */       String host1 = url1.getHost().toLowerCase();
/* 271 */       String host2 = url2.getHost().toLowerCase();
/* 272 */       if ((host1 != null) && (host1.equals(host2))) {
/* 273 */         return true;
/*     */       }
/* 275 */       return false; } catch (MalformedURLException e) {
/*     */     }
/* 277 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean isSubdomain(String url, String testUrl)
/*     */   {
/*     */     try
/*     */     {
/* 289 */       URL url1 = new URL(url);
/* 290 */       URL url2 = new URL(testUrl);
/* 291 */       String host1 = url1.getHost().toLowerCase();
/* 292 */       String host2 = url2.getHost().toLowerCase();
/* 293 */       if ((host1 != null) && (host2 != null) && (host2.endsWith(host1)) && (!host1.equals(host2))) {
/* 294 */         return true;
/*     */       }
/* 296 */       return false; } catch (MalformedURLException e) {
/*     */     }
/* 298 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean isChildFolder(String url, String testUrl)
/*     */   {
/*     */     try
/*     */     {
/* 310 */       URL url1 = new URL(url);
/* 311 */       URL url2 = new URL(testUrl);
/* 312 */       String path1 = url1.getPath().toLowerCase();
/* 313 */       String path2 = url2.getPath().toLowerCase();
/* 314 */       if ((path1.isEmpty()) || (path2.startsWith(path1))) {
/* 315 */         return true;
/*     */       }
/* 317 */       return false; } catch (MalformedURLException e) {
/*     */     }
/* 319 */     return false;
/*     */   }
/*     */ 
/*     */   public static String toHttpUrl(String hostName) {
/* 323 */     if (hostName == null)
/* 324 */       return null;
/* 325 */     String lower = hostName.toLowerCase().trim();
/* 326 */     if ((lower.startsWith("http://")) || (lower.startsWith("https://")))
/* 327 */       return hostName;
/* 328 */     if (lower.endsWith("/")) {
/* 329 */       return "http://" + hostName;
/*     */     }
/* 331 */     return "http://" + hostName + "/";
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.UrlTools
 * JD-Core Version:    0.6.2
 */