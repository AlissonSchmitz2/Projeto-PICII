package br.com.projetopicii.view;

import java.awt.*;
import java.util.HashMap;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.*;

import br.com.projetopicii.model.EstanteBiblioteca;
import br.com.projetopicii.model.TerminalPesquisa;
import br.com.projetopicii.model.dao.EstanteDao;
import br.com.projetopicii.model.dao.TerminalDao;
import br.com.projetopicii.pictures.ImageController;
import br.com.projetopicii.threads.ThreadSubirListaEstante;

public class CriarBibliotecaWindow extends JFrame {
	private static final long serialVersionUID = -6766594202823918036L;

	// Bal�es do tutorial.
	private JLabel labelBalao1;
	private JLabel labelBalao2;
	private JLabel labelBalao3;

	// Auxiliares do tutorial.
	private boolean informacao1 = true;
	private boolean informacao2 = true;

	// Bot�es.
	private JButton btnFimTutorial;
	private JButton btnSalvar;

	// Classe que faz conex�o com banco.
	private static EstanteDao estanteDao = new EstanteDao();
	private static TerminalDao terminalDao = new TerminalDao();

	// Painel para as estantes.
	private JPanel painelEstantes = new JPanel();

	// HashMap: Key -> Refer�ncia da estante / Value -> Objeto estante.
	private HashMap<String, EstanteBiblioteca> listaDeEstantes = new HashMap<>();

	// Estantes.
	private EstanteBiblioteca estanteBiblioteca;

	// Terminal de pesquisa.
	private TerminalPesquisa terminalPesquisa = new TerminalPesquisa();

	// Dados para cria��o das estantes.
	private String[] referencias;

	// Thread para subir as estantes ao retirar alguma do painel.
	ThreadSubirListaEstante threadSubirListaEstante;

	// Tamanho da tela.
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public CriarBibliotecaWindow(String[] referencias) {

		this.referencias = referencias;
		setTitle("Constru��o da Biblioteca");
		super.setContentPane(new NewContentPane());
		setLayout(null);
		setBounds(new Rectangle(0, 0, screenSize.width, screenSize.height));
		setExtendedState(MAXIMIZED_BOTH);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		criarComponentes();

		setVisible(true);
	}

	private void criarComponentes() {

		// Painel para as estantes.
		painelEstantes.setBackground(Color.WHITE);
		painelEstantes.setLayout(null);
		painelEstantes.setBorder(BorderFactory.createTitledBorder("Estantes"));
		painelEstantes.setBackground((new Color(245, 245, 245)));

		int y = 25;

		// Adiciona as estantes ao painel de acordo com a quantidade de refer�ncias.
		for (int i = 0; i < referencias.length; i++) {
			estanteBiblioteca = new EstanteBiblioteca();
			estanteBiblioteca.setBackground(Color.WHITE);
			estanteBiblioteca.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, false));
			estanteBiblioteca.setBounds(15, y, 110, 40);

			estanteBiblioteca.setThisPanel(estanteBiblioteca);
			estanteBiblioteca.setCriarBibliotecaWindow(this);
			estanteBiblioteca.ativarCliqueMouse();
			estanteBiblioteca.setReferencia(referencias[i]);
			estanteBiblioteca.setCoordenadaY(y);

			listaDeEstantes.put(referencias[i], estanteBiblioteca);

