package br.com.projetopicii.grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Grafo {

	private List<Vertice> vertice = new ArrayList<Vertice>();
	private final int OO = 9999;

	public List<Vertice> getVertices() {
		return vertice;
	}

	public void setVertices(Vertice novoVertice) {
		vertice.add(novoVertice);
	}
}