package fr.com.nfa019.restaurant.controller.form;

import java.util.Optional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import fr.com.nfa019.restaurant.modele.Role;
import fr.com.nfa019.restaurant.modele.Utilisateur;
import fr.com.nfa019.restaurant.repository.RoleRepository;
import fr.com.nfa019.restaurant.repository.UtilisateurRepository;

public class UtilisateurForm {

//	@NotNull
	@Length(min = 10)
	private String message;

	@NotNull
	@NotEmpty
	private String nom;

	private String prenom;
	private String adresseMail;
	private String motDePasse;
	private String login;
//	private List<Long> roleIds;
	private Long roleId;

	public Utilisateur converter(RoleRepository roleRepository) {
		Utilisateur utilisateur = new Utilisateur(nom, prenom, adresseMail, motDePasse, login);

//		List<Role> roles = new ArrayList<Role>();
//		for (Long id : roleIds) {
//			Optional<Role> role = roleRepository.findById(id);
//			if (role.isPresent()) {
//				roles.add(role.get());
//			}
//		}
		Optional<Role> role = roleRepository.findById(getRoleId());

//		utilisateur.setRoles(roles);
		utilisateur.setRole(role.get());
		return utilisateur;
	}

	public Utilisateur update(Long id, UtilisateurRepository utilisateurRepository, RoleRepository roleRepository) {
		Utilisateur utilisateur = utilisateurRepository.getOne(id);

		utilisateur.setNom(this.getNom());
		utilisateur.setPrenom(this.getPrenom());
		utilisateur.setLogin(this.getLogin());
		utilisateur.setAdresseMail(this.getAdresseMail());
		utilisateur.setMotDePasse(this.getMotDePasse());

//		List<Role> roles = new ArrayList<Role>();
//		for (Long roleId : roleIds) {
//			Optional<Role> role = roleRepository.findById(roleId);
//			if (role.isPresent()) {
//				roles.add(role.get());
//			}
//		}
		Optional<Role> role = roleRepository.findById(getRoleId());
		utilisateur.setRole(role.get());
		return utilisateur;
//		utilisateur.setRoles(roles);
//		return utilisateur;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

//	public List<Long> getRoleIds() {
//		return roleIds;
//	}
//
//	public void setRoleIds(List<Long> roleIds) {
//		this.roleIds = roleIds;
//	}

}
