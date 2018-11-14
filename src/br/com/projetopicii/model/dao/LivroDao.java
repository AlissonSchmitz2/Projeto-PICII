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

	public ArrayList<Livro> pegarLivrosCadastrados() {
		try {
			stmt = con.prepareStatement("Select * from livro");
//			stmt = con.prepareStatement("Select * from livro where titulo = ?");
//			stmt.setString(1, valorBusca);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaLivro;
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaLivro;
	}
}