			y += 60;
			painelEstantes.add(estanteBiblioteca);
		}
		// Ajusta o tamanho do painel de acordo com a quantidade de estantes.
		painelEstantes.setPreferredSize(new Dimension(160, y));

		// Terminal de Pesquisa.
		terminalPesquisa.setarTerminal(this);

		// Thread.
		threadSubirListaEstante = new ThreadSubirListaEstante(
				(HashMap<String, EstanteBiblioteca>) listaDeEstantes.clone(), referencias);

		// Scroll para o painel de estantes.
		JScrollPane scrollPane = new JScrollPane(painelEstantes);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 0, 160, screenSize.height - 60);
		getContentPane().add(scrollPane);

		btnSalvar = new JButton(new AbstractAction("Salvar Biblioteca") {
			private static final long serialVersionUID = 6168373527954185392L;

			@Override
			public void actionPerformed(ActionEvent e) {

				if (painelEstantes.getComponentCount() != 0) {

					JOptionPane.showMessageDialog(rootPane, "Todas as estantes devem ser posicionadas antes de salvar.",
							"", JOptionPane.ERROR_MESSAGE, null);
				} else {

					for (int i = 0; i < listaDeEstantes.size(); i++) {
						int coordenadaX = listaDeEstantes.get(referencias[i]).getPosicaoEstante().getX();
						int coordenadaY = listaDeEstantes.get(referencias[i]).getPosicaoEstante().getY();

						try {
							estanteDao.atualizarCoordenadas(referencias[i], coordenadaX, coordenadaY);
						} catch(Exception e1) {
							JOptionPane.showMessageDialog(rootPane, "Ocorreu um erro ao tentar salvar as estantes",
									"", JOptionPane.ERROR_MESSAGE, null);
						}
					}

					try {
						terminalDao.atualizarCoordenadas(terminalPesquisa.getPosicaoTerminal().getX(),
								terminalPesquisa.getPosicaoTerminal().getY());
	
						JOptionPane.showMessageDialog(null, "Biblioteca salva com sucesso!");
					} catch(Exception e1) {
						JOptionPane.showMessageDialog(rootPane, "Ocorreu um erro ao tentar salvar o terminal de pesquisa",
								"", JOptionPane.ERROR_MESSAGE, null);
					}
				}
			}
		});
		btnSalvar.setBounds((int) screenSize.getWidth() - 150, 160, 130, 25);
		getContentPane().add(btnSalvar);

		// COMPONENTES DO TUTORIAL.

		labelBalao1 = new JLabel();
		labelBalao1.setBounds(170, -10, 225, 245);
		labelBalao1.setIcon(ImageController.informacao1);
		getContentPane().add(labelBalao1);

		labelBalao2 = new JLabel();
		labelBalao2.setBounds(370, -10, 225, 245);
		labelBalao2.setIcon(ImageController.informacao2);

		labelBalao3 = new JLabel();
		labelBalao3.setBounds(570, -10, 245, 225);
		labelBalao3.setIcon(ImageController.informacao3);

		btnFimTutorial = new JButton(new AbstractAction("Finalizar Tutorial") {
			private static final long serialVersionUID = -6855202875453107191L;

			@Override
			public void actionPerformed(ActionEvent e) {

				removerInformacao3();
				getContentPane().remove(btnFimTutorial);
				repaint();
			}
		});
		btnFimTutorial.setBounds(680, 155, 130, 25);

	}

	@SuppressWarnings("serial")
	private class NewContentPane extends JPanel {
		protected void paintComponent(final Graphics g) {
			super.paintComponent(g);
			g.drawImage(ImageController.FundoBiblioteca.getImage(), 0, 0, this);
		}

	}

	// M�TODOS PARA OS BAL�ES DO TUTORIAL.

	public boolean isInformacao1() {
		return informacao1;
	}

	public void setInformacao1(boolean informacao1) {
		this.informacao1 = informacao1;
	}

	public void removerInformacao1() {
		getContentPane().remove(labelBalao1);
	}

	public boolean isInformacao2() {
		return informacao2;
	}

	public void setInformacao2(boolean informacao2) {
		this.informacao2 = informacao2;
	}

	public void inserirInformacao2() {
		getContentPane().add(labelBalao2);
	}

	public void removerInformacao2() {
		getContentPane().remove(labelBalao2);
	}

	public void inserirInformacao3() {
		getContentPane().add(labelBalao3);
		getContentPane().add(btnFimTutorial);
		this.repaint();
	}

	public void removerInformacao3() {
		getContentPane().remove(labelBalao3);
	}

	// Main para teste.
	public static void main(String[] args) {

		String[] referencias;

		referencias = estanteDao.pegarNomeEstantes();

		for (String r : referencias) {
			System.out.println(r);
		}

		new CriarBibliotecaWindow(referencias);
	}
}