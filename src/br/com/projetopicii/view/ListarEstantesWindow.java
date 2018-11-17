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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.projetopicii.model.bean.Estante;
import br.com.projetopicii.model.dao.EstanteDao;
import br.com.projetopicii.table.model.EstanteTableModel;

public class ListarEstantesWindow extends AbstractGridWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				buscarEstantes();
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

	private JTable jTableEstantes;
	private EstanteTableModel model;
	private JDesktopPane desktop;
	private List<Estante> listaEstantes = new ArrayList<Estante>();
	
	public ListarEstantesWindow(JDesktopPane desktop) {
		super("Lista de estantes");

		this.desktop = desktop;
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
				EstanteDao eD = new EstanteDao();
				
				eD.excluirEstanteELivros(Integer.parseInt(idSelecionado));
			
				// Reseta a lista e atualiza JTable novamente
				listaEstantes = eD.pegarArrayEstantes(false);
				listaEstantes = eD.pegarArrayEstantes(true);
				model.limpar();
				model.addListaDeEstantes(listaEstantes);

				// Limpa seleção
				jTableEstantes.getSelectionModel().clearSelection();

				// Desabilita botão de ações (uma vez que a linha selecionada anteriormente não
				// existe, desabilita botões de ação
				desabilitarBotoesDeAcoes();
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
					buscarEstantes();	
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

					model.addListaDeEstantes(listaEstantes);
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
						model.addListaDeEstantes(listaEstantes);
					} catch (Exception e2) {
						System.err.printf("Erro ao iniciar lista de alunos: %s.\n", e2.getMessage());
					}
				}
			}
		});
	}

	public void buscarEstantes() {

		model.limpar();				
	}
	
	private void abrirFrame(AbstractWindowFrame frame) {
		desktop.add(frame);

		frame.showFrame();
	}

	private void carregarGrid() {
		model = new EstanteTableModel();
		jTableEstantes = new JTable(model);

		// Habilita a seleção por linha
		jTableEstantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Ação Seleção de uma linha
		jTableEstantes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				habilitarBotoesDeAcoes();
				
				if (jTableEstantes.getSelectedRow() != -1) {
					idSelecionado = jTableEstantes.getValueAt(jTableEstantes.getSelectedRow(), 0).toString();
				}
			}
		});
		
		//Double Click na linha
		jTableEstantes.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					
				}
			}
		});

		try {
			EstanteDao eD = new EstanteDao();
			listaEstantes = eD.pegarArrayEstantes(false);
			listaEstantes = eD.pegarArrayEstantes(true);
			model.addListaDeEstantes(listaEstantes);
		} catch (Exception e) {
			System.err.printf("Erro ao iniciar lista de alunos: %s.\n", e.getMessage());
		}

		grid = new JScrollPane(jTableEstantes);
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
