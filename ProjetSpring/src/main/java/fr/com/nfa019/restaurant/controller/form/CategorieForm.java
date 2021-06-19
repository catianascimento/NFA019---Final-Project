package fr.com.nfa019.restaurant.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import fr.com.nfa019.restaurant.modele.Categorie;
import fr.com.nfa019.restaurant.repository.CategorieRepository;

public class CategorieForm {

	@NotNull
	@NotEmpty
	private String nom;

	public Categorie converter() {
		Categorie categorie = new Categorie(nom);
		return categorie;
	}

	public Categorie update(Long id, CategorieRepository categorieRepository) {
		Categorie categorie = categorieRepository.getOne(id);

		categorie.setNom(this.getNom());

		return categorie;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
