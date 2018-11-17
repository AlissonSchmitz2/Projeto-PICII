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

			String itensEstante = "";
			while (rS.next()) {
				for (int i = 1; i < 5; i++) {
					itensEstante += rS.getString(i) + ";";
				}
				String[] auxEstantes = itensEstante.split(";");

				Estante estante = new Estante();
				estante.setId(Integer.parseInt(auxEstantes[0]));
				estante.setLivros(livroDao.pegarLivrosCadastrados(Integer.parseInt(auxEstantes[0])));
				estante.setNome(auxEstantes[1]);
				estante.setCoordenadaX(Integer.parseInt(auxEstantes[2]));
				estante.setCoordenadaY(Integer.parseInt(auxEstantes[3]));

				arrayEstantes.add(estante);
				itensEstante = "";

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

	public void atualizarCoordenadas(String referencia, int coordenadaX, int coordenadaY) {
		try {
			stmt = con.prepareStatement("update estante set coordenadax = ?, coordenaday = ? where nome = ?");
			stmt.setInt(1, coordenadaX);
			stmt.setInt(2, coordenadaY);
			stmt.setString(3, referencia);

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
			stmt.setNull(1, java.sql.Types.INTEGER);
			stmt.setNull(2, java.sql.Types.INTEGER);
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
