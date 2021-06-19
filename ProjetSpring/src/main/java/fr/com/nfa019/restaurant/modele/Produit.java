package fr.com.nfa019.restaurant.modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "produit")
public class Produit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nom;
	private Integer dureeDeConservationEnJours;

	@ManyToOne
	private Categorie categorie;

	public Produit() {

	}

	public Produit(String nom, Integer dureeDeConservationEnJours) {
		this.nom = nom;
		this.dureeDeConservationEnJours = dureeDeConservationEnJours;
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

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public Long getCategorieId() {
		return this.getCategorie().getId();
	}

//	public StockProduit getStockProduit() {
//		return stockProduit;
//	}
//
//	public void setStockProduit(StockProduit stockProduit) {
//		this.stockProduit = stockProduit;
//	}

	public Integer getDureeDeConservationEnJours() {
		return dureeDeConservationEnJours;
	}

	public void setDureeDeConservationEnJours(Integer dureeDeConservationEnJours) {
		this.dureeDeConservationEnJours = dureeDeConservationEnJours;
	}

}
