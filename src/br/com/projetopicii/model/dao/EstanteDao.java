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

			listaEstante = new String[5];

			int j = 0;
			while (rS.next()) {
				for (int i = 1; i <= columnCount; i++) {
					final Object value = rS.getObject(i);
					listaEstante[j] = value.toString();
				}
				j++;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaEstante;
	}

}
