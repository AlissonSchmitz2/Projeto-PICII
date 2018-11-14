package br.com.projetopicii.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.projetopicii.connection.ConnectionFactory;

public class EstanteDao {

	Connection con = ConnectionFactory.getConnection();
	PreparedStatement stmt = null;
	ResultSet rS = null;
	String[] listaEstante;

	public String[] pegarEstantes() {

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
		} //finally {
//			ConnectionFactory.closeConnection(con, stmt, rS);
//		}
				
		return listaEstante;
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
			stmt = con.prepareStatement("update estante set coordenadaX = ?,coordenadaY = ? where nome = ?");
			//stmt = con.prepareStatement("update usuario set usuario = ?,senha = ? where id = 1");
			stmt.setInt(1, coordenadaX);
			stmt.setInt(2, coordenadaY);
			System.out.println(referencia);
			stmt.setString(3, referencia);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}
