package br.com.projetopicii.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import br.com.projetopicii.auxiliaries.CellRenderer;
import br.com.projetopicii.model.LivrosTableModel;
import br.com.projetopicii.model.bean.Livro;
import br.com.projetopicii.model.dao.EstanteDao;
import br.com.projetopicii.model.dao.LivroDao;
import br.com.projetopicii.pictures.ImageController;

public class BuscarLivrosWindow extends AbstractWindowFrame {
	private static final long serialVersionUID = -4815632540386262125L;
	
	//Componentes tela.
	private JTextField txfBusca;
	private JButton btnBuscar;
	private JButton btnLimpar;
	private JButton btnGerarLocalizacao;
	private JLabel labelDescricao;
	private ButtonGroup radiosBusca = new ButtonGroup();
	private JRadioButton r_titulo;
	private JRadioButton r_autor;
	private JRadioButton r_genero;
		
	//JTable.
	private JTable tableLivros;
	private TableModel livrosTableModel = new LivrosTableModel();
	private JScrollPane scrollpaneTable;
	private ArrayList<Livro> arrayLivros = new ArrayList<>();
	private String tituloSelecionado;
	
	//Tamanho da Tela.
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//Tecla ENTER.
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {				
				btnBuscar.doClick();				 
			}
		}
	};	
	
	public BuscarLivrosWindow() {
		
		super("Consultar Acervo");	
		super.setClosable(false);
		setContentPane(new NewContentPane());
		setLayout(null);
		
		//LivroDao lD = new LivroDao();
		//arrayLivros = lD.pegarLivrosCadastrados();
		//((LivrosTableModel) livrosTableModel).addRow(arrayLivros);;
		criarComponentes();
	}
	
