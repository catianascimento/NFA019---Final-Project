package fr.com.nfa019.restaurant.controller;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.com.nfa019.restaurant.controller.form.UtilisateurForm;
import fr.com.nfa019.restaurant.dto.UtilisateurDTO;
import fr.com.nfa019.restaurant.modele.Historique;
import fr.com.nfa019.restaurant.modele.Utilisateur;
import fr.com.nfa019.restaurant.repository.HistoriqueRepository;
import fr.com.nfa019.restaurant.repository.RoleRepository;
import fr.com.nfa019.restaurant.repository.UtilisateurRepository;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private HistoriqueRepository historiqueRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@GetMapping
	public List<UtilisateurDTO> lister() {
		List<Utilisateur> produits = utilisateurRepository.findAll();
		return UtilisateurDTO.converter(produits);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<UtilisateurDTO> save(@RequestBody @Valid UtilisateurForm form) {
		Utilisateur utilisateur = form.converter(roleRepository);
		utilisateur.setMotDePasse(new BCryptPasswordEncoder().encode(utilisateur.getMotDePasse()));

		utilisateurRepository.save(utilisateur);
		saveLog("save utilisateur " + utilisateur.getId());
		return ResponseEntity.ok(new UtilisateurDTO(utilisateur));
	}

	@GetMapping("/{id}")
	public ResponseEntity<UtilisateurDTO> get(@PathVariable Long id) {
		Optional<Utilisateur> utilisateur = utilisateurRepository.findById(id);
		if (utilisateur.isPresent()) {
			return ResponseEntity.ok(new UtilisateurDTO(utilisateur.get()));
		}

		return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/login/{id}")
	public ResponseEntity<UtilisateurDTO> getByLogin(@PathVariable String login) {
		Optional<Utilisateur> utilisateur = utilisateurRepository.findByLogin(login);
		if (utilisateur.isPresent()) {
			return ResponseEntity.ok(new UtilisateurDTO(utilisateur.get()));
		}

		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<UtilisateurDTO> update(@PathVariable Long id, @RequestBody @Valid UtilisateurForm form) {
		Optional<Utilisateur> optional = utilisateurRepository.findById(id);
		if (optional.isPresent()) {
			Utilisateur utilisateur = form.update(id, utilisateurRepository, roleRepository);
			utilisateur.setMotDePasse(new BCryptPasswordEncoder().encode(utilisateur.getMotDePasse()));
			saveLog("update utilisateur " + utilisateur.getId());
			return ResponseEntity.ok(new UtilisateurDTO(utilisateur));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Utilisateur> optional = utilisateurRepository.findById(id);
		if (optional.isPresent()) {
			utilisateurRepository.deleteById(id);
			saveLog("delete utilisateur " + id);
			return ResponseEntity.ok().build();
		}

		return ResponseEntity.notFound().build();
	}

	@GetMapping("/verify")
	public ResponseEntity<UtilisateurDTO> verifyLogin(@RequestParam String login, @RequestParam String motDePasse) {
		Optional<Utilisateur> optional = utilisateurRepository.findByLogin(login);
		if (optional.isPresent()) {
			var utilisateur = optional.get();
			if (new BCryptPasswordEncoder().matches(String.valueOf(motDePasse), utilisateur.getMotDePasse())) {
				return ResponseEntity.ok(new UtilisateurDTO(utilisateur));
			} else {
				return ResponseEntity.notFound().build();
			}
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
