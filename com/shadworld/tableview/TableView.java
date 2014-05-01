/*     */ package com.shadworld.tableview;
/*     */ 
/*     */ import com.shadworld.struct.DataTable;
/*     */ import com.shadworld.struct.Record;
/*     */ import java.awt.Container;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Point;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import javax.swing.GroupLayout;
/*     */ import javax.swing.GroupLayout.Alignment;
/*     */ import javax.swing.GroupLayout.ParallelGroup;
/*     */ import javax.swing.GroupLayout.SequentialGroup;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.LayoutStyle.ComponentPlacement;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableModel;
/*     */ 
/*     */ public class TableView extends JFrame
/*     */ {
/*     */   private TableModel tableModel;
/*  26 */   private boolean doNext = false;
/*     */   public static TableView instance;
/*  29 */   private int invocation = 1;
/*     */   private JButton btnNext;
/*     */   private JCheckBox cbUnblock;
/*     */   private JLabel invLabel;
/*     */   private JScrollPane jScrollPane1;
/*     */   private JTable jTable;
/*     */ 
/*     */   public static void show(Collection col, boolean showCols, boolean unBlock)
/*     */   {
/*  32 */     TableModel model = TableModelTools.getStaticBeanTableModel(col, 
/*  33 */       showCols);
/*  34 */     show(model, unBlock);
/*  35 */     if (!instance.cbUnblock.isSelected())
/*  36 */       instance.cbUnblock.setSelected(unBlock);
/*     */   }
/*     */ 
/*     */   public static void showObject(Object o) {
/*  40 */     showObject(o, false);
/*     */   }
/*     */   public static void showObject(Object o, boolean unBlock) {
/*  43 */     if (o == null)
/*  44 */       return;
/*  45 */     if ((o instanceof Record)) {
/*  46 */       DataTable tbl = Record.invertToTable((Record)o);
/*  47 */       show(tbl);
/*     */     } else {
/*  49 */       Record rec = Record.recordFromBean(o);
/*  50 */       DataTable tbl = Record.invertToTable(rec);
/*  51 */       show(tbl, true, unBlock);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void showBlocker()
/*     */   {
/*  57 */     showObject("Blocking... Press next to continue", false);
/*     */   }
/*     */ 
/*     */   public static void show(TableModel model, boolean unBlock) {
/*  61 */     if (instance == null)
/*  62 */       createInstance(model);
/*     */     else {
/*  64 */       refreshModel(model);
/*     */     }
/*  66 */     if (!instance.cbUnblock.isSelected())
/*  67 */       instance.cbUnblock.setSelected(unBlock);
/*  68 */     block();
/*     */   }
/*     */ 
/*     */   public static void show(Collection col) {
/*  72 */     show(col, true, false);
/*     */   }
/*     */ 
/*     */   public static void showNoCols(Collection col) {
/*  76 */     show(col, true, false);
/*     */   }
/*     */ 
/*     */   public static void show(DataTable table, boolean showCols, boolean unBlock) {
/*  80 */     TableModel model = TableModelTools.getStaticDataTableModel(table, 
/*  81 */       showCols);
/*  82 */     show(model, unBlock);
/*  83 */     if (!instance.cbUnblock.isSelected())
/*  84 */       instance.cbUnblock.setSelected(unBlock);
/*     */   }
/*     */ 
/*     */   public static void show(DataTable table) {
/*  88 */     show(table, true, false);
/*     */   }
/*     */ 
/*     */   public static void showNoCols(DataTable table) {
/*  92 */     show(table, false, false);
/*     */   }
/*     */ 
/*     */   private static void refreshModel(TableModel model) {
/*  96 */     instance.invocation += 1;
/*  97 */     instance.invLabel.setText("Invocation: " + instance.invocation);
/*  98 */     instance.tableModel = model;
/*  99 */     instance.jTable.setModel(model);
/* 100 */     instance.jTable.repaint();
/*     */   }
/*     */ 
/*     */   private static void createInstance(TableModel model) {
/*     */     try {
/* 105 */       EventQueue.invokeAndWait(new Runnable() {
/*     */         public void run() {
/* 107 */           TableView.instance = new TableView(TableView.this);
/* 108 */           TableView.instance.setVisible(true);
/*     */         } } );
/*     */     } catch (InterruptedException localInterruptedException) {
/*     */     }
/*     */     catch (InvocationTargetException localInvocationTargetException) {
/*     */     }
/*     */     try {
/* 115 */       Thread.sleep(200L);
/*     */     } catch (InterruptedException localInterruptedException1) {
/*     */     }
/* 118 */     if (instance != null)
/* 119 */       instance.invLabel.setText("Invocation: " + instance.invocation);
/*     */   }
/*     */ 
/*     */   private static void createNewInstance(TableModel model, Object o) {
/* 123 */     TableView view = new TableView(model);
/* 124 */     view.setDefaultCloseOperation(2);
/* 125 */     view.setVisible(true);
/*     */     try
/*     */     {
/* 128 */       Thread.sleep(200L);
/*     */     } catch (InterruptedException localInterruptedException) {
/*     */     }
/* 131 */     if (view != null)
/* 132 */       view.invLabel.setText(o.getClass().getSimpleName() + ": " + o.toString());
/*     */   }
/*     */ 
/*     */   private static void block()
/*     */   {
/* 137 */     boolean unblock = false;
/* 138 */     while (!unblock) {
/* 139 */       if (instance.doNext)
/* 140 */         unblock = true;
/* 141 */       if (!instance.isBlocked())
/* 142 */         unblock = true;
/* 143 */       if (!unblock)
/*     */         try {
/* 145 */           Thread.sleep(10L);
/*     */         }
/*     */         catch (InterruptedException localInterruptedException) {
/*     */         }
/*     */     }
/* 150 */     instance.doNext = false;
/*     */   }
/*     */ 
/*     */   public TableView(TableModel tableModel)
/*     */   {
/* 155 */     this.tableModel = tableModel;
/*     */     try {
/* 157 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*     */     }
/*     */     catch (ClassNotFoundException e)
/*     */     {
/* 161 */       e.printStackTrace();
/*     */     }
/*     */     catch (InstantiationException e) {
/* 164 */       e.printStackTrace();
/*     */     }
/*     */     catch (IllegalAccessException e) {
/* 167 */       e.printStackTrace();
/*     */     }
/*     */     catch (UnsupportedLookAndFeelException e) {
/* 170 */       e.printStackTrace();
/*     */     }
/* 172 */     initComponents();
/*     */   }
/*     */ 
/*     */   private void initComponents()
/*     */   {
/* 184 */     this.jScrollPane1 = new JScrollPane();
/* 185 */     this.jTable = new JTable();
/* 186 */     this.btnNext = new JButton();
/* 187 */     this.cbUnblock = new JCheckBox();
/* 188 */     this.invLabel = new JLabel();
/*     */ 
/* 190 */     setDefaultCloseOperation(3);
/*     */ 
/* 192 */     this.jTable.setModel(this.tableModel);
/*     */ 
/* 194 */     this.jTable.addMouseListener(new MouseAdapter() {
/*     */       public void mouseClicked(MouseEvent e) {
/* 196 */         System.out.println(e.getClickCount() + " clicks");
/* 197 */         if (e.getClickCount() == 1) {
/* 198 */           Point p = e.getPoint();
/* 199 */           int row = TableView.this.jTable.rowAtPoint(p);
/* 200 */           int column = TableView.this.jTable.columnAtPoint(p);
/*     */ 
/* 202 */           Object o = TableView.this.jTable.getModel().getValueAt(row, column);
/* 203 */           System.out.println(e.getClickCount() + " clicks: " + (
/* 204 */             o == null ? "null" : o.getClass().getName()));
/* 205 */           if ((o instanceof DataTable)) {
/* 206 */             TableModel model = 
/* 207 */               TableModelTools.getStaticDataTableModel((DataTable)o);
/* 208 */             TableView.createNewInstance(model, o);
/* 209 */           } else if ((o instanceof Collection)) {
/* 210 */             TableModel model = 
/* 211 */               TableModelTools.getStaticBeanTableModel((Collection)o);
/* 212 */             TableView.createNewInstance(model, o);
/* 213 */           } else if ((o instanceof Record)) {
/* 214 */             DataTable tbl = Record.invertToTable((Record)o);
/* 215 */             TableModel model = 
/* 216 */               TableModelTools.getStaticDataTableModel(tbl);
/* 217 */             TableView.createNewInstance(model, o);
/* 218 */           } else if (TableModelTools.hasGetter(o)) {
/* 219 */             Record rec = Record.recordFromBean(o);
/* 220 */             DataTable tbl = Record.invertToTable(rec);
/* 221 */             TableModel model = 
/* 222 */               TableModelTools.getStaticDataTableModel(tbl);
/* 223 */             TableView.createNewInstance(model, o);
/*     */           }
/*     */         }
/*     */       }
/*     */     });
/* 230 */     this.jScrollPane1.setViewportView(this.jTable);
/*     */ 
/* 232 */     this.btnNext.setText("Next");
/* 233 */     this.btnNext.addActionListener(new ActionListener() {
/*     */       public void actionPerformed(ActionEvent evt) {
/* 235 */         TableView.this.btnNextActionPerformed(evt);
/*     */       }
/*     */     });
/* 239 */     this.cbUnblock.setText("Unblock");
/*     */ 
/* 241 */     this.invLabel.setText("Invocation: ");
/*     */ 
/* 243 */     GroupLayout layout = new GroupLayout(
/* 244 */       getContentPane());
/* 245 */     getContentPane().setLayout(layout);
/* 246 */     layout
/* 247 */       .setHorizontalGroup(layout
/* 248 */       .createParallelGroup(
/* 249 */       GroupLayout.Alignment.LEADING)
/* 250 */       .addGroup(
/* 251 */       layout
/* 252 */       .createSequentialGroup()
/* 253 */       .addGap(25, 25, 25)
/* 254 */       .addComponent(this.btnNext)
/* 255 */       .addGap(198, 198, 198)
/* 256 */       .addComponent(this.invLabel)
/* 257 */       .addPreferredGap(
/* 258 */       LayoutStyle.ComponentPlacement.RELATED, 
/* 259 */       223, 32767)
/* 260 */       .addComponent(this.cbUnblock).addGap(42, 42, 
/* 261 */       42))
/* 262 */       .addGroup(
/* 263 */       layout
/* 264 */       .createSequentialGroup()
/* 265 */       .addContainerGap()
/* 266 */       .addComponent(
/* 267 */       this.jScrollPane1, 
/* 268 */       -1, 
/* 269 */       646, 32767)
/* 270 */       .addContainerGap()));
/* 271 */     layout
/* 272 */       .setVerticalGroup(layout
/* 273 */       .createParallelGroup(
/* 274 */       GroupLayout.Alignment.LEADING)
/* 275 */       .addGroup(
/* 276 */       layout
/* 277 */       .createSequentialGroup()
/* 278 */       .addContainerGap()
/* 279 */       .addGroup(
/* 280 */       layout
/* 281 */       .createParallelGroup(
/* 282 */       GroupLayout.Alignment.LEADING)
/* 283 */       .addGroup(
/* 284 */       layout
/* 285 */       .createParallelGroup(
/* 286 */       GroupLayout.Alignment.BASELINE)
/* 287 */       .addComponent(
/* 288 */       this.btnNext)
/* 289 */       .addComponent(
/* 290 */       this.cbUnblock))
/* 291 */       .addComponent(this.invLabel))
/* 292 */       .addPreferredGap(
/* 293 */       LayoutStyle.ComponentPlacement.RELATED)
/* 294 */       .addComponent(
/* 295 */       this.jScrollPane1, 
/* 296 */       -1, 
/* 297 */       445, 32767)
/* 298 */       .addContainerGap()));
/*     */ 
/* 300 */     pack();
/*     */   }
/*     */ 
/*     */   private void btnNextActionPerformed(ActionEvent evt) {
/* 304 */     this.doNext = true;
/*     */   }
/*     */ 
/*     */   public boolean isDoNext() {
/* 308 */     return this.doNext;
/*     */   }
/*     */ 
/*     */   public boolean isBlocked() {
/* 312 */     return !this.cbUnblock.isSelected();
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 320 */     EventQueue.invokeLater(new Runnable() {
/*     */       public void run() {
/* 322 */         DataTable table = new DataTable();
/* 323 */         Record rec = new Record();
/* 324 */         rec.put("DATE", new Date());
/* 325 */         rec.put("STRING", "this is a string");
/* 326 */         table.add(rec);
/* 327 */         rec = new Record();
/* 328 */         rec.put("DATE", 
/* 329 */           new Date(System.currentTimeMillis() + 86400000L));
/* 330 */         rec.put("STRING", "this is another string");
/* 331 */         table.add(rec);
/*     */ 
/* 333 */         DefaultTableModel tv = new DefaultTableModel(table
/* 334 */           .getObjectArrays(), table.getKeyArray());
/* 335 */         new TableView(tv).setVisible(true);
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.tableview.TableView
 * JD-Core Version:    0.6.2
 */