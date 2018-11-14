package br.com.projetopicii.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.projetopicii.connection.ConnectionFactory;
import br.com.projetopicii.model.bean.Terminal;

public class TerminalDao {

	Connection con = ConnectionFactory.getConnection();
	PreparedStatement stmt = null;
	ResultSet rS = null;
	Terminal terminalPesquisa = new Terminal();
	
	public Terminal pegarTerminal() {
		
		try {			
			stmt = con.prepareStatement("Select * from terminalpesquisa");
			rS = stmt.executeQuery();
			
			while(rS.next()) {
				terminalPesquisa.setId(rS.getInt("id"));
				terminalPesquisa.setCoordenadaX(rS.getInt("coordenadax"));
				terminalPesquisa.setCoordenadaY(rS.getInt("coordenaday"));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return terminalPesquisa;
	}
	
	public void atualizarCoordenadas(int coordenadaX, int coordenadaY) {
		try {
			stmt = con.prepareStatement("update terminalpesquisa set coordenadax = ?, coordenaday = ? where id = ?");
			stmt.setInt(1, coordenadaX);
			stmt.setInt(2, coordenadaY);
			stmt.setInt(3, 0);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
