package fr.com.nfa019.restaurant.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import fr.com.nfa019.restaurant.modele.Status;
import fr.com.nfa019.restaurant.modele.StockProduit;

public class StockProduitDTO {

	private Long id;
//	private Integer identifiantStock;
	private Long produitId;
	private LocalDateTime dataDInsertion;
	private Integer quantite;
	private Status status;

	public StockProduitDTO(StockProduit stockProduit) {
		this.id = stockProduit.getId();
		this.produitId = stockProduit.getProduit().getId();
		this.dataDInsertion = stockProduit.getDataDInsertion();
		this.setQuantite(stockProduit.getQuantite());
		this.status = stockProduit.getStatus();
//		this.identifiantStock = stockProduit.getIdentifiantStock();
	}

	public static List<StockProduitDTO> converter(List<StockProduit> stockProduits) {
		return stockProduits.stream().map(StockProduitDTO::new).collect(Collectors.toList());
	}

	public Long getProduitId() {
		return produitId;
	}

	public void setProduitId(Long produitId) {
		this.produitId = produitId;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantite() {
		return quantite;
	}

	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}

//	public Integer getIdentifiantStock() {
//		return identifiantStock;
//	}
//
//	public void setIdentifiantStock(Integer identifiantStock) {
//		this.identifiantStock = identifiantStock;
//	}

}
