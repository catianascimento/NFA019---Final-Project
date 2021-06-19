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

import fr.com.nfa019.restaurant.controller.form.RefrigerateurForm;
import fr.com.nfa019.restaurant.controller.form.RefrigerateurTemperatureForm;
import fr.com.nfa019.restaurant.dto.RefrigerateurDTO;
import fr.com.nfa019.restaurant.dto.RefrigerateurTemperatureDTO;
import fr.com.nfa019.restaurant.modele.Historique;
import fr.com.nfa019.restaurant.modele.Refrigerateur;
import fr.com.nfa019.restaurant.modele.RefrigerateurTemperature;
import fr.com.nfa019.restaurant.repository.HistoriqueRepository;
import fr.com.nfa019.restaurant.repository.RefrigerateurRepository;
import fr.com.nfa019.restaurant.repository.RefrigerateurTemperatureRepository;
import fr.com.nfa019.restaurant.repository.UtilisateurRepository;

@RestController
@RequestMapping("/refrigerateurs")
public class RefrigerateurController {

	@Autowired
	private RefrigerateurRepository refrigerateurRepository;

	@Autowired
	private RefrigerateurTemperatureRepository refrigerateurTemperatureRepository;

	@Autowired
	private HistoriqueRepository historiqueRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@GetMapping
	public List<RefrigerateurDTO> lister() {
		List<Refrigerateur> refrigerateurs = refrigerateurRepository.findAll();
		return RefrigerateurDTO.converter(refrigerateurs);
	}
	
	@GetMapping("/{id}/refrigerateur-temperature")
	public List<RefrigerateurTemperatureDTO> listerTemperatures(@PathVariable("id") Long refrigerateurId) {
		List<RefrigerateurTemperature> temperatures = refrigerateurTemperatureRepository.findByRefrigerateurId(refrigerateurId);
		return RefrigerateurTemperatureDTO.converter(temperatures);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<RefrigerateurDTO> save(@RequestBody @Valid RefrigerateurForm form) {
		Refrigerateur refrigerateur = form.converter(refrigerateurTemperatureRepository);
		refrigerateurRepository.save(refrigerateur);
		saveLog("save refrigerateur " + refrigerateur.getId());
		return ResponseEntity.ok(new RefrigerateurDTO(refrigerateur));
	}

	@PostMapping("/{id}/refrigerateur-temperature")
	@Transactional
	public ResponseEntity<RefrigerateurTemperatureDTO> saveRefrigerateurTemperature(
			@RequestBody @Valid RefrigerateurTemperatureForm form, @PathVariable("id") Long refrigerateurId) {
		form.setRefrigerateurId(refrigerateurId);
		RefrigerateurTemperature refrigerateurTemperature = form.converter(refrigerateurRepository);
		refrigerateurTemperatureRepository.save(refrigerateurTemperature);
		saveLog("save refrigerateur-temperature" + refrigerateurTemperature.getId());
		return ResponseEntity.ok(new RefrigerateurTemperatureDTO(refrigerateurTemperature));
	}

	@GetMapping("/{id}")
	public ResponseEntity<RefrigerateurDTO> get(@PathVariable Long id) {
		Optional<Refrigerateur> Refrigerateur = refrigerateurRepository.findById(id);
		if (Refrigerateur.isPresent()) {
			return ResponseEntity.ok(new RefrigerateurDTO(Refrigerateur.get()));
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<RefrigerateurDTO> update(@PathVariable Long id, @RequestBody @Valid RefrigerateurForm form) {
		Optional<Refrigerateur> optional = refrigerateurRepository.findById(id);
		if (optional.isPresent()) {
			Refrigerateur refrigerateur = form.update(id, refrigerateurRepository, refrigerateurTemperatureRepository);
			saveLog("update refrigerateur " + refrigerateur.getId());
			return ResponseEntity.ok(new RefrigerateurDTO(refrigerateur));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Refrigerateur> optional = refrigerateurRepository.findById(id);
		if (optional.isPresent()) {
			refrigerateurRepository.deleteById(id);
			saveLog("delete refrigerateur " + id);
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
