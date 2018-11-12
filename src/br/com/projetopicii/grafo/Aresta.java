package br.com.projetopicii.grafo;

public class Aresta {
	private int codOrigin;
	private int codDestino;
	private String origem;
	private String estanteDestino;
	private float pesoAresta;
	private float distancia;
	
	public int getCodOrigin() {
		return codOrigin;
	}
	
	public void setCodOrigin(int codOrigin) {
		this.codOrigin = codOrigin;
	}
	
	public int getCodDestino() {
		return codDestino;
	}
	
	public void setCodDestino(int codDestino) {
		this.codDestino = codDestino;
	}
	
	public float getPesoAresta() {
		return pesoAresta;
	}

	public void setPesoAresta(float pesoAresta) {
		this.pesoAresta = pesoAresta;
	}

	public float getDistancia() {
		return distancia;
	}

	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}
	
	public void setEstante(String estanteOrigem) {
		this.origem = estanteOrigem;	
	} 
	
	public String getEstante() {
		return origem;
	}
	
	public void setEstanteDestino(String estanteDestino) {
		this.estanteDestino = estanteDestino;	
	} 
	
	public String getEstanteDestino() {
		return estanteDestino;
	}
	
}
