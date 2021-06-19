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

import fr.com.nfa019.restaurant.beans.RefrigerateurBean;
import fr.com.nfa019.restaurant.beans.RefrigerateurTemperatureBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.utils.JSONUtils;

public class HttpClientRefrigerateur {
	String password;
	String login;

	public HttpClientRefrigerateur(UtilisateurBean utilisateur) {
		this.login = utilisateur.getLogin();
		this.password = utilisateur.getMotDePasse();
	}

	private final String serviceURL = "http://localhost:8080/refrigerateurs";

	public List<RefrigerateurBean> getAllRefrigerateurs()
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String url = serviceURL;

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		List<RefrigerateurBean> Refrigerateurs = JSONUtils.convertFromJsonToList(response.getBody(),
				new TypeReference<List<RefrigerateurBean>>() {
				});
		Refrigerateurs.forEach(Refrigerateur -> System.out.println(Refrigerateur.toString()));
		return Refrigerateurs;
	}

	public List<RefrigerateurTemperatureBean> getAllRefrigerateurTemperatures(Long refrigerateurId)
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String url = serviceURL + "/" + refrigerateurId + "/refrigerateur-temperature";

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		List<RefrigerateurTemperatureBean> Refrigerateurs = JSONUtils.convertFromJsonToList(response.getBody(),
				new TypeReference<List<RefrigerateurTemperatureBean>>() {
				});
		Refrigerateurs.forEach(Refrigerateur -> System.out.println(Refrigerateur.toString()));
		return Refrigerateurs;
	}

	// sending request to retrieve all the Refrigerateurs available.
	public int saveRefrigerateur(RefrigerateurBean refrigerateur)
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		var RefrigerateurJson = JSONUtils.convertFromObjectToJson(refrigerateur);

		String url = serviceURL;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(RefrigerateurJson, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);

		return result.getStatusCodeValue();
	}

	// sending request retrieve the Refrigerateur based on the RefrigerateurId
	public void getRefrigerateurDetailsById(Integer id) throws InterruptedException, ExecutionException, IOException {
		String url = serviceURL + "/" + id;

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		int statusCode = response.getStatusCode().value();
		if (statusCode == 500)
			System.out.println("Refrigerateur Not Avaialble");
		else {
			RefrigerateurBean bean = JSONUtils.convertFromJsonToObject(response.getBody(), RefrigerateurBean.class);
			System.out.println(bean);
		}
	}

	// send request to update the Refrigerateur details.
	public void updateRefrigerateur(Long id, RefrigerateurBean Refrigerateur)
			throws InterruptedException, ExecutionException, IOException {
		var RefrigerateurJson = JSONUtils.convertFromObjectToJson(Refrigerateur);

		String url = serviceURL + "/" + id;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<String> entity = new HttpEntity<String>(RefrigerateurJson, headers);
		restTemplate.put(url, entity);

	}

	// sending request to retrieve all the Refrigerateurs available.
	public int saveRefrigerateurTemperature(Long id, RefrigerateurTemperatureBean refrigerateurTemperature)
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		var RefrigerateurTemperatureJson = JSONUtils.convertFromObjectToJson(refrigerateurTemperature);

		String url = serviceURL + "/" + id + "/refrigerateur-temperature";
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(RefrigerateurTemperatureJson, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);

		return result.getStatusCodeValue();
	}

	// send request to delete the Refrigerateur by its RefrigerateurId
	public void deleteRefrigerateur(Long id) throws ExecutionException, InterruptedException, IOException {
		String url = serviceURL + "/" + id;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);

	}

}
