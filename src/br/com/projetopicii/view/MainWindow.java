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

import br.com.projetopicii.model.dao.EstanteDao;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 2630247367422389726L;
	
	//Componentes Janela Principal.
	private JMenu menuAdministrador;
	private JMenu menuHome;
	private JDesktopPane desktop;	
	private EstanteDao estanteDao = new EstanteDao();
	private LoginWindow lW = null;
	
	private boolean primeiraExecucao;
	
	//Frames
	BuscarLivrosWindow frameBuscarLivrosWindow;
		
	public MainWindow() {
		super();
		
		new Splash();
		lW = new LoginWindow(this);
		
		desktop = new JDesktopPane();
		desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
		desktop.setVisible(true);
		setContentPane(desktop);
		
		inicializar();		
		
		//Full screen
		setExtendedState(Frame.MAXIMIZED_BOTH);
	}
	
	private void inicializar() {
		
		verificarPrimeiraExecucao();		
		this.setTitle("Find My Book");
		this.setJMenuBar(getWindowMenuBar());
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setBounds(new Rectangle(0, 0, 1200, 700));	
		this.setVisible(true);
		this.setFocusableWindowState(true);	
		
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
				
				frameBuscarLivrosWindow = new BuscarLivrosWindow(desktop);
				abrirFrame(frameBuscarLivrosWindow);
			}
		});
		
		if(primeiraExecucao) {
			menuItem.setEnabled(false);
		}

		return menuItem;
	}
	
	private JMenuItem getMenuItemRealizarLogin() {
		JMenuItem menuItem = new JMenuItem();
		menuItem.setText("Realizar Login");
		menuItem.setFont(getDefaultFont());

		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lW.setVisible(true);
			}
		});

		if(primeiraExecucao) {
			menuItem.setEnabled(false);
		}
		
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
	
	private void verificarPrimeiraExecucao() {

		if (estanteDao.verificarPrimeiraExecucao()) {
			
			primeiraExecucao = true;
			JOptionPane.showMessageDialog(null,
					"Bem-vindo ao software Find My Book."
							+ "\nDetectamos que você ainda não possui um mapa para a sua biblioteca. "
							+ "\nPor favor, construa-o na tela a seguir.");

			String[] referencias;
			referencias = estanteDao.pegarNomeEstantes(true);
			abrirFrame(new CriarBibliotecaWindow(referencias, this));
		} else {
			frameBuscarLivrosWindow = new BuscarLivrosWindow(desktop);
			abrirFrame(frameBuscarLivrosWindow);
		}
		
	}
		
	public static void main(final String[] args) {
		//Abrir janela principal.
		new MainWindow();
	}

}
