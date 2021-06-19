package fr.com.nfa019.restaurant.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import fr.com.nfa019.restaurant.modele.Historique;

public class HistoriqueDTO {

	private Long id;
	private Long utilisateurId;
	private String utilisateurLogin;
	private LocalDateTime dateAndTime;
	private String action;

	public HistoriqueDTO(Historique historique) {
		this.id = historique.getId();
		this.utilisateurId = historique.getUtilisateur().getId();
		this.utilisateurLogin = historique.getUtilisateur().getLogin();
		this.dateAndTime = historique.getDateAndTime();
		this.action = historique.getAction();
	}

	public static List<HistoriqueDTO> converter(List<Historique> historiques) {
		return historiques.stream().map(HistoriqueDTO::new).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getUtilisateurLogin() {
		return utilisateurLogin;
	}

	public void setUtilisateurLogin(String utilisateurLogin) {
		this.utilisateurLogin = utilisateurLogin;
	}

}
