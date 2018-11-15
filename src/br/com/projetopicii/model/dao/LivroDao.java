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

	// Recupera todos os livros cadastrados.
	public ArrayList<Livro> pegarLivrosCadastrados() {
		try {
			stmt = con.prepareStatement("Select * from livro");
			rS = stmt.executeQuery();

			String itensLivro = "";
			while (rS.next()) {
				for (int i = 2; i < 7; i++) {
					itensLivro += rS.getString(i) + ";";
				}
				String[] auxLivros = itensLivro.split(";");

				Livro livro = new Livro();
				livro.setTitulo(auxLivros[0]);
				livro.setAutor(auxLivros[1]);
				livro.setGenero(auxLivros[2]);
				livro.setAnoLancamento(auxLivros[3]);
				livro.setNumPaginas(Integer.parseInt(auxLivros[4]));
				livro.setIdioma("Português");
				listaLivro.add(livro);
				itensLivro = "";
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

			String itensLivro = "";
			while (rS.next()) {
				for (int i = 2; i < 7; i++) {
					itensLivro += rS.getString(i) + ";";
				}
				String[] auxLivros = itensLivro.split(";");

				Livro livro = new Livro();
				livro.setTitulo(auxLivros[0]);
				livro.setAutor(auxLivros[1]);
				livro.setGenero(auxLivros[2]);
				livro.setAnoLancamento(auxLivros[3]);
				livro.setNumPaginas(Integer.parseInt(auxLivros[4]));
				livro.setIdioma("Português");
				listaLivro.add(livro);
				itensLivro = "";
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		return listaLivro;
	}

	// Recupera os livros localizados em determinada estante.
	public ArrayList<Livro> pegarLivrosCadastrados(int idEstante) {
		try {
			listaLivro = new ArrayList<>();

			stmt = con.prepareStatement("SELECT * FROM livro WHERE id_estante = ?");
			stmt.setInt(1, idEstante);
			rS = stmt.executeQuery();

			String itensLivro = "";
			while (rS.next()) {
				for (int i = 2; i < 7; i++) {
					itensLivro += rS.getString(i) + ";";
				}
				String[] auxLivros = itensLivro.split(";");

				Livro livro = new Livro();
				livro.setTitulo(auxLivros[0]);
				livro.setAutor(auxLivros[1]);
				livro.setGenero(auxLivros[2]);
				livro.setAnoLancamento(auxLivros[3]);
				livro.setNumPaginas(Integer.parseInt(auxLivros[4]));
				livro.setIdioma("Português");
				listaLivro.add(livro);
				itensLivro = "";
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
				idEstante = Integer.parseInt(rS.getString("id_estante"));
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return idEstante;
	}

	// Cadastra um novo livro.
	public void cadastrarLivro(String titulo, String autor, String genero, int ano_lancamento, int num_pag, int id_estante) {

		try {
			stmt = con.prepareStatement("insert into livro (titulo, autor, genero, ano_lancamento, numero_paginas, id_estante) values (?, ?, ?, ?, ?, ?)");
			stmt.setString(1, titulo);
			stmt.setString(2, autor);
			stmt.setString(3, genero);
			stmt.setInt(4, ano_lancamento);
			stmt.setInt(5, num_pag);
			stmt.setInt(6, id_estante);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
