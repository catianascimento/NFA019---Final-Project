package fr.com.nfa019.restaurant.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.com.nfa019.restaurant.modele.Utilisateur;
import fr.com.nfa019.restaurant.repository.UtilisateurRepository;

@Service
public class AuthenticationService implements UserDetailsService{

	@Autowired
	private UtilisateurRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Utilisateur> utilisateur = repository.findByLogin(username);
		
		if(utilisateur.isPresent()) {
			return utilisateur.get();
		}
		throw new UsernameNotFoundException("Donnees invalides: " + username);
	}

}
