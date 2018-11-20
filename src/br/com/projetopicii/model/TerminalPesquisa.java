package br.com.projetopicii.model;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;

import br.com.projetopicii.pictures.ImageController;
import br.com.projetopicii.view.CriarBibliotecaWindow;
import br.com.projetopicii.view.EditarBibliotecaWindow;
import br.com.projetopicii.view.ResultadoCaminhoWindow;

public class TerminalPesquisa extends JLabel implements MouseListener, MouseMotionListener{
	
	private static final long serialVersionUID = 4785021430218627178L;
	
	//Auxiliares para movimentar as estantes.
	private int startDragX;
	private int startDragY;
	private boolean inDrag = false;
	
	// Instância da criação da biblioteca.
	private CriarBibliotecaWindow criarBibliotecaWindow;
	private EditarBibliotecaWindow editarBibliotecaWindow;
	
	//Tamanho da tela.
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//Objeto responsavel por guardar as posições X e Y do terminal de pesquisa.
	private PosicaoComponente posicaoTerminal = new PosicaoComponente();	

	public void setarTerminal(CriarBibliotecaWindow criarBibliotecaWindow) {
		this.criarBibliotecaWindow = criarBibliotecaWindow;
		this.setIcon(ImageController.TerminalPesquisa);
		setToolTipText("Terminal de Pesquisa");
		setBounds(screenSize.width / 2, (screenSize.height / 2) + 185, 90, 90);
		criarBibliotecaWindow.getContentPane().add(this);
		
		posicaoTerminal.setX(screenSize.width / 2);
		posicaoTerminal.setY((screenSize.height / 2) + 250);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void setarTerminal(ResultadoCaminhoWindow resultadoCaminhoWindow, int coordenadaX, int coordenadaY) {
		this.setIcon(ImageController.TerminalPesquisa);
		setToolTipText("Terminal de Pesquisa");
		setBounds(coordenadaX, coordenadaY, 90, 90);
		resultadoCaminhoWindow.getContentPane().add(this);
	}
	
	public void setarTerminal(EditarBibliotecaWindow editarBibliotecaWindow, int coordenadaX, int coordenadaY) {
		this.setIcon(ImageController.TerminalPesquisa);
		this.editarBibliotecaWindow = editarBibliotecaWindow;
		setToolTipText("Terminal de Pesquisa");
		setBounds(coordenadaX, coordenadaY, 90, 90);
		editarBibliotecaWindow.getContentPane().add(this);		
		
		posicaoTerminal.setX(screenSize.width / 2);
		posicaoTerminal.setY((screenSize.height / 2) + 250);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public PosicaoComponente getPosicaoTerminal() {
		return posicaoTerminal;
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
            System.out.println("Terminal de pesquisa alterado para: " + " X: " + this.getX() + " Y: " + this.getY());
            
            inDrag = false;
            posicaoTerminal.setX(this.getX());
            posicaoTerminal.setY(this.getY());
        }
                        
        verificarLimites();
     }
    
    private void verificarLimites() {
    	
		if (criarBibliotecaWindow != null
				? criarBibliotecaWindow.getComponentCount_PainelEstantes() != 0
						&& criarBibliotecaWindow.getPainelVisivel()
				: editarBibliotecaWindow.getComponentCount_PainelEstantes() != 0
						&& editarBibliotecaWindow.getPainelVisivel()) {
	    	//Limite lado esquerdo.
	        this.setLocation ((int) ( (this.getX() < 162) ? 180 : this.getX()), this.getY());
	        this.getPosicaoTerminal().setX((this.getX() < 162) ? 180 : this.getX());
	        
	        //Limite lado direito.
	        this.setLocation((int) (this.getX() > screenSize.getWidth() - 80 ? screenSize.getWidth() - 110 : this.getX()), this.getY());
	        this.getPosicaoTerminal().setX( (int) ((this.getX() > screenSize.getWidth() - 80) ? screenSize.getWidth() - 110 : this.getX()));
	        
	        //Limite superior.        
	        this.setLocation(this.getX(), this.getY() < 198 ? 198 : this.getY());	
	        this.getPosicaoTerminal().setY( (this.getY() < 198) ? 198 : this.getY()); 
	        
	        //Limite inferior        
	        this.setLocation(this.getX(), (int) (this.getY() > screenSize.getHeight() - 185 ? screenSize.getHeight() - 200 : this.getY()));	
	        this.getPosicaoTerminal().setY( (int) ((this.getY() > screenSize.getHeight() - 185) ? screenSize.getHeight() - 200 : this.getY()));
    	} else {
    		//Limite lado esquerdo.
            this.setLocation ((int) ( (this.getX() < 5) ? 15 : this.getX()), this.getY());
            this.getPosicaoTerminal().setX((this.getX() < 5) ? 15 : this.getX());
            
            //Limite lado direito.
            this.setLocation((int) (this.getX() > screenSize.getWidth() - 80 ? screenSize.getWidth() - 110 : this.getX()), this.getY());
            this.getPosicaoTerminal().setX( (int) ((this.getX() > screenSize.getWidth() - 80) ? screenSize.getWidth() - 110 : this.getX()));
            
            //Limite superior.        
            this.setLocation(this.getX(), this.getY() < 198 ? 198 : this.getY());	
            this.getPosicaoTerminal().setY( (this.getY() < 198) ? 198 : this.getY()); 
            
            //Limite inferior        
            this.setLocation(this.getX(), (int) (this.getY() > screenSize.getHeight() - 185 ? screenSize.getHeight() - 200 : this.getY()));	
            this.getPosicaoTerminal().setY( (int) ((this.getY() > screenSize.getHeight() - 185) ? screenSize.getHeight() - 200 : this.getY()));
    	}
    }
    
    @Override
    public void mouseClicked(MouseEvent e) { 
    	// not interested
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
}
