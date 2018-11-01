package com.biblioteca.view;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.biblioteca.model.PosicaoEstante;

public class CriarBibliotecaWindow extends JFrame implements MouseListener, MouseMotionListener{
	private static final long serialVersionUID = -6766594202823918036L;
	
	private int startDragX, startDragY;
    private boolean inDrag = false;
    private JPanel estante = new JPanel();
    private PosicaoEstante posicao = new PosicaoEstante();
    
    public CriarBibliotecaWindow() {
        
        //Setar o tamanho
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        setTitle("Crie sua biblioteca");
        setLayout(null);
        setMinimumSize(new Dimension(screenSize.width, screenSize.height));
        
        //Para Teste:
        //setMinimumSize(new Dimension(300, 300));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        estante.setBackground(Color.red);
        estante.setBounds(10, 10, 110, 40);
        estante.addMouseListener(this);
        estante.addMouseMotionListener(this);
        add(estante);
        setVisible(true);
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
            System.out.println("Alterado posições para: " + estante.getX() + ", " + estante.getY());
            inDrag = false;
            posicao.setX(estante.getX());
            posicao.setY(estante.getY());
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    	
    	System.out.println(estante.getX()+ "  " + estante.getY() + "   " + e.getX() + "  " + e.getY());
    	estante.setBounds(posicao.getX(), posicao.getY(),estante.getHeight(),estante.getWidth());
    	
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        int newX = estante.getX() + (e.getX() - startDragX);
        int newY = estante.getY() + (e.getY() - startDragY);
        estante.setLocation(newX, newY);
        inDrag = true;
    }
    
    @Override
    public void mouseMoved(MouseEvent arg0) {
        // not interested
    }
        
    public static void main(String[] args) {
        
    	java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CriarBibliotecaWindow();
            }
        });
    	
    }
}