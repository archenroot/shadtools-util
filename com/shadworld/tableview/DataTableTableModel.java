/*    */ package com.shadworld.tableview;
/*    */ 
/*    */ import com.shadworld.struct.DataTable;
/*    */ import com.shadworld.struct.Record;
/*    */ import javax.swing.event.TableModelListener;
/*    */ import javax.swing.table.TableModel;
/*    */ 
/*    */ public class DataTableTableModel
/*    */   implements TableModel
/*    */ {
/*    */   DataTable table;
/*    */ 
/*    */   public DataTableTableModel(DataTable table)
/*    */   {
/* 16 */     this.table = table;
/*    */   }
/*    */ 
/*    */   public void addTableModelListener(TableModelListener l)
/*    */   {
/*    */   }
/*    */ 
/*    */   public Class<?> getColumnClass(int columnIndex)
/*    */   {
/* 27 */     Object o = findFirstNonNull(columnIndex);
/* 28 */     if (o != null)
/* 29 */       return o.getClass();
/* 30 */     return null;
/*    */   }
/*    */ 
/*    */   private Object findFirstNonNull(int columnIndex) {
/* 34 */     for (int i = 0; i < this.table.size(); i++) {
/* 35 */       Record rec = (Record)this.table.get(i);
/* 36 */       if (rec != null) {
/* 37 */         Object o = rec.getColumn(columnIndex);
/* 38 */         if (o != null)
/* 39 */           return o;
/*    */       }
/*    */     }
/* 42 */     return null;
/*    */   }
/*    */ 
/*    */   public int getColumnCount()
/*    */   {
/* 47 */     if (this.table.size() > 0) {
/* 48 */       return ((Record)this.table.get(0)).size();
/*    */     }
/* 50 */     return -1;
/*    */   }
/*    */ 
/*    */   public String getColumnName(int columnIndex)
/*    */   {
/* 55 */     if (this.table.size() > 0) {
/* 56 */       return String.valueOf(((Record)this.table.get(0)).getColumnKey(columnIndex));
/*    */     }
/* 58 */     return null;
/*    */   }
/*    */ 
/*    */   public int getRowCount()
/*    */   {
/* 64 */     return this.table.size();
/*    */   }
/*    */ 
/*    */   public Object getValueAt(int rowIndex, int columnIndex)
/*    */   {
/* 69 */     Record rec = (Record)this.table.get(rowIndex);
/* 70 */     if (rec != null)
/* 71 */       return rec.getColumn(columnIndex);
/* 72 */     return null;
/*    */   }
/*    */ 
/*    */   public boolean isCellEditable(int rowIndex, int columnIndex)
/*    */   {
/* 78 */     return false;
/*    */   }
/*    */ 
/*    */   public void removeTableModelListener(TableModelListener l)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void setValueAt(Object aValue, int rowIndex, int columnIndex)
/*    */   {
/* 89 */     Record rec = (Record)this.table.get(rowIndex);
/* 90 */     if (rec != null) {
/* 91 */       Object key = rec.getColumnKey(columnIndex);
/* 92 */       rec.put(key, aValue);
/*    */     }
/*    */   }
/*    */ }

/* Location:           D:\development\cryptocurrency\crypto-pool-poolserverj\poolserverj-main\etc\lib\lib_non-maven\shadtools-util-0.0.1-SNAPSHOT.jar
 * Qualified Name:     com.shadworld.tableview.DataTableTableModel
 * JD-Core Version:    0.6.2
 */