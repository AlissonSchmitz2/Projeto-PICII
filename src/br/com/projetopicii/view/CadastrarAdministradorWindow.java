package br.com.projetopicii.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import br.com.projetopicii.model.bean.Usuario;
import br.com.projetopicii.model.dao.UsuarioDao;
import br.com.projetopicii.observer.ObserverUsuario;
import br.com.projetopicii.observer.SubjectUsuario;

public class CadastrarAdministradorWindow extends AbstractWindowFrame implements SubjectUsuario {
	private static final long serialVersionUID = -3504919349682454017L;
	
	// Componentes da Tela.
	private JTextField txfUsuario;
	private JPasswordField txfSenha;
	private JButton btnSalvar;
	private JButton btnLimpar;
	private JLabel labelAux;	
	
	// Acesso ao Banco de Dados.
	private UsuarioDao usuarioDao;
	
	// Usuário auxiliar.
	private Usuario usuario;
	private Usuario usuarioAux;
	
	// Observer.
	private ArrayList<ObserverUsuario> observers = new ArrayList<ObserverUsuario>();
	
	// Tecla ENTER.
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				btnSalvar.doClick();
			}
		}
	};

	public CadastrarAdministradorWindow() {
		
		super("Cadastrar Administrador");
		setBackground(new Color(250, 250, 250));
		criarComponentes();
	}
	
	public CadastrarAdministradorWindow(Usuario usuario) {
		
		super("Cadastrar Administrador");
		this.usuario = usuario;
		setBackground(new Color(250, 250, 250));
		criarComponentes();
		setarValores(usuario);
	}
	
	private void criarComponentes() {
		
		labelAux = new JLabel("Login:");
		labelAux.setBounds(15, 10, 250, 25);
		getContentPane().add(labelAux);
		
		txfUsuario = new JTextField();
		txfUsuario.setBounds(15, 30, 200, 25);
		txfUsuario.setToolTipText("Informe o login");
		txfUsuario.addKeyListener(acao);
		getContentPane().add(txfUsuario);
		
		labelAux = new JLabel("Senha:");
		labelAux.setBounds(15, 60, 250, 25);
		getContentPane().add(labelAux);
		
		txfSenha = new JPasswordField();
		txfSenha.setBounds(15, 80, 200, 25);
		txfSenha.setToolTipText("Informe a senha");
		txfSenha.addKeyListener(acao);
		getContentPane().add(txfSenha);
		
		btnSalvar = new JButton(new AbstractAction("Salvar") {
			private static final long serialVersionUID = -2642953701217591758L;

			@Override
			public void actionPerformed(ActionEvent e) {
				cadastrarUsuario();				
			}
		});
		btnSalvar.setBounds(15, 130, 95, 25);
		getContentPane().add(btnSalvar);
		
		btnLimpar = new JButton(new AbstractAction("Limpar") {
			private static final long serialVersionUID = 2989104567835263142L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(usuario != null) {
					setarValores(usuario);
				} else {
					limparCampos();	
				}
			}
		});
		btnLimpar.setBounds(120, 130, 95, 25);
		getContentPane().add(btnLimpar);
	}
	
	private void cadastrarUsuario() {
		
		if(validarCampos()) {
			
			try {			
				
				if(usuario != null) {
					usuario.setLogin(txfUsuario.getText());
					usuario.setSenha(new String(txfSenha.getPassword()));
					usuarioDao.registerUser(usuario, false, usuario.getId());
					notifyObservers(usuario);
					usuario = null;
					JOptionPane.showMessageDialog(null, "Usuário salvo com sucesso!");
					dispose();					
				} else {
					usuario = new Usuario();
					usuario.setLogin(txfUsuario.getText());
					usuario.setSenha(new String(txfSenha.getPassword()));
					usuarioDao.registerUser(usuario, true, -1);
					
					JOptionPane.showMessageDialog(null, "Usuário salvo com sucesso!");
					limparCampos();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(rootPane, 
						"Ocorreu um erro no cadastro!", "Alerta",
						JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}
	
	private boolean validarCampos() {
		
		if(txfUsuario.getText().isEmpty() || new String(txfSenha.getPassword()).isEmpty()) {
			JOptionPane.showMessageDialog(rootPane, 
					"Preencha todos os campos para cadastrar!", "Alerta",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		usuarioDao = new UsuarioDao();
		if(usuario != null ? !txfUsuario.getText().equals(usuario.getLogin()) : true) {
			usuarioAux = usuarioDao.pegarUsuarioPorLogin(txfUsuario.getText());		
			if(usuarioAux != null) {
				JOptionPane.showMessageDialog(rootPane, 
						"O Login digitado já existe!", "Alerta",
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		
		return true;		
	}
	
	private void limparCampos() {
		txfSenha.setText("");
		txfUsuario.setText("");
		txfUsuario.requestFocus();
	}
	
	private void setarValores(Usuario usuario) {

		 txfUsuario.setText(usuario.getLogin());
		 txfSenha.setText(usuario.getSenha());
	}

	@Override
	public void addObserver(ObserverUsuario o) {
		observers.add(o);

	}

	@Override
	public void removeObserver(ObserverUsuario o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Usuario usuario) {
		Iterator<ObserverUsuario> it = observers.iterator();
		while (it.hasNext()) {
			ObserverUsuario observer = (ObserverUsuario) it.next();
			observer.update(usuario);
		}

	}
}
