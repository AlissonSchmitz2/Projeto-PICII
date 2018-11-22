package br.com.projetopicii.model.bean;

public class Usuario {
	public static String ADMINISTRADOR = "Administrador";
	public static String CONVIDADO = "Convidado";
	
	private Integer id;
	private String senha;
	private String login;
	
	public Usuario() {
	}
	
	public Usuario(Integer id, String login, String senha, String perfil) {
		this.id = id;
		this.senha = senha;
		this.login = login;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}	
	
}