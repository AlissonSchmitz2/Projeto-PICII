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
	private Usuario usuario;
	
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
				login(usuario);
				mW.setVisible(false);
			}else {
				JOptionPane.showMessageDialog(null, "Usuario ou senha incorreta!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		} finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
	}

	public void login(Usuario usuarioLogado) {
		new AdministradorMainWindow(usuarioLogado).setVisible(true);
		lW.setVisible(false);
	}

	public void registerUser(Usuario usuario, boolean usuarioNovo, int idUsuario) {
		try {
			if(usuarioNovo) {
				stmt = con.prepareStatement("insert into usuario (nome,senha) values(?,?)");
				stmt.setString(1, usuario.getLogin());
				stmt.setString(2, usuario.getSenha());
			} else {
				stmt = con.prepareStatement("update usuario set nome = ?, senha = ? where id = ?");
				stmt.setString(1, usuario.getLogin());
				stmt.setString(2, usuario.getSenha());
				stmt.setInt(3, idUsuario);
			}

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

			while (rS.next()) {

				Usuario usuario = new Usuario();
				usuario.setId(rS.getInt("id"));
				usuario.setLogin(rS.getString("nome"));
				usuario.setSenha(rS.getString("senha"));

				arrayUsuario.add(usuario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return arrayUsuario;
	}
	
	public ArrayList<Usuario> pegarUsuarios(String valorBusca) {
		try {
			stmt = con.prepareStatement("SELECT * FROM usuario WHERE (id = ?) OR (UPPER(nome) LIKE ?)");
			stmt.setString(2, "%" + valorBusca + "%");
			
			try {
				stmt.setInt(1, Integer.parseInt(valorBusca));
			} catch (Exception e) {
				stmt.setInt(1, -1);
			}
			
			rS = stmt.executeQuery();

			while (rS.next()) {

				Usuario usuario = new Usuario();
				usuario.setId(rS.getInt("id"));
				usuario.setLogin(rS.getString("nome"));
				usuario.setSenha(rS.getString("senha"));

				arrayUsuario.add(usuario);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return arrayUsuario;
	}
	
	public Usuario pegarUsuarioPorLogin(String login) {
				
		Usuario usuario = null;
		
		try {
			stmt = con.prepareStatement("select * from usuario where nome = ?");
			stmt.setString(1, login);			
			rS = stmt.executeQuery();
			
			while(rS.next()) {
				
				usuario = new Usuario();
				usuario.setId(rS.getInt("id"));
				usuario.setLogin(rS.getString("nome"));
				usuario.setSenha(rS.getString("senha"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return usuario;
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
	
	// Retorna o usuário de acordo com o ID.
	public Usuario pegarUsuarioPorId(int idUsuario) {
		
		try {
			stmt = con.prepareStatement("select * from usuario where id = ?");
		    stmt.setInt(1, idUsuario);
		    
		    rS = stmt.executeQuery();
		    
		    while(rS.next()) {
		    	usuario = new Usuario();
		    	usuario.setId(rS.getInt("id"));
		    	usuario.setLogin(rS.getString("nome"));
		    	usuario.setSenha(rS.getString("senha"));
		    }			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return usuario;
	}

}
