package fr.com.nfa019.restaurant.beans;

import org.springframework.security.core.GrantedAuthority;

public class RoleBean implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nom;
	private String authority;

	@Override
	public String getAuthority() {
		return this.authority;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
}
