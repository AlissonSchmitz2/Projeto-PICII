package br.com.projetopicii.grafo;

import java.util.ArrayList;
import java.util.List;

public class Vertice{
        
	
	private double distancia;
	private int quantidadeLivrosAfim;
	private int codigo;
	private boolean verticeVisitado = false;
	private Vertice pai = null;
	private List<Aresta> aresta = new ArrayList<Aresta>();
	
	public List<Aresta> getAresta() {
		return aresta;
	}
	
	public void setAresta(Aresta novaAresta) {
		aresta.add(novaAresta);
	}
	
	public double getDistancia() {
		return distancia;
	}
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	public boolean isVerticeVisitado() {
		return verticeVisitado;
	}
	public void setVerticeVisitado(boolean verticeVisitado) {
		this.verticeVisitado = verticeVisitado;
	}

	public Vertice getPai() {
		return pai;
	}

	public void setPai(Vertice pai) {
		this.pai = pai;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codOrigin) {
		this.codigo = codOrigin;
	}

	public int getQuantidadeLivrosAfim() {
		return quantidadeLivrosAfim;
	}

	public void setQuantidadeLivrosAfim(int quantidadeLivrosAfim) {
		this.quantidadeLivrosAfim = quantidadeLivrosAfim;
	}
	
	
	
}