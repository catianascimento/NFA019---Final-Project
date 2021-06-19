package fr.com.nfa019.restaurant.controller;

import java.util.List;
import java.util.Optional;

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

import fr.com.nfa019.restaurant.controller.form.CategorieForm;
import fr.com.nfa019.restaurant.dto.CategorieDTO;
import fr.com.nfa019.restaurant.modele.Categorie;
import fr.com.nfa019.restaurant.modele.Historique;
import fr.com.nfa019.restaurant.repository.CategorieRepository;
import fr.com.nfa019.restaurant.repository.HistoriqueRepository;
import fr.com.nfa019.restaurant.repository.UtilisateurRepository;

@RestController
@RequestMapping("/categories")
public class CategorieController {

	@Autowired
	private CategorieRepository categorieRepository;
	
	@Autowired
	private HistoriqueRepository historiqueRepository;
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@GetMapping
	public List<CategorieDTO> lister(){
		List<Categorie> categories = categorieRepository.findAll();
		
		return CategorieDTO.converter(categories);
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<CategorieDTO> save(@RequestBody @Valid CategorieForm form) {
		Categorie categorie = form.converter();
		categorieRepository.save(categorie);
		saveLog("save categorie "+ categorie.getId());
		return ResponseEntity.ok(new CategorieDTO(categorie));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategorieDTO> get(@PathVariable Long id) {
		Optional<Categorie> categorie = categorieRepository.findById(id);
		if (categorie.isPresent()) {
			return ResponseEntity.ok(new CategorieDTO(categorie.get()));
		}

		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<CategorieDTO> update(@PathVariable Long id, @RequestBody @Valid CategorieForm form) {
		Optional<Categorie> optional = categorieRepository.findById(id);
		if (optional.isPresent()) {
			Categorie categorie = form.update(id, categorieRepository);
			saveLog("update categorie "+ categorie.getId());
			return ResponseEntity.ok(new CategorieDTO(categorie));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Categorie> optional = categorieRepository.findById(id);
		if (optional.isPresent()) {
			categorieRepository.deleteById(id);
			saveLog("delete categorie "+ id);
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
