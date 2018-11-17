package br.com.projetopicii.threads;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.projetopicii.model.EstanteBibliotecaEdicao;

public class ThreadSubirEstantesEdicao {

	//Dados primários.
		private HashMap<String, EstanteBibliotecaEdicao> listaDeEstantes;
		
		//Auxiliares.
		private ArrayList<Integer> coordenadasAnteriores = new ArrayList<>();
		private ArrayList<String> arrayReferencias = new ArrayList<>();
		
		public ThreadSubirEstantesEdicao(HashMap<String, EstanteBibliotecaEdicao> listaDeEstantes, String[] referencias) {
			
			this.listaDeEstantes = listaDeEstantes;
			
			for(int i = 0; i < referencias.length; i++) {
				arrayReferencias.add(referencias[i]);
			}
			
			startThread();	
		}
		
		private void startThread() {
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					try {
						while (!Thread.currentThread().isInterrupted()) {
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								throw e;
							}					
							
							for(int i = 0; i < arrayReferencias.size(); i++) {						
								
								if(listaDeEstantes.get(arrayReferencias.get(i)) != null) {
									
									//se for false, quer dizer que a estante foi removida do painel de estantes.
									if(!listaDeEstantes.get(arrayReferencias.get(i)).getAtivarMovimentoMouse()) {
																		
										//verifica se é a ultima estante do painel. Se não for, sobe.
										if(i + 1 >= arrayReferencias.size()) {
											
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
					catch (InterruptedException ex) {
						System.out.println(Thread.currentThread().getName()+" parou. Recriando ... ");
						startThread();
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
					
					coordenadasAnteriores.add(listaDeEstantes.get(arrayReferencias.get(estanteAcima)).getCoordenadaY());
						
					estanteAcima++;
				}
			}
						
			int indiceCoordenadas = 0;
			
			for(int j = estanteAbaixo; j < arrayReferencias.size(); j++) {				
					
				if(listaDeEstantes.get(arrayReferencias.get(j)) != null ) {
										
					listaDeEstantes.get(arrayReferencias.get(j)).setCoordenadaY(coordenadasAnteriores.get(indiceCoordenadas));
						
					indiceCoordenadas++;
				}
			}
							
			coordenadasAnteriores.clear();
			listaDeEstantes.remove(arrayReferencias.get(indiceEstanteMovida));
			arrayReferencias.remove(indiceEstanteMovida);
			
		}
}
