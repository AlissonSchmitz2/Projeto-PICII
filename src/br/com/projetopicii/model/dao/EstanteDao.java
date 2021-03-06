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
	private Estante estante;

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
	
	// M�todo para a busca na listagem de estantes.
	public ArrayList<Estante> pegarArrayEstantes(String valorBusca) {

		try {
			
			stmt = con.prepareStatement("SELECT * FROM estante WHERE (id = ?) OR (UPPER(nome) LIKE ?) OR (coordenadax = ?) OR"
					+ "(coordenaday = ?)");
			try {
				stmt.setInt(1, Integer.parseInt(valorBusca));
				stmt.setInt(3, Integer.parseInt(valorBusca));
				stmt.setInt(4, Integer.parseInt(valorBusca));
			} catch (Exception e) {
				stmt.setInt(1, -1);
				stmt.setInt(3, -1);
				stmt.setInt(4, -1);
			}
			
			stmt.setString(2, "%" + valorBusca + "%");

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

	// Retorna a estante de acordo com o id.
	public Estante pegarEstantePorId(int idEstante) {
		
		try {
			stmt = con.prepareStatement("select * from estante where id = ?");
			stmt.setInt(1, idEstante);

			rS = stmt.executeQuery();
			
			while(rS.next()) {
				estante = new Estante();
				estante.setId(rS.getInt("id"));
				estante.setNome(rS.getString("nome"));
				estante.setCoordenadaX(rS.getInt("coordenadax"));
				estante.setCoordenadaY(rS.getInt("coordenaday"));
				estante.setVertical(rS.getBoolean("vertical"));
				estante.setLivros(livroDao.pegarLivrosCadastrados(rS.getInt("id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return estante;
	}
	
	// Retorna a estante de acordo com o nome.
	public Estante pegarEstantePorNome(String referencia) {

		try {
			stmt = con.prepareStatement("select * from estante where upper (nome) = ?");
			stmt.setString(1, referencia);

			rS = stmt.executeQuery();

			while (rS.next()) {
				estante = new Estante();
				estante.setId(rS.getInt("id"));
				estante.setNome(rS.getString("nome"));
				estante.setCoordenadaX(rS.getInt("coordenadax"));
				estante.setCoordenadaY(rS.getInt("coordenaday"));
				estante.setVertical(rS.getBoolean("vertical"));
				estante.setLivros(livroDao.pegarLivrosCadastrados(rS.getInt("id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return estante;
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
	public void cadastrarEstante(String referenciaNova, String referenciaAntiga, boolean estanteNova) {

		try {
			if(estanteNova) {
				stmt = con.prepareStatement("insert into estante (nome) values (?)");
				stmt.setString(1, referenciaNova);
			} else {
				stmt = con.prepareStatement("update estante set nome = ? where nome = ?");
				stmt.setString(1, referenciaNova);
				stmt.setString(2, referenciaAntiga);
			}
			
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
	
	// Se alguma estante j� foi posicionada retorna false, caso contr�rio retorna true.
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
