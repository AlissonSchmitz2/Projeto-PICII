package br.com.projetopicii.model.bean;

import java.util.ArrayList;

public class Estante {
	
	private ArrayList<Livro> livros = new ArrayList<Livro>();
	private int id;
	private String nome;
	private int coordenadaX;
	private int coordenadaY;
	private boolean vertical;
	
	public ArrayList<Livro> getLivros() {
		return livros;
	}

	public void setLivros(ArrayList<Livro> livros) {
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isVertical() {
		return vertical;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}	
			
}
