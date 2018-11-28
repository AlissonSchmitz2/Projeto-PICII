package br.com.projetopicii.view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import br.com.projetopicii.auxiliaries.CellRenderer;
import br.com.projetopicii.model.LivrosTableModel;
import br.com.projetopicii.model.bean.Estante;
import br.com.projetopicii.pictures.ImageController;

public class ListarLivrosPorEstanteWindow extends AbstractWindowFrame {
	private static final long serialVersionUID = 5514482732745103923L;
	
	// JTable.
	private JTable tableLivros;
	private TableModel livrosTableModel;
	private JScrollPane scrollpaneTable;

	// Tamanho da Tela.
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	private Estante estante;

	public ListarLivrosPorEstanteWindow(Estante estante) {
		super("Livros da Estante");
		livrosTableModel = new LivrosTableModel();
		this.estante = estante;
		setTitle("Livros da Estante " + estante.getNome());
		setContentPane(new NewContentPane());
		setLayout(null);
		setVisible(true);

		criarComponentes();
	}

	private void criarComponentes() {

		tableLivros = new JTable(livrosTableModel);

		// Setar tamanho de cada coluna.
		tableLivros.getColumnModel().getColumn(0).setPreferredWidth(200);
		tableLivros.getColumnModel().getColumn(1).setPreferredWidth(160);
		tableLivros.getColumnModel().getColumn(2).setPreferredWidth(120);
		tableLivros.getColumnModel().getColumn(3).setPreferredWidth(60);
		tableLivros.getColumnModel().getColumn(4).setPreferredWidth(60);
		tableLivros.getColumnModel().getColumn(5).setPreferredWidth(30);

		tableLivros.setDefaultRenderer(Object.class, new CellRenderer());
		scrollpaneTable = new JScrollPane(tableLivros);
		scrollpaneTable.setBounds((int) (screenSize.getWidth() / 2) - 500, (int) (screenSize.getHeight() / 2) - 250,
				1000, 460);
		scrollpaneTable.setVisible(true);
		getContentPane().add(scrollpaneTable);

		((LivrosTableModel) livrosTableModel).limpar();
		((LivrosTableModel) livrosTableModel).addRow(estante.getLivros());
	}

	@SuppressWarnings("serial")
	private class NewContentPane extends JPanel {
		protected void paintComponent(final Graphics g) {
			super.paintComponent(g);

			// Fundo para tela HD.
			if (screenSize.getWidth() < 1400) {
				g.drawImage(ImageController.findMyBook_1366x768.getImage(), 0, 0, this);
			}
			// Fundo para tela Full HD.
			else {
				g.drawImage(ImageController.findMyBook_1920x1080.getImage(), 0, 0, this);
			}
		}

	}
}
