package br.com.projetopicii.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.com.projetopicii.model.EstanteBibliotecaEdicao;
import br.com.projetopicii.model.TerminalPesquisa;
import br.com.projetopicii.model.bean.Estante;
import br.com.projetopicii.model.bean.Terminal;
import br.com.projetopicii.model.dao.EstanteDao;
import br.com.projetopicii.model.dao.TerminalDao;
import br.com.projetopicii.pictures.ImageController;
import br.com.projetopicii.threads.ThreadSubirEstantesEdicao;

public class EditarBibliotecaWindow extends AbstractWindowFrame {
	private static final long serialVersionUID = 882257866262142034L;

	// Botões.
	private JButton btnSalvar;
	private JButton btnOcultarPainel;
	
	// Auxilar
	private boolean painelVisivel;

	// Classe que faz conexão com banco.
	private static EstanteDao estanteDao;
	private static TerminalDao terminalDao;

	// Painel para as estantes.
	private JPanel painelEstantes = new JPanel();
	private JScrollPane scrollPane;

	// HashMap: Key -> Referência da estante / Value -> Objeto estante.
	private HashMap<String, EstanteBibliotecaEdicao> listaDeEstantes;
	
	// Estantes.
	private EstanteBibliotecaEdicao estanteBiblioteca;
	private ArrayList<Estante> arrayEstantesAntigas;

	// Terminal de pesquisa.
	private TerminalPesquisa terminalPesquisa;
	private Terminal terminal;

	// Dados para criação das estantes.
	private String[] referenciasAntigas;
	private String[] referenciasNovas;
	private ArrayList<String> arrayAuxReferencias;

	// Thread para subir as estantes ao retirar alguma do painel.
	ThreadSubirEstantesEdicao threadSubirListaEstante;

	// Tamanho da tela.
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public EditarBibliotecaWindow() {

		super("Edição da Biblioteca");
		setContentPane(new NewContentPane());
		setLayout(null);

		listaDeEstantes = new HashMap<>();
		arrayEstantesAntigas = new ArrayList<>();
		arrayAuxReferencias = new ArrayList<>();
		estanteDao = new EstanteDao();
		terminalDao = new TerminalDao();
		terminal = new Terminal();
		terminalPesquisa = new TerminalPesquisa();

		terminal = terminalDao.pegarTerminal();
		arrayEstantesAntigas = estanteDao.pegarArrayEstantes(false);
		referenciasAntigas = estanteDao.pegarNomeEstantes(false);
		referenciasNovas = estanteDao.pegarNomeEstantes(true);

		criarComponentes();
	}

	private void criarComponentes() {

		if (referenciasNovas.length > 0) {

			// Painel para as novas estantes.
			painelEstantes.setBackground(Color.WHITE);
			painelEstantes.setLayout(null);
			painelEstantes.setBorder(BorderFactory.createTitledBorder("Novas Estantes"));
			painelEstantes.setBackground((new Color(245, 245, 245)));

			int y = 25;

			// Adiciona as novas estantes ao painel de acordo com a quantidade de
			// referências.
			for (int i = 0; i < referenciasNovas.length; i++) {
				estanteBiblioteca = new EstanteBibliotecaEdicao();
				estanteBiblioteca.setBackground(Color.WHITE);
				estanteBiblioteca.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, false));
				estanteBiblioteca.setBounds(15, y, 110, 40);

				estanteBiblioteca.setThisPanel(estanteBiblioteca);
				estanteBiblioteca.setEditarBibliotecaWindow(this);
				estanteBiblioteca.ativarCliqueMouse();
				estanteBiblioteca.setReferencia(referenciasNovas[i]);
				estanteBiblioteca.setCoordenadaY(y);

				listaDeEstantes.put(referenciasNovas[i], estanteBiblioteca);
				arrayAuxReferencias.add(referenciasNovas[i]);

				y += 60;
				painelEstantes.add(estanteBiblioteca);
			}
			// Ajusta o tamanho do painel de acordo com a quantidade de estantes.
			painelEstantes.setPreferredSize(new Dimension(160, y));

			// Thread.
			threadSubirListaEstante = new ThreadSubirEstantesEdicao(
					(HashMap<String, EstanteBibliotecaEdicao>) listaDeEstantes.clone(), referenciasNovas);

			// Scroll para o painel de estantes.
			scrollPane = new JScrollPane(painelEstantes);
			scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setBounds(0, 0, 160, screenSize.height - 105);
			getContentPane().add(scrollPane);
			
