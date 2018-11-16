package br.com.projetopicii.view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import br.com.projetopicii.model.bean.Usuario;
import br.com.projetopicii.model.dao.EstanteDao;
import br.com.projetopicii.model.dao.TerminalDao;
import br.com.projetopicii.model.dao.UsuarioDao;

public class CadastrarPrimeiroUser extends JDialog {

	private static final long serialVersionUID = -111508881355118807L;

	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				cadastrarPrimeiroUser();
			}
		}
	};

	private JTextField txfNome;
	private JPasswordField txfSenha;
	private JButton btnAcessar;
	private JLabel Descricao;
	private String login, senha;
	
	// Instância MainWindow
	private MainWindow mainWindow;
	
	private EstanteDao estanteDao = new EstanteDao();
	private TerminalDao terminalDao = new TerminalDao();

	public CadastrarPrimeiroUser(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
		setSize(250, 200);
		setTitle("Cadastro Administrador");
		setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		criarComponentes();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// Caso a janela de cadastro seja fechada antes da realização do cadastro, o banco de dados
		// é resetado para a próxima execução.
		this.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent evt) {

				estanteDao.removerCoordenadasEstantes();
				terminalDao.deletarTerminal();
				System.exit(0);
			}
		});
		
		// Caso a janela principal seja fechada antes da realização do cadastro, o banco de dados
		// é resetado para a próxima execução.
		mainWindow.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {

				estanteDao.removerCoordenadasEstantes();
				terminalDao.deletarTerminal();
				System.exit(0);
			}
		});
	}

	public void criarComponentes() {
		Descricao = new JLabel("Informe um login para cadastro: ");
		Descricao.setBounds(10, 10, 200, 25);
		getContentPane().add(Descricao);

		txfNome = new JTextField();
		txfNome.setBounds(10, 30, 200, 25);
		getContentPane().add(txfNome);
		txfNome.addKeyListener(acao);

		Descricao = new JLabel("Informe sua senha: ");
		Descricao.setBounds(10, 65, 200, 25);
		getContentPane().add(Descricao);

		txfSenha = new JPasswordField();
		txfSenha.setBounds(10, 85, 200, 25);
		getContentPane().add(txfSenha);
		txfSenha.addKeyListener(acao);

		btnAcessar = new JButton(new AbstractAction("Cadastrar") {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				cadastrarPrimeiroUser();
			}
		});

		btnAcessar.addKeyListener(acao);
		btnAcessar.setBounds(10, 115, 100, 25);
		getContentPane().add(btnAcessar);

	}

	public boolean validarCamposObrigatorios() {
		if ((new String(txfSenha.getPassword()).equals("")) || txfNome.getText().equals("")) {
			return true;
		}

		return false;
	}

	public void cadastrarPrimeiroUser() {
		if (validarCamposObrigatorios()) {
			JOptionPane.showMessageDialog(rootPane, "Informe todos os campos para cadastrar!", "",
					JOptionPane.ERROR_MESSAGE, null);
		} else {
			login = txfNome.getText();
			senha = new String(txfSenha.getPassword());
			Usuario user = new Usuario();
			user.setLogin(login);
			user.setSenha(senha);
			
			UsuarioDao uS = new UsuarioDao();
			uS.registerFirstLogin(user);
			
			JOptionPane.showMessageDialog(null, "Administrador cadastrado com sucesso!");
			JOptionPane.showMessageDialog(null, "Todas as configurações iniciais foram realizadas.\nO programa será reiniciado agora.");

			mainWindow.dispose();
			
			this.setVisible(false);
			
			new MainWindow();
		}
		
	}	
	
}
