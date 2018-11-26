package br.com.projetopicii.grafo;

public class Aresta {
	private int    codOrigin;
	private int    codDestino;
	private int    quantidadeLivrosAfim;
	private double  pesoAresta;
	private float  distancia;
	private String estanteOrigem;
	private String estanteDestino;
	
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
	
	public double getPesoAresta() {
		return pesoAresta;
	}

	public void setPesoAresta(double pesoAresta) {
		this.pesoAresta = pesoAresta;
	}

	public float getDistancia() {
		return distancia;
	}

	public void setDistancia(float distancia) {
		this.distancia = distancia;
	}
	
	public void setEstanteOrigem(String estanteOrigem) {
		this.estanteOrigem = estanteOrigem;	
	} 
	
	public String getEstanteOrigem() {
		return estanteOrigem;
	}
	
	public void setEstanteDestino(String estanteDestino) {
		this.estanteDestino = estanteDestino;	
	} 
	
	public String getEstanteDestino() {
		return estanteDestino;
	}

	public int getQuantidadeLivros() {
		return quantidadeLivrosAfim;
	}

	public void setQuantidadeLivros(int quantidadeLivros) {
		this.quantidadeLivrosAfim = quantidadeLivros;
	}
}