private void criarComponentes() {		
		
	labelDescricao = new JLabel("Busca:");
	labelDescricao.setBounds((int) (screenSize.getWidth() / 2) - 280, (int) (screenSize.getHeight() / 2) - 270, 100, 25);
	getContentPane().add(labelDescricao);
		
	txfBusca = new JTextField();
	txfBusca.setBounds((int) (screenSize.getWidth() / 2) - 230, (int) (screenSize.getHeight() / 2) - 270, 450, 25);
	txfBusca.setToolTipText("Digite a informação a ser pesquisada");	
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
			
			// TODO: realizar a busca e inserir os resultados na table.
			
			if(radiosBusca.getSelection() == r_titulo.getModel()) {
					
				//Buscar por título.
				
				//TESTE
				
				Livro livro = new Livro();
				livro.setTitulo("Jogador Número 1");
				livro.setAutor("Wilian Hendler Borges");
				livro.setAnoLancamento("2017");
				livro.setGenero("Ficção Cientifica");
				livro.setIdioma("Português");
				
				Livro livro2 = new Livro();
				livro2.setTitulo("Jogador Número 2");
				livro2.setAutor("Wilian Hendler Borges");
				livro2.setAnoLancamento("2017");
				livro2.setGenero("Ficção Cientifica");
				livro2.setIdioma("Português");
				
				arrayLivros.add(livro);
				arrayLivros.add(livro2);
				
				((LivrosTableModel) livrosTableModel).addRow(arrayLivros); 
				
			} else if(radiosBusca.getSelection() == r_autor.getModel()) {
					
				//Buscar por autor.
			} else {
					
				//Buscar por gênero.
					
			}
				
		}
	});
	btnBuscar.setBounds((int) (screenSize.getWidth() / 2) + 230, (int) (screenSize.getHeight() / 2) - 270, 90, 25);
	btnBuscar.addKeyListener(acao);
	getContentPane().add(btnBuscar);
	
	btnLimpar = new JButton(new AbstractAction("Limpar") {
		private static final long serialVersionUID = 9168726551391956466L;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			txfBusca.setText("");
			txfBusca.requestFocus();
		}
	});
	btnLimpar.setBounds((int) (screenSize.getWidth() / 2) + 330, (int) (screenSize.getHeight() / 2) - 270, 90, 25);
	getContentPane().add(btnLimpar);
	
	btnGerarLocalizacao = new JButton(new AbstractAction("Gerar Localização") {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO: Abrir a tela do caminho a ser seguido com as estantes pintadas.	
			
		}
	});
	btnGerarLocalizacao.setBounds((int) (screenSize.getWidth() / 2) + 255, (int) (screenSize.getHeight() / 2) - 235, 150, 25);
	btnGerarLocalizacao.setEnabled(false);
	getContentPane().add(btnGerarLocalizacao);
		
	labelDescricao = new JLabel("Título");
	labelDescricao.setBounds((int) (screenSize.getWidth() / 2) - 210, (int) (screenSize.getHeight() / 2) - 240, 100, 25);
	getContentPane().add(labelDescricao);
		
	r_titulo = new JRadioButton("Título");
	r_titulo.setOpaque(false);
	r_titulo.setBounds((int) (screenSize.getWidth() / 2) - 230, (int) (screenSize.getHeight() / 2) - 240, 20, 25);
	radiosBusca.add(r_titulo);
	r_titulo.setSelected(true);
	getContentPane().add(r_titulo);
		
	labelDescricao = new JLabel("Autor");
	labelDescricao.setBounds((int) (screenSize.getWidth() / 2) - 150, (int) (screenSize.getHeight() / 2) - 240, 100, 25);
	getContentPane().add(labelDescricao);
		
	r_autor = new JRadioButton("Autor");
	r_autor.setOpaque(false);
	r_autor.setBounds((int) (screenSize.getWidth() / 2) - 170, (int) (screenSize.getHeight() / 2) - 240, 20, 25);
	radiosBusca.add(r_autor);
	getContentPane().add(r_autor);
		
	labelDescricao = new JLabel("Gênero");
	labelDescricao.setBounds((int) (screenSize.getWidth() / 2) - 90, (int) (screenSize.getHeight() / 2) - 240, 100, 25);
	getContentPane().add(labelDescricao);
		
	r_genero = new JRadioButton("Gênero");
	r_genero.setOpaque(false);
	r_genero.setBounds((int) (screenSize.getWidth() / 2) - 110, (int) (screenSize.getHeight() / 2) - 240, 20, 25);
	radiosBusca.add(r_genero);
	getContentPane().add(r_genero);
		
	tableLivros = new JTable(livrosTableModel);
	criarJTable(tableLivros);
	tableLivros.setDefaultRenderer(Object.class, new CellRenderer());
	scrollpaneTable = new JScrollPane(tableLivros);
	scrollpaneTable.setBounds((int) (screenSize.getWidth() / 2) - 450, (int) (screenSize.getHeight() / 2) - 200 , 1000, 460);
	scrollpaneTable.setVisible(true);
	getContentPane().add(scrollpaneTable);
		
}

private void criarJTable(JTable table) {
	
	
	// Habilita a seleção por linha
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	// Ação Seleção de uma linha
	table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent event) {
			habilitarBotaoGerarLocalizacao();

			if (table.getSelectedRow() != -1) {
				
				tituloSelecionado = table.getValueAt(table.getSelectedRow(), 0).toString();
			}
		}
	});

	// Double Click na linha
	table.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				
				//TODO: Abrir a tela do caminho a ser seguido com as estantes pintadas.
			}
		}
	});
		
	//Setar tamanho de cada coluna.
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
		
		//Fundo para tela HD.
		if(screenSize.getWidth() < 1400) {
			g.drawImage(ImageController.findMyBook_1366x768.getImage(), 0, 0, this);
		} 
		//Fundo para tela Full HD.
		else {
			g.drawImage(ImageController.findMyBook_1920x1080.getImage(), 0, 0, this);
		}
	}

}
}
