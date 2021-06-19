package fr.com.nfa019.restaurant.beans;

public class UtilisateurBean {

	private Long id;
	private String nom;
	private String prenom;
	private String adresseMail;
	private String motDePasse;
	private String login;
//	private ArrayList<Integer> roleIds;
//	private ArrayList<RoleBean> roles;

	private Integer roleId;

	private RoleBean role;
	
	@Override
	public String toString() {
		return "UtilisateurBean [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", adresseMail=" + adresseMail
				+ ", motDePasse=" + motDePasse + ", login=" + login + ", roleId=" + roleId + ", role=" + role + "]";
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public RoleBean getRole() {
		return role;
	}

	public void setRole(RoleBean role) {
		this.role = role;
	}

}
