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

import fr.com.nfa019.restaurant.beans.CategorieBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.utils.JSONUtils;

public class HttpClientCategorie {

	String password = "";
	String login = "";

	public HttpClientCategorie(UtilisateurBean utilisateur) {
		this.login = utilisateur.getLogin();
		this.password = utilisateur.getMotDePasse();
	}

	private final String serviceURL = "http://localhost:8080/categories";

	public List<CategorieBean> getAllCategories()
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String url = serviceURL;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		List<CategorieBean> Categories = JSONUtils.convertFromJsonToList(response.getBody(),
				new TypeReference<List<CategorieBean>>() {
				});
		Categories.forEach(Categorie -> System.out.println(Categorie.toString()));
		return Categories;
	}

	// sending request to retrieve all the Categories available.
	public int saveCategorie(CategorieBean produit)
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		var CategorieJson = JSONUtils.convertFromObjectToJson(produit);

		String url = serviceURL;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(CategorieJson, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);

		return result.getStatusCodeValue();
	}

	// sending request retrieve the Categorie based on the CategorieId
	public void getCategorieDetailsById(Integer id) throws InterruptedException, ExecutionException, IOException {
		String url = serviceURL + "/" + id;

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		int statusCode = response.getStatusCode().value();
		if (statusCode == 500)
			System.out.println("Categorie Not Avaialble");
		else {
			CategorieBean bean = JSONUtils.convertFromJsonToObject(response.getBody(), CategorieBean.class);
			System.out.println(bean);
		}
	}

	// send request to update the Categorie details.
	public void updateCategorie(Integer id, CategorieBean Categorie)
			throws InterruptedException, ExecutionException, IOException {
		var CategorieJson = JSONUtils.convertFromObjectToJson(Categorie);

		String url = serviceURL + "/" + id;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<String> entity = new HttpEntity<String>(CategorieJson, headers);
		restTemplate.put(url, entity);

	}

	// send request to delete the Categorie by its CategorieId
	public void deleteCategorie(Integer id) throws ExecutionException, InterruptedException, IOException {
		String url = serviceURL + "/" + id;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);

	}

}
