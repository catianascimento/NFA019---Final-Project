package fr.com.nfa019.restaurant.httpclients;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;

import fr.com.nfa019.restaurant.beans.RoleBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.utils.JSONUtils;

public class HttpClientRole {

	String password = "";
	String login = "";

	public HttpClientRole(UtilisateurBean utilisateur) {
		this.login = utilisateur.getLogin();
		this.password = utilisateur.getMotDePasse();
	}

	private final String serviceURL = "http://localhost:8080/roles";

	public List<RoleBean> getAllRoles()
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String url = serviceURL;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		List<RoleBean> Roles = JSONUtils.convertFromJsonToList(response.getBody(), new TypeReference<List<RoleBean>>() {
		});
		Roles.forEach(Role -> System.out.println(Role.toString()));
		return Roles;
	}

	// sending request to retrieve all the Roles available.
	public int saveRole(RoleBean produit)
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		var RoleJson = JSONUtils.convertFromObjectToJson(produit);

		String url = serviceURL;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(RoleJson, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);

		return result.getStatusCodeValue();
	}

	// sending request retrieve the Role based on the RoleId
	public RoleBean getRoleDetailsById(Integer id) throws InterruptedException, ExecutionException, IOException {
		String url = serviceURL + "/" + id;

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		int statusCode = response.getStatusCode().value();
		if (statusCode == 500) {
			System.out.println("Role Not Avaialble");
			return null;
		} else {
			RoleBean bean = JSONUtils.convertFromJsonToObject(response.getBody(), RoleBean.class);
			System.out.println(bean);
			return bean;
		}
	}

	// send request to update the Role details.
	public void updateRole(Integer id, RoleBean Role) throws InterruptedException, ExecutionException, IOException {
		var RoleJson = JSONUtils.convertFromObjectToJson(Role);

		String url = serviceURL + "/" + id;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<String> entity = new HttpEntity<String>(RoleJson, headers);
		restTemplate.put(url, entity);

	}

	// send request to delete the Role by its RoleId
	public void deleteRole(Integer id) throws ExecutionException, InterruptedException, IOException {
		String url = serviceURL + "/" + id;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);

	}

}
