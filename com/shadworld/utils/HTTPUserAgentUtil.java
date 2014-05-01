/*     */ package com.shadworld.utils;
/*     */ 
/*     */ public class HTTPUserAgentUtil
/*     */ {
/*     */   public static String getFirstVersionNumber(String a_userAgent, int a_position, int numDigits)
/*     */   {
/*   6 */     String ver = getVersionNumber(a_userAgent, a_position);
/*   7 */     if (ver == null)
/*   8 */       return "";
/*   9 */     int i = 0;
/*  10 */     String res = "";
/*  11 */     while ((i < ver.length()) && (i < numDigits)) {
/*  12 */       res = res + String.valueOf(ver.charAt(i));
/*  13 */       i++;
/*     */     }
/*  15 */     return res;
/*     */   }
/*     */ 
/*     */   public static String getVersionNumber(String a_userAgent, int a_position) {
/*  19 */     if (a_position < 0)
/*  20 */       return "";
/*  21 */     StringBuffer res = new StringBuffer();
/*  22 */     int status = 0;
/*     */ 
/*  24 */     while (a_position < a_userAgent.length()) {
/*  25 */       char c = a_userAgent.charAt(a_position);
/*  26 */       switch (status)
/*     */       {
/*     */       case 0:
/*  29 */         if ((c != ' ') && (c != '/'))
/*     */         {
/*  31 */           if ((c == ';') || (c == ')'))
/*  32 */             return "";
/*  33 */           status = 1;
/*     */         }break;
/*     */       case 1:
/*  36 */         if ((c == ';') || (c == '/') || (c == ')') || (c == '(') || (c == '['))
/*  37 */           return res.toString().trim();
/*  38 */         if (c == ' ')
/*  39 */           status = 2;
/*  40 */         res.append(c);
/*  41 */         break;
/*     */       case 2:
/*  44 */         if (((Character.isLetter(c)) && (Character.isLowerCase(c))) || (Character.isDigit(c))) {
/*  45 */           res.append(c);
/*  46 */           status = 1;
/*     */         } else {
/*  48 */           return res.toString().trim();
/*     */         }break;
/*     */       }
/*  51 */       a_position++;
/*     */     }
/*  53 */     return res.toString().trim();
/*     */   }
/*     */ 
/*     */   public static String[] getArray(String a, String b, String c) {
/*  57 */     String[] res = new String[3];
/*  58 */     res[0] = a;
/*  59 */     res[1] = b;
/*  60 */     res[2] = c;
/*  61 */     return res;
/*     */   }
/*     */ 
/*     */   public static String[] getBotName(String userAgent) {
/*  65 */     userAgent = userAgent.toLowerCase();
/*  66 */     int pos = 0;
/*  67 */     String res = null;
/*  68 */     if ((pos = userAgent.indexOf("help.yahoo.com/")) > -1) {
/*  69 */       res = "Yahoo";
/*  70 */       pos += 7;
/*  71 */     } else if ((pos = userAgent.indexOf("google/")) > -1) {
/*  72 */       res = "Google";
/*  73 */       pos += 7;
/*  74 */     } else if ((pos = userAgent.indexOf("msnbot/")) > -1) {
/*  75 */       res = "MSNBot";
/*  76 */       pos += 7;
/*  77 */     } else if ((pos = userAgent.indexOf("googlebot/")) > -1) {
/*  78 */       res = "Google";
/*  79 */       pos += 10;
/*  80 */     } else if ((pos = userAgent.indexOf("webcrawler/")) > -1) {
/*  81 */       res = "WebCrawler";
/*  82 */       pos += 11;
/*     */     }
/*  86 */     else if ((pos = userAgent.indexOf("inktomi")) > -1) {
/*  87 */       res = "Inktomi";
/*  88 */       pos = -1;
/*  89 */     } else if ((pos = userAgent.indexOf("teoma")) > -1) {
/*  90 */       res = "Teoma";
/*  91 */       pos = -1;
/*     */     }
/*  93 */     if (res == null)
/*  94 */       return null;
/*  95 */     return getArray(res, res, res + getVersionNumber(userAgent, pos));
/*     */   }
/*     */ 
/*     */   public static String[] getOS(String userAgent) {
/*  99 */     if (getBotName(userAgent) != null)
/* 100 */       return getArray("Bot", "Bot", "Bot");
/* 101 */     String[] res = (String[])null;
/*     */     int pos;
/* 103 */     if ((pos = userAgent.indexOf("Windows-NT")) > -1) {
/* 104 */       res = getArray("Win", "WinNT", "Win" + getVersionNumber(userAgent, pos + 8));
/* 105 */     } else if (userAgent.indexOf("Windows NT") > -1)
/*     */     {
/* 109 */       if ((pos = userAgent.indexOf("Windows NT 5.1")) > -1)
/* 110 */         res = getArray("Win", "WinXP", "Win" + getVersionNumber(userAgent, pos + 7));
/* 111 */       else if ((pos = userAgent.indexOf("Windows NT 6.0")) > -1)
/* 112 */         res = getArray("Win", "Vista", "Vista" + getVersionNumber(userAgent, pos + 7));
/* 113 */       else if ((pos = userAgent.indexOf("Windows NT 6.1")) > -1)
/* 114 */         res = getArray("Win", "Seven", "Seven " + getVersionNumber(userAgent, pos + 7));
/* 115 */       else if ((pos = userAgent.indexOf("Windows NT 5.0")) > -1)
/* 116 */         res = getArray("Win", "Win2000", "Win" + getVersionNumber(userAgent, pos + 7));
/* 117 */       else if ((pos = userAgent.indexOf("Windows NT 5.2")) > -1)
/* 118 */         res = getArray("Win", "Win2003", "Win" + getVersionNumber(userAgent, pos + 7));
/* 119 */       else if ((pos = userAgent.indexOf("Windows NT 4.0")) > -1)
/* 120 */         res = getArray("Win", "WinNT4", "Win" + getVersionNumber(userAgent, pos + 7));
/* 121 */       else if ((pos = userAgent.indexOf("Windows NT)")) > -1)
/* 122 */         res = getArray("Win", "WinNT", "WinNT");
/* 123 */       else if ((pos = userAgent.indexOf("Windows NT;")) > -1)
/* 124 */         res = getArray("Win", "WinNT", "WinNT");
/*     */       else
/* 126 */         res = getArray("Win", "<B>WinNT?</B>", "<B>WinNT?</B>");
/* 127 */     } else if (userAgent.indexOf("Win") > -1) {
/* 128 */       if (userAgent.indexOf("Windows") > -1) {
/* 129 */         if ((pos = userAgent.indexOf("Windows 98")) > -1)
/* 130 */           res = getArray("Win", "Win98", "Win" + getVersionNumber(userAgent, pos + 7));
/* 131 */         else if ((pos = userAgent.indexOf("Windows_98")) > -1)
/* 132 */           res = getArray("Win", "Win98", "Win" + getVersionNumber(userAgent, pos + 8));
/* 133 */         else if ((pos = userAgent.indexOf("Windows 2000")) > -1)
/* 134 */           res = getArray("Win", "Win2000", "Win" + getVersionNumber(userAgent, pos + 7));
/* 135 */         else if ((pos = userAgent.indexOf("Windows 95")) > -1)
/* 136 */           res = getArray("Win", "Win95", "Win" + getVersionNumber(userAgent, pos + 7));
/* 137 */         else if ((pos = userAgent.indexOf("Windows 9x")) > -1)
/* 138 */           res = getArray("Win", "Win9x", "Win" + getVersionNumber(userAgent, pos + 7));
/* 139 */         else if ((pos = userAgent.indexOf("Windows ME")) > -1)
/* 140 */           res = getArray("Win", "WinME", "Win" + getVersionNumber(userAgent, pos + 7));
/* 141 */         else if ((pos = userAgent.indexOf("Windows CE;")) > -1)
/* 142 */           res = getArray("Win", "WinCE", "WinCE");
/* 143 */         else if ((pos = userAgent.indexOf("Windows 3.1")) > -1) {
/* 144 */           res = getArray("Win", "Win31", "Win" + getVersionNumber(userAgent, pos + 7));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 153 */       if (res == null) {
/* 154 */         if ((pos = userAgent.indexOf("Win98")) > -1)
/* 155 */           res = getArray("Win", "Win98", "Win" + getVersionNumber(userAgent, pos + 3));
/* 156 */         else if ((pos = userAgent.indexOf("Win31")) > -1)
/* 157 */           res = getArray("Win", "Win31", "Win" + getVersionNumber(userAgent, pos + 3));
/* 158 */         else if ((pos = userAgent.indexOf("Win95")) > -1)
/* 159 */           res = getArray("Win", "Win95", "Win" + getVersionNumber(userAgent, pos + 3));
/* 160 */         else if ((pos = userAgent.indexOf("Win 9x")) > -1)
/* 161 */           res = getArray("Win", "Win9x", "Win" + getVersionNumber(userAgent, pos + 3));
/* 162 */         else if ((pos = userAgent.indexOf("WinNT4.0")) > -1)
/* 163 */           res = getArray("Win", "WinNT4", "Win" + getVersionNumber(userAgent, pos + 3));
/* 164 */         else if ((pos = userAgent.indexOf("WinNT")) > -1) {
/* 165 */           res = getArray("Win", "WinNT", "Win" + getVersionNumber(userAgent, pos + 3));
/*     */         }
/*     */       }
/* 168 */       if (res == null)
/* 169 */         if ((pos = userAgent.indexOf("Windows")) > -1)
/* 170 */           res = getArray("Win", "<B>Win?</B>", "<B>Win?" + getVersionNumber(userAgent, pos + 7) + "</B>");
/* 171 */         else if ((pos = userAgent.indexOf("Win")) > -1) {
/* 172 */           res = getArray("Win", "<B>Win?</B>", "<B>Win?" + getVersionNumber(userAgent, pos + 3) + "</B>");
/*     */         }
/*     */         else
/*     */         {
/* 176 */           res = getArray("Win", "<B>Win?</B>", "<B>Win?</B>");
/*     */         }
/* 178 */     } else if ((pos = userAgent.indexOf("Mac OS X")) > -1) {
/* 179 */       if (userAgent.indexOf("iPhone") > -1) {
/* 180 */         pos = userAgent.indexOf("iPhone OS");
/* 181 */         if (userAgent.indexOf("iPod") > -1)
/* 182 */           res = getArray("iOS", "iOS-iPod", 
/* 183 */             "iOS-iPod " + (pos < 0 ? "" : getVersionNumber(userAgent, pos + 9)));
/*     */         else
/* 185 */           res = getArray("iOS", "iOS-iPhone", 
/* 186 */             "iOS-iPhone " + (pos < 0 ? "" : getVersionNumber(userAgent, pos + 9)));
/*     */       }
/* 188 */       else if (userAgent.indexOf("iPad") > -1) {
/* 189 */         pos = userAgent.indexOf("CPU OS");
/* 190 */         res = getArray("iOS", "iOS-iPad", "iOS-iPad " + (pos < 0 ? "" : getVersionNumber(userAgent, pos + 6)));
/*     */       } else {
/* 192 */         res = getArray("Mac", "MacOSX", "MacOS " + getVersionNumber(userAgent, pos + 8));
/*     */       } } else if ((pos = userAgent.indexOf("Android")) > -1) {
/* 194 */       res = getArray("Linux", "Android", "Android " + getVersionNumber(userAgent, pos + 8));
/* 195 */     } else if ((pos = userAgent.indexOf("Mac_PowerPC")) > -1) {
/* 196 */       res = getArray("Mac", "MacPPC", "MacOS " + getVersionNumber(userAgent, pos + 3));
/* 197 */     } else if ((pos = userAgent.indexOf("Macintosh")) > -1) {
/* 198 */       if (userAgent.indexOf("PPC") > -1)
/* 199 */         res = getArray("Mac", "MacPPC", "Mac PPC");
/*     */       else
/* 201 */         res = getArray("Mac?", "Mac?", "MacOS?");
/* 202 */     } else if ((pos = userAgent.indexOf("FreeBSD")) > -1) {
/* 203 */       res = getArray("*BSD", "*BSD FreeBSD", "FreeBSD " + getVersionNumber(userAgent, pos + 7));
/* 204 */     } else if ((pos = userAgent.indexOf("OpenBSD")) > -1) {
/* 205 */       res = getArray("*BSD", "*BSD OpenBSD", "OpenBSD " + getVersionNumber(userAgent, pos + 7));
/* 206 */     } else if ((pos = userAgent.indexOf("Linux")) > -1) {
/* 207 */       String detail = "Linux " + getVersionNumber(userAgent, pos + 5);
/* 208 */       String med = "Linux";
/* 209 */       if ((pos = userAgent.indexOf("Ubuntu/")) > -1) {
/* 210 */         detail = "Ubuntu " + getVersionNumber(userAgent, pos + 7);
/* 211 */         med = med + " Ubuntu";
/*     */       }
/* 213 */       res = getArray("Linux", med, detail);
/* 214 */     } else if ((pos = userAgent.indexOf("CentOS")) > -1) {
/* 215 */       res = getArray("Linux", "Linux CentOS", "CentOS");
/* 216 */     } else if ((pos = userAgent.indexOf("NetBSD")) > -1) {
/* 217 */       res = getArray("*BSD", "*BSD NetBSD", "NetBSD " + getVersionNumber(userAgent, pos + 6));
/* 218 */     } else if ((pos = userAgent.indexOf("Unix")) > -1) {
/* 219 */       res = getArray("Linux", "Linux", "Linux " + getVersionNumber(userAgent, pos + 4));
/* 220 */     } else if ((pos = userAgent.indexOf("SunOS")) > -1) {
/* 221 */       res = getArray("Unix", "SunOS", "SunOS" + getVersionNumber(userAgent, pos + 5));
/* 222 */     } else if ((pos = userAgent.indexOf("IRIX")) > -1) {
/* 223 */       res = getArray("Unix", "IRIX", "IRIX" + getVersionNumber(userAgent, pos + 4));
/* 224 */     } else if ((pos = userAgent.indexOf("SonyEricsson")) > -1) {
/* 225 */       res = getArray("SonyEricsson", "SonyEricsson", "SonyEricsson" + getVersionNumber(userAgent, pos + 12));
/* 226 */     } else if ((pos = userAgent.indexOf("Nokia")) > -1) {
/* 227 */       res = getArray("Nokia", "Nokia", "Nokia" + getVersionNumber(userAgent, pos + 5));
/* 228 */     } else if ((pos = userAgent.indexOf("BlackBerry")) > -1) {
/* 229 */       res = getArray("BlackBerry", "BlackBerry", "BlackBerry" + getVersionNumber(userAgent, pos + 10));
/* 230 */     } else if ((pos = userAgent.indexOf("SymbianOS")) > -1) {
/* 231 */       res = getArray("SymbianOS", "SymbianOS", "SymbianOS" + getVersionNumber(userAgent, pos + 10));
/* 232 */     } else if ((pos = userAgent.indexOf("BeOS")) > -1) {
/* 233 */       res = getArray("BeOS", "BeOS", "BeOS");
/* 234 */     } else if ((pos = userAgent.indexOf("Nintendo Wii")) > -1) {
/* 235 */       res = getArray("Nintendo Wii", "Nintendo Wii", "Nintendo Wii" + getVersionNumber(userAgent, pos + 10));
/* 236 */     } else if ((pos = userAgent.indexOf("J2ME/MIDP")) > -1) {
/* 237 */       res = getArray("Java", "J2ME", "J2ME/MIDP");
/*     */     } else {
/* 239 */       res = getArray("<b>?</b>", "<b>?</b>", "<b>?</b>");
/* 240 */     }return res;
/*     */   }
/*     */ 
/*     */   public static String[] getBrowser(String userAgent)
/*     */   {
/*     */     String[] botName;
/* 245 */     if ((botName = getBotName(userAgent)) != null)
/* 246 */       return botName;
/* 247 */     String[] res = (String[])null;
/*     */     int pos;
/* 249 */     if ((pos = userAgent.indexOf("Lotus-Notes/")) > -1) {
/* 250 */       res = getArray("LotusNotes", "LotusNotes", "LotusNotes" + getVersionNumber(userAgent, pos + 12));
/* 251 */     } else if ((pos = userAgent.indexOf("Opera")) > -1) {
/* 252 */       String ver = getVersionNumber(userAgent, pos + 5);
/* 253 */       res = getArray("Opera", "Opera" + getFirstVersionNumber(userAgent, pos + 5, 1), "Opera" + ver);
/* 254 */       if ((pos = userAgent.indexOf("Opera Mini/")) > -1) {
/* 255 */         String ver2 = getVersionNumber(userAgent, pos + 11);
/* 256 */         res = getArray("Opera", "Opera Mini", "Opera Mini " + ver2);
/* 257 */       } else if ((pos = userAgent.indexOf("Opera Mobi/")) > -1) {
/* 258 */         String ver2 = getVersionNumber(userAgent, pos + 11);
/* 259 */         res = getArray("Opera", "Opera Mobi", "Opera Mobi " + ver2);
/*     */       }
/* 261 */     } else if (userAgent.indexOf("MSIE") > -1) {
/* 262 */       if ((pos = userAgent.indexOf("MSIE 6.0")) > -1)
/* 263 */         res = getArray("MSIE", "MSIE6", "MSIE" + getVersionNumber(userAgent, pos + 4));
/* 264 */       else if ((pos = userAgent.indexOf("MSIE 5.0")) > -1)
/* 265 */         res = getArray("MSIE", "MSIE5", "MSIE" + getVersionNumber(userAgent, pos + 4));
/* 266 */       else if ((pos = userAgent.indexOf("MSIE 5.5")) > -1)
/* 267 */         res = getArray("MSIE", "MSIE5.5", "MSIE" + getVersionNumber(userAgent, pos + 4));
/* 268 */       else if ((pos = userAgent.indexOf("MSIE 5.")) > -1)
/* 269 */         res = getArray("MSIE", "MSIE5.x", "MSIE" + getVersionNumber(userAgent, pos + 4));
/* 270 */       else if ((pos = userAgent.indexOf("MSIE 4")) > -1)
/* 271 */         res = getArray("MSIE", "MSIE4", "MSIE" + getVersionNumber(userAgent, pos + 4));
/* 272 */       else if (((pos = userAgent.indexOf("MSIE 7")) > -1) && (userAgent.indexOf("Trident/4.0") < 0))
/* 273 */         res = getArray("MSIE", "MSIE7", "MSIE" + getVersionNumber(userAgent, pos + 4));
/* 274 */       else if (((pos = userAgent.indexOf("MSIE 8")) > -1) || (userAgent.indexOf("Trident/4.0") > -1))
/* 275 */         res = getArray("MSIE", "MSIE8", "MSIE" + getVersionNumber(userAgent, pos + 4));
/* 276 */       else if (((pos = userAgent.indexOf("MSIE 9")) > -1) || (userAgent.indexOf("Trident/4.0") > -1))
/* 277 */         res = getArray("MSIE", "MSIE9", "MSIE" + getVersionNumber(userAgent, pos + 4));
/*     */       else
/* 279 */         res = getArray("MSIE", "<B>MSIE?</B>", 
/* 280 */           "<B>MSIE?" + getVersionNumber(userAgent, userAgent.indexOf("MSIE") + 4) + "</B>");
/* 281 */     } else if ((pos = userAgent.indexOf("Gecko/")) > -1) {
/* 282 */       res = getArray("Gecko", "Gecko", "Gecko" + getFirstVersionNumber(userAgent, pos + 5, 4));
/* 283 */       if ((pos = userAgent.indexOf("Camino/")) > -1)
/*     */       {
/*     */         int tmp789_788 = 1;
/*     */         String[] tmp789_787 = res; tmp789_787[tmp789_788] = (tmp789_787[tmp789_788] + "(Camino)");
/*     */         int tmp814_813 = 2;
/*     */         String[] tmp814_812 = res; tmp814_812[tmp814_813] = (tmp814_812[tmp814_813] + "(Camino" + getVersionNumber(userAgent, pos + 7) + ")");
/* 286 */       } else if ((pos = userAgent.indexOf("Chimera/")) > -1)
/*     */       {
/*     */         int tmp872_871 = 1;
/*     */         String[] tmp872_870 = res; tmp872_870[tmp872_871] = (tmp872_870[tmp872_871] + "(Chimera)");
/*     */         int tmp897_896 = 2;
/*     */         String[] tmp897_895 = res; tmp897_895[tmp897_896] = (tmp897_895[tmp897_896] + "(Chimera" + getVersionNumber(userAgent, pos + 8) + ")");
/* 289 */       } else if ((pos = userAgent.indexOf("Firebird/")) > -1)
/*     */       {
/*     */         int tmp955_954 = 1;
/*     */         String[] tmp955_953 = res; tmp955_953[tmp955_954] = (tmp955_953[tmp955_954] + "(Firebird)");
/*     */         int tmp980_979 = 2;
/*     */         String[] tmp980_978 = res; tmp980_978[tmp980_979] = (tmp980_978[tmp980_979] + "(Firebird" + getVersionNumber(userAgent, pos + 9) + ")");
/* 292 */       } else if ((pos = userAgent.indexOf("Phoenix/")) > -1)
/*     */       {
/*     */         int tmp1038_1037 = 1;
/*     */         String[] tmp1038_1036 = res; tmp1038_1036[tmp1038_1037] = (tmp1038_1036[tmp1038_1037] + "(Phoenix)");
/*     */         int tmp1063_1062 = 2;
/*     */         String[] tmp1063_1061 = res; tmp1063_1061[tmp1063_1062] = (tmp1063_1061[tmp1063_1062] + "(Phoenix" + getVersionNumber(userAgent, pos + 8) + ")");
/* 295 */       } else if ((pos = userAgent.indexOf("Galeon/")) > -1)
/*     */       {
/*     */         int tmp1121_1120 = 1;
/*     */         String[] tmp1121_1119 = res; tmp1121_1119[tmp1121_1120] = (tmp1121_1119[tmp1121_1120] + "(Galeon)");
/*     */         int tmp1146_1145 = 2;
/*     */         String[] tmp1146_1144 = res; tmp1146_1144[tmp1146_1145] = (tmp1146_1144[tmp1146_1145] + "(Galeon" + getVersionNumber(userAgent, pos + 7) + ")");
/* 298 */       } else if ((pos = userAgent.indexOf("Firefox/")) > -1)
/*     */       {
/*     */         int tmp1204_1203 = 1;
/*     */         String[] tmp1204_1202 = res; tmp1204_1202[tmp1204_1203] = (tmp1204_1202[tmp1204_1203] + "(Firefox)");
/*     */         int tmp1229_1228 = 2;
/*     */         String[] tmp1229_1227 = res; tmp1229_1227[tmp1229_1228] = (tmp1229_1227[tmp1229_1228] + "(Firefox" + getVersionNumber(userAgent, pos + 8) + ")");
/* 301 */       } else if ((pos = userAgent.indexOf("Netscape/")) > -1) {
/* 302 */         if ((pos = userAgent.indexOf("Netscape/6")) > -1)
/*     */         {
/*     */           int tmp1300_1299 = 1;
/*     */           String[] tmp1300_1298 = res; tmp1300_1298[tmp1300_1299] = (tmp1300_1298[tmp1300_1299] + "(NS6)");
/*     */           int tmp1325_1324 = 2;
/*     */           String[] tmp1325_1323 = res; tmp1325_1323[tmp1325_1324] = (tmp1325_1323[tmp1325_1324] + "(NS" + getVersionNumber(userAgent, pos + 9) + ")");
/* 305 */         } else if ((pos = userAgent.indexOf("Netscape/7")) > -1)
/*     */         {
/*     */           int tmp1383_1382 = 1;
/*     */           String[] tmp1383_1381 = res; tmp1383_1381[tmp1383_1382] = (tmp1383_1381[tmp1383_1382] + "(NS7)");
/*     */           int tmp1408_1407 = 2;
/*     */           String[] tmp1408_1406 = res; tmp1408_1406[tmp1408_1407] = (tmp1408_1406[tmp1408_1407] + "(NS" + getVersionNumber(userAgent, pos + 9) + ")");
/* 308 */         } else if ((pos = userAgent.indexOf("Netscape/8")) > -1)
/*     */         {
/*     */           int tmp1466_1465 = 1;
/*     */           String[] tmp1466_1464 = res; tmp1466_1464[tmp1466_1465] = (tmp1466_1464[tmp1466_1465] + "(NS8)");
/*     */           int tmp1491_1490 = 2;
/*     */           String[] tmp1491_1489 = res; tmp1491_1489[tmp1491_1490] = (tmp1491_1489[tmp1491_1490] + "(NS" + getVersionNumber(userAgent, pos + 9) + ")");
/* 311 */         } else if ((pos = userAgent.indexOf("Netscape/9")) > -1)
/*     */         {
/*     */           int tmp1549_1548 = 1;
/*     */           String[] tmp1549_1547 = res; tmp1549_1547[tmp1549_1548] = (tmp1549_1547[tmp1549_1548] + "(NS9)");
/*     */           int tmp1574_1573 = 2;
/*     */           String[] tmp1574_1572 = res; tmp1574_1572[tmp1574_1573] = (tmp1574_1572[tmp1574_1573] + "(NS" + getVersionNumber(userAgent, pos + 9) + ")");
/*     */         }
/*     */         else
/*     */         {
/*     */           int tmp1619_1618 = 1;
/*     */           String[] tmp1619_1617 = res; tmp1619_1617[tmp1619_1618] = (tmp1619_1617[tmp1619_1618] + "(NS?)");
/*     */           int tmp1644_1643 = 2;
/*     */           String[] tmp1644_1642 = res; tmp1644_1642[tmp1644_1643] = (tmp1644_1642[tmp1644_1643] + "(NS?" + getVersionNumber(userAgent, userAgent.indexOf("Netscape/") + 9) + ")");
/*     */         }
/*     */       }
/* 319 */     } else if ((pos = userAgent.indexOf("Netscape/")) > -1) {
/* 320 */       if ((pos = userAgent.indexOf("Netscape/4")) > -1)
/* 321 */         res = getArray("NS", "NS4", "NS" + getVersionNumber(userAgent, pos + 9));
/*     */       else
/* 323 */         res = getArray("NS", "NS?", "NS?" + getVersionNumber(userAgent, pos + 9));
/* 324 */     } else if ((pos = userAgent.indexOf("Chrome/")) > -1) {
/* 325 */       res = getArray("KHTML", "KHTML(Chrome)", "KHTML(Chrome" + getVersionNumber(userAgent, pos + 6) + ")");
/* 326 */     } else if ((pos = userAgent.indexOf("Safari/")) > -1) {
/* 327 */       res = getArray("KHTML", "KHTML(Safari)", "KHTML(Safari" + getVersionNumber(userAgent, pos + 6) + ")");
/* 328 */     } else if ((pos = userAgent.indexOf("Konqueror/")) > -1) {
/* 329 */       res = getArray("KHTML", "KHTML(Konqueror)", "KHTML(Konqueror" + getVersionNumber(userAgent, pos + 9) + ")");
/* 330 */     } else if ((pos = userAgent.indexOf("KHTML")) > -1) {
/* 331 */       res = getArray("KHTML", "KHTML?", "KHTML?(" + getVersionNumber(userAgent, pos + 5) + ")");
/* 332 */     } else if ((pos = userAgent.indexOf("NetFront")) > -1) {
/* 333 */       res = getArray("NetFront", "NetFront", "NetFront " + getVersionNumber(userAgent, pos + 8));
/* 334 */     } else if ((pos = userAgent.indexOf("BlackBerry")) > -1) {
/* 335 */       pos = userAgent.indexOf("/", pos + 2);
/* 336 */       res = getArray("BlackBerry", "BlackBerry", "BlackBerry" + getVersionNumber(userAgent, pos + 1));
/*     */     }
/* 341 */     else if ((userAgent.indexOf("Mozilla/4.") == 0) && (userAgent.indexOf("Mozilla/4.0") < 0) && 
/* 342 */       (userAgent.indexOf("Mozilla/4.5 ") < 0)) {
/* 343 */       res = getArray("Communicator", "Communicator", "Communicator" + getVersionNumber(userAgent, pos + 8));
/*     */     } else {
/* 345 */       return getArray("<B>?</B>", "<B>?</B>", "<B>?</B>");
/* 346 */     }return res;
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.HTTPUserAgentUtil
 * JD-Core Version:    0.6.2
 */