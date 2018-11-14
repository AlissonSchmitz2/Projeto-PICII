package br.com.projetopicii.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class CadastrarEstanteWindow extends AbstractWindowFrame{

	private static final long serialVersionUID = -7382690220432259644L;

		private JLabel labes;
		private JTextField txfRef, txfEstante;
		private JButton btnLimpar, btnSalvar;
	
		//Desktop.
		private JDesktopPane desktop;
		
		public CadastrarEstanteWindow(JDesktopPane desktop) {		
			super("Cadastrar Estante");
			this.desktop = desktop;
			setBackground(new Color(250, 250, 250));
			criarComponentes();
		}
		
		private void criarComponentes() {
			labes = new JLabel("Referência:");
			labes.setBounds(15, 10, 250, 25);
			getContentPane().add(labes);
			
			txfRef = new JTextField();
			txfRef.setBounds(15, 30, 200, 25);
			txfRef.setToolTipText("Informe a referência");
			getContentPane().add(txfRef);
			
			labes = new JLabel("Estante:");
			labes.setBounds(15, 60, 250, 25);
			getContentPane().add(labes);
			
			txfEstante = new JTextField();
			txfEstante.setBounds(15, 80, 200, 25);
			txfEstante.setToolTipText("Informe o nome da estante");
			getContentPane().add(txfEstante);
		
			btnSalvar = new JButton("Salvar");
			btnSalvar.setBounds(15, 130, 95, 25);
			btnSalvar.setToolTipText("Clique aqui para salvar");
			getContentPane().add(btnSalvar);
			btnSalvar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					cadastrarEstante();
				}
			});
			
			btnLimpar = new JButton("Limpar");
			btnLimpar.setBounds(120, 130, 95, 25);
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
			if(txfEstante.getText().isEmpty() | txfRef.getText().isEmpty()) {
				return true;
			}
			
			return false;
		}
		
		private void limparCampos() {
			txfEstante.setText("");
			txfRef.setText("");
		}
}
