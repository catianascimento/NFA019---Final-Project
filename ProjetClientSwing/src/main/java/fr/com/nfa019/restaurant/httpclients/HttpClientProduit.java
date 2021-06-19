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

import fr.com.nfa019.restaurant.beans.ProduitBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.utils.JSONUtils;

public class HttpClientProduit {

	String password = "";
	String login = "";
	
	UtilisateurBean utilisateurBean;

	public HttpClientProduit(UtilisateurBean utilisateur) {
		this.login = utilisateur.getLogin();
		this.password = utilisateur.getMotDePasse();
	}

	private final String serviceURL = "http://localhost:8080/produits";

	public List<ProduitBean> getAllProduits()
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String url = serviceURL;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		List<ProduitBean> Produits = JSONUtils.convertFromJsonToList(response.getBody(),
				new TypeReference<List<ProduitBean>>() {
				});
		Produits.forEach(Produit -> System.out.println(Produit.toString()));
		return Produits;
	}

	public List<ProduitBean> getAllAvailablesProduits()
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String url = serviceURL + "/produits-disponibles";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		List<ProduitBean> Produits = JSONUtils.convertFromJsonToList(response.getBody(),
				new TypeReference<List<ProduitBean>>() {
				});
		Produits.forEach(Produit -> System.out.println(Produit.toString()));
		return Produits;
	}

	// sending request to retrieve all the Produits available.
	public int saveProduit(ProduitBean produit)
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		var ProduitJson = JSONUtils.convertFromObjectToJson(produit);

		String url = serviceURL;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(ProduitJson, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);

		return result.getStatusCodeValue();
	}

	// sending request retrieve the Produit based on the ProduitId
	public ProduitBean getProduitDetailsById(Long id) throws InterruptedException, ExecutionException, IOException {
		String url = serviceURL + "/" + id;

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		int statusCode = response.getStatusCode().value();
		if (statusCode == 500) {
			System.out.println("Produit Not Avaialble");
			return null;
		} else {
			ProduitBean bean = JSONUtils.convertFromJsonToObject(response.getBody(), ProduitBean.class);
			return bean;
		}
	}

	// send request to update the Produit details.
	public void updateProduit(Long id, ProduitBean Produit)
			throws InterruptedException, ExecutionException, IOException {
		String url = serviceURL + "/" + id;
		var ProduitJson = JSONUtils.convertFromObjectToJson(Produit);
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<String> entity = new HttpEntity<String>(ProduitJson, headers);
		restTemplate.put(url, entity);

	}

	// send request to delete the Produit by its ProduitId
	public void deleteProduit(Long id) throws ExecutionException, InterruptedException, IOException {
		String url = serviceURL + "/" + id;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);
	}

}
