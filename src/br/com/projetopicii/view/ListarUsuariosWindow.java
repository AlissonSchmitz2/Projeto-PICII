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
import br.com.projetopicii.table.model.UsuarioTableModel;

public class ListarUsuariosWindow extends AbstractGridWindow {
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
	
	// Usuário Logado.
	private Usuario usuarioLogado;
	
	public ListarUsuariosWindow(JDesktopPane desktop, Usuario usuarioLogado) {
		super("Lista de Usuários");

		this.desktop = desktop;
		this.usuarioLogado = usuarioLogado;
		criarComponentes();
		carregarGrid();
	}
	
	private void criarComponentes() {
		// Botão de ação Editar
		botaoEditar = new JButton("Editar");
		botaoEditar.setBounds(15, 30, 100, 25);
		botaoEditar.setEnabled(false);
		add(botaoEditar);
		botaoEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO:Implementar método para editar estante com Observer
			}
		});

		// Botão de ação Excluir
		botaoExcluir = new JButton("Excluir");
		botaoExcluir.setBounds(135, 30, 100, 25);
		botaoExcluir.setEnabled(false);
		add(botaoExcluir);
		botaoExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					UsuarioDao uD = new UsuarioDao();
				
				// Não permite que o usuário logado exclua o seu próprio perfil.	
				if (idSelecionado.equals(usuarioLogado.getId().toString())) {

					JOptionPane.showMessageDialog(rootPane, "O usuário logado não pode ser excluído!", "Alerta",
							JOptionPane.WARNING_MESSAGE);
				} else {
					uD.excluirUsuario(Integer.parseInt(idSelecionado));

					// Reseta a lista e atualiza JTable novamente
					listaUsuarios = uD.pegarUsuarios();
					model.limpar();
					model.addListaDeUsuarios(listaUsuarios);

					// Limpa seleção
					jTableUsuarios.getSelectionModel().clearSelection();

					// Desabilita botão de ações (uma vez que a linha selecionada anteriormente não
					// existe, desabilita botões de ação
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

					model.addListaDeUsuarios(listaUsuarios);
				} catch (Exception e2) {
					System.err.printf("Erro ao iniciar lista de alunos: %s.\n", e2.getMessage());
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
						System.err.printf("Erro ao iniciar lista de alunos: %s.\n", e2.getMessage());
					}
				}
			}
		});
	}

	public void buscarUsuarios() {
		//Limpa a lista.
		model.limpar();				
	}
	
	private void abrirFrame(AbstractWindowFrame frame) {
		desktop.add(frame);

		frame.showFrame();
	}

	private void carregarGrid() {
		model = new UsuarioTableModel();
		jTableUsuarios = new JTable(model);

		// Habilita a seleção por linha
		jTableUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Ação Seleção de uma linha
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
					
				}
			}
		});

		try {
			UsuarioDao uD = new UsuarioDao();
			listaUsuarios = uD.pegarUsuarios();
			model.addListaDeUsuarios(listaUsuarios);
		} catch (Exception e) {
			System.err.printf("Erro ao iniciar lista de alunos: %s.\n", e.getMessage());
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

}