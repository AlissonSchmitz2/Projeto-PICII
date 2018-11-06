package br.com.projetopicii.view;
import java.awt.*;
import java.util.HashMap;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.*;

import br.com.projetopicii.model.EstanteBiblioteca;
import br.com.projetopicii.model.TerminalPesquisa;
import br.com.projetopicii.threads.ThreadSubirListaEstante;

public class CriarBibliotecaWindow extends JFrame {
	private static final long serialVersionUID = -6766594202823918036L;
	        
    //Imagens e Icons.
	private Image fundoBiblioteca;
	private ImageIcon iconBalao1;
	private ImageIcon iconBalao2;
	private ImageIcon iconBalao3;
	
	//Balões do tutorial.
	private JLabel labelBalao1;	
	private JLabel labelBalao2;	
	private JLabel labelBalao3;
	
	//Auxiliares do tutorial.
	private boolean informacao1 = true;
	private boolean informacao2 = true;
	
	
	//Botões.
	private JButton btnFimTutorial;
	private JButton btnSalvar;
		
    //Painel para as estantes.    
    private JPanel painelEstantes = new JPanel();
   
    //HashMap: Key -> Referência da estante / Value -> Objeto estante.
    private HashMap<String, EstanteBiblioteca> listaDeEstantes = new HashMap<>();
    
    //Estantes.
    private EstanteBiblioteca estanteBiblioteca;
    
    //Terminal de pesquisa.
    private TerminalPesquisa terminalPesquisa = new TerminalPesquisa();
    
    //Dados para criação das estantes.
    private String[] referencias;
    
    //Thread para subir as estantes ao retirar alguma do painel.
    ThreadSubirListaEstante threadSubirListaEstante;
    
    //Tamanho da tela.
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
    
    public CriarBibliotecaWindow(String[] referencias) {   
    	
    	this.fundoBiblioteca = this.createImage(this.getClass().getResource("/br/com/projetopicii/pictures/FundoBiblioteca.png"));
    	this.referencias = referencias;
    	
        setTitle("Construção da Biblioteca");
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
    	
    	//Painel para as estantes.
    	painelEstantes.setBackground(Color.WHITE);
    	painelEstantes.setLayout(null);
    	painelEstantes.setBorder(BorderFactory.createTitledBorder("Estantes"));
    	painelEstantes.setBackground((new Color(245, 245, 245)));
    	
    	int y = 25;
    	
    	//Adiciona as estantes ao painel de acordo com a quantidade de referências.
    	for(int i = 0; i < referencias.length; i++) {
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
    	//Ajusta o tamanho do painel de acordo com a quantidade de estantes.
    	painelEstantes.setPreferredSize(new Dimension(160, y));
    	
    	//Terminal de Pesquisa.
    	terminalPesquisa.setarTerminal(this);
    	
    	//Thread.
    	threadSubirListaEstante = new ThreadSubirListaEstante((HashMap<String, EstanteBiblioteca>) listaDeEstantes.clone(), referencias);
    	  
    	//Scroll para o painel de estantes.
    	JScrollPane scrollPane = new JScrollPane(painelEstantes);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(0, 0, 160, screenSize.height - 60);
        getContentPane().add(scrollPane);
        
        btnSalvar = new JButton(new AbstractAction("Salvar Biblioteca") {
			private static final long serialVersionUID = 6168373527954185392L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Salvar as posições das estantes e do terminal no banco de dados.
								
				System.out.println("-----TESTE POSIÇÕES PARA SALVAR-----");
				for(int i = 0; i < listaDeEstantes.size(); i++) {
					
					System.out.println("Estante " + referencias[i] + ": X: " + listaDeEstantes.get(referencias[i]).getPosicaoEstante().getX() 
							+ " Y: " + listaDeEstantes.get(referencias[i]).getPosicaoEstante().getY());
				}
				
				System.out.println("Terminal de Pesquisa: X: " + terminalPesquisa.getPosicaoTerminal().getX() + " Y: " + terminalPesquisa.getPosicaoTerminal().getY() );
			}
		});
        btnSalvar.setBounds((int) screenSize.getWidth() - 150, 160, 130 , 25);
        getContentPane().add(btnSalvar);
        
        //COMPONENTES DO TUTORIAL.
        
        iconBalao1 = new ImageIcon(this.getClass().getResource("/br/com/projetopicii/pictures/Informação1.png"));
        
        labelBalao1 = new JLabel();
        labelBalao1.setBounds(170, -10, 225, 245);
        labelBalao1.setIcon(iconBalao1);
        
        getContentPane().add(labelBalao1);
        
        iconBalao2 = new ImageIcon(this.getClass().getResource("/br/com/projetopicii/pictures/Informação2.png"));
        
        labelBalao2 = new JLabel();
        labelBalao2.setBounds(370, -10, 225, 245);
        labelBalao2.setIcon(iconBalao2);
        
        iconBalao3 = new ImageIcon(this.getClass().getResource("/br/com/projetopicii/pictures/Informação3.png"));
        
        labelBalao3 = new JLabel();
        labelBalao3.setBounds(570, -10, 245, 225);
        labelBalao3.setIcon(iconBalao3); 
        
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
    
    private Image createImage(URL url){
        return Toolkit.getDefaultToolkit().createImage(url);
    }
    
    @SuppressWarnings("serial")
	private class NewContentPane extends JPanel{
        protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(fundoBiblioteca, 0, 0, this);
        }
        
    }
    
    //MÉTODOS PARA OS BALÕES DO TUTORIAL.
    
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
	

	//Main para teste.
	public static void main(String[] args) {
    	
    	String[] referenciasTeste = new String[5];
    	
    	for(int i = 0; i < 5; i++) {
    		referenciasTeste[i] = "Ref: teste" + i;
    	}
    	
    	new CriarBibliotecaWindow(referenciasTeste);    	
    }
}