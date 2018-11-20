package br.com.projetopicii.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.projetopicii.model.bean.Livro;

public class LivroTableModel extends AbstractTableModel{
	private static final long serialVersionUID = -3586211638575736174L;

	private List<Livro> livros;
	private String[] colunas = new String[] { "ID", "Titulo","Autor","Genero","Ano de lançamento","Idioma","Nº Paginas"};

	public LivroTableModel(List<Livro> livros) {
		this.livros = livros;
	}

	public LivroTableModel() {
		this.livros = new ArrayList<Livro>();
	}

	public int getRowCount() {
		return livros.size();
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

	public void setValueAt(Livro aValue, int rowIndex) {
		Livro livro = livros.get(rowIndex);

		livro.setId(aValue.getId());
		livro.setTitulo(aValue.getTitulo());
		livro.setAutor(aValue.getAutor());
		livro.setGenero(aValue.getGenero());
		livro.setAnoLancamento(aValue.getAnoLancamento());
		livro.setIdioma(aValue.getIdioma());
		livro.setNumPaginas(aValue.getNumPaginas());

		fireTableCellUpdated(rowIndex, 0);
		fireTableCellUpdated(rowIndex, 1);
		fireTableCellUpdated(rowIndex, 2);
		fireTableCellUpdated(rowIndex, 3);
		fireTableCellUpdated(rowIndex, 4);
		fireTableCellUpdated(rowIndex, 5);
		fireTableCellUpdated(rowIndex, 6);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Livro livro = livros.get(rowIndex);

		switch (columnIndex) {
		case 0:
			livro.setId(Integer.parseInt(aValue.toString()));
		case 1:
			livro.setTitulo(aValue.toString());
		case 2:
			livro.setAutor(aValue.toString());
		case 3:
			livro.setGenero(aValue.toString());
		case 4:
			livro.setAnoLancamento(Integer.parseInt(aValue.toString()));
		case 5:
			livro.setIdioma(aValue.toString());
		case 6:
			livro.setNumPaginas(Integer.parseInt(aValue.toString()));

		default:
			System.err.println("Índice da coluna inválido");
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Livro livroSelecionado = livros.get(rowIndex);
		String valueObject = null;
		switch (columnIndex) {
		case 0:
			valueObject = String.valueOf(livroSelecionado.getId());
			break;
		case 1:
			valueObject = livroSelecionado.getTitulo();
			break;
		case 2:
			valueObject = livroSelecionado.getAutor();
			break;
		case 3:
			valueObject = livroSelecionado.getGenero();
			break;
		case 4:
			valueObject = String.valueOf(livroSelecionado.getAnoLancamento());
			break;
		case 5:
			valueObject = livroSelecionado.getIdioma();
			break;
		case 6:
			valueObject = String.valueOf(livroSelecionado.getNumPaginas());
			break;
	
		default:
			System.err.println("Índice inválido para propriedade do bean Usuario.class");
		}

		return valueObject;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public Livro getLivro(int indiceLinha) {
		return livros.get(indiceLinha);
	}

	public void addLivro(Livro l) {
		livros.add(l);

		int ultimoIndice = getRowCount() - 1;

		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}

	public void removeLivro(int indiceLinha) {
		livros.remove(indiceLinha);

		fireTableRowsDeleted(indiceLinha, indiceLinha);
	}

	public void addListaDeLivros(List<Livro> novosLivros) {

		int tamanhoAntigo = getRowCount();
		livros.addAll(novosLivros);
		fireTableRowsInserted(tamanhoAntigo, getRowCount() - 1);
	}

	public void limpar() {
		livros.clear();
		fireTableDataChanged();
	}

	public boolean isEmpty() {
		return livros.isEmpty();
	}

}
