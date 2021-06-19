package fr.com.nfa019.restaurant.dto;

import java.util.List;
import java.util.stream.Collectors;

import fr.com.nfa019.restaurant.modele.Produit;

public class ProduitDTO {

	private Long id;
	private String nom;
	private Integer dureeDeConservationEnJours;
	private CategorieDTO categorie;

	public ProduitDTO(Produit produit) {
		this.id = produit.getId();
		this.nom = produit.getNom();
		this.dureeDeConservationEnJours = produit.getDureeDeConservationEnJours();
		this.categorie = new CategorieDTO(produit.getCategorie());
	}

	public static List<ProduitDTO> converter(List<Produit> produits) {
		return produits.stream().map(ProduitDTO::new).collect(Collectors.toList());
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

	public Integer getDureeDeConservationEnJours() {
		return dureeDeConservationEnJours;
	}

	public void setDureeDeConservationEnJours(Integer dureeDeConservationEnJours) {
		this.dureeDeConservationEnJours = dureeDeConservationEnJours;
	}

	public CategorieDTO getCategorie() {
		return categorie;
	}

	public void setCategorie(CategorieDTO categorie) {
		this.categorie = categorie;
	}

}
