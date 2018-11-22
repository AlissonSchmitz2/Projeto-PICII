package br.com.projetopicii.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import br.com.projetopicii.model.bean.Estante;
import br.com.projetopicii.model.bean.Livro;
import br.com.projetopicii.model.dao.EstanteDao;
import br.com.projetopicii.model.dao.LivroDao;
import br.com.projetopicii.observer.ObserverLivro;
import br.com.projetopicii.observer.SubjectLivro;

public class CadastrarLivrosWindow extends AbstractWindowFrame implements SubjectLivro {

	private static final long serialVersionUID = -8924865338524496408L;

	private JLabel labes;
	private JTextField txfTitulo, txfAutor, txfGenero, txfIdioma;
	private JFormattedTextField txfAnoLancamento, txfNumeroPag;
	private JComboBox<String> cbxEstantes;
	private JButton btnLimpar, btnSalvar;

	private EstanteDao estanteDao = new EstanteDao();
	private LivroDao livroDao = new LivroDao();
	private String nomeEstantes[];
	private Livro livro;
	private ArrayList<ObserverLivro> observers = new ArrayList<ObserverLivro>();

	// Tecla ENTER.
	KeyAdapter acao = new KeyAdapter() {
		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				cadastrarLivro();
			}
		}
	};

	public CadastrarLivrosWindow() {
		super("Cadastrar Livros");
		setBackground(new Color(250, 250, 250));
		criarComponentes();
	}

	public CadastrarLivrosWindow(Livro livro) {
		super("Cadastrar Livros");
		this.livro = livro;
		setBackground(new Color(250, 250, 250));
		criarComponentes();
		setarValores(livro);
	}

	private void criarComponentes() {
		labes = new JLabel("Título:");
		labes.setBounds(15, 10, 250, 25);
		getContentPane().add(labes);

		txfTitulo = new JTextField();
		txfTitulo.setBounds(15, 30, 200, 25);
		txfTitulo.setToolTipText("Informe o título do livro");
		txfTitulo.addKeyListener(acao);
		getContentPane().add(txfTitulo);

		labes = new JLabel("Autor:");
		labes.setBounds(15, 60, 250, 25);
		getContentPane().add(labes);

		txfAutor = new JTextField();
		txfAutor.setBounds(15, 80, 200, 25);
		txfAutor.setToolTipText("Informe o autor");
		txfAutor.addKeyListener(acao);
		getContentPane().add(txfAutor);

		labes = new JLabel("Gênero:");
		labes.setBounds(15, 110, 250, 25);
		getContentPane().add(labes);

		txfGenero = new JTextField();
		txfGenero.setBounds(15, 130, 200, 25);
		txfGenero.setToolTipText("Informe o Gênero");
		txfGenero.addKeyListener(acao);
		getContentPane().add(txfGenero);

		labes = new JLabel("Ano de lançamento:");
		labes.setBounds(15, 160, 250, 25);
		getContentPane().add(labes);

		try {
			txfAnoLancamento = new JFormattedTextField(new MaskFormatter("####"));
			txfAnoLancamento.setFocusLostBehavior(JFormattedTextField.COMMIT);
			txfAnoLancamento.setBounds(15, 180, 200, 25);
			txfAnoLancamento.setToolTipText("Informe o ano de lançamento");
			txfAnoLancamento.addKeyListener(acao);
			getContentPane().add(txfAnoLancamento);

			labes = new JLabel("Números de páginas:");
			labes.setBounds(15, 210, 250, 25);
			getContentPane().add(labes);

			txfNumeroPag = new JFormattedTextField(new MaskFormatter("#######"));
			txfNumeroPag.setFocusLostBehavior(JFormattedTextField.COMMIT);
			txfNumeroPag.setBounds(15, 230, 200, 25);
			txfNumeroPag.setToolTipText("Informe o número de páginas");
			txfNumeroPag.addKeyListener(acao);
			getContentPane().add(txfNumeroPag);

		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		labes = new JLabel("Idioma");
		labes.setBounds(15, 260, 250, 25);
		getContentPane().add(labes);

		txfIdioma = new JTextField();
		txfIdioma.setBounds(15, 280, 200, 25);
		txfIdioma.setToolTipText("Informe o idioma");
		txfIdioma.addKeyListener(acao);
		getContentPane().add(txfIdioma);

		labes = new JLabel("Estante");
		labes.setBounds(15, 310, 250, 25);
		getContentPane().add(labes);

		cbxEstantes = new JComboBox<>();
		cbxEstantes.setToolTipText("Selecione uma estante para o livro");
		cbxEstantes.addItem("-Selecione-");
		nomeEstantes = estanteDao.pegarNomeEstantes(false);

		for (int i = 0; i < nomeEstantes.length; i++) {

			cbxEstantes.addItem(nomeEstantes[i]);
		}
		cbxEstantes.setBounds(15, 330, 200, 25);
		getContentPane().add(cbxEstantes);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(15, 380, 95, 25);
		btnSalvar.setToolTipText("Clique aqui para salvar");
		getContentPane().add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cadastrarLivro();
			}
		});

		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(120, 380, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar o campo");
		getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (livro != null) {
					setarValores(livro);
				} else {
					limparCampos();
				}
			}
		});

	}

	private void cadastrarLivro() {
		if (validarCampos()) {
			JOptionPane.showMessageDialog(rootPane, "Preencha todos os campos para cadastrar!", "Alerta",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		try {

			if (livro != null) {
				int idEstante = estanteDao.pegarIdEstante(cbxEstantes.getSelectedItem().toString());

				livroDao.cadastrarLivro(txfTitulo.getText(), txfAutor.getText(), txfGenero.getText(),
						Integer.parseInt(txfAnoLancamento.getText().trim()),
						Integer.parseInt(txfNumeroPag.getText().trim()), idEstante, txfIdioma.getText(), false,
						livro.getId());
				notifyObservers(livro);
				livro = null;
				JOptionPane.showMessageDialog(null, "Livro salvo com sucesso!");
				dispose();
			} else {
				int idEstante = estanteDao.pegarIdEstante(cbxEstantes.getSelectedItem().toString());

				livroDao.cadastrarLivro(txfTitulo.getText(), txfAutor.getText(), txfGenero.getText(),
						Integer.parseInt(txfAnoLancamento.getText().trim()),
						Integer.parseInt(txfNumeroPag.getText().trim()), idEstante, txfIdioma.getText(), true, -1);

				JOptionPane.showMessageDialog(null, "Livro salvo com sucesso!");
				limparCampos();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(rootPane, "Houve um erro ao tentar salvar o livro", "",
					JOptionPane.ERROR_MESSAGE, null);
			e.printStackTrace();
		}
	}

	private boolean validarCampos() {
		if (txfTitulo.getText().isEmpty() || txfAutor.getText().isEmpty() || txfGenero.getText().isEmpty()
				|| txfAnoLancamento.getText().trim().isEmpty() || txfNumeroPag.getText().trim().isEmpty()
				|| cbxEstantes.getSelectedItem().toString().equals("-Selecione-") || txfIdioma.getText().isEmpty()) {
			return true;
		}

		return false;
	}

	private void limparCampos() {
		txfTitulo.setText("");
		txfAutor.setText("");
		txfGenero.setText("");
		txfAnoLancamento.setText("");
		txfNumeroPag.setText("");
		cbxEstantes.setSelectedItem("-Selecione-");
		txfIdioma.setText("");
		txfTitulo.requestFocus();
	}

	private void setarValores(Livro livro) {

		Estante estanteAux = new Estante();
		estanteAux = estanteDao.pegarEstantePorId(livro.getId_Estante());
		txfTitulo.setText(livro.getTitulo());
		txfAutor.setText(livro.getAutor());
		txfGenero.setText(livro.getGenero());
		txfAnoLancamento.setText(String.valueOf(livro.getAnoLancamento()));
		txfNumeroPag.setText(String.valueOf(livro.getNumPaginas()));
		txfIdioma.setText(livro.getIdioma());
		cbxEstantes.setSelectedItem(estanteAux.getNome());
	}

	@Override
	public void addObserver(ObserverLivro o) {
		observers.add(o);

	}

	@Override
	public void removeObserver(ObserverLivro o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(Livro livro) {
		Iterator<ObserverLivro> it = observers.iterator();
		while (it.hasNext()) {
			ObserverLivro observer = (ObserverLivro) it.next();
			observer.update(livro);
		}

	}
}
