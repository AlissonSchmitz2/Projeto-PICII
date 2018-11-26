package br.com.projetopicii.model.bean;

import java.util.ArrayList;

import br.com.projetopicii.model.dao.EstanteDao;

public class BibliotecaDao {
	public static void main(String[] args) {
		EstanteDao eD = new EstanteDao();
		
		ArrayList<Estante> estantes = new ArrayList<Estante>();
		estantes = eD.pegarArrayEstantes(false);
		
		for(Estante e:estantes) {
			System.out.println(e.getNome());
		}
	}
}
