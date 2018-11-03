package br.com.projetopicii.threads;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.projetopicii.model.EstanteBiblioteca;

public class ThreadSubirListaEstante {
	
	//Dados primários.
	private HashMap<String, EstanteBiblioteca> listaDeEstantes;
	private String[] referencias;
	
	//Auxiliares.
	private ArrayList<Integer> coordenadasAnteriores = new ArrayList<>();
	private ArrayList<String> arrayReferencias = new ArrayList<>();
	
	public ThreadSubirListaEstante(HashMap<String, EstanteBiblioteca> listaDeEstantes, String[] referencias) {
		
		this.listaDeEstantes = listaDeEstantes;
		this.referencias = referencias;
		
		for(int i = 0; i < referencias.length; i++) {
			arrayReferencias.add(referencias[i]);
		}
		
		startThread();	
	}
	
	private void startThread() {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				while (!Thread.currentThread().isInterrupted()) {
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();;
					}
										
					
					for(int i = 0; i < arrayReferencias.size(); i++) {						
						
						if(listaDeEstantes.get(arrayReferencias.get(i)) != null) {
							
							//se for false, quer dizer que a estante foi removida do painel de estantes.
							if(!listaDeEstantes.get(arrayReferencias.get(i)).getAtivarMovimentoMouse()) {
								
								//System.out.println("Estante removida: " + arrayReferencias.get(i));
								
								//verifica se é a ultima estante do painel. Se não for, sobe.
								if(i + 1 >= arrayReferencias.size()) {
									
									//System.out.println("entrou " + listaDeEstantes.get(arrayReferencias.get(i)) + " " + arrayReferencias.get(i));
									listaDeEstantes.remove(arrayReferencias.get(i));
									arrayReferencias.remove(i);
								} else {
									subirEstantes(i);
								}
							}
						}
					}
					
				}
			}
		},"MoverEstantesParaCima").start();		
		
	}
	
	//Move as estantes para cima.
	private void subirEstantes(int indiceEstanteMovida) {
					
		int estanteAcima = indiceEstanteMovida;
		int estanteAbaixo = indiceEstanteMovida +1;
		for(int j = estanteAbaixo; j < arrayReferencias.size(); j++) {
				
		
			if(listaDeEstantes.get(arrayReferencias.get(j)) != null && listaDeEstantes.get(arrayReferencias.get(estanteAcima)) != null) {
					
				listaDeEstantes.get(arrayReferencias.get(j)).setBounds(15, listaDeEstantes.get(arrayReferencias.get(estanteAcima)).getCoordenadaY(), 110, 40);
				System.out.println("Estante " + arrayReferencias.get(j) +": movida para: " + listaDeEstantes.get(arrayReferencias.get(estanteAcima)).getCoordenadaY());
					
				coordenadasAnteriores.add(listaDeEstantes.get(arrayReferencias.get(estanteAcima)).getCoordenadaY());
					
				estanteAcima++;
			}
		}
					
		int indiceCoordenadas = 0;
		for(int j = estanteAbaixo; j < arrayReferencias.size(); j++) {
				
				
			if(listaDeEstantes.get(arrayReferencias.get(j)) != null ) {
									
				//System.out.println("Coordenada da estante: " + arrayReferencias.get(j) + " alterada para: " + coordenadasAnteriores.get(z) + "----------------\n");
				listaDeEstantes.get(arrayReferencias.get(j)).setCoordenadaY(coordenadasAnteriores.get(indiceCoordenadas));
					
				indiceCoordenadas++;
			}
		}
						
		coordenadasAnteriores.clear();
		listaDeEstantes.remove(referencias[indiceEstanteMovida]);
		arrayReferencias.remove(indiceEstanteMovida);		
		
	}
}
