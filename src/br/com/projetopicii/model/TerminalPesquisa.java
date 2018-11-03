package br.com.projetopicii.model;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import br.com.projetopicii.view.CriarBibliotecaWindow;

public class TerminalPesquisa extends JLabel implements MouseListener, MouseMotionListener{
	
	private static final long serialVersionUID = 4785021430218627178L;
	
	//Auxiliares para movimentar as estantes.
	private int startDragX;
	private int startDragY;
	private boolean inDrag = false;
	
	//Tamanho da tela.
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	//Objeto responsavel por guardar as posições X e Y do terminal de pesquisa.
	private PosicaoComponente posicaoTerminal = new PosicaoComponente();	

	public void setarTerminal(CriarBibliotecaWindow criarBibliotecaWindow) {
		ImageIcon icone = new ImageIcon(this.getClass().getResource("/br/com/projetopicii/pictures/TerminalPesquisa.png"));
		this.setIcon(icone);
		setBounds(screenSize.width / 2, (screenSize.height / 2) + 250, 90, 90);
		criarBibliotecaWindow.getContentPane().add(this);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
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
            System.out.println("Alterado posições para: " + this.getX() + ", " + this.getY());
            
            inDrag = false;
            posicaoTerminal.setX(this.getX());
            posicaoTerminal.setY(this.getY());
        }
                        
        //Limite lado esquerdo.
        this.setLocation ((int) ( (this.getX() < 162) ? 180 : this.getX()), this.getY());	
        
        //Limite lado direito.
        this.setLocation((int) (this.getX() > screenSize.getWidth() - 80 ? screenSize.getWidth() - 110 : this.getX()), this.getY());	
        //Limite superior.
        
        this.setLocation(this.getX(), this.getY() < 10 ? 10 : this.getY());	
        
        //Limite inferior        
        this.setLocation(this.getX(), (int) (this.getY() > screenSize.getHeight() - 143 ? screenSize.getHeight() - 160 : this.getY()));	
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
