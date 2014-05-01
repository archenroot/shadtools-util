/*     */ package com.shadworld.utils;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class StringCleaner
/*     */ {
/*  12 */   private static String stopWordString = "a; about; above; across; after; afterwards; again; against; all; almost; alone; along; already; also; although; always; am; among; amongst; amoungst; amount; an; and; another; any; anyhow; anyone; anything; anyway; anywhere; are; around; as; at; back; be; became; because; become; becomes; becoming; been; before; beforehand; behind; being; below; beside; besides; between; beyond; bill; both; bottom; but; by; call; can; cannot; cant; co; computer; con; could; couldnt; cry; de; describe; detail; do; done; down; due; during; each; eg; eight; either; eleven; else; elsewhere; empty; enough; etc; even; ever; every; everyone; everything; everywhere; except; few; fifteen; fify; fill; find; fire; first; five; for; former; formerly; forty; found; four; from; front; full; further; get; give; go; had; has; hasnt; have; he; hence; her; here; hereafter; hereby; herein; hereupon; hers; herse�; him; himse�; his; how; however; hundred; i; ie; if; in; inc; indeed; interest; into; is; it; its; itse�; keep; last; latter; latterly; least; less; ltd; made; many; may; me; meanwhile; might; mill; mine; more; moreover; most; mostly; move; much; must; my; myse�; name; namely; neither; never; nevertheless; next; nine; no; nobody; none; noone; nor; not; nothing; now; nowhere; of; off; often; on; once; one; only; onto; or; other; others; otherwise; our; ours; ourselves; out; over; own; part; per; perhaps; please; put; rather; re; same; see; seem; seemed; seeming; seems; serious; several; she; should; show; side; since; sincere; six; sixty; so; some; somehow; someone; something; sometime; sometimes; somewhere; still; such; system; take; ten; than; that; the; their; them; themselves; then; thence; there; thereafter; thereby; therefore; therein; thereupon; these; they; thick; thin; third; this; those; though; three; through; throughout; thru; thus; to; together; too; top; toward; towards; twelve; twenty; two; un; under; until; up; upon; us; very; via; was; we; well; were; what; whatever; when; whence; whenever; where; whereafter; whereas; whereby; wherein; whereupon; wherever; whether; which; while; whither; who; whoever; whole; whom; whose; why; will; with; within; without; would; yet; you; your; yours; yourself; yourselves";
/*     */ 
/*  14 */   public static HashSet<String> stopWords = new HashSet(StringTools.split(stopWordString, ";", true, false));
/*     */   private String string;
/*  33 */   private static Pattern pNonWords = Pattern.compile("\\S*[^\\w\\s']+\\S*", 34);
/*     */ 
/*  44 */   private static Pattern pPunctuationEnd = Pattern.compile("(\\w+)[^\\w\\s]+(\\s)", 34);
/*  45 */   private static Pattern pPunctuationBegin = Pattern.compile("(\\s)[^\\w\\s]+(\\w+)", 34);
/*     */ 
/*  57 */   private static Pattern pMultiSpace = Pattern.compile(" {2,}", 34);
/*     */ 
/*  68 */   private static Pattern pWord = Pattern.compile("\\S+");
/*  69 */   private static Pattern pNonLetter = Pattern.compile("[^a-zA-Z]");
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/*  18 */     String s = "a cat s#t on? ##, \"the\" $mat didn't it";
/*  19 */     s = "Simple, Easy Forex Trading System ABSOLUTE BARGAIN !!!!!";
/*  20 */     L.println(s);
/*  21 */     L.println(new StringCleaner(s).removePunctuation().removeNonWords().removeWords(stopWords, false, true).singleSpaces().toString());
/*     */   }
/*     */ 
/*     */   public StringCleaner(String string)
/*     */   {
/*  29 */     this.string = string;
/*     */   }
/*     */ 
/*     */   public StringCleaner removeNonWords()
/*     */   {
/*  40 */     this.string = pNonWords.matcher(this.string).replaceAll("");
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */   public StringCleaner removePunctuation()
/*     */   {
/*  52 */     this.string = pPunctuationEnd.matcher(this.string).replaceAll("$1$2");
/*  53 */     this.string = pPunctuationBegin.matcher(this.string).replaceAll("$1$2");
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */   public StringCleaner singleSpaces()
/*     */   {
/*  64 */     this.string = pMultiSpace.matcher(this.string).replaceAll(" ");
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */   public StringCleaner removeWords(Collection<String> words, boolean caseSensitive, boolean cleanNonWordChars)
/*     */   {
/*  81 */     StringBuffer sb = new StringBuffer();
/*  82 */     Matcher m = pWord.matcher(this.string);
/*  83 */     while (m.find())
/*     */     {
/*  85 */       String word = m.group();
/*  86 */       if (cleanNonWordChars)
/*  87 */         word = pNonLetter.matcher(word).replaceAll("");
/*  88 */       String testWord = caseSensitive ? word : word.toLowerCase();
/*  89 */       m.appendReplacement(sb, words.contains(testWord) ? "" : word);
/*     */     }
/*  91 */     m.appendTail(sb);
/*  92 */     this.string = sb.toString();
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public StringCleaner removeStopWords()
/*     */   {
/* 104 */     return removeWords(stopWords, false, true);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 112 */     return this.string;
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.utils.StringCleaner
 * JD-Core Version:    0.6.2
 */