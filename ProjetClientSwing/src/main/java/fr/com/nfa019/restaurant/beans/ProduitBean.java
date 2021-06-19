package fr.com.nfa019.restaurant.beans;

public class ProduitBean {

	private Long id;
	private String nom;
	private Integer dureeDeConservationEnJours;
	private CategorieBean categorie;
	private Integer categorieId;

	public ProduitBean() {
	}

	private Long stockProduitId;

	public ProduitBean(Long id, String nom, Integer dureeDeConservationEnJours, CategorieBean categorie,
			Long stockProduitId) {
		this.id = id;
		this.nom = nom;
		this.dureeDeConservationEnJours = dureeDeConservationEnJours;
		this.categorie = categorie;
		this.stockProduitId = stockProduitId;
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

	public CategorieBean getCategorie() {
		return categorie;
	}

	public void setCategorie(CategorieBean categorie) {
		this.categorie = categorie;
	}

	public Long getStockProduitId() {
		return stockProduitId;
	}

	public void setStockProduitId(Long stockProduitId) {
		this.stockProduitId = stockProduitId;
	}

	public Integer getCategorieId() {
		return categorieId;
	}

	public void setCategorieId(Integer categorieId) {
		this.categorieId = categorieId;
	}
}
