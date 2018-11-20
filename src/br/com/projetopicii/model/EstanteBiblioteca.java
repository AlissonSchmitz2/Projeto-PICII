package br.com.projetopicii.model;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.plaf.LabelUI;

import br.com.projetopicii.auxiliaries.VerticalLabelUI;
import br.com.projetopicii.view.CriarBibliotecaWindow;

public class EstanteBiblioteca extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = -936720566669213646L;

	// Auxiliares para movimentar as estantes.
	private int startDragX;
	private int startDragY;
	private boolean inDrag = false;

	// JLabel para as referências.
	private JLabel labelReferencia;

	// Auxiliares para o JLabel.
	@SuppressWarnings("unused")
	private VerticalLabelUI verticalLabelUI;
	private LabelUI defaultUI;
	private boolean vertical;

	// Auxiliar para saber se o movimento do mause foi ativado.
	private boolean ativarMovimentoMouse = true;

	// Objetos necessários.
	private JPanel thisPanel;
	private CriarBibliotecaWindow criarBibliotecaWindow;

	// Tamanho da tela.
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	// Objeto responsavel por guardar as posições X e Y da estante.
	private PosicaoComponente posicaoEstante = new PosicaoComponente();

	// Coordenada utilizada na thread que sobe as estantes.
	private int coordenadaY = 0;

	public PosicaoComponente getPosicaoEstante() {
		return posicaoEstante;
	}

	public boolean getAtivarMovimentoMouse() {
		return ativarMovimentoMouse;
	}

	public void setReferencia(String referencia) {

		labelReferencia = new JLabel(referencia);
		labelReferencia.setBounds(10, 20, 90, 25);
		defaultUI = labelReferencia.getUI();

		thisPanel.add(labelReferencia);
	}

	public void setThisPanel(JPanel thisPanel) {
		this.thisPanel = thisPanel;
	}

	public void ativarMovimentoMouse() {
		this.addMouseMotionListener(this);
	}

	public void ativarCliqueMouse() {
		this.addMouseListener(this);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// not interested
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// not interested
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startDragX = e.getX();
		startDragY = e.getY();
	}

	//
	@Override
	public void mouseReleased(MouseEvent e) {
		if (inDrag) {
			System.out.println("Estante " + labelReferencia.getText() + " alterada para: " + "X: " + this.getX()
					+ " Y: " + this.getY());

			inDrag = false;
			posicaoEstante.setX(this.getX());
			posicaoEstante.setY(this.getY());

			if (criarBibliotecaWindow != null) {
				if (criarBibliotecaWindow.isInformacao2()) {
					criarBibliotecaWindow.removerInformacao2();
					criarBibliotecaWindow.setInformacao2(false);
					criarBibliotecaWindow.repaint();
					criarBibliotecaWindow.inserirInformacao3();
				}
			}
		}

		verificarLimites();
	}

	private void verificarLimites() {

		if (criarBibliotecaWindow.getComponentCount_PainelEstantes() != 0 && criarBibliotecaWindow.getPainelVisivel()) {

			if (!ativarMovimentoMouse && !vertical) {

				// Limite lado esquerdo.
				this.setLocation((int) ((this.getX() < 162) ? 180 : this.getX()), this.getY());
				this.getPosicaoEstante().setX((this.getX() < 162) ? 180 : this.getX());

				// Limite lado direito.
				this.setLocation(
						(int) (this.getX() > screenSize.getWidth() - 110 ? screenSize.getWidth() - 130 : this.getX()),
						this.getY());
				this.getPosicaoEstante()
						.setX((int) ((this.getX() > screenSize.getWidth() - 110) ? screenSize.getWidth() - 130
								: this.getX()));

				// Limite superior.
				this.setLocation(this.getX(), this.getY() < 212 ? 212 : this.getY());
				this.getPosicaoEstante().setY((this.getY() < 212) ? 212 : this.getY());

				// Limite inferior
				this.setLocation(this.getX(),
						(int) (this.getY() > screenSize.getHeight() - 155 ? screenSize.getHeight() - 165
								: this.getY()));
				this.getPosicaoEstante()
						.setY((int) ((this.getY() > screenSize.getHeight() - 155) ? screenSize.getHeight() - 165
								: this.getY()));
			} else if (!ativarMovimentoMouse && vertical) {

				// Limite lado esquerdo.
				this.setLocation((int) ((this.getX() < 162) ? 180 : this.getX()), this.getY());
				this.getPosicaoEstante().setX((this.getX() < 162) ? 180 : this.getX());

				// Limite lado direito.
				this.setLocation(
						(int) (this.getX() > screenSize.getWidth() - 50 ? screenSize.getWidth() - 60 : this.getX()),
						this.getY());
				this.getPosicaoEstante().setX(
						(int) ((this.getX() > screenSize.getWidth() - 50) ? screenSize.getWidth() - 60 : this.getX()));

				// Limite superior.
				this.setLocation(this.getX(), this.getY() < 212 ? 212 : this.getY());
				this.getPosicaoEstante().setY((this.getY() < 212) ? 212 : this.getY());

				// Limite inferior
				this.setLocation(this.getX(),
						(int) (this.getY() > screenSize.getHeight() - 220 ? screenSize.getHeight() - 230
								: this.getY()));
				this.getPosicaoEstante()
						.setY((int) ((this.getY() > screenSize.getHeight() - 220) ? screenSize.getHeight() - 230
								: this.getY()));
			}
		} else {

			if (!ativarMovimentoMouse && !vertical) {

				// Limite lado esquerdo.
				this.setLocation((int) ((this.getX() < 5) ? 15 : this.getX()), this.getY());
				this.getPosicaoEstante().setX((this.getX() < 5) ? 15 : this.getX());

				// Limite lado direito.
				this.setLocation(
						(int) (this.getX() > screenSize.getWidth() - 110 ? screenSize.getWidth() - 130 : this.getX()),
						this.getY());
				this.getPosicaoEstante()
						.setX((int) ((this.getX() > screenSize.getWidth() - 110) ? screenSize.getWidth() - 130
								: this.getX()));

				// Limite superior.
				this.setLocation(this.getX(), this.getY() < 212 ? 212 : this.getY());
				this.getPosicaoEstante().setY((this.getY() < 212) ? 212 : this.getY());

				// Limite inferior
				this.setLocation(this.getX(),
						(int) (this.getY() > screenSize.getHeight() - 155 ? screenSize.getHeight() - 165
								: this.getY()));
				this.getPosicaoEstante()
						.setY((int) ((this.getY() > screenSize.getHeight() - 155) ? screenSize.getHeight() - 165
								: this.getY()));
			} else if (!ativarMovimentoMouse && vertical) {

				// Limite lado esquerdo.
				this.setLocation((int) ((this.getX() < 5) ? 15 : this.getX()), this.getY());
				this.getPosicaoEstante().setX((this.getX() < 5) ? 15 : this.getX());

				// Limite lado direito.
				this.setLocation(
						(int) (this.getX() > screenSize.getWidth() - 50 ? screenSize.getWidth() - 60 : this.getX()),
						this.getY());
				this.getPosicaoEstante().setX(
						(int) ((this.getX() > screenSize.getWidth() - 50) ? screenSize.getWidth() - 60 : this.getX()));

				// Limite superior.
				this.setLocation(this.getX(), this.getY() < 212 ? 212 : this.getY());
				this.getPosicaoEstante().setY((this.getY() < 212) ? 212 : this.getY());

				// Limite inferior
				this.setLocation(this.getX(),
						(int) (this.getY() > screenSize.getHeight() - 220 ? screenSize.getHeight() - 230
								: this.getY()));
				this.getPosicaoEstante()
						.setY((int) ((this.getY() > screenSize.getHeight() - 220) ? screenSize.getHeight() - 230
								: this.getY()));
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		// Identifica o clique com o botão direito.
		if (e.isMetaDown()) {

			// cria o primeiro item do menu e atribui uma ação pra ele
			JMenuItem item1 = new JMenuItem();
			item1.setText("Posicionar Estante");
			item1.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					if (criarBibliotecaWindow.isInformacao1()) {
						criarBibliotecaWindow.removerInformacao1();
						criarBibliotecaWindow.setInformacao1(false);
						criarBibliotecaWindow.repaint();
						criarBibliotecaWindow.inserirInformacao2();
					}

					// Mantém a estante vertical caso ela seja posicionada assim.
					if (vertical) {
						setBounds(200, 220, thisPanel.getWidth(), thisPanel.getHeight());
					} else {
						setBounds(200, 220, 110, 45);
					}

					criarBibliotecaWindow.getContentPane().add(thisPanel);

					if (criarBibliotecaWindow.getComponentCount_PainelEstantes() == 0) {
						criarBibliotecaWindow.removerPainelEstantes();
						criarBibliotecaWindow.removerBotaoPainel();
					}

					posicaoEstante.setX(200);
					posicaoEstante.setY(70);

					if (ativarMovimentoMouse) {
						ativarMovimentoMouse();
						ativarMovimentoMouse = false;
					}
				}
			});

			// cria o segundo item do menu e atribui uma ação pra ele
			JMenuItem item2 = new JMenuItem();
			item2.setText("Estante Vertical");
			item2.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					if (!vertical && !ativarMovimentoMouse) {

						labelReferencia.setUI(new VerticalLabelUI(true));
						setBounds(posicaoEstante.getX(), posicaoEstante.getY(), thisPanel.getHeight(),
								thisPanel.getWidth());
						vertical = true;
						mouseReleased(null);
					}
				}
			});

			// cria o terceiro item do menu e atribui uma ação pra ele
			JMenuItem item3 = new JMenuItem();
			item3.setText("Estante Horizontal");
			item3.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					if (vertical && !ativarMovimentoMouse) {

						labelReferencia.setUI(defaultUI);
						setBounds(posicaoEstante.getX(), posicaoEstante.getY(), thisPanel.getHeight(),
								thisPanel.getWidth());
						vertical = false;
						mouseReleased(null);
					}
				}
			});

			// cria o menu popup e adiciona os 2 itens
			JPopupMenu popup = new JPopupMenu();
			popup.add(item1);
			popup.add(item2);
			popup.add(item3);

			// mostra na tela
			popup.show(this, 10, 30);
		}

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int newX = this.getX() + (e.getX() - startDragX);
		int newY = this.getY() + (e.getY() - startDragY);
		this.setLocation(newX, newY);
		inDrag = true;
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// not interested
	}

	public void setCriarBibliotecaWindow(CriarBibliotecaWindow criarBibliotecaWindow) {
		this.criarBibliotecaWindow = criarBibliotecaWindow;
	}

	public int getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(int coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	public boolean isVertical() {
		return vertical;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
		if(isVertical()) {
			labelReferencia.setUI(new VerticalLabelUI(true));
		}
	}

}
