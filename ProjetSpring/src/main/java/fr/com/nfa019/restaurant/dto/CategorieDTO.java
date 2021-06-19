package fr.com.nfa019.restaurant.dto;

import java.util.List;
import java.util.stream.Collectors;

import fr.com.nfa019.restaurant.modele.Categorie;

public class CategorieDTO {

	private Long id;
	private String nom;

	public CategorieDTO(Categorie categorie) {
		this.id = categorie.getId();
		this.nom = categorie.getNom();
	}

	public static List<CategorieDTO> converter(List<Categorie> categories) {
		return categories.stream().map(CategorieDTO::new).collect(Collectors.toList());
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
