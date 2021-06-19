package fr.com.nfa019.restaurant.modele;

import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name = "stock_produit")
public class StockProduit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	private Integer identifiantStock;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "produit_id")
	private Produit produit;

	@Column(name = "date_dinsertion")
	private LocalDateTime dataDInsertion;
	
	private Integer quantite;
	
	@Enumerated(EnumType.STRING)
	private Status status;

	public StockProduit() {

	}

	public StockProduit(Status status, Integer quantite) {
//		this.setIdentifiantStock(identifiantStock);
		this.status = status;
		this.setQuantite(quantite);
		// create a clock
		ZoneId zid = ZoneId.of("Europe/Paris");
		this.dataDInsertion = LocalDateTime.now(zid);
	}

	public StockProduit(Status status) {
		this.status = status;

		// create a clock
		ZoneId zid = ZoneId.of("Europe/Paris");
		this.dataDInsertion = LocalDateTime.now(zid);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
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
