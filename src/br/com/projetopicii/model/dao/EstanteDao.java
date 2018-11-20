package br.com.projetopicii.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.projetopicii.connection.ConnectionFactory;
import br.com.projetopicii.model.bean.Estante;

public class EstanteDao {

	Connection con = ConnectionFactory.getConnection();
	PreparedStatement stmt = null;
	ResultSet rS = null;
	String[] listaEstante;
	ArrayList<Estante> arrayEstantes = new ArrayList<>();
	LivroDao livroDao = new LivroDao();

	public String[] pegarNomeEstantes(boolean pegarSomenteNull) {

		try {

			if(pegarSomenteNull) {
				stmt = con.prepareStatement("Select nome from estante where coordenadax = ?");
				stmt.setInt(1, 0);
			} else {
				stmt = con.prepareStatement("Select nome from estante where coordenadax > ?");
				stmt.setInt(1, 0);
			}
			
			rS = stmt.executeQuery();

			listaEstante = new String[pegarQuantidadeEstante()];

			rS = stmt.executeQuery();

			int j = 0;

			while (rS.next()) {
				listaEstante[j] = rS.getString("nome");
				j++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listaEstante;
	}

	public ArrayList<Estante> pegarArrayEstantes(boolean pegarSomenteNull) {

		try {	
			
			if(pegarSomenteNull) {
				stmt = con.prepareStatement("Select * from estante where coordenadax = ?");
				stmt.setInt(1, 0);
			} else {				
				stmt = con.prepareStatement("Select * from estante where coordenadax > ?");
				stmt.setInt(1, 0);
			}			
			
			rS = stmt.executeQuery();			
			while (rS.next()) {

				Estante estante = new Estante();				
				estante.setId(rS.getInt("id"));
				estante.setLivros(livroDao.pegarLivrosCadastrados(rS.getInt("id")));
				estante.setNome(rS.getString("nome"));
				estante.setCoordenadaX(rS.getInt("coordenadax"));
				estante.setCoordenadaY(rS.getInt("coordenaday"));
				estante.setVertical(rS.getBoolean("vertical"));

				arrayEstantes.add(estante);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return arrayEstantes;
	}

	public int pegarQuantidadeEstante() {
		int numRow = 0;

		try {

			while (rS.next()) {
				numRow++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return numRow;
	}

	public void atualizarCoordenadas(String referencia, int coordenadaX, int coordenadaY, boolean vertical) {
		try {
			stmt = con.prepareStatement("update estante set coordenadax = ?, coordenaday = ?, vertical = ? where nome = ?");
			stmt.setInt(1, coordenadaX);
			stmt.setInt(2, coordenadaY);
			stmt.setBoolean(3, vertical);
			stmt.setString(4, referencia);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Cadastra uma nova estante.
	public void cadastrarEstante(String referencia) {

		try {
			stmt = con.prepareStatement("insert into estante (nome) values (?)");
			stmt.setString(1, referencia);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Retorna o id de uma estante de acordo com o nome.
	public int pegarIdEstante(String nome) {

		int idEstante = -1;

		try {
			stmt = con.prepareStatement("Select id from estante where nome = ?");
			stmt.setString(1, nome);
			rS = stmt.executeQuery();

			while (rS.next()) {
				idEstante = Integer.parseInt(rS.getString("id"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return idEstante;
	}
	
	// Se alguma estante já foi posicionada retorna false, caso contrário retorna true.
	public boolean verificarPrimeiraExecucao() {
		
		try {
			stmt = con.prepareStatement("Select coordenadax, coordenaday from estante");
			rS = stmt.executeQuery();

			while (rS.next()) {
				
				if(rS.getInt("coordenadax") != 0 || rS.getInt("coordenaday") != 0) {
					return false;					
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	// Remove todas as coordenadas x e y salvas nas estantes.
	public void removerCoordenadasEstantes() {
		
		try {
			stmt = con.prepareStatement("update estante set coordenadax = ?, coordenaday = ?");
			stmt.setInt(1, 0);
			stmt.setInt(2, 0);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void excluirEstanteELivros(Integer id) {
		try {
			stmt = con.prepareStatement("delete from estante where id = ?");
			stmt.setInt(1, id);
	
			stmt.executeUpdate();
			
			stmt = con.prepareStatement("delete from livro where id_estante = ?");
			stmt.setInt(1, id);
	
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
