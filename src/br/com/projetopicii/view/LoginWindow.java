
package br.com.projetopicii.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import br.com.projetopicii.model.bean.Usuario;
import br.com.projetopicii.model.dao.UsuarioDao;

public class LoginWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	private JTextField txfNome;
	private JPasswordField txfSenha;
	private JButton btnAcessar;
	private JLabel Descricao;
	private String login, senha;
	MainWindow mW = null;

	LoginWindow(MainWindow mW) {
		this.mW = mW;
		setSize(300, 200);
		setTitle("Find my book");
		setLayout(null);
		setResizable(false);
		criarComponentes();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.getContentPane().setBackground(new Color(230, 230, 250));
	}

	public void criarComponentes() {
		Descricao = new JLabel("Login: ");
		Descricao.setBounds(45, 05, 200, 25);
		getContentPane().add(Descricao);

		txfNome = new JTextField();
		txfNome.setBounds(45, 30, 200, 30);
		txfNome.setToolTipText("Informe seu login");
		getContentPane().add(txfNome);

		Descricao = new JLabel("Senha: ");
		Descricao.setBounds(45, 60, 200, 25);
		getContentPane().add(Descricao);

		txfSenha = new JPasswordField();
		txfSenha.setBounds(45, 85, 200, 30);
		txfSenha.setToolTipText("Informe sua senha");
		getContentPane().add(txfSenha);

		txfNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login = txfNome.getText();
					senha = new String(txfSenha.getPassword());

					autenticaUsuario(login, senha);
				}
			}
		});

		txfSenha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login = txfNome.getText();
					senha = new String(txfSenha.getPassword());

					autenticaUsuario(login, senha);
				}
			}
		});

		btnAcessar = new JButton(new AbstractAction("Acessar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				login = txfNome.getText();
				senha = new String(txfSenha.getPassword());

				autenticaUsuario(login, senha);
			}
		});

		btnAcessar.setBounds(45, 125, 100, 25);
		getContentPane().add(btnAcessar);
		btnAcessar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login = txfNome.getText();
					senha = new String(txfSenha.getPassword());

					autenticaUsuario(login, senha);
				}
			}
		});

	}

	public void autenticaUsuario(String login, String senha) {
		try {
			if(verificaCamposObrigatorios()) {
				JOptionPane.showMessageDialog(rootPane, 
						"Preencha todos os campos para cadastrar!", "Alerta",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			Usuario u = new Usuario();
			Usuario u2 = new Usuario();
			UsuarioDao uD = new UsuarioDao(this);
			u2 = uD.pegarUsuarioPorLogin(login);
			
			if(u2 != null) {
				u.setId(u2.getId());
			}
			u.setLogin(login);
			u.setSenha(senha);
			uD.checkLogin(u, mW);
		} catch (Exception message) {
			JOptionPane.showMessageDialog(null, "Erro: " + message);
		}
	}

	public boolean verificaCamposObrigatorios() {
		
		if (login.isEmpty() || senha.isEmpty()) {
			return true;
		}

		return false;
	}

}
