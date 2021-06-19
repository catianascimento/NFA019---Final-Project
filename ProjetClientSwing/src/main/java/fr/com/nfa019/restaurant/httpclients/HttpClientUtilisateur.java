package fr.com.nfa019.restaurant.httpclients;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.utils.JSONUtils;

public class HttpClientUtilisateur {

	String password;
	String login;

	public HttpClientUtilisateur(UtilisateurBean utilisateur) {
		this.login = utilisateur.getLogin();
		this.password = utilisateur.getMotDePasse();
	}

	private final String serviceURL = "http://localhost:8080/utilisateurs";

	public List<UtilisateurBean> getAllUtilisateurs()
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String url = serviceURL;

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		List<UtilisateurBean> Utilisateurs = JSONUtils.convertFromJsonToList(response.getBody(),
				new TypeReference<List<UtilisateurBean>>() {
				});
		Utilisateurs.forEach(Utilisateur -> System.out.println(Utilisateur.toString()));
		return Utilisateurs;
	}

	// sending request to retrieve all the Utilisateurs available.
	public int saveUtilisateur(UtilisateurBean utilisateur)
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		var UtilisateurJson = JSONUtils.convertFromObjectToJson(utilisateur);

		String url = serviceURL;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(UtilisateurJson, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);

		return result.getStatusCodeValue();
	}

	// sending request retrieve the Utilisateur based on the UtilisateurId
	public UtilisateurBean getUtilisateurDetailsById(Integer id)
			throws InterruptedException, ExecutionException, IOException {
		String url = serviceURL + "/" + id;

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		int statusCode = response.getStatusCode().value();
		if (statusCode == 500) {
			System.out.println("Utilisateur Not Available");
			return null;
		} else {
			UtilisateurBean bean = JSONUtils.convertFromJsonToObject(response.getBody(), UtilisateurBean.class);
			System.out.println(bean);
			return bean;
		}
	}

	// sending request retrieve the Utilisateur based on the UtilisateurId
	public UtilisateurBean verifyUtilisateurByLogin(UtilisateurBean utilisateur)
			throws InterruptedException, ExecutionException, IOException {
		String url = serviceURL + "/verify/?login=" + utilisateur.getLogin() + "&motDePasse="
				+ utilisateur.getMotDePasse();

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		int statusCode = response.getStatusCode().value();
		if (statusCode == 500) {
			System.out.println("Utilisateur Not Available");
			return null;
		} else {
			UtilisateurBean bean = JSONUtils.convertFromJsonToObject(response.getBody(), UtilisateurBean.class);
			System.out.println(bean);
			return bean;
		}
	}

	// send request to update the Utilisateur details.
	public void updateUtilisateur(Long id, UtilisateurBean Utilisateur)
			throws InterruptedException, ExecutionException, IOException {
		var UtilisateurJson = JSONUtils.convertFromObjectToJson(Utilisateur);

		String url = serviceURL + "/" + id;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<String> entity = new HttpEntity<String>(UtilisateurJson, headers);
		restTemplate.put(url, entity);

	}

	// send request to delete the Utilisateur by its UtilisateurId
	public void deleteUtilisateur(Long id) throws ExecutionException, InterruptedException, IOException {
		String url = serviceURL + "/" + id;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);

	}

}
