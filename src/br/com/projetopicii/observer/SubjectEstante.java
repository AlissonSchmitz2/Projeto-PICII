package br.com.projetopicii.observer;

import br.com.projetopicii.model.bean.Estante;

public interface SubjectEstante {
	
	public void addObserver(ObserverEstante o);

	public void removeObserver(ObserverEstante o);

	public void notifyObservers(Estante estante);
	
}
