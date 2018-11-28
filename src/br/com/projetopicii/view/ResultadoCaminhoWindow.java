package br.com.projetopicii.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
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
	private JPanel jPanelDesejada;
	private JPanel jPanelSugeridas;
	private JPanel jPanelBalao;
	private JLabel jLabelAux;

	public ResultadoCaminhoWindow(String tituloSelecionado,ArrayList<Integer> idsPintar) {

		super("Resultado da Pesquisa");
		
		arrayEstantes = estanteDao.pegarArrayEstantes(false);
		terminal = terminalDao.pegarTerminal();
		
		// Id da estante que contém o livro desejado.
		idEstanteAlvo = livroDao.pegarIdEstante(tituloSelecionado);

		// TODO: Utilizar o dijkstra para recuperar os ids das estantes que devem ser pintadas.
		this.idsPintar = idsPintar;

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
			estanteBiblioteca.setThisPanel(estanteBiblioteca);
			estanteBiblioteca.setReferencia(arrayEstantes.get(i).getNome());
			
			if(arrayEstantes.get(i).isVertical()) {
				estanteBiblioteca.setBounds(arrayEstantes.get(i).getCoordenadaX(), arrayEstantes.get(i).getCoordenadaY(),
						40, 110);
				estanteBiblioteca.setVertical(true);
			} else {
				estanteBiblioteca.setBounds(arrayEstantes.get(i).getCoordenadaX(), arrayEstantes.get(i).getCoordenadaY(),
						110, 40);				
			}

			getContentPane().add(estanteBiblioteca);
			hashDeEstantes.put(arrayEstantes.get(i).getId(), estanteBiblioteca);
		}
		
		terminalPesquisa = new TerminalPesquisa();
		terminalPesquisa.setarTerminal(this, terminal.getCoordenadaX(), terminal.getCoordenadaY());
		
		jPanelBalao = new NewContentPane2();
		jPanelBalao.setBounds(120, 10, 330, 215);
		jPanelBalao.setOpaque(false);
		jPanelBalao.setLayout(null);
		getContentPane().add(jPanelBalao);
		
		jLabelAux = new JLabel("Estantes Sugeridas");
		jLabelAux.setBounds(115, 42, 125, 35);
		
		jPanelSugeridas = new JPanel();
		jPanelSugeridas.setLayout(null);
		jPanelSugeridas.setBounds(65, 50, 40, 20);
		jPanelSugeridas.setBackground(new Color(255, 85, 85));
		
		jPanelBalao.add(jPanelSugeridas);
		jPanelBalao.add(jLabelAux);
		
		jLabelAux = new JLabel("Estante Desejada");
		jLabelAux.setBounds(115, 82, 125, 35);
		
		jPanelDesejada = new JPanel();
		jPanelDesejada.setLayout(null);
		jPanelDesejada.setBounds(65, 90, 40, 20);
		jPanelDesejada.setBackground(new Color(39, 248, 75));
		
		jPanelBalao.add(jPanelDesejada);
		jPanelBalao.add(jLabelAux);
		
		pintarEstantes();

	}

	@SuppressWarnings("serial")
	private class NewContentPane extends JPanel {

		protected void paintComponent(final Graphics g) {

			super.paintComponent(g);
			g.drawImage(ImageController.FundoBiblioteca.getImage(), 0, 0, this);
		}
	}
	
	@SuppressWarnings("serial")
	private class NewContentPane2 extends JPanel {

		protected void paintComponent(final Graphics g) {

			super.paintComponent(g);
			g.drawImage(ImageController.balaoResultado.getImage(), 0, 0, this);
		}
	}

	// Pinta as estantes que o usuário deve seguir de acordo com os ids retornados
	// pelo dijkstra.
	// Verde = estante onde o livro pesquisado está.
	// Vermelho = estantes sugeridas.
	private void pintarEstantes() {		
		for (int i = 0; i < idsPintar.size(); i++) {

			hashDeEstantes.get(idsPintar.get(i)).setBackground(new Color(255, 85, 85));
		}
		
		hashDeEstantes.get(idEstanteAlvo).setBackground(new Color(39, 248, 75));

		
	}

}
