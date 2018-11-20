package br.com.projetopicii.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.projetopicii.model.EstanteBiblioteca;
import br.com.projetopicii.model.TerminalPesquisa;
import br.com.projetopicii.model.bean.Estante;
import br.com.projetopicii.model.bean.Terminal;
import br.com.projetopicii.model.dao.EstanteDao;
import br.com.projetopicii.model.dao.LivroDao;
import br.com.projetopicii.model.dao.TerminalDao;
import br.com.projetopicii.pictures.ImageController;

public class ResultadoCaminhoWindow extends AbstractWindowFrame {
	private static final long serialVersionUID = 8264825839066397462L;

	// Ids das estantes a serem pintadas.
	private ArrayList<Integer> idsPintar = new ArrayList<>();

	// Estantes.
	private EstanteBiblioteca estanteBiblioteca;
	private EstanteDao estanteDao = new EstanteDao();
	private LivroDao livroDao = new LivroDao();
	private ArrayList<Estante> arrayEstantes = new ArrayList<>();
	private int idEstanteAlvo = -1;

	// Terminal de Pesquisa
	private Terminal terminal = new Terminal();
	private TerminalDao terminalDao = new TerminalDao();
	private TerminalPesquisa terminalPesquisa;
	
	// HashMap: Key -> Id estante / Value -> Objeto estante.
	private HashMap<Integer, EstanteBiblioteca> hashDeEstantes = new HashMap<>();

	// Tamanho da tela.
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	// Auxiliar
	private JPanel jPanelAux;
	private JLabel jLabelAux;

	// Fundo Biblioteca.
	private Image fundoBiblioteca;

	public ResultadoCaminhoWindow(String tituloSelecionado) {

		// TODO: Pensar em um nome melhor para a tela.	
		super("Menor e Melhor Caminho");
		
		arrayEstantes = estanteDao.pegarArrayEstantes(false);
		terminal = terminalDao.pegarTerminal();
		
		// Id da estante que contém o livro desejado.
		idEstanteAlvo = livroDao.pegarIdEstante(tituloSelecionado);

		// TODO: Utilizar o dijkstra para recuperar os ids das estantes que devem ser pintadas.
		// idsPintar = Dijkstra;

		this.fundoBiblioteca = ImageController.FundoBiblioteca.getImage();

		super.setContentPane(new NewContentPane());
		setLayout(null);
		setBounds(new Rectangle(0, 0, screenSize.width, screenSize.height));

		criarComponentes();
	}

	private void criarComponentes() {

		// Posiciona as estantes na tela.
		for (int i = 0; i < arrayEstantes.size(); i++) {

			estanteBiblioteca = new EstanteBiblioteca();
			estanteBiblioteca.setBackground(Color.WHITE);
			estanteBiblioteca.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, false));
			estanteBiblioteca.setBounds(arrayEstantes.get(i).getCoordenadaX(), arrayEstantes.get(i).getCoordenadaY(),
					110, 40);
			estanteBiblioteca.setThisPanel(estanteBiblioteca);
			estanteBiblioteca.setReferencia(arrayEstantes.get(i).getNome());

			getContentPane().add(estanteBiblioteca);
			hashDeEstantes.put(arrayEstantes.get(i).getId(), estanteBiblioteca);
		}
		
		terminalPesquisa = new TerminalPesquisa();
		terminalPesquisa.setarTerminal(this, terminal.getCoordenadaX(), terminal.getCoordenadaY());
		
		jLabelAux = new JLabel("Estantes Sugeridas");
		jLabelAux.setBounds(120, 62, 125, 25);
		getContentPane().add(jLabelAux);
		
		jPanelAux = new JPanel();
		jPanelAux.setBounds(80, 65, 30, 20);
		jPanelAux.setBackground(Color.RED);
		getContentPane().add(jPanelAux);
		
		jLabelAux = new JLabel("Estante Desejada");
		jLabelAux.setBounds(120, 102, 125, 25);
		getContentPane().add(jLabelAux);
		
		jPanelAux = new JPanel();
		jPanelAux.setBounds(80, 105, 30, 20);
		jPanelAux.setBackground(Color.BLUE);
		getContentPane().add(jPanelAux);
		
		pintarEstantes();

	}

	@SuppressWarnings("serial")
	private class NewContentPane extends JPanel {

		protected void paintComponent(final Graphics g) {

			super.paintComponent(g);
			g.drawImage(fundoBiblioteca, 0, 0, this);
		}
	}

	// Pinta as estantes que o usuário deve seguir de acordo com os ids retornados
	// pelo dijkstra.
	// Azul = estante onde o livro pesquisado está.
	// Vermelho = estantes a serem seguidas até a estante azul.
	private void pintarEstantes() {		
		
		hashDeEstantes.get(idEstanteAlvo).setBackground(Color.BLUE);

		for (int i = 0; i < idsPintar.size(); i++) {

			hashDeEstantes.get(idsPintar.get(i)).setBackground(Color.RED);
		}
	}

}
