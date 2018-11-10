package br.com.projetopicii.model.bean;

import java.util.ArrayList;
import java.util.List;

public class Estante {
	
	private List<Livro> livros = new ArrayList<Livro>();
	private int coordenadaX;
	private int coordenadaY;
	
	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}

	public int getCoordenadaX() {
		return coordenadaX;
	}

	public void setCoordenadaX(int coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	public int getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(int coordenadaY) {
		this.coordenadaY = coordenadaY;
	}
	
	
}
