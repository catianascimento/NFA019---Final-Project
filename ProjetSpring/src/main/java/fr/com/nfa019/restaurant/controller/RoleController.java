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

import fr.com.nfa019.restaurant.controller.form.RoleForm;
import fr.com.nfa019.restaurant.dto.RoleDTO;
import fr.com.nfa019.restaurant.modele.Historique;
import fr.com.nfa019.restaurant.modele.Role;
import fr.com.nfa019.restaurant.repository.HistoriqueRepository;
import fr.com.nfa019.restaurant.repository.RoleRepository;
import fr.com.nfa019.restaurant.repository.UtilisateurRepository;

@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private HistoriqueRepository historiqueRepository;
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@GetMapping
	public List<RoleDTO> lister() {
		List<Role> roles = roleRepository.findAll();
		return RoleDTO.converter(roles);
	}

	@PostMapping
	@Transactional
	public ResponseEntity<RoleDTO> save(@RequestBody @Valid RoleForm form) {
		Role role = form.converter();
		roleRepository.save(role);
		saveLog("save role "+ role.getId());
		return ResponseEntity.ok(new RoleDTO(role));
	}

	@GetMapping("/{id}")
	public ResponseEntity<RoleDTO> get(@PathVariable Long id) {
		Optional<Role> role = roleRepository.findById(id);
		if (role.isPresent()) {
			return ResponseEntity.ok(new RoleDTO(role.get()));
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<RoleDTO> update(@PathVariable Long id, @RequestBody @Valid RoleForm form) {
		Optional<Role> optional = roleRepository.findById(id);
		if (optional.isPresent()) {
			Role role = form.update(id, roleRepository);
			saveLog("update role "+ role.getId());
			return ResponseEntity.ok(new RoleDTO(role));
		}

		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Role> optional = roleRepository.findById(id);
		if (optional.isPresent()) {
			roleRepository.deleteById(id);
			saveLog("delete role "+ id);
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
