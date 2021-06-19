package fr.com.nfa019.restaurant.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.com.nfa019.restaurant.controller.form.HistoriqueForm;
import fr.com.nfa019.restaurant.dto.HistoriqueDTO;
import fr.com.nfa019.restaurant.modele.Historique;
import fr.com.nfa019.restaurant.repository.HistoriqueRepository;
import fr.com.nfa019.restaurant.repository.UtilisateurRepository;

@RestController
@RequestMapping("/historiques")
public class HistoriqueController {

	@Autowired
	private HistoriqueRepository historiqueRepository;
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@GetMapping
	public List<HistoriqueDTO> lister() {
		List<Historique> historiques = historiqueRepository.findAll();
		return HistoriqueDTO.converter(historiques);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<HistoriqueDTO> save(@RequestBody @Valid HistoriqueForm form) {
		Historique historique = form.converter(utilisateurRepository);
		historiqueRepository.save(historique);
		return ResponseEntity.ok(new HistoriqueDTO(historique));
	}

	@GetMapping("/{id}")
	public ResponseEntity<HistoriqueDTO> get(@PathVariable Long id) {
		Optional<Historique> historique = historiqueRepository.findById(id);
		if (historique.isPresent()) {
			return ResponseEntity.ok(new HistoriqueDTO(historique.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<HistoriqueDTO> update(@PathVariable Long id, @RequestBody @Valid HistoriqueForm form) {
		Optional<Historique> optional = historiqueRepository.findById(id);
		if (optional.isPresent()) {
			Historique historique = form.update(id, historiqueRepository, utilisateurRepository);
			return ResponseEntity.ok(new HistoriqueDTO(historique));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Historique> optional = historiqueRepository.findById(id);
		if (optional.isPresent()) {
			historiqueRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

}
