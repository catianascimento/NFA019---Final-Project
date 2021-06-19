package fr.com.nfa019.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.com.nfa019.restaurant.modele.StockProduit;

public interface StockProduitRepository extends JpaRepository<StockProduit, Long> {

	List<StockProduit> findById(String nomStock);

//	List<StockProduit> findByIdentifiantStock(String nomStock);
}
