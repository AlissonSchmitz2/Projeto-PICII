package br.com.projetopicii.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.projetopicii.connection.ConnectionFactory;
import br.com.projetopicii.model.bean.Usuario;
import br.com.projetopicii.view.AdministradorMainWindow;
import br.com.projetopicii.view.CadastrarPrimeiroUser;
import br.com.projetopicii.view.LoginWindow;
import br.com.projetopicii.view.MainWindow;

public class UsuarioDao {

	Connection con = ConnectionFactory.getConnection();
	PreparedStatement stmt = null;
	ResultSet rS = null;
	LoginWindow lW = null;

	public UsuarioDao() {

	}

	public UsuarioDao(LoginWindow lW) {
		this.lW = lW;
	}

	public void checkLogin(Usuario usuario,MainWindow mW) {

		try {
			if (usuario.getLogin().equals("admin") && usuario.getSenha().equals("admin")) {
				stmt = con.prepareStatement("Select * from usuario where nome = ? and senha = ? and id = 1");
				stmt.setString(1, usuario.getLogin());
				stmt.setString(2, usuario.getSenha());

				rS = stmt.executeQuery();
				if (rS.next()) {
					new CadastrarPrimeiroUser().setVisible(true);
				}
			} else {

				stmt = con.prepareStatement("Select * from usuario where nome = ? and senha = ?");
				stmt.setString(1, usuario.getLogin());
				stmt.setString(2, usuario.getSenha());

				rS = stmt.executeQuery();

				if (rS.next()) {
					login();
					mW.setVisible(false);
				}
			}
		} catch (SQLException e) {
			// TODO: Lançar exception correta
		} finally {
			ConnectionFactory.closeConnection(con, stmt, rS);
		}

	}

	public void registerFirstLogin(Usuario usuario) {
		try {
			stmt = con.prepareStatement("update usuario set usuario = ?,senha = ? where id = 1");
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());

			stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
	}

	public void login() {
		new AdministradorMainWindow().setVisible(true);
		lW.setVisible(false);
	}

	public void registerUser(Usuario usuario) {
		try {
			stmt = con.prepareStatement("insert into usuario (usuario,senha) values(?,?)");
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}

	}

}
