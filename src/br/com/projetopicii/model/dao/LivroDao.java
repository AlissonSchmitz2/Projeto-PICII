package br.com.projetopicii.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.projetopicii.connection.ConnectionFactory;
import br.com.projetopicii.model.bean.Livro;

public class LivroDao {
	Connection con = ConnectionFactory.getConnection();
	PreparedStatement stmt = null;
	ResultSet rS = null;
	ArrayList<Livro> listaLivro = new ArrayList<Livro>();
	private Livro livro;

	// Recupera todos os livros cadastrados.
	public ArrayList<Livro> pegarLivrosCadastrados() {
		try {
			stmt = con.prepareStatement("Select * from livro");
			rS = stmt.executeQuery();
			
			while (rS.next()) {

				Livro livro = new Livro();
				livro.setId(rS.getInt("id"));
				livro.setTitulo(rS.getString("titulo"));
				livro.setAutor(rS.getString("autor"));
				livro.setGenero(rS.getString("genero"));
				livro.setAnoLancamento(rS.getInt("ano_lancamento"));
				livro.setNumPaginas(rS.getInt("numero_paginas"));
				livro.setId_Estante(rS.getInt("id_estante"));
				livro.setIdioma(rS.getString("idioma"));
				listaLivro.add(livro);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return listaLivro;
	}

	// Recupera os livros cadastrados de acordo com o valor de busca e a coluna
	// desejada.
	public ArrayList<Livro> pegarLivrosCadastrados(String valorBusca, String coluna) {
		try {

			stmt = con.prepareStatement("SELECT * FROM livro WHERE UPPER(" + coluna + ") LIKE ?");
			stmt.setString(1, "%" + valorBusca + "%");
			rS = stmt.executeQuery();
			
			while (rS.next()) {

				Livro livro = new Livro();				
				livro.setId(rS.getInt("id"));
				livro.setTitulo(rS.getString("titulo"));
				livro.setAutor(rS.getString("autor"));
				livro.setGenero(rS.getString("genero"));
				livro.setAnoLancamento(rS.getInt("ano_lancamento"));
				livro.setNumPaginas(rS.getInt("numero_paginas"));
				livro.setId_Estante(rS.getInt("id_estante"));
				livro.setIdioma(rS.getString("idioma"));
				listaLivro.add(livro);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return listaLivro;
	}
	
	// Recupera o livro de acordo com o Id.
	public Livro pegarLivroPorId(int idLivro) {
		
		try {
			stmt = con.prepareStatement("Select * from livro where id = ?");
			stmt.setInt(1, idLivro);
			rS = stmt.executeQuery();
			
			while (rS.next()) {

				livro = new Livro();
				livro.setId(rS.getInt("id"));
				livro.setTitulo(rS.getString("titulo"));
				livro.setAutor(rS.getString("autor"));
				livro.setGenero(rS.getString("genero"));
				livro.setAnoLancamento(rS.getInt("ano_lancamento"));
				livro.setNumPaginas(rS.getInt("numero_paginas"));
				livro.setId_Estante(rS.getInt("id_estante"));
				livro.setIdioma(rS.getString("idioma"));
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return livro;
	}

	// Recupera os livros localizados em determinada estante.
	public ArrayList<Livro> pegarLivrosCadastrados(int idEstante) {
		try {
			listaLivro = new ArrayList<>();

			stmt = con.prepareStatement("SELECT * FROM livro WHERE id_estante = ?");
			stmt.setInt(1, idEstante);
			rS = stmt.executeQuery();
			while (rS.next()) {

				Livro livro = new Livro();				
				livro.setId(rS.getInt("id"));
				livro.setTitulo(rS.getString("titulo"));
				livro.setAutor(rS.getString("autor"));
				livro.setGenero(rS.getString("genero"));
				livro.setAnoLancamento(rS.getInt("ano_lancamento"));
				livro.setNumPaginas(rS.getInt("numero_paginas"));
				livro.setId_Estante(rS.getInt("id_estante"));
				livro.setIdioma(rS.getString("idioma"));
				listaLivro.add(livro);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return listaLivro;
	}

	// Retorna o id da estante que contém determinado livro.
	public int pegarIdEstante(String titulo) {

		int idEstante = -1;
		try {
			stmt = con.prepareStatement("Select id_estante from livro where titulo = ?");
			stmt.setString(1, titulo);
			rS = stmt.executeQuery();

			while (rS.next()) {
				idEstante = rS.getInt("id_estante");
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return idEstante;
	}

	// Cadastra um novo livro.
	public void cadastrarLivro(String titulo, String autor, String genero, int ano_lancamento, int num_pag, int id_estante, String idioma, boolean livroNovo, int id_livro) {

		try {
			if(livroNovo) {
				stmt = con.prepareStatement("insert into livro (titulo, autor, genero, ano_lancamento, numero_paginas,"
						+ " id_estante, idioma) values (?, ?, ?, ?, ?, ?, ?)");
				stmt.setString(1, titulo);
				stmt.setString(2, autor);
				stmt.setString(3, genero);
				stmt.setInt(4, ano_lancamento);
				stmt.setInt(5, num_pag);
				stmt.setInt(6, id_estante);
				stmt.setString(7, idioma);
			} else {
				stmt = con.prepareStatement("update livro set titulo = ?, autor = ?, genero = ?, ano_lancamento = ?"
						+ ", numero_paginas = ?, id_estante = ?, idioma = ? where id = ?");
				stmt.setString(1, titulo);
				stmt.setString(2, autor);
				stmt.setString(3, genero);
				stmt.setInt(4, ano_lancamento);
				stmt.setInt(5, num_pag);
				stmt.setInt(6, id_estante);
				stmt.setString(7, idioma);
				stmt.setInt(8, id_livro);
			}

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void excluirLivro(Integer id) {
		try {
			stmt = con.prepareStatement("delete from livro where id = ?");
			stmt.setInt(1, id);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
