package br.com.projetopicii.view;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;


public class AdministradorMainWindow extends JFrame {
	private static final long serialVersionUID = 4203314756947777665L;

	// Componentes Janela Principal.
	private JMenu menuHome;
	private JMenu menuEstantes;
	private JMenu menuLivros;
	private JMenu menuBiblioteca;
	private JMenu menuAdministrador;
	private JDesktopPane desktop;

	// Frames
	private BuscarLivrosWindow frameBuscarLivrosWindow;
	private CadastrarEstanteWindow frameCadastrarEstanteWindow;
	private CadastrarLivrosWindow frameCadastrarLivrosWindow;
	private ListarEstantesWindow frameListarEstantes;
	private ListarUsuariosWindow frameListarUsuarios;
	
	public AdministradorMainWindow() {
		super();

		desktop = new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		desktop.setVisible(true);
		setContentPane(desktop);

		inicializar();

		// Full screen
		setExtendedState(Frame.MAXIMIZED_BOTH);
	}

	private void inicializar() {

		this.setVisible(true);
		this.setTitle("Find My Book (Administrador)");
		this.setJMenuBar(getWindowMenuBar());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(new Rectangle(0, 0, 1200, 700));
		this.setFocusableWindowState(true);

		frameBuscarLivrosWindow = new BuscarLivrosWindow(desktop);
		abrirFrame(frameBuscarLivrosWindow);
	}

	/*
	 * MENU DE NAVEGAÇÃO
	 */
	private JMenuBar getWindowMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(getMenuHome());
		menuBar.add(getMenuBiblioteca());
		menuBar.add(getMenuEstantes());
		menuBar.add(getMenuLivros());
		menuBar.add(getMenuAdministrador());

		return menuBar;
	}

	// Menu Home
	private JMenu getMenuHome() {
		menuHome = new JMenu();
		menuHome.setText("Home");
		menuHome.setFont(getDefaultFont());
		menuHome.add(getMenuItemBuscarLivros());

		return menuHome;
	}

	// Menu Estantes
	private JMenu getMenuEstantes() {
		menuEstantes = new JMenu();
		menuEstantes.setText("Estantes");
		menuEstantes.setFont(getDefaultFont());
		menuEstantes.add(getMenuItemCadastrarEstantes());
		menuEstantes.add(getMenuItemListarEstantes());

		return menuEstantes;
	}

	// Menu Biblioteca
	private JMenu getMenuBiblioteca() {
		menuBiblioteca = new JMenu();
		menuBiblioteca.setText("Biblioteca");
		menuBiblioteca.setFont(getDefaultFont());
		menuBiblioteca.add(getMenuItemEditarBiblioteca());

		return menuBiblioteca;
	}

	// Menu Livros
	private JMenu getMenuLivros() {
		menuLivros = new JMenu();
		menuLivros.setText("Livros");
		menuLivros.setFont(getDefaultFont());
		menuLivros.add(getMenuItemCadastrarLivros());
		menuLivros.add(getMenuItemListarLivros());

		return menuLivros;
	}

	// Menu Administrador
	private JMenu getMenuAdministrador() {
		menuAdministrador = new JMenu();
		menuAdministrador.setText("Administrador");
		menuAdministrador.setFont(getDefaultFont());
		menuAdministrador.add(getMenuItemCadastrarAdm());
		menuAdministrador.add(getMenuItemListarAdm());
		menuAdministrador.add(getMenuItemDeslogar());

		return menuAdministrador;
	}

	private JMenuItem getMenuItemDeslogar() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Deslogar");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				new MainWindow();
				dispose();
			}
		});

		return menuItem;
	}
	
	private JMenuItem getMenuItemCadastrarAdm() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//TODO: Abrir frame para cadastro de administradores.
			}
		});

		return menuItem;
	}
	
	private JMenuItem getMenuItemListarAdm() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Listar");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameListarUsuarios = new ListarUsuariosWindow(desktop);
				abrirFrame(frameListarUsuarios);
				
				frameListarUsuarios.redimensionarGrid(frameListarUsuarios.getGridContent());
			}
		});

		return menuItem;
	}

	private JMenuItem getMenuItemBuscarLivros() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Consultar Acervo");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frameBuscarLivrosWindow = new BuscarLivrosWindow(desktop);
				abrirFrame(frameBuscarLivrosWindow);
			}
		});

		return menuItem;
	}

	private JMenuItem getMenuItemCadastrarEstantes() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frameCadastrarEstanteWindow = new CadastrarEstanteWindow();
				abrirFrame(frameCadastrarEstanteWindow);
				
			}
		});

		return menuItem;
	}

	private JMenuItem getMenuItemListarEstantes() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Listar");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameListarEstantes = new ListarEstantesWindow(desktop);
				abrirFrame(frameListarEstantes);
				
				frameListarEstantes.redimensionarGrid(frameListarEstantes.getGridContent());
			}
		});

		return menuItem;
	}

	private JMenuItem getMenuItemCadastrarLivros() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Cadastrar");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameCadastrarLivrosWindow = new CadastrarLivrosWindow();
				abrirFrame(frameCadastrarLivrosWindow);
			}
		});

		return menuItem;
	}

	private JMenuItem getMenuItemListarLivros() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Listar");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: Abrir janela para listagem de livros.

			}
		});

		return menuItem;
	}

	private JMenuItem getMenuItemEditarBiblioteca() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Editar");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO: Abrir janela para edição da biblioteca.

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
			JOptionPane.showMessageDialog(rootPane, "Houve um erro ao abrir a janela", "", JOptionPane.ERROR_MESSAGE,
					null);
		}
	}

	private Font getDefaultFont() {
		return new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12);
	}

}
