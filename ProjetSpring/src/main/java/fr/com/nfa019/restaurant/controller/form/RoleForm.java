package fr.com.nfa019.restaurant.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import fr.com.nfa019.restaurant.modele.Role;
import fr.com.nfa019.restaurant.repository.RoleRepository;

public class RoleForm {

	@NotNull
	@NotEmpty
	private String nom;

	public Role converter() {
		Role role = new Role(nom);
		return role;
	}

	public Role update(Long id, RoleRepository roleRepository) {
		Role role = roleRepository.getOne(id);
		role.setNom(this.getNom());

		return role;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
