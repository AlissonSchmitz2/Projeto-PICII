package br.com.projetopicii.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.projetopicii.model.bean.Usuario;
import br.com.projetopicii.model.dao.UsuarioDao;
import br.com.projetopicii.observer.ObserverUsuario;
import br.com.projetopicii.table.model.UsuarioTableModel;

public class ListarUsuariosWindow extends AbstractGridWindow implements ObserverUsuario {
	private static final long serialVersionUID = 1L;

	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				buscarUsuarios();
			}
		}
	};

	private JButton botaoExcluir;
	private JButton botaoEditar;
	private String idSelecionado;
	
	//Componentes Para Busca
	private JTextField txfBuscar;
	private JButton btnBuscar;
	private JButton btnLimparBusca;
	private JLabel labelInformacao;

	private JTable jTableUsuarios;
	private UsuarioTableModel model;
	private JDesktopPane desktop;
	private List<Usuario> listaUsuarios = new ArrayList<Usuario>();
	private Usuario usuario;
	private UsuarioDao uD;
	
	// Usu�rio Logado.
	private Usuario usuarioLogado;
	
	public ListarUsuariosWindow(JDesktopPane desktop, Usuario usuarioLogado) {
		super("Lista de Usu�rios");

		this.desktop = desktop;
		this.usuarioLogado = usuarioLogado;
		criarComponentes();
		carregarGrid();
	}
	
	private void criarComponentes() {
		// Bot�o de a��o Editar
		botaoEditar = new JButton("Editar");
		botaoEditar.setBounds(15, 30, 100, 25);
		botaoEditar.setEnabled(false);
		add(botaoEditar);
		botaoEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				usuario = new Usuario();
				uD = new UsuarioDao();
				usuario = uD.pegarUsuarioPorId(Integer.parseInt(idSelecionado));
				
				if (usuario instanceof Usuario) {
					abrirEdicaoUsuario(usuario);
				}
			}
		});

		// Bot�o de a��o Excluir
		botaoExcluir = new JButton("Excluir");
		botaoExcluir.setBounds(135, 30, 100, 25);
		botaoExcluir.setEnabled(false);
		add(botaoExcluir);
		botaoExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					uD = new UsuarioDao();
				
				// N�o permite que o usu�rio logado exclua o seu pr�prio perfil.
				if (idSelecionado.equals(usuarioLogado.getId().toString())) {

					JOptionPane.showMessageDialog(rootPane, "O usu�rio logado n�o pode ser exclu�do!", "Alerta",
							JOptionPane.WARNING_MESSAGE);
				} else {
					uD.excluirUsuario(Integer.parseInt(idSelecionado));

					// Reseta a lista e atualiza JTable novamente
					listaUsuarios = uD.pegarUsuarios();
					model.limpar();
					model.addListaDeUsuarios(listaUsuarios);

					// Limpa sele��o
					jTableUsuarios.getSelectionModel().clearSelection();

					// Desabilita bot�o de a��es (uma vez que a linha selecionada anteriormente n�o
					// existe, desabilita bot�es de a��o
					desabilitarBotoesDeAcoes();
				}
			}
		});
		
		//Componentes Para Busca
		labelInformacao = new JLabel("Busca:");
		labelInformacao.setBounds(280, 30, 100, 25);
		getContentPane().add(labelInformacao);	    
		
		txfBuscar = new JTextField();
		txfBuscar.setBounds(330, 30, 200, 25);
		getContentPane().add(txfBuscar);
		txfBuscar.addKeyListener(acao);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(550, 30, 100, 25);
		getContentPane().add(btnBuscar);
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
					buscarUsuarios();	
			}
		});
		
		btnBuscar.addKeyListener(acao);
		
		btnLimparBusca = new JButton("Limpar Busca");
		btnLimparBusca.setBounds(670, 30, 140, 25);
		getContentPane().add(btnLimparBusca);
		btnLimparBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Limpa o campo de busca e mostra a lista inteira novamente.
				txfBuscar.setText("");
				model.limpar();
				try {
					UsuarioDao uD = new UsuarioDao();
					listaUsuarios = uD.pegarUsuarios();
					model.addListaDeUsuarios(listaUsuarios);
				} catch (Exception e2) {
					System.err.printf("Erro ao iniciar lista de usu�rios: %s.\n", e2.getMessage());
				}
			}
		});
		
		//Enter para limpar
		btnLimparBusca.addKeyListener(new KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					//Limpa o campo de busca e mostra a lista inteira novamente.
					txfBuscar.setText("");
					model.limpar();
					try {
						model.addListaDeUsuarios(listaUsuarios);
					} catch (Exception e2) {
						System.err.printf("Erro ao iniciar lista de usu�rios: %s.\n", e2.getMessage());
					}
				}
			}
		});
	}

	public void buscarUsuarios() {
		
		UsuarioDao uD = new UsuarioDao();
		listaUsuarios = uD.pegarUsuarios(txfBuscar.getText().toUpperCase());
		model.limpar();
		model.addListaDeUsuarios(listaUsuarios);				
	}
	
	private void abrirFrame(AbstractWindowFrame frame) {
		desktop.add(frame);

		frame.showFrame();
	}

	private void carregarGrid() {
		model = new UsuarioTableModel();
		jTableUsuarios = new JTable(model);

		// Habilita a sele��o por linha
		jTableUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// A��o Sele��o de uma linha
		jTableUsuarios.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				habilitarBotoesDeAcoes();
				
				if (jTableUsuarios.getSelectedRow() != -1) {
					idSelecionado = jTableUsuarios.getValueAt(jTableUsuarios.getSelectedRow(), 0).toString();
				}
			}
		});
		
		//Double Click na linha
		jTableUsuarios.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					usuario = new Usuario();
					uD = new UsuarioDao();
					usuario = uD.pegarUsuarioPorId(Integer.parseInt(idSelecionado));
					
					if (usuario instanceof Usuario) {
						abrirEdicaoUsuario(usuario);
					}
				}
			}
		});

		try {
			UsuarioDao uD = new UsuarioDao();
			listaUsuarios = uD.pegarUsuarios();
			model.addListaDeUsuarios(listaUsuarios);
		} catch (Exception e) {
			System.err.printf("Erro ao iniciar lista de usu�rios: %s.\n", e.getMessage());
		}

		grid = new JScrollPane(jTableUsuarios);
		setLayout(null);
		redimensionarGrid(grid);
		grid.setVisible(true);

		add(grid);
	}

	protected void windowFoiRedimensionada() {
		if (grid != null) {
			redimensionarGrid(grid);
		}
	}
	
	private void desabilitarBotoesDeAcoes() {
		botaoEditar.setEnabled(false);
		botaoExcluir.setEnabled(false);
	}
	
	private void habilitarBotoesDeAcoes() {
			botaoEditar.setEnabled(true);
			botaoExcluir.setEnabled(true);
	}
	
	private void abrirEdicaoUsuario(Usuario usuario) {
		
		CadastrarAdministradorWindow frame = new CadastrarAdministradorWindow(usuario);
		frame.addObserver(this);
		abrirFrame(frame);
	}
	
	@Override
	public void update(Usuario usuario) {
		model.limpar();
		listaUsuarios.clear();
		listaUsuarios = uD.pegarUsuarios();
		model.addListaDeUsuarios(listaUsuarios);
	}

}