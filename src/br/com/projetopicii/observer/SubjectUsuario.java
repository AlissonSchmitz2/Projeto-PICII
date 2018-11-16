package br.com.projetopicii.observer;

import br.com.projetopicii.model.bean.Usuario;

public interface SubjectUsuario {
	
	public void addObserver(ObserverUsuario o);

	public void removeObserver(ObserverUsuario o);

	public void notifyObservers(Usuario usuario);
	
}