			painelVisivel = true;
		}

		// Posiciona as estantes antigas na tela.
		for (int i = 0; i < arrayEstantesAntigas.size(); i++) {

			estanteBiblioteca = new EstanteBibliotecaEdicao();
			estanteBiblioteca.setBackground(Color.WHITE);
			estanteBiblioteca.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, false));
			estanteBiblioteca.setBounds(arrayEstantesAntigas.get(i).getCoordenadaX(),
					arrayEstantesAntigas.get(i).getCoordenadaY(), 110, 40);
			estanteBiblioteca.getPosicaoEstante().setX(arrayEstantesAntigas.get(i).getCoordenadaX());
			estanteBiblioteca.getPosicaoEstante().setY(arrayEstantesAntigas.get(i).getCoordenadaY());
			estanteBiblioteca.setThisPanel(estanteBiblioteca);
			estanteBiblioteca.setReferencia(arrayEstantesAntigas.get(i).getNome());
			estanteBiblioteca.ativarCliqueMouse();
			estanteBiblioteca.ativarMovimentoMouse();
			estanteBiblioteca.setEditarBibliotecaWindow(this);

			getContentPane().add(estanteBiblioteca);

			listaDeEstantes.put(referenciasAntigas[i], estanteBiblioteca);
			arrayAuxReferencias.add(referenciasAntigas[i]);
		}

		// Terminal de Pesquisa.
		terminalPesquisa.setarTerminal(this, terminal.getCoordenadaX(), terminal.getCoordenadaY());
		terminalPesquisa.getPosicaoTerminal().setX(terminal.getCoordenadaX());
		terminalPesquisa.getPosicaoTerminal().setY(terminal.getCoordenadaY());

		btnSalvar = new JButton(new AbstractAction("Salvar Biblioteca") {
			private static final long serialVersionUID = 6168373527954185392L;

			@Override
			public void actionPerformed(ActionEvent e) {

				for (int i = 0; i < listaDeEstantes.size(); i++) {
					int coordenadaX = listaDeEstantes.get(arrayAuxReferencias.get(i)).getPosicaoEstante().getX();
					int coordenadaY = listaDeEstantes.get(arrayAuxReferencias.get(i)).getPosicaoEstante().getY();

					try {
						estanteDao.atualizarCoordenadas(arrayAuxReferencias.get(i), coordenadaX, coordenadaY);
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(rootPane, "Ocorreu um erro ao tentar salvar as estantes", "",
								JOptionPane.ERROR_MESSAGE, null);
						e1.printStackTrace();
					}
				}

				try {
					terminalDao.atualizarCoordenadas(terminalPesquisa.getPosicaoTerminal().getX(),
							terminalPesquisa.getPosicaoTerminal().getY());

					JOptionPane.showMessageDialog(null, "Biblioteca salva com sucesso!");

					dispose();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(rootPane, "Ocorreu um erro ao tentar salvar o terminal de pesquisa",
							"", JOptionPane.ERROR_MESSAGE, null);
					e1.printStackTrace();
				}
			}
		});
		btnSalvar.setBounds((int) screenSize.getWidth() - 150, 160, 130, 25);
		btnSalvar.setToolTipText("Salvar alterações");
		getContentPane().add(btnSalvar);
		
		if(painelVisivel) {			
			btnOcultarPainel = new JButton(new AbstractAction("Ocultar Painel") {
				private static final long serialVersionUID = -8738313098464987940L;

				@Override
				public void actionPerformed(ActionEvent e) {
					
					if(painelVisivel) {
						
						for(int i = 0; i < listaDeEstantes.size(); i++) {
							
							if(listaDeEstantes.get(arrayAuxReferencias.get(i)).getPosicaoEstante().getX() < 162 
									&& listaDeEstantes.get(arrayAuxReferencias.get(i)).getPosicaoEstante().getX() != 0) {
								listaDeEstantes.get(arrayAuxReferencias.get(i)).setVisible(true);
							}
						}
						
						if(terminalPesquisa.getPosicaoTerminal().getX() < 162) {
							terminalPesquisa.setVisible(true);
						}
						
						getContentPane().remove(scrollPane);	
						repaint();
						painelVisivel = false;
						btnOcultarPainel.setText("Mostrar Painel");
						btnOcultarPainel.setToolTipText("Abrir painel de estantes");
					} else {
						
						for(int i = 0; i < listaDeEstantes.size(); i++) {
							
							if(listaDeEstantes.get(arrayAuxReferencias.get(i)).getPosicaoEstante().getX() < 162
									&& listaDeEstantes.get(arrayAuxReferencias.get(i)).getPosicaoEstante().getX() != 0) {
								listaDeEstantes.get(arrayAuxReferencias.get(i)).setVisible(false);
							}
						}
						
						if(terminalPesquisa.getPosicaoTerminal().getX() < 162) {
							terminalPesquisa.setVisible(false);
						}
						
						getContentPane().add(scrollPane);
						repaint();
						btnOcultarPainel.setText("Ocultar Painel");
						btnOcultarPainel.setToolTipText("Esconder painel de estantes");						
						painelVisivel = true;
					}
				}
			});
			btnOcultarPainel.setBounds(180, 160, 120, 25);
			getContentPane().add(btnOcultarPainel);
		}
		
	}

	public boolean getPainelVisivel() {
		return painelVisivel;
	}
	
	public int getComponentCount_PainelEstantes() {
		return painelEstantes.getComponentCount();
	}

	public void removerPainelEstantes() {
		if(scrollPane != null) {
			getContentPane().remove(scrollPane);
			this.repaint();
		}
	}
	
	public void removerBotaoPainel() {
		if(btnOcultarPainel != null) {
			for(int i = 0; i < listaDeEstantes.size(); i++) {
				
				if(!listaDeEstantes.get(arrayAuxReferencias.get(i)).isVisible()) {
					listaDeEstantes.get(arrayAuxReferencias.get(i)).setVisible(true);
				}
			}
			
			if(!terminalPesquisa.isVisible()) {
				terminalPesquisa.setVisible(true);
			}
			
			getContentPane().remove(btnOcultarPainel);
			repaint();
		}
	}

	@SuppressWarnings("serial")
	private class NewContentPane extends JPanel {
		protected void paintComponent(final Graphics g) {
			super.paintComponent(g);
			g.drawImage(ImageController.FundoBiblioteca.getImage(), 0, 0, this);
		}

	}

}
