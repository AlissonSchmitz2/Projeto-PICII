package br.com.projetopicii.grafo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import br.com.projetopicii.model.CaminhoBiblioteca;

public class Grafo {

	private List<Vertice> vertice = new ArrayList<Vertice>();
	private final int OO = 9999;
	
	public List<Vertice> getVertices() {
		return vertice;
	}

	public void setVertices(Vertice novoVertice) {
		vertice.add(novoVertice);
	}
	
	ArrayList<CaminhoBiblioteca> listCB = null;

	// HashMap que guardar vertices para controle de exceções
	HashMap<Integer, String> map = new HashMap<Integer, String>();

	public void montarGrafo(ArrayList<CaminhoBiblioteca> listCB) throws Exception {
		this.listCB = listCB;

		for (int i = 0; i < listCB.size(); i++) {

			Aresta aresta = new Aresta();
			Vertice vertice = new Vertice();

			aresta.setCodOrigin(listCB.get(i).getCodigoOrigem());
			aresta.setCodDestino(listCB.get(i).getCodigoDestino());
			aresta.setEstanteOrigem(listCB.get(i).getEstanteOrigem());
			aresta.setEstanteDestino(listCB.get(i).getEstanteDestino());
			aresta.setPesoAresta(listCB.get(i).getDistancia());
			aresta.setDistancia(OO);

			// Tratamento de excessões;

			vertice.setAresta(aresta);
			vertice.setCodigo(listCB.get(i).getCodigoOrigem());
			vertice.setDistancia(OO);
			vertice.setQuantidadeLivrosAfim(listCB.get(i).getQuantidadeLivrosAfim());
			
			// TODO:Implementar lógica para vértices inseridos sem ordem;

			/*
			 * int aux = i; for(int j = aux;j<listCM.size();j++) {
			 * if(listCM.get(i).getCodigoOrigem() == listCM.get(j).getCodigoOrigem()) {
			 * Aresta aresta1 = new Aresta();
			 * aresta1.setCodOrigin(listCM.get(j).getCodigoOrigem());
			 * aresta1.setCodDestino(listCM.get(j).getCodigoDestino());
			 * aresta1.setPesoAresta(listCM.get(j).getDistanciaKM());
			 * aresta1.setDistancia(OO); vertice.setAresta(aresta1); ++i; } }
			 */

			if (i != listCB.size() - 1) {
				while (listCB.get(i + 1).getCodigoOrigem() == listCB.get(i).getCodigoOrigem()) {
					Aresta aresta1 = new Aresta();
					aresta1.setCodOrigin(listCB.get(i + 1).getCodigoOrigem());
					aresta1.setCodDestino(listCB.get(i + 1).getCodigoDestino());
					aresta1.setEstanteOrigem(listCB.get(i + 1).getEstanteOrigem());
					aresta1.setEstanteDestino(listCB.get(i + 1).getEstanteDestino());
					aresta1.setPesoAresta(listCB.get(i + 1).getDistancia());
					aresta1.setDistancia(OO);
					vertice.setAresta(aresta1);
					++i;
					if (i == listCB.size() - 1) {
						break;
					}
				}
			}

			this.setVertices(vertice);
		}

		inserirChaveValor(vertice);
		percorrerGrafo();
	}

	public void inserirChaveValor(List<Vertice> v) throws Exception {
		for (int i = 0; i < v.size(); i++) {
			for (int j = 0; j < v.get(i).getAresta().size(); j++) {

				// Tratamento de exceções
				// Verifica se contem cidade iguais com códigos distintos
				if (map.containsValue(v.get(i).getAresta().get(j).getEstanteOrigem())) {
					Integer keyCidadeOrigem = getKeyByValue(map, v.get(i).getAresta().get(j).getEstanteOrigem());

					if (!(keyCidadeOrigem).equals(v.get(i).getAresta().get(j).getCodOrigin())) {
						throw new Exception("Não foi possível percorrer a menor rota, a cidade "
								+ v.get(i).getAresta().get(j).getEstanteOrigem() + " contém"
								+ " dois ou mais códigos diferentes associada a ela.");
					}
				}

				if (map.containsValue(v.get(i).getAresta().get(j).getEstanteDestino())) {
					Integer keyCidadeDestino = getKeyByValue(map, v.get(i).getAresta().get(j).getEstanteDestino());

					if (!(keyCidadeDestino).equals(v.get(i).getAresta().get(j).getCodDestino())) {
						throw new Exception("Não foi possível percorrer a menor rota, a cidade "
								+ v.get(i).getAresta().get(j).getEstanteDestino() + " contém"
								+ " dois ou mais códigos diferentes associada a ela.");
					}
				}

				map.put(v.get(i).getAresta().get(j).getCodOrigin(), v.get(i).getAresta().get(j).getEstanteOrigem());
				map.put(v.get(i).getAresta().get(j).getCodDestino(), v.get(i).getAresta().get(j).getEstanteDestino());
			}
		}
	}

	// Recuperar a key a partir de um valor no HashMap.
	public static <T, E> T getKeyByValue(HashMap<T, E> map, E value) {

		for (Entry<T, E> entry : map.entrySet()) {

			if (value.equals(entry.getValue())) {
				return entry.getKey();
			}
		}

		return null;
	}
	
	public void percorrerGrafo() {
		for(int i = 0;i<vertice.size();i++) {
			System.out.println(vertice.get(i).getAresta().get(0).getEstanteOrigem() + "  " + vertice.get(i).getCodigo());
			
			for(int j = 0;j<vertice.get(i).getAresta().size();j++) {
				System.out.println(vertice.get(i).getAresta().get(j).getEstanteOrigem() + "----"  + vertice.get(i).getAresta().get(j).getEstanteDestino());
			}
			System.out.println("------------------\n\n");
		}
	}
}