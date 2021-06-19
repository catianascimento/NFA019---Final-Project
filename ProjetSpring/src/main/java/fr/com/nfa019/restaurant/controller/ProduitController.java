package fr.com.nfa019.restaurant.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.com.nfa019.restaurant.controller.form.ProduitForm;
import fr.com.nfa019.restaurant.dto.ProduitDTO;
import fr.com.nfa019.restaurant.modele.Historique;
import fr.com.nfa019.restaurant.modele.Produit;
import fr.com.nfa019.restaurant.modele.StockProduit;
import fr.com.nfa019.restaurant.repository.CategorieRepository;
import fr.com.nfa019.restaurant.repository.HistoriqueRepository;
import fr.com.nfa019.restaurant.repository.ProduitRepository;
import fr.com.nfa019.restaurant.repository.StockProduitRepository;
import fr.com.nfa019.restaurant.repository.UtilisateurRepository;

@RestController
@RequestMapping("/produits")
public class ProduitController {

	@Autowired
	private ProduitRepository produitRepository;

	@Autowired
	private CategorieRepository categorieRepository;

	@Autowired
	private StockProduitRepository stockProduitRepository;

	@Autowired
	private HistoriqueRepository historiqueRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@GetMapping
	public List<ProduitDTO> lister() {
		List<Produit> produits = produitRepository.findAll();
		return ProduitDTO.converter(produits);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<ProduitDTO> save(@RequestBody @Valid ProduitForm form) {
		Produit produit = form.converter(categorieRepository, stockProduitRepository);
		produitRepository.save(produit);
		saveLog("save produit " + produit.getId());
		return ResponseEntity.ok(new ProduitDTO(produit));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProduitDTO> get(@PathVariable Long id) {
		Optional<Produit> produit = produitRepository.findById(id);
		if (produit.isPresent()) {
			var dto = new ProduitDTO(produit.get());
			return ResponseEntity.ok(dto);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/produits-disponibles")
	public List<ProduitDTO> getDisponibles() {
		List<StockProduit> stockProduits = stockProduitRepository.findAll();
		List<Produit> produitsNotAvailables = stockProduits.stream().map(StockProduit::getProduit)
				.collect(Collectors.toList());

		List<Produit> produits = produitRepository.findAll().stream().filter(e -> !produitsNotAvailables.contains(e))
				.collect(Collectors.toList());
		return ProduitDTO.converter(produits);
	}

	@GetMapping("/produits-filtered")
	public List<ProduitDTO> getByIds(@RequestParam List<Long> ids) {
		List<Produit> produits = produitRepository.findAllById(ids);
		return ProduitDTO.converter(produits);
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<ProduitDTO> update(@PathVariable Long id, @RequestBody @Valid ProduitForm form) {
		Optional<Produit> optional = produitRepository.findById(id);
		if (optional.isPresent()) {
			Produit produit = form.update(id, produitRepository, categorieRepository, stockProduitRepository);
			saveLog("update produit " + produit.getId());
			return ResponseEntity.ok(new ProduitDTO(produit));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Produit> optional = produitRepository.findById(id);
		if (optional.isPresent()) {
			produitRepository.deleteById(id);
			saveLog("delete produit " + id);
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
