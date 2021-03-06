package br.com.projetopicii.table.model;

import javax.swing.table.AbstractTableModel;

import br.com.projetopicii.model.bean.Usuario;

import java.util.ArrayList;
import java.util.List;

public class UsuarioTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -3586211638575736174L;

	private List<Usuario> usuarios;
	private String[] colunas = new String[] { "ID", "Login"};

	public UsuarioTableModel(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public UsuarioTableModel() {
		this.usuarios = new ArrayList<Usuario>();
	}

	public int getRowCount() {
		return usuarios.size();
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

	public void setValueAt(Usuario aValue, int rowIndex) {
		Usuario usuario = usuarios.get(rowIndex);

		usuario.setId(aValue.getId());
		usuario.setLogin(aValue.getLogin());

		fireTableCellUpdated(rowIndex, 0);
		fireTableCellUpdated(rowIndex, 1);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Usuario usuario = usuarios.get(rowIndex);

		switch (columnIndex) {
		case 0:
			usuario.setId(Integer.parseInt(aValue.toString()));
		case 1:
			usuario.setLogin(aValue.toString());

		default:
			System.err.println("�ndice da coluna inv�lido");
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Usuario usuarioSelecionado = usuarios.get(rowIndex);
		String valueObject = null;
		switch (columnIndex) {
		case 0:
			valueObject = usuarioSelecionado.getId().toString();
			break;
		case 1:
			valueObject = usuarioSelecionado.getLogin();
			break;
		default:
			System.err.println("�ndice inv�lido para propriedade do bean Usuario.class");
		}

		return valueObject;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Usuario getUsuario(int indiceLinha) {
		return usuarios.get(indiceLinha);
	}

	public void addUsuario(Usuario u) {
		usuarios.add(u);

		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	public void removeUsuario(int indiceLinha) {
		usuarios.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}

	public void addListaDeUsuarios(List<Usuario> novosUsuarios) {

		int tamanhoAntigo = getRowCount();
		usuarios.addAll(novosUsuarios);
		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}

	public void limpar() {
		usuarios.clear();
		fireTableDataChanged();
	}

	public boolean isEmpty() {
		return usuarios.isEmpty();
	}

}