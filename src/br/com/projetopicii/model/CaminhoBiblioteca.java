package br.com.projetopicii.model;

public class CaminhoBiblioteca {

	private int codigoOrigem;
	private int codigoDestino;
	private String estanteOrigem;
	private String estanteDestino;
	private double distancia;
	private int quantidadeLivrosAfim;
	
	public int getCodigoOrigem() {
		return codigoOrigem;
	}
	public void setCodigoOrigem(int codigoOrigem) {
		this.codigoOrigem = codigoOrigem;
	}
	
	public int getCodigoDestino() {
		return codigoDestino;
	}
	public void setCodigoDestino(int codigoDestino) {
		this.codigoDestino = codigoDestino;
	}
	
	public String getEstanteOrigem() {
		return estanteOrigem;
	}
	public void setEstanteOrigem(String estanteOrigem) {
		this.estanteOrigem = estanteOrigem;
	}
	
	public String getEstanteDestino() {
		return estanteDestino;
	}
	public void setEstanteDestino(String estanteDestino) {
		this.estanteDestino = estanteDestino;
	}
	
	public double getDistancia() {
		return distancia;
	}
	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}
	
	public int getQuantidadeLivrosAfim() {
		return quantidadeLivrosAfim;
	}
	public void setQuantidadeLivrosAfim(int quantidadeLivrosAfim) {
		this.quantidadeLivrosAfim = quantidadeLivrosAfim;
	}		
}
