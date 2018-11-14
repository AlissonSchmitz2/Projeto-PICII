package br.com.projetopicii.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

public class CadastrarLivrosWindow extends AbstractWindowFrame{

	private static final long serialVersionUID = -8924865338524496408L;

	private JLabel labes;
	private JTextField txfTitulo, txfAutor, txfGenero;
	private JFormattedTextField txfAnoLancamento, txfNumeroPag;
	//Idestante
	private JButton btnLimpar, btnSalvar;

	//Desktop.
	private JDesktopPane desktop;
	
	public CadastrarLivrosWindow(JDesktopPane desktop) {		
		super("Cadastrar Livros");
		this.desktop = desktop;
		setBackground(new Color(250, 250, 250));
		criarComponentes();
	}
	
	private void criarComponentes() {
		labes = new JLabel("Título:");
		labes.setBounds(15, 10, 250, 25);
		getContentPane().add(labes);
		
		txfTitulo = new JTextField();
		txfTitulo.setBounds(15, 30, 200, 25);
		txfTitulo.setToolTipText("Informe o título do livro");
		getContentPane().add(txfTitulo);
		
		labes = new JLabel("Autor:");
		labes.setBounds(15, 60, 250, 25);
		getContentPane().add(labes);
		
		txfAutor = new JTextField();
		txfAutor.setBounds(15, 80, 200, 25);
		txfAutor.setToolTipText("Informe o autor");
		getContentPane().add(txfAutor);
		
		labes = new JLabel("Gênero:");
		labes.setBounds(15, 110, 250, 25);
		getContentPane().add(labes);
		
		txfGenero = new JTextField();
		txfGenero.setBounds(15, 130, 200, 25);
		txfGenero.setToolTipText("Informe o Gênero");
		getContentPane().add(txfGenero);
		
		labes = new JLabel("Ano de lançamento:");
		labes.setBounds(15, 160, 250, 25);
		getContentPane().add(labes);
		
		try {
		txfAnoLancamento = new JFormattedTextField(new MaskFormatter("####"));
		txfAnoLancamento.setFocusLostBehavior(JFormattedTextField.COMMIT);
		txfAnoLancamento.setBounds(15, 180, 200, 25);
		txfAnoLancamento.setToolTipText("Informe o ano de lançamento");
		getContentPane().add(txfAnoLancamento);
		
		labes = new JLabel("Números de páginas:");
		labes.setBounds(15, 210, 250, 25);
		getContentPane().add(labes);
		
		txfNumeroPag = new JFormattedTextField(new MaskFormatter("#######"));
		txfNumeroPag.setFocusLostBehavior(JFormattedTextField.COMMIT);
		txfNumeroPag.setBounds(15, 230, 200, 25);
		txfNumeroPag.setToolTipText("Informe o número de páginas");
		getContentPane().add(txfNumeroPag);
		
		} catch (ParseException e1) {
			e1.printStackTrace();
			}
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(15, 280, 95, 25);
		btnSalvar.setToolTipText("Clique aqui para salvar");
		getContentPane().add(btnSalvar);
		btnSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cadastrarEstante();
			}
		});
		
		btnLimpar = new JButton("Limpar");
		btnLimpar.setBounds(120, 280, 95, 25);
		btnLimpar.setToolTipText("Clique aqui para limpar o campo");
		getContentPane().add(btnLimpar);
		btnLimpar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});

	}
	
	private void cadastrarEstante() {
		if(validarCampos()) {
			JOptionPane.showMessageDialog(rootPane, 
					"Preencha todos os campos para cadastrar!", "Alerta",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		//TODO: Cadastrar estante
	}
	
	private boolean validarCampos() {
		if(txfTitulo.getText().isEmpty() | txfAutor.getText().isEmpty()) {
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
	}
}
