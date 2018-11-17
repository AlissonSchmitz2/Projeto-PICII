package br.com.projetopicii.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import br.com.projetopicii.connection.ConnectionFactory;
import br.com.projetopicii.model.bean.Usuario;
import br.com.projetopicii.view.AdministradorMainWindow;
import br.com.projetopicii.view.LoginWindow;
import br.com.projetopicii.view.MainWindow;

public class UsuarioDao {

	Connection con = ConnectionFactory.getConnection();
	PreparedStatement stmt = null;
	ResultSet rS = null;
	LoginWindow lW = null;
	ArrayList<Usuario> arrayUsuario = new ArrayList<Usuario>();
	
	public UsuarioDao() {

	}

	public UsuarioDao(LoginWindow lW) {
		this.lW = lW;
	}

	public void checkLogin(Usuario usuario, MainWindow mW) {

		try {

			stmt = con.prepareStatement("Select * from usuario where nome = ? and senha = ?");
			stmt.setString(1, usuario.getLogin());
			stmt.setString(2, usuario.getSenha());

			rS = stmt.executeQuery();

			if (rS.next()) {
				login();
				mW.setVisible(false);
			}else {
				JOptionPane.showMessageDialog(null, "Usuario ou senha incorreta!");
			}
		} catch (SQLException e) {
			// TODO: Lançar exception correta
		} finally {
			ConnectionFactory.closeConnection(con, stmt, rS);
		}

	}

	public void registerFirstLogin(Usuario usuario) {
		try {
			stmt = con.prepareStatement("insert into usuario (nome, senha) values (?, ?)");
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
	
	public ArrayList<Usuario> pegarUsuarios() {
		try {
			stmt = con.prepareStatement("Select * from usuario");
			rS = stmt.executeQuery();

			String itemUsuario = "";
			while (rS.next()) {
				for (int i = 1; i < 3; i++) {
					itemUsuario += rS.getString(i) + ";";
				}
				String[] auxEstantes = itemUsuario.split(";");

				Usuario usuario = new Usuario();
				usuario.setId(Integer.parseInt(auxEstantes[0]));
				usuario.setLogin(auxEstantes[1]);

				arrayUsuario.add(usuario);
				itemUsuario = "";

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return arrayUsuario;
	}
	
	public void excluirUsuario(Integer id) {
		try {
			stmt = con.prepareStatement("delete from usuario where id = ?");
			stmt.setInt(1, id);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
