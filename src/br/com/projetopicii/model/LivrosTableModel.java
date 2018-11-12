package br.com.projetopicii.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.projetopicii.model.bean.Livro;

public class LivrosTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -6991516693967833564L;
	
	private List<Livro> dados = new ArrayList<>();	
	private String[] colunas = {"Título", "Autor", "Gênero", "Ano de Lançamento", "Idioma", "Nº Páginas"};
	
	//Retorna a quantidade de colunas da JTable.
	public int getColumnCount() {
		return colunas.length;
	}

	//Retorna a quantidade de linhas da JTable.
	public int getRowCount() {
		return dados.size();
	}
	
	//Retornar valor a partir da linha e coluna.
	public Object getValueAt(int linhaIndex, int colunaIndex) {
		
		switch(colunaIndex) {
			case 0: 
				return dados.get(linhaIndex).getTitulo();
			case 1:
				return dados.get(linhaIndex).getAutor();
			case 2:
				return dados.get(linhaIndex).getGenero();
			case 3:
				return dados.get(linhaIndex).getAnoLancamento();
			case 4:
				return dados.get(linhaIndex).getIdioma();
			case 5:
				return dados.get(linhaIndex).getNumPaginas();
			
		}
		
		return null;
	}	
	
	public String getColumnName(int column) {
		return colunas[column];
	}
	
	//Adiciona dados a tabela.
	public void addRow(ArrayList<Livro> arrayLivros) {
		this.dados.addAll(arrayLivros);
		
		//Atualiza a tabela quando há mudança nos dados.
		this.fireTableDataChanged();
	}
	
	//Limpa os dados da tabela.
	public void limpar() {
		dados.clear();
		this.fireTableDataChanged();
	}
}
