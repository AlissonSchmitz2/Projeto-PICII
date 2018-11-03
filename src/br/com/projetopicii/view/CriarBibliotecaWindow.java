package br.com.projetopicii.view;
import java.awt.*;
import java.util.HashMap;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import javax.swing.*;

import br.com.projetopicii.model.EstanteBiblioteca;
import br.com.projetopicii.model.TerminalPesquisa;
import br.com.projetopicii.threads.ThreadSubirListaEstante;

public class CriarBibliotecaWindow extends JFrame {
	private static final long serialVersionUID = -6766594202823918036L;
	        
    //private JLabel wallpaper;
	private Image bImage;
        
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
    
    NewContentPane teste;
    
    public CriarBibliotecaWindow(String[] referencias) {   
    	
    	this.bImage = this.createImage(this.getClass().getResource("/br/com/projetopicii/pictures/FundoBiblioteca.png"));
    	this.referencias = referencias;
    	
        setTitle("Construção da Biblioteca");
        super.setContentPane(teste = new NewContentPane());
        setLayout(null);
        setBounds(new Rectangle(0, 0, screenSize.width, screenSize.height));
        setExtendedState(MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        
        criarComponentes();
        
        setVisible(true);
    }
    
    private void criarComponentes() {
    	
    	painelEstantes.setPreferredSize(new Dimension(160, screenSize.height));
    	painelEstantes.setBackground(Color.WHITE);
    	painelEstantes.setLayout(null);
    	painelEstantes.setBorder(BorderFactory.createTitledBorder("Estantes"));
    	painelEstantes.setBackground((new Color(245, 245, 245)));
    	
    	int y = 25;
    	
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
    	
    	//Terminal de Pesquisa.
    	terminalPesquisa.setarTerminal(this);
    	
    	//Thread.
    	threadSubirListaEstante = new ThreadSubirListaEstante(listaDeEstantes, referencias);
    	  
    	
    	JScrollPane scrollPane = new JScrollPane(painelEstantes);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(0, 0, 160, screenSize.height - 60);
        getContentPane().add(scrollPane);
        
    }
    
    private Image createImage(URL url){
        return Toolkit.getDefaultToolkit().createImage(url);
    }
    
    @SuppressWarnings("serial")
	private class NewContentPane extends JPanel{
        protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.drawImage(bImage, 0, 0, this);
        }
        
    }
        
    public static void main(String[] args) {
    	
    	String[] referenciasTeste = new String[5];
    	
    	for(int i = 0; i < 5; i++) {
    		referenciasTeste[i] = "Ref: teste" + i;
    	}
    	
    	new CriarBibliotecaWindow(referenciasTeste);    	
    }
}