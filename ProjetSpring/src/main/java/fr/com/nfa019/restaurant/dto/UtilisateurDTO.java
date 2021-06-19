package fr.com.nfa019.restaurant.dto;

import java.util.List;
import java.util.stream.Collectors;

import fr.com.nfa019.restaurant.modele.Utilisateur;

public class UtilisateurDTO {

	private Long id;
	private String nom;
	private String prenom;
	private String adresseMail;
	private String motDePasse;
	private String login;
//	private List<Long> roleIds;
	private Long roleId;

	public UtilisateurDTO(Utilisateur utilisateur) {
		this.id = utilisateur.getId();
		this.nom = utilisateur.getNom();
		this.prenom = utilisateur.getPrenom();
		this.adresseMail = utilisateur.getAdresseMail();
		this.motDePasse = utilisateur.getMotDePasse();
		this.login = utilisateur.getLogin();
//		this.setRoleIds(utilisateur.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
		this.setRoleId(utilisateur.getRole().getId());
	}

	public static List<UtilisateurDTO> converter(List<Utilisateur> utilisateurs) {
		return utilisateurs.stream().map(UtilisateurDTO::new).collect(Collectors.toList());
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresseMail() {
		return adresseMail;
	}

	public void setAdresseMail(String adresseMail) {
		this.adresseMail = adresseMail;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

//	public List<Long> getRoleIds() {
//		return roleIds;
//	}
//
//	public void setRoleIds(List<Long> roleIds) {
//		this.roleIds = roleIds;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
