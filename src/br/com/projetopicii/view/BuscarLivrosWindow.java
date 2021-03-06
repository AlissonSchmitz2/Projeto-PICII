package br.com.projetopicii.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import br.com.projetopicii.grafo.Grafo;
import br.com.projetopicii.algoritmo.Dijkstra;
import br.com.projetopicii.auxiliaries.CellRenderer;
import br.com.projetopicii.model.CaminhoBiblioteca;
import br.com.projetopicii.model.LivrosTableModel;
import br.com.projetopicii.model.bean.Estante;
import br.com.projetopicii.model.bean.Livro;
import br.com.projetopicii.model.bean.Terminal;
import br.com.projetopicii.model.dao.EstanteDao;
import br.com.projetopicii.model.dao.LivroDao;
import br.com.projetopicii.model.dao.TerminalDao;
import br.com.projetopicii.pictures.ImageController;

public class BuscarLivrosWindow extends AbstractWindowFrame {
	private static final long serialVersionUID = -4815632540386262125L;

	// Componentes tela.
	private JTextField txfBusca;
	private JButton btnBuscar;
	private JButton btnLimpar;
	private JButton btnGerarLocalizacao;
	private JLabel labelDescricao;
	private ButtonGroup radiosBusca = new ButtonGroup();
	private JRadioButton r_titulo;
	private JRadioButton r_autor;
	private JRadioButton r_genero;

	// Banco de dados (Livros)
	LivroDao livroDao;
	EstanteDao estanteDao;
	TerminalDao terminalDao;

	// JTable.
	private JTable tableLivros;
	private TableModel livrosTableModel;
	private JScrollPane scrollpaneTable;
	private ArrayList<Livro> arrayLivros = new ArrayList<>();
	private ArrayList<Estante> arrayEstantes = new ArrayList<>();
	private ArrayList<CaminhoBiblioteca> listCB = new ArrayList<>();
	private String tituloSelecionado;
	private String generoSelecionado;

	// Tamanho da Tela.
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	// Desktop tela principal.
	private JDesktopPane desktop;

	// Frames
	private ResultadoCaminhoWindow frameResultadoCaminhoWindow;

	// Cria��o da variavel grafo para calculo do dijkstra
	Grafo grafo;

	// Quantidade de livros afins por estante
	int[] quantidadeLivrosAfim;
	
	//Indices do menor/melhor caminho
	ArrayList<Integer> indicesMenorCaminho = null;

