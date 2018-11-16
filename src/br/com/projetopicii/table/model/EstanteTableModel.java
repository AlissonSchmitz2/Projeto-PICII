package br.com.projetopicii.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.projetopicii.model.bean.Estante;
import br.com.projetopicii.model.bean.Livro;

public class EstanteTableModel extends AbstractTableModel{
	
	private static final long serialVersionUID = -3586211638575736174L;

	private List<Estante> estantes;
	private String[] colunas = new String[] { "ID", "Nome estante", "CoordenadaX", "CoordenadaY" };

	public EstanteTableModel(List<Estante> estantes) {
		this.estantes = estantes;
	}

	public EstanteTableModel() {
		this.estantes = new ArrayList<Estante>();
	}

	public int getRowCount() {
		return estantes.size();
	}

	public int getColumnCount() {
		return colunas.length;
	}

	public String getColumnName(int columnIndex) {
		return colunas[columnIndex];
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public void setValueAt(Estante aValue, int rowIndex) {
		Estante estante = estantes.get(rowIndex);

		estante.setId(aValue.getId());
		estante.setNome(aValue.getNome());
		estante.setCoordenadaX(aValue.getCoordenadaX());
		estante.setCoordenadaY(aValue.getCoordenadaY());

		fireTableCellUpdated(rowIndex, 0);
		fireTableCellUpdated(rowIndex, 1);
		fireTableCellUpdated(rowIndex, 2);
		fireTableCellUpdated(rowIndex, 3);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Estante estante = estantes.get(rowIndex);

		switch (columnIndex) {
		case 0:
			estante.setId(Integer.parseInt(aValue.toString()));
		case 1:
			estante.setNome(aValue.toString());
		case 2:
			estante.setCoordenadaX(Integer.parseInt(aValue.toString()));
		case 3:
			estante.setCoordenadaY(Integer.parseInt(aValue.toString()));
		default:
			System.err.println("Índice da coluna inválido");
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Estante getEstante(int indiceLinha) {
		return estantes.get(indiceLinha);
	}

	public void addEstante(Estante u) {
		estantes.add(u);

		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	public void removeEstante(int indiceLinha) {
		estantes.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}

	public void addListaDeEstantes(List<Estante> novasEstantes) {

		int tamanhoAntigo = getRowCount();
		estantes.addAll(novasEstantes);
		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}

	public void limpar() {
		estantes.clear();
		fireTableDataChanged();
	}

	public boolean isEmpty() {
		return estantes.isEmpty();
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Estante estanteSelecionada = estantes.get(rowIndex);
		String valueObject = null;
		switch (columnIndex) {
		case 0:
			valueObject = String.valueOf(estanteSelecionada.getId());
			break;
		case 1:
			valueObject = estanteSelecionada.getNome();
			break;
		case 2:
			valueObject = String.valueOf(estanteSelecionada.getCoordenadaX());
			break;
		case 3:
			valueObject = String.valueOf(estanteSelecionada.getCoordenadaY());
			break;
		default:
			System.err.println("Índice inválido para propriedade do bean Usuario.class");
		}

		return valueObject;
	}
}
