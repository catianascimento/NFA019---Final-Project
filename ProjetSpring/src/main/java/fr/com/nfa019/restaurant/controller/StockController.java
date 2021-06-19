package fr.com.nfa019.restaurant.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.com.nfa019.restaurant.controller.form.StockProduitForm;
import fr.com.nfa019.restaurant.dto.ProduitDTO;
import fr.com.nfa019.restaurant.dto.StockProduitDTO;
import fr.com.nfa019.restaurant.modele.Historique;
import fr.com.nfa019.restaurant.modele.Produit;
import fr.com.nfa019.restaurant.modele.StockProduit;
import fr.com.nfa019.restaurant.repository.HistoriqueRepository;
import fr.com.nfa019.restaurant.repository.ProduitRepository;
import fr.com.nfa019.restaurant.repository.StockProduitRepository;
import fr.com.nfa019.restaurant.repository.UtilisateurRepository;

@RestController
@RequestMapping("/stocks")
public class StockController {

	@Autowired
	private StockProduitRepository stockProduitRepository;

	@Autowired
	private ProduitRepository produitRepository;

	@Autowired
	private HistoriqueRepository historiqueRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@GetMapping
	public List<StockProduitDTO> lister() {
		List<StockProduit> stocks = stockProduitRepository.findAll();
		return StockProduitDTO.converter(stocks);
	}

	@GetMapping("/expireds")
	public List<StockProduitDTO> listerOnlyExpiredToday() {
		List<StockProduit> stocks = stockProduitRepository.findAll();

		stocks = stocks.stream().filter(s -> s.getDataDInsertion().plusDays(s.getProduit().getDureeDeConservationEnJours())
				.isBefore(LocalDateTime.now())).collect(Collectors.toList());
		return StockProduitDTO.converter(stocks);
	}

//	@GetMapping("/{id}/stock-produit")
//	public List<StockProduitDTO> listerProduits(@PathVariable("id") Long stockId) {
//		List<StockProduit> stockProduits = stockProduitRepository.findByStockId(stockId);
//		return StockProduitDTO.converter(stockProduits);
//	}

	@GetMapping("/{id}/produits")
	public List<ProduitDTO> listerProduits(@PathVariable("id") String nomStock) {
		List<StockProduit> stockProduits = stockProduitRepository.findById(nomStock);

		List<Produit> produits = new ArrayList<Produit>();

		for (StockProduit stockProduit : stockProduits) {
			produits.add(stockProduit.getProduit());
		}
//		produitRepository.findAllById(stockProduits.);
		return ProduitDTO.converter(produits);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<StockProduitDTO> save(@RequestBody @Valid StockProduitForm form) {
		StockProduit stockProduit = form.converter(produitRepository);
		stockProduitRepository.save(stockProduit);
		saveLog("save stock " + stockProduit.getId());
		return ResponseEntity.ok(new StockProduitDTO(stockProduit));
	}

	@PostMapping("/{id}/stock-produit")
	@Transactional
	public ResponseEntity<StockProduitDTO> saveStockProduit(@RequestBody @Valid StockProduitForm form,
			@PathVariable("id") Integer stockId) {
//		form.setIdentifiantStock(refrigerateurId);
		StockProduit stockProduit = form.converter(produitRepository);
		stockProduitRepository.save(stockProduit);
		saveLog("save stock " + stockProduit.getId());
		return ResponseEntity.ok(new StockProduitDTO(stockProduit));
	}

	@GetMapping("/{id}")
	public ResponseEntity<StockProduitDTO> get(@PathVariable Long id) {
		Optional<StockProduit> stock = stockProduitRepository.findById(id);
		if (stock.isPresent()) {
			return ResponseEntity.ok(new StockProduitDTO(stock.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<StockProduitDTO> update(@PathVariable Long id, @RequestBody @Valid StockProduitForm form) {
		Optional<StockProduit> optional = stockProduitRepository.findById(id);
		if (optional.isPresent()) {
			StockProduit stock = form.update(id, stockProduitRepository, produitRepository);
			saveLog("update stock - produit " + stock.getId());
			return ResponseEntity.ok(new StockProduitDTO(stock));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<StockProduit> optional = stockProduitRepository.findById(id);
		if (optional.isPresent()) {
			stockProduitRepository.deleteById(id);
			saveLog("delete stock - produit " + id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	private void saveLog(String action) {
		Historique historique = new Historique();
		historique.setAction(action);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		historique.setUtilisateur(utilisateurRepository.findByLogin(auth.getName()).get());
		historiqueRepository.save(historique);
	}

}
