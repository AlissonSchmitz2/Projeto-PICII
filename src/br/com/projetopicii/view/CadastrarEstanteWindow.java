package br.com.projetopicii.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.com.projetopicii.model.dao.EstanteDao;

public class CadastrarEstanteWindow extends AbstractWindowFrame{

	private static final long serialVersionUID = -7382690220432259644L;

		private JLabel labes;
		private JTextField txfEstante;
		private JButton btnLimpar, btnSalvar;
		
		private EstanteDao estanteDao = new EstanteDao();
		
		// Tecla ENTER.
		KeyAdapter acao = new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					cadastrarEstante();
				}
			}
		};
		
		public CadastrarEstanteWindow() {		
			super("Cadastrar Estante");
			setBackground(new Color(250, 250, 250));
			criarComponentes();
		}
		
		private void criarComponentes() {
			
			labes = new JLabel("Estante:");
			labes.setBounds(15, 10, 250, 25);
			getContentPane().add(labes);
			
			txfEstante = new JTextField();
			txfEstante.setBounds(15, 30, 200, 25);
			txfEstante.setToolTipText("Informe a referência da estante");
			txfEstante.addKeyListener(acao);
			getContentPane().add(txfEstante);
		
			btnSalvar = new JButton("Salvar");
			btnSalvar.setBounds(15, 80, 95, 25);
			btnSalvar.setToolTipText("Clique aqui para salvar");
			getContentPane().add(btnSalvar);
			btnSalvar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					cadastrarEstante();
				}
			});
			
			btnLimpar = new JButton("Limpar");
			btnLimpar.setBounds(120, 80, 95, 25);
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
			
			try {
				estanteDao.cadastrarEstante(txfEstante.getText());
				JOptionPane.showMessageDialog(null, "Estante salva com sucesso!");
				limparCampos();
			} catch(Exception e1) {
				JOptionPane.showMessageDialog(rootPane, "Houve um erro ao tentar salvar a estante.",
						"", JOptionPane.ERROR_MESSAGE, null);
				e1.printStackTrace();
			}
		}
		
		private boolean validarCampos() {
			if(txfEstante.getText().isEmpty()) {
				return true;
			}
			
			return false;
		}
		
		private void limparCampos() {
			txfEstante.setText("");
		}
}
