package br.com.projetopicii.auxiliaries;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

//Centraliza o conteúdo das células na JTable.
public class CellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = -8141540804686499241L;
	
	public CellRenderer() 
	{
 		super();
 	}
 	public Component getTableCellRendererComponent(JTable table, Object value,
 			boolean isSelected, boolean hasFocus, int row, int column) {
 		this.setHorizontalAlignment(CENTER);
 		return super.getTableCellRendererComponent(table, value, isSelected,
 				hasFocus, row, column);
 	}
}
