package br.com.projetopicii.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

	public String[] pegarNomeEstantes() {

		try {

			stmt = con.prepareStatement("Select nome from estante");
			rS = stmt.executeQuery();

			final ResultSetMetaData metaRS = rS.getMetaData();
			final int columnCount = metaRS.getColumnCount();

			listaEstante = new String[pegarQuantidadeEstante()];

			rS = stmt.executeQuery();

			int j = 0;

			while (rS.next()) {
				listaEstante[j] = rS.getString("nome");
				j++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} // finally {
//			ConnectionFactory.closeConnection(con, stmt, rS);
//		}

		return listaEstante;
	}

	public ArrayList<Estante> pegarArrayEstantes() {

		try {
			stmt = con.prepareStatement("Select * from estante");
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
}
