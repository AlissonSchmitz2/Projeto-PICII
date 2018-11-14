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

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 2630247367422389726L;
	
	//Componentes Janela Principal.
	private JMenu menuAdministrador;
	private JMenu menuHome;
	private JDesktopPane desktop;	
	
	//Frames
	BuscarLivrosWindow frameBuscarLivrosWindow;
		
	public MainWindow() {
		super();
			
		desktop = new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		desktop.setVisible(true);
		setContentPane(desktop);
		
		inicializar();		
		
		//Full screen
		setExtendedState(Frame.MAXIMIZED_BOTH);
	}
	
	private void inicializar() {
		
		this.setVisible(true);
		this.setTitle("Find My Book");
		this.setJMenuBar(getWindowMenuBar());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(new Rectangle(0, 0, 1200, 700));		
		this.setFocusableWindowState(true);	
		
		frameBuscarLivrosWindow = new BuscarLivrosWindow();
		abrirFrame(frameBuscarLivrosWindow);		
	}
	
	
	
	/*
	 * MENU DE NAVEGAÇÃO
	 */
	private JMenuBar getWindowMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(getMenuHome());
		menuBar.add(getMenuAdministrador());
		return menuBar;
	}
	
	// Menu Administrador
	private JMenu getMenuAdministrador() {
		menuAdministrador = new JMenu();
		menuAdministrador.setText("Administrador");
		menuAdministrador.setFont(getDefaultFont());
		menuAdministrador.add(getMenuItemRealizarLogin());

		return menuAdministrador;
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
		menuItem.setText("Consultar Acervo");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frameBuscarLivrosWindow = new BuscarLivrosWindow();
				abrirFrame(frameBuscarLivrosWindow);
			}
		});

		return menuItem;
	}
	
	private JMenuItem getMenuItemRealizarLogin() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Realizar Login");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: Abrir janela para login do usuário administrador.
				
				//Abrir janela do administrador caso o login esteja correto.
				new AdministradorMainWindow();
				dispose();
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
		//Abrir janela principal.
		new MainWindow();
	}

}
