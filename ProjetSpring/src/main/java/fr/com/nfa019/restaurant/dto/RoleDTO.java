package fr.com.nfa019.restaurant.dto;

import java.util.List;
import java.util.stream.Collectors;

import fr.com.nfa019.restaurant.modele.Role;

public class RoleDTO {

	private Long id;
	private String nom;

	public RoleDTO(Role role) {
		this.id = role.getId();
		this.nom = role.getNom();
	}

	public static List<RoleDTO> converter(List<Role> roles) {
		return roles.stream().map(RoleDTO::new).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
