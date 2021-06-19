package fr.com.nfa019.restaurant.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import fr.com.nfa019.restaurant.modele.Categorie;
import fr.com.nfa019.restaurant.modele.Produit;
import fr.com.nfa019.restaurant.repository.CategorieRepository;
import fr.com.nfa019.restaurant.repository.ProduitRepository;
import fr.com.nfa019.restaurant.repository.StockProduitRepository;

public class ProduitForm {

	@NotNull
	@NotEmpty
	private String nom;
	private Integer dureeDeConservationEnJours;
	private Long categorieId;
	private Long stockProduitId;

	public Produit converter(CategorieRepository categorieRepository, StockProduitRepository stockProduitRepository) {
		Produit produit = new Produit(nom, dureeDeConservationEnJours);
		Optional<Categorie> categorie = categorieRepository.findById(categorieId);
		if (categorie.isPresent()) {
			produit.setCategorie(categorie.get());
		}
//		if (stockProduitId != null) {
//			Optional<StockProduit> stockProduit = stockProduitRepository.findById(stockProduitId);
////			if (stockProduit.isPresent()) {
////				produit.setStockProduit(stockProduit.get());
////			}
//		}
		return produit;
	}

	public Produit update(Long id, ProduitRepository produitRepository, CategorieRepository categorieRepository,
			StockProduitRepository stockProduitRepository) {
		Produit produit = produitRepository.getOne(id);

		produit.setNom(this.getNom());
		produit.setDureeDeConservationEnJours(this.getDureeDeConservationEnJours());

		Optional<Categorie> categorie = categorieRepository.findById(this.categorieId);
		if (categorie.isPresent()) {
			produit.setCategorie(categorie.get());
		}
//		if (stockProduitId != null) {
//			Optional<StockProduit> stockProduit = stockProduitRepository.findById(stockProduitId);
//			if (stockProduit.isPresent()) {
//				produit.setStockProduit(stockProduit.get());
//			}
//		}

		return produit;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Long getCategorieId() {
		return categorieId;
	}

	public void setCategorieId(Long categorieId) {
		this.categorieId = categorieId;
	}

	public Integer getDureeDeConservationEnJours() {
		return dureeDeConservationEnJours;
	}

	public void setDureeDeConservationEnJours(Integer dureeDeConservationEnJours) {
		this.dureeDeConservationEnJours = dureeDeConservationEnJours;
	}

	public Long getStockProduitId() {
		return stockProduitId;
	}

	public void setStockProduitId(Long stockProduitId) {
		this.stockProduitId = stockProduitId;
	}

}