	// Tecla ENTER.
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				btnBuscar.doClick();
			}
		}
	};

	public BuscarLivrosWindow(JDesktopPane desktop) {

		super("Consultar Acervo");
		super.setClosable(false);
		setContentPane(new NewContentPane());
		setLayout(null);

		livrosTableModel = new LivrosTableModel();
		livroDao = new LivroDao();

		arrayLivros = livroDao.pegarLivrosCadastrados();
		((LivrosTableModel) livrosTableModel).limpar();
		((LivrosTableModel) livrosTableModel).addRow(arrayLivros);
		this.desktop = desktop;
		
		estanteDao = new EstanteDao();
		arrayEstantes = estanteDao.pegarArrayEstantes(false);
		
		terminalDao = new TerminalDao();
		
		criarComponentes();
	}

	private void criarComponentes() {

		labelDescricao = new JLabel("Busca:");
		labelDescricao.setBounds((int) (screenSize.getWidth() / 2) - 280, (int) (screenSize.getHeight() / 2) - 270, 100,
				25);
		getContentPane().add(labelDescricao);

		txfBusca = new JTextField();
		txfBusca.setBounds((int) (screenSize.getWidth() / 2) - 230, (int) (screenSize.getHeight() / 2) - 270, 450, 25);
		txfBusca.setToolTipText("Digite a informa��o a ser pesquisada");
		txfBusca.addKeyListener(acao);
		getContentPane().add(txfBusca);
		txfBusca.requestFocus();

		btnBuscar = new JButton(new AbstractAction("Buscar") {
			private static final long serialVersionUID = -998996978922572909L;

			@Override
			public void actionPerformed(ActionEvent e) {

				((LivrosTableModel) livrosTableModel).limpar();
				arrayLivros.clear();
				btnGerarLocalizacao.setEnabled(false);

				if (radiosBusca.getSelection() == r_titulo.getModel()) {

					// Buscar por t�tulo.
					arrayLivros = livroDao.pegarLivrosCadastrados(txfBusca.getText().toUpperCase(), "titulo");
					((LivrosTableModel) livrosTableModel).addRow(arrayLivros);

				} else if (radiosBusca.getSelection() == r_autor.getModel()) {

					// Buscar por autor.
					arrayLivros = livroDao.pegarLivrosCadastrados(txfBusca.getText().toUpperCase(), "autor");
					((LivrosTableModel) livrosTableModel).addRow(arrayLivros);

				} else {

					// Buscar por g�nero.
					arrayLivros = livroDao.pegarLivrosCadastrados(txfBusca.getText().toUpperCase(), "genero");
					((LivrosTableModel) livrosTableModel).addRow(arrayLivros);

				}

			}
		});
		btnBuscar.setBounds((int) (screenSize.getWidth() / 2) + 230, (int) (screenSize.getHeight() / 2) - 270, 90, 25);
		btnBuscar.addKeyListener(acao);
		btnBuscar.setToolTipText("Buscar a informa��o digitada");
		getContentPane().add(btnBuscar);

		btnLimpar = new JButton(new AbstractAction("Limpar") {
			private static final long serialVersionUID = 9168726551391956466L;

			@Override
			public void actionPerformed(ActionEvent e) {

				txfBusca.setText("");
				txfBusca.requestFocus();
				arrayLivros.clear();
				arrayLivros = livroDao.pegarLivrosCadastrados();
				((LivrosTableModel) livrosTableModel).limpar();
				((LivrosTableModel) livrosTableModel).addRow(arrayLivros);
			}
		});
		btnLimpar.setBounds((int) (screenSize.getWidth() / 2) + 330, (int) (screenSize.getHeight() / 2) - 270, 90, 25);
		btnLimpar.setToolTipText("Limpar a busca e recuperar todo o acervo");
		getContentPane().add(btnLimpar);

		btnGerarLocalizacao = new JButton(new AbstractAction("Gerar Localiza��o") {
			private static final long serialVersionUID = -6208535404081754764L;
			
			@Override
			public void actionPerformed(ActionEvent e) {
					Dijkstra djk;
				
					criarCaminhoBiblioteca();
					
					grafo = new Grafo();
					try {
						grafo.montarGrafo(listCB);
						livroDao = new LivroDao();
						Livro livro = livroDao.pegarLivroPorNome(tituloSelecionado);
						System.out.println(livro.getId_Estante());
						djk = new Dijkstra(grafo, arrayEstantes.get(0).getId(), livro.getId_Estante());
						indicesMenorCaminho = djk.pegarMenorCaminho();
						
						for(int i = 0; i < indicesMenorCaminho.size();i++) {
							System.out.println(indicesMenorCaminho.get(i));
						}
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					frameResultadoCaminhoWindow = new ResultadoCaminhoWindow(tituloSelecionado,indicesMenorCaminho);
					abrirFrame(frameResultadoCaminhoWindow);
				}
			
		});
		btnGerarLocalizacao.setBounds((int) (screenSize.getWidth() / 2) + 255, (int) (screenSize.getHeight() / 2) - 235,
				150, 25);
		btnGerarLocalizacao.setEnabled(false);
		btnGerarLocalizacao.setToolTipText("Encontrar a estante do livro selecionado");
		getContentPane().add(btnGerarLocalizacao);

		labelDescricao = new JLabel("T�tulo");
		labelDescricao.setBounds((int) (screenSize.getWidth() / 2) - 210, (int) (screenSize.getHeight() / 2) - 240, 100,
				25);
		getContentPane().add(labelDescricao);

		r_titulo = new JRadioButton("T�tulo");
		r_titulo.setOpaque(false);
		r_titulo.setBounds((int) (screenSize.getWidth() / 2) - 230, (int) (screenSize.getHeight() / 2) - 240, 20, 25);
		radiosBusca.add(r_titulo);
		r_titulo.setSelected(true);
		getContentPane().add(r_titulo);

		labelDescricao = new JLabel("Autor");
		labelDescricao.setBounds((int) (screenSize.getWidth() / 2) - 150, (int) (screenSize.getHeight() / 2) - 240, 100,
				25);
		getContentPane().add(labelDescricao);

		r_autor = new JRadioButton("Autor");
		r_autor.setOpaque(false);
		r_autor.setBounds((int) (screenSize.getWidth() / 2) - 170, (int) (screenSize.getHeight() / 2) - 240, 20, 25);
		radiosBusca.add(r_autor);
		getContentPane().add(r_autor);

		labelDescricao = new JLabel("G�nero");
		labelDescricao.setBounds((int) (screenSize.getWidth() / 2) - 90, (int) (screenSize.getHeight() / 2) - 240, 100,
				25);
		getContentPane().add(labelDescricao);

		r_genero = new JRadioButton("G�nero");
		r_genero.setOpaque(false);
		r_genero.setBounds((int) (screenSize.getWidth() / 2) - 110, (int) (screenSize.getHeight() / 2) - 240, 20, 25);
		radiosBusca.add(r_genero);
		getContentPane().add(r_genero);

		tableLivros = new JTable(livrosTableModel);
		criarJTable(tableLivros);
		tableLivros.setDefaultRenderer(Object.class, new CellRenderer());
		scrollpaneTable = new JScrollPane(tableLivros);
		scrollpaneTable.setBounds((int) (screenSize.getWidth() / 2) - 450, (int) (screenSize.getHeight() / 2) - 200,
				1000, 460);
		scrollpaneTable.setVisible(true);
		getContentPane().add(scrollpaneTable);

	}

	private void criarJTable(JTable table) {

		// Habilita a sele��o por linha
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// A��o Sele��o de uma linha
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				habilitarBotaoGerarLocalizacao();

				if (table.getSelectedRow() != -1) {
					generoSelecionado = table.getValueAt(table.getSelectedRow(), 2).toString();
					tituloSelecionado = table.getValueAt(table.getSelectedRow(), 0).toString();
				}
			}
		});

		// Double Click na linha
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Dijkstra djk;
				
				if (e.getClickCount() == 2) {

					criarCaminhoBiblioteca();
					
					grafo = new Grafo();
					try {
						grafo.montarGrafo(listCB);
						livroDao = new LivroDao();
						Livro livro = livroDao.pegarLivroPorNome(tituloSelecionado);
						System.out.println(livro.getId_Estante());
						djk = new Dijkstra(grafo, arrayEstantes.get(0).getId(), livro.getId_Estante());
						indicesMenorCaminho = djk.pegarMenorCaminho();
						
						for(int i = 0; i < indicesMenorCaminho.size();i++) {
							System.out.println(indicesMenorCaminho.get(i));
						}
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					frameResultadoCaminhoWindow = new ResultadoCaminhoWindow(tituloSelecionado,indicesMenorCaminho);
					abrirFrame(frameResultadoCaminhoWindow);
				}
			}
		});

		// Setar tamanho de cada coluna.
		table.getColumnModel().getColumn(0).setPreferredWidth(200);
		table.getColumnModel().getColumn(1).setPreferredWidth(160);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(3).setPreferredWidth(60);
		table.getColumnModel().getColumn(4).setPreferredWidth(60);
		table.getColumnModel().getColumn(5).setPreferredWidth(30);
	}

	private void habilitarBotaoGerarLocalizacao() {

		btnGerarLocalizacao.setEnabled(true);
	}

	@SuppressWarnings("serial")
	private class NewContentPane extends JPanel {
		protected void paintComponent(final Graphics g) {
			super.paintComponent(g);

			// Fundo para tela HD.
			if (screenSize.getWidth() < 1400) {
				g.drawImage(ImageController.findMyBook_1366x768.getImage(), 0, 0, this);
			}
			// Fundo para tela Full HD.
			else {
				g.drawImage(ImageController.findMyBook_1920x1080.getImage(), 0, 0, this);
			}
		}

	}

	private void abrirFrame(AbstractWindowFrame frame) {
		boolean frameJaExiste = false;

		// Percorre todos os frames adicionados
		for (JInternalFrame addedFrame : desktop.getAllFrames()) {
			// Se o frame a ser adicionado j� estiver aberto
			if (addedFrame.getTitle().equals(frame.getTitle())) {
				frame = (AbstractWindowFrame) addedFrame;
				frameJaExiste = true;

				break;
			}
		}

		try {
			if (!frameJaExiste) {
				desktop.add(frame);
			}

			frame.setSelected(true);
			frame.setMaximum(true);
			frame.setVisible(true);
		} catch (PropertyVetoException e) {
			JOptionPane.showMessageDialog(rootPane, "Houve um erro ao abrir a janela", "", JOptionPane.ERROR_MESSAGE,
					null);
		}
	}

	// Alimenta caminho da biblioteca
	public void criarCaminhoBiblioteca() {
		quantidadeLivrosAfim = new int[arrayEstantes.size()];
		buscarQuantidadeLivrosAfimPorEstante(generoSelecionado);
		
		for (int i = 0; i < arrayEstantes.size(); i++) {
			int cont = 0;
			for (int j = i; j < arrayEstantes.size(); j++) {
				++cont;
				
				if(cont == 4) {
					break;
				}
				//Verifica se n�o � a mesma estante
				if(arrayEstantes.get(i).getNome().equals(arrayEstantes.get(j).getNome())) {
					continue;
				}
				
				//Caso n�o seja...Adiciona a distancia entre os vertices
				CaminhoBiblioteca CM = new CaminhoBiblioteca();
				CM.setCodigoOrigem(arrayEstantes.get(i).getId());
				CM.setCodigoDestino(arrayEstantes.get(j).getId());
				CM.setEstanteOrigem(arrayEstantes.get(i).getNome());
				CM.setEstanteDestino(arrayEstantes.get(j).getNome());
				
				if(quantidadeLivrosAfim[i] == 0) {
					CM.setDistancia(calcularDistancia(arrayEstantes.get(i).getCoordenadaX(),
							arrayEstantes.get(i).getCoordenadaY(),
							arrayEstantes.get(j).getCoordenadaX(),
							arrayEstantes.get(j).getCoordenadaY()));
				}
				//Chama m�todo para calculo da distancia entre dois pontos
				else {
					System.out.println(quantidadeLivrosAfim[i]);
					CM.setDistancia(calcularDistancia(arrayEstantes.get(i).getCoordenadaX(),
							arrayEstantes.get(i).getCoordenadaY(),
							arrayEstantes.get(j).getCoordenadaX(),
							arrayEstantes.get(j).getCoordenadaY())/quantidadeLivrosAfim[i]);
				}
								
				
				CM.setQuantidadeLivrosAfim(quantidadeLivrosAfim[i]);
				
				listCB.add(CM);
			}
		}
	}

	//Busca quantidade de livros afim de acordo com o selecionado na busca
	public void buscarQuantidadeLivrosAfimPorEstante(String generoSelecionado) {

		for (int i = 0; i < arrayEstantes.size(); i++) {
			int cont = 0;

			for (int j = 0; j < arrayEstantes.get(i).getLivros().size(); j++) {
				if (arrayEstantes.get(i).getLivros().get(j).getGenero().equals(generoSelecionado)) {
					cont++;
				}
			}
			quantidadeLivrosAfim[i] = cont;
			cont = 0;
		}
	}
	
	//Calcula distancia pelo m�todo euclidiano
	public double calcularDistancia(double x1,double y1,double x2,double y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}
	
	public int buscaIndice(String tituloSelecionado) {
		for(int i = 0;i<arrayEstantes.size();i++) {
			for(int j = 0;j<arrayEstantes.get(i).getLivros().size();j++) {
			if(tituloSelecionado.equals(arrayEstantes.get(i).getLivros().get(j).getTitulo())) {
				return arrayEstantes.get(i).getLivros().get(j).getId();
				}
			}
		}
		
		return 0;
	}
}
