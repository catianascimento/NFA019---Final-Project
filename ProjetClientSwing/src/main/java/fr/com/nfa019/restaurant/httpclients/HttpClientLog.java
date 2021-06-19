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

import fr.com.nfa019.restaurant.beans.LogBean;
import fr.com.nfa019.restaurant.beans.UtilisateurBean;
import fr.com.nfa019.restaurant.utils.JSONUtils;

public class HttpClientLog {
	String password;
	String login;

	public HttpClientLog(UtilisateurBean utilisateur) {
		this.login = utilisateur.getLogin();
		this.password = utilisateur.getMotDePasse();
	}

	private final String serviceURL = "http://localhost:8080/historiques";

	public List<LogBean> getAllLogs()
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		String url = serviceURL;

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

		List<LogBean> Logs = JSONUtils.convertFromJsonToList(response.getBody(), new TypeReference<List<LogBean>>() {
		});
		Logs.forEach(Log -> System.out.println(Log.toString()));
		return Logs;
	}

	// sending request to retrieve all the Logs available.
	public int saveLog(LogBean log)
			throws InterruptedException, ExecutionException, JsonParseException, JsonMappingException, IOException {
		var LogJson = JSONUtils.convertFromObjectToJson(log);

		String url = serviceURL;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(LogJson, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);

		return result.getStatusCodeValue();
	}

	// sending request retrieve the Log based on the LogId
	public void getLogDetailsById(Integer id) throws InterruptedException, ExecutionException, IOException {
		String url = serviceURL + "/" + id;

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
		int statusCode = response.getStatusCode().value();
		if (statusCode == 500)
			System.out.println("Log Not Avaialble");
		else {
			LogBean bean = JSONUtils.convertFromJsonToObject(response.getBody(), LogBean.class);
			System.out.println(bean);
		}
	}

	// send request to update the Log details.
	public void updateLog(Integer id, LogBean Log) throws InterruptedException, ExecutionException, IOException {
		var LogJson = JSONUtils.convertFromObjectToJson(Log);

		String url = serviceURL;
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		HttpEntity<String> entity = new HttpEntity<String>(LogJson, headers);
		restTemplate.put(url, entity);

	}

	// send request to delete the Log by its LogId
	public void deleteLog(Integer id) throws ExecutionException, InterruptedException, IOException {
		String url = serviceURL + "/" + id;
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.setBasicAuth(this.login, this.password);

		HttpEntity<Object> request = new HttpEntity<Object>(headers);
		restTemplate.exchange(url, HttpMethod.DELETE, request, String.class);

	}

}
