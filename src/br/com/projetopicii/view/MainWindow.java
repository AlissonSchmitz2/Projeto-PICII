package br.com.projetopicii.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.table.TableModel;

import br.com.projetopicii.model.LivrosTableModel;
import br.com.projetopicii.model.bean.Livro;
import br.com.projetopicii.pictures.ImageController;

public class MainWindow extends JFrame{
	private static final long serialVersionUID = 2630247367422389726L;
	
	//Componentes Janela Principal.
	private JMenu menuBiblioteca;
	private JMenu menuHome;
	private JDesktopPane desktop;	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//Componentes Busca.
	private JTextField txfBusca;
	private JButton btnBuscar;
	private JLabel labelDescricao;
	private ButtonGroup radiosBusca = new ButtonGroup();
	private JRadioButton r_titulo;
	private JRadioButton r_autor;
	private JRadioButton r_genero;
	
	//JTable.
	private JTable tableLivros;
	private TableModel tableModel = new LivrosTableModel();
	private JScrollPane scrollpaneTable;
	private ArrayList<Livro> arrayLivros = new ArrayList<>();
	
	//Tecla ENTER.
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {				
				btnBuscar.doClick();				 
			}
		}
	};	
	
	public MainWindow() {
		super();
			
		desktop = new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		desktop.setVisible(true);
		setContentPane(desktop);
		desktop.setBackground(new Color(250, 250, 250));
		
		inicializar(); 
		
	}
	
	private void inicializar() {
		this.setVisible(true);
		this.setTitle("Find My Book");
		this.setJMenuBar(getWindowMenuBar());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(new Rectangle(0, 0, 796, 713));
		this.setFocusableWindowState(true);			
		super.setContentPane(new NewContentPane());
		this.setLayout(null);
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		criarComponentes();
	}
	
	private void criarComponentes() {
		
		labelDescricao = new JLabel("Busca:");
		labelDescricao.setBounds((int) (screenSize.getWidth() / 2) - 280, (int) (screenSize.getHeight() / 2) - 300, 100, 25);
		getContentPane().add(labelDescricao);
		
		txfBusca = new JTextField();
		txfBusca.setBounds((int) (screenSize.getWidth() / 2) - 230, (int) (screenSize.getHeight() / 2) - 300, 450, 25);
		txfBusca.setToolTipText("Digite a informação a ser pesquisada");	
		txfBusca.addKeyListener(acao);
		getContentPane().add(txfBusca);
		txfBusca.requestFocus();
		
		btnBuscar = new JButton(new AbstractAction("Buscar") {
			private static final long serialVersionUID = -998996978922572909L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: realizar a busca e inserir os resultados na table.
				
				if(radiosBusca.getSelection() == r_titulo.getModel()) {
					
					//Buscar por título.
					
				} else if(radiosBusca.getSelection() == r_autor.getModel()) {
					
					//Buscar por autor.
					
				} else {
					
					//Buscar por gênero.
					
				}
				
			}
		});
		btnBuscar.setBounds((int) (screenSize.getWidth() / 2) + 230, (int) (screenSize.getHeight() / 2) - 300, 90, 25);
		btnBuscar.addKeyListener(acao);
		getContentPane().add(btnBuscar);
		
		labelDescricao = new JLabel("Título");
		labelDescricao.setBounds((int) (screenSize.getWidth() / 2) - 210, (int) (screenSize.getHeight() / 2) - 270, 100, 25);
		getContentPane().add(labelDescricao);
		
		r_titulo = new JRadioButton("Título");
		r_titulo.setOpaque(false);
		r_titulo.setBounds((int) (screenSize.getWidth() / 2) - 230, (int) (screenSize.getHeight() / 2) - 270, 20, 25);
		radiosBusca.add(r_titulo);
		r_titulo.setSelected(true);
		getContentPane().add(r_titulo);
		
		labelDescricao = new JLabel("Autor");
		labelDescricao.setBounds((int) (screenSize.getWidth() / 2) - 150, (int) (screenSize.getHeight() / 2) - 270, 100, 25);
		getContentPane().add(labelDescricao);
		
		r_autor = new JRadioButton("Autor");
		r_autor.setOpaque(false);
		r_autor.setBounds((int) (screenSize.getWidth() / 2) - 170, (int) (screenSize.getHeight() / 2) - 270, 20, 25);
		radiosBusca.add(r_autor);
		getContentPane().add(r_autor);
		
		labelDescricao = new JLabel("Gênero");
		labelDescricao.setBounds((int) (screenSize.getWidth() / 2) - 90, (int) (screenSize.getHeight() / 2) - 270, 100, 25);
		getContentPane().add(labelDescricao);
		
		r_genero = new JRadioButton("Gênero");
		r_genero.setOpaque(false);
		r_genero.setBounds((int) (screenSize.getWidth() / 2) - 110, (int) (screenSize.getHeight() / 2) - 270, 20, 25);
		radiosBusca.add(r_genero);
		getContentPane().add(r_genero);
		
		tableLivros = new JTable(tableModel);
		scrollpaneTable = new JScrollPane(tableLivros);
		scrollpaneTable.setBounds((int) (screenSize.getWidth() / 2) - 450, (int) (screenSize.getHeight() / 2) - 230 , 1000, 500);
		scrollpaneTable.setVisible(true);
		getContentPane().add(scrollpaneTable);
		
	}
	
	/*
	 * MENU DE NAVEGAÇÃO
	 */
	private JMenuBar getWindowMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(getMenuHome());
		menuBar.add(getMenuBiblioteca());
		return menuBar;
	}
	
	// Menu Biblioteca
	private JMenu getMenuBiblioteca() {
		menuBiblioteca = new JMenu();
		menuBiblioteca.setText("Biblioteca");
		menuBiblioteca.setFont(getDefaultFont());
		menuBiblioteca.add(getMenuItemEditarMapa());

		return menuBiblioteca;
	}
	
	// Menu Home
	private JMenu getMenuHome() {
		menuHome = new JMenu();
		menuHome.setText("Home");
		menuHome.setFont(getDefaultFont());
		menuHome.add(getMenuItemBuscarLivro());	

		return menuHome;
	}
	
	private JMenuItem getMenuItemBuscarLivro() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Buscar Livros");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: fechar todos os internal frames abertos.;
			}
		});

		return menuItem;
	}
	
	private JMenuItem getMenuItemEditarMapa() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Editar Mapa");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Abrir janela para edição do mapa da biblioteca.
			}
		});

		return menuItem;
	}
	
	private void abrirFrame(AbstractWindowFrame frame) {		
		boolean frameJaExiste = false;

		// Percorre todos os frames adicionados
		for (JInternalFrame addedFrame : desktop.getAllFrames()) {
			// Se o frame a ser adicionado já estiver aberto
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
			JOptionPane.showMessageDialog(rootPane, "Houve um erro ao abrir a janela", "", JOptionPane.ERROR_MESSAGE, null);
		}
	}
	
	private Font getDefaultFont() {
		return new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12);
	}
		
	public static void main(final String[] args) {
		//Abrir janela principal no centro da tela.
		new MainWindow().setLocationRelativeTo(null);
	}
	
	@SuppressWarnings("serial")
	private class NewContentPane extends JPanel {
		protected void paintComponent(final Graphics g) {
			super.paintComponent(g);
			g.drawImage(ImageController.findMyBook.getImage(), 0, 0, this);
		}

	}

}
