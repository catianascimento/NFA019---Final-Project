package fr.com.nfa019.restaurant.controller.form;

import java.time.LocalDateTime;
import java.util.Optional;

import fr.com.nfa019.restaurant.modele.Produit;
import fr.com.nfa019.restaurant.modele.Status;
import fr.com.nfa019.restaurant.modele.StockProduit;
import fr.com.nfa019.restaurant.repository.ProduitRepository;
import fr.com.nfa019.restaurant.repository.StockProduitRepository;

public class StockProduitForm {

//	private Integer identifiantStock;
	private LocalDateTime dataDInsertion;
	private Status status;
	private Integer quantite;
	private Long produitId;

	public StockProduit converter(ProduitRepository produitRepository) {
		StockProduit stockProduit = new StockProduit(status, quantite);

		Optional<Produit> produit = produitRepository.findById(produitId);
		if (produit.isPresent()) {
			stockProduit.setProduit(produit.get());
		}

		return stockProduit;
	}

	public StockProduit update(Long stockProduitId, StockProduitRepository stockProduitRepository,
			ProduitRepository produitRepository) {
		StockProduit stockProduit = stockProduitRepository.getOne(stockProduitId);

		stockProduit.setStatus(this.getStatus());
//		stockProduit.setIdentifiantStock(this.getIdentifiantStock());
		stockProduit.setQuantite(this.getQuantite());

		Optional<Produit> produit = produitRepository.findById(produitId);

		if (produit.isPresent()) {
			stockProduit.setProduit(produit.get());
		}

		return stockProduit;
	}

	public LocalDateTime getDataDInsertion() {
		return dataDInsertion;
	}

	public void setDataDInsertion(LocalDateTime dataDInsertion) {
		this.dataDInsertion = dataDInsertion;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getProduitId() {
		return produitId;
	}

	public void setProduitId(Long produitId) {
		this.produitId = produitId;
	}
//
//	public Integer getIdentifiantStock() {
//		return identifiantStock;
//	}
//
//	public void setIdentifiantStock(Integer identifiantStock) {
//		this.identifiantStock = identifiantStock;
//	}

	public Integer getQuantite() {
		return quantite;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

}
