package br.com.projetopicii.observer;

import br.com.projetopicii.model.bean.Livro;

public interface SubjectLivro {
	
	public void addObserver(ObserverLivro o);

	public void removeObserver(ObserverLivro o);

	public void notifyObservers(Livro livro);
	
}
