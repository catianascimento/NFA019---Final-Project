package fr.com.nfa019.restaurant.controller.form;

import java.time.LocalDateTime;
import java.util.Optional;

import fr.com.nfa019.restaurant.modele.Historique;
import fr.com.nfa019.restaurant.modele.Utilisateur;
import fr.com.nfa019.restaurant.repository.HistoriqueRepository;
import fr.com.nfa019.restaurant.repository.UtilisateurRepository;

public class HistoriqueForm {

	private Long utilisateurId;
	private LocalDateTime dateAndTime;
	private String action;

	public Historique converter(UtilisateurRepository utilisateurRepository) {
		Optional<Utilisateur> utilisateur = utilisateurRepository.findById(utilisateurId);

		Historique historique = new Historique(dateAndTime, action);
		if (utilisateur.isPresent()) {
			historique.setUtilisateur(utilisateur.get());
		}
		return historique;
	}

	public Historique update(Long id, HistoriqueRepository historiqueRepository,
			UtilisateurRepository utilisateurRepository) {
		Optional<Utilisateur> utilisateur = utilisateurRepository.findById(utilisateurId);

		Historique historique = historiqueRepository.getOne(id);

		historique.setDateAndTime(this.getDateAndTime());
		historique.setAction(this.getAction());
		if (utilisateur.isPresent()) {
			historique.setUtilisateur(utilisateur.get());
		}

		return historique;
	}

	public LocalDateTime getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(LocalDateTime dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Long getUtilisateurId() {
		return utilisateurId;
	}

	public void setUtilisateurId(Long utilisateurId) {
		this.utilisateurId = utilisateurId;
	}

}
